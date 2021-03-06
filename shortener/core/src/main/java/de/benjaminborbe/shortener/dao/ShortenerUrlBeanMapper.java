package de.benjaminborbe.shortener.dao;

import com.google.inject.Provider;
import de.benjaminborbe.shortener.api.ShortenerUrlIdentifier;
import de.benjaminborbe.tools.mapper.MapperCalendar;
import de.benjaminborbe.tools.mapper.MapperString;
import de.benjaminborbe.tools.mapper.mapobject.MapObjectMapperAdapter;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapper;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapperAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Singleton
public class ShortenerUrlBeanMapper extends MapObjectMapperAdapter<ShortenerUrlBean> {

	@Inject
	public ShortenerUrlBeanMapper(
		final Provider<ShortenerUrlBean> provider,
		final MapperString mapperString,
		final MapperCalendar mapperCalendar,
		final MapperShortenerUrlIdentifier shortenerUrlIdentifierMapper
	) {
		super(provider, buildMappings(shortenerUrlIdentifierMapper, mapperString, mapperCalendar));
	}

	private static Collection<StringObjectMapper<ShortenerUrlBean>> buildMappings(
		final MapperShortenerUrlIdentifier shortenerUrlIdentifierMapper, final MapperString mapperString,
		final MapperCalendar mapperCalendar
	) {
		final List<StringObjectMapper<ShortenerUrlBean>> result = new ArrayList<StringObjectMapper<ShortenerUrlBean>>();
		result.add(new StringObjectMapperAdapter<ShortenerUrlBean, ShortenerUrlIdentifier>("id", shortenerUrlIdentifierMapper));
		result.add(new StringObjectMapperAdapter<ShortenerUrlBean, String>("url", mapperString));
		result.add(new StringObjectMapperAdapter<ShortenerUrlBean, Calendar>("created", mapperCalendar));
		result.add(new StringObjectMapperAdapter<ShortenerUrlBean, Calendar>("modified", mapperCalendar));
		return result;
	}
}
