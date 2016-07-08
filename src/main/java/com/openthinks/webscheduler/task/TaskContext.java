package com.openthinks.webscheduler.task;

import org.quartz.JobExecutionContext;

public class TaskContext {
	private JobExecutionContext context;

	public TaskContext(JobExecutionContext context) {
		super();
		this.context = context;
	}

	public JobExecutionContext getContext() {
		return context;
	}

	public Object get(Object key) {
		return context.getMergedJobDataMap().get(key);
	}
}
