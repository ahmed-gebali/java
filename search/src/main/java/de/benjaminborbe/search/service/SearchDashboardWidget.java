package de.benjaminborbe.search.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.dashboard.api.CssResource;
import de.benjaminborbe.dashboard.api.CssResourceImpl;
import de.benjaminborbe.dashboard.api.DashboardWidget;
import de.benjaminborbe.dashboard.api.JavascriptResource;
import de.benjaminborbe.dashboard.api.JavascriptResourceImpl;
import de.benjaminborbe.dashboard.api.RequireCssResource;
import de.benjaminborbe.dashboard.api.RequireJavascriptResource;

@Singleton
public class SearchDashboardWidget implements DashboardWidget, RequireCssResource, RequireJavascriptResource {

	private static final String TITLE = "SearchDashboardWidget";

	private final static String PARAMETER_SEARCH = "q";

	private final Logger logger;

	@Inject
	public SearchDashboardWidget(final Logger logger) {
		this.logger = logger;
	}

	@Override
	public void render(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		logger.debug("render");

		final PrintWriter out = response.getWriter();
		final String contextPath = request.getContextPath();
		final String searchSuggestUrl = contextPath + "/search/suggest";
		final String searchQuery = request.getParameter(PARAMETER_SEARCH);

		final String action = contextPath + "/search";
		out.println("<form method=\"GET\" action=\"" + action + "\">");
		out.println("<input name=\"q\" id=\"searchBox\" type=\"text\""
				+ (searchQuery != null ? StringEscapeUtils.escapeHtml(searchQuery) : "") + "\" />");
		out.println("<input type=\"submit\" value=\"search\" />");
		out.println("</form>");
		out.println("<script language=\"javascript\">");
		out.println("$(document).ready(function() {");
		out.println("$('input#searchBox').autocomplete({");
		out.println("source: '" + searchSuggestUrl + "',");
		out.println("method: 'POST',");
		out.println("minLength: 1,");
		out.println("});");
		out.println("});");
		out.println("</script>");
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public Collection<JavascriptResource> getJavascriptResource(final HttpServletRequest request,
			final HttpServletResponse response) {
		final Set<JavascriptResource> result = new HashSet<JavascriptResource>();
		result.add(new JavascriptResourceImpl("http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"));
		result.add(new JavascriptResourceImpl("http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"));
		return result;
	}

	@Override
	public Collection<CssResource> getCssResource(final HttpServletRequest request, final HttpServletResponse response) {
		final String contextPath = request.getContextPath();
		final Set<CssResource> result = new HashSet<CssResource>();
		result.add(new CssResourceImpl("http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"));
		result.add(new CssResourceImpl(contextPath + "/search/css/style.css"));
		return result;
	}
}