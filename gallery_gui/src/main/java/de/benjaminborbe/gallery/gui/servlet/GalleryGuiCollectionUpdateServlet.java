package de.benjaminborbe.gallery.gui.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.api.ValidationError;
import de.benjaminborbe.api.ValidationErrorSimple;
import de.benjaminborbe.api.ValidationException;
import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.api.AuthenticationServiceException;
import de.benjaminborbe.authentication.api.LoginRequiredException;
import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authentication.api.SuperAdminRequiredException;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.gallery.api.GalleryCollection;
import de.benjaminborbe.gallery.api.GalleryCollectionIdentifier;
import de.benjaminborbe.gallery.api.GalleryGroupIdentifier;
import de.benjaminborbe.gallery.api.GalleryService;
import de.benjaminborbe.gallery.api.GalleryServiceException;
import de.benjaminborbe.gallery.gui.GalleryGuiConstants;
import de.benjaminborbe.gallery.gui.util.GalleryGuiLinkFactory;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.navigation.api.NavigationWidget;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.tools.util.ParseException;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.tools.validation.ValidationResultImpl;
import de.benjaminborbe.website.form.FormInputHiddenWidget;
import de.benjaminborbe.website.form.FormInputSubmitWidget;
import de.benjaminborbe.website.form.FormInputTextWidget;
import de.benjaminborbe.website.form.FormWidget;
import de.benjaminborbe.website.servlet.RedirectException;
import de.benjaminborbe.website.util.ExceptionWidget;
import de.benjaminborbe.website.util.H1Widget;
import de.benjaminborbe.website.util.ListWidget;
import de.benjaminborbe.website.widget.ValidationExceptionWidget;

@Singleton
public class GalleryGuiCollectionUpdateServlet extends GalleryGuiHtmlServlet {

	private static final long serialVersionUID = -5013723680643328782L;

	private static final String TITLE = "Gallery - Create";

	private final GalleryService galleryService;

	private final Logger logger;

	private final GalleryGuiLinkFactory galleryGuiLinkFactory;

	private final AuthenticationService authenticationService;

	private final ParseUtil parseUtil;

	@Inject
	public GalleryGuiCollectionUpdateServlet(
			final Logger logger,
			final CalendarUtil calendarUtil,
			final TimeZoneUtil timeZoneUtil,
			final ParseUtil parseUtil,
			final NavigationWidget navigationWidget,
			final AuthenticationService authenticationService,
			final Provider<HttpContext> httpContextProvider,
			final UrlUtil urlUtil,
			final GalleryService galleryService,
			final AuthorizationService authorizationService,
			final GalleryGuiLinkFactory galleryGuiLinkFactory) {
		super(logger, calendarUtil, timeZoneUtil, parseUtil, navigationWidget, authenticationService, authorizationService, httpContextProvider, urlUtil);
		this.galleryService = galleryService;
		this.logger = logger;
		this.galleryGuiLinkFactory = galleryGuiLinkFactory;
		this.authenticationService = authenticationService;
		this.parseUtil = parseUtil;
	}

