package vault.backend.secret;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vault.Vault;
import vault.backend.BackendConfig;
import vault.backend.Backends;
import vault.backend.VaultBackend;
import vault.domain.response.HealthResponse;
import vault.domain.response.MountOutput;
import vault.domain.response.HttpResponse.MountHttpResponse;
import vault.exception.VaultApiException;
import vault.exception.VaultAuthorizationException;
import vault.exception.VaultException;
import vault.exception.VaultHealthException;
import vault.rest.VaultOkHttp3RestClient;
import vault.rest.VaultRestClient;
import vault.rest.VaultRestRequest;
import vault.rest.VaultRestResponse;

public final class System extends VaultBackend {
	private static final String SYSTEM_HEALTH_API_PATH = "/v1/sys/health";
	private static final String SYSTEM_INTERNAL_UI_MOUNTS_API_PATH = "/v1/sys/internal/ui/mounts";

	public System(String accessor, BackendConfig config, String description, boolean local, String name, boolean sealWrap, Vault vault) {
		super(accessor, config, description, local, name, sealWrap, Backends.SYSTEM, vault);
	}

	public static System from(MountOutput mountOutput, String name, Vault vault) {
		return new System(mountOutput.getAccessor(), mountOutput.getConfig() != null ? BackendConfig.from(mountOutput.getConfig()) : null, mountOutput.getDescription(), mountOutput.isLocal(), name, mountOutput.isSealWrap(), vault);
	}

	public static HealthResponse health(Vault vault) throws VaultHealthException {
		return health(vault.getVaultAddress());
	}

	public static HealthResponse health(String vaultAddress) throws VaultHealthException {
		Logger log = LoggerFactory.getLogger(System.class);
		log.debug("Vault System backend not initialized yet; calling health check for initialization.");

		VaultRestClient vaultHealthRestClient = new VaultOkHttp3RestClient(vaultAddress, "dummy_token");
		Map<String, String> queryParameters = new HashMap<>();
		queryParameters.put("standbyok", "true");
		queryParameters.put("perfstandbyok", "true");

		try {
			VaultRestResponse<HealthResponse> vaultResponse = vaultHealthRestClient.get(new VaultRestRequest(SYSTEM_HEALTH_API_PATH, queryParameters, null, false), HealthResponse.class);
			if (vaultResponse.getStatusCode() == 200) {
				return vaultResponse.getBody();
			}

			throw new VaultHealthException("Vault health check returned with HTTP Code " + vaultResponse.getStatusCode() + ".");
		} catch (VaultApiException e) {
			throw new VaultException("Error connecting to Vault health at address " + vaultAddress + SYSTEM_HEALTH_API_PATH + ".", e);
		} catch (VaultAuthorizationException e) {
			log.error("Unauthorized access to Vault health check - this message should never be seen.", e);
		}

		return null;
	}

	public static Map<String, Map<String, MountOutput>> internalUiMounts(Vault vault) {
		return internalUiMounts(vault.getVaultAddress(), vault.getVaultToken());
	}

	public static Map<String, Map<String, MountOutput>> internalUiMounts(String vaultAddress, String vaultToken) {
		Logger log = LoggerFactory.getLogger(System.class);
		log.debug("Vault System backend not initialized yet; initializing mounts now.");

		VaultRestClient vaultMountsRestClient = new VaultOkHttp3RestClient(vaultAddress, vaultToken);
		try {
			return vaultMountsRestClient.get(new VaultRestRequest(SYSTEM_INTERNAL_UI_MOUNTS_API_PATH, null, null, true), MountHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			throw new VaultException("Error getting Internal UI Mounts from Vault at address " + vaultAddress + SYSTEM_INTERNAL_UI_MOUNTS_API_PATH + ".", e);
		} catch (VaultAuthorizationException e) {
			throw new VaultException("Token is not authorized to view Internal UI Mounts from Vault at address " + vaultAddress + SYSTEM_INTERNAL_UI_MOUNTS_API_PATH + ".", e);
		}
	}

	public HealthResponse health(boolean standbyOk, boolean perfStandbyOk, Integer activeCode, Integer standbyCode, Integer drStandbyCode, Integer performanceStandbyCode, Integer sealedCode, Integer uninitCode) throws VaultHealthException {
		int defaultActiveCode = activeCode != null ? activeCode.intValue() : 200;
		int defaultStandbyCode = standbyCode != null ? standbyCode.intValue() : 429;
		int defaultDrStandbyCode = drStandbyCode != null ? drStandbyCode.intValue() : 472;
		int defaultPerformanceStandbyCode = performanceStandbyCode != null ? performanceStandbyCode.intValue() : 473;
		int defaultSealedCode = sealedCode != null ? sealedCode.intValue() : 503;
		int defaultUninitCode = uninitCode != null ? uninitCode.intValue() : 501;

		Map<String, String> queryParameters = new HashMap<>();
		if (standbyOk) {
			queryParameters.put("standbyok", "true");
		}
		if (perfStandbyOk) {
			queryParameters.put("perfstandbyok", "true");
		}
		if (activeCode != null) {
			queryParameters.put("activecode", activeCode.toString());
		}
		if (standbyCode != null) {
			queryParameters.put("standbycode", standbyCode.toString());
		}
		if (drStandbyCode != null) {
			queryParameters.put("drstandbycode", drStandbyCode.toString());
		}
		if (performanceStandbyCode != null) {
			queryParameters.put("performancestandbycode", performanceStandbyCode.toString());
		}
		if (sealedCode != null) {
			queryParameters.put("sealedcode", sealedCode.toString());
		}
		if (uninitCode != null) {
			queryParameters.put("uninitcode", uninitCode.toString());
		}

		try {
			VaultRestResponse<HealthResponse> vaultResponse = get(SYSTEM_HEALTH_API_PATH, queryParameters, HealthResponse.class);
			if (vaultResponse != null) {
				if (vaultResponse.getStatusCode() == defaultActiveCode) {
					return vaultResponse.getBody();
				} else if (vaultResponse.getStatusCode() == defaultStandbyCode) {
					throw new VaultHealthException("Vault is in standby mode.");
				} else if (vaultResponse.getStatusCode() == defaultDrStandbyCode) {
					throw new VaultHealthException("Vault is in DR standby mode.");
				} else if (vaultResponse.getStatusCode() == defaultPerformanceStandbyCode) {
					throw new VaultHealthException("Vault is in performance standby mode.");
				} else if (vaultResponse.getStatusCode() == defaultSealedCode) {
					throw new VaultHealthException("Vault is sealed.");
				} else if (vaultResponse.getStatusCode() == defaultUninitCode) {
					throw new VaultHealthException("Vault is uninitialized.");
				} else {
					throw new VaultException("Vault health check returned with unexpected HTTP Code " + vaultResponse.getStatusCode());
				}
			}
		} catch (VaultApiException e) {
			this.log.error("Error getting health for Vault instance {}", this.vault, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Unauthorized access to Vault health check - this message should never be seen.", e);
		}

		return null;
	}

	public Map<String, Map<String, MountOutput>> internalUiMounts() {
		this.log.debug("Vault System backend not initialized yet; initializing mounts now.");
		try {
			return get(SYSTEM_INTERNAL_UI_MOUNTS_API_PATH, MountHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			throw new VaultException("Error getting Internal UI Mounts.", e);
		} catch (VaultAuthorizationException e) {
			throw new VaultException("Token is not authorized to view Internal UI Mounts.", e);
		}
	}
}