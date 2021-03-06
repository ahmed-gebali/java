package de.benjaminborbe.googlesearch.service;

import de.benjaminborbe.search.api.SearchResult;
import de.benjaminborbe.tools.html.HtmlTagParser;
import de.benjaminborbe.tools.html.HtmlUtil;
import de.benjaminborbe.tools.html.HtmlUtilImpl;
import de.benjaminborbe.tools.json.JSONParser;
import de.benjaminborbe.tools.json.JSONParserSimple;
import de.benjaminborbe.tools.stream.ChannelTools;
import de.benjaminborbe.tools.stream.StreamUtil;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.tools.url.UrlUtilImpl;
import de.benjaminborbe.tools.util.ResourceUtil;
import de.benjaminborbe.tools.util.ResourceUtilImpl;
import org.easymock.EasyMock;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GoogleSearchServiceComponentUnitTest {

	@Test
	public void testBuildResults() throws Exception {
		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);
		final JSONParser jsonParser = new JSONParserSimple();

		final HtmlTagParser htmlTagParser = new HtmlTagParser(logger);
		final HtmlUtil htmlUtil = new HtmlUtilImpl(logger, htmlTagParser);
		final UrlUtil urlUtil = new UrlUtilImpl();
		final GoogleSearchServiceComponent googleSearchServiceComponent = new GoogleSearchServiceComponent(null, null, htmlUtil, urlUtil, jsonParser, null, null);
		final StreamUtil streamUtil = new StreamUtil(new ChannelTools());
		final ResourceUtil resourceUtil = new ResourceUtilImpl(streamUtil);
		final String content = resourceUtil.getResourceContentAsString("sample-result.txt");
		assertNotNull(content);
		final List<SearchResult> result = googleSearchServiceComponent.buildResults(content);
		assertEquals(4, result.size());
		{
			final int pos = 0;
			assertEquals("Google", result.get(pos).getType());
			assertEquals("http://en.wikipedia.org/wiki/Foobar", result.get(pos).getUrl());
			assertEquals("Foobar - Wikipedia, the free encyclopedia", result.get(pos).getTitle());
			assertEquals(
				"The terms foobar /ˈfʊːbɑː/, fubar, or foo , bar, baz and qux (alternatively quux) are sometimes used as placeholder names (also referred to as metasyntactic ...",
				result.get(pos).getDescription());
		}
		{
			final int pos = 1;
			assertEquals("Google", result.get(pos).getType());
			assertEquals("http://www.foofighters.com/", result.get(pos).getUrl());
			assertEquals("FooFighters.com", result.get(pos).getTitle());
			assertEquals("Official site. News, touring information, store, multimedia, concert chronology, articles, interviews, discography, image gallery, and a discussion board.",
				result.get(pos).getDescription());
		}
		{
			final int pos = 2;
			assertEquals("Google", result.get(pos).getType());
			assertEquals("http://catb.org/jargon/html/F/foo.html", result.get(pos).getUrl());
			assertEquals("foo", result.get(pos).getTitle());
			assertEquals(
				"When ' foo ' is used in connection with 'bar' it has generally traced to the WWII-era Army slang acronym FUBAR ('Fucked Up Beyond All Repair' or 'Fucked Up ...", result
				.get(pos).getDescription());
		}
		{
			final int pos = 3;
			assertEquals("Google", result.get(pos).getType());
			assertEquals("http://www.foopets.com/", result.get(pos).getUrl());
			assertEquals("FooPets | Real Virtual Pets Online", result.get(pos).getTitle());
			assertEquals("Adopt the World's cutest virtual pets, and play games with these adorable cats and dogs.", result.get(pos).getDescription());
		}
	}

	@Test
	public void testBuildQueryUrl() throws Exception {
		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final HtmlTagParser htmlTagParser = new HtmlTagParser(logger);
		final HtmlUtil htmlUtil = new HtmlUtilImpl(logger, htmlTagParser);
		final UrlUtil urlUtil = new UrlUtilImpl();
		final GoogleSearchServiceComponent googleSearchServiceComponent = new GoogleSearchServiceComponent(logger, null, htmlUtil, urlUtil, null, null, null);
		assertEquals("https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=crawler+java", googleSearchServiceComponent.buildQueryUrl(Arrays.asList("crawler", "java"))
			.toExternalForm());

	}
}
