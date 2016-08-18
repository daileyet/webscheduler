package com.openthinks.webscheduler.task;

import org.junit.Test;
import org.quartz.JobExecutionException;

import com.openthinks.webscheduler.task.support.SimpleDownloadFileTask;

public class DownloadFileTaskTest {

	@Test
	public void test() throws JobExecutionException {
		SimpleDownloadFileTask task = new SimpleDownloadFileTask();
		System.out.println("Start...");
	}

}
