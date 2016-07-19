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

	@SuppressWarnings("unchecked")
	public <T extends Object> T get(Object key) {
		return (T) context.getMergedJobDataMap().get(key);
	}
}
