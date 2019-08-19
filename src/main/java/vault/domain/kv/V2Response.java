package vault.domain.kv;

import static vault.util.Util.copyMap;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class V2Response implements Serializable {
	private static final long serialVersionUID = -6602570393972629197L;

	@JsonProperty("data")
	private Map<String, String> data;

	@JsonProperty("metadata")
	private Metadata metadata;

	public V2Response() {
		super();
	}

	public Map<String, String> getData() {
		return this.data;
	}

	public void setData(Map<String, String> data) {
		this.data = copyMap(data);
	}

	public Metadata getMetadata() {
		return this.metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.data, this.metadata);
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
		V2Response other = (V2Response) obj;
		return Objects.equals(this.data, other.data) && Objects.equals(this.metadata, other.metadata);
	}

	@Override
	public String toString() {
		return "KVResponseV2 [data=" + this.data + ", metadata=" + this.metadata + "]";
	}
}