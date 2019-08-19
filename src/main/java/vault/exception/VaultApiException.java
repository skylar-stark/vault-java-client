package vault.exception;

public class VaultApiException extends Exception {
	private static final long serialVersionUID = -8217666957831031063L;

	public VaultApiException() {
		super();
	}

	public VaultApiException(String message) {
		super(message);
	}

	public VaultApiException(Throwable cause) {
		super(cause);
	}

	public VaultApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public VaultApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}