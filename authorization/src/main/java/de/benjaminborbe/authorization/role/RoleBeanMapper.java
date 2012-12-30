package de.benjaminborbe.authorization.role;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.authorization.api.RoleIdentifier;
import de.benjaminborbe.tools.mapper.MapperCalendar;
import de.benjaminborbe.tools.mapper.mapobject.MapObjectMapperAdapter;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapper;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapperAdapter;

@Singleton
public class RoleBeanMapper extends MapObjectMapperAdapter<RoleBean> {

	@Inject
	public RoleBeanMapper(final Provider<RoleBean> provider, final MapperCalendar mapperCalendar, final MapperRoleIdentifier mapperRoleIdentifier) {
		super(provider, buildMappings(mapperCalendar, mapperRoleIdentifier));
	}

	private static Collection<StringObjectMapper<RoleBean>> buildMappings(final MapperCalendar mapperCalendar, final MapperRoleIdentifier mapperRoleIdentifier) {
		final List<StringObjectMapper<RoleBean>> result = new ArrayList<StringObjectMapper<RoleBean>>();
		result.add(new StringObjectMapperAdapter<RoleBean, RoleIdentifier>("id", mapperRoleIdentifier));
		result.add(new StringObjectMapperAdapter<RoleBean, Calendar>("created", mapperCalendar));
		result.add(new StringObjectMapperAdapter<RoleBean, Calendar>("modified", mapperCalendar));
		return result;
	}
}
