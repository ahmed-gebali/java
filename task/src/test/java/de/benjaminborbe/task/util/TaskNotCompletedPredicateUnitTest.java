package de.benjaminborbe.task.util;

import de.benjaminborbe.task.dao.task.TaskBean;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TaskNotCompletedPredicateUnitTest {

	@Test
	public void testApply() {
		final TaskNotCompletedPredicate<TaskBean> p = new TaskNotCompletedPredicate<>();
		{
			final TaskBean task = new TaskBean();
			task.setCompleted(null);
			assertTrue(p.apply(task));
		}
		{
			final TaskBean task = new TaskBean();
			task.setCompleted(true);
			assertFalse(p.apply(task));
		}
		{
			final TaskBean task = new TaskBean();
			task.setCompleted(false);
			assertTrue(p.apply(task));
		}
	}
}
