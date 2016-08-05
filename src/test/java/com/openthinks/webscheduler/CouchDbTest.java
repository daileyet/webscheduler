package com.openthinks.webscheduler;

import org.junit.Test;
import org.lightcouch.CouchDbClient;
import org.lightcouch.Response;

import com.openthinks.webscheduler.model.TaskRunTimeData;
import com.openthinks.webscheduler.model.task.def.CompileResult;
import com.openthinks.webscheduler.model.task.def.TaskDefRuntimeData;

public class CouchDbTest {
	CouchDbClient dbClient = new CouchDbClient();

	//	@Test
	public void testSaveTaskDef() {

		TaskDefRuntimeData defRuntimeData = new TaskDefRuntimeData();
		defRuntimeData.setSourceCode("test");
		defRuntimeData.setFileName("TestCustomTask.java");
		defRuntimeData.setFullName("com.openthinks.webscheduler.task.custom.TestCustomTask");
		defRuntimeData.setKeepSourceFile(true);
		defRuntimeData.setLastCompileResult(new CompileResult(defRuntimeData.getFullName()));
		Response response = dbClient.save(defRuntimeData);
		System.out.println(response.getId());
		//		System.out.println(response.getError());
	}

	@Test
	public void testSaveTask() {
		TaskRunTimeData taskRunTimeData = new TaskRunTimeData();
		taskRunTimeData.makeDefault();
		taskRunTimeData.setTaskName("testecho1");
		taskRunTimeData.setTaskType("com.openthinks.webscheduler.task.support.EchoTask");
		taskRunTimeData.setTaskRefContent("");
		Response response = dbClient.save(taskRunTimeData);
		System.out.println(response.getId());
		//		System.out.println(response.getError());
		//
	}

}
