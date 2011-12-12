package de.benjaminborbe.monitoring.check;

import org.slf4j.Logger;

import com.google.inject.Inject;

import de.benjaminborbe.tools.util.HttpDownloader;

public class UrlCheckBuilder {

	private static final int RETRY_LIMIT = 3;

	private UrlCheckBuilder() {

	}

	@Inject
	public static void link(final Logger logger, final CheckRegistry registry, final HttpDownloader httpDownloader) {

		{
			final String url = "https://www.twentyfeet.com/index.xhtml";
			final String titleMatch = "TwentyFeet - Social Media Monitoring &amp; Ego tracking";
			final String contentMatch = "<a id=\"logo_l\" href=\"/\">TwentyFeet Online-Performance-Tracking</a>";
			register(logger, registry, httpDownloader, url, titleMatch, contentMatch);
		}

		{
			final String url = "https://frontend1.twentyfeet.com/index.xhtml";
			final String titleMatch = "TwentyFeet - Social Media Monitoring &amp; Ego tracking";
			final String contentMatch = "<a id=\"logo_l\" href=\"/\">TwentyFeet Online-Performance-Tracking</a>";
			register(logger, registry, httpDownloader, url, titleMatch, contentMatch);
		}

		{
			final String url = "https://frontend2.twentyfeet.com/index.xhtml";
			final String titleMatch = "TwentyFeet - Social Media Monitoring &amp; Ego tracking";
			final String contentMatch = "<a id=\"logo_l\" href=\"/\">TwentyFeet Online-Performance-Tracking</a>";
			register(logger, registry, httpDownloader, url, titleMatch, contentMatch);
		}

		{
			final String url = "https://test.twentyfeet.com/index.xhtml";
			final String titleMatch = "TwentyFeet - Social Media Monitoring &amp; Ego tracking";
			final String contentMatch = "<a id=\"logo_l\" href=\"/\">TwentyFeet Online-Performance-Tracking</a>";
			register(logger, registry, httpDownloader, url, titleMatch, contentMatch);
		}

		{
			final String url = "http://www.benjamin-borbe.de/";
			final String titleMatch = "Portrait - Benjamin Borbe";
			final String contentMatch = "<span class=\"photography\">photography</span>";
			register(logger, registry, httpDownloader, url, titleMatch, contentMatch);
		}

		{
			final String url = "http://www.benjaminborbe.de/";
			final String titleMatch = "Portrait - Benjamin Borbe";
			final String contentMatch = "<span class=\"photography\">photography</span>";
			register(logger, registry, httpDownloader, url, titleMatch, contentMatch);
		}

		{
			final String url = "http://www.harteslicht.com/";
			final String titleMatch = "harteslicht.com | Portrait Beauty Shooting in Wiesbaden";
			final String contentMatch = "<div id=\"site-description\">von Benjamin Borbe";
			register(logger, registry, httpDownloader, url, titleMatch, contentMatch);
		}

		{
			final String url = "http://confluence.rocketnews.de/";
			final String titleMatch = "Dashboard - Confluence";
			final String contentMatch = "<span>Dashboard</span>";
			register(logger, registry, httpDownloader, url, titleMatch, contentMatch);
		}

		{
			final String url = "http://0.0.0.0:8161/admin/queues.jsp";
			final String titleMatch = "localhost : Queues";
			final String contentMatch = ">Queues<";
			register(logger, registry, httpDownloader, url, titleMatch, contentMatch);
		}

	}

	protected static void register(final Logger logger, final CheckRegistry registry,
			final HttpDownloader httpDownloader, final String url, final String titleMatch, final String contentMatch) {
		registry.register(new RetryCheck(new UrlCheck(logger, httpDownloader, url, titleMatch, contentMatch), RETRY_LIMIT));
	}
}