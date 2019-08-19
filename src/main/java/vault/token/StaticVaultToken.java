package vault.token;

public class StaticVaultToken implements VaultTokenFactory {

	private String token;

	public StaticVaultToken(String token) {
		this.token = token;
	}

	@Override
	public String getVaultToken() {
		return this.token;
	}
}