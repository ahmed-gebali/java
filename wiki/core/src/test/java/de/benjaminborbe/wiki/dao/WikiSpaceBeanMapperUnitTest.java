package de.benjaminborbe.wiki.dao;

import com.google.inject.Provider;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.CalendarUtilImpl;
import de.benjaminborbe.tools.date.CurrentTime;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.date.TimeZoneUtilImpl;
import de.benjaminborbe.tools.guice.ProviderMock;
import de.benjaminborbe.tools.mapper.MapperCalendar;
import de.benjaminborbe.tools.mapper.MapperString;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.tools.util.ParseUtilImpl;
import de.benjaminborbe.wiki.util.MapperWikiSpaceIdentifier;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class WikiSpaceBeanMapperUnitTest {

	private final String fieldName;

	private final String fieldValue;

	public WikiSpaceBeanMapperUnitTest(final String fieldName, final String fieldValue) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	@Parameters(name = "{index} - \"{0}\" = \"{1}\"")
	public static Collection<Object[]> generateData() {
		final List<Object[]> result = new ArrayList<Object[]>();
		result.add(new Object[]{"id", "1337"});
		result.add(new Object[]{"name", "bla"});
		result.add(new Object[]{"created", "123456"});
		result.add(new Object[]{"modified", "123456"});
		return result;
	}

	private WikiSpaceBeanMapper getWikiSpaceBeanMapper() {
		final Provider<WikiSpaceBean> beanProvider = new ProviderMock<WikiSpaceBean>(WikiSpaceBean.class);
		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final TimeZoneUtil timeZoneUtil = new TimeZoneUtilImpl();
		final ParseUtil parseUtil = new ParseUtilImpl();

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);

		final CalendarUtil calendarUtil = new CalendarUtilImpl(logger, currentTime, parseUtil, timeZoneUtil);
		final MapperCalendar mapperCalendar = new MapperCalendar(timeZoneUtil, calendarUtil, parseUtil);
		final MapperString mapperString = new MapperString();
		final MapperWikiSpaceIdentifier mapperWikiSpaceIdentifier = new MapperWikiSpaceIdentifier();
		return new WikiSpaceBeanMapper(beanProvider, mapperWikiSpaceIdentifier, mapperString, mapperCalendar);
	}

	@Test
	public void testMaxRetryCounter() throws Exception {
		final WikiSpaceBeanMapper mapper = getWikiSpaceBeanMapper();
		final Map<String, String> inputData = new HashMap<String, String>();
		inputData.put(fieldName, fieldValue);
		final WikiSpaceBean bean = mapper.map(inputData);
		final Map<String, String> data = mapper.map(bean);
		assertThat(data.containsKey(fieldName), is(true));
		assertThat(data.get(fieldName), is(fieldValue));
	}

}
