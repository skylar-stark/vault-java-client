package vault.rest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import vault.Vault;
import vault.exception.VaultApiException;
import vault.exception.VaultAuthorizationException;
import vault.exception.VaultException;

public class VaultOkHttp3RestClient implements VaultRestClient {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final OkHttpClient httpClient = new OkHttpClient();

	private String baseUrl;
	private String vaultToken;

	public VaultOkHttp3RestClient(Vault vault) {
		this(vault.getVaultAddress(), vault.getVaultToken());
	}

	public VaultOkHttp3RestClient(String baseUrl, String vaultToken) {
		this.baseUrl = baseUrl;
		this.vaultToken = vaultToken;
	}

	public static String getResponseBody(Response response, boolean onlyOnSuccess) throws VaultApiException {
		if (!onlyOnSuccess || response.isSuccessful()) {
			try (ResponseBody responseBody = response.body()) {
				return responseBody.string();
			} catch (IOException e) {
				throw new VaultApiException("Error getting body from Response.", e);
			}
		}

		if (isAuthorizationError(response)) {
			throw new VaultAuthorizationException("HTTP Request was not successful due to an authorization error. Received HTTP Status code " + response.code() + " with message " + response.message() + ".");
		}

		if (isClientError(response)) {
			throw new VaultApiException("HTTP Request was not successful due to a client error. Received HTTP Status code " + response.code() + " with message " + response.message() + ".");
		}

		if (isServerError(response)) {
			throw new VaultException("HTTP Request was not successful due to a server error. Received HTTP Status code " + response.code() + " with message " + response.message() + ".");
		}

		throw new VaultException("HTTP Request was not successful due to unknown error; received HTTP Status code " + response.code() + " with message " + response.message() + ".");
	}

	public static boolean isAuthorizationError(Response response) {
		return response.code() == 401 || response.code() == 403;
	}

	public static boolean isClientError(Response response) {
		return !isAuthorizationError(response) && response.code() >= 400 && response.code() < 500;
	}

	public static boolean isServerError(Response response) {
		return response.code() >= 500 && response.code() < 600;
	}

	public RequestBody createRequestBody(Object payload) {
		return RequestBody.create(writeObjectAsJson(payload), MediaType.get("application/json; charset=utf-8"));
	}

	@Override
	public VaultRestResponse<String> delete(VaultRestRequest vaultRequest) throws VaultApiException {
		Request.Builder requestBuilder = getOkHttp3RequestBuilder(vaultRequest);

		Request request;
		if (vaultRequest.getPayload() != null) {
			request = requestBuilder.delete(createRequestBody(vaultRequest.getPayload())).build();
		} else {
			request = requestBuilder.delete().build();
		}

		try (Response response = getResponse(request)) {
			return new VaultRestResponse<>(getResponseBody(response, vaultRequest.isRequireSuccessfulStatusCode()), response.code());
		}
	}

	@Override
	public <T> VaultRestResponse<T> delete(VaultRestRequest vaultRequest, Class<T> clazz) throws VaultApiException {
		Request.Builder requestBuilder = getOkHttp3RequestBuilder(vaultRequest);

		Request request;
		if (vaultRequest.getPayload() != null) {
			request = requestBuilder.delete(createRequestBody(vaultRequest.getPayload())).build();
		} else {
			request = requestBuilder.delete().build();
		}

		try (Response response = getResponse(request)) {
			return new VaultRestResponse<>(getResponseBody(response, vaultRequest.isRequireSuccessfulStatusCode(), clazz), response.code());
		}
	}

	@Override
	public VaultRestResponse<String> get(VaultRestRequest vaultRequest) throws VaultApiException {
		Request request = getOkHttp3RequestBuilder(vaultRequest).get().build();
		try (Response response = getResponse(request)) {
			return new VaultRestResponse<>(getResponseBody(response, vaultRequest.isRequireSuccessfulStatusCode()), response.code());
		}
	}

	@Override
	public <T> VaultRestResponse<T> get(VaultRestRequest vaultRequest, Class<T> clazz) throws VaultApiException {
		Request request = getOkHttp3RequestBuilder(vaultRequest).get().build();
		try (Response response = getResponse(request)) {
			return new VaultRestResponse<>(getResponseBody(response, vaultRequest.isRequireSuccessfulStatusCode(), clazz), response.code());
		}
	}

	@Override
	public String getBaseUrl() {
		return this.baseUrl;
	}

	private String getVaultToken() {
		return this.vaultToken;
	}

	private Request.Builder getOkHttp3RequestBuilder(VaultRestRequest vaultRequest) {
		if (vaultRequest == null) {
			throw new NullPointerException("VaultRestRequest cannot be null.");
		}

		HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(getBaseUrl() + vaultRequest.getPath()).newBuilder();
		if (vaultRequest.getQueryParameters() != null) {
			vaultRequest.getQueryParameters().forEach(httpUrlBuilder::setQueryParameter);
		}

		return new Request.Builder().url(httpUrlBuilder.build()).header("X-Vault-Token", getVaultToken());
	}

