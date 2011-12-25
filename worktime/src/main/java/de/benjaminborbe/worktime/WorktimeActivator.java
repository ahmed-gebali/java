package de.benjaminborbe.worktime;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;

import com.google.inject.Inject;

import de.benjaminborbe.cron.api.CronJob;
import de.benjaminborbe.dashboard.api.DashboardWidget;
import de.benjaminborbe.tools.guice.Modules;
import de.benjaminborbe.tools.osgi.HttpBundleActivator;
import de.benjaminborbe.tools.osgi.ServiceInfo;
import de.benjaminborbe.tools.osgi.ServletInfo;
import de.benjaminborbe.worktime.guice.WorktimeModules;
import de.benjaminborbe.worktime.service.WorktimeCronJob;
import de.benjaminborbe.worktime.service.WorktimeDashboardWidget;
import de.benjaminborbe.worktime.servlet.WorktimeServlet;

public class WorktimeActivator extends HttpBundleActivator {

	@Inject
	private WorktimeServlet worktimeServlet;

	@Inject
	private WorktimeCronJob worktimeCronJob;

	@Inject
	private WorktimeDashboardWidget worktimeDashboardWidget;

	public WorktimeActivator() {
		super("worktime");
	}

	@Override
	protected Modules getModules(final BundleContext context) {
		return new WorktimeModules(context);
	}

	@Override
	protected Collection<ServletInfo> getServletInfos() {
		final Set<ServletInfo> result = new HashSet<ServletInfo>(super.getServletInfos());
		result.add(new ServletInfo(worktimeServlet, "/"));
		return result;
	}

	@Override
	protected Collection<ServiceInfo> getServiceInfos() {
		final Set<ServiceInfo> result = new HashSet<ServiceInfo>(super.getServiceInfos());
		result.add(new ServiceInfo(CronJob.class, worktimeCronJob, worktimeCronJob.getClass().getName()));
		result.add(new ServiceInfo(DashboardWidget.class, worktimeDashboardWidget, worktimeDashboardWidget.getClass()
				.getName()));
		return result;
	}

}
