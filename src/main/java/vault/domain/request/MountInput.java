package vault.domain.request;

import static vault.util.Util.copyMap;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class MountInput implements Serializable {
	private static final long serialVersionUID = -4826403633762665618L;

	@JsonProperty("config")
	private MountConfigInput config;

	@JsonProperty("description")
	private String description;

	@JsonProperty("local")
	private boolean local;

	@JsonProperty("options")
	private Map<String, String> options;

	@JsonProperty("seal_wrap")
	private boolean sealWrap;

	@JsonProperty("type")
	private String type;

	public MountInput() {
		super();
	}

	public MountConfigInput getConfig() {
		return this.config;
	}

	public void setConfig(MountConfigInput config) {
		this.config = config;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isLocal() {
		return this.local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

	public Map<String, String> getOptions() {
		return this.options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = copyMap(options);
	}

	public boolean isSealWrap() {
		return this.sealWrap;
	}

	public void setSealWrap(boolean sealWrap) {
		this.sealWrap = sealWrap;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.config, this.description, Boolean.valueOf(this.local), this.options, Boolean.valueOf(this.sealWrap), this.type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MountInput)) {
			return false;
		}
		MountInput other = (MountInput) obj;
		return Objects.equals(this.config, other.config) && Objects.equals(this.description, other.description)
				&& this.local == other.local && Objects.equals(this.options, other.options) && this.sealWrap == other.sealWrap
				&& Objects.equals(this.type, other.type);
	}

	@Override
	public String toString() {
		return "Mount [description=" + this.description + ", local=" + this.local + ", options=" + this.options + ", sealWrap="
				+ this.sealWrap + ", type=" + this.type + "]";
	}
}