	private Response getResponse(Request request) throws VaultApiException {
		try {
			return this.httpClient.newCall(request).execute();
		} catch (IOException e) {
			throw new VaultApiException("Error executing request: " + request + ".", e);
		}
	}

	private <T> T getResponseBody(Response response, boolean onlyOnSuccess, Class<T> clazz) throws VaultApiException {
		try {
			return this.objectMapper.readValue(getResponseBody(response, onlyOnSuccess), clazz);
		} catch (IOException e) {
			throw new VaultApiException("Error translating Response body to Class '" + clazz + "'.", e);
		}
	}

	@Override
	public VaultRestResponse<String> head(VaultRestRequest vaultRequest) throws VaultApiException {
		Request request = getOkHttp3RequestBuilder(vaultRequest).head().build();
		try (Response response = getResponse(request)) {
			return new VaultRestResponse<>(getResponseBody(response, vaultRequest.isRequireSuccessfulStatusCode()), response.code());
		}
	}

	@Override
	public <T> VaultRestResponse<T> head(VaultRestRequest vaultRequest, Class<T> clazz) throws VaultApiException {
		Request request = getOkHttp3RequestBuilder(vaultRequest).head().build();
		try (Response response = getResponse(request)) {
			return new VaultRestResponse<>(getResponseBody(response, vaultRequest.isRequireSuccessfulStatusCode(), clazz), response.code());
		}
	}

	@Override
	public VaultRestResponse<String> patch(VaultRestRequest vaultRequest) throws VaultApiException {
		Request request = getOkHttp3RequestBuilder(vaultRequest).patch(createRequestBody(vaultRequest.getPayload())).build();
		try (Response response = getResponse(request)) {
			return new VaultRestResponse<>(getResponseBody(response, vaultRequest.isRequireSuccessfulStatusCode()), response.code());
		}
	}

	@Override
	public <T> VaultRestResponse<T> patch(VaultRestRequest vaultRequest, Class<T> clazz) throws VaultApiException {
		Request request = getOkHttp3RequestBuilder(vaultRequest).patch(createRequestBody(vaultRequest.getPayload())).build();
		try (Response response = getResponse(request)) {
			return new VaultRestResponse<>(getResponseBody(response, vaultRequest.isRequireSuccessfulStatusCode(), clazz), response.code());
		}
	}

	@Override
	public VaultRestResponse<String> post(VaultRestRequest vaultRequest) throws VaultApiException {
		Request request = getOkHttp3RequestBuilder(vaultRequest).post(createRequestBody(vaultRequest.getPayload())).build();
		try (Response response = getResponse(request)) {
			return new VaultRestResponse<>(getResponseBody(response, vaultRequest.isRequireSuccessfulStatusCode()), response.code());
		}
	}

	@Override
	public <T> VaultRestResponse<T> post(VaultRestRequest vaultRequest, Class<T> clazz) throws VaultApiException {
		Request request = getOkHttp3RequestBuilder(vaultRequest).post(createRequestBody(vaultRequest.getPayload())).build();
		try (Response response = getResponse(request)) {
			return new VaultRestResponse<>(getResponseBody(response, vaultRequest.isRequireSuccessfulStatusCode(), clazz), response.code());
		}
	}

	@Override
	public VaultRestResponse<String> put(VaultRestRequest vaultRequest) throws VaultApiException {
		Request request = getOkHttp3RequestBuilder(vaultRequest).put(createRequestBody(vaultRequest.getPayload())).build();
		try (Response response = getResponse(request)) {
			return new VaultRestResponse<>(getResponseBody(response, vaultRequest.isRequireSuccessfulStatusCode()), response.code());
		}
	}

	@Override
	public <T> VaultRestResponse<T> put(VaultRestRequest vaultRequest, Class<T> clazz) throws VaultApiException {
		Request request = getOkHttp3RequestBuilder(vaultRequest).put(createRequestBody(vaultRequest.getPayload())).build();
		try (Response response = getResponse(request)) {
			return new VaultRestResponse<>(getResponseBody(response, vaultRequest.isRequireSuccessfulStatusCode(), clazz), response.code());
		}
	}

	private String writeObjectAsJson(Object object) {
		try {
			this.log.trace("Writing entity {} as JSON String", object);
			String jsonString = this.objectMapper.writeValueAsString(object);
			this.log.trace("JSON String: {}", jsonString);
			return jsonString;
		} catch (JsonProcessingException e) {
			throw new VaultException("Error converting payload to JSON.", e);
		}
	}
}