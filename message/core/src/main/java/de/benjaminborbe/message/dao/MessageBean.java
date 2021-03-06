package de.benjaminborbe.message.dao;

import de.benjaminborbe.message.api.Message;
import de.benjaminborbe.message.api.MessageIdentifier;
import de.benjaminborbe.storage.tools.EntityBase;
import de.benjaminborbe.storage.tools.HasCreated;
import de.benjaminborbe.storage.tools.HasModified;

import java.util.Calendar;

public class MessageBean extends EntityBase<MessageIdentifier> implements Message, HasCreated, HasModified {

	private static final long serialVersionUID = -8803301003126328406L;

	private MessageIdentifier id;

	private String content;

	private Calendar created;

	private Calendar modified;

	private String type;

	private Long retryCounter;

	private String lockName;

	private Calendar lockTime;

	private Long maxRetryCounter;

	private Calendar startTime;

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
	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	@Override
	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	@Override
	public Long getRetryCounter() {
		return retryCounter;
	}

	public void setRetryCounter(final Long retryCounter) {
		this.retryCounter = retryCounter;
	}

	@Override
	public String getLockName() {
		return lockName;
	}

	public void setLockName(final String lockName) {
		this.lockName = lockName;
	}

	@Override
	public Calendar getLockTime() {
		return lockTime;
	}

	public void setLockTime(final Calendar lockTime) {
		this.lockTime = lockTime;
	}

	@Override
	public MessageIdentifier getId() {
		return id;
	}

	@Override
	public void setId(final MessageIdentifier id) {
		this.id = id;
	}

	@Override
	public Long getMaxRetryCounter() {
		return maxRetryCounter;
	}

	public void setMaxRetryCounter(final Long maxRetryCounter) {
		this.maxRetryCounter = maxRetryCounter;
	}

	@Override
	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(final Calendar startTime) {
		this.startTime = startTime;
	}

}
