package vault.backend;

import static vault.util.Util.copyList;

import java.util.List;
import java.util.Objects;

import vault.domain.response.MountConfigOutput;

public class BackendConfig {

	private List<String> auditNonHmacRequestKeys;
	private List<String> auditNonHmacResponseKeys;
	private long defaultLeaseTtl;
	private boolean forceNoCache;
	private String listingVisibility;
	private long maxLeaseTtl;
	private String passthroughRequestHeaders;
	private String tokenType;

	public BackendConfig(List<String> auditNonHmacRequestKeys, List<String> auditNonHmacResponseKeys, long defaultLeaseTtl, boolean forceNoCache, String listingVisibility, long maxLeaseTtl, String passthroughRequestHeaders, String tokenType) {
		this.auditNonHmacRequestKeys = copyList(auditNonHmacRequestKeys);
		this.auditNonHmacResponseKeys = copyList(auditNonHmacResponseKeys);
		this.defaultLeaseTtl = defaultLeaseTtl;
		this.forceNoCache = forceNoCache;
		this.listingVisibility = listingVisibility;
		this.maxLeaseTtl = maxLeaseTtl;
		this.passthroughRequestHeaders = passthroughRequestHeaders;
		this.tokenType = tokenType;
	}

	public static BackendConfig from(MountConfigOutput mountConfigOutput) {
		return new BackendConfig(mountConfigOutput.getAuditNonHmacRequestKeys(), mountConfigOutput.getAuditNonHmacResponseKeys(), mountConfigOutput.getDefaultLeaseTtl(), mountConfigOutput.isForceNoCache(), mountConfigOutput.getListingVisibility(), mountConfigOutput.getMaxLeaseTtl(), mountConfigOutput.getPassthroughRequestHeaders(), mountConfigOutput.getTokenType());
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

	public String getPassthroughRequestHeaders() {
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		BackendConfig other = (BackendConfig) obj;
		return Objects.equals(this.auditNonHmacRequestKeys, other.auditNonHmacRequestKeys)
				&& Objects.equals(this.auditNonHmacResponseKeys, other.auditNonHmacResponseKeys)
				&& this.defaultLeaseTtl == other.defaultLeaseTtl && this.forceNoCache == other.forceNoCache
				&& Objects.equals(this.listingVisibility, other.listingVisibility) && this.maxLeaseTtl == other.maxLeaseTtl
				&& Objects.equals(this.passthroughRequestHeaders, other.passthroughRequestHeaders)
				&& Objects.equals(this.tokenType, other.tokenType);
	}

	@Override
	public String toString() {
		return "BackendConfig [auditNonHmacRequestKeys=" + this.auditNonHmacRequestKeys + ", auditNonHmacResponseKeys="
				+ this.auditNonHmacResponseKeys + ", defaultLeaseTtl=" + this.defaultLeaseTtl + ", forceNoCache="
				+ this.forceNoCache + ", listingVisibility=" + this.listingVisibility + ", maxLeaseTtl="
				+ this.maxLeaseTtl + ", passthroughRequestHeaders=" + this.passthroughRequestHeaders + ", tokenType="
				+ this.tokenType + "]";
	}
}