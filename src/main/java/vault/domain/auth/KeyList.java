package vault.domain.auth;

import static vault.util.Util.copyList;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyList implements Serializable {
	private static final long serialVersionUID = -8132709681241284504L;

	@JsonProperty("keys")
	private List<String> keys;

	public KeyList() {
		super();
	}

	public List<String> getKeys() {
		return this.keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = copyList(keys);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.keys);
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
		KeyList other = (KeyList) obj;
		return Objects.equals(this.keys, other.keys);
	}


	@Override
	public String toString() {
		return "TokenKeyList [keys=" + this.keys + "]";
	}
}