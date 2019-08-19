package vault.domain.response;

import static vault.util.Util.copyList;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class MountConfigOutput implements Serializable {
	private static final long serialVersionUID = 785800761616776557L;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("audit_non_hmac_request_keys")
	private List<String> auditNonHmacRequestKeys;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("audit_non_hmac_response_keys")
	private List<String> auditNonHmacResponseKeys;

	@JsonProperty("default_lease_ttl")
	private long defaultLeaseTtl;

	@JsonProperty("force_no_cache")
	private boolean forceNoCache;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("listing_visibility")
	private String listingVisibility;

	@JsonProperty("max_lease_ttl")
	private long maxLeaseTtl;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("passthrough_request_headers")
	private List<String> passthroughRequestHeaders;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("token_type")
	private String tokenType;

	@JsonCreator
	public MountConfigOutput(@JsonProperty("audit_non_hmac_request_keys") List<String> auditNonHmacRequestKeys,@JsonProperty("audit_non_hmac_response_keys") List<String> auditNonHmacResponseKeys, @JsonProperty("default_lease_ttl") long defaultLeaseTtl, @JsonProperty("force_no_cache") boolean forceNoCache, @JsonProperty("listing_visibility") String listingVisibility, @JsonProperty("max_lease_ttl") long maxLeaseTtl, @JsonProperty("passthrough_request_headers") List<String> passthroughRequestHeaders, @JsonProperty("token_type") String tokenType) {
		this.auditNonHmacRequestKeys = copyList(auditNonHmacRequestKeys);
		this.auditNonHmacResponseKeys = copyList(auditNonHmacResponseKeys);
		this.defaultLeaseTtl = defaultLeaseTtl;
		this.forceNoCache = forceNoCache;
		this.listingVisibility = listingVisibility;
		this.maxLeaseTtl = maxLeaseTtl;
		this.passthroughRequestHeaders = copyList(passthroughRequestHeaders);
		this.tokenType = tokenType;
	}

	public List<String> getAuditNonHmacRequestKeys() {
		return this.auditNonHmacRequestKeys;
	}

	public List<String> getAuditNonHmacResponseKeys() {
		return this.auditNonHmacResponseKeys;
	}

	public long getDefaultLeaseTtl() {
		return this.defaultLeaseTtl;
	}

	public boolean isForceNoCache() {
		return this.forceNoCache;
	}

	public String getListingVisibility() {
		return this.listingVisibility;
	}

	public long getMaxLeaseTtl() {
		return this.maxLeaseTtl;
	}

	public List<String> getPassthroughRequestHeaders() {
		return this.passthroughRequestHeaders;
	}

	public String getTokenType() {
		return this.tokenType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.auditNonHmacRequestKeys, this.auditNonHmacResponseKeys, Long.valueOf(this.defaultLeaseTtl), Boolean.valueOf(this.forceNoCache), this.listingVisibility, Long.valueOf(this.maxLeaseTtl), this.passthroughRequestHeaders, this.tokenType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MountConfigOutput)) {
			return false;
		}
		MountConfigOutput other = (MountConfigOutput) obj;
		return Objects.equals(this.auditNonHmacRequestKeys, other.auditNonHmacRequestKeys)
				&& Objects.equals(this.auditNonHmacResponseKeys, other.auditNonHmacResponseKeys)
				&& this.defaultLeaseTtl == other.defaultLeaseTtl && this.forceNoCache == other.forceNoCache
				&& Objects.equals(this.listingVisibility, other.listingVisibility) && this.maxLeaseTtl == other.maxLeaseTtl
				&& Objects.equals(this.passthroughRequestHeaders, other.passthroughRequestHeaders)
				&& Objects.equals(this.tokenType, other.tokenType);
	}

	@Override
	public String toString() {
		return "MountConfigOutput [auditNonHmacRequestKeys=" + this.auditNonHmacRequestKeys
				+ ", auditNonHmacResponseKeys=" + this.auditNonHmacResponseKeys + ", defaultLeaseTtl="
				+ this.defaultLeaseTtl + ", forceNoCache=" + this.forceNoCache + ", listingVisibility="
				+ this.listingVisibility + ", maxLeaseTtl=" + this.maxLeaseTtl + ", passthroughRequestHeaders="
				+ this.passthroughRequestHeaders + ", tokenType=" + this.tokenType + "]";
	}
}