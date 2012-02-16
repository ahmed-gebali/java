package de.benjaminborbe.search.gui.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.html.api.CssResource;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.JavascriptResource;
import de.benjaminborbe.search.api.SearchSpecial;
import de.benjaminborbe.search.api.SearchResult;
import de.benjaminborbe.search.api.SearchService;
import de.benjaminborbe.search.api.SearchWidget;
import de.benjaminborbe.tools.html.Target;
import de.benjaminborbe.tools.util.SearchUtil;

@Singleton
public class SearchGuiWidgetImpl implements SearchWidget {

	private final Logger logger;

	private final SearchService searchService;

	private final static String PARAMETER_SEARCH = "q";

	private static final int MAX_RESULTS = 20;

	private static final Target target = Target.BLANK;

	private final SearchUtil searchUtil;

	private final SearchGuiDashboardWidget searchDashboardWidget;

	private final SearchGuiSpecialSearchFactory searchGuiSpecialSearchFactory;

	@Inject
	public SearchGuiWidgetImpl(
			final Logger logger,
			final SearchUtil searchUtil,
			final SearchService searchService,
			final SearchGuiDashboardWidget searchDashboardWidget,
			final SearchGuiSpecialSearchFactory searchGuiSpecialSearchFactory) {
		this.logger = logger;
		this.searchUtil = searchUtil;
		this.searchService = searchService;
		this.searchDashboardWidget = searchDashboardWidget;
		this.searchGuiSpecialSearchFactory = searchGuiSpecialSearchFactory;
	}

	@Override
	public void render(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException {
		logger.trace("render");
		final String searchQuery = request.getParameter(PARAMETER_SEARCH);
		logger.trace("searchQuery: " + searchQuery);
		final SearchSpecial searchGuiSpecialSearch = searchGuiSpecialSearchFactory.findSpecial(searchQuery);
		if (searchGuiSpecialSearch != null) {
			logger.trace("found special search");
			searchGuiSpecialSearch.render(request, response, context);
			return;
		}
		else {
			logger.trace("found no special search");
		}

		printSearchForm(request, response, context);
		final String[] words = searchUtil.buildSearchParts(searchQuery);
		final SessionIdentifier sessionIdentifier = new SessionIdentifier(request);
		final List<SearchResult> results = searchService.search(sessionIdentifier, words, MAX_RESULTS);
		printSearchResults(request, response, results);
	}

	@Override
	public List<JavascriptResource> getJavascriptResource(final HttpServletRequest request, final HttpServletResponse response) {
		return searchDashboardWidget.getJavascriptResource(request, response);
	}

	@Override
	public List<CssResource> getCssResource(final HttpServletRequest request, final HttpServletResponse response) {
		return searchDashboardWidget.getCssResource(request, response);
	}

	protected void printSearchResults(final HttpServletRequest request, final HttpServletResponse response, final List<SearchResult> results) throws IOException {
		final PrintWriter out = response.getWriter();
		out.println("<div>");
		out.println("found " + results.size() + " matches");
		out.println("</div>");
		for (final SearchResult result : results) {
			printSearchResult(request, response, result);
		}
	}

	protected void printSearchForm(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException {
		searchDashboardWidget.render(request, response, context);
	}

	protected void printSearchResult(final HttpServletRequest request, final HttpServletResponse response, final SearchResult result) throws IOException {
		final PrintWriter out = response.getWriter();
		final String url = result.getUrl().toExternalForm();
		final String type = result.getType();
		final String title = result.getTitle();
		final String description = result.getDescription();
		out.println("<div class=\"searchResult\">");
		out.println("<div class=\"title\">");
		out.println("<a href=\"" + url + "\" target=\"" + target + "\">");
		out.println("[" + StringEscapeUtils.escapeHtml(type.toUpperCase()) + "] - " + StringEscapeUtils.escapeHtml(title));
		out.println("</a>");
		out.println("</div>");
		out.println("<div class=\"link\">");
		out.println("<a href=\"" + url + "\" target=\"" + target + "\">" + StringEscapeUtils.escapeHtml(url) + "</a>");
		out.println("</div>");
		out.println("<div class=\"description\">");
		out.println(StringEscapeUtils.escapeHtml(description));
		out.println("<br/>");
		out.println("</div>");
		out.println("</div>");
	}

}