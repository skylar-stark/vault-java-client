package vault.rest;

public class VaultRestResponse<T> {

	private T body;
	private int statusCode;

	public VaultRestResponse(T body, int statusCode) {
		this.body = body;
		this.statusCode = statusCode;
	}

	public T getBody() {
		return this.body;
	}

	public int getStatusCode() {
		return this.statusCode;
	}
}