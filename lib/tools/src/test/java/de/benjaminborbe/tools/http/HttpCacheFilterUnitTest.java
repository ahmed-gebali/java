package de.benjaminborbe.tools.http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpCacheFilterUnitTest {

	@Test
	public void testCacheEntry() {
		final String contentType = "contentTyp";
		final String writerContent = "writer";
		final byte[] streamContent = new byte[10];
		final HttpCacheEntry cacheEntry = new HttpCacheEntry(contentType, writerContent, streamContent);
		assertEquals(contentType, cacheEntry.getContentType());
		assertEquals(writerContent, cacheEntry.getWriterContent());
		assertEquals(streamContent.length, cacheEntry.getStreamContent().length);
		assertTrue(streamContent.hashCode() != cacheEntry.getStreamContent().hashCode());
		for (int i = 0; i < streamContent.length; ++i) {
			assertEquals(streamContent[i], cacheEntry.getStreamContent()[i]);
		}
	}
}
