package vault.backend.secret.generic;

import static vault.util.Util.checkArgumentCondition;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import vault.Vault;
import vault.backend.BackendConfig;
import vault.backend.Backends;
import vault.backend.VaultBackend;
import vault.domain.KeyList;
import vault.domain.kv.Configuration;
import vault.domain.kv.Metadata;
import vault.domain.kv.Options;
import vault.domain.kv.SecretMetadata;
import vault.domain.kv.V2Request;
import vault.domain.kv.V2Response;
import vault.domain.response.HttpResponse;
import vault.domain.response.HttpResponse.KvV2HttpResponse;
import vault.domain.response.HttpResponse.KeyListHttpResponse;
import vault.domain.response.HttpResponse.MetadataHttpResponse;
import vault.domain.response.HttpResponse.SecretMetadataHttpResponse;
import vault.domain.response.HttpResponse.StringMapHttpResponse;
import vault.domain.response.MountOutput;
import vault.exception.VaultApiException;
import vault.exception.VaultAuthorizationException;
import vault.exception.VaultException;

public final class KV extends VaultBackend {
	private static final String KV_V1_API_PATH = "/v1/%s/%%s";
	private static final String KV_V2_CONFIG_API_PATH = "/v1/%s/config";
	private static final String KV_V2_DATA_API_PATH = "/v1/%s/data/%%s";
	private static final String KV_V2_DELETE_API_PATH = "/v1/%s/delete/%%s";
	private static final String KV_V2_DESTROY_API_PATH = "/v1/%s/destroy/%%s";
	private static final String KV_V2_METADATA_API_PATH = "/v1/%s/metadata/%%s";
	private static final String KV_V2_UNDELETE_API_PATH = "/v1/%s/undelete/%%s";

	private int version;
	private String kvV1ApiPath;
	private String kvV2ConfigApiPath;
	private String kvV2DataApiPath;
	private String kvV2DeleteApiPath;
	private String kvV2DestroyApiPath;
	private String kvV2MetadataApiPath;
	private String kvV2UndeleteApiPath;

	public KV(String accessor, BackendConfig config, String description, boolean local, String name, boolean sealWrap, Vault vault, int version) {
		super(accessor, config, description, local, name, sealWrap, Backends.KV, vault);

		if (version != 1 && version != 2) {
			throw new IllegalArgumentException("KV version must be either 1 or 2");
		}

		this.version = version;
		this.kvV1ApiPath = String.format(KV_V1_API_PATH, this.name);
		this.kvV2ConfigApiPath = String.format(KV_V2_CONFIG_API_PATH, this.name);
		this.kvV2DataApiPath = String.format(KV_V2_DATA_API_PATH, this.name);
		this.kvV2DeleteApiPath = String.format(KV_V2_DELETE_API_PATH, this.name);
		this.kvV2DestroyApiPath = String.format(KV_V2_DESTROY_API_PATH, this.name);
		this.kvV2MetadataApiPath = String.format(KV_V2_METADATA_API_PATH, this.name);
		this.kvV2UndeleteApiPath = String.format(KV_V2_UNDELETE_API_PATH, this.name);
	}

	public static KV from(MountOutput mountOutput, String name, Vault vault) {
		Map<String, String> mountOptions = mountOutput.getOptions();
		checkArgumentCondition(mountOptions.containsKey("version"), "MountOutput options must contain key 'version'.");

		return new KV(mountOutput.getAccessor(), mountOutput.getConfig() != null ? BackendConfig.from(mountOutput.getConfig()) : null, mountOutput.getDescription(), mountOutput.isLocal(), name, mountOutput.isSealWrap(), vault, Integer.parseInt(mountOptions.get("version")));
	}

	public boolean configureEngine(Configuration configuration) {
		if (this.version == 1) {
			throw new UnsupportedOperationException("Configuring the engine is only available for version 2 KV backends.");
		}

		try {
			post(this.getKvV2ConfigApiPath(), configuration);
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error updating Configuration for KV {}.", this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to configure KV backend {} (V2).", this.name, e);
		}

