package de.benjaminborbe.poker.guice;

import com.google.inject.AbstractModule;
import de.benjaminborbe.analytics.api.AnalyticsService;
import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.configuration.api.ConfigurationService;
import de.benjaminborbe.cron.api.CronController;
import de.benjaminborbe.eventbus.api.EventbusService;
import de.benjaminborbe.storage.api.StorageService;
import org.apache.felix.http.api.ExtHttpService;
import org.osgi.service.log.LogService;

import static org.ops4j.peaberry.Peaberry.service;

public class PokerOsgiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CronController.class).toProvider(service(CronController.class).single());
		bind(EventbusService.class).toProvider(service(EventbusService.class).single());
		bind(AnalyticsService.class).toProvider(service(AnalyticsService.class).single());
		bind(ConfigurationService.class).toProvider(service(ConfigurationService.class).single());
		bind(AuthenticationService.class).toProvider(service(AuthenticationService.class).single());
		bind(AuthorizationService.class).toProvider(service(AuthorizationService.class).single());
		bind(StorageService.class).toProvider(service(StorageService.class).single());
		bind(LogService.class).toProvider(service(LogService.class).single());
		bind(ExtHttpService.class).toProvider(service(ExtHttpService.class).single());
	}
}
