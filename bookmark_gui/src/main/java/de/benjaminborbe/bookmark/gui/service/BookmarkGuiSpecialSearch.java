package de.benjaminborbe.bookmark.gui.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.bookmark.api.Bookmark;
import de.benjaminborbe.bookmark.api.BookmarkService;
import de.benjaminborbe.bookmark.api.BookmarkServiceException;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.search.api.SearchSpecial;
import de.benjaminborbe.tools.util.SearchUtil;
import de.benjaminborbe.website.util.ExceptionWidget;
import de.benjaminborbe.website.util.H2Widget;
import de.benjaminborbe.website.util.ListWidget;

@Singleton
public class BookmarkGuiSpecialSearch implements SearchSpecial {

	private final static String PARAMETER_SEARCH = "q";

	private final BookmarkService bookmarkService;

	private final SearchUtil searchUtil;

	@Inject
	public BookmarkGuiSpecialSearch(final BookmarkService bookmarkService, final SearchUtil searchUtil) {
		this.bookmarkService = bookmarkService;
		this.searchUtil = searchUtil;
	}

	@Override
	public String getName() {
		return "b";
	}

	@Override
	public void render(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException {
		try {
			final String searchQuery = request.getParameter(PARAMETER_SEARCH);
			final String[] words = searchUtil.buildSearchParts(searchQuery.substring(searchQuery.indexOf(":")));
			final SessionIdentifier sessionIdentifier = new SessionIdentifier(request);
			final List<Bookmark> bookmarks = bookmarkService.searchBookmarks(sessionIdentifier, words);
			if (bookmarks.size() > 0) {
				response.sendRedirect(bookmarks.get(0).getUrl());
			}
			else {
				final ListWidget widgets = new ListWidget();
				widgets.add(new H2Widget("Bookmarksearch"));
				widgets.add("no match");
				widgets.render(request, response, context);
			}
		}
		catch (final BookmarkServiceException e) {
			final ExceptionWidget widget = new ExceptionWidget(e);
			widget.render(request, response, context);
		}
	}
}