package com.openthinks.webscheduler.task;

import java.util.Optional;

import org.quartz.JobExecutionContext;

import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.libs.utilities.CommonUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.model.TaskRunTimeData;
import com.openthinks.webscheduler.service.TaskService;

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

	public Optional<TaskRunTimeData> getTaskMetaData() {
		TaskRunTimeData taskRunTimeData = get(ITaskDefinition.TASK_META);
		return Optional.ofNullable(taskRunTimeData);
	}

	public static final TaskContext wrapper(JobExecutionContext jobExecutionContext) {
		return new TaskContext(jobExecutionContext);
	}

	public void syncTaskMetaData() {
		Optional<TaskRunTimeData> optional = getTaskMetaData();
		if (optional.isPresent()) {
			try {
				WebContexts.get().lookup(TaskService.class).saveTask(optional.get());
			} catch (Exception e) {
				ProcessLogger.warn(CommonUtilities.getCurrentInvokerMethod(), e.getMessage());
			}
		}
	}
}
