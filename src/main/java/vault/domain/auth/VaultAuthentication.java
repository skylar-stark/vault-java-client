package vault.domain.auth;

import static vault.util.Util.copyList;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VaultAuthentication implements Serializable {
	private static final long serialVersionUID = -5437316928341999793L;

	@JsonProperty("token_ttl")
	protected long tokenTtl;

	@JsonProperty("token_max_ttl")
	protected long tokenMaxTtl;

	@JsonProperty("token_policies")
	protected List<String> tokenPolicies;

	@JsonProperty("token_bound_cidrs")
	protected List<String> tokenBoundCidrs;

	@JsonProperty("token_explicit_max_ttl")
	protected long tokenExplicitMaxTtl;

	@JsonProperty("token_no_default_policy")
	protected boolean tokenNoDefaultPolicy;

	@JsonProperty("token_num_uses")
	protected int tokenNumUses;

	@JsonProperty("token_period")
	protected String tokenPeriod;

	@JsonProperty("token_type")
	protected String tokenType;

	public VaultAuthentication() {
		super();
	}

	public long getTokenTtl() {
		return this.tokenTtl;
	}

	public void setTokenTtl(long tokenTtl) {
		this.tokenTtl = tokenTtl;
	}

	public long getTokenMaxTtl() {
		return this.tokenMaxTtl;
	}

	public void setTokenMaxTtl(long tokenMaxTtl) {
		this.tokenMaxTtl = tokenMaxTtl;
	}

	public List<String> getTokenPolicies() {
		return this.tokenPolicies;
	}

	public void setTokenPolicies(List<String> tokenPolicies) {
		this.tokenPolicies = copyList(tokenPolicies);
	}

	public List<String> getTokenBoundCidrs() {
		return this.tokenBoundCidrs;
	}

	public void setTokenBoundCidrs(List<String> tokenBoundCidrs) {
		this.tokenBoundCidrs = copyList(tokenBoundCidrs);
	}

	public long getTokenExplicitMaxTtl() {
		return this.tokenExplicitMaxTtl;
	}

	public void setTokenExplicitMaxTtl(long tokenExplicitMaxTtl) {
		this.tokenExplicitMaxTtl = tokenExplicitMaxTtl;
	}

	public boolean isTokenNoDefaultPolicy() {
		return this.tokenNoDefaultPolicy;
	}

	public void setTokenNoDefaultPolicy(boolean tokenNoDefaultPolicy) {
		this.tokenNoDefaultPolicy = tokenNoDefaultPolicy;
	}

	public int getTokenNumUses() {
		return this.tokenNumUses;
	}

	public void setTokenNumUses(int tokenNumUses) {
		this.tokenNumUses = tokenNumUses;
	}

	public String getTokenPeriod() {
		return this.tokenPeriod;
	}

	public void setTokenPeriod(String tokenPeriod) {
		this.tokenPeriod = tokenPeriod;
	}

	public String getTokenType() {
		return this.tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.tokenBoundCidrs, Long.valueOf(this.tokenExplicitMaxTtl), Long.valueOf(this.tokenMaxTtl), Boolean.valueOf(this.tokenNoDefaultPolicy), Integer.valueOf(this.tokenNumUses), this.tokenPeriod, this.tokenPolicies, Long.valueOf(this.tokenTtl), this.tokenType);
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
		VaultAuthentication other = (VaultAuthentication) obj;
		return Objects.equals(this.tokenBoundCidrs, other.tokenBoundCidrs) && this.tokenExplicitMaxTtl == other.tokenExplicitMaxTtl && this.tokenMaxTtl == other.tokenMaxTtl && this.tokenNoDefaultPolicy == other.tokenNoDefaultPolicy && this.tokenNumUses == other.tokenNumUses && Objects.equals(this.tokenPeriod, other.tokenPeriod) && Objects.equals(this.tokenPolicies, other.tokenPolicies) && this.tokenTtl == other.tokenTtl && Objects.equals(this.tokenType, other.tokenType);
	}

	@Override
	public String toString() {
		return "VaultAuthentication [tokenTtl=" + this.tokenTtl + ", tokenMaxTtl=" + this.tokenMaxTtl + ", tokenPolicies=" + this.tokenPolicies + ", tokenBoundCidrs=" + this.tokenBoundCidrs + ", tokenExplicitMaxTtl=" + this.tokenExplicitMaxTtl + ", tokenNoDefaultPolicy=" + this.tokenNoDefaultPolicy + ", tokenNumUses=" + this.tokenNumUses + ", tokenPeriod=" + this.tokenPeriod + ", tokenType=" + this.tokenType + "]";
	}
}