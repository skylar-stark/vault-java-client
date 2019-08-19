package vault.rest;

import vault.exception.VaultApiException;
import vault.exception.VaultAuthorizationException;

public interface VaultRestClient {

	public abstract String getBaseUrl();
	public abstract VaultRestResponse<String> get(VaultRestRequest vaultRequest) throws VaultApiException, VaultAuthorizationException;
	public abstract <T> VaultRestResponse<T> get(VaultRestRequest vaultRequest, Class<T> clazz) throws VaultApiException, VaultAuthorizationException;
	public abstract VaultRestResponse<String> post(VaultRestRequest vaultRequest) throws VaultApiException, VaultAuthorizationException;
	public abstract <T> VaultRestResponse<T> post(VaultRestRequest vaultRequest, Class<T> clazz) throws VaultApiException, VaultAuthorizationException;
	public abstract VaultRestResponse<String> put(VaultRestRequest vaultRequest) throws VaultApiException, VaultAuthorizationException;
	public abstract <T> VaultRestResponse<T> put(VaultRestRequest vaultRequest, Class<T> clazz) throws VaultApiException, VaultAuthorizationException;
	public abstract VaultRestResponse<String> delete(VaultRestRequest vaultRequest) throws VaultApiException, VaultAuthorizationException;
	public abstract <T> VaultRestResponse<T> delete(VaultRestRequest vaultRequest, Class<T> clazz) throws VaultApiException, VaultAuthorizationException;
	public abstract VaultRestResponse<String> head(VaultRestRequest vaultRequest) throws VaultApiException, VaultAuthorizationException;
	public abstract <T> VaultRestResponse<T> head(VaultRestRequest vaultRequest, Class<T> clazz) throws VaultApiException, VaultAuthorizationException;
	public abstract VaultRestResponse<String> patch(VaultRestRequest vaultRequest) throws VaultApiException, VaultAuthorizationException;
	public abstract <T> VaultRestResponse<T> patch(VaultRestRequest vaultRequest, Class<T> clazz) throws VaultApiException, VaultAuthorizationException;
}