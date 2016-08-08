package com.openthinks.webscheduler.model.task;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.openthinks.webscheduler.model.TaskRunTimeData;

public class UpdateableTest {
	TaskRunTimeData oldTask, newTask;

	@Before
	public void setUp() {
		oldTask = new TaskRunTimeData();
		oldTask.makeDefault();

		newTask = new TaskRunTimeData();
		newTask.makeDefault();
		newTask.getLastTaskResult().start();
		newTask.setTaskRefContent("##properties");
		newTask.getLastTaskResult().track("test");
		newTask.getLastTaskResult().end();
	}

	@Test
	public void testUpdate() {
		Assert.assertEquals(oldTask.getTaskRefContent(), "");
		Assert.assertEquals(oldTask.getLastTaskResult().getLogContent(), "");
		System.out.println(oldTask);
		System.out.println(newTask);
		oldTask.update(newTask);
		System.out.println("------------------");
		System.out.println(oldTask);
		Assert.assertEquals(oldTask.getTaskRefContent(), "##properties");
		Assert.assertNotNull(oldTask.getLastTaskResult().getStartTime());
	}
}
