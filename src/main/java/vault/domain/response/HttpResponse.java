package vault.domain.response;

import static vault.util.Util.copyList;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import vault.domain.KeyList;
import vault.domain.auth.VaultAuthentication;
import vault.domain.auth.token.TokenLookupResponse;
import vault.domain.kv.Metadata;
import vault.domain.kv.SecretMetadata;
import vault.domain.kv.V2Response;
import vault.domain.ssh.Credential;
import vault.domain.ssh.Key;
import vault.domain.ssh.Role;
import vault.domain.ssh.Roles;
import vault.domain.ssh.RoleList;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class HttpResponse<T> implements Serializable {
	private static final long serialVersionUID = -662868336457859523L;

	@JsonProperty("auth")
	private HttpAuth auth;

	@JsonProperty("data")
	private T data;

	@JsonProperty("lease_duration")
	private long leaseDuration;

	@JsonProperty("lease_id")
	private String leaseId;

	@JsonProperty("renewable")
	private boolean renewable;

	@JsonProperty("request_id")
	private String requestId;

	@JsonProperty("warnings")
	private List<String> warnings;

	@JsonProperty("wrap_info")
	private HttpWrapInfo wrapInfo;

	@JsonIgnore
	protected HttpResponse() {
		super();
	}

	@JsonCreator
	public HttpResponse(@JsonProperty("auth") HttpAuth auth, @JsonProperty("data") T data, @JsonProperty("lease_duration") long leaseDuration, String leaseId, @JsonProperty("renewable") boolean renewable, @JsonProperty("request_id") String requestId, @JsonProperty("warnings") List<String> warnings, @JsonProperty("wrap_info") HttpWrapInfo wrapInfo) {
		this.auth = auth;
		this.data = data;
		this.leaseDuration = leaseDuration;
		this.leaseId = leaseId;
		this.renewable = renewable;
		this.requestId = requestId;
		this.warnings = copyList(warnings);
		this.wrapInfo = wrapInfo;
	}

	public HttpAuth getAuth() {
		return this.auth;
	}

	public T getData() {
		return this.data;
	}

	public long getLeaseDuration() {
		return this.leaseDuration;
	}

	public String getLeaseId() {
		return this.leaseId;
	}

	public boolean isRenewable() {
		return this.renewable;
	}

	public String getRequestId() {
		return this.requestId;
	}

	public List<String> getWarnings() {
		return this.warnings;
	}

	public HttpWrapInfo getWrapInfo() {
		return this.wrapInfo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.auth, this.data, Long.valueOf(this.leaseDuration), this.leaseId, Boolean.valueOf(this.renewable), this.requestId, this.warnings, this.wrapInfo);
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
		HttpResponse<?> other = (HttpResponse<?>) obj;
		return Objects.equals(this.auth, other.auth) && Objects.equals(this.data, other.data)
				&& this.leaseDuration == other.leaseDuration && Objects.equals(this.leaseId, other.leaseId)
				&& this.renewable == other.renewable && Objects.equals(this.requestId, other.requestId)
				&& Objects.equals(this.warnings, other.warnings) && Objects.equals(this.wrapInfo, other.wrapInfo);
	}

	@Override
	public String toString() {
		return "HttpResponse [auth=" + this.auth + ", data=" + this.data + ", leaseDuration=" + this.leaseDuration
				+ ", leaseId=" + this.leaseId + ", renewable=" + this.renewable + ", requestId=" + this.requestId
				+ ", warnings=" + this.warnings + ", wrapInfo=" + this.wrapInfo + "]";
	}

	public static final class CredentialHttpResponse extends HttpResponse<Credential> {
		private static final long serialVersionUID = 4558531492104885999L;
	}

	public static final class KeyHttpResponse extends HttpResponse<Key> {
		private static final long serialVersionUID = -520565096901127714L;
	}

	public static final class KeyListHttpResponse extends HttpResponse<KeyList> {
		private static final long serialVersionUID = -7931559482015221173L;
	}

	public static final class KvV2HttpResponse extends HttpResponse<V2Response> {
		private static final long serialVersionUID = -8786210996376002666L;
	}

	public static final class MountHttpResponse extends HttpResponse<Map<String, Map<String, MountOutput>>> {
		private static final long serialVersionUID = -7575010113736184280L;
	}

	public static final class MetadataHttpResponse extends HttpResponse<Metadata> {
		private static final long serialVersionUID = 3583640597017345121L;
	}

	public static final class GenericHttpResponse extends HttpResponse<Object> {
		private static final long serialVersionUID = 1255302066360280962L;
	}

	public static final class RoleHttpResponse extends HttpResponse<Role> {
		private static final long serialVersionUID = -173069556674012809L;
	}

	public static final class RoleListHttpResponse extends HttpResponse<RoleList> {
		private static final long serialVersionUID = 2703425538832638612L;
	}

	public static final class RolesHttpResponse extends HttpResponse<Roles> {
		private static final long serialVersionUID = 8830559114410982488L;
	}

	public static final class SecretMetadataHttpResponse extends HttpResponse<SecretMetadata> {
		private static final long serialVersionUID = -358744517454414719L;
	}

	public static final class StringMapHttpResponse extends HttpResponse<Map<String, String>> {
		private static final long serialVersionUID = 1320358832831994708L;
	}

	public static final class TokenLookupResponseHttpResponse extends HttpResponse<TokenLookupResponse> {
		private static final long serialVersionUID = 7280276563208787979L;
	}

	public static final class VaultAuthenticationHttpResponse extends HttpResponse<VaultAuthentication> {
		private static final long serialVersionUID = -2813729618065198179L;
	}
}