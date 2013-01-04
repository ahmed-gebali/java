package de.benjaminborbe.confluence.connector;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.confluence.ConfluenceConstants;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;

@Singleton
public class ConfluenceConnectorImpl implements ConfluenceConnector {

	private final Logger logger;

	private final CalendarUtil calendarUtil;

	private final TimeZoneUtil timeZoneUtil;

	@Inject
	public ConfluenceConnectorImpl(final Logger logger, final CalendarUtil calendarUtil, final TimeZoneUtil timeZoneUtil) {
		this.logger = logger;
		this.calendarUtil = calendarUtil;
		this.timeZoneUtil = timeZoneUtil;
	}

	@Override
	public String login(final String confluenceBaseUrl, final String username, final String password) throws MalformedURLException, XmlRpcException {
		logger.debug("login");
		final XmlRpcClient client = getClient(confluenceBaseUrl);
		final String token = (String) client.execute("confluence1.login", new Object[] { username, password });
		return token;
	}

	private XmlRpcClient getClient(final String confluenceBaseUrl) throws MalformedURLException {
		final URL url = new URL(confluenceBaseUrl + "/rpc/xmlrpc");
		final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(url);
		config.setConnectionTimeout(ConfluenceConstants.CONNECTION_TIMEOUT);
		config.setReplyTimeout(ConfluenceConstants.CONNECTION_TIMEOUT);
		final XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		return client;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getRenderedContent(final String confluenceBaseUrl, final String token, final String spaceName, final String pageName) throws MalformedURLException, XmlRpcException {
		logger.debug("getRenderedContent");
		final XmlRpcClient client = getClient(confluenceBaseUrl);
		final Map page = (Map) client.execute("confluence1.getPage", new Object[] { token, spaceName, pageName });
		final String pageId = (String) page.get("id");
		final String content = (String) page.get("content");

		return (String) client.execute("confluence1.renderContent", new Object[] { token, "Main", pageId, content });
	}

	@Override
	public String getRenderedContent(final String confluenceBaseUrl, final String token, final String pageId) throws MalformedURLException, XmlRpcException {
		logger.debug("getRenderedContent");
		final XmlRpcClient client = getClient(confluenceBaseUrl);
		return (String) client.execute("confluence1.renderContent", new Object[] { token, "Main", pageId, "" });
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Collection<String> getSpaceKeys(final String confluenceBaseUrl, final String token) throws MalformedURLException, XmlRpcException {
		logger.debug("getSpaceKeys");
		final XmlRpcClient client = getClient(confluenceBaseUrl);
		final Object[] spaces = (Object[]) client.execute("confluence1.getSpaces", new Object[] { token });
		final List<String> result = new ArrayList<String>();
		for (final Object spaceObject : spaces) {
			final Map space = (Map) spaceObject;
			result.add(String.valueOf(space.get("key")));
		}
		return result;
	}

	@Override
	public ConfluenceConnectorPage getPage(final String confluenceBaseUrl, final String token, final ConfluenceConnectorPageSummary confluenceConnectorPageSummary)
			throws MalformedURLException, XmlRpcException {
		return getPage(confluenceBaseUrl, token, confluenceConnectorPageSummary.getPageId());
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public ConfluenceConnectorPage getPage(final String confluenceBaseUrl, final String token, final String pageId) throws MalformedURLException, XmlRpcException {
		logger.debug("getPages");
		final XmlRpcClient client = getClient(confluenceBaseUrl);
		final Object pageObject = client.execute("confluence1.getPage", new Object[] { token, pageId });
		final Map page = (Map) pageObject;
		logger.debug(StringUtils.join(page.keySet(), ","));
		return new ConfluenceConnectorPage(String.valueOf(page.get("id")), String.valueOf(page.get("url")), String.valueOf(page.get("title")), calendarUtil.parseTimestamp(
				timeZoneUtil.getUTCTimeZone(), String.valueOf(page.get("modified")), null));
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Collection<ConfluenceConnectorPageSummary> getPageSummaries(final String confluenceBaseUrl, final String token, final String spaceKey) throws MalformedURLException,
			XmlRpcException {
		logger.debug("getPages");
		final XmlRpcClient client = getClient(confluenceBaseUrl);
		final Object[] pages = (Object[]) client.execute("confluence1.getPages", new Object[] { token, spaceKey });
		final List<ConfluenceConnectorPageSummary> result = new ArrayList<ConfluenceConnectorPageSummary>();
		for (final Object pageObject : pages) {
			final Map page = (Map) pageObject;
			result.add(new ConfluenceConnectorPageSummary(String.valueOf(page.get("id")), String.valueOf(page.get("url")), String.valueOf(page.get("title"))));
		}
		return result;
	}
}
