package de.benjaminborbe.microblog.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.dashboard.api.DashboardContentWidget;
import de.benjaminborbe.html.api.CssResource;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.RequireCssResource;
import de.benjaminborbe.microblog.util.MicroblogRevisionStorage;
import de.benjaminborbe.tools.html.Target;

@Singleton
public class MicroblogDashboardWidget implements DashboardContentWidget, RequireCssResource {

	private static final String TITLE = "Microblog";

	private final Logger logger;

	private final MicroblogRevisionStorage microblogRevisionStorage;

	@Inject
	public MicroblogDashboardWidget(final Logger logger, final MicroblogRevisionStorage microblogRevisionStorage) {
		this.logger = logger;
		this.microblogRevisionStorage = microblogRevisionStorage;
	}

	@Override
	public void render(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException {
		logger.debug("render");
		final PrintWriter out = response.getWriter();
		out.println("latest revision: " + microblogRevisionStorage.getLastRevision() + "<br/>");
		out.println("<a href=\"https://micro.rp.seibert-media.net/\" target=\"" + Target.BLANK + "\">Microblog</a>");
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public long getPriority() {
		return 1;
	}

	@Override
	public List<CssResource> getCssResource(final HttpServletRequest request, final HttpServletResponse response) {
		return new ArrayList<CssResource>();
	}

}
