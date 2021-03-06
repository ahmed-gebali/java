package de.benjaminborbe.gallery.gui.servlet;

import com.google.common.collect.Lists;
import com.google.inject.Provider;
import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.api.AuthenticationServiceException;
import de.benjaminborbe.authentication.api.LoginRequiredException;
import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.cache.api.CacheService;
import de.benjaminborbe.gallery.api.GalleryCollection;
import de.benjaminborbe.gallery.api.GalleryCollectionIdentifier;
import de.benjaminborbe.gallery.api.GalleryEntry;
import de.benjaminborbe.gallery.api.GalleryGroup;
import de.benjaminborbe.gallery.api.GalleryService;
import de.benjaminborbe.gallery.api.GalleryServiceException;
import de.benjaminborbe.gallery.gui.GalleryGuiConstants;
import de.benjaminborbe.gallery.gui.util.GalleryEntryComparator;
import de.benjaminborbe.gallery.gui.util.GalleryGuiLinkFactory;
import de.benjaminborbe.gallery.gui.util.GalleryGuiWebsiteHtmlServlet;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.navigation.api.NavigationWidget;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.website.servlet.RedirectException;
import de.benjaminborbe.website.util.DivWidget;
import de.benjaminborbe.website.util.ExceptionWidget;
import de.benjaminborbe.website.util.H1Widget;
import de.benjaminborbe.website.util.ListWidget;
import de.benjaminborbe.website.util.UlWidget;
import de.benjaminborbe.website.widget.BrWidget;
import de.benjaminborbe.website.widget.ImageWidget;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Singleton
public class GalleryGuiEntryListServlet extends GalleryGuiWebsiteHtmlServlet {

	private static final long serialVersionUID = 1328676176772634649L;

	private static final String TITLE = "Gallery - Images";

	private final GalleryService galleryService;

	private final Logger logger;

	private final GalleryGuiLinkFactory galleryGuiLinkFactory;

	private final AuthenticationService authenticationService;

	private final GalleryEntryComparator galleryEntryComparator;

	@Inject
	public GalleryGuiEntryListServlet(
		final Logger logger,
		final CalendarUtil calendarUtil,
		final TimeZoneUtil timeZoneUtil,
		final ParseUtil parseUtil,
		final AuthenticationService authenticationService,
		final NavigationWidget navigationWidget,
		final Provider<HttpContext> httpContextProvider,
		final UrlUtil urlUtil,
		final GalleryService galleryService,
		final AuthorizationService authorizationService,
		final GalleryGuiLinkFactory galleryGuiLinkFactory,
		final GalleryEntryComparator galleryEntryComparator,
		final CacheService cacheService
	) {
		super(logger, calendarUtil, timeZoneUtil, parseUtil, navigationWidget, authenticationService, authorizationService, httpContextProvider, urlUtil, cacheService, galleryService);
		this.galleryService = galleryService;
		this.logger = logger;
		this.galleryGuiLinkFactory = galleryGuiLinkFactory;
		this.authenticationService = authenticationService;
		this.galleryEntryComparator = galleryEntryComparator;
	}

	@Override
	protected String getTitle() {
		return TITLE;
	}

	@Override
	protected Widget createGalleryContentWidget(final HttpServletRequest request) throws IOException,
		PermissionDeniedException, RedirectException, LoginRequiredException {
		try {
			final SessionIdentifier sessionIdentifier = authenticationService.createSessionIdentifier(request);
			final String galleryId = request.getParameter(GalleryGuiConstants.PARAMETER_COLLECTION_ID);
			final GalleryCollectionIdentifier galleryCollectionIdentifier = galleryService.createCollectionIdentifier(galleryId);
			final GalleryCollection galleryCollection = galleryService.getCollection(sessionIdentifier, galleryCollectionIdentifier);
			final GalleryGroup galleryGroup = galleryService.getGroup(sessionIdentifier, galleryCollection.getGroupId());

			final ListWidget widgets = new ListWidget();
			widgets.add(new H1Widget(galleryGroup.getName() + "/" + galleryCollection.getName() + " - Images"));
			final UlWidget ul = new UlWidget();

			final List<GalleryEntry> entries = Lists.newArrayList(galleryService.getEntries(sessionIdentifier, galleryCollectionIdentifier));
			Collections.sort(entries, galleryEntryComparator);

			for (int i = 0; i < entries.size(); ++i) {
				final GalleryEntry galleryEntry = entries.get(i);
				final ListWidget list = new ListWidget();
				list.add(new ImageWidget(galleryGuiLinkFactory.createImage(request, galleryEntry.getPreviewImageIdentifier())));
				list.add(new ImageWidget(galleryGuiLinkFactory.createImage(request, galleryEntry.getImageIdentifier())));
				list.add(new BrWidget());

				if (i > 0) {
					list.add(galleryGuiLinkFactory.swapEntryPrio(request, galleryEntry.getId(), entries.get(i - 1).getId(), "up"));
					list.add(" ");
				}
				if (i < entries.size() - 1) {
					list.add(galleryGuiLinkFactory.swapEntryPrio(request, galleryEntry.getId(), entries.get(i + 1).getId(), "down"));
					list.add(" ");
				}
				final boolean shared = Boolean.TRUE.equals(galleryEntry.getShared());
				if (shared) {
					list.add(galleryGuiLinkFactory.unshareEntry(request, galleryEntry.getId()));
				} else {
					list.add(galleryGuiLinkFactory.shareEntry(request, galleryEntry.getId()));
				}
				list.add(" ");
				list.add(galleryGuiLinkFactory.updateEntry(request, galleryEntry.getId()));
				list.add(" ");
				list.add(galleryGuiLinkFactory.deleteEntry(request, galleryEntry.getId()));

				ul.add(new DivWidget(list).addAttribute("class", shared ? "entryShared" : "entryNotShared"));
			}
			ul.addClass("entrylist");
			widgets.add(ul);

			final ListWidget links = new ListWidget();
			links.add(galleryGuiLinkFactory.listGroups(request));
			links.add(" ");
			links.add(galleryGuiLinkFactory.listCollections(request, galleryGroup.getId()));
			links.add(" ");
			links.add(galleryGuiLinkFactory.createEntry(request, galleryCollectionIdentifier));
			links.add(" ");

			widgets.add(links);
			return widgets;
		} catch (final GalleryServiceException e) {
			logger.debug(e.getClass().getName(), e);
			return new ExceptionWidget(e);
		} catch (final AuthenticationServiceException e) {
			logger.debug(e.getClass().getName(), e);
			return new ExceptionWidget(e);
		}
	}
}
