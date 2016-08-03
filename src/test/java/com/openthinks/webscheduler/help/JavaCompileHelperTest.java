package com.openthinks.webscheduler.help;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.openthinks.webscheduler.model.task.def.TaskDefRuntimeData;

public class JavaCompileHelperTest {
	TaskDefRuntimeData defRuntimeData;
	
	@Before
	public void setUp(){
		defRuntimeData = new TaskDefRuntimeData();
		defRuntimeData.setFileName("TestCustomTask.java");
		defRuntimeData.setFullName("com.openthinks.webscheduler.task.custom.TestCustomTask");
		defRuntimeData.setKeepSourceFile(true);
		defRuntimeData.setSourceDir("R:\\MyGit\\webscheduler\\target\\classes\\com\\openthinks\\webscheduler\\task\\custom");
		defRuntimeData.setTargetDir("R:\\MyGit\\webscheduler\\target\\classes");
	}
	
	@Test
	public void testCompile(){
		//boolean isSuccess= JavaCompileHelper.getCompiler(defRuntimeData).exec();
		
		 File targetClassDir = new File("R:\\MyGit\\webscheduler\\target\\webscheduler\\WEB-INF","ext/classes");
		 if(!targetClassDir.exists()){
			 targetClassDir.mkdirs();
		 }
		//System.out.println(isSuccess);
	}
	
	
}
