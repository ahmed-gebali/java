package de.benjaminborbe.tools.osgi;

import org.apache.felix.http.api.ExtHttpService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTracker;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Set;

public abstract class HttpBundleActivator extends BaseBundleActivator {

	private final String prefix;

	@Inject
	private BaseGuiceFilter guiceFilter;

	public HttpBundleActivator(final String name) {
		this.prefix = "/" + name.toLowerCase();
	}

	protected void serviceAdded(final ExtHttpService service) {
		for (final FilterInfo filterInfo : getFilterInfos()) {
			final HttpContext context = filterInfo.getContext();
			final Filter filter = filterInfo.getFilter();
			final String pattern;
			if (filterInfo.isSlashFilter()) {
				pattern = cleanupPattern(filterInfo.getPattern());
			} else {
				pattern = cleanupPattern(prefix + filterInfo.getPattern());
			}
			@SuppressWarnings("rawtypes")
			final Dictionary initParams = filterInfo.getInitParams();
			final int ranking = filterInfo.getRanking();
			try {
				logger.info("registerFilter for pattern: \"" + pattern + "\"");
				service.registerFilter(filter, pattern, initParams, ranking, context);
			} catch (final ServletException e) {
				logger.error("ServletException", e);
			}
		}
		for (final ServletInfo servletInfo : getServletInfos()) {
			final String alias;
			if (servletInfo.isSlashServlet()) {
				alias = cleanupAlias(servletInfo.getAlias());
			} else {
				alias = cleanupAlias(prefix + "/" + servletInfo.getAlias());
			}
			final Servlet servlet = servletInfo.getServlet();
			@SuppressWarnings("rawtypes")
			final Dictionary initparams = servletInfo.getInitParams();
			final HttpContext context = servletInfo.getContext();
			try {
				logger.info("registerServlet for alias: \"" + alias + "\"");
				service.registerServlet(alias, servlet, initparams, context);
			} catch (final ServletException e) {
				logger.error("ServletException", e);
			} catch (final NamespaceException e) {
				logger.error("NamespaceException", e);
			}
		}
		for (final ResourceInfo resourceInfo : getResouceInfos()) {
			final String alias;
			if (resourceInfo.isSlashResoure()) {
				alias = cleanupAlias(resourceInfo.getAlias());
			} else {
				alias = cleanupAlias(prefix + "/" + resourceInfo.getAlias());
			}
			final String name = resourceInfo.getName();
			final HttpContext context = resourceInfo.getContext();
			try {
				logger.info("registerResource for alias: \"" + alias + "\"");
				service.registerResources(alias, name, context);
			} catch (final NamespaceException e) {
				logger.error("NamespaceException", e);
			}
		}
	}

	protected void serviceRemoved(final ExtHttpService service) {
		for (final FilterInfo filterInfo : getFilterInfos()) {
			final Filter filter = filterInfo.getFilter();
			service.unregisterFilter(filter);
		}
		for (final ServletInfo servletInfo : getServletInfos()) {
			final Servlet servlet = servletInfo.getServlet();
			service.unregisterServlet(servlet);
		}
		for (final ResourceInfo resourceInfo : getResouceInfos()) {
			final String alias = resourceInfo.getAlias();
			service.unregister(alias);
		}
	}

	@Override
	public Collection<ServiceTracker> getServiceTrackers(final BundleContext context) {
		final Set<ServiceTracker> serviceTrackers = new HashSet<ServiceTracker>(super.getServiceTrackers(context));
		// create serviceTracker for ExtHttpService
		{
			final ServiceTracker serviceTracker = new ServiceTracker(context, ExtHttpService.class.getName(), null) {

				@Override
				public Object addingService(final ServiceReference ref) {
					final Object service = super.addingService(ref);
					serviceAdded((ExtHttpService) service);
					return service;
				}

				@Override
				public void removedService(final ServiceReference ref, final Object service) {
					serviceRemoved((ExtHttpService) service);
					super.removedService(ref, service);
				}
			};
			serviceTrackers.add(serviceTracker);
		}
		return serviceTrackers;
	}

	protected Collection<ServletInfo> getServletInfos() {
		final Set<ServletInfo> result = new HashSet<ServletInfo>();
		return result;
	}

	protected Collection<ResourceInfo> getResouceInfos() {
		final Set<ResourceInfo> result = new HashSet<ResourceInfo>();
		return result;
	}

	protected Collection<FilterInfo> getFilterInfos() {
		final Set<FilterInfo> result = new HashSet<FilterInfo>();
		result.add(new FilterInfo(guiceFilter, ".*", 999));
		return result;
	}

	protected String cleanupAlias(final String alias) {
		logger.trace("alias to cleanup " + alias);
		if (alias.matches("/+"))
			return "/";
		else
			return alias.replaceFirst("/+$", "").replaceAll("/+", "/");
	}

	protected String cleanupPattern(final String pattern) {
		return pattern.replaceFirst("/+$", "").replaceAll("/+", "/");
	}

}
