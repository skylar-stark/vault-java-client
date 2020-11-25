package vault.exception;

public class VaultAuthorizationException extends RuntimeException {
	private static final long serialVersionUID = 6921910884067849846L;

	public VaultAuthorizationException() {
		super();
	}

	public VaultAuthorizationException(String message) {
		super(message);
	}

	public VaultAuthorizationException(Throwable cause) {
		super(cause);
	}

	public VaultAuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	public VaultAuthorizationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}