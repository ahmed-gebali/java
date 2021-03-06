package de.benjaminborbe.microblog.gui.servlet;

import com.google.inject.Provider;
import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.api.LoginRequiredException;
import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.microblog.api.MicroblogService;
import de.benjaminborbe.microblog.api.MicroblogServiceException;
import de.benjaminborbe.microblog.gui.MicroblogGuiConstants;
import de.benjaminborbe.microblog.gui.config.MicroblogGuiConfig;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.json.JSONArray;
import de.benjaminborbe.tools.json.JSONArraySimple;
import de.benjaminborbe.tools.json.JSONObject;
import de.benjaminborbe.tools.json.JSONObjectSimple;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.website.servlet.WebsiteJsonServlet;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class MicroblogGuiNotificationListJsonServlet extends WebsiteJsonServlet {

	private static final long serialVersionUID = 1885838810460233686L;

	private final Logger logger;

	private final MicroblogGuiConfig microblogGuiConfig;

	private final MicroblogService microblogService;

	@Inject
	public MicroblogGuiNotificationListJsonServlet(
		final Logger logger,
		final UrlUtil urlUtil,
		final AuthenticationService authenticationService,
		final AuthorizationService authorizationService,
		final CalendarUtil calendarUtil,
		final TimeZoneUtil timeZoneUtil,
		final Provider<HttpContext> httpContextProvider,
		final MicroblogGuiConfig microblogGuiConfig,
		final MicroblogService microblogService
	) {
		super(logger, urlUtil, authenticationService, authorizationService, calendarUtil, timeZoneUtil, httpContextProvider);
		this.logger = logger;
		this.microblogGuiConfig = microblogGuiConfig;
		this.microblogService = microblogService;
	}

	@Override
	protected void doService(
		final HttpServletRequest request,
		final HttpServletResponse response,
		final HttpContext context
	) throws ServletException, IOException,
		PermissionDeniedException, LoginRequiredException {
		try {
			final String token = request.getParameter(MicroblogGuiConstants.PARAEMTER_NOTIFICATION_TOKEN);
			if (token == null || token.isEmpty() || !token.equals(microblogGuiConfig.getAuthToken())) {
				printError(response, "parameter " + MicroblogGuiConstants.PARAEMTER_NOTIFICATION_TOKEN + " missing or invalid");
				return;
			}

			final String login = request.getParameter(MicroblogGuiConstants.PARAEMTER_NOTIFICATION_LOGIN);
			if (login == null || login.isEmpty()) {
				printError(response, "parameter " + MicroblogGuiConstants.PARAEMTER_NOTIFICATION_LOGIN + " missing");
				return;
			}

			final Collection<String> keywords = microblogService.listNotifications(new UserIdentifier(login));
			final JSONArray ws = new JSONArraySimple();
			ws.addAll(keywords);

			logger.debug("list notification-setting for user: " + login);
			final JSONObject jsonObject = new JSONObjectSimple();
			jsonObject.put("result", "success");
			jsonObject.put("keywords", ws);
			printJson(response, jsonObject);
		} catch (final MicroblogServiceException e) {
			logger.warn(e.getClass().getName(), e);
			printException(response, e);
		}
	}

	@Override
	public boolean isLoginRequired() {
		return false;
	}

	@Override
	public boolean isAdminRequired() {
		return false;
	}

}
