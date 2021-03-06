package de.benjaminborbe.task.core.dao.attachment;

import com.google.inject.Provider;
import de.benjaminborbe.filestorage.api.FilestorageEntryIdentifier;
import de.benjaminborbe.task.api.TaskAttachmentIdentifier;
import de.benjaminborbe.task.api.TaskIdentifier;
import de.benjaminborbe.task.core.util.MapperFilestorageEntryIdentifier;
import de.benjaminborbe.task.core.util.MapperTaskAttachmentIdentifier;
import de.benjaminborbe.task.core.util.MapperTaskIdentifier;
import de.benjaminborbe.tools.mapper.MapperCalendar;
import de.benjaminborbe.tools.mapper.MapperString;
import de.benjaminborbe.tools.mapper.mapobject.MapObjectMapperAdapter;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapper;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapperAdapter;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public class TaskAttachmentBeanMapper extends MapObjectMapperAdapter<TaskAttachmentBean> {

	public static final String NAME = "name";

	public static final String TASK = "task";

	public static final String CREATED = "created";

	public static final String MODIFIED = "modified";

	public static final String ID = "id";

	public static final String FILE = "file";

	@Inject
	public TaskAttachmentBeanMapper(
		final Provider<TaskAttachmentBean> provider,
		final MapperTaskIdentifier mapperTaskIdentifier,
		final MapperTaskAttachmentIdentifier mapperTaskAttachmentIdentifier,
		final MapperString mapperString,
		final MapperCalendar mapperCalendar,
		final MapperFilestorageEntryIdentifier mapperFilestorageEntryIdentifier
	) {
		super(provider, buildMappings(mapperTaskIdentifier, mapperTaskAttachmentIdentifier, mapperString, mapperCalendar, mapperFilestorageEntryIdentifier));
	}

	private static Collection<StringObjectMapper<TaskAttachmentBean>> buildMappings(
		final MapperTaskIdentifier mapperTaskIdentifier, final MapperTaskAttachmentIdentifier mapperTaskAttachmentIdentifier, final MapperString mapperString,
		final MapperCalendar mapperCalendar, final MapperFilestorageEntryIdentifier mapperFilestorageEntryIdentifier
	) {
		final List<StringObjectMapper<TaskAttachmentBean>> result = new ArrayList<StringObjectMapper<TaskAttachmentBean>>();
		result.add(new StringObjectMapperAdapter<TaskAttachmentBean, TaskAttachmentIdentifier>(ID, mapperTaskAttachmentIdentifier));
		result.add(new StringObjectMapperAdapter<TaskAttachmentBean, String>(NAME, mapperString));
		result.add(new StringObjectMapperAdapter<TaskAttachmentBean, TaskIdentifier>(TASK, mapperTaskIdentifier));
		result.add(new StringObjectMapperAdapter<TaskAttachmentBean, FilestorageEntryIdentifier>(FILE, mapperFilestorageEntryIdentifier));
		result.add(new StringObjectMapperAdapter<TaskAttachmentBean, Calendar>(CREATED, mapperCalendar));
		result.add(new StringObjectMapperAdapter<TaskAttachmentBean, Calendar>(MODIFIED, mapperCalendar));
		return result;
	}
}
