package vault.domain.response;

import static vault.util.Util.copyList;
import static vault.util.Util.copyMap;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class HttpAuth implements Serializable {
	private static final long serialVersionUID = -1499210388745995398L;

	@JsonProperty("accessor")
	private String accessor;

	@JsonProperty("client_token")
	private String clientToken;

	@JsonProperty("entity_id")
	private String entityId;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("identity_policies")
	private List<String> identityPolicies;

	@JsonProperty("lease_duration")
	private long leaseDuration;

	@JsonProperty("metadata")
	private Map<String, String> metadata;

	@JsonProperty("policies")
	private List<String> policies;

	@JsonProperty("renewable")
	private boolean renewable;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("token_policies")
	private List<String> tokenPolicies;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonCreator
	public HttpAuth(@JsonProperty("accessor") String accessor, @JsonProperty("client_token") String clientToken, @JsonProperty("entity_id") String entityId, @JsonProperty("identity_policies") List<String> identityPolicies, @JsonProperty("lease_duration") long leaseDuration, @JsonProperty("metadata") Map<String, String> metadata, @JsonProperty("policies") List<String> policies, @JsonProperty("renewable") boolean renewable, @JsonProperty("token_policies") List<String> tokenPolicies, @JsonProperty("token_type") String tokenType) {
		this.accessor = accessor;
		this.clientToken = clientToken;
		this.entityId = entityId;
		this.identityPolicies = copyList(identityPolicies);
		this.leaseDuration = leaseDuration;
		this.metadata = copyMap(metadata);
		this.policies = copyList(policies);
		this.renewable = renewable;
		this.tokenPolicies = copyList(tokenPolicies);
		this.tokenType = tokenType;
	}

	public String getAccessor() {
		return this.accessor;
	}

	public String getClientToken() {
		return this.clientToken;
	}

	public String getEntityId() {
		return this.entityId;
	}

	public List<String> getIdentityPolicies() {
		return this.identityPolicies;
	}

	public long getLeaseDuration() {
		return this.leaseDuration;
	}

	public Map<String, String> getMetadata() {
		return this.metadata;
	}

	public List<String> getPolicies() {
		return this.policies;
	}

	public boolean isRenewable() {
		return this.renewable;
	}

	public List<String> getTokenPolicies() {
		return this.tokenPolicies;
	}

	public String getTokenType() {
		return this.tokenType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.accessor, this.clientToken, this.entityId, this.identityPolicies, Long.valueOf(this.leaseDuration), this.metadata, this.policies, Boolean.valueOf(this.renewable), this.tokenPolicies, this.tokenType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HttpAuth)) {
			return false;
		}
		HttpAuth other = (HttpAuth) obj;
		return Objects.equals(this.accessor, other.accessor) && Objects.equals(this.clientToken, other.clientToken)
				&& Objects.equals(this.entityId, other.entityId) && Objects.equals(this.identityPolicies, other.identityPolicies)
				&& this.leaseDuration == other.leaseDuration && Objects.equals(this.metadata, other.metadata)
				&& Objects.equals(this.policies, other.policies) && this.renewable == other.renewable
				&& Objects.equals(this.tokenPolicies, other.tokenPolicies) && Objects.equals(this.tokenType, other.tokenType);
	}

	@Override
	public String toString() {
		return "HttpAuth [accessor=" + this.accessor + ", clientToken=" + this.clientToken + ", entityId=" + this.entityId
				+ ", identityPolicies=" + this.identityPolicies + ", leaseDuration=" + this.leaseDuration + ", metadata="
				+ this.metadata + ", policies=" + this.policies + ", renewable=" + this.renewable + ", tokenPolicies=" + this.tokenPolicies
				+ ", tokenType=" + this.tokenType + "]";
	}
}