package de.benjaminborbe.authorization.mock;

import java.util.Collection;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.AuthorizationServiceException;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.authorization.api.PermissionIdentifier;
import de.benjaminborbe.authorization.api.RoleIdentifier;

@Singleton
public class AuthorizationServiceMock implements AuthorizationService {

	@Inject
	public AuthorizationServiceMock() {
	}

	@Override
	public boolean hasRole(final SessionIdentifier sessionIdentifier, final RoleIdentifier roleIdentifier) throws AuthorizationServiceException {
		return false;
	}

	@Override
	public void expectRole(final SessionIdentifier sessionIdentifier, final RoleIdentifier roleIdentifier) throws AuthorizationServiceException, PermissionDeniedException {
	}

	@Override
	public boolean hasPermission(final SessionIdentifier sessionIdentifier, final PermissionIdentifier permissionIdentifier) throws AuthorizationServiceException {
		return false;
	}

	@Override
	public void expectPermission(final SessionIdentifier sessionIdentifier, final PermissionIdentifier permissionIdentifier) throws AuthorizationServiceException,
			PermissionDeniedException {
	}

	@Override
	public boolean createRole(final SessionIdentifier sessionIdentifier, final RoleIdentifier roleIdentifier) throws PermissionDeniedException, AuthorizationServiceException {
		return false;
	}

	@Override
	public RoleIdentifier createRoleIdentifier(final String rolename) {
		return null;
	}

	@Override
	public boolean addUserRole(final SessionIdentifier sessionIdentifier, final UserIdentifier userIdentifier, final RoleIdentifier roleIdentifier) throws PermissionDeniedException,
			AuthorizationServiceException {
		return false;
	}

	@Override
	public boolean removeUserRole(final SessionIdentifier sessionIdentifier, final UserIdentifier userIdentifier, final RoleIdentifier roleIdentifier)
			throws PermissionDeniedException, AuthorizationServiceException {
		return false;
	}

	@Override
	public boolean hasRole(final UserIdentifier userIdentifier, final RoleIdentifier roleIdentifier) throws AuthorizationServiceException {
		return false;
	}

	@Override
	public Collection<RoleIdentifier> roleList() {
		return null;
	}
}
