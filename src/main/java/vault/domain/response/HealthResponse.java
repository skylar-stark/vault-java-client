package vault.domain.response;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class HealthResponse implements Serializable {
	private static final long serialVersionUID = 8264319198276523218L;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("cluster_id")
	private String clusterId;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("cluster_name")
	private String clusterName;

	@JsonProperty("initialized")
	private boolean initialized;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("last_wal")
	private Long lastWal;

	@JsonProperty("performance_standby")
	private boolean performanceStandby;

	@JsonProperty("replication_dr_mode")
	private String replicationDrMode;

	@JsonProperty("replication_performance_mode")
	private String replicationPerformanceMode;

	@JsonProperty("sealed")
	private boolean sealed;

	@JsonProperty("server_time_utc")
	private long serverTimeUtc;

	@JsonProperty("standby")
	private boolean standby;

	@JsonProperty("version")
	private String version;

	@JsonCreator
	public HealthResponse(@JsonProperty("cluster_id") String clusterId, @JsonProperty("cluster_name") String clusterName, @JsonProperty("initialized") boolean initialized, @JsonProperty("last_wal") Long lastWal, @JsonProperty("performance_standby") boolean performanceStandby, @JsonProperty("replication_dr_mode") String replicationDrMode, @JsonProperty("replication_performance_mode") String replicationPerformanceMode, @JsonProperty("sealed") boolean sealed, @JsonProperty("server_time_utc") long serverTimeUtc, @JsonProperty("standby") boolean standby, @JsonProperty("version") String version) {
		this.clusterId = clusterId;
		this.clusterName = clusterName;
		this.initialized = initialized;
		this.lastWal = lastWal;
		this.performanceStandby = performanceStandby;
		this.replicationDrMode = replicationDrMode;
		this.replicationPerformanceMode = replicationPerformanceMode;
		this.sealed = sealed;
		this.serverTimeUtc = serverTimeUtc;
		this.standby = standby;
		this.version = version;
	}

	public String getClusterId() {
		return this.clusterId;
	}

	public String getClusterName() {
		return this.clusterName;
	}

	public boolean isInitialized() {
		return this.initialized;
	}

	public Long getLastWal() {
		return this.lastWal;
	}

	public boolean isPerformanceStandby() {
		return this.performanceStandby;
	}

	public String getReplicationDrMode() {
		return this.replicationDrMode;
	}

	public String getReplicationPerformanceMode() {
		return this.replicationPerformanceMode;
	}

	public boolean isSealed() {
		return this.sealed;
	}

	public long getServerTimeUtc() {
		return this.serverTimeUtc;
	}

	public boolean isStandby() {
		return this.standby;
	}

	public String getVersion() {
		return this.version;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.clusterId, this.clusterName, Boolean.valueOf(this.initialized), this.lastWal, Boolean.valueOf(this.performanceStandby), this.replicationDrMode, this.replicationPerformanceMode, Boolean.valueOf(this.sealed), Long.valueOf(this.serverTimeUtc), Boolean.valueOf(this.standby), this.version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HealthResponse)) {
			return false;
		}
		HealthResponse other = (HealthResponse) obj;
		return Objects.equals(this.clusterId, other.clusterId) && Objects.equals(this.clusterName, other.clusterName)
				&& this.initialized == other.initialized && Objects.equals(this.lastWal, other.lastWal)
				&& this.performanceStandby == other.performanceStandby
				&& Objects.equals(this.replicationDrMode, other.replicationDrMode)
				&& Objects.equals(this.replicationPerformanceMode, other.replicationPerformanceMode)
				&& this.sealed == other.sealed && this.serverTimeUtc == other.serverTimeUtc && this.standby == other.standby
				&& Objects.equals(this.version, other.version);
	}

	@Override
	public String toString() {
		return "Health [clusterId=" + this.clusterId + ", clusterName=" + this.clusterName + ", initialized=" + this.initialized
				+ ", lastWal=" + this.lastWal + ", performanceStandby=" + this.performanceStandby + ", replicationDrMode="
				+ this.replicationDrMode + ", replicationPerformanceMode=" + this.replicationPerformanceMode + ", sealed="
				+ this.sealed + ", serverTimeUtc=" + this.serverTimeUtc + ", standby=" + this.standby + ", version=" + this.version + "]";
	}
}