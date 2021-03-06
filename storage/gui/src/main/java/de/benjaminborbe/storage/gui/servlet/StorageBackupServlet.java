package de.benjaminborbe.storage.gui.servlet;

import com.google.inject.Provider;
import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.api.LoginRequiredException;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.cache.api.CacheService;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.navigation.api.NavigationWidget;
import de.benjaminborbe.storage.api.StorageException;
import de.benjaminborbe.storage.api.StorageService;
import de.benjaminborbe.storage.gui.StorageGuiConstants;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.website.form.FormInputSubmitWidget;
import de.benjaminborbe.website.form.FormInputTextWidget;
import de.benjaminborbe.website.form.FormMethod;
import de.benjaminborbe.website.form.FormWidget;
import de.benjaminborbe.website.servlet.RedirectException;
import de.benjaminborbe.website.util.ExceptionWidget;
import de.benjaminborbe.website.util.H1Widget;
import de.benjaminborbe.website.util.ListWidget;
import de.benjaminborbe.website.widget.BrWidget;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class StorageBackupServlet extends StorageHtmlServlet {

	private static final String ALL = "all";

	private static final long serialVersionUID = 1048276599809672509L;

	private static final String TITLE = "Storage - Backup";

	private final Logger logger;

	private final StorageService persistentStorageService;

	@Inject
	public StorageBackupServlet(
		final Logger logger,
		final CalendarUtil calendarUtil,
		final TimeZoneUtil timeZoneUtil,
		final ParseUtil parseUtil,
		final NavigationWidget navigationWidget,
		final AuthenticationService authenticationService,
		final AuthorizationService authorizationService,
		final Provider<HttpContext> httpContextProvider,
		final UrlUtil urlUtil,
		final StorageService persistentStorageService,
		final CacheService cacheService
	) {
		super(logger, calendarUtil, timeZoneUtil, parseUtil, navigationWidget, authenticationService, authorizationService, httpContextProvider, urlUtil, cacheService);
		this.logger = logger;
		this.persistentStorageService = persistentStorageService;
	}

	@Override
	protected Widget createContentWidget(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException,
		PermissionDeniedException, RedirectException, LoginRequiredException {
		try {
			logger.trace("printContent");
			final ListWidget widgets = new ListWidget();
			widgets.add(new H1Widget(getTitle()));

			final String cf = request.getParameter(StorageGuiConstants.PARAMETER_COLUMNFAMILY);
			if (cf != null && !cf.trim().isEmpty()) {
				if (ALL.equals(cf)) {
					persistentStorageService.backup();
				} else {
					persistentStorageService.backup(cf);
				}
				widgets.add("backup done");
				widgets.add(new BrWidget());
			}

			widgets.add("use " + ALL + " to backup all columnfamilies");
			final FormWidget form = new FormWidget().addMethod(FormMethod.POST);
			form.addFormInputWidget(new FormInputTextWidget(StorageGuiConstants.PARAMETER_COLUMNFAMILY).addLabel("ColumnFamily"));
			form.addFormInputWidget(new FormInputSubmitWidget("backup"));
			widgets.add(form);

			return widgets;
		} catch (final StorageException e) {
			return new ExceptionWidget(e);
		}
	}

	@Override
	protected String getTitle() {
		return TITLE;
	}
}
