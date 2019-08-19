package vault;

import static vault.util.Util.checkAllowedValue;

import vault.token.VaultTokenFactory;

public final class VaultConfiguration {
	private static final String VAULT_URI = "%s://%s:%s";

	private String vaultAddress;
	private String vaultHost;
	private Integer vaultPort;
	private String vaultScheme;
	private String vaultToken;
	private VaultTokenFactory vaultTokenFactory;

	public VaultConfiguration() {
		super();
	}

	public String getVaultAddress() {
		if (this.vaultAddress == null && (this.vaultHost == null || this.vaultPort == null || this.vaultScheme == null)) {
			throw new UnsupportedOperationException("vaultAdress must not be null, or all of vaultHost, vaultPort, and vaultScheme must not be null.");
		}

		return this.vaultAddress == null ? String.format(VAULT_URI, this.vaultScheme, this.vaultHost, this.vaultPort.toString()) : this.vaultAddress;
	}

	public void setVaultAddress(String vaultAddress) {
		this.vaultAddress = vaultAddress;
	}

	public void setVaultHost(String vaultHost) {
		this.vaultHost = vaultHost;
	}

	public void setVaultPort(Integer vaultPort) {
		this.vaultPort = vaultPort;
	}

	public void setVaultScheme(String vaultScheme) {
		this.vaultScheme = checkAllowedValue("vaultScheme", vaultScheme, "http", "https");
	}

	public String getVaultToken() {
		if (this.vaultToken == null && this.vaultTokenFactory == null) {
			throw new UnsupportedOperationException("vaultToken or vaultTokenFactory must not be null.");
		}

		return this.vaultToken == null ? this.vaultTokenFactory.getVaultToken() : this.vaultToken;
	}

	public void setVaultToken(String vaultToken) {
		this.vaultToken = vaultToken;
	}

	public void setVaultTokenFactory(VaultTokenFactory vaultTokenFactory) {
		this.vaultTokenFactory = vaultTokenFactory;
	}
}