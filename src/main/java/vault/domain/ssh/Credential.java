package vault.domain.ssh;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Credential implements Serializable {
	private static final long serialVersionUID = 6816782385864868056L;

	@JsonProperty("ip")
	private String ip;

	@JsonProperty("username")
	private String username;

	public Credential() {
		super();
	}

	public Credential(String ip, String username) {
		this.ip = ip;
		this.username = username;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.ip, this.username);
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
		Credential other = (Credential) obj;
		return Objects.equals(this.ip, other.ip) && Objects.equals(this.username, other.username);
	}

	@Override
	public String toString() {
		return "CredentialRequest [ip=" + this.ip + ", username=" + this.username + "]";
	}
}