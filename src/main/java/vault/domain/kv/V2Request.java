package vault.domain.kv;

import static vault.util.Util.copyMap;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class V2Request implements Serializable {
	private static final long serialVersionUID = -3997755814579058641L;

	@JsonProperty("data")
	private Map<String, String> data;

	@JsonProperty("options")
	private Options options;

	public V2Request() {
		super();
	}

	public V2Request(Map<String, String> data, Options options) {
		this.data = copyMap(data);
		this.options = options;
	}

	public Map<String, String> getData() {
		return this.data;
	}

	public void setData(Map<String, String> data) {
		this.data = copyMap(data);
	}

	public Options getOptions() {
		return this.options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.data, this.options);
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
		V2Request other = (V2Request) obj;
		return Objects.equals(this.data, other.data) && Objects.equals(this.options, other.options);
	}

	@Override
	public String toString() {
		return "V2Request [data=" + this.data + ", options=" + this.options + "]";
	}
}