package de.benjaminborbe.cron.gui.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.api.AuthenticationServiceException;
import de.benjaminborbe.authentication.api.LoginRequiredException;
import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.cache.api.CacheService;
import de.benjaminborbe.cron.api.CronController;
import de.benjaminborbe.cron.api.CronIdentifier;
import de.benjaminborbe.cron.api.CronService;
import de.benjaminborbe.cron.api.CronServiceException;
import de.benjaminborbe.cron.gui.util.CronGuiLinkFactory;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.navigation.api.NavigationWidget;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.website.servlet.RedirectUtil;
import de.benjaminborbe.website.servlet.WebsiteHtmlServlet;
import de.benjaminborbe.website.util.ExceptionWidget;
import de.benjaminborbe.website.util.H1Widget;
import de.benjaminborbe.website.util.ListWidget;
import de.benjaminborbe.website.util.UlWidget;

@Singleton
public class CronGuiListServlet extends WebsiteHtmlServlet {

	private static final long serialVersionUID = 1328676176772634649L;

	private static final String TITLE = "Cron";

	private final CronService cronService;

	private final AuthenticationService authenticationService;

	private final CronGuiLinkFactory cronGuiLinkFactory;

	@Inject
	public CronGuiListServlet(
			final Logger logger,
			final CalendarUtil calendarUtil,
			final TimeZoneUtil timeZoneUtil,
			final ParseUtil parseUtil,
			final CronController cronController,
			final AuthenticationService authenticationService,
			final NavigationWidget navigationWidget,
			final Provider<HttpContext> httpContextProvider,
			final RedirectUtil redirectUtil,
			final UrlUtil urlUtil,
			final AuthorizationService authorizationService,
			final CacheService cacheService,
			final CronService cronService,
			final CronGuiLinkFactory cronGuiLinkFactory) {
		super(logger, calendarUtil, timeZoneUtil, parseUtil, navigationWidget, authenticationService, authorizationService, httpContextProvider, urlUtil, cacheService);
		this.authenticationService = authenticationService;
		this.cronService = cronService;
		this.cronGuiLinkFactory = cronGuiLinkFactory;
	}

	@Override
	protected Widget createContentWidget(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException, LoginRequiredException,
			PermissionDeniedException {
		try {
			final ListWidget widgets = new ListWidget();
			widgets.add(new H1Widget(getTitle()));

			final UlWidget ul = new UlWidget();
			final SessionIdentifier sessionIdentifier = authenticationService.createSessionIdentifier(request);
			for (final CronIdentifier cron : cronService.getCronIdentifiers(sessionIdentifier)) {
				final ListWidget row = new ListWidget();
				row.add(cron.getId());
				row.add(" ");
				row.add(cronGuiLinkFactory.triggerCron(request, cron));
				ul.add(row);
			}
			widgets.add(ul);

			return widgets;
		}
		catch (final CronServiceException e) {
			final ExceptionWidget widget = new ExceptionWidget(e);
			return widget;
		}
		catch (final AuthenticationServiceException e) {
			final ExceptionWidget widget = new ExceptionWidget(e);
			return widget;
		}
	}

	@Override
	protected String getTitle() {
		return TITLE;
	}

}
