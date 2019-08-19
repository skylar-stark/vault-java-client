package vault.token;

@FunctionalInterface
public interface VaultTokenFactory {

	public abstract String getVaultToken();
}