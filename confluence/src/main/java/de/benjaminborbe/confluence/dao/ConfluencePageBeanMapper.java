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
import de.benjaminborbe.confluence.api.ConfluencePageIdentifier;
import de.benjaminborbe.tools.mapper.MapperCalendar;
import de.benjaminborbe.tools.mapper.MapperString;
import de.benjaminborbe.tools.mapper.mapobject.MapObjectMapperAdapter;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapper;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapperBase;

@Singleton
public class ConfluencePageBeanMapper extends MapObjectMapperAdapter<ConfluencePageBean> {

	@Inject
	public ConfluencePageBeanMapper(
			final Provider<ConfluencePageBean> provider,
			final MapperConfluencePageIdentifier mapperConfluencePageIdentifier,
			final MapperUserIdentifier mapperUserIdentifier,
			final MapperConfluenceInstanceIdentifier mapperConfluenceInstanceIdentifier,
			final MapperString mapperString,
			final MapperCalendar mapperCalendar) {
		super(provider, buildMappings(mapperConfluencePageIdentifier, mapperUserIdentifier, mapperConfluenceInstanceIdentifier, mapperString, mapperCalendar));
	}

	private static Collection<StringObjectMapper<ConfluencePageBean>> buildMappings(final MapperConfluencePageIdentifier mapperConfluencePageIdentifier,
			final MapperUserIdentifier mapperUserIdentifier, final MapperConfluenceInstanceIdentifier mapperConfluenceInstanceIdentifier, final MapperString mapperString,
			final MapperCalendar mapperCalendar) {
		final List<StringObjectMapper<ConfluencePageBean>> result = new ArrayList<StringObjectMapper<ConfluencePageBean>>();
		result.add(new StringObjectMapperBase<ConfluencePageBean, ConfluencePageIdentifier>("id", mapperConfluencePageIdentifier));
		result.add(new StringObjectMapperBase<ConfluencePageBean, UserIdentifier>("owner", mapperUserIdentifier));
		result.add(new StringObjectMapperBase<ConfluencePageBean, ConfluenceInstanceIdentifier>("instanceId", mapperConfluenceInstanceIdentifier));
		result.add(new StringObjectMapperBase<ConfluencePageBean, String>("pageId", mapperString));
		result.add(new StringObjectMapperBase<ConfluencePageBean, Calendar>("lastVisit", mapperCalendar));
		result.add(new StringObjectMapperBase<ConfluencePageBean, Calendar>("created", mapperCalendar));
		result.add(new StringObjectMapperBase<ConfluencePageBean, Calendar>("modified", mapperCalendar));
		return result;
	}
}
