package vault.domain.auth.userpass;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import vault.domain.auth.VaultAuthentication;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserpassCreate extends VaultAuthentication {
	private static final long serialVersionUID = 3916272854480191723L;

	@JsonProperty("password")
	private String password;

	public UserpassCreate() {
		super();
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.password);
		return result;
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
		UserpassCreate other = (UserpassCreate) obj;
		return Objects.equals(this.password, other.password);
	}

	@Override
	public String toString() {
		return "UserpassCreate [password=" + this.password + ", tokenTtl=" + this.tokenTtl + ", tokenMaxTtl=" + this.tokenMaxTtl + ", tokenPolicies=" + this.tokenPolicies + ", tokenBoundCidrs=" + this.tokenBoundCidrs + ", tokenExplicitMaxTtl=" + this.tokenExplicitMaxTtl + ", tokenNoDefaultPolicy=" + this.tokenNoDefaultPolicy + ", tokenNumUses=" + this.tokenNumUses + ", tokenPeriod=" + this.tokenPeriod + ", tokenType=" + this.tokenType + "]";
	}
}