package de.benjaminborbe.monitoring.check;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.HashSet;

import org.easymock.EasyMock;
import org.junit.Test;

import de.benjaminborbe.monitoring.api.CheckResult;

public class NodeCheckerCacheUnitTest {

	@Test
	public void testCache() {
		final Node node = EasyMock.createMock(Node.class);
		EasyMock.replay(node);
		final NodeCheckerImpl nodeChecker = EasyMock.createMock(NodeCheckerImpl.class);
		final Collection<CheckResult> checkResults = new HashSet<CheckResult>();
		EasyMock.expect(nodeChecker.checkNode(node)).andReturn(checkResults);
		EasyMock.replay(nodeChecker);
		final NodeCheckerCache nodeCheckerCache = new NodeCheckerCache(nodeChecker);

		// 1. call => nothing in cache
		{
			final Collection<CheckResult> result = nodeCheckerCache.checkNodeWithCache(node);
			assertNotNull(result);
			assertEquals(checkResults, result);
		}
		// 2. call => use cache
		{
			final Collection<CheckResult> result = nodeCheckerCache.checkNodeWithCache(node);
			assertNotNull(result);
			assertEquals(checkResults, result);
		}
	}

}