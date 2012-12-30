package de.benjaminborbe.wiki.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.tools.mapper.MapperCalendar;
import de.benjaminborbe.tools.mapper.MapperString;
import de.benjaminborbe.tools.mapper.mapobject.MapObjectMapperAdapter;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapper;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapperAdapter;
import de.benjaminborbe.wiki.api.WikiSpaceIdentifier;

@Singleton
public class WikiSpaceBeanMapper extends MapObjectMapperAdapter<WikiSpaceBean> {

	@Inject
	public WikiSpaceBeanMapper(
			final Provider<WikiSpaceBean> provider,
			final MapperWikiSpaceIdentifier mapperWikiSpaceIdentifier,
			final MapperString mapperString,
			final MapperCalendar mapperCalendar) {
		super(provider, buildMappings(mapperWikiSpaceIdentifier, mapperString, mapperCalendar));
	}

	private static Collection<StringObjectMapper<WikiSpaceBean>> buildMappings(final MapperWikiSpaceIdentifier mapperWikiSpaceIdentifier, final MapperString mapperString,
			final MapperCalendar mapperCalendar) {
		final List<StringObjectMapper<WikiSpaceBean>> result = new ArrayList<StringObjectMapper<WikiSpaceBean>>();
		result.add(new StringObjectMapperAdapter<WikiSpaceBean, WikiSpaceIdentifier>("id", mapperWikiSpaceIdentifier));
		result.add(new StringObjectMapperAdapter<WikiSpaceBean, String>("name", mapperString));
		result.add(new StringObjectMapperAdapter<WikiSpaceBean, Calendar>("created", mapperCalendar));
		result.add(new StringObjectMapperAdapter<WikiSpaceBean, Calendar>("modified", mapperCalendar));
		return result;
	}

}
