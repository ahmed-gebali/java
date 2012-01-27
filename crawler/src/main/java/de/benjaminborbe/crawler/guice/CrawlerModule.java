package de.benjaminborbe.crawler.guice;

import org.slf4j.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import de.benjaminborbe.crawler.api.CrawlerNotifier;
import de.benjaminborbe.crawler.api.CrawlerService;
import de.benjaminborbe.crawler.service.CrawlerNotifierImpl;
import de.benjaminborbe.crawler.service.CrawlerServiceImpl;
import de.benjaminborbe.tools.log.LoggerSlf4Provider;

public class CrawlerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CrawlerService.class).to(CrawlerServiceImpl.class).in(Singleton.class);
		bind(CrawlerNotifier.class).to(CrawlerNotifierImpl.class).in(Singleton.class);
		bind(Logger.class).toProvider(LoggerSlf4Provider.class).in(Singleton.class);
	}
}