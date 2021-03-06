package de.benjaminborbe.tools.html;

import org.easymock.EasyMock;
import org.junit.Test;
import org.slf4j.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HtmlContentIteratorUnitTest {

	@Test
	public void testEmptyText() throws Exception {
		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final HtmlTagParser htmlTagParser = new HtmlTagParser(logger);
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, null);
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "");
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, " ");
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "  ");
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, " \n\t\r ");
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
	}

	@Test
	public void testPlainText() throws Exception {
		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final HtmlTagParser htmlTagParser = new HtmlTagParser(logger);
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "bla");
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("bla"));
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "&uuml;");
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("ü"));
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, " bla ");
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("bla"));
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "foo baa");
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("foo"));
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("baa"));
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, " foo baa ");
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("foo"));
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("baa"));
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
	}

	@Test
	public void testSimpleHtmlTag() throws Exception {
		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final HtmlTagParser htmlTagParser = new HtmlTagParser(logger);
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "<br>");
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "foo<br>bar");
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("foo"));
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("bar"));
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
	}

	@Test
	public void testScriptTag() throws Exception {
		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final HtmlTagParser htmlTagParser = new HtmlTagParser(logger);
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "<script>hello world</script>");
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "foo<script>hello world</script>bar");
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("foo"));
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("bar"));
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
	}

	@Test
	public void testScriptTagSingle() throws Exception {
		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final HtmlTagParser htmlTagParser = new HtmlTagParser(logger);
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "<script/>");
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "foo<script/>bar");
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("foo"));
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("bar"));
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
	}

	@Test
	public void testStyleTag() throws Exception {
		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final HtmlTagParser htmlTagParser = new HtmlTagParser(logger);
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "<style>hello world</style>");
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "foo<style>hello world</style>bar");
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("foo"));
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("bar"));
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
	}

	@Test
	public void testScriptTagTypeJavascript() throws Exception {
		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final HtmlTagParser htmlTagParser = new HtmlTagParser(logger);
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "<script type=\"text/javascript\">hello world</script>");
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "foo<script type=\"text/javascript\">hello world</script>bar");
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("foo"));
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("bar"));
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
	}

	@Test
	public void testScriptTagSyntaxhighlighter() throws Exception {
		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final HtmlTagParser htmlTagParser = new HtmlTagParser(logger);
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "<script type=\"syntaxhighlighter\">hello world</script>");
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("hello"));
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("world"));
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
		{
			final HtmlContentIterator i = new HtmlContentIterator(htmlTagParser, "<script type=\"syntaxhighlighter\"><![CDATA[hello world]]></script>");
			assertThat(i.hasNext(), is(true));
			assertThat(i.hasNext(), is(true));
			assertThat(i.next(), is("hello world"));
			assertThat(i.hasNext(), is(false));
			assertThat(i.hasNext(), is(false));
		}
	}
}
