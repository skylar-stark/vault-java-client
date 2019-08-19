package vault.domain.ssh;

import static vault.util.Util.checkAllowedValue;
import static vault.util.Util.copyMap;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class KeySign implements Serializable {
	private static final long serialVersionUID = -2146425289280747141L;

	@JsonProperty("cert_type")
	private String certType = "user";

	@JsonProperty("critical_options")
	private Map<String, String> criticalOptions;

	@JsonProperty("extension")
	private Map<String, String> extension;

	@JsonProperty("key_id")
	private String keyId;

	@JsonProperty("public_key")
	private String publicKey;

	@JsonProperty("ttl")
	private String ttl;

	@JsonProperty("valid_principals")
	private String validPrincipals;

	public KeySign() {
		super();
	}

	public String getCertType() {
		return this.certType;
	}

	public void setCertType(String certType) {
		this.certType = checkAllowedValue("cert_type", certType == null ? null : certType.toLowerCase(Locale.ENGLISH), "host", "user");
	}

	public Map<String, String> getCriticalOptions() {
		return this.criticalOptions;
	}

	public void setCriticalOptions(Map<String, String> criticalOptions) {
		this.criticalOptions = copyMap(criticalOptions);
	}

	public Map<String, String> getExtension() {
		return this.extension;
	}

	public void setExtension(Map<String, String> extension) {
		this.extension = copyMap(extension);
	}

	public String getKeyId() {
		return this.keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getPublicKey() {
		return this.publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getTtl() {
		return this.ttl;
	}

	public void setTtl(String ttl) {
		this.ttl = ttl;
	}

	public String getValidPrincipals() {
		return this.validPrincipals;
	}

	public void setValidPrincipals(String validPrincipals) {
		this.validPrincipals = validPrincipals;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.certType, this.criticalOptions, this.extension, this.keyId, this.publicKey, this.ttl, this.validPrincipals);
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
		KeySign other = (KeySign) obj;
		return Objects.equals(this.certType, other.certType) && Objects.equals(this.criticalOptions, other.criticalOptions)
				&& Objects.equals(this.extension, other.extension) && Objects.equals(this.keyId, other.keyId)
				&& Objects.equals(this.publicKey, other.publicKey) && Objects.equals(this.ttl, other.ttl)
				&& Objects.equals(this.validPrincipals, other.validPrincipals);
	}

	@Override
	public String toString() {
		return "KeySign [certType=" + this.certType + ", criticalOptions=" + this.criticalOptions + ", extension="
				+ this.extension + ", keyId=" + this.keyId + ", publicKey=" + this.publicKey + ", ttl=" + this.ttl
				+ ", validPrincipals=" + this.validPrincipals + "]";
	}
}