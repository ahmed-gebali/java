package de.benjaminborbe.poker.game;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.poker.api.PokerGameIdentifier;
import de.benjaminborbe.poker.api.PokerPlayerIdentifier;
import de.benjaminborbe.poker.util.MapperPokerGameIdentifier;
import de.benjaminborbe.poker.util.MapperPokerPlayerIdentifier;
import de.benjaminborbe.tools.mapper.MapperBoolean;
import de.benjaminborbe.tools.mapper.MapperCalendar;
import de.benjaminborbe.tools.mapper.MapperLong;
import de.benjaminborbe.tools.mapper.MapperString;
import de.benjaminborbe.tools.mapper.mapobject.MapObjectMapperAdapter;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapper;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapperAdapter;

@Singleton
public class PokerGameBeanMapper extends MapObjectMapperAdapter<PokerGameBean> {

	public static final String MODIFIED = "modified";

	public static final String CREATED = "created";

	public static final String NAME = "name";

	public static final String ACTIVE_PLAYER = "activePlayer";

	public static final String ID = "id";

	public static final String RUNNING = "running";

	public static final String BIG_BLIND = "bigBlind";

	public static final String SMALL_BLIND = "smallBlind";

	@Inject
	public PokerGameBeanMapper(
			final Provider<PokerGameBean> provider,
			final MapperPokerGameIdentifier mapperPokerGameIdentifier,
			final MapperPokerPlayerIdentifier mapperPokerPlayerIdentifier,
			final MapperCalendar mapperCalendar,
			final MapperString mapperString,
			final MapperBoolean mapperBoolean,
			final MapperLong mapperLong) {
		super(provider, buildMappings(mapperPokerGameIdentifier, mapperPokerPlayerIdentifier, mapperCalendar, mapperString, mapperBoolean, mapperLong));
	}

	private static Collection<StringObjectMapper<PokerGameBean>> buildMappings(final MapperPokerGameIdentifier mapperPokerGameIdentifier,
			final MapperPokerPlayerIdentifier mapperPokerPlayerIdentifier, final MapperCalendar mapperCalendar, final MapperString mapperString, final MapperBoolean mapperBoolean,
			final MapperLong mapperLong) {
		final List<StringObjectMapper<PokerGameBean>> result = new ArrayList<StringObjectMapper<PokerGameBean>>();
		result.add(new StringObjectMapperAdapter<PokerGameBean, PokerGameIdentifier>(ID, mapperPokerGameIdentifier));
		result.add(new StringObjectMapperAdapter<PokerGameBean, PokerPlayerIdentifier>(ACTIVE_PLAYER, mapperPokerPlayerIdentifier));
		result.add(new StringObjectMapperAdapter<PokerGameBean, String>(NAME, mapperString));
		result.add(new StringObjectMapperAdapter<PokerGameBean, Calendar>(CREATED, mapperCalendar));
		result.add(new StringObjectMapperAdapter<PokerGameBean, Calendar>(MODIFIED, mapperCalendar));
		result.add(new StringObjectMapperAdapter<PokerGameBean, Boolean>(RUNNING, mapperBoolean));
		result.add(new StringObjectMapperAdapter<PokerGameBean, Long>(SMALL_BLIND, mapperLong));
		result.add(new StringObjectMapperAdapter<PokerGameBean, Long>(BIG_BLIND, mapperLong));
		return result;
	}
}
