package vault.domain.kv;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Configuration implements Serializable {
	private static final long serialVersionUID = -8564595335711808798L;

	@JsonProperty("cas_required")
	private boolean casRequired;

	@JsonProperty("max_versions")
	private int maxVersions;

	public Configuration() {
		super();
	}

	public boolean isCasRequired() {
		return this.casRequired;
	}

	public void setCasRequired(boolean casRequired) {
		this.casRequired = casRequired;
	}

	public int getMaxVersions() {
		return this.maxVersions;
	}

	public void setMaxVersions(int maxVersions) {
		this.maxVersions = maxVersions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Boolean.valueOf(this.casRequired), Integer.valueOf(this.maxVersions));
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
		Configuration other = (Configuration) obj;
		return this.casRequired == other.casRequired && this.maxVersions == other.maxVersions;
	}

	@Override
	public String toString() {
		return "Configuration [casRequired=" + this.casRequired + ", maxVersions=" + this.maxVersions + "]";
	}
}