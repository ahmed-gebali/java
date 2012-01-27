package de.benjaminborbe.authentication.gui;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;

import com.google.inject.Inject;

import de.benjaminborbe.authentication.gui.guice.AuthenticationGuiModules;
import de.benjaminborbe.authentication.gui.servlet.AuthenticationGuiServlet;
import de.benjaminborbe.tools.guice.Modules;
import de.benjaminborbe.tools.osgi.HttpBundleActivator;
import de.benjaminborbe.tools.osgi.ServletInfo;

public class AuthenticationGuiActivator extends HttpBundleActivator {

	@Inject
	private AuthenticationGuiServlet authenticationGuiServlet;

	public AuthenticationGuiActivator() {
		super("authentication");
	}

	@Override
	protected Modules getModules(final BundleContext context) {
		return new AuthenticationGuiModules(context);
	}

	@Override
	protected Collection<ServletInfo> getServletInfos() {
		final Set<ServletInfo> result = new HashSet<ServletInfo>(super.getServletInfos());
		result.add(new ServletInfo(authenticationGuiServlet, "/"));
		return result;
	}

}