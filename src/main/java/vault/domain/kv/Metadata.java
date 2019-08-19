package vault.domain.kv;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Metadata implements Serializable {
	private static final long serialVersionUID = -396643515299342127L;

	@JsonProperty("created_time")
	private String createdTime;

	@JsonProperty("deletion_time")
	private String deletionTime;

	@JsonProperty("destroyed")
	private boolean destroyed;

	@JsonProperty("version")
	private int version;

	public Metadata() {
		super();
	}

	public String getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getDeletionTime() {
		return this.deletionTime;
	}

	public void setDeletionTime(String deletionTime) {
		this.deletionTime = deletionTime;
	}

	public boolean isDestroyed() {
		return this.destroyed;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.createdTime, this.deletionTime, Boolean.valueOf(this.destroyed), Integer.valueOf(this.version));
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
		Metadata other = (Metadata) obj;
		return Objects.equals(this.createdTime, other.createdTime) && Objects.equals(this.deletionTime, other.deletionTime)
				&& this.destroyed == other.destroyed && this.version == other.version;
	}

	@Override
	public String toString() {
		return "Metadata [createdTime=" + this.createdTime + ", deletionTime=" + this.deletionTime + ", destroyed="
				+ this.destroyed + ", version=" + this.version + "]";
	}
}