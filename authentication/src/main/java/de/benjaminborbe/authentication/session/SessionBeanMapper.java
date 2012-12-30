package de.benjaminborbe.authentication.session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.authentication.user.MapperUserIdentifier;
import de.benjaminborbe.tools.mapper.MapperCalendar;
import de.benjaminborbe.tools.mapper.mapobject.MapObjectMapperAdapter;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapper;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapperAdapter;

@Singleton
public class SessionBeanMapper extends MapObjectMapperAdapter<SessionBean> {

	@Inject
	public SessionBeanMapper(
			final Provider<SessionBean> provider,
			final MapperCalendar mapperCalendar,
			final MapperSessionIdentifier mapperSessionIdentifier,
			final MapperUserIdentifier mapperUserIdentifier) {
		super(provider, buildMappings(mapperCalendar, mapperSessionIdentifier, mapperUserIdentifier));
	}

	private static Collection<StringObjectMapper<SessionBean>> buildMappings(final MapperCalendar mapperCalendar, final MapperSessionIdentifier mapperSessionIdentifier,
			final MapperUserIdentifier mapperUserIdentifier) {
		final List<StringObjectMapper<SessionBean>> result = new ArrayList<StringObjectMapper<SessionBean>>();
		result.add(new StringObjectMapperAdapter<SessionBean, SessionIdentifier>("id", mapperSessionIdentifier));
		result.add(new StringObjectMapperAdapter<SessionBean, UserIdentifier>("currentUser", mapperUserIdentifier));
		result.add(new StringObjectMapperAdapter<SessionBean, Calendar>("created", mapperCalendar));
		result.add(new StringObjectMapperAdapter<SessionBean, Calendar>("modified", mapperCalendar));
		return result;
	}
}