	@Override
	protected Widget createGalleryContentWidget(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException,
			PermissionDeniedException, RedirectException, LoginRequiredException, SuperAdminRequiredException {
		try {
			final ListWidget widgets = new ListWidget();
			widgets.add(new H1Widget(getTitle()));
			final String id = request.getParameter(GalleryGuiConstants.PARAMETER_COLLECTION_ID);
			final String name = request.getParameter(GalleryGuiConstants.PARAMETER_COLLECTION_NAME);
			final String referer = request.getParameter(GalleryGuiConstants.PARAMETER_REFERER);
			final String prio = request.getParameter(GalleryGuiConstants.PARAMETER_COLLECTION_PRIO);
			final String shared = request.getParameter(GalleryGuiConstants.PARAMETER_COLLECTION_SHARED);
			final String groupId = request.getParameter(GalleryGuiConstants.PARAMETER_GROUP_ID);

			final SessionIdentifier sessionIdentifier = authenticationService.createSessionIdentifier(request);
			final GalleryCollectionIdentifier galleryCollectionIdentifier = galleryService.createCollectionIdentifier(id);
			final GalleryCollection galleryCollection = galleryService.getCollection(sessionIdentifier, galleryCollectionIdentifier);

			if (name != null && prio != null && groupId != null && shared != null) {
				try {

					final GalleryGroupIdentifier galleryGroupIdentifier = galleryService.createGroupIdentifier(groupId);
					updateCollection(sessionIdentifier, galleryCollectionIdentifier, galleryGroupIdentifier, name, prio, shared);

					if (referer != null) {
						throw new RedirectException(referer);
					}
					else {
						throw new RedirectException(galleryGuiLinkFactory.entryListUrl(request, galleryCollectionIdentifier));
					}
				}
				catch (final ValidationException e) {
					widgets.add("create collection => failed");
					widgets.add(new ValidationExceptionWidget(e));
				}
			}
			final FormWidget formWidget = new FormWidget();
			formWidget.addFormInputWidget(new FormInputHiddenWidget(GalleryGuiConstants.PARAMETER_REFERER).addDefaultValue(buildRefererUrl(request)));
			formWidget.addFormInputWidget(new FormInputHiddenWidget(GalleryGuiConstants.PARAMETER_COLLECTION_ID).addValue(galleryCollection.getId()));
			formWidget.addFormInputWidget(new FormInputHiddenWidget(GalleryGuiConstants.PARAMETER_GROUP_ID).addValue(galleryCollection.getGroupId()));
			formWidget.addFormInputWidget(new FormInputTextWidget(GalleryGuiConstants.PARAMETER_COLLECTION_NAME).addLabel("Name...").addDefaultValue(galleryCollection.getName()));
			formWidget.addFormInputWidget(new FormInputTextWidget(GalleryGuiConstants.PARAMETER_COLLECTION_PRIO).addLabel("Prio...").addDefaultValue(galleryCollection.getPriority()));
			formWidget.addFormInputWidget(new FormInputTextWidget(GalleryGuiConstants.PARAMETER_COLLECTION_SHARED).addLabel("Shared").addPlaceholder("shared...")
					.addDefaultValue(galleryCollection.getShared()));
			formWidget.addFormInputWidget(new FormInputSubmitWidget("update"));
			widgets.add(formWidget);
			return widgets;
		}
		catch (final GalleryServiceException e) {
			logger.debug(e.getClass().getName(), e);
			return new ExceptionWidget(e);
		}
		catch (final AuthenticationServiceException e) {
			logger.debug(e.getClass().getName(), e);
			return new ExceptionWidget(e);
		}
	}

	private void updateCollection(final SessionIdentifier sessionIdentifier, final GalleryCollectionIdentifier galleryCollectionIdentifier,
			final GalleryGroupIdentifier galleryGroupIdentifier, final String name, final String prioString, final String sharedString) throws GalleryServiceException,
			LoginRequiredException, PermissionDeniedException, ValidationException, SuperAdminRequiredException {

		Long prio;
		Boolean shared;
		final List<ValidationError> errors = new ArrayList<ValidationError>();
		{
			try {
				if (prioString == null || prioString.length() == 0) {
					prio = null;
				}
				else {
					prio = parseUtil.parseLong(prioString);
				}
			}
			catch (final ParseException e) {
				prio = null;
				errors.add(new ValidationErrorSimple("illegal prio"));
			}
		}
		{
			try {
				shared = parseUtil.parseBoolean(sharedString);
			}
			catch (final ParseException e) {
				shared = null;
				errors.add(new ValidationErrorSimple("illegal shared"));
			}
		}

		if (!errors.isEmpty()) {
			throw new ValidationException(new ValidationResultImpl(errors));
		}
		else {
			galleryService.updateCollection(sessionIdentifier, galleryCollectionIdentifier, galleryGroupIdentifier, name, prio, shared);
		}
	}

	@Override
	protected String getTitle() {
		return TITLE;
	}
}