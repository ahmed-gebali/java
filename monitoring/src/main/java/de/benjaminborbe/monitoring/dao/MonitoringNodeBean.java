package de.benjaminborbe.monitoring.dao;

import java.util.Calendar;
import java.util.Map;

import de.benjaminborbe.monitoring.api.MonitoringCheckType;
import de.benjaminborbe.monitoring.api.MonitoringNodeIdentifier;
import de.benjaminborbe.storage.tools.EntityBase;
import de.benjaminborbe.storage.tools.HasCreated;
import de.benjaminborbe.storage.tools.HasModified;

public class MonitoringNodeBean extends EntityBase<MonitoringNodeIdentifier> implements HasCreated, HasModified {

	private static final long serialVersionUID = -8803301003126328406L;

	private MonitoringNodeIdentifier id;

	private String name;

	private Calendar created;

	private Calendar modified;

	private MonitoringCheckType checkType;

	private Map<String, String> parameter;

	private Boolean active;

	private Boolean silent;

	private String message;

	private Boolean result;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
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
	public Calendar getModified() {
		return modified;
	}

	@Override
	public void setModified(final Calendar modified) {
		this.modified = modified;
	}

	@Override
	public MonitoringNodeIdentifier getId() {
		return id;
	}

	@Override
	public void setId(final MonitoringNodeIdentifier id) {
		this.id = id;
	}

	public MonitoringCheckType getCheckType() {
		return checkType;
	}

	public void setCheckType(final MonitoringCheckType checkType) {
		this.checkType = checkType;
	}

	public Map<String, String> getParameter() {
		return parameter;
	}

	public void setParameter(final Map<String, String> parameter) {
		this.parameter = parameter;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(final Boolean active) {
		this.active = active;
	}

	public Boolean getSilent() {
		return silent;
	}

	public void setSilent(final Boolean silent) {
		this.silent = silent;
	}

	public String getMessage() {
		return message;
	}

	public Boolean getResult() {
		return result;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void setResult(final Boolean result) {
		this.result = result;
	}

}
