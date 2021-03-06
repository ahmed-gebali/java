package de.benjaminborbe.util.gui.servlet;

import com.google.inject.Provider;
import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.cache.api.CacheService;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.navigation.api.NavigationWidget;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.tools.util.ParseException;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.util.gui.util.UtilGuiTimeConvert;
import de.benjaminborbe.website.servlet.WebsiteHtmlServlet;
import de.benjaminborbe.website.util.H1Widget;
import de.benjaminborbe.website.util.ListWidget;
import de.benjaminborbe.website.widget.BrWidget;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

@Singleton
public class UtilGuiTimeConvertServlet extends WebsiteHtmlServlet {

	private static final long serialVersionUID = 3897185107545429460L;

	private static final String TITLE = "Util - Index";

	private static final String SOURCE_DATETIME = "source_datetime";

	private static final String SOURCE_TIMEZONE = "source_timezone";

	private static final String TARGET_TIMEZONE = "target_timezone";

	private final UtilGuiTimeConvert utilGuiTimeConvert;

	private final CalendarUtil calendarUtil;

	@Inject
	public UtilGuiTimeConvertServlet(
		final Logger logger,
		final CalendarUtil calendarUtil,
		final TimeZoneUtil timeZoneUtil,
		final ParseUtil parseUtil,
		final AuthenticationService authenticationService,
		final NavigationWidget navigationWidget,
		final Provider<HttpContext> httpContextProvider,
		final UrlUtil urlUtil,
		final UtilGuiTimeConvert utilGuiTimeConvert,
		final AuthorizationService authorizationService,
		final CacheService cacheService
	) {
		super(logger, calendarUtil, timeZoneUtil, parseUtil, navigationWidget, authenticationService, authorizationService, httpContextProvider, urlUtil, cacheService);
		this.utilGuiTimeConvert = utilGuiTimeConvert;
		this.calendarUtil = calendarUtil;
	}

	@Override
	protected String getTitle() {
		return TITLE;
	}

	@Override
	protected Widget createContentWidget(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException {
		final ListWidget widgets = new ListWidget();
		widgets.add(new H1Widget(getTitle()));

		try {
			final TimeZone sourceTimeZone = TimeZone.getTimeZone(request.getParameter(SOURCE_TIMEZONE));
			final Calendar sourceCalendar = calendarUtil.parseDateTime(sourceTimeZone, request.getParameter(SOURCE_DATETIME));
			final TimeZone targetTimeZone = TimeZone.getTimeZone(request.getParameter(TARGET_TIMEZONE));
			final Calendar targetCalendar = utilGuiTimeConvert.convert(sourceCalendar, targetTimeZone);

			widgets.add("Source: " + calendarUtil.toDateTimeString(sourceCalendar) + " " + sourceTimeZone.getID());
			widgets.add(new BrWidget());
			widgets.add("Target: " + calendarUtil.toDateTimeString(targetCalendar) + " " + targetTimeZone.getID());
		} catch (final ParseException e) {
			widgets.add("invalid input");
		}

		return widgets;
	}
}
