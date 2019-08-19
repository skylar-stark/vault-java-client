package vault.domain.ssh;

import static vault.util.Util.copyList;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class RoleList implements Serializable {
	private static final long serialVersionUID = 4462348382753157998L;

	@JsonProperty("roles")
	private List<String> roles;

	public RoleList() {
		super();
	}

	public RoleList(List<String> roles) {
		this.roles = copyList(roles);
	}

	public List<String> getRoles() {
		return this.roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = copyList(roles);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.roles);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof RoleList)) {
			return false;
		}
		RoleList other = (RoleList) obj;
		return Objects.equals(this.roles, other.roles);
	}

	@Override
	public String toString() {
		return "ZeroAddressRoles [roles=" + this.roles + "]";
	}
}