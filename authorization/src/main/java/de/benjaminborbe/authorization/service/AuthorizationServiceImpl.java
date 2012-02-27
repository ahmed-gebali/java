package de.benjaminborbe.authorization.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.api.AuthenticationServiceException;
import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.AuthorizationServiceException;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.authorization.api.PermissionIdentifier;
import de.benjaminborbe.authorization.api.RoleIdentifier;
import de.benjaminborbe.authorization.role.RoleBean;
import de.benjaminborbe.authorization.role.RoleDao;
import de.benjaminborbe.authorization.userrole.UserRoleManyToManyRelation;
import de.benjaminborbe.storage.api.StorageException;

@Singleton
public class AuthorizationServiceImpl implements AuthorizationService {

	private static final String ADMIN_USERNAME = "admin";

	private static final String ADMIN_ROLE = "admin";

	private final Logger logger;

	private final RoleDao roleDao;

	private final AuthenticationService authenticationService;

	private final UserRoleManyToManyRelation userRoleManyToManyRelation;

	@Inject
	public AuthorizationServiceImpl(
			final Logger logger,
			final AuthenticationService authenticationService,
			final RoleDao roleDao,
			final UserRoleManyToManyRelation userRoleManyToManyRelation) {
		this.logger = logger;
		this.authenticationService = authenticationService;
		this.roleDao = roleDao;
		this.userRoleManyToManyRelation = userRoleManyToManyRelation;
	}

	@Override
	public boolean hasRole(final SessionIdentifier sessionIdentifier, final RoleIdentifier roleIdentifier) throws AuthorizationServiceException {
		try {
			logger.trace("hasRole " + roleIdentifier);
			final UserIdentifier userIdentifier = authenticationService.getCurrentUser(sessionIdentifier);
			if (userIdentifier == null) {
				return false;
			}
			return hasRole(userIdentifier, roleIdentifier);
		}
		catch (final AuthenticationServiceException e) {
			throw new AuthorizationServiceException("StorageException", e);
		}
	}

	@Override
	public boolean hasPermission(final SessionIdentifier sessionIdentifier, final PermissionIdentifier permissionIdentifier) throws AuthorizationServiceException {
		try {
			final UserIdentifier userIdentifier = authenticationService.getCurrentUser(sessionIdentifier);
			if (userIdentifier == null) {
				return false;
			}
			final String username = userIdentifier.getId();
			return ADMIN_USERNAME.equals(username);
		}
		catch (final AuthenticationServiceException e) {
			throw new AuthorizationServiceException("AuthenticationServiceException", e);
		}
	}

	@Override
	public void expectRole(final SessionIdentifier sessionIdentifier, final RoleIdentifier roleIdentifier) throws AuthorizationServiceException, PermissionDeniedException {
		if (!hasRole(sessionIdentifier, roleIdentifier)) {
			throw new PermissionDeniedException("no role " + roleIdentifier);
		}
	}

	@Override
	public void expectPermission(final SessionIdentifier sessionIdentifier, final PermissionIdentifier permissionIdentifier) throws AuthorizationServiceException,
			PermissionDeniedException {
		if (!hasPermission(sessionIdentifier, permissionIdentifier)) {
			throw new PermissionDeniedException("no permission " + permissionIdentifier);
		}
	}

	@Override
	public boolean createRole(final SessionIdentifier sessionIdentifier, final RoleIdentifier roleIdentifier) throws PermissionDeniedException, AuthorizationServiceException {
		try {
			expectPermission(sessionIdentifier, new PermissionIdentifier("createRole"));

			if (roleDao.findByRolename(roleIdentifier) != null) {
				logger.info("role " + roleIdentifier + " allready exists");
				return false;
			}
			final RoleBean role = roleDao.findOrCreateByRolename(roleIdentifier);
			roleDao.save(role);
			return true;
		}
		catch (final StorageException e) {
			throw new AuthorizationServiceException("StorageException", e);
		}
	}

	@Override
	public boolean addUserRole(final SessionIdentifier sessionIdentifier, final UserIdentifier userIdentifier, final RoleIdentifier roleIdentifier) throws PermissionDeniedException,
			AuthorizationServiceException {
		try {
			expectRole(sessionIdentifier, new RoleIdentifier(ADMIN_ROLE));

			logger.info("addUserRole " + userIdentifier + " " + roleIdentifier);
			userRoleManyToManyRelation.add(userIdentifier, roleIdentifier);

			return true;
		}
		catch (final StorageException e) {
			throw new AuthorizationServiceException("StorageException", e);
		}
	}

	@Override
	public boolean removeUserRole(final SessionIdentifier sessionIdentifier, final UserIdentifier userIdentifier, final RoleIdentifier roleIdentifier)
			throws PermissionDeniedException, AuthorizationServiceException {
		try {
			expectRole(sessionIdentifier, new RoleIdentifier(ADMIN_ROLE));

			logger.info("removeUserRole " + userIdentifier + " " + roleIdentifier);
			userRoleManyToManyRelation.remove(userIdentifier, roleIdentifier);

			return true;
		}
		catch (final StorageException e) {
			throw new AuthorizationServiceException("StorageException", e);
		}
	}

	@Override
	public RoleIdentifier createRoleIdentifier(final String rolename) {
		return new RoleIdentifier(rolename);
	}

	@Override
	public boolean hasRole(final UserIdentifier userIdentifier, final RoleIdentifier roleIdentifier) throws AuthorizationServiceException {
		try {
			final RoleBean role = roleDao.findByRolename(roleIdentifier);
			if (role == null) {
				return false;
			}
			if (userRoleManyToManyRelation.exists(userIdentifier, roleIdentifier)) {
				return true;
			}
			final String username = userIdentifier.getId();
			return ADMIN_USERNAME.equals(username);
		}
		catch (final StorageException e) {
			throw new AuthorizationServiceException("StorageException", e);
		}
	}

	@Override
	public Collection<RoleIdentifier> roleList() throws AuthorizationServiceException {
		try {
			final Set<RoleIdentifier> result = new HashSet<RoleIdentifier>();
			for (final RoleBean role : roleDao.getAll()) {
				result.add(role.getId());
			}
			return result;
		}
		catch (final StorageException e) {
			throw new AuthorizationServiceException("StorageException", e);
		}
	}
}
