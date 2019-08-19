package vault.domain.ssh;

import static vault.util.Util.copyList;
import static vault.util.Util.copyMap;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Roles implements Serializable {
	private static final long serialVersionUID = 6257363947232467187L;

	@JsonProperty("key_info")
	private Map<String, Role> keyInfo;

	@JsonProperty("keys")
	private List<String> keys;

	public Roles() {
		super();
	}

	public Map<String, Role> getKeyInfo() {
		return this.keyInfo;
	}

	public void setKeyInfo(Map<String, Role> keyInfo) {
		this.keyInfo = copyMap(keyInfo);
	}

	public List<String> getKeys() {
		return this.keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = copyList(keys);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.keyInfo, this.keys);
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
		Roles other = (Roles) obj;
		return Objects.equals(this.keyInfo, other.keyInfo) && Objects.equals(this.keys, other.keys);
	}

	@Override
	public String toString() {
		return "RolesList [keyInfo=" + this.keyInfo + ", keys=" + this.keys + "]";
	}
}