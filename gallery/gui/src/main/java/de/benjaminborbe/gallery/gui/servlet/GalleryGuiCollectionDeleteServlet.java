package de.benjaminborbe.gallery.gui.servlet;

import com.google.inject.Provider;
import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.api.AuthenticationServiceException;
import de.benjaminborbe.authentication.api.LoginRequiredException;
import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.gallery.api.GalleryCollectionIdentifier;
import de.benjaminborbe.gallery.api.GalleryService;
import de.benjaminborbe.gallery.api.GalleryServiceException;
import de.benjaminborbe.gallery.gui.GalleryGuiConstants;
import de.benjaminborbe.gallery.gui.util.GalleryGuiWebsiteServlet;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.website.util.RedirectWidget;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class GalleryGuiCollectionDeleteServlet extends GalleryGuiWebsiteServlet {

	private static final long serialVersionUID = 7727468974460815201L;

	private final GalleryService galleryService;

	private final Logger logger;

	private final AuthenticationService authenticationService;

	@Inject
	public GalleryGuiCollectionDeleteServlet(
		final Logger logger,
		final UrlUtil urlUtil,
		final AuthenticationService authenticationService,
		final CalendarUtil calendarUtil,
		final TimeZoneUtil timeZoneUtil,
		final Provider<HttpContext> httpContextProvider,
		final GalleryService galleryService,
		final AuthorizationService authorizationService
	) {
		super(logger, urlUtil, authenticationService, authorizationService, calendarUtil, timeZoneUtil, httpContextProvider, galleryService);
		this.galleryService = galleryService;
		this.logger = logger;
		this.authenticationService = authenticationService;
	}

	@Override
	protected void doService(
		final HttpServletRequest request,
		final HttpServletResponse response,
		final HttpContext context
	) throws ServletException, IOException,
		LoginRequiredException, PermissionDeniedException {
		try {
			final SessionIdentifier sessionIdentifier = authenticationService.createSessionIdentifier(request);
			final GalleryCollectionIdentifier galleryCollectionIdentifier = galleryService.createCollectionIdentifier(request.getParameter(GalleryGuiConstants.PARAMETER_COLLECTION_ID));
			galleryService.deleteCollection(sessionIdentifier, galleryCollectionIdentifier);
		} catch (final GalleryServiceException e) {
			logger.warn(e.getClass().getName(), e);
		} catch (AuthenticationServiceException e) {
			logger.warn(e.getClass().getName(), e);
		}
		final RedirectWidget widget = new RedirectWidget(buildRefererUrl(request));
		widget.render(request, response, context);
	}
}
