package de.benjaminborbe.xmlrpc.gui.servlet;

import com.google.inject.Injector;
import de.benjaminborbe.tools.guice.GuiceInjectorBuilder;
import de.benjaminborbe.xmlrpc.gui.guice.XmlrpcGuiModulesMock;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XmlrpcGuiServletIntegrationTest {

	@Test
	public void testSingleton() {
		final Injector injector = GuiceInjectorBuilder.getInjector(new XmlrpcGuiModulesMock());
		final XmlrpcGuiServlet a = injector.getInstance(XmlrpcGuiServlet.class);
		final XmlrpcGuiServlet b = injector.getInstance(XmlrpcGuiServlet.class);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
		assertEquals(a, b);
	}

}
