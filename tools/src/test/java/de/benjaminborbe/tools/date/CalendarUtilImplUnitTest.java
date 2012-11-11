package de.benjaminborbe.tools.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.TimeZone;

import org.easymock.EasyMock;
import org.junit.Test;
import org.slf4j.Logger;

import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.tools.util.ParseUtilImpl;

public class CalendarUtilImplUnitTest {

	@Test
	public void testToDateString() {
		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);

		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.replay(timeZoneUtil);

		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, null, timeZoneUtil);
		final Calendar calendar = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 15, 13);
		assertEquals("2011-12-24", u.toDateString(calendar));
	}

	@Test
	public void testToHourString() {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);

		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.replay(timeZoneUtil);

		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, null, timeZoneUtil);
		final Calendar calendar = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 15, 13);
		assertEquals("20:15:13", u.toTimeString(calendar));
	}

	@Test
	public void testGetCalendar() {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);

		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.replay(timeZoneUtil);

		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, null, timeZoneUtil);
		final Calendar calendar = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 15, 13);
		assertEquals(TimeZone.getTimeZone("UTF8"), calendar.getTimeZone());
		assertEquals(2011, calendar.get(Calendar.YEAR));
		assertEquals(11, calendar.get(Calendar.MONTH));
		assertEquals(24, calendar.get(Calendar.DAY_OF_MONTH));
		assertEquals(20, calendar.get(Calendar.HOUR_OF_DAY));
		assertEquals(15, calendar.get(Calendar.MINUTE));
		assertEquals(13, calendar.get(Calendar.SECOND));
	}

	@Test
	public void testClone() {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);

		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.replay(timeZoneUtil);

		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, null, timeZoneUtil);
		final Calendar calendar = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 15, 13);
		final Calendar clone = u.clone(calendar);
		assertTrue(calendar.getTimeInMillis() == clone.getTimeInMillis());
		calendar.set(Calendar.YEAR, 2010);
		assertFalse(calendar.getTimeInMillis() == clone.getTimeInMillis());
	}

	@Test
	public void testAddDays() {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);

		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.replay(timeZoneUtil);

		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, null, timeZoneUtil);
		final Calendar calendar = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 15, 13);
		final Calendar clone = u.addDays(calendar, 1);
		assertEquals("2011-12-25", u.toDateString(clone));
	}

	@Test
	public void testSubDays() {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);

		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.replay(timeZoneUtil);

		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, null, timeZoneUtil);
		final Calendar calendar = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 15, 13);
		final Calendar clone = u.subDays(calendar, 1);
		assertEquals("2011-12-23", u.toDateString(clone));
	}

	@Test
	public void testGetWeekday() {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);

		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.replay(timeZoneUtil);

		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, null, timeZoneUtil);
		{
			final Calendar calendarMonday = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2012, 3, 23, 20, 15, 13);
			assertEquals("monday", u.getWeekday(calendarMonday));
		}
		{
			final Calendar calendarTuesday = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2012, 3, 24, 20, 15, 13);
			assertEquals("tuesday", u.getWeekday(calendarTuesday));
		}
		{
			final Calendar calendarWednesday = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2012, 3, 25, 20, 15, 13);
			assertEquals("wednesday", u.getWeekday(calendarWednesday));
		}
		{
			final Calendar calendarThursday = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2012, 3, 26, 20, 15, 13);
			assertEquals("thursday", u.getWeekday(calendarThursday));
		}
		{
			final Calendar calendarFriday = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2012, 3, 27, 20, 15, 13);
			assertEquals("friday", u.getWeekday(calendarFriday));
		}
		{
			final Calendar calendarSaturday = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2012, 3, 28, 20, 15, 13);
			assertEquals("saturday", u.getWeekday(calendarSaturday));
		}
		{
			final Calendar calendarSunday = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2012, 3, 29, 20, 15, 13);
			assertEquals("sunday", u.getWeekday(calendarSunday));
		}
	}

	@Test
	public void testDayEquals() {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);

		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.replay(timeZoneUtil);

		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, null, timeZoneUtil);
		{
			final Calendar calendar1 = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 15, 13);
			final Calendar calendar2 = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 15, 13);
			assertTrue(u.dayEquals(calendar1, calendar2));
		}
		{
			final Calendar calendar1 = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 15, 13);
			final Calendar calendar2 = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 19, 15, 13);
			assertTrue(u.dayEquals(calendar1, calendar2));
		}
		{
			final Calendar calendar1 = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 15, 13);
			final Calendar calendar2 = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 14, 13);
			assertTrue(u.dayEquals(calendar1, calendar2));
		}
		{
			final Calendar calendar1 = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 15, 13);
			final Calendar calendar2 = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 15, 12);
			assertTrue(u.dayEquals(calendar1, calendar2));
		}
		{
			final Calendar calendar1 = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 15, 13);
			final Calendar calendar2 = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 23, 20, 15, 13);
			assertFalse(u.dayEquals(calendar1, calendar2));
		}
		{
			final Calendar calendar1 = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 15, 13);
			final Calendar calendar2 = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 10, 24, 20, 15, 13);
			assertFalse(u.dayEquals(calendar1, calendar2));
		}
		{
			final Calendar calendar1 = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 15, 13);
			final Calendar calendar2 = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2010, 11, 24, 20, 15, 13);
			assertFalse(u.dayEquals(calendar1, calendar2));
		}
	}

	@Test
	public void testGetCalendarByTimestamp() {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);

		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.expect(timeZoneUtil.getUTCTimeZone()).andReturn(TimeZone.getTimeZone("UTF8"));
		EasyMock.replay(timeZoneUtil);
		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, null, timeZoneUtil);
		long time;
		{
			final Calendar calendar = u.getCalendar(TimeZone.getTimeZone("UTF8"), 2011, 11, 24, 20, 15, 13);
			assertNotNull(calendar);
			assertEquals(TimeZone.getTimeZone("UTF8"), calendar.getTimeZone());
			assertEquals(2011, calendar.get(Calendar.YEAR));
			assertEquals(11, calendar.get(Calendar.MONTH));
			assertEquals(24, calendar.get(Calendar.DAY_OF_MONTH));
			assertEquals(20, calendar.get(Calendar.HOUR_OF_DAY));
			assertEquals(15, calendar.get(Calendar.MINUTE));
			assertEquals(13, calendar.get(Calendar.SECOND));
			assertEquals(1324757713000l, calendar.getTimeInMillis());
			time = calendar.getTimeInMillis();
		}
		{
			final Calendar calendar = u.getCalendar(timeZoneUtil.getUTCTimeZone(), time);
			assertNotNull(calendar);
			assertEquals(TimeZone.getTimeZone("UTF8"), calendar.getTimeZone());
			assertEquals(2011, calendar.get(Calendar.YEAR));
			assertEquals(11, calendar.get(Calendar.MONTH));
			assertEquals(24, calendar.get(Calendar.DAY_OF_MONTH));
			assertEquals(20, calendar.get(Calendar.HOUR_OF_DAY));
			assertEquals(15, calendar.get(Calendar.MINUTE));
			assertEquals(13, calendar.get(Calendar.SECOND));
			assertEquals(1324757713000l, calendar.getTimeInMillis());
		}
	}

	@Test
	public void testParseSmart() throws Exception {
		final long time = 1352661011123l;

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.expect(currentTime.currentTimeMillis()).andReturn(time).anyTimes();
		EasyMock.replay(currentTime);

		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.expect(timeZoneUtil.getUTCTimeZone()).andReturn(TimeZone.getTimeZone("UTC")).anyTimes();
		EasyMock.replay(timeZoneUtil);

		final ParseUtil parseUtil = new ParseUtilImpl();
		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, parseUtil, timeZoneUtil);

		{
			assertEquals("2012-11-11 20:59:45", u.toDateTimeString(u.parseSmart("2012-11-11 20:59:45")));
			assertEquals("2012-11-11 00:00:00", u.toDateTimeString(u.parseSmart("2012-11-11")));
		}

		{
			assertEquals("2012-11-11 00:00:00", u.toDateTimeString(u.parseSmart("0d")));
			assertEquals("2012-11-12 00:00:00", u.toDateTimeString(u.parseSmart("1d")));
			assertEquals("2012-11-13 00:00:00", u.toDateTimeString(u.parseSmart("2d")));
			assertEquals("2012-11-10 00:00:00", u.toDateTimeString(u.parseSmart("-1d")));
			assertEquals("2012-11-09 00:00:00", u.toDateTimeString(u.parseSmart("-2d")));
		}

		{
			final Calendar baseValue = u.parseDateTime(timeZoneUtil.getUTCTimeZone(), "2012-11-11 20:59:45");
			assertEquals("2012-11-11 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "+0d")));
			assertEquals("2012-11-12 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "+1d")));
			assertEquals("2012-11-13 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "+2d")));
			assertEquals("2012-11-10 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "-1d")));
			assertEquals("2012-11-09 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "-2d")));
		}

		{
			final Calendar baseValue = u.parseDateTime(timeZoneUtil.getUTCTimeZone(), "2012-11-11 20:59:45");
			assertEquals("2012-11-11 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "+0d")));
			assertEquals("2012-11-12 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "+1d")));
			assertEquals("2012-11-13 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "+2d")));
			assertEquals("2012-11-10 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "-1d")));
			assertEquals("2012-11-09 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "-2d")));
		}

		{
			final Calendar baseValue = u.parseDateTime(timeZoneUtil.getUTCTimeZone(), "2012-11-11 20:59:45");
			assertEquals("2012-11-10 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "yesterday")));
			assertEquals("2012-11-11 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "today")));
			assertEquals("2012-11-12 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "tomorrow")));
		}

		{
			final Calendar baseValue = u.parseDateTime(timeZoneUtil.getUTCTimeZone(), "2012-11-11 20:59:45");
			assertEquals("2012-11-11 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "sunday")));
			assertEquals("2012-11-12 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "monday")));
			assertEquals("2012-11-13 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "tuesday")));
			assertEquals("2012-11-14 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "wednesday")));
			assertEquals("2012-11-15 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "thursday")));
			assertEquals("2012-11-16 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "friday")));
			assertEquals("2012-11-17 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "saturday")));
		}

		{
			final Calendar baseValue = u.parseDateTime(timeZoneUtil.getUTCTimeZone(), "2012-11-12 20:59:45");
			assertEquals("2012-11-12 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "monday")));
			assertEquals("2012-11-13 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "tuesday")));
			assertEquals("2012-11-14 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "wednesday")));
			assertEquals("2012-11-15 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "thursday")));
			assertEquals("2012-11-16 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "friday")));
			assertEquals("2012-11-17 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "saturday")));
			assertEquals("2012-11-18 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "sunday")));
		}

		{
			final Calendar baseValue = u.parseDateTime(timeZoneUtil.getUTCTimeZone(), "2012-11-13 20:59:45");
			assertEquals("2012-11-13 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "tuesday")));
			assertEquals("2012-11-14 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "wednesday")));
			assertEquals("2012-11-15 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "thursday")));
			assertEquals("2012-11-16 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "friday")));
			assertEquals("2012-11-17 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "saturday")));
			assertEquals("2012-11-18 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "sunday")));
			assertEquals("2012-11-19 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "monday")));
		}

		{
			final Calendar baseValue = u.parseDateTime(timeZoneUtil.getUTCTimeZone(), "2012-11-13 20:59:45");
			assertEquals("2012-11-13 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "tue")));
			assertEquals("2012-11-14 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "wed")));
			assertEquals("2012-11-15 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "thu")));
			assertEquals("2012-11-16 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "fri")));
			assertEquals("2012-11-17 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "sat")));
			assertEquals("2012-11-18 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "sun")));
			assertEquals("2012-11-19 00:00:00", u.toDateTimeString(u.parseSmart(baseValue, "mon")));
		}
	}

	@Test
	public void testIsLE() throws Exception {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);

		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.replay(timeZoneUtil);

		final TimeZone timeZone = TimeZone.getDefault();
		final ParseUtil parseUtil = new ParseUtilImpl();
		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, parseUtil, timeZoneUtil);
		assertTrue(u.isLE(u.parseDateTime(timeZone, "2012-12-24 20:15:50"), u.parseDateTime(timeZone, "2012-12-24 20:15:51")));
		assertTrue(u.isLE(u.parseDateTime(timeZone, "2012-12-24 20:15:51"), u.parseDateTime(timeZone, "2012-12-24 20:15:51")));
		assertFalse(u.isLE(u.parseDateTime(timeZone, "2012-12-24 20:15:52"), u.parseDateTime(timeZone, "2012-12-24 20:15:51")));
	}

	@Test
	public void testIsGE() throws Exception {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);
		final TimeZone timeZone = TimeZone.getDefault();
		final ParseUtil parseUtil = new ParseUtilImpl();
		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.replay(timeZoneUtil);
		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, parseUtil, timeZoneUtil);
		assertFalse(u.isGE(u.parseDateTime(timeZone, "2012-12-24 20:15:50"), u.parseDateTime(timeZone, "2012-12-24 20:15:51")));
		assertTrue(u.isGE(u.parseDateTime(timeZone, "2012-12-24 20:15:51"), u.parseDateTime(timeZone, "2012-12-24 20:15:51")));
		assertTrue(u.isGE(u.parseDateTime(timeZone, "2012-12-24 20:15:52"), u.parseDateTime(timeZone, "2012-12-24 20:15:51")));
	}

	@Test
	public void testIsLT() throws Exception {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);

		final TimeZone timeZone = TimeZone.getDefault();
		final ParseUtil parseUtil = new ParseUtilImpl();
		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.replay(timeZoneUtil);
		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, parseUtil, timeZoneUtil);
		assertTrue(u.isLT(u.parseDateTime(timeZone, "2012-12-24 20:15:50"), u.parseDateTime(timeZone, "2012-12-24 20:15:51")));
		assertFalse(u.isLT(u.parseDateTime(timeZone, "2012-12-24 20:15:51"), u.parseDateTime(timeZone, "2012-12-24 20:15:51")));
		assertFalse(u.isLT(u.parseDateTime(timeZone, "2012-12-24 20:15:52"), u.parseDateTime(timeZone, "2012-12-24 20:15:51")));
	}

	@Test
	public void testIsGT() throws Exception {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);

		final TimeZone timeZone = TimeZone.getDefault();
		final ParseUtil parseUtil = new ParseUtilImpl();
		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.replay(timeZoneUtil);
		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, parseUtil, timeZoneUtil);
		assertFalse(u.isGT(u.parseDateTime(timeZone, "2012-12-24 20:15:50"), u.parseDateTime(timeZone, "2012-12-24 20:15:51")));
		assertFalse(u.isGT(u.parseDateTime(timeZone, "2012-12-24 20:15:51"), u.parseDateTime(timeZone, "2012-12-24 20:15:51")));
		assertTrue(u.isGT(u.parseDateTime(timeZone, "2012-12-24 20:15:52"), u.parseDateTime(timeZone, "2012-12-24 20:15:51")));
	}

	@Test
	public void testIsEQ() throws Exception {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);

		final TimeZone timeZone = TimeZone.getDefault();
		final ParseUtil parseUtil = new ParseUtilImpl();
		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.replay(timeZoneUtil);
		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, parseUtil, timeZoneUtil);
		assertFalse(u.isEQ(u.parseDateTime(timeZone, "2012-12-24 20:15:50"), u.parseDateTime(timeZone, "2012-12-24 20:15:51")));
		assertTrue(u.isEQ(u.parseDateTime(timeZone, "2012-12-24 20:15:51"), u.parseDateTime(timeZone, "2012-12-24 20:15:51")));
		assertFalse(u.isEQ(u.parseDateTime(timeZone, "2012-12-24 20:15:52"), u.parseDateTime(timeZone, "2012-12-24 20:15:51")));
	}

	@Test
	public void testParseDate() throws Exception {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);
		final TimeZone timeZone = TimeZone.getDefault();
		final ParseUtil parseUtil = new ParseUtilImpl();
		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.replay(timeZoneUtil);
		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, parseUtil, timeZoneUtil);
		final Calendar c = u.parseDate(timeZone, "2012-11-09");
		assertEquals(2012, c.get(Calendar.YEAR));
		assertEquals(10, c.get(Calendar.MONTH));
		assertEquals(9, c.get(Calendar.DAY_OF_MONTH));
		assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
		assertEquals(0, c.get(Calendar.MINUTE));
		assertEquals(0, c.get(Calendar.SECOND));
		assertEquals(0, c.get(Calendar.MILLISECOND));
	}

	@Test
	public void testParseDateToDate() throws Exception {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);
		final TimeZone timeZone = TimeZone.getDefault();
		final ParseUtil parseUtil = new ParseUtilImpl();
		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.replay(timeZoneUtil);
		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, parseUtil, timeZoneUtil);
		final String dateString = "2012-11-09";
		final Calendar c = u.parseDate(timeZone, dateString);
		assertEquals(2012, c.get(Calendar.YEAR));
		assertEquals(10, c.get(Calendar.MONTH));
		assertEquals(9, c.get(Calendar.DAY_OF_MONTH));
		assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
		assertEquals(0, c.get(Calendar.MINUTE));
		assertEquals(0, c.get(Calendar.SECOND));
		assertEquals(0, c.get(Calendar.MILLISECOND));
		assertEquals(dateString, u.toDateString(c));
	}

	@Test
	public void testParseDateToDatetime() throws Exception {

		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);
		final TimeZone timeZone = TimeZone.getDefault();
		final ParseUtil parseUtil = new ParseUtilImpl();
		final TimeZoneUtil timeZoneUtil = EasyMock.createMock(TimeZoneUtil.class);
		EasyMock.replay(timeZoneUtil);
		final CalendarUtil u = new CalendarUtilImpl(logger, currentTime, parseUtil, timeZoneUtil);
		final String dateString = "2012-11-09 20:45:59";
		final Calendar c = u.parseDateTime(timeZone, dateString);
		assertEquals(2012, c.get(Calendar.YEAR));
		assertEquals(10, c.get(Calendar.MONTH));
		assertEquals(9, c.get(Calendar.DAY_OF_MONTH));
		assertEquals(20, c.get(Calendar.HOUR_OF_DAY));
		assertEquals(45, c.get(Calendar.MINUTE));
		assertEquals(59, c.get(Calendar.SECOND));
		assertEquals(0, c.get(Calendar.MILLISECOND));
		assertEquals(dateString, u.toDateTimeString(c));
	}

}
