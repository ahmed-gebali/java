package de.benjaminborbe.confluence.guice;

import static org.ops4j.peaberry.Peaberry.service;

import org.apache.felix.http.api.ExtHttpService;
import org.osgi.service.log.LogService;

import com.google.inject.AbstractModule;

import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.index.api.IndexerService;
import de.benjaminborbe.storage.api.StorageService;

public class ConfluenceOsgiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IndexerService.class).toProvider(service(IndexerService.class).single());
		bind(AuthenticationService.class).toProvider(service(AuthenticationService.class).single());
		bind(StorageService.class).toProvider(service(StorageService.class).single());
		bind(LogService.class).toProvider(service(LogService.class).single());
		bind(ExtHttpService.class).toProvider(service(ExtHttpService.class).single());
	}
}
