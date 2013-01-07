package de.benjaminborbe.confluence.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.xmlrpc.XmlRpcException;
import org.slf4j.Logger;

import com.google.inject.Inject;

import de.benjaminborbe.confluence.config.ConfluenceConfig;
import de.benjaminborbe.confluence.connector.ConfluenceConnector;
import de.benjaminborbe.confluence.connector.ConfluenceConnectorPage;
import de.benjaminborbe.confluence.connector.ConfluenceConnectorPageSummary;
import de.benjaminborbe.confluence.connector.ConfluenceSession;
import de.benjaminborbe.confluence.dao.ConfluenceInstanceBean;
import de.benjaminborbe.confluence.dao.ConfluenceInstanceDao;
import de.benjaminborbe.confluence.dao.ConfluencePageBean;
import de.benjaminborbe.confluence.dao.ConfluencePageDao;
import de.benjaminborbe.index.api.IndexService;
import de.benjaminborbe.index.api.IndexerServiceException;
import de.benjaminborbe.storage.api.StorageException;
import de.benjaminborbe.storage.tools.EntityIterator;
import de.benjaminborbe.storage.tools.EntityIteratorException;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.html.HtmlUtil;
import de.benjaminborbe.tools.synchronize.RunOnlyOnceATime;

public class ConfluenceRefresher {

	private final class RefreshRunnable implements Runnable {

		@Override
		public void run() {
			try {
				logger.debug("refresh started");
				final EntityIterator<ConfluenceInstanceBean> i = confluenceInstanceDao.getActivatedEntityIterator();
				int counter = 0;
				while (i.hasNext()) {
					counter++;
					try {
						final ConfluenceInstanceBean confluenceInstance = i.next();
						handle(confluenceInstance);
					}
					catch (final MalformedURLException e) {
						logger.warn(e.getClass().getName(), e);
					}
				}
				logger.debug("refresh of " + counter + " confluenceInstances finished");
			}
			catch (final StorageException e) {
				logger.warn(e.getClass().getName(), e);
			}
			catch (final XmlRpcException e) {
				logger.warn(e.getClass().getName(), e);
			}
			catch (final EntityIteratorException e) {
				logger.warn(e.getClass().getName(), e);
			}
			finally {
				logger.debug("refresh finished");
			}
		}
	}

	// 1 day
	private static final long EXPIRE_DAY = 24l * 60l * 60l * 1000l;

	private static final long DEFAULT_DELAY = 300;

	private final Logger logger;

	private final IndexService indexerService;

	private final ConfluenceInstanceDao confluenceInstanceDao;

	private final ConfluenceConnector confluenceConnector;

	private final HtmlUtil htmlUtil;

	private final ConfluencePageDao confluencePageDao;

	private final CalendarUtil calendarUtil;

	private final ConfluenceIndexUtil confluenceIndexUtil;

	private final RunOnlyOnceATime runOnlyOnceATime;

	private final TimeZoneUtil timeZoneUtil;

	private final ConfluenceConfig confluenceConfig;

	@Inject
	public ConfluenceRefresher(
			final Logger logger,
			final RunOnlyOnceATime runOnlyOnceATime,
			final CalendarUtil calendarUtil,
			final IndexService indexerService,
			final ConfluenceInstanceDao confluenceInstanceDao,
			final ConfluencePageDao confluencePageDao,
			final ConfluenceConnector confluenceConnector,
			final HtmlUtil htmlUtil,
			final TimeZoneUtil timeZoneUtil,
			final ConfluenceConfig confluenceConfig,
			final ConfluenceIndexUtil confluenceIndexUtil) {
		this.logger = logger;
		this.runOnlyOnceATime = runOnlyOnceATime;
		this.calendarUtil = calendarUtil;
		this.indexerService = indexerService;
		this.confluenceInstanceDao = confluenceInstanceDao;
		this.confluencePageDao = confluencePageDao;
		this.confluenceConnector = confluenceConnector;
		this.htmlUtil = htmlUtil;
		this.timeZoneUtil = timeZoneUtil;
		this.confluenceConfig = confluenceConfig;
		this.confluenceIndexUtil = confluenceIndexUtil;
	}

