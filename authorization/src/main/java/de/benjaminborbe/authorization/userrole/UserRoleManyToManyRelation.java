package de.benjaminborbe.authorization.userrole;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.authorization.api.RoleIdentifier;
import de.benjaminborbe.storage.api.StorageService;
import de.benjaminborbe.storage.tools.ManyToManyRelationStorage;

@Singleton
public class UserRoleManyToManyRelation extends ManyToManyRelationStorage<UserIdentifier, RoleIdentifier> {

	private static final String COLUMN_FAMILY = "user_role";

	@Inject
	public UserRoleManyToManyRelation(final Logger logger, final StorageService storageService) {
		super(logger, storageService);
	}

	@Override
	protected String getColumnFamily() {
		return COLUMN_FAMILY;
	}
}
