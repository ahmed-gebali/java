package de.benjaminborbe.confluence.connector;

import java.util.Calendar;

public class ConfluenceConnectorPage {

	private final String pageId;

	private final String url;

	private final String title;

	private final Calendar modified;

	public ConfluenceConnectorPage(final String pageId, final String url, final String title, final Calendar modified) {
		this.pageId = pageId;
		this.url = url;
		this.title = title;
		this.modified = modified;
	}

	public String getPageId() {
		return pageId;
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public Calendar getModified() {
		return modified;
	}
}
