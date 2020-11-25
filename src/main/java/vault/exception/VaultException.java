package vault.exception;

import java.text.MessageFormat;

public class VaultException extends RuntimeException {
	private static final long serialVersionUID = -3463161799516213779L;

	public VaultException() {
		super();
	}

	public VaultException(String message) {
		super(message);
	}

	public VaultException(Throwable cause) {
		super(cause);
	}

	public VaultException(String message, Throwable cause) {
		super(message, cause);
	}

	public VaultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public VaultException(Throwable cause, String message, Object... messageParameters) {
		this(MessageFormat.format(message, messageParameters), cause);
	}
}