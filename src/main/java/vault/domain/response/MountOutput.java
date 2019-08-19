package vault.domain.response;

import static vault.util.Util.copyMap;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class MountOutput implements Serializable {
	private static final long serialVersionUID = -7758318703937529192L;

	@JsonProperty("accessor")
	private String accessor;

	@JsonProperty("config")
	private MountConfigOutput config;

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

	@JsonCreator
	public MountOutput(@JsonProperty("accessor") String accessor, @JsonProperty("config") MountConfigOutput config, @JsonProperty("description") String description, @JsonProperty("local") boolean local, @JsonProperty("options") Map<String, String> options, @JsonProperty("seal_wrap") boolean sealWrap, @JsonProperty("type") String type) {
		this.accessor = accessor;
		this.config = config;
		this.description = description;
		this.local = local;
		this.options = copyMap(options);
		this.sealWrap = sealWrap;
		this.type = type;
	}

	public String getAccessor() {
		return this.accessor;
	}

	public MountConfigOutput getConfig() {
		return this.config;
	}

	public String getDescription() {
		return this.description;
	}

	public boolean isLocal() {
		return this.local;
	}

	public Map<String, String> getOptions() {
		return this.options;
	}

	public boolean isSealWrap() {
		return this.sealWrap;
	}

	public String getType() {
		return this.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.accessor, this.config, this.description, Boolean.valueOf(this.local), this.options, Boolean.valueOf(this.sealWrap), this.type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MountOutput)) {
			return false;
		}
		MountOutput other = (MountOutput) obj;
		return Objects.equals(this.accessor, other.accessor) && Objects.equals(this.config, other.config)
				&& Objects.equals(this.description, other.description) && this.local == other.local
				&& Objects.equals(this.options, other.options) && this.sealWrap == other.sealWrap
				&& Objects.equals(this.type, other.type);
	}

	@Override
	public String toString() {
		return "MountOutput [accessor=" + this.accessor + ", config=" + this.config + ", description=" + this.description + ", local="
				+ this.local + ", options=" + this.options + ", sealWrap=" + this.sealWrap + ", type=" + this.type + "]";
	}
}