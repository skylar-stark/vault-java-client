package vault.domain.kv;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Options implements Serializable {
	private static final long serialVersionUID = -5866765857155691865L;

	@JsonProperty("cas")
	private int cas;

	public Options() {
		super();
	}

	public Options(int cas) {
		this.cas = cas;
	}

	public int getCas() {
		return this.cas;
	}

	public void setCas(int cas) {
		this.cas = cas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.cas));
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
		Options other = (Options) obj;
		return this.cas == other.cas;
	}

	@Override
	public String toString() {
		return "Options [cas=" + this.cas + "]";
	}
}