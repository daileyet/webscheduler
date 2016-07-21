package com.openthinks.webscheduler.service;

import java.util.Date;
import java.util.Optional;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.model.TaskMetaData;
import com.openthinks.webscheduler.model.task.TaskState;
import com.openthinks.webscheduler.task.ITask;
import com.openthinks.webscheduler.task.TaskContext;

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

	public void stop() throws SchedulerException {
		scheduler.shutdown();
	}

	public void scheduleJob(JobDetail job, Trigger trigger) throws SchedulerException {
		scheduler.scheduleJob(job, trigger);
		Object dataObj = job.getJobDataMap().get(ITask.TASK_META);
		if (dataObj != null && dataObj instanceof TaskMetaData) {
			TaskMetaData taskMetaData = (TaskMetaData) dataObj;
			taskMetaData.setTaskState(TaskState.SCHEDULED);
			taskMetaData.getLastTaskResult().setStartTime(new Date());
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
		Optional<TaskMetaData> metaData = TaskContext.wrapper(ctx).getTaskMetaData();
		if (metaData.isPresent()) {
			metaData.get().setTaskState(TaskState.INTERRUPT);
			metaData.get().getLastTaskResult().setSuccess(false);
			metaData.get().getLastTaskResult().setEndTime(new Date());
		}
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext ctx) {
		ProcessLogger.debug("jobToBeExecuted");
		Optional<TaskMetaData> metaData = TaskContext.wrapper(ctx).getTaskMetaData();
		if (metaData.isPresent()) {
			metaData.get().setTaskState(TaskState.RUNNING);
		}

	}

	@Override
	public void jobWasExecuted(JobExecutionContext ctx, JobExecutionException executionException) {
		ProcessLogger.debug("jobWasExecuted");
		Optional<TaskMetaData> metaData = TaskContext.wrapper(ctx).getTaskMetaData();
		if (metaData.isPresent()) {
			metaData.get().setTaskState(TaskState.COMPLETE);
		}
		if (executionException != null) {
			ProcessLogger.error(executionException);
			if (metaData.isPresent()) {
				metaData.get().getLastTaskResult().setSuccess(false);
				metaData.get().getLastTaskResult().setEndTime(new Date());
				metaData.get().getLastTaskResult().setLogContent(executionException.getMessage());
			}
		}

	}

}
