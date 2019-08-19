package vault.backend.auth;

import java.util.Collections;

import vault.Vault;
import vault.backend.BackendConfig;
import vault.backend.Backends;
import vault.backend.VaultBackend;
import vault.domain.auth.KeyList;
import vault.domain.auth.token.TokenCreateRequest;
import vault.domain.auth.token.TokenLookupResponse;
import vault.domain.response.HttpAuth;
import vault.domain.response.HttpResponse;
import vault.domain.response.HttpResponse.GenericHttpResponse;
import vault.domain.response.HttpResponse.KeyListHttpResponse;
import vault.domain.response.HttpResponse.TokenLookupResponseHttpResponse;
import vault.domain.response.MountOutput;
import vault.exception.VaultApiException;
import vault.exception.VaultAuthorizationException;

public final class Token extends VaultBackend {
	private static final String TIDY_TOKEN_API_PATH = "/v1/auth/%s/tidy";
	private static final String TOKEN_ACCESSORS_API_PATH = "/v1/auth/%s/accessors";
	private static final String TOKEN_CREATE_API_PATH = "/v1/auth/%s/create";
	private static final String TOKEN_CREATE_ORPHAN_API_PATH = "/v1/auth/%s/create-orphan";
	private static final String TOKEN_CREATE_ROLE_API_PATH = "/v1/auth/%s/create/%%s";
	private static final String TOKEN_LOOKUP_API_PATH = "/v1/auth/%s/lookup";
	private static final String TOKEN_LOOKUP_ACCESSOR_API_PATH = "/v1/auth/%s/lookup-accessor";
	private static final String TOKEN_LOOKUP_SELF_API_PATH = "/v1/auth/%s/lookup-self";
	private static final String TOKEN_ROLES_API_PATH = "/v1/auth/%s/roles/%%s";

	private String tidyTokenApiPath;
	private String tokenAccessorsApiPath;
	private String tokenCreateApiPath;
	private String tokenCreateOrphanApiPath;
	private String tokenCreateRoleApiPath;
	private String tokenLookupAccessorApiPath;
	private String tokenLookupApiPath;
	private String tokenLookupSelfApiPath;
	private String tokenRolesApiPath;

	public Token(String accessor, BackendConfig config, String description, boolean local, String name, boolean sealWrap, Vault vault) {
		super(accessor, config, description, local, name, sealWrap, Backends.TOKEN, vault);

		this.tidyTokenApiPath = String.format(TIDY_TOKEN_API_PATH, this.name);
		this.tokenAccessorsApiPath = String.format(TOKEN_ACCESSORS_API_PATH, this.name);
		this.tokenCreateApiPath = String.format(TOKEN_CREATE_API_PATH, this.name);
		this.tokenCreateOrphanApiPath = String.format(TOKEN_CREATE_ORPHAN_API_PATH, this.name);
		this.tokenCreateRoleApiPath = String.format(TOKEN_CREATE_ROLE_API_PATH, this.name);
		this.tokenLookupAccessorApiPath = String.format(TOKEN_LOOKUP_ACCESSOR_API_PATH, this.name);
		this.tokenLookupApiPath = String.format(TOKEN_LOOKUP_API_PATH, this.name);
		this.tokenLookupSelfApiPath = String.format(TOKEN_LOOKUP_SELF_API_PATH, this.name);
		this.tokenRolesApiPath = String.format(TOKEN_ROLES_API_PATH, this.name);
	}

	public static Token from(MountOutput mountOutput, String name, Vault vault) {
		return new Token(mountOutput.getAccessor(), mountOutput.getConfig() != null ? BackendConfig.from(mountOutput.getConfig()) : null, mountOutput.getDescription(), mountOutput.isLocal(), name, mountOutput.isSealWrap(), vault);
	}

	public HttpAuth createOrphanToken(TokenCreateRequest tokenCreateRequest) {
		return createToken(tokenCreateRequest, true);
	}

	public HttpAuth createToken(TokenCreateRequest tokenCreateRequest, boolean orphan) {
		try {
			return post(orphan ? this.getTokenCreateOrphanApiPath() : this.getTokenCreateApiPath(), tokenCreateRequest, GenericHttpResponse.class).getBody().getAuth();
		} catch (VaultApiException e) {
			this.log.error("Error creating {}token.", orphan ? "orphan " : "", e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to create {}token.", orphan ? "orphan " : "", e);
		}

		return null;
	}

	public HttpAuth createToken(TokenCreateRequest tokenCreateRequest) {
		return createToken(tokenCreateRequest, false);
	}

	public HttpAuth createToken(TokenCreateRequest tokenCreateRequest, String role) {
		try {
			return post(this.getTokenCreateRoleApiPath(role), tokenCreateRequest, GenericHttpResponse.class).getBody().getAuth();
		} catch (VaultApiException e) {
			this.log.error("Error creating token with role {}.", role, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to create token with role {}.", role, e);
		}

		return null;
	}

	public TokenLookupResponse lookupToken(String clientToken) {
		try {
			return post(this.getTokenLookupApiPath(), Collections.singletonMap("token", clientToken), TokenLookupResponseHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			this.log.error("Error looking up client token {}.", clientToken, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to lookup client token {}.", clientToken, e);
		}

		return null;
	}

	public KeyList listTokenAccessors() {
		try {
			return get(this.getTokenAccessorsApiPath(), Collections.singletonMap("list", "true"), KeyListHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			this.log.error("Error listing token accessors.", e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to list token accessors.", e);
		}

		return null;
	}

	public KeyList listTokenRoles() {
		try {
			return get(this.getTokenRolesApiPath(""), Collections.singletonMap("list", "true"), KeyListHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			this.log.error("Error listing token roles.", e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to list token roles.", e);
		}

		return null;
	}

	public TokenLookupResponse lookupTokenSelf() {
		try {
			return get(this.getTokenLookupSelfApiPath(), TokenLookupResponseHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			this.log.error("Error looking up token (self).", e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to lookup token (self).", e);
		}

		return null;
	}

	public TokenLookupResponse lookupTokenAccessor(String tokenAccessor) {
		try {
			return post(this.getTokenLookupAccessorApiPath(), Collections.singletonMap("accessor", tokenAccessor), TokenLookupResponseHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			this.log.error("Error looking up token accessor {}.", tokenAccessor, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to lookup token accessor {}.", tokenAccessor, e);
		}

		return null;
	}

	public HttpResponse<Object> tidyTokens() {
		try {
			return post(this.getTidyTokenApiPath(), null, GenericHttpResponse.class).getBody();
		} catch (VaultApiException e) {
			this.log.error("Error while tidying tokens.", e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to tidy tokens.", e);
		}

		return null;
	}

	private String getTidyTokenApiPath() {
		return this.tidyTokenApiPath;
	}

	private String getTokenAccessorsApiPath() {
		return this.tokenAccessorsApiPath;
	}

	private String getTokenCreateApiPath() {
		return this.tokenCreateApiPath;
	}

	private String getTokenCreateOrphanApiPath() {
		return this.tokenCreateOrphanApiPath;
	}

	private String getTokenCreateRoleApiPath(String role) {
		return String.format(this.tokenCreateRoleApiPath, role);
	}

	private String getTokenLookupAccessorApiPath() {
		return this.tokenLookupAccessorApiPath;
	}

	private String getTokenLookupApiPath() {
		return this.tokenLookupApiPath;
	}

	private String getTokenLookupSelfApiPath() {
		return this.tokenLookupSelfApiPath;
	}

	private String getTokenRolesApiPath(String path) {
		return String.format(this.tokenRolesApiPath, path);
	}
}