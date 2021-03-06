package de.benjaminborbe.slash.gui.servlet;

import com.google.inject.Provider;
import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.website.servlet.WebsiteTextServlet;
import de.benjaminborbe.website.util.ListWidget;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class SlashGuiRobotsTxtServlet extends WebsiteTextServlet {

	private static final long serialVersionUID = -7647639127591841698L;

	@Inject
	public SlashGuiRobotsTxtServlet(
		final Logger logger,
		final AuthenticationService authenticationService,
		final UrlUtil urlUtil,
		final CalendarUtil calendarUtil,
		final TimeZoneUtil timeZoneUtil,
		final Provider<HttpContext> httpContextProvider,
		final AuthorizationService authorizationService
	) {
		super(logger, urlUtil, authenticationService, calendarUtil, timeZoneUtil, httpContextProvider, authorizationService);
	}

	@Override
	protected Widget createContentWidget() throws IOException {
		final ListWidget widgets = new ListWidget();
		widgets.add("User-agent: *\n");
		widgets.add("Disallow: /css/\n");
		widgets.add("Disallow: /images/\n");
		return widgets;
	}

	@Override
	public boolean isAdminRequired() {
		return false;
	}

	@Override
	public boolean isLoginRequired() {
		return false;
	}

}
