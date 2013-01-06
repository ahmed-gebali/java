package de.benjaminborbe.authorization.dao;

import java.util.Calendar;

import de.benjaminborbe.authorization.api.Role;
import de.benjaminborbe.authorization.api.RoleIdentifier;
import de.benjaminborbe.storage.tools.EntityBase;
import de.benjaminborbe.storage.tools.HasCreated;
import de.benjaminborbe.storage.tools.HasModified;

public class RoleBean extends EntityBase<RoleIdentifier> implements Role, HasCreated, HasModified {

	private static final long serialVersionUID = 5954692477523378479L;

	private Calendar modified;

	private Calendar created;

	@Override
	public String getName() {
		return getId() != null ? getId().getId() : null;
	}

	@Override
	public Calendar getModified() {
		return modified;
	}

	@Override
	public void setModified(final Calendar modified) {
		this.modified = modified;
	}

	@Override
	public Calendar getCreated() {
		return created;
	}

	@Override
	public void setCreated(final Calendar created) {
		this.created = created;
	}

}
