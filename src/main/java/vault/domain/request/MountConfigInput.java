package vault.domain.request;

import static vault.util.Util.checkAllowedValue;
import static vault.util.Util.copyList;
import static vault.util.Util.copyMap;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class MountConfigInput implements Serializable {
	private static final long serialVersionUID = 7653312953687062108L;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("audit_non_hmac_request_keys")
	private List<String> auditNonHmacRequestKeys;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("audit_non_hmac_response_keys")
	private List<String> auditNonHmacResponseKeys;

	@JsonProperty("default_lease_ttl")
	private String defaultLeaseTtl;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("description")
	private String description;

	@JsonProperty("force_no_cache")
	private boolean forceNoCache;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("listing_visibility")
	private String listingVisibility;

	@JsonProperty("max_lease_ttl")
	private String maxLeaseTtl;

	@JsonProperty("options")
	private Map<String, String> options;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("passthrough_request_headers")
	private String passthroughRequestHeaders;

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("token_type")
	private String tokenType;

	public MountConfigInput() {
		super();
	}

	public List<String> getAuditNonHmacRequestKeys() {
		return this.auditNonHmacRequestKeys;
	}

	public void setAuditNonHmacRequestKeys(List<String> auditNonHmacRequestKeys) {
		this.auditNonHmacRequestKeys = copyList(auditNonHmacRequestKeys);
	}

	public List<String> getAuditNonHmacResponseKeys() {
		return this.auditNonHmacResponseKeys;
	}

	public void setAuditNonHmacResponseKeys(List<String> auditNonHmacResponseKeys) {
		this.auditNonHmacResponseKeys = copyList(auditNonHmacResponseKeys);
	}

	public String getDefaultLeaseTtl() {
		return this.defaultLeaseTtl;
	}

	public void setDefaultLeaseTtl(String defaultLeaseTtl) {
		this.defaultLeaseTtl = defaultLeaseTtl;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isForceNoCache() {
		return this.forceNoCache;
	}

	public void setForceNoCache(boolean forceNoCache) {
		this.forceNoCache = forceNoCache;
	}

	public String getListingVisibility() {
		return this.listingVisibility;
	}

	public void setListingVisibility(String listingVisibility) {
		this.listingVisibility = checkAllowedValue("listing_visibility", listingVisibility == null ? null : listingVisibility.toLowerCase(Locale.ENGLISH), "auth", "hidden");
	}

	public String getMaxLeaseTtl() {
		return this.maxLeaseTtl;
	}

	public void setMaxLeaseTtl(String maxLeaseTtl) {
		this.maxLeaseTtl = maxLeaseTtl;
	}

	public Map<String, String> getOptions() {
		return this.options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = copyMap(options);
	}

	public String getPassthroughRequestHeaders() {
		return this.passthroughRequestHeaders;
	}

	public void setPassthroughRequestHeaders(String passthroughRequestHeaders) {
		this.passthroughRequestHeaders = passthroughRequestHeaders;
	}

	public String getTokenType() {
		return this.tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.auditNonHmacRequestKeys, this.auditNonHmacResponseKeys, this.defaultLeaseTtl, this.description, Boolean.valueOf(this.forceNoCache), this.listingVisibility, this.maxLeaseTtl, this.options, this.passthroughRequestHeaders, this.tokenType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MountConfigInput)) {
			return false;
		}
		MountConfigInput other = (MountConfigInput) obj;
		return Objects.equals(this.auditNonHmacRequestKeys, other.auditNonHmacRequestKeys)
				&& Objects.equals(this.auditNonHmacResponseKeys, other.auditNonHmacResponseKeys)
				&& Objects.equals(this.defaultLeaseTtl, other.defaultLeaseTtl)
				&& Objects.equals(this.description, other.description) && this.forceNoCache == other.forceNoCache
				&& Objects.equals(this.listingVisibility, other.listingVisibility)
				&& Objects.equals(this.maxLeaseTtl, other.maxLeaseTtl) && Objects.equals(this.options, other.options)
				&& Objects.equals(this.passthroughRequestHeaders, other.passthroughRequestHeaders)
				&& Objects.equals(this.tokenType, other.tokenType);
	}

	@Override
	public String toString() {
		return "MountConfig [auditNonHmacRequestKeys=" + this.auditNonHmacRequestKeys + ", auditNonHmacResponseKeys="
				+ this.auditNonHmacResponseKeys + ", defaultLeaseTtl=" + this.defaultLeaseTtl + ", description=" + this.description
				+ ", forceNoCache=" + this.forceNoCache + ", listingVisibility=" + this.listingVisibility + ", maxLeaseTtl="
				+ this.maxLeaseTtl + ", options=" + this.options + ", passthroughRequestHeaders=" + this.passthroughRequestHeaders
				+ ", tokenType=" + this.tokenType + "]";
	}
}