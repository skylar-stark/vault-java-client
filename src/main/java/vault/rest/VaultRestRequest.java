package vault.rest;

import java.util.Map;
import java.util.Objects;

public class VaultRestRequest {

	private String path;
	private Map<String, String> queryParameters;
	private Object payload;
	private boolean requireSuccessfulStatusCode;

	public VaultRestRequest(String path, Map<String, String> queryParameters, Object payload, boolean requireSuccessfulStatusCode) {
		this.path = path;
		this.queryParameters = queryParameters;
		this.payload = payload;
		this.requireSuccessfulStatusCode = requireSuccessfulStatusCode;
	}

	public VaultRestRequest(String path, Map<String, String> queryParameters, Object payload) {
		this(path, queryParameters, payload, true);
	}

	public VaultRestRequest(String path, Map<String, String> queryParameters) {
		this(path, queryParameters, null);
	}

	public VaultRestRequest(String path, Object payload) {
		this(path, null, payload);
	}

	public VaultRestRequest(String path) {
		this(path, null, null);
	}

	public String getPath() {
		return this.path;
	}

	public Map<String, String> getQueryParameters() {
		return this.queryParameters;
	}

	public Object getPayload() {
		return this.payload;
	}

	public boolean isRequireSuccessfulStatusCode() {
		return this.requireSuccessfulStatusCode;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.path, this.payload, this.queryParameters, Boolean.valueOf(this.requireSuccessfulStatusCode));
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
		VaultRestRequest other = (VaultRestRequest) obj;
		return Objects.equals(this.path, other.path) && Objects.equals(this.payload, other.payload) && Objects.equals(this.queryParameters, other.queryParameters) && this.requireSuccessfulStatusCode == other.requireSuccessfulStatusCode;
	}

	@Override
	public String toString() {
		return "VaultRestRequest [path=" + this.path + ", queryParameters=" + this.queryParameters + ", payload=" + this.payload + ", requireSuccessfulStatusCode=" + this.requireSuccessfulStatusCode + "]";
	}
}