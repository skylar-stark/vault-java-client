package vault.backend;

import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vault.Vault;
import vault.exception.VaultApiException;
import vault.rest.VaultOkHttp3RestClient;
import vault.rest.VaultRestClient;
import vault.rest.VaultRestRequest;
import vault.rest.VaultRestResponse;

public abstract class VaultBackend {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	protected String accessor;
	protected BackendConfig config;
	protected String description;
	protected boolean local;
	protected String name;
	protected VaultRestClient restClient;
	protected boolean sealWrap;
	protected Backends type;
	protected Vault vault;

	protected VaultBackend(String accessor, BackendConfig config, String description, boolean local, String name, boolean sealWrap, Backends type, Vault vault) {
		this.accessor = accessor;
		this.config = config;
		this.description = description;
		this.local = local;
		this.name = name;
		this.sealWrap = sealWrap;
		this.type = type;

		this.restClient = new VaultOkHttp3RestClient(vault);
	}

	public String getAccessor() {
		return this.accessor;
	}

	public void setAccessor(String accessor) {
		this.accessor = accessor;
	}

	public BackendConfig getConfig() {
		return this.config;
	}

	public void setConfig(BackendConfig config) {
		this.config = config;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isLocal() {
		return this.local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSealWrap() {
		return this.sealWrap;
	}

	public void setSealWrap(boolean sealWrap) {
		this.sealWrap = sealWrap;
	}

	public Backends getType() {
		return this.type;
	}

	public void setType(Backends type) {
		this.type = type;
	}

	public Vault getVault() {
		return this.vault;
	}

	public void setVault(Vault vault) {
		this.vault = vault;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		VaultBackend other = (VaultBackend) obj;
		return Objects.equals(this.accessor, other.accessor) && Objects.equals(this.config, other.config) && Objects.equals(this.description, other.description) && this.local == other.local && Objects.equals(this.name, other.name) && this.sealWrap == other.sealWrap && Objects.equals(this.vault, other.vault);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.accessor, this.config, this.description, Boolean.valueOf(this.local), this.name, Boolean.valueOf(this.sealWrap), this.vault);
	}

	protected VaultRestResponse<String> delete(String path) throws VaultApiException {
		return delete(path, null);
	}

	protected VaultRestResponse<String> delete(String path, Object payload) throws VaultApiException {
		return this.restClient.delete(new VaultRestRequest(path, null, payload, true));
	}

	protected VaultRestResponse<String> get(String path) throws VaultApiException {
		return get(path, null, true);
	}

	protected <T> VaultRestResponse<T> get(String path, Class<T> clazz) throws VaultApiException {
		return get(path, null, true, clazz);
	}

	protected VaultRestResponse<String> get(String path, Map<String, String> queryParameters) throws VaultApiException {
		return get(path, queryParameters, true);
	}

	protected VaultRestResponse<String> get(String path, Map<String, String> queryParameters, boolean onlyOnSuccess) throws VaultApiException {
		return this.restClient.get(new VaultRestRequest(path, queryParameters, null, onlyOnSuccess));
	}

	protected <T> VaultRestResponse<T> get(String path, Map<String, String> queryParameters, boolean onlyOnSuccess, Class<T> clazz) throws VaultApiException {
		return this.restClient.get(new VaultRestRequest(path, queryParameters, null, onlyOnSuccess), clazz);
	}

	protected <T> VaultRestResponse<T> get(String path, Map<String, String> queryParameters, Class<T> clazz) throws VaultApiException {
		return get(path, queryParameters, true, clazz);
	}

	protected VaultRestResponse<String> head(String path) throws VaultApiException {
		return head(path, null);
	}

	protected VaultRestResponse<String> head(String path, Map<String, String> queryParameters) throws VaultApiException {
		return this.restClient.head(new VaultRestRequest(path, queryParameters, null, true));
	}

	protected VaultRestResponse<String> patch(String path, Object payload) throws VaultApiException {
		return this.restClient.patch(new VaultRestRequest(path, null, payload, true));
	}

	protected <T> VaultRestResponse<T> patch(String path, Object payload, Class<T> clazz) throws VaultApiException {
		return this.restClient.patch(new VaultRestRequest(path, null, payload, true), clazz);
	}

	protected VaultRestResponse<String> post(String path, Object payload) throws VaultApiException {
		return this.restClient.post(new VaultRestRequest(path, null, payload, true));
	}

	protected <T> VaultRestResponse<T> post(String path, Object payload, Class<T> clazz) throws VaultApiException {
		return this.restClient.post(new VaultRestRequest(path, null, payload, true), clazz);
	}

	protected VaultRestResponse<String> put(String path, Object payload) throws VaultApiException {
		return this.restClient.put(new VaultRestRequest(path, null, payload, true));
	}

	protected <T> VaultRestResponse<T> put(String path, Object payload, Class<T> clazz) throws VaultApiException {
		return this.restClient.put(new VaultRestRequest(path, null, payload, true), clazz);
	}
}