package de.benjaminborbe.analytics.gui.guice;

import org.slf4j.Logger;

import com.google.inject.AbstractModule;
import javax.inject.Singleton;

import de.benjaminborbe.analytics.gui.config.AnalyticsGuiConfig;
import de.benjaminborbe.analytics.gui.config.AnalyticsGuiConfigImpl;
import de.benjaminborbe.tools.log.LoggerSlf4Provider;

public class AnalyticsGuiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AnalyticsGuiConfig.class).to(AnalyticsGuiConfigImpl.class).in(Singleton.class);
		bind(Logger.class).toProvider(LoggerSlf4Provider.class).in(Singleton.class);
	}
}