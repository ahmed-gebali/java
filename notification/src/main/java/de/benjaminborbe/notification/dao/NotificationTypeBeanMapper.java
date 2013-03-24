package de.benjaminborbe.notification.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.notification.api.NotificationTypeIdentifier;
import de.benjaminborbe.notification.util.MapperNotificationTypeIdentifier;
import de.benjaminborbe.tools.mapper.MapperCalendar;
import de.benjaminborbe.tools.mapper.MapperString;
import de.benjaminborbe.tools.mapper.mapobject.MapObjectMapperAdapter;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapper;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapperAdapter;

@Singleton
public class NotificationTypeBeanMapper extends MapObjectMapperAdapter<NotificationTypeBean> {

	public static final String OWNER = "owner";

	@Inject
	public NotificationTypeBeanMapper(
			final Provider<NotificationTypeBean> provider,
			final MapperNotificationTypeIdentifier mapperNotificationTypeIdentifier,
			final MapperString mapperString,
			final MapperCalendar mapperCalendar) {
		super(provider, buildMappings(mapperString, mapperCalendar, mapperNotificationTypeIdentifier));
	}

	private static Collection<StringObjectMapper<NotificationTypeBean>> buildMappings(final MapperString mapperString, final MapperCalendar mapperCalendar,
			final MapperNotificationTypeIdentifier mapperNotificationTypeIdentifier) {
		final List<StringObjectMapper<NotificationTypeBean>> result = new ArrayList<StringObjectMapper<NotificationTypeBean>>();
		result.add(new StringObjectMapperAdapter<NotificationTypeBean, NotificationTypeIdentifier>("id", mapperNotificationTypeIdentifier));
		result.add(new StringObjectMapperAdapter<NotificationTypeBean, Calendar>("created", mapperCalendar));
		result.add(new StringObjectMapperAdapter<NotificationTypeBean, Calendar>("modified", mapperCalendar));
		return result;
	}
}
