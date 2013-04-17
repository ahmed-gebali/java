package de.benjaminborbe.proxy.gui;

import com.google.inject.Inject;
import de.benjaminborbe.navigation.api.NavigationEntry;
import de.benjaminborbe.proxy.gui.guice.ProxyGuiModules;
import de.benjaminborbe.proxy.gui.service.ProxyGuiNavigationEntry;
import de.benjaminborbe.proxy.gui.servlet.ProxyGuiAdminServlet;
import de.benjaminborbe.proxy.gui.servlet.ProxyGuiServlet;
import de.benjaminborbe.proxy.gui.servlet.ProxyGuiStartServlet;
import de.benjaminborbe.proxy.gui.servlet.ProxyGuiStopServlet;
import de.benjaminborbe.tools.guice.Modules;
import de.benjaminborbe.tools.osgi.HttpBundleActivator;
import de.benjaminborbe.tools.osgi.ServiceInfo;
import de.benjaminborbe.tools.osgi.ServletInfo;
import org.osgi.framework.BundleContext;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ProxyGuiActivator extends HttpBundleActivator {

	@Inject
	private ProxyGuiNavigationEntry proxyGuiNavigationEntry;

	@Inject
	private ProxyGuiStartServlet proxyGuiStartServlet;

	@Inject
	private ProxyGuiStopServlet proxyGuiStopServlet;

	@Inject
	private ProxyGuiAdminServlet proxyGuiAdminServlet;

	@Inject
	private ProxyGuiServlet proxyGuiServlet;

	public ProxyGuiActivator() {
		super(ProxyGuiConstants.NAME);
	}

	@Override
	protected Modules getModules(final BundleContext context) {
		return new ProxyGuiModules(context);
	}

	@Override
	public Collection<ServiceInfo> getServiceInfos() {
		final Set<ServiceInfo> result = new HashSet<>(super.getServiceInfos());
		result.add(new ServiceInfo(NavigationEntry.class, proxyGuiNavigationEntry));
		return result;
	}

	@Override
	protected Collection<ServletInfo> getServletInfos() {
		final Set<ServletInfo> result = new HashSet<>(super.getServletInfos());
		result.add(new ServletInfo(proxyGuiServlet, ProxyGuiConstants.URL_HOME));
		result.add(new ServletInfo(proxyGuiAdminServlet, ProxyGuiConstants.URL_ADMIN));
		result.add(new ServletInfo(proxyGuiStartServlet, ProxyGuiConstants.URL_START));
		result.add(new ServletInfo(proxyGuiStopServlet, ProxyGuiConstants.URL_STOP));
		return result;
	}

}
