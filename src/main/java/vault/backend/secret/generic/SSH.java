package vault.backend.secret.generic;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import vault.Vault;
import vault.backend.BackendConfig;
import vault.backend.Backends;
import vault.backend.VaultBackend;
import vault.domain.response.HttpResponse.CredentialHttpResponse;
import vault.domain.response.HttpResponse.KeyHttpResponse;
import vault.domain.response.HttpResponse.RoleHttpResponse;
import vault.domain.response.HttpResponse.RolesHttpResponse;
import vault.domain.response.HttpResponse.StringMapHttpResponse;
import vault.domain.response.HttpResponse.RoleListHttpResponse;
import vault.domain.response.MountOutput;
import vault.domain.ssh.Credential;
import vault.domain.ssh.Key;
import vault.domain.ssh.KeySign;
import vault.domain.ssh.Role;
import vault.domain.ssh.Roles;
import vault.domain.ssh.RoleList;
import vault.exception.VaultApiException;
import vault.exception.VaultAuthorizationException;

public class SSH extends VaultBackend {
	private static final String SSH_CA_API_PATH = "/v1/%s/config/ca";
	private static final String SSH_CREDS_API_PATH = "/v1/%s/creds/%%s";
	private static final String SSH_KEYS_API_PATH = "/v1/%s/keys/%%s";
	private static final String SSH_LOOKUP_API_PATH = "/v1/%s/lookup";
	private static final String SSH_PUBLIC_KEY_API_PATH = "/v1/%s/public_key";
	private static final String SSH_ROLES_API_PATH = "/v1/%s/roles/%%s";
	private static final String SSH_SIGN_KEY_API_PATH = "/v1/%s/sign/%%s";
	private static final String SSH_VERIFY_API_PATH = "/v1/%s/verify";
	private static final String SSH_ZEROADDRESS_API_PATH = "/v1/%s/config/zeroaddress";

	private String sshCaApiPath;
	private String sshCredsApiPath;
	private String sshKeysApiPath;
	private String sshLookupApiPath;
	private String sshPublicKeyApiPath;
	private String sshRolesApiPath;
	private String sshSignKeyApiPath;
	private String sshVerifyApiPath;
	private String sshZeroaddressApiPath;

	public SSH(String accessor, BackendConfig config, String description, boolean local, String name, boolean sealWrap, Vault vault) {
		super(accessor, config, description, local, name, sealWrap, Backends.SYSTEM, vault);

		this.sshCaApiPath = String.format(SSH_CA_API_PATH, this.name);
		this.sshCredsApiPath = String.format(SSH_CREDS_API_PATH, this.name);
		this.sshKeysApiPath = String.format(SSH_KEYS_API_PATH, this.name);
		this.sshLookupApiPath = String.format(SSH_LOOKUP_API_PATH, this.name);
		this.sshPublicKeyApiPath = String.format(SSH_PUBLIC_KEY_API_PATH, this.name);
		this.sshRolesApiPath = String.format(SSH_ROLES_API_PATH, this.name);
		this.sshSignKeyApiPath = String.format(SSH_SIGN_KEY_API_PATH, this.name);
		this.sshVerifyApiPath = String.format(SSH_VERIFY_API_PATH, this.name);
		this.sshZeroaddressApiPath = String.format(SSH_ZEROADDRESS_API_PATH, this.name);
	}

	public static SSH from(MountOutput mountOutput, String name, Vault vault) {
		return new SSH(mountOutput.getAccessor(), mountOutput.getConfig() != null ? BackendConfig.from(mountOutput.getConfig()) : null, mountOutput.getDescription(), mountOutput.isLocal(), name, mountOutput.isSealWrap(), vault);
	}

	public boolean configureZeroAddressRoles(String... zeroAddressRoles) {
		return configureZeroAddressRoles(Arrays.stream(zeroAddressRoles).collect(Collectors.toList()));
	}

	public boolean configureZeroAddressRoles(List<String> zeroAddressRoles) {
		return configureZeroAddressRoles(new RoleList(zeroAddressRoles));
	}

	public boolean configureZeroAddressRoles(RoleList roleList) {
		try {
			post(this.getSshZeroaddressApiPath(), roleList);
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error configuring zero-address roles in SSH backend {}.", this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to configure zero-address roles in SSH backend {}.", this.name, e);
		}

		return false;
	}

	public boolean createKey(String keyName, String key) {
		try {
			post(this.getSshKeysApiPath(keyName), Collections.singletonMap("key", key));
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error creating/updating key {} in SSH backend {}.", keyName, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to create/update key {} in SSH backend {}.", keyName, this.name, e);
		}

		return false;
	}

	public boolean createRole(String roleName, Role role) {
		try {
			post(this.getSshRolesApiPath(roleName), role);
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error creating role {} in SSH backend {}.", roleName, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to create role {} in SSH backend {}.", roleName, this.name, e);
		}

		return false;
	}

	public boolean deleteCaInformation() {
		try {
			delete(this.getSshCaApiPath());
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error deleting CA information in SSH backend {}.", this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to delete CA information in SSH backend {}.", this.name, e);
		}

		return false;
	}

	public boolean deleteKey(String keyName) {
		try {
			delete(this.getSshKeysApiPath(keyName));
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error deleting key {} in SSH backend {}.", keyName, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to delete key {} in SSH backend {}.", keyName, this.name, e);
		}

		return false;
	}

	public boolean deleteRole(String roleName) {
		try {
			delete(this.getSshRolesApiPath(roleName));
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error deleting role {} in SSH backend {}.", roleName, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to delete role {} in SSH backend {}.", roleName, this.name, e);
		}

		return false;
	}

	public boolean deleteZeroAddressRoles() {
		try {
			delete(this.getSshZeroaddressApiPath());
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error deleting zero-address roles in SSH backend {}.", this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to delete zero-address roles in SSH backend {}.", this.name, e);
		}

		return false;
	}

	public Role generateCredentials(String roleName, Credential credential) {
		try {
			return post(this.getSshCredsApiPath(roleName), credential, RoleHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			this.log.error("Error generating credentials against role {} in SSH backend {}.", roleName, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to generate credentials against role {} in SSH backend {}.", roleName, this.name, e);
		}

		return null;
	}

	public Role generateCredentials(String roleName, String ip, String username) {
		return generateCredentials(roleName, new Credential(ip, username));
	}

	public Roles listRoles() {
		try {
			return get(this.getSshRolesApiPath(""), Collections.singletonMap("list", "true"), RolesHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			this.log.error("Error listing roles in SSH backend {}.", this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to list roles in SSH backed {}.", this.name, e);
		}

		return null;
	}

	public RoleList listRolesByIp(String ip) {
		try {
			return post(this.getSshLookupApiPath(), Collections.singletonMap("ip", ip), RoleListHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			this.log.error("Error listing roles by IP {} in SSH backend {}.", ip, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to list roles by IP {} in SSH backend {}.", ip, this.name, e);
		}

		return new RoleList();
	}

	public RoleList listZeroAddressRoles() {
		try {
			return get(this.getSshZeroaddressApiPath(), RoleListHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			this.log.error("Error listing zero-address roles in SSH backend {}.", this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to list zero-address roles in SSH backend {}.", this.name, e);
		}

		return new RoleList();
	}

	public Role readRole(String roleName) {
		try {
			return get(this.getSshRolesApiPath(roleName), RoleHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			this.log.error("Error reading role {} in SSH backend {}.", roleName, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to read role {} in SSH backend {}.", roleName, this.name, e);
		}

		return null;
	}

	public String readPublicKey() {
		return readPublicKey(false);
	}

	public String readPublicKey(boolean authenticated) {
		try {
			if (authenticated) {
				Map<String, String> responseMap = get(this.getSshCaApiPath(), StringMapHttpResponse.class).getBody().getData();
				if (responseMap != null) {
					return responseMap.get("public_key");
				}

				return "";
			}

			return get(this.getSshPublicKeyApiPath()).getBody();
		} catch (VaultApiException e) {
			this.log.error("Error reading public key from SSH backend {}.", this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to read public key from SSH backend {}.", this.name, e);
		}

		return "";
	}

	public Key signKey(String keyName, KeySign keySign) {
		try {
			return post(this.getSshSignKeyApiPath(keyName), keySign, KeyHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			this.log.error("Error signing key {} in SSH backend {}.", keyName, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to sign key {} in SSH backend {}.", keyName, this.name, e);
		}

		return null;
	}

	public String submitCaInformation(boolean generateSigningKey, String privateKey, String publicKey) {
		Map<String, Object> payload = new HashMap<>();
		payload.put("generate_signing_key", Boolean.valueOf(generateSigningKey));

		try {
			if (generateSigningKey) {
				Map<String, String> responseMap = post(this.getSshCaApiPath(), payload, StringMapHttpResponse.class).getBody().getData();
				if (responseMap != null) {
					return responseMap.get("public_key");
				}
			} else {
				if (privateKey == null) {
					throw new NullPointerException("Private key must not be null if 'Generate Signing Key' is false.");
				}

				if (publicKey == null) {
					throw new NullPointerException("Public key must not be null if 'Generate Signing Key' is false.");
				}

				payload.put("private_key", privateKey);
				payload.put("public_key", publicKey);
				post(this.getSshCaApiPath(), payload);
				return publicKey;
			}
		} catch (VaultApiException e) {
			this.log.error("Error generating signing key in SSH backend {}.", this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to generate signing key in SSH backend {}.", this.name, e);
		}

		return null;
	}

	@Override
	public String toString() {
		return "SSH [accessor=" + this.accessor + ", config=" + this.config + ", description=" + this.description + ", local=" + this.local + ", name=" + this.name + ", restClient=" + this.restClient + ", sealWrap=" + this.sealWrap + ", type=" + this.type + ", vault=" + this.vault + "]";
	}

	public boolean updateKey(String keyName, String key) {
		return createKey(keyName, key);
	}

	public Credential verifyOTP(String otp) {
		try {
			return post(this.getSshVerifyApiPath(), Collections.singletonMap("otp", otp), CredentialHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			this.log.error("Error verifying OTP {} in SSH backend {}.", otp, this.name, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to verify OTP {} in SSH backend {}.", otp, this.name, e);
		}

		return null;
	}

	private String getSshCaApiPath() {
		return this.sshCaApiPath;
	}

	private String getSshCredsApiPath(String roleName) {
		return String.format(this.sshCredsApiPath, roleName);
	}

	private String getSshKeysApiPath(String keyName) {
		return String.format(this.sshKeysApiPath, keyName);
	}

	private String getSshLookupApiPath() {
		return this.sshLookupApiPath;
	}

	private String getSshPublicKeyApiPath() {
		return this.sshPublicKeyApiPath;
	}

	private String getSshRolesApiPath(String roleName) {
		return String.format(this.sshRolesApiPath, roleName);
	}

	private String getSshSignKeyApiPath(String keyName) {
		return String.format(this.sshSignKeyApiPath, keyName);
	}

	private String getSshVerifyApiPath() {
		return this.sshVerifyApiPath;
	}

	private String getSshZeroaddressApiPath() {
		return this.sshZeroaddressApiPath;
	}
}