package vault.domain.ssh;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Key implements Serializable {
	private static final long serialVersionUID = -1515669155118571236L;

	@JsonProperty("serial_number")
	private String serialNumber;

	@JsonProperty("signed_key")
	private String signedKey;

	public Key() {
		super();
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSignedKey() {
		return this.signedKey;
	}

	public void setSignedKey(String signedKey) {
		this.signedKey = signedKey;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.serialNumber, this.signedKey);
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
		Key other = (Key) obj;
		return Objects.equals(this.serialNumber, other.serialNumber) && Objects.equals(this.signedKey, other.signedKey);
	}

	@Override
	public String toString() {
		return "Key [serialNumber=" + this.serialNumber + ", signedKey=" + this.signedKey + "]";
	}
}