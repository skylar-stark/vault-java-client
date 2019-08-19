package vault.domain.kv;

import static vault.util.Util.copyMap;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class SecretMetadata implements Serializable {
	private static final long serialVersionUID = -8111636737854159539L;

	@JsonProperty("created_time")
	private String createdTime;

	@JsonProperty("current_version")
	private int currentVersion;

	@JsonProperty("max_versions")
	private int maxVersions;

	@JsonProperty("oldest_version")
	private int oldestVersion;

	@JsonProperty("updated_time")
	private String updatedTime;

	@JsonProperty("versions")
	private Map<String, Metadata> versions;

	
	public SecretMetadata() {
		super();
	}

	public String getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public int getCurrentVersion() {
		return this.currentVersion;
	}

	public void setCurrentVersion(int currentVersion) {
		this.currentVersion = currentVersion;
	}

	public int getMaxVersions() {
		return this.maxVersions;
	}

	public void setMaxVersions(int maxVersions) {
		this.maxVersions = maxVersions;
	}

	public int getOldestVersion() {
		return this.oldestVersion;
	}

	public void setOldestVersion(int oldestVersion) {
		this.oldestVersion = oldestVersion;
	}

	public String getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Map<String, Metadata> getVersions() {
		return this.versions;
	}

	public void setVersions(Map<String, Metadata> versions) {
		this.versions = copyMap(versions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.createdTime, Integer.valueOf(this.currentVersion), Integer.valueOf(this.maxVersions), Integer.valueOf(this.oldestVersion), this.updatedTime, this.versions);
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
		SecretMetadata other = (SecretMetadata) obj;
		return Objects.equals(this.createdTime, other.createdTime) && this.currentVersion == other.currentVersion
				&& this.maxVersions == other.maxVersions && this.oldestVersion == other.oldestVersion
				&& Objects.equals(this.updatedTime, other.updatedTime) && Objects.equals(this.versions, other.versions);
	}

	@Override
	public String toString() {
		return "SecretMetadata [createdTime=" + this.createdTime + ", currentVersion=" + this.currentVersion
				+ ", maxVersions=" + this.maxVersions + ", oldestVersion=" + this.oldestVersion + ", updatedTime="
				+ this.updatedTime + ", versions=" + this.versions + "]";
	}
}