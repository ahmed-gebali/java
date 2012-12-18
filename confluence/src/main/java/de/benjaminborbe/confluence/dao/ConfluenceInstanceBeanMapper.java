package de.benjaminborbe.confluence.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.confluence.api.ConfluenceInstanceIdentifier;
import de.benjaminborbe.tools.mapper.MapperCalendar;
import de.benjaminborbe.tools.mapper.MapperBoolean;
import de.benjaminborbe.tools.mapper.MapperInteger;
import de.benjaminborbe.tools.mapper.MapperLong;
import de.benjaminborbe.tools.mapper.MapperString;
import de.benjaminborbe.tools.mapper.mapobject.MapObjectMapperAdapter;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapper;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapperBase;

@Singleton
public class ConfluenceInstanceBeanMapper extends MapObjectMapperAdapter<ConfluenceInstanceBean> {

	@Inject
	public ConfluenceInstanceBeanMapper(
			final Provider<ConfluenceInstanceBean> provider,

			final MapperConfluenceInstanceIdentifier mapperConfluenceInstanceIdentifier,
			final MapperUserIdentifier mapperUserIdentifier,
			final MapperString mapperString,
			final MapperLong mapperLong,
			final MapperBoolean mapperBoolean,
			final MapperInteger mapperInteger,
			final MapperCalendar mapperCalendar) {
		super(provider, buildMappings(mapperConfluenceInstanceIdentifier, mapperUserIdentifier, mapperString, mapperLong, mapperBoolean, mapperInteger, mapperCalendar));
	}

	private static Collection<StringObjectMapper<ConfluenceInstanceBean>> buildMappings(final MapperConfluenceInstanceIdentifier mapperConfluenceInstanceIdentifier,
			final MapperUserIdentifier mapperUserIdentifier, final MapperString mapperString, final MapperLong mapperLong, final MapperBoolean mapperBoolean,
			final MapperInteger mapperInteger, final MapperCalendar mapperCalendar) {
		final List<StringObjectMapper<ConfluenceInstanceBean>> result = new ArrayList<StringObjectMapper<ConfluenceInstanceBean>>();
		result.add(new StringObjectMapperBase<ConfluenceInstanceBean, ConfluenceInstanceIdentifier>("id", mapperConfluenceInstanceIdentifier));
		result.add(new StringObjectMapperBase<ConfluenceInstanceBean, UserIdentifier>("owner", mapperUserIdentifier));
		result.add(new StringObjectMapperBase<ConfluenceInstanceBean, String>("url", mapperString));
		result.add(new StringObjectMapperBase<ConfluenceInstanceBean, String>("username", mapperString));
		result.add(new StringObjectMapperBase<ConfluenceInstanceBean, Long>("delay", mapperLong));
		result.add(new StringObjectMapperBase<ConfluenceInstanceBean, String>("password", mapperString));
		result.add(new StringObjectMapperBase<ConfluenceInstanceBean, Boolean>("shared", mapperBoolean));
		result.add(new StringObjectMapperBase<ConfluenceInstanceBean, Boolean>("activated", mapperBoolean));
		result.add(new StringObjectMapperBase<ConfluenceInstanceBean, Integer>("expire", mapperInteger));
		result.add(new StringObjectMapperBase<ConfluenceInstanceBean, Calendar>("created", mapperCalendar));
		result.add(new StringObjectMapperBase<ConfluenceInstanceBean, Calendar>("modified", mapperCalendar));
		return result;
	}
}
