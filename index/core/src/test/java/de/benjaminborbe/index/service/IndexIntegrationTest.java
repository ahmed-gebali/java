package de.benjaminborbe.index.service;

import com.google.inject.Injector;
import de.benjaminborbe.index.api.IndexService;
import de.benjaminborbe.index.guice.IndexModulesMock;
import de.benjaminborbe.tools.guice.GuiceInjectorBuilder;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class IndexIntegrationTest {

	@Test
	public void testInjections() {
		final Injector injector = GuiceInjectorBuilder.getInjector(new IndexModulesMock());
		final IndexService indexerService = injector.getInstance(IndexService.class);
		assertNotNull(indexerService);
		final IndexService indexSearcherService = injector.getInstance(IndexService.class);
		assertNotNull(indexSearcherService);
	}
}
