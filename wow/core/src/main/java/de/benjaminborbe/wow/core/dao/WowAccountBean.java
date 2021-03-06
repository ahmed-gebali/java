package de.benjaminborbe.wow.core.dao;

import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.storage.tools.EntityBase;
import de.benjaminborbe.storage.tools.HasCreated;
import de.benjaminborbe.storage.tools.HasModified;

import java.util.Calendar;

public class WowAccountBean extends EntityBase<WowAccountIdentifier> implements WowAccount, HasCreated, HasModified {

	private static final long serialVersionUID = 4613788971187238396L;

	private WowAccountIdentifier id;

	private String account;

	private String password;

	private String email;

	private UserIdentifier owner;

	private Calendar modified;

	private Calendar created;

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getAccount() {
		return account;
	}

	public void setAccount(final String account) {
		this.account = account;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@Override
	public UserIdentifier getOwner() {
		return owner;
	}

	public void setOwner(final UserIdentifier owner) {
		this.owner = owner;
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

	@Override
	public WowAccountIdentifier getId() {
		return id;
	}

	@Override
	public void setId(final WowAccountIdentifier id) {
		this.id = id;
	}

}
