package de.benjaminborbe.task.gui.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import de.benjaminborbe.task.api.Task;
import de.benjaminborbe.task.api.TaskIdentifier;

public class TaskGuiUtilUnitTest {

	@Test
	public void testGetChildTasks() {
		final TaskGuiUtil taskGuiUtil = new TaskGuiUtil();

		{
			final List<Task> allTasks = new ArrayList<Task>();
			assertEquals(0, taskGuiUtil.getChildTasks(allTasks, null).size());
		}
		{
			final List<Task> allTasks = new ArrayList<Task>();
			allTasks.add(buildTask(null));
			assertEquals(1, taskGuiUtil.getChildTasks(allTasks, null).size());
		}
		{
			final List<Task> allTasks = new ArrayList<Task>();
			allTasks.add(buildTask(null));
			allTasks.add(buildTask(new TaskIdentifier(123)));
			assertEquals(1, taskGuiUtil.getChildTasks(allTasks, null).size());
		}
		{
			final List<Task> allTasks = new ArrayList<Task>();
			allTasks.add(buildTask(null));
			allTasks.add(buildTask(new TaskIdentifier(123)));
			assertEquals(1, taskGuiUtil.getChildTasks(allTasks, new TaskIdentifier(123)).size());
		}
	}

	private Task buildTask(final TaskIdentifier value) {
		final Task task = EasyMock.createMock(Task.class);
		EasyMock.expect(task.getParentId()).andReturn(value).anyTimes();
		EasyMock.replay(task);
		return task;
	}

}
