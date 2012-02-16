package de.benjaminborbe.performance.gui;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;

import com.google.inject.Inject;

import de.benjaminborbe.performance.gui.guice.PerformanceGuiModules;
import de.benjaminborbe.performance.gui.servlet.PerformanceGuiFilter;
import de.benjaminborbe.performance.gui.servlet.PerformanceGuiServlet;
import de.benjaminborbe.tools.guice.Modules;
import de.benjaminborbe.tools.osgi.FilterInfo;
import de.benjaminborbe.tools.osgi.HttpBundleActivator;
import de.benjaminborbe.tools.osgi.ResourceInfo;
import de.benjaminborbe.tools.osgi.ServletInfo;

public class PerformanceGuiActivator extends HttpBundleActivator {

	@Inject
	private PerformanceGuiServlet performanceServlet;

	@Inject
	private PerformanceGuiFilter performanceFilter;

	public PerformanceGuiActivator() {
		super("performance");
	}

	@Override
	protected Modules getModules(final BundleContext context) {
		return new PerformanceGuiModules(context);
	}

	@Override
	protected Collection<ServletInfo> getServletInfos() {
		final Set<ServletInfo> result = new HashSet<ServletInfo>(super.getServletInfos());
		result.add(new ServletInfo(performanceServlet, "/"));
		return result;
	}

	@Override
	protected Collection<FilterInfo> getFilterInfos() {
		final Set<FilterInfo> result = new HashSet<FilterInfo>(super.getFilterInfos());
		result.add(new FilterInfo(performanceFilter, ".*", 998, true));
		return result;
	}

	@Override
	protected Collection<ResourceInfo> getResouceInfos() {
		final Set<ResourceInfo> result = new HashSet<ResourceInfo>(super.getResouceInfos());
		// result.add(new ResourceInfo("/css", "css"));
		// result.add(new ResourceInfo("/js", "js"));
		return result;
	}

}