package de.benjaminborbe.microblog.guice;

import org.apache.felix.http.api.ExtHttpService;
import org.osgi.service.log.LogService;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.mock.AuthenticationServiceMock;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.mock.AuthorizationServiceMock;
import de.benjaminborbe.configuration.api.ConfigurationService;
import de.benjaminborbe.configuration.mock.ConfigurationServiceMock;
import de.benjaminborbe.index.api.IndexService;
import de.benjaminborbe.index.mock.IndexServiceMock;
import de.benjaminborbe.mail.api.MailService;
import de.benjaminborbe.mail.mock.MailServiceMock;
import de.benjaminborbe.notification.api.NotificationService;
import de.benjaminborbe.notification.mock.NotificationServiceMock;
import de.benjaminborbe.storage.api.StorageService;
import de.benjaminborbe.storage.mock.StorageServiceMock;
import de.benjaminborbe.tools.osgi.mock.ExtHttpServiceMock;
import de.benjaminborbe.tools.osgi.mock.LogServiceMock;

public class MicroblogOsgiModuleMock extends AbstractModule {

	@Override
	protected void configure() {
		bind(AuthenticationService.class).to(AuthenticationServiceMock.class).in(Singleton.class);
		bind(NotificationService.class).to(NotificationServiceMock.class).in(Singleton.class);
		bind(AuthorizationService.class).to(AuthorizationServiceMock.class).in(Singleton.class);
		bind(ConfigurationService.class).to(ConfigurationServiceMock.class).in(Singleton.class);
		bind(IndexService.class).to(IndexServiceMock.class).in(Singleton.class);
		bind(StorageService.class).to(StorageServiceMock.class).in(Singleton.class);
		bind(MailService.class).to(MailServiceMock.class).in(Singleton.class);
		bind(LogService.class).to(LogServiceMock.class).in(Singleton.class);
		bind(ExtHttpService.class).to(ExtHttpServiceMock.class).in(Singleton.class);
	}
}