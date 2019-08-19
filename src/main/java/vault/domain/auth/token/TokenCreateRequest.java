package vault.domain.auth.token;

import static vault.util.Util.copyList;
import static vault.util.Util.copyMap;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class TokenCreateRequest implements Serializable {
	private static final long serialVersionUID = 1177937995252227138L;

	@JsonProperty("display_name")
	private String displayName = "token";

	@JsonProperty("explicit_max_ttl")
	private String explicitMaxTtl;

	@JsonProperty("id")
	private String id;

	@Deprecated
	@JsonProperty("lease")
	private String lease;

	@JsonProperty("meta")
	private Map<String, String> meta;

	@JsonProperty("no_default_policy")
	private boolean noDefaultPolicy;

	@JsonProperty("no_parent")
	private boolean noParent;

	@JsonProperty("num_uses")
	private int numUses;

	@JsonProperty("period")
	private String period;

	@JsonProperty("policies")
	private List<String> policies;

	@JsonProperty("renewable")
	private boolean renewable = true;

	@JsonProperty("role_name")
	private String roleName;

	@JsonProperty("ttl")
	private String ttl;

	public TokenCreateRequest() {
		super();
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getExplicitMaxTtl() {
		return this.explicitMaxTtl;
	}

	public void setExplicitMaxTtl(String explicitMaxTtl) {
		this.explicitMaxTtl = explicitMaxTtl;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLease() {
		return this.lease;
	}

	public void setLease(String lease) {
		this.lease = lease;
	}

	public Map<String, String> getMeta() {
		return this.meta;
	}

	public void setMeta(Map<String, String> meta) {
		this.meta = copyMap(meta);
	}

	public boolean isNoDefaultPolicy() {
		return this.noDefaultPolicy;
	}

	public void setNoDefaultPolicy(boolean noDefaultPolicy) {
		this.noDefaultPolicy = noDefaultPolicy;
	}

	public boolean isNoParent() {
		return this.noParent;
	}

	public void setNoParent(boolean noParent) {
		this.noParent = noParent;
	}

	public int getNumUses() {
		return this.numUses;
	}

	public void setNumUses(int numUses) {
		this.numUses = numUses;
	}

	public String getPeriod() {
		return this.period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public List<String> getPolicies() {
		return this.policies;
	}

	public void setPolicies(List<String> policies) {
		this.policies = copyList(policies);
	}

	public boolean isRenewable() {
		return this.renewable;
	}

	public void setRenewable(boolean renewable) {
		this.renewable = renewable;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getTtl() {
		return this.ttl;
	}

	public void setTtl(String ttl) {
		this.ttl = ttl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.displayName, this.explicitMaxTtl, this.id, this.lease, this.meta, Boolean.valueOf(this.noDefaultPolicy), Boolean.valueOf(this.noParent), Integer.valueOf(this.numUses), this.period, this.policies, Boolean.valueOf(this.renewable), this.roleName, this.ttl);
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
		TokenCreateRequest other = (TokenCreateRequest) obj;
		return Objects.equals(this.displayName, other.displayName) && Objects.equals(this.explicitMaxTtl, other.explicitMaxTtl)
				&& Objects.equals(this.id, other.id) && Objects.equals(this.lease, other.lease)
				&& Objects.equals(this.meta, other.meta) && this.noDefaultPolicy == other.noDefaultPolicy
				&& this.noParent == other.noParent && this.numUses == other.numUses
				&& Objects.equals(this.period, other.period) && Objects.equals(this.policies, other.policies)
				&& this.renewable == other.renewable && Objects.equals(this.roleName, other.roleName)
				&& Objects.equals(this.ttl, other.ttl);
	}

	@Override
	public String toString() {
		return "CreateToken [displayName=" + this.displayName + ", explicitMaxTtl=" + this.explicitMaxTtl + ", id="
				+ this.id + ", lease=" + this.lease + ", meta=" + this.meta + ", noDefaultPolicy="
				+ this.noDefaultPolicy + ", noParent=" + this.noParent + ", numUses=" + this.numUses + ", period="
				+ this.period + ", policies=" + this.policies + ", renewable=" + this.renewable + ", roleName="
				+ this.roleName + ", ttl=" + this.ttl + "]";
	}
}