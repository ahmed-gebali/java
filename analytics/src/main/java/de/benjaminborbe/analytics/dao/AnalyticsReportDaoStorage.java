package de.benjaminborbe.analytics.dao;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.analytics.api.AnalyticsReportIdentifier;
import de.benjaminborbe.storage.api.StorageService;
import de.benjaminborbe.storage.tools.DaoStorage;
import de.benjaminborbe.tools.date.CalendarUtil;

@Singleton
public class AnalyticsReportDaoStorage extends DaoStorage<AnalyticsReportBean, AnalyticsReportIdentifier> implements AnalyticsReportDao {

	@Inject
	public AnalyticsReportDaoStorage(
			final Logger logger,
			final StorageService storageService,
			final Provider<AnalyticsReportBean> beanProvider,
			final AnalyticsReportBeanMapper mapper,
			final AnalyticsReportIdentifierBuilder identifierBuilder,
			final CalendarUtil calendarUtil) {
		super(logger, storageService, beanProvider, mapper, identifierBuilder, calendarUtil);
	}

	private static final String COLUMN_FAMILY = "analytics_report";

	@Override
	protected String getColumnFamily() {
		return COLUMN_FAMILY;
	}

}