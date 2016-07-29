package com.openthinks.webscheduler.service;

import java.util.Date;
import java.util.Optional;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.UnableToInterruptJobException;
import org.quartz.impl.StdSchedulerFactory;

import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.model.TaskRunTimeData;
import com.openthinks.webscheduler.model.task.TaskState;
import com.openthinks.webscheduler.task.ITaskDefinition;
import com.openthinks.webscheduler.task.TaskContext;
import com.openthinks.webscheduler.task.TaskInterruptException;

/**
 * Scheduler service
 * @author dailey.yet@outlook.com
 *
 */
public class SchedulerService {

	private Scheduler scheduler;

	public SchedulerService() {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.getListenerManager().addJobListener(new DefaultJobListener());
		} catch (SchedulerException e) {
			ProcessLogger.fatal(e);
		}
	}

	public void start() throws SchedulerException {
		scheduler.start();
	}

	public boolean interrupt(JobKey jobKey) {
		boolean isSuccess = false;
		try {
			isSuccess = scheduler.interrupt(jobKey);
			isSuccess = scheduler.deleteJob(jobKey);
		} catch (UnableToInterruptJobException e) {
			isSuccess = false;
		} catch (TaskInterruptException e) {
			isSuccess = true;
		} catch (SchedulerException e) {
			isSuccess = false;
			ProcessLogger.fatal(e);
		}

		return isSuccess;
	}

	public boolean delete(JobKey jobKey) {
		boolean isSuccess = false;
		try {
			isSuccess = scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			isSuccess = false;
			ProcessLogger.fatal(e);
		}
		return isSuccess;
	}

	public void stop() throws SchedulerException {
		scheduler.shutdown();
	}

	public void scheduleJob(JobDetail job, Trigger trigger) throws SchedulerException {
		scheduler.scheduleJob(job, trigger);
		Object dataObj = job.getJobDataMap().get(ITaskDefinition.TASK_DATA);
		if (dataObj != null && dataObj instanceof TaskRunTimeData) {
			TaskRunTimeData taskRunTimeData = (TaskRunTimeData) dataObj;
			taskRunTimeData.setTaskState(TaskState.SCHEDULED);
		}
	}

}

class DefaultJobListener implements JobListener {

	@Override
	public String getName() {
		return "DEFAULT-JOB-LISTENER";
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext ctx) {
		ProcessLogger.debug("jobExecutionVetoed");
		Optional<TaskRunTimeData> metaData = TaskContext.wrapper(ctx).getTaskRuntimeData();
		if (metaData.isPresent()) {
			metaData.get().setTaskState(TaskState.INTERRUPT);
			metaData.get().getLastTaskResult().setSuccess(false);
			metaData.get().getLastTaskResult().setEndTime(new Date());
			TaskContext.wrapper(ctx).syncTaskRuntimeData();
		}
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext ctx) {
		ProcessLogger.debug("jobToBeExecuted");
		Optional<TaskRunTimeData> metaData = TaskContext.wrapper(ctx).getTaskRuntimeData();
		if (metaData.isPresent()) {
			metaData.get().setTaskState(TaskState.RUNNING);
			metaData.get().getLastTaskResult().clear();
			metaData.get().getLastTaskResult().setStartTime(new Date());
			TaskContext.wrapper(ctx).syncTaskRuntimeData();
		}

	}

	@Override
	public void jobWasExecuted(JobExecutionContext ctx, JobExecutionException executionException) {
		ProcessLogger.debug("jobWasExecuted");
		Optional<TaskRunTimeData> metaData = TaskContext.wrapper(ctx).getTaskRuntimeData();
		if (metaData.isPresent()) {
			metaData.get().setTaskState(TaskState.COMPLETE);
			metaData.get().getLastTaskResult().setSuccess(true);
			metaData.get().getLastTaskResult().setEndTime(new Date());
			TaskContext.wrapper(ctx).syncTaskRuntimeData();
		}
		if (executionException != null) {
			ProcessLogger.error(executionException);
			if (metaData.isPresent()) {
				metaData.get().getLastTaskResult().setSuccess(false);
				metaData.get().getLastTaskResult().track(executionException.getMessage());
				TaskContext.wrapper(ctx).syncTaskRuntimeData();
			}
		}

	}

}
