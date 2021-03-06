package de.benjaminborbe.task.gui.servlet;

import com.google.inject.Provider;
import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.api.AuthenticationServiceException;
import de.benjaminborbe.authentication.api.LoginRequiredException;
import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.cache.api.CacheService;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.navigation.api.NavigationWidget;
import de.benjaminborbe.task.api.TaskContext;
import de.benjaminborbe.task.api.TaskService;
import de.benjaminborbe.task.api.TaskServiceException;
import de.benjaminborbe.task.gui.util.TaskContextComparator;
import de.benjaminborbe.task.gui.util.TaskGuiLinkFactory;
import de.benjaminborbe.task.gui.util.TaskGuiUtil;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.website.servlet.RedirectException;
import de.benjaminborbe.website.util.ExceptionWidget;
import de.benjaminborbe.website.util.H1Widget;
import de.benjaminborbe.website.util.ListWidget;
import de.benjaminborbe.website.util.UlWidget;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
public class TaskGuiTaskContextListServlet extends TaskGuiWebsiteHtmlServlet {

	private static final long serialVersionUID = 1328676176772634649L;

	private static final String TITLE = "TaskContext - List";

	private final Logger logger;

	private final TaskService taskService;

	private final AuthenticationService authenticationService;

	private final TaskGuiLinkFactory taskGuiLinkFactory;

	@Inject
	public TaskGuiTaskContextListServlet(
		final Logger logger,
		final CalendarUtil calendarUtil,
		final TimeZoneUtil timeZoneUtil,
		final ParseUtil parseUtil,
		final AuthenticationService authenticationService,
		final NavigationWidget navigationWidget,
		final Provider<HttpContext> httpContextProvider,
		final UrlUtil urlUtil,
		final AuthorizationService authorizationService,
		final TaskService taskService,
		final TaskGuiLinkFactory taskGuiLinkFactory,
		final TaskGuiUtil taskGuiUtil,
		final CacheService cacheService
	) {
		super(logger, calendarUtil, timeZoneUtil, parseUtil, navigationWidget, authenticationService, authorizationService, httpContextProvider, urlUtil, taskGuiUtil, cacheService);
		this.logger = logger;
		this.taskService = taskService;
		this.authenticationService = authenticationService;
		this.taskGuiLinkFactory = taskGuiLinkFactory;
	}

	@Override
	protected String getTitle() {
		return TITLE;
	}

	@Override
	protected Widget createTaskContentWidget(final HttpServletRequest request) throws IOException,
		PermissionDeniedException, RedirectException, LoginRequiredException {
		try {
			logger.trace("printContent");
			final ListWidget widgets = new ListWidget();
			widgets.add(new H1Widget(getTitle()));
			final UlWidget ul = new UlWidget();
			final SessionIdentifier sessionIdentifier = authenticationService.createSessionIdentifier(request);
			final List<TaskContext> taskContexts = new ArrayList<TaskContext>(taskService.getTaskContexts(sessionIdentifier));
			Collections.sort(taskContexts, new TaskContextComparator());
			for (final TaskContext taskContext : taskContexts) {
				final ListWidget row = new ListWidget();
				row.add(taskContext.getName());
				row.add(" ");
				row.add(taskGuiLinkFactory.taskContextUpdate(request, taskContext));
				row.add(" ");
				row.add(taskGuiLinkFactory.taskContextUser(request, taskContext.getId()));
				row.add(" ");
				row.add(taskGuiLinkFactory.taskContextDelete(request, taskContext));
				ul.add(row);
			}
			widgets.add(ul);
			final ListWidget links = new ListWidget();
			links.add(taskGuiLinkFactory.tasksNext(request));
			links.add(" ");
			links.add(taskGuiLinkFactory.tasksUncompleted(request));
			links.add(" ");
			links.add(taskGuiLinkFactory.taskContextCreate(request));
			widgets.add(links);
			return widgets;
		} catch (final AuthenticationServiceException e) {
			logger.trace(e.getClass().getName(), e);
			final ExceptionWidget widget = new ExceptionWidget(e);
			return widget;
		} catch (final TaskServiceException e) {
			logger.trace(e.getClass().getName(), e);
			final ExceptionWidget widget = new ExceptionWidget(e);
			return widget;
		}
	}
}
