package com.openthinks.webscheduler.dao.impl;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.neodatis.odb.Objects;

import com.openthinks.webscheduler.dao.impl.odb.ODBHelper;
import com.openthinks.webscheduler.dao.impl.odb.TaskDefODBStore;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.model.task.def.TaskDefRuntimeData;

public class TaskDefODBStoreTest {
	TaskDefODBStore taskDefStore;

	@Before
	public void setUp() {
		taskDefStore = new TaskDefODBStore();
		ODBHelper.setUp(new File("R:/MyGit/webscheduler/target/classes"), StaticDict.STORE_DB);
	}

	//@Test
	public void testGet() {
		Objects<TaskDefRuntimeData> taskDefRunTimeDatas = ODBHelper.getSigltonODB()
				.getObjects(TaskDefRuntimeData.class);
		while (taskDefRunTimeDatas.hasNext()) {
			TaskDefRuntimeData taskDefRunTimeData = taskDefRunTimeDatas.next();
			System.out.println(taskDefRunTimeData);
		}
	}

	@Test
	public void testGet2() {
		testGet();
		TaskDefRuntimeData taskDefRunTimeData = taskDefStore
				.get("com.openthinks.webscheduler.task.custom.TestCustomTask");
		System.out.println(taskDefRunTimeData);
	}
}
