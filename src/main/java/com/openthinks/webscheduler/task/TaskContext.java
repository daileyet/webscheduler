package com.openthinks.webscheduler.task;

import java.util.Optional;

import org.quartz.JobExecutionContext;

import com.openthinks.webscheduler.model.TaskMetaData;

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
	
	public  Optional<TaskMetaData> getTaskMetaData() {
		TaskMetaData taskMetaData = get(ITask.TASK_META);
		return Optional.ofNullable(taskMetaData);
	}
	
	public static final TaskContext wrapper(JobExecutionContext jobExecutionContext){
		return new TaskContext(jobExecutionContext);
	}
}
