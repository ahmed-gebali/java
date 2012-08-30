package de.benjaminborbe.dhl.guice;

import static org.ops4j.peaberry.Peaberry.service;

import org.apache.felix.http.api.ExtHttpService;
import org.osgi.service.log.LogService;

import com.google.inject.AbstractModule;

import de.benjaminborbe.mail.api.MailService;
import de.benjaminborbe.storage.api.StorageService;

public class DhlOsgiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(StorageService.class).toProvider(service(StorageService.class).single());
		bind(MailService.class).toProvider(service(MailService.class).single());
		bind(LogService.class).toProvider(service(LogService.class).single());
		bind(ExtHttpService.class).toProvider(service(ExtHttpService.class).single());
	}
}
