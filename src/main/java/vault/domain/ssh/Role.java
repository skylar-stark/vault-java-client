package vault.domain.ssh;

import static vault.util.Util.checkAllowedValue;
import static vault.util.Util.copyMap;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Role implements Serializable {
	private static final long serialVersionUID = 7002855303350072826L;

	@JsonProperty("admin_user")
	private String adminUser;

	@JsonProperty("allow_bare_domains")
	private boolean allowBareDomains;

	@JsonProperty("allowed_critical_options")
	private String allowedCriticalOptions;

	@JsonProperty("allowed_domains")
	private String allowedDomains;

	@JsonProperty("allowed_extensions")
	private String allowedExtensions;

	@JsonProperty("allowed_users")
	private String allowedUsers;

	@JsonProperty("allow_host_certificates")
	private boolean allowHostCertificates;

	@JsonProperty("allow_subdomains")
	private boolean allowSubdomains;

	@JsonProperty("allow_user_certificates")
	private boolean allowUserCertificates;

	@JsonProperty("allow_user_key_ids")
	private boolean allowUserKeyIds;

	@JsonProperty("cidr_list")
	private String cidrList;

	@JsonProperty("default_critical_options")
	private Map<String, String> defaultCriticalOptions;

	@JsonProperty("default_extensions")
	private Map<String, String> defaultExtensions;

	@JsonProperty("default_user")
	private String defaultUser;

	@JsonProperty("exlude_cidr_list")
	private String excludeCidrList;

	@JsonProperty("install_script")
	private String installScript;

	@JsonProperty("key")
	private String key;

	@JsonProperty("key_bits")
	private int keyBits = 1024;

	@JsonProperty("key_id_format")
	private String keyIdFormat;

	@JsonProperty("key_option_specs")
	private String keyOptionSpecs;

	@JsonProperty("key_type")
	private String keyType;

	@JsonProperty("max_ttl")
	private String maxTtl;

	@JsonProperty("port")
	private int port = 22;

	@JsonProperty("ttl")
	private String ttl;

	public Role() {
		super();
	}

	public String getAdminUser() {
		return this.adminUser;
	}

	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
	}

	public boolean isAllowBareDomains() {
		return this.allowBareDomains;
	}

	public void setAllowBareDomains(boolean allowBareDomains) {
		this.allowBareDomains = allowBareDomains;
	}

	public String getAllowedCriticalOptions() {
		return this.allowedCriticalOptions;
	}

	public void setAllowedCriticalOptions(String allowedCriticalOptions) {
		this.allowedCriticalOptions = allowedCriticalOptions;
	}

	public String getAllowedDomains() {
		return this.allowedDomains;
	}

	public void setAllowedDomains(String allowedDomains) {
		this.allowedDomains = allowedDomains;
	}

	public String getAllowedExtensions() {
		return this.allowedExtensions;
	}

	public void setAllowedExtensions(String allowedExtensions) {
		this.allowedExtensions = allowedExtensions;
	}

	public String getAllowedUsers() {
		return this.allowedUsers;
	}

	public void setAllowedUsers(String allowedUsers) {
		this.allowedUsers = allowedUsers;
	}

	public boolean isAllowHostCertificates() {
		return this.allowHostCertificates;
	}

	public void setAllowHostCertificates(boolean allowHostCertificates) {
		this.allowHostCertificates = allowHostCertificates;
	}

	public boolean isAllowSubdomains() {
		return this.allowSubdomains;
	}

	public void setAllowSubdomains(boolean allowSubdomains) {
		this.allowSubdomains = allowSubdomains;
	}

	public boolean isAllowUserCertificates() {
		return this.allowUserCertificates;
	}

	public void setAllowUserCertificates(boolean allowUserCertificates) {
		this.allowUserCertificates = allowUserCertificates;
	}

	public boolean isAllowUserKeyIds() {
		return this.allowUserKeyIds;
	}

	public void setAllowUserKeyIds(boolean allowUserKeyIds) {
		this.allowUserKeyIds = allowUserKeyIds;
	}

	public String getCidrList() {
		return this.cidrList;
	}

	public void setCidrList(String cidrList) {
		this.cidrList = cidrList;
	}

	public Map<String, String> getDefaultCriticalOptions() {
		return this.defaultCriticalOptions;
	}

	public void setDefaultCriticalOptions(Map<String, String> defaultCriticalOptions) {
		this.defaultCriticalOptions = copyMap(defaultCriticalOptions);
	}

	public Map<String, String> getDefaultExtensions() {
		return this.defaultExtensions;
	}

	public void setDefaultExtensions(Map<String, String> defaultExtensions) {
		this.defaultExtensions = copyMap(defaultExtensions);
	}

	public String getDefaultUser() {
		return this.defaultUser;
	}

	public void setDefaultUser(String defaultUser) {
		this.defaultUser = defaultUser;
	}

	public String getExcludeCidrList() {
		return this.excludeCidrList;
	}

	public void setExcludeCidrList(String excludeCidrList) {
		this.excludeCidrList = excludeCidrList;
	}

	public String getInstallScript() {
		return this.installScript;
	}

	public void setInstallScript(String installScript) {
		this.installScript = installScript;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getKeyBits() {
		return this.keyBits;
	}

	public void setKeyBits(int keyBits) {
		this.keyBits = checkAllowedValue("keyBits", Integer.valueOf(keyBits), Integer.valueOf(1024), Integer.valueOf(2048)).intValue();
	}

	public String getKeyIdFormat() {
		return this.keyIdFormat;
	}

	public void setKeyIdFormat(String keyIdFormat) {
		this.keyIdFormat = keyIdFormat;
	}

	public String getKeyOptionSpecs() {
		return this.keyOptionSpecs;
	}

	public void setKeyOptionSpecs(String keyOptionSpecs) {
		this.keyOptionSpecs = keyOptionSpecs;
	}

	public String getKeyType() {
		return this.keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = checkAllowedValue("keyType", keyType == null ? null : keyType.toLowerCase(Locale.ENGLISH), "ca", "dynamic", "otp");
	}

	public String getMaxTtl() {
		return this.maxTtl;
	}

	public void setMaxTtl(String maxTtl) {
		this.maxTtl = maxTtl;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getTtl() {
		return this.ttl;
	}

	public void setTtl(String ttl) {
		this.ttl = ttl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.adminUser, Boolean.valueOf(this.allowBareDomains), Boolean.valueOf(this.allowHostCertificates), Boolean.valueOf(this.allowSubdomains), Boolean.valueOf(this.allowUserCertificates),
				Boolean.valueOf(this.allowUserKeyIds), this.allowedCriticalOptions, this.allowedDomains, this.allowedExtensions, this.allowedUsers, this.cidrList,
				this.defaultCriticalOptions, this.defaultExtensions, this.defaultUser, this.excludeCidrList, this.installScript, this.key, Integer.valueOf(this.keyBits),
				this.keyIdFormat, this.keyOptionSpecs, this.keyType, this.maxTtl, Integer.valueOf(this.port), this.ttl);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Role)) {
			return false;
		}
		Role other = (Role) obj;
		return Objects.equals(this.adminUser, other.adminUser) && this.allowBareDomains == other.allowBareDomains
				&& this.allowHostCertificates == other.allowHostCertificates && this.allowSubdomains == other.allowSubdomains
				&& this.allowUserCertificates == other.allowUserCertificates && this.allowUserKeyIds == other.allowUserKeyIds
				&& Objects.equals(this.allowedCriticalOptions, other.allowedCriticalOptions)
				&& Objects.equals(this.allowedDomains, other.allowedDomains)
				&& Objects.equals(this.allowedExtensions, other.allowedExtensions)
				&& Objects.equals(this.allowedUsers, other.allowedUsers) && Objects.equals(this.cidrList, other.cidrList)
				&& Objects.equals(this.defaultCriticalOptions, other.defaultCriticalOptions)
				&& Objects.equals(this.defaultExtensions, other.defaultExtensions)
				&& Objects.equals(this.defaultUser, other.defaultUser)
				&& Objects.equals(this.excludeCidrList, other.excludeCidrList)
				&& Objects.equals(this.installScript, other.installScript) && Objects.equals(this.key, other.key)
				&& this.keyBits == other.keyBits && Objects.equals(this.keyIdFormat, other.keyIdFormat)
				&& Objects.equals(this.keyOptionSpecs, other.keyOptionSpecs) && Objects.equals(this.keyType, other.keyType)
				&& Objects.equals(this.maxTtl, other.maxTtl) && this.port == other.port && Objects.equals(this.ttl, other.ttl);
	}

	@Override
	public String toString() {
		return "Role [adminUser=" + this.adminUser + ", allowBareDomains=" + this.allowBareDomains + ", allowedCriticalOptions="
				+ this.allowedCriticalOptions + ", allowedDomains=" + this.allowedDomains + ", allowedExtensions="
				+ this.allowedExtensions + ", allowedUsers=" + this.allowedUsers + ", allowHostCertificates="
				+ this.allowHostCertificates + ", allowSubdomains=" + this.allowSubdomains + ", allowUserCertificates="
				+ this.allowUserCertificates + ", allowUserKeyIds=" + this.allowUserKeyIds + ", cidrList=" + this.cidrList
				+ ", defaultCriticalOptions=" + this.defaultCriticalOptions + ", defaultExtensions=" + this.defaultExtensions
				+ ", defaultUser=" + this.defaultUser + ", excludeCidrList=" + this.excludeCidrList + ", installScript="
				+ this.installScript + ", key=" + this.key + ", keyBits=" + this.keyBits + ", keyIdFormat=" + this.keyIdFormat
				+ ", keyOptionSpecs=" + this.keyOptionSpecs + ", keyType=" + this.keyType + ", maxTtl=" + this.maxTtl + ", port="
				+ this.port + ", ttl=" + this.ttl + "]";
	}
}