package de.benjaminborbe.notification.dao;

import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.notification.api.NotificationMediaIdentifier;
import de.benjaminborbe.notification.api.NotificationTypeIdentifier;
import de.benjaminborbe.storage.api.StorageException;

import java.util.Collection;

public interface NotificationMediaDao {

	void add(
		UserIdentifier userIdentifier,
		NotificationTypeIdentifier notificationTypeIdentifier,
		NotificationMediaIdentifier notificationMediaIdentifier
	) throws StorageException;

	void remove(UserIdentifier userIdentifier, NotificationTypeIdentifier notificationTypeIdentifier, NotificationMediaIdentifier notificationMediaIdentifier)
		throws StorageException;

	Collection<NotificationMediaIdentifier> get(UserIdentifier userIdentifier, NotificationTypeIdentifier notificationTypeIdentifier) throws StorageException;

	boolean has(UserIdentifier userIdentifier, NotificationTypeIdentifier notificationTypeIdentifier, NotificationMediaIdentifier notificationMediaIdentifier)
		throws StorageException;

}
