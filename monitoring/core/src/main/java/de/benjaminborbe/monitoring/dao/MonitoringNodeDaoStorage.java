package de.benjaminborbe.monitoring.dao;

import com.google.inject.Provider;
import de.benjaminborbe.monitoring.api.MonitoringNodeIdentifier;
import de.benjaminborbe.storage.api.StorageService;
import de.benjaminborbe.storage.tools.DaoStorage;
import de.benjaminborbe.tools.date.CalendarUtil;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MonitoringNodeDaoStorage extends DaoStorage<MonitoringNodeBean, MonitoringNodeIdentifier> implements MonitoringNodeDao {

	@Inject
	public MonitoringNodeDaoStorage(
		final Logger logger,
		final StorageService storageService,
		final Provider<MonitoringNodeBean> beanProvider,
		final MonitoringNodeBeanMapper mapper,
		final MonitoringNodeIdentifierBuilder identifierBuilder,
		final CalendarUtil calendarUtil
	) {
		super(logger, storageService, beanProvider, mapper, identifierBuilder, calendarUtil);
	}

	private static final String COLUMN_FAMILY = "monitoring_node";

	@Override
	protected String getColumnFamily() {
		return COLUMN_FAMILY;
	}

}
