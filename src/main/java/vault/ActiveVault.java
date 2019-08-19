package vault;

import static vault.util.Util.removeTrailingSlash;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import vault.backend.auth.Token;
import vault.backend.auth.Userpass;
import vault.backend.secret.System;
import vault.backend.secret.generic.KV;
import vault.backend.secret.generic.SSH;
import vault.domain.response.HealthResponse;
import vault.domain.response.MountOutput;
import vault.exception.VaultException;
import vault.exception.VaultHealthException;
import vault.token.VaultTokenFactory;

public final class ActiveVault implements Vault {
	private Set<KV> kvBackends = new HashSet<>();
	private Set<SSH> sshBackends = new HashSet<>();
	private Set<Token> tokenBackends = new HashSet<>();
	private Set<Userpass> usepassBackends = new HashSet<>();
	private String vaultAddress;
	private HealthResponse vaultHealth;
	private String vaultToken;
	private System system;

	private ActiveVault(String vaultAddress, String vaultToken) {
		if (!(vaultAddress.startsWith("http://") || vaultAddress.startsWith("https://"))) {
			throw new IllegalArgumentException("Vault Address must start with 'http[s]://'");
		}

		this.vaultAddress = removeTrailingSlash(vaultAddress);
		this.vaultToken = vaultToken;
	}

	private ActiveVault(String vaultAddress, VaultTokenFactory vaultTokenFactory) {
		this(vaultAddress, vaultTokenFactory.getVaultToken());
	}

	private ActiveVault(VaultConfiguration vaultConfiguration) {
		this(vaultConfiguration.getVaultAddress(), vaultConfiguration.getVaultToken());
	}

	private ActiveVault(Vault vault) {
		this(vault.getVaultAddress(), vault.getVaultToken());
	}

	public static ActiveVault newAndInitialize(String vaultAddress, String vaultToken) throws VaultHealthException {
		ActiveVault vault = new ActiveVault(vaultAddress, vaultToken);
		vault.updateHealth();
		vault.updateMounts();
		return vault;
	}

	public static ActiveVault newAndInitialize(String vaultAddress, VaultTokenFactory vaultTokenFactory) throws VaultHealthException {
		ActiveVault vault = new ActiveVault(vaultAddress, vaultTokenFactory);
		vault.updateHealth();
		vault.updateMounts();
		return vault;
	}

	public static ActiveVault newAndInitialize(VaultConfiguration vaultConfiguration) throws VaultHealthException {
		ActiveVault vault = new ActiveVault(vaultConfiguration);
		vault.updateHealth();
		vault.updateMounts();
		return vault;
	}

	public static ActiveVault newAndInitialize(Vault vault) throws VaultHealthException {
		ActiveVault newVault = new ActiveVault(vault);
		newVault.updateHealth();
		newVault.updateMounts();
		return newVault;
	}

	public KV getKVBackend(String name) {
		return this.kvBackends.stream().filter(kv -> kv.getName().equals(name)).findFirst().orElseThrow(() -> new VaultException("KV Backend '" + name + "' does not exist or is not accessible with the current token."));
	}

	@Override
	public String getVaultAddress() {
		return this.vaultAddress;
	}

	public HealthResponse getVaultHealth() {
		return this.vaultHealth;
	}

	@Override
	public String getVaultToken() {
		return this.vaultToken;
	}

	@Override
	public boolean isInitialized() {
		return true;
	}

	@Override
	public boolean isSealed() {
		return false;
	}

	@Override
	public void setVaultToken(String vaultToken) {
		this.vaultToken = vaultToken;
		updateMounts();
	}

	@Override
	public void setVaultToken(VaultTokenFactory vaultTokenFactory) {
		this.vaultToken = vaultTokenFactory.getVaultToken();
	}

	private void clearBackends() {
		this.system = null;
		this.kvBackends.clear();
		this.tokenBackends.clear();
	}

	@Override
	public void updateHealth() throws VaultHealthException {
		this.vaultHealth = this.system == null ? System.health(this.vaultAddress) : this.system.health(true, true, null, null, null, null, null, null);
		if (!this.vaultHealth.isInitialized()) {
			throw new VaultException("Specified Vault is not initialized.");
		}
		if (this.vaultHealth.isSealed()) {
			throw new VaultException("Specified Vault is sealed.");
		}
	}

	@Override
	public void updateMounts() {
		updateBackends(System.internalUiMounts(this.vaultAddress, this.vaultToken));
	}

	private void updateBackends(Map<String, Map<String, MountOutput>> mounts) {
		clearBackends();
		if (mounts != null) {
			updateAuthBackends(mounts.get("auth"));
			updateSecretBackends(mounts.get("secret"));
		}
	}

	private void updateAuthBackends(Map<String, MountOutput> authBackends) {
		if (authBackends != null) {
			authBackends.values().removeIf(Objects::isNull);
			authBackends.values().removeIf(v -> v.getType() == null);

			for (Entry<String, MountOutput> authEntry : authBackends.entrySet()) {
				String key = authEntry.getKey();
				MountOutput value = authEntry.getValue();
				String type = value.getType().toLowerCase(Locale.ENGLISH);
				switch (type) {
					case "token":
						this.tokenBackends.add(Token.from(value, removeTrailingSlash(key), this));
						break;
					case "userpass":
						this.usepassBackends.add(Userpass.from(value, removeTrailingSlash(key), this));
						break;
					default:
						break;
				}
			}
		}
	}

	private void updateSecretBackends(Map<String, MountOutput> secretBackends) {
		if (secretBackends != null) {
			secretBackends.values().removeIf(Objects::isNull);
			secretBackends.values().removeIf(v -> v.getType() == null);

			for (Entry<String, MountOutput> secretsEntry : secretBackends.entrySet()) {
				String key = secretsEntry.getKey();
				MountOutput value = secretsEntry.getValue();
				String type = value.getType().toLowerCase(Locale.ENGLISH);
				switch (type) {
					case "kv":
						this.kvBackends.add(KV.from(value, removeTrailingSlash(key), this));
						break;
					case "system":
						this.system = System.from(value, removeTrailingSlash(key), this);
						break;
					case "ssh":
						this.sshBackends.add(SSH.from(value, removeTrailingSlash(key), this));
						break;
					default:
						break;
				}
			}
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.vaultAddress, this.vaultToken);
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
		ActiveVault other = (ActiveVault) obj;
		return Objects.equals(this.vaultAddress, other.vaultAddress) && Objects.equals(this.vaultToken, other.vaultToken);
	}

	@Override
	public String toString() {
		return "Vault [vaultAddress=" + this.vaultAddress + ", vaultToken=" + this.vaultToken + "]";
	}
}