	private void handle(final ConfluenceInstanceBean confluenceInstanceBean) throws MalformedURLException, XmlRpcException {
		final long delay = getDelay(confluenceInstanceBean);
		final String indexName = confluenceIndexUtil.getIndex(confluenceInstanceBean);
		int counter = 0;
		final String confluenceBaseUrl = confluenceInstanceBean.getUrl();
		final String username = confluenceInstanceBean.getUsername();
		final String password = confluenceInstanceBean.getPassword();
		final ConfluenceSession token = confluenceConnector.login(confluenceBaseUrl, username, password);
		final Collection<String> spaceKeys = confluenceConnector.getSpaceKeys(confluenceBaseUrl, token);
		logger.debug("found " + spaceKeys.size() + " spaces in " + confluenceBaseUrl);
		for (final String spaceKey : spaceKeys) {
			logger.debug("process space " + spaceKey);
			final Collection<ConfluenceConnectorPageSummary> pageSummaries = confluenceConnector.getPageSummaries(confluenceBaseUrl, token, spaceKey);
			logger.debug("found " + pageSummaries.size() + " pages in space " + spaceKey);
			for (final ConfluenceConnectorPageSummary pageSummary : pageSummaries) {

				if (confluenceConfig.getRefreshLimit() != null && counter >= confluenceConfig.getRefreshLimit()) {
					logger.debug("refresh limit reached => exit refresh");
					return;
				}

				final ConfluenceConnectorPage page = confluenceConnector.getPage(confluenceBaseUrl, token, pageSummary);
				final Calendar pageModified = toCalendar(page.getModified());
				logger.debug("process page " + page.getTitle() + " lastmodified: " + calendarUtil.toDateTimeString(pageModified));
				try {
					// check expire
					final ConfluencePageBean pageBean = confluencePageDao.findOrCreate(confluenceInstanceBean.getId(), indexName, page.getPageId());
					if (isExpired(confluenceInstanceBean, pageBean, page)) {

						counter++;
						logger.debug("refresh-counter: " + counter);
						logger.debug("update page " + page.getTitle());
						final String content = confluenceConnector.getRenderedContent(confluenceBaseUrl, token, page.getPageId());
						final URL url = new URL(page.getUrl());
						final String title = page.getTitle();

						indexerService.addToIndex(indexName, url, title, filterContent(content));

						logger.info("addToIndex " + url.toExternalForm());

						// update lastVisit
						pageBean.setLastVisit(pageModified);
						pageBean.setPageId(page.getPageId());
						pageBean.setOwner(confluenceInstanceBean.getOwner());
						pageBean.setInstanceId(confluenceInstanceBean.getId());
						pageBean.setUrl(url);
						confluencePageDao.save(pageBean);

						// Throttle crawling
						try {
							Thread.sleep(delay);
						}
						catch (final InterruptedException e) {
						}
					}
					else {
						logger.debug("skip page " + page.getTitle());
					}
				}
				catch (final IndexerServiceException e) {
					logger.warn(e.getClass().getName(), e);
				}
				catch (final StorageException e) {
					logger.warn(e.getClass().getName(), e);
				}
			}
		}
	}

	private long getDelay(final ConfluenceInstanceBean confluenceInstanceBean) {
		if (confluenceInstanceBean.getDelay() != null && confluenceInstanceBean.getDelay() >= 0) {
			return confluenceInstanceBean.getDelay();
		}
		return DEFAULT_DELAY;
	}

	private boolean isExpired(final ConfluenceInstanceBean confluenceInstanceBean, final ConfluencePageBean pageBean, final ConfluenceConnectorPage page) {
		final Calendar lastVisit = pageBean.getLastVisit();
		if (lastVisit == null) {
			logger.debug("expired because never visted before");
			return true;
		}
		final Calendar pageModified = toCalendar(page.getModified());
		if (lastVisit != null && pageModified != null && lastVisit.before(pageModified)) {
			logger.debug("expired because modified(" + pageModified.getTimeInMillis() + ") after last visit(" + lastVisit.getTimeInMillis() + ")");
			return true;
		}
		Integer expire = confluenceInstanceBean.getExpire();
		if (expire == null) {
			expire = 7;
		}
		return calendarUtil.getTime() - lastVisit.getTimeInMillis() > expire * EXPIRE_DAY;
	}

	private Calendar toCalendar(final Date date) {
		return calendarUtil.parseDate(timeZoneUtil.getUTCTimeZone(), date, null);
	}

	private String filterContent(final String orgContent) {
		final String filteredContent = htmlUtil.filterHtmlTages(orgContent);
		logger.trace("filterContent - orgContent: " + orgContent + " filteredContent: " + filteredContent);
		return filteredContent;
	}

	public boolean refresh() {
		logger.debug("confluence-refresh - started");
		if (runOnlyOnceATime.run(new RefreshRunnable())) {
			logger.debug("confluence-refresh - finished");
			return true;
		}
		else {
			logger.debug("confluence-refresh - skipped");
			return false;
		}
	}
}
