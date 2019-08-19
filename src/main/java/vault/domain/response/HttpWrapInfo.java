package vault.domain.response;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class HttpWrapInfo implements Serializable {
	private static final long serialVersionUID = 6871382167601890879L;

	@JsonProperty("accessor")
	private String accessor;

	@JsonProperty("creation_path")
	private String creationPath;

	@JsonProperty("creation_time")
	private String creationTime;

	@JsonProperty("token")
	private String token;

	@JsonProperty("ttl")
	private long ttl;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("wrapped_accessor")
	private String wrappedAccessor;

	@JsonCreator
	public HttpWrapInfo(@JsonProperty("accessor") String accessor, @JsonProperty("creation_path") String creationPath, @JsonProperty("creation_time") String creationTime, @JsonProperty("token") String token, @JsonProperty("ttl") long ttl, @JsonProperty("wrapped_accessor") String wrappedAccessor) {
		this.accessor = accessor;
		this.creationPath = creationPath;
		this.creationTime = creationTime;
		this.token = token;
		this.ttl = ttl;
		this.wrappedAccessor = wrappedAccessor;
	}

	public String getAccessor() {
		return this.accessor;
	}

	public String getCreationPath() {
		return this.creationPath;
	}

	public String getCreationTime() {
		return this.creationTime;
	}

	public String getToken() {
		return this.token;
	}

	public long getTtl() {
		return this.ttl;
	}

	public String getWrappedAccessor() {
		return this.wrappedAccessor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.accessor, this.creationPath, this.creationTime, this.token, Long.valueOf(this.ttl), this.wrappedAccessor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HttpWrapInfo)) {
			return false;
		}
		HttpWrapInfo other = (HttpWrapInfo) obj;
		return Objects.equals(this.accessor, other.accessor) && Objects.equals(this.creationPath, other.creationPath)
				&& Objects.equals(this.creationTime, other.creationTime) && Objects.equals(this.token, other.token)
				&& this.ttl == other.ttl && Objects.equals(this.wrappedAccessor, other.wrappedAccessor);
	}

	@Override
	public String toString() {
		return "HttpWrapInfo [accessor=" + this.accessor + ", creationPath=" + this.creationPath + ", creationTime="
				+ this.creationTime + ", token=" + this.token + ", ttl=" + this.ttl + ", wrappedAccessor=" + this.wrappedAccessor + "]";
	}
}