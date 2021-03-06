package de.benjaminborbe.util.gui.servlet;

import com.google.inject.Provider;
import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.cache.api.CacheService;
import de.benjaminborbe.html.api.CssResource;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.JavascriptResource;
import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.navigation.api.NavigationWidget;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.website.link.LinkWidget;
import de.benjaminborbe.website.servlet.RedirectException;
import de.benjaminborbe.website.servlet.WebsiteHtmlServlet;
import de.benjaminborbe.website.util.CssResourceImpl;
import de.benjaminborbe.website.util.DivWidget;
import de.benjaminborbe.website.util.H1Widget;
import de.benjaminborbe.website.util.JavascriptResourceImpl;
import de.benjaminborbe.website.util.ListWidget;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Singleton
public class UtilGuiQUnitServlet extends WebsiteHtmlServlet {

	private static final long serialVersionUID = 3897185107545429460L;

	private static final String TITLE = "Util - QUnit";

	private final Logger logger;

	@Inject
	public UtilGuiQUnitServlet(
		final Logger logger,
		final CalendarUtil calendarUtil,
		final TimeZoneUtil timeZoneUtil,
		final ParseUtil parseUtil,
		final AuthenticationService authenticationService,
		final NavigationWidget navigationWidget,
		final Provider<HttpContext> httpContextProvider,
		final UrlUtil urlUtil,
		final AuthorizationService authorizationService,
		final CacheService cacheService
	) {
		super(logger, calendarUtil, timeZoneUtil, parseUtil, navigationWidget, authenticationService, authorizationService, httpContextProvider, urlUtil, cacheService);
		this.logger = logger;
	}

	@Override
	protected String getTitle() {
		return TITLE;
	}

	@Override
	protected List<JavascriptResource> getJavascriptResources(final HttpServletRequest request, final HttpServletResponse response) {
		final String contextPath = request.getContextPath();
		final List<JavascriptResource> result = super.getJavascriptResources(request, response);
		result.add(new JavascriptResourceImpl(contextPath + "/util/js/qunit-1.9.0.js"));
		result.add(new JavascriptResourceImpl(contextPath + "/util/js/function.js"));
		result.add(new JavascriptResourceImpl(contextPath + "/util/js/function-tests.js"));
		return result;
	}

	@Override
	protected Collection<CssResource> getCssResources(final HttpServletRequest request, final HttpServletResponse response) {
		final String contextPath = request.getContextPath();
		final List<CssResource> result = new ArrayList<CssResource>(super.getCssResources(request, response));
		result.add(new CssResourceImpl(contextPath + "/util/css/qunit-1.9.0.css"));
		return result;
	}

	@Override
	protected Widget createContentWidget(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException,
		PermissionDeniedException, RedirectException {
		logger.trace("printContent");
		final ListWidget widgets = new ListWidget();
		widgets.add(new H1Widget(getTitle()));
		widgets.add(new DivWidget().addAttribute("id", "qunit"));
		widgets.add(new LinkWidget(new URL("http://qunitjs.com/"), "QUnit-Homepage"));
		return widgets;
	}
}
