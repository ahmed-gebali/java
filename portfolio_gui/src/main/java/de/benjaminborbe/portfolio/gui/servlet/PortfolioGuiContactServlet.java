package de.benjaminborbe.portfolio.gui.servlet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.portfolio.gui.widget.PortfolioLayoutWidget;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.website.link.LinkMailtoWidget;
import de.benjaminborbe.website.link.LinkSkypeWidget;
import de.benjaminborbe.website.link.LinkWidget;
import de.benjaminborbe.website.servlet.WebsiteWidgetServlet;
import de.benjaminborbe.website.util.H1Widget;
import de.benjaminborbe.website.util.ListWidget;
import de.benjaminborbe.website.widget.BrWidget;
import de.benjaminborbe.website.widget.ImageWidget;

@Singleton
public class PortfolioGuiContactServlet extends WebsiteWidgetServlet {

	private static final long serialVersionUID = 1328676176772634649L;

	private final PortfolioLayoutWidget portfolioWidget;

	@Inject
	public PortfolioGuiContactServlet(
			final Logger logger,
			final UrlUtil urlUtil,
			final CalendarUtil calendarUtil,
			final TimeZoneUtil timeZoneUtil,
			final Provider<HttpContext> httpContextProvider,
			final AuthenticationService authenticationService,
			final PortfolioLayoutWidget portfolioWidget) {
		super(logger, urlUtil, calendarUtil, timeZoneUtil, httpContextProvider, authenticationService);
		this.portfolioWidget = portfolioWidget;
	}

	protected Widget createContentWidget(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException {
		final ListWidget widgets = new ListWidget();
		widgets.add(new H1Widget("Contact"));
		widgets.add("Benjamin Borbe");
		widgets.add(new BrWidget());
		widgets.add("Darmstädter Landstr. 117");
		widgets.add(new BrWidget());
		widgets.add("65462 Ginsheim-Gustavsburg");
		widgets.add(new BrWidget());
		widgets.add("Mobil: 0178-1970808");
		widgets.add(new BrWidget());
		widgets.add(new LinkMailtoWidget("info@benjamin-borbe.de"));
		widgets.add(new BrWidget());
		widgets.add(createFacebookLink());
		widgets.add(new BrWidget());
		widgets.add(createTwitterLink());
		widgets.add(new BrWidget());
		widgets.add(createGooglePlusLink());
		widgets.add(new BrWidget());
		widgets.add(createICQLink());
		widgets.add(new BrWidget());
		widgets.add(createSkypeLink());
		return widgets;
	}

	private Widget createSkypeLink() {
		final ListWidget content = new ListWidget();
		content.add(new ImageWidget("images/icons/skype.png").addAlt("Skype").addHeight(15).addWidth(15));
		content.add(" ");
		content.add("Skype (bborbe)");
		return new LinkSkypeWidget("bborbe", content);
	}

	private Widget createICQLink() throws MalformedURLException {
		final ListWidget content = new ListWidget();
		content.add(new ImageWidget("images/icons/icq.png").addAlt("ICQ").addHeight(15).addWidth(15));
		content.add(" ");
		content.add("ICQ (28535634)");
		return new LinkWidget(new URL("http://wwp.icq.com/scripts/search.dll?to=28535634"), content);
	}

	private Widget createGooglePlusLink() throws MalformedURLException {
		final ListWidget content = new ListWidget();
		content.add(new ImageWidget("images/icons/googleplus.gif").addAlt("Google+").addHeight(15).addWidth(15));
		content.add(" ");
		content.add("Google+");
		return new LinkWidget(new URL("https://plus.google.com/104705832645903707938"), content);
	}

	private Widget createFacebookLink() throws MalformedURLException {
		final ListWidget content = new ListWidget();
		content.add(new ImageWidget("images/icons/facebook.png").addAlt("Facebook").addHeight(15).addWidth(15));
		content.add(" ");
		content.add("Facebook");
		return new LinkWidget(new URL("https://www.facebook.com/benjamin.borbe"), content);
	}

	private Widget createTwitterLink() throws MalformedURLException {
		final ListWidget content = new ListWidget();
		content.add(new ImageWidget("images/icons/twitter.png").addAlt("Twitter").addHeight(15).addWidth(15));
		content.add(" ");
		content.add("Twitter");
		return new LinkWidget(new URL("https://twitter.com/bborbe"), content);
	}

	@Override
	public Widget createWidget(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException {
		portfolioWidget.addTitle("Portfolio - Benjamin Borbe - Contact");
		portfolioWidget.addContent(createContentWidget(request, response, context));
		return portfolioWidget;
	}

	@Override
	protected boolean isLoginRequired() {
		return false;
	}

}
