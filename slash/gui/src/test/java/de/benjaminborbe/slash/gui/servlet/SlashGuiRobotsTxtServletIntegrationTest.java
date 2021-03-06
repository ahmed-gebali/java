package de.benjaminborbe.slash.gui.servlet;

import com.google.inject.Injector;
import de.benjaminborbe.slash.gui.guice.SlashGuiModulesMock;
import de.benjaminborbe.tools.guice.GuiceInjectorBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SlashGuiRobotsTxtServletIntegrationTest {

	@Test
	public void testSingleton() {
		final Injector injector = GuiceInjectorBuilder.getInjector(new SlashGuiModulesMock());
		final SlashGuiRobotsTxtServlet a = injector.getInstance(SlashGuiRobotsTxtServlet.class);
		final SlashGuiRobotsTxtServlet b = injector.getInstance(SlashGuiRobotsTxtServlet.class);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
		assertEquals(a, b);
	}

}
