package vault.exception;

public class VaultHealthException extends Exception {
	private static final long serialVersionUID = 1871341823616624408L;

	public VaultHealthException() {
		super();
	}

	public VaultHealthException(String message) {
		super(message);
	}

	public VaultHealthException(Throwable cause) {
		super(cause);
	}

	public VaultHealthException(String message, Throwable cause) {
		super(message, cause);
	}

	public VaultHealthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}