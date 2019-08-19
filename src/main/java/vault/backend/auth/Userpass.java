package vault.backend.auth;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import vault.Vault;
import vault.backend.BackendConfig;
import vault.backend.Backends;
import vault.backend.VaultBackend;
import vault.domain.auth.KeyList;
import vault.domain.auth.VaultAuthentication;
import vault.domain.auth.userpass.UserpassCreate;
import vault.domain.response.HttpAuth;
import vault.domain.response.HttpResponse.GenericHttpResponse;
import vault.domain.response.HttpResponse.VaultAuthenticationHttpResponse;
import vault.domain.response.HttpResponse.KeyListHttpResponse;
import vault.domain.response.MountOutput;
import vault.exception.VaultApiException;
import vault.exception.VaultAuthorizationException;

public class Userpass extends VaultBackend {
	private static final String USERPASS_LOGIN_API_PATH = "/v1/auth/%s/login/%%s";
	private static final String USERPASS_USERS_API_PATH = "/v1/auth/%s/users/%%s";
	private static final String USERPASS_USERS_PASSWORD_API_PATH = "/v1/auth/%s/users/%%s/password";
	private static final String USERPASS_USERS_POLICIES_API_PATH = "/v1/auth/%s/users/%%s/policies";

	private String userpassLoginApiPath;
	private String userpassUsersApiPath;
	private String userpassUsersPasswordApiPath;
	private String userpassUsersPoliciesApiPath;

	public Userpass(String accessor, BackendConfig config, String description, boolean local, String name, boolean sealWrap, Vault vault) {
		super(accessor, config, description, local, name, sealWrap, Backends.USERPASS, vault);

		this.userpassLoginApiPath = String.format(USERPASS_LOGIN_API_PATH, this.name);
		this.userpassUsersApiPath = String.format(USERPASS_USERS_API_PATH, this.name);
		this.userpassUsersPasswordApiPath = String.format(USERPASS_USERS_PASSWORD_API_PATH, this.name);
		this.userpassUsersPoliciesApiPath = String.format(USERPASS_USERS_POLICIES_API_PATH, this.name);
	}

	public static Userpass from(MountOutput mountOutput, String name, Vault vault) {
		return new Userpass(mountOutput.getAccessor(), mountOutput.getConfig() != null ? BackendConfig.from(mountOutput.getConfig()) : null, mountOutput.getDescription(), mountOutput.isLocal(), name, mountOutput.isSealWrap(), vault);
	}

	public boolean createUser(String username, UserpassCreate userpassCreate) {
		try {
			post(this.getUserpassUsersApiPath(username), userpassCreate);
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error creating user with username {}.", username, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to create user with username {}.", username, e);
		}

		return false;
	}

	public boolean updateUser(String username, UserpassCreate userpassCreate) {
		try {
			post(this.getUserpassUsersApiPath(username), userpassCreate);
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error updating user with username {}.", username, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to update user with username {}.", username, e);
		}

		return false;
	}

	public boolean updatePassword(String username, String password) {
		try {
			post(this.getUserpassUsersPasswordApiPath(username), Collections.singletonMap("password", password));
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error updating password for username {}.", username, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to update password for username {}.", username, e);
		}

		return false;
	}

	public boolean updatePolicies(String username, List<String> policies) {
		try {
			post(this.getUserpassUsersPoliciesApiPath(username), Collections.singletonMap("policies", policies));
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error updating policies for username {}.", username, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to update policies for username {}.", username, e);
		}

		return false;
	}

	public VaultAuthentication readUser(String username) {
		try {
			return get(this.getUserpassUsersPoliciesApiPath(username), VaultAuthenticationHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			this.log.error("Error reading user {}.", username, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to read user {}.", username, e);
		}

		return null;
	}

	public boolean updatePolicies(String username, String... policies) {
		return updatePolicies(username, Arrays.asList(policies));
	}

	public boolean deleteUser(String username) {
		try {
			delete(this.getUserpassUsersApiPath(username));
			return true;
		} catch (VaultApiException e) {
			this.log.error("Error deleting user with username {}.", username, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to delete user with username {}.", username, e);
		}

		return false;
	}

	public KeyList listUsers() {
		try {
			return get(this.getUserpassUsersApiPath(""), Collections.singletonMap("list", "true"), KeyListHttpResponse.class).getBody().getData();
		} catch (VaultApiException e) {
			this.log.error("Error listing users.", e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Token is not authorized to list users.", e);
		}

		return null;
	}

	public HttpAuth login(String username, String password) {
		try {
			return post(this.getUserpassLoginApiPath(username), Collections.singletonMap("password", password), GenericHttpResponse.class).getBody().getAuth();
		} catch (VaultApiException e) {
			this.log.error("Error logging in with username {}.", username, e);
		} catch (VaultAuthorizationException e) {
			this.log.error("Login is not authorized for username {}.", username, e);
		}

		return null;
	}

	private String getUserpassLoginApiPath(String username) {
		return String.format(this.userpassLoginApiPath, username);
	}

	private String getUserpassUsersApiPath(String username) {
		return String.format(this.userpassUsersApiPath, username);
	}

	private String getUserpassUsersPasswordApiPath(String username) {
		return String.format(this.userpassUsersPasswordApiPath, username);
	}

	private String getUserpassUsersPoliciesApiPath(String username) {
		return String.format(this.userpassUsersPoliciesApiPath, username);
	}
}