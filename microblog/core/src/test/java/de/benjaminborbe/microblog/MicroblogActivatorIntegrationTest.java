package de.benjaminborbe.microblog;

import com.google.inject.Injector;
import de.benjaminborbe.configuration.api.ConfigurationDescription;
import de.benjaminborbe.cron.api.CronJob;
import de.benjaminborbe.microblog.api.MicroblogService;
import de.benjaminborbe.microblog.guice.MicroblogModulesMock;
import de.benjaminborbe.search.api.SearchServiceComponent;
import de.benjaminborbe.tools.guice.GuiceInjectorBuilder;
import de.benjaminborbe.tools.osgi.ServiceInfo;
import de.benjaminborbe.tools.osgi.mock.ExtHttpServiceMock;
import de.benjaminborbe.tools.osgi.test.BundleActivatorTestUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MicroblogActivatorIntegrationTest {

	@Test
	public void testInject() {
		final Injector injector = GuiceInjectorBuilder.getInjector(new MicroblogModulesMock());
		assertNotNull(injector.getInstance(MicroblogActivator.class));
	}

	@Test
	public void testResources() throws Exception {
		final Injector injector = GuiceInjectorBuilder.getInjector(new MicroblogModulesMock());
		final MicroblogActivator activator = new MicroblogActivator() {

			@Override
			public Injector getInjector() {
				return injector;
			}

		};
		final BundleActivatorTestUtil bundleActivatorTestUtil = new BundleActivatorTestUtil();
		final ExtHttpServiceMock extHttpServiceMock = bundleActivatorTestUtil.startBundle(activator);
		final List<String> paths = new ArrayList<>();
		assertEquals(paths.size(), extHttpServiceMock.getRegisterResourceCallCounter());
		for (final String path : paths) {
			assertTrue("no resource for path " + path + " registered", extHttpServiceMock.hasResource(path));
		}
	}

	@Test
	public void testServices() throws Exception {
		final Injector injector = GuiceInjectorBuilder.getInjector(new MicroblogModulesMock());
		final MicroblogActivator activator = new MicroblogActivator() {

			@Override
			public Injector getInjector() {
				return injector;
			}

		};

		final BundleActivatorTestUtil bundleActivatorTestUtil = new BundleActivatorTestUtil();
		bundleActivatorTestUtil.startBundle(activator);

		final Collection<ServiceInfo> serviceInfos = activator.getServiceInfos();
		final List<String> names = new ArrayList<>();
		names.add(CronJob.class.getName());
		names.add(MicroblogService.class.getName());
		names.add(SearchServiceComponent.class.getName());
		names.add(ConfigurationDescription.class.getName());
		names.add(ConfigurationDescription.class.getName());
		names.add(ConfigurationDescription.class.getName());
		names.add(ConfigurationDescription.class.getName());

		assertEquals(names.size(), serviceInfos.size());
		for (final String name : names) {
			boolean match = false;
			for (final ServiceInfo serviceInfo : serviceInfos) {
				if (name.equals(serviceInfo.getName())) {
					match = true;
				}
			}
			assertTrue("no service with name: " + name + " found", match);
		}
	}
}
