package vault;

import vault.exception.VaultHealthException;
import vault.token.VaultTokenFactory;

public interface Vault {

	public abstract String getVaultAddress();
	public abstract String getVaultToken();
	public abstract boolean isInitialized();
	public abstract boolean isSealed();
	public abstract void setVaultToken(String vaultToken);
	public abstract void setVaultToken(VaultTokenFactory vaultTokenFactory);
	public abstract void updateHealth() throws VaultHealthException;
	public abstract void updateMounts();
}