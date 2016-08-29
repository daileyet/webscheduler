package com.openthinks.webscheduler.task;

import java.io.Serializable;
import java.util.Optional;

import org.apache.commons.lang3.SerializationUtils;
import org.quartz.JobExecutionContext;

import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.libs.utilities.CommonUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.model.TaskRunTimeData;
import com.openthinks.webscheduler.service.TaskService;

/**
 * simple wrapped for {@link JobExecutionContext}
 * @author dailey.yet@outlook.com
 *
 */
public class TaskContext implements Serializable {
	private static final long serialVersionUID = 4635900468824571727L;
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

	public void put(String key, Object value) {
		context.getMergedJobDataMap().put(key, value);
	}

	/**
	 * get the instance of {@link TaskRunTimeData} from this context
	 * @return Optional<TaskRunTimeData>
	 */
	public Optional<TaskRunTimeData> getTaskRuntimeData() {
		TaskRunTimeData taskRunTimeData = get(ITaskDefinition.TASK_DATA);
		return Optional.ofNullable(taskRunTimeData);
	}

	public void setTaskRuntimeData(TaskRunTimeData taskRunTimeData) {
		put(ITaskDefinition.TASK_DATA, taskRunTimeData);
	}

	public static final TaskContext wrapper(JobExecutionContext jobExecutionContext) {
		return new TaskContext(jobExecutionContext);
	}

	/**
	 * sync {@link TaskRunTimeData} with persist layer, try to store current {@link TaskRunTimeData} instance in this context 
	 */
	public void syncTaskRuntimeData() {
		Optional<TaskRunTimeData> optional = getTaskRuntimeData();
		if (optional.isPresent()) {
			try {
				WebContexts.get().lookup(TaskService.class).saveTask(optional.get());
			} catch (Exception e) {
				ProcessLogger.warn(CommonUtilities.getCurrentInvokerMethod(), e);
			}
		}
	}

	@Override
	public TaskContext clone() {
		return SerializationUtils.clone(this);
	}
}