		return false;
	}

	public boolean configureSecret(String path, Configuration configuration) {
		return updateMetadata(path, configuration);
	}

	public boolean createSecret(String path, Map<String, String> data) {
		return createSecret(path, data, null);
	}

	public boolean createSecret(String path, Map<String, String> data, Options options) {
		if (secretExists(path)) {
			throw new VaultException("Path " + path + " already exists in KV backend '" + this.name + "'");
		}

		if (this.version == 1) {
			return createSecretV1(path, data);
		} else if (this.version == 2) {
			return createOrUpdateSecretV2(path, data, options) != null;
		}

		return false;
	}

	public boolean deleteSecret(String path) {
		try {
			if (this.version == 1) {
				delete(this.getKvV1ApiPath(path));
			} else if (this.version == 2) {
				delete(this.getKvV2DeleteApiPath(path));
			}
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error deleting secret at path {} in KV {} (V{}).", path, this.name, Integer.valueOf(this.version), e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to delete secret at path {} in KV backend {} (V{}).", path, this.name, Integer.valueOf(this.version), e);
		}

		return false;
	}

	public boolean deleteSecretVersions(String path, int... kvVersions) {
		return deleteSecretVersions(path, Arrays.stream(kvVersions).boxed().collect(Collectors.toList()));
	}

	public boolean deleteSecretVersions(String path, List<Integer> kvVersions) {
		if (this.version == 1) {
			throw new UnsupportedOperationException("Deleting secret versions is only available for version 2 KV backends.");
		}

		checkArgumentCondition(!kvVersions.isEmpty(), "KV Versions to delete must not be empty.");

		try {
			post(this.getKvV2DeleteApiPath(path), createVersionsPayload(kvVersions));
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error deleting secret versions {} at path {} in KV backend {}.", kvVersions, path, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to delete secret versions at path {} in KV backend {} (V2).", path, this.name, e);
		}

		return false;
	}

	public boolean destroySecretVersions(String path, int... kvVersions) {
		return destroySecretVersions(path, Arrays.stream(kvVersions).boxed().collect(Collectors.toList()));
	}

	public boolean destroySecretVersions(String path, List<Integer> kvVersions) {
		if (this.version == 1) {
			throw new UnsupportedOperationException("Destroying secret versions is only available for version 2 KV backends.");
		}

		checkArgumentCondition(!kvVersions.isEmpty(), "KV Versions to destroy must not be empty.");

		try {
			post(this.getKvV2DestroyApiPath(path), createVersionsPayload(kvVersions));
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error destroying secret versions {} at path {} in KV backend {}.", kvVersions, path, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to destroy secret versions at path {} in KV backend {} (V2).", path, this.name, e);
		}

		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		KV other = (KV) obj;
		return this.version == other.version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(Integer.valueOf(this.version));
		return result;
	}

	public KeyList listSecrets(String path) {
		Map<String, String> queryParameters = Collections.singletonMap("list", "true");
		String apiPath = null;

		if (this.version == 1) {
			apiPath = this.getKvV1ApiPath(path == null ? "" : path);
		} else if (this.version == 2) {
			apiPath = this.getKvV2MetadataApiPath(path == null ? "" : path);
		}

		try {
			return get(apiPath, queryParameters, KeyListHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			this.log.error("Error listing secret keys at path {} in KV {} (V{}).", path, this.name, Integer.valueOf(this.version), e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to lists secrets at path {} in KV backend {} (V{}).", path, this.name, Integer.valueOf(this.version), e);
		}

		return null;
	}

	public Configuration readEngineConfiguration() {
		if (this.version == 1) {
			throw new UnsupportedOperationException("Read engine configuration is only available for version 2 KV backends.");
		}

		try {
			return get(this.getKvV2ConfigApiPath(), Configuration.class).getBody();
		} catch (VaultApiException e) {
			this.log.error("Error reading Configuration for KV {}.", this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to read engine configuration for KV backend {} (V2).", this.name, e);
		}

		return null;
	}

	public Map<String, String> readSecret(String path) {
		return readSecretVersion(path, 0);
	}

	public SecretMetadata readSecretMetadata(String path) {
		if (this.version == 1) {
			throw new UnsupportedOperationException("Read Secret Metadata is only available for version 2 KV backends.");
		}

		try {
			return get(this.getKvV2MetadataApiPath(path), SecretMetadataHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			this.log.error("Error reading secret metadata at path {} in KV backend {}.", path, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to read secret metadata at path {} in KV backend {}.", path, this.name, e);
		}

		return null;
	}

	public Map<String, String> readSecretVersion(String path, int kvVersion) {
		if (this.version == 1) {
			HttpResponse<Map<String, String>> rawResponse = readSecretV1(path);
			if (rawResponse != null) {
				return rawResponse.getData();
			}
		} else if (this.version == 2) {
			HttpResponse<V2Response> rawResponse = readSecretV2(path, kvVersion);
			if (rawResponse != null && rawResponse.getData() != null) {
				return rawResponse.getData().getData();
			}
		}

		return Collections.emptyMap();
	}

	public boolean secretExists(String path) {
		if (path == null || path.length() == 0) {
			return false;
		}

		StringBuilder pathToCheck = new StringBuilder("");
		String[] splitPath = path.split("(?<=/)");
		for (String split : splitPath) {
			KeyList keyList = listSecrets(pathToCheck.toString());
			if (keyList == null || keyList.getKeys() == null || !keyList.getKeys().contains(split)) {
				return false;
			}
			pathToCheck.append(split);
		}

		return true;
	}

	@Override
	public String toString() {
		return "KV [version=" + this.version + ", type=" + this.type + ", accessor=" + this.accessor + ", config=" + this.config + ", description=" + this.description + ", local=" + this.local + ", name=" + this.name + ", sealWrap=" + this.sealWrap + ", vault=" + this.vault + "]";
	}

	public boolean undeleteSecretVersions(String path, int... kvVersions) {
		return undeleteSecretVersions(path, Arrays.stream(kvVersions).boxed().collect(Collectors.toList()));
	}

	public boolean undeleteSecretVersions(String path, List<Integer> kvVersions) {
		if (this.version == 1) {
			throw new UnsupportedOperationException("Undeleting secret versions is only available for version 2 KV backends.");
		}

		checkArgumentCondition(!kvVersions.isEmpty(), "KV Versions to undelete must not be empty.");

		try {
			post(this.getKvV2UndeleteApiPath(path), createVersionsPayload(kvVersions));
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error undeleting secret versions {} at path {} in KV backend {} (V2).", kvVersions, path, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to undelete secret versions at path {} in KV backend {} (V2).", path, this.name, e);
		}

		return false;
	}

	public boolean updateMetadata(String path, Configuration configuration) {
		if (this.version == 1) {
			throw new UnsupportedOperationException("Update metadata is only available for version 2 KV backends.");
		}

		try {
			post(this.getKvV2MetadataApiPath(path), configuration);
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error updating metadata for KV {} (V2).", this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to update metadata at path {} in KV backend {} (V2).", path, this.name, e);
		}

		return false;
	}

	public boolean updateSecret(String path, Map<String, String> data) {
		return updateSecret(path, data, null, true);
	}

	public boolean updateSecret(String path, Map<String, String> data, boolean replace) {
		return updateSecret(path, data, null, replace);
	}

	public boolean updateSecret(String path, Map<String, String> data, Options options) {
		return updateSecret(path, data, options, true);
	}

	public boolean updateSecret(String path, Map<String, String> data, Options options, boolean replace) {
		if (this.version == 1) {
			if (!replace) {
				readSecret(path).forEach(data::putIfAbsent);
			}
			return updateSecretV1(path, data);
		} else if (this.version == 2) {
			return createOrUpdateSecretV2(path, data, options) != null;
		}

		return false;
	}

	private boolean createSecretV1(String path, Map<String, String> data) {
		try {
			post(this.getKvV1ApiPath(path), data);
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error posting to path {} in KV {} (V1)", path, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to post to path {} in KV backend {} (V1).", path, this.name, e);
		}

		return false;
	}

	private HttpResponse<Metadata> createOrUpdateSecretV2(String path, Map<String, String> data, Options options) {
		try {
			return post(this.getKvV2DataApiPath(path), new V2Request(data, options), MetadataHttpResponse.class).getBody();
		} catch (VaultApiException e) {
			this.log.error("Error posting to path {} with Options {} in KV {} (V2)", path, options, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to post to path {} in KV backend {} (V2).", path, this.name, e);
		}

		return null;
	}

	private static Map<String, List<Integer>> createVersionsPayload(List<Integer> kvVersions) {
		return Collections.singletonMap("versions", kvVersions);
	}

	private HttpResponse<Map<String, String>> readSecretV1(String path) {
		try {
			return get(this.getKvV1ApiPath(path), null, StringMapHttpResponse.class).getBody();
		} catch (VaultApiException e) {
			this.log.error("Error reading from path {} in KV backend {} (V1)", path, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to read secret at path {} in KV backend {} (V1).", path, this.name, e);
		}

		return null;
	}

	private HttpResponse<V2Response> readSecretV2(String path, int kvVersion) {
		Map<String, String> queryParameters = Collections.singletonMap("version", String.valueOf(kvVersion));

		try {
			return get(this.getKvV2DataApiPath(path), queryParameters, KvV2HttpResponse.class).getBody();
		} catch (VaultApiException e) {
			this.log.error("Error reading from path {} using kv version {} in KV backend {} (V2).", path, Integer.valueOf(kvVersion), this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to read secret at path {} in KV backend {} (V2).", path, this.name, e);
		}

		return null;
	}

	private boolean updateSecretV1(String path, Map<String, String> data) {
		try {
			put(this.getKvV1ApiPath(path), data);
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error posting to path {} in KV {} (V1)", path, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to put to path {} in KV backend {} (V1).", path, this.name, e);
		}

		return false;
	}

	private String getKvV1ApiPath(String path) {
		return String.format(this.kvV1ApiPath, path);
	}

	private String getKvV2ConfigApiPath() {
		return this.kvV2ConfigApiPath;
	}

	private String getKvV2DataApiPath(String path) {
		return String.format(this.kvV2DataApiPath, path);
	}

	private String getKvV2DeleteApiPath(String path) {
		return String.format(this.kvV2DeleteApiPath, path);
	}

	private String getKvV2DestroyApiPath(String path) {
		return String.format(this.kvV2DestroyApiPath, path);
	}

	private String getKvV2MetadataApiPath(String path) {
		return String.format(this.kvV2MetadataApiPath, path);
	}

	private String getKvV2UndeleteApiPath(String path) {
		return String.format(this.kvV2UndeleteApiPath, path);
	}

	public int getVersion() {
		return this.version;
	}
}