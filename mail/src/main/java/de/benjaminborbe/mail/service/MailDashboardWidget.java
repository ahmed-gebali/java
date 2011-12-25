package de.benjaminborbe.mail.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.dashboard.api.DashboardWidget;

@Singleton
public class MailDashboardWidget implements DashboardWidget {

	private final Logger logger;

	@Inject
	public MailDashboardWidget(final Logger logger) {
		this.logger = logger;
	}

	@Override
	public void render(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		logger.debug("render");
		final PrintWriter out = response.getWriter();
		out.println("0 new mails");
	}

	@Override
	public String getTitle() {
		return "Mail";
	}

}
