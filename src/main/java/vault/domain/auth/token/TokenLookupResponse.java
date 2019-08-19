package vault.domain.auth.token;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class TokenLookupResponse implements Serializable {
	private static final long serialVersionUID = -916958986995810780L;

	@JsonProperty("accessor")
	private String accessor;

	@JsonProperty("creation_time")
	private long creationTime;

	@JsonProperty("creation_ttl")
	private long creationTtl;

	@JsonProperty("display_name")
	private String displayName;

	@JsonProperty("entity_id")
	private String entityId;

	@JsonProperty("expire_time")
	private String expireTime;

	@JsonProperty("explicit_max_ttl")
	private long explicitMaxTtl;

	@JsonProperty("id")
	private String id;

	@JsonProperty("identity_policies")
	private List<String> identityPolicies;

	@JsonProperty("meta")
	private Map<String, String> meta;

	@JsonProperty("num_uses")
	private long numUses;

	@JsonProperty("orphan")
	private boolean orphan;

	@JsonProperty("path")
	private String path;

	@JsonProperty("policies")
	private List<String> policies;

	@JsonProperty("renewable")
	private boolean renewable;

	@JsonProperty("ttl")
	private long ttl;

	public TokenLookupResponse() {
		super();
	}

	public String getAccessor() {
		return this.accessor;
	}

	public void setAccessor(String accessor) {
		this.accessor = accessor;
	}

	public long getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public long getCreationTtl() {
		return this.creationTtl;
	}

	public void setCreationTtl(long creationTtl) {
		this.creationTtl = creationTtl;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEntityId() {
		return this.entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getExpireTime() {
		return this.expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public long getExplicitMaxTtl() {
		return this.explicitMaxTtl;
	}

	public void setExplicitMaxTtl(long explicitMaxTtl) {
		this.explicitMaxTtl = explicitMaxTtl;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getIdentityPolicies() {
		return this.identityPolicies;
	}

	public void setIdentityPolicies(List<String> identityPolicies) {
		this.identityPolicies = identityPolicies;
	}

	public Map<String, String> getMeta() {
		return this.meta;
	}

	public void setMeta(Map<String, String> meta) {
		this.meta = meta;
	}

	public long getNumUses() {
		return this.numUses;
	}

	public void setNumUses(long numUses) {
		this.numUses = numUses;
	}

	public boolean isOrphan() {
		return this.orphan;
	}

	public void setOrphan(boolean orphan) {
		this.orphan = orphan;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<String> getPolicies() {
		return this.policies;
	}

	public void setPolicies(List<String> policies) {
		this.policies = policies;
	}

	public boolean isRenewable() {
		return this.renewable;
	}

	public void setRenewable(boolean renewable) {
		this.renewable = renewable;
	}

	public long getTtl() {
		return this.ttl;
	}

	public void setTtl(long ttl) {
		this.ttl = ttl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.accessor, Long.valueOf(this.creationTime), Long.valueOf(this.creationTtl), this.displayName, this.entityId, this.expireTime, Long.valueOf(this.explicitMaxTtl), this.id, this.identityPolicies, this.meta, Long.valueOf(this.numUses), Boolean.valueOf(this.orphan), this.path, this.policies, Boolean.valueOf(this.renewable), Long.valueOf(this.ttl));
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
		TokenLookupResponse other = (TokenLookupResponse) obj;
		return Objects.equals(this.accessor, other.accessor) && this.creationTime == other.creationTime && this.creationTtl == other.creationTtl && Objects.equals(this.displayName, other.displayName) && Objects.equals(this.entityId, other.entityId) && Objects.equals(this.expireTime, other.expireTime) && this.explicitMaxTtl == other.explicitMaxTtl && Objects.equals(this.id, other.id) && Objects.equals(this.identityPolicies, other.identityPolicies) && Objects.equals(this.meta, other.meta) && this.numUses == other.numUses && this.orphan == other.orphan && Objects.equals(this.path, other.path) && Objects.equals(this.policies, other.policies) && this.renewable == other.renewable && this.ttl == other.ttl;
	}

	@Override
	public String toString() {
		return "TokenLookupResponse [accessor=" + this.accessor + ", creationTime=" + this.creationTime + ", creationTtl=" + this.creationTtl + ", displayName=" + this.displayName + ", entityId=" + this.entityId + ", expireTime=" + this.expireTime + ", explicitMaxTtl=" + this.explicitMaxTtl + ", id=" + this.id + ", identityPolicies=" + this.identityPolicies + ", meta=" + this.meta + ", numUses=" + this.numUses + ", orphan=" + this.orphan + ", path=" + this.path + ", policies=" + this.policies + ", renewable=" + this.renewable + ", ttl=" + this.ttl + "]";
	}
}