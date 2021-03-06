package de.benjaminborbe.crawler.guice;

import com.google.inject.AbstractModule;
import de.benjaminborbe.analytics.api.AnalyticsService;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.httpdownloader.api.HttpdownloaderService;
import de.benjaminborbe.message.api.MessageService;
import de.benjaminborbe.navigation.api.NavigationWidget;
import org.apache.felix.http.api.ExtHttpService;
import org.osgi.service.log.LogService;

import static org.ops4j.peaberry.Peaberry.service;

public class CrawlerOsgiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AuthorizationService.class).toProvider(service(AuthorizationService.class).single());
		bind(HttpdownloaderService.class).toProvider(service(HttpdownloaderService.class).single());
		bind(AnalyticsService.class).toProvider(service(AnalyticsService.class).single());
		bind(MessageService.class).toProvider(service(MessageService.class).single());
		bind(NavigationWidget.class).toProvider(service(NavigationWidget.class).single());
		bind(LogService.class).toProvider(service(LogService.class).single());
		bind(ExtHttpService.class).toProvider(service(ExtHttpService.class).single());
	}
}
