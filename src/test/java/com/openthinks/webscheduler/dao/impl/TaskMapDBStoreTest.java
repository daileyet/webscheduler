package com.openthinks.webscheduler.dao.impl;

import java.io.File;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.openthinks.webscheduler.dao.impl.mapdb.MapDBHelper;
import com.openthinks.webscheduler.dao.impl.mapdb.TaskMapDBStore;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.model.TaskRunTimeData;

public class TaskMapDBStoreTest {
	TaskMapDBStore taskStore;

	@Before
	public void setUp() {
		try {
			MapDBHelper.setUp(new File("R:/MyGit/webscheduler"), StaticDict.STORE_DB);
		} catch (Exception e) {
		}
		taskStore = new TaskMapDBStore();
	}

	@Test
	public void testGet() {
		Collection<TaskRunTimeData> collects = taskStore.getTasks((task) -> {
			return true;
		});
		for (TaskRunTimeData task : collects) {
			System.out.println(task);
		}
		System.out.println(taskStore.get("d0c71de4-d788-4886-8b15-e87ea5c8987e"));
	}
}
