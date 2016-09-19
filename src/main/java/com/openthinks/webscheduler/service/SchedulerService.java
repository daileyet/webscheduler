package com.openthinks.webscheduler.service;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.quartz.UnableToInterruptJobException;
import org.quartz.impl.StdSchedulerFactory;

import com.openthinks.libs.utilities.Checker;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.help.confs.QuartzConfig;
import com.openthinks.webscheduler.model.TaskRunTimeData;
import com.openthinks.webscheduler.model.task.ITaskTrigger;
import com.openthinks.webscheduler.model.task.TaskState;
import com.openthinks.webscheduler.task.TaskContext;
import com.openthinks.webscheduler.task.TaskInterruptException;

/**
 * Scheduler service
 * @author dailey.yet@outlook.com
 *
 */
public class SchedulerService {

	private StdSchedulerFactory stdSchedulerFactory;
	private QuartzConfig quartzConfig;
	private Scheduler scheduler;
	private Lock lock = new ReentrantLock();

	public void init(QuartzConfig quartzConfig) throws SchedulerException {
		ProcessLogger.debug(getClass() + " start init...");
		this.quartzConfig = quartzConfig;
		Checker.require(this.quartzConfig).notNull();
		this.stdSchedulerFactory = new StdSchedulerFactory(this.quartzConfig.getQuartzProps());
	}

	public String getQuartzConfigContent() {
		return quartzConfig == null ? "" : quartzConfig.getConfigContent();
	}

	public String getSchedulerMetaData() {
		try {
			return getScheduler().getMetaData().toString();
		} catch (SchedulerException e) {
			ProcessLogger.warn(e);
			return "";
		}
	}

	private Scheduler getScheduler() throws SchedulerException {
		lock.lock();
		try {
			if (scheduler == null) {
				scheduler = getStdSchedulerFactory().getScheduler();
				scheduler.getListenerManager().addJobListener(new DefaultJobListener());
				scheduler.getListenerManager().addTriggerListener(new DefaultTriggerListener());
			}
		} finally {
			lock.unlock();
		}
		return scheduler;
	}

	private StdSchedulerFactory getStdSchedulerFactory() {
		lock.lock();
		try {
			if (stdSchedulerFactory == null)
				stdSchedulerFactory = new StdSchedulerFactory();
		} finally {
			lock.unlock();
		}
		return stdSchedulerFactory;
	}

	public void start() throws SchedulerException {
		Scheduler scheduler = getScheduler();
		if (scheduler.isShutdown()) {
			this.scheduler = null;
		}
		getScheduler().start();
	}

	public boolean interrupt(JobKey jobKey) {
		boolean isSuccess = false;
		try {
			isSuccess = getScheduler().interrupt(jobKey);
			isSuccess = getScheduler().deleteJob(jobKey);
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

	public boolean unschedule(TaskRunTimeData taskRunTimeData) {
		boolean isSuccess = false;
		try {
			ITaskTrigger taskTrigger = taskRunTimeData.getTaskTrigger();
			TriggerKey triggerKey = taskTrigger.getTriggerKey();
			if (getScheduler().checkExists(triggerKey)) {
				isSuccess = getScheduler().unscheduleJob(triggerKey);
			} else {
				isSuccess = true;
			}
		} catch (SchedulerException e) {
			isSuccess = false;
			ProcessLogger.fatal(e);
		} catch (Exception e) {
			isSuccess = false;
			ProcessLogger.fatal(e);
		}
		return isSuccess;
	}

	public boolean delete(JobKey jobKey) {
		boolean isSuccess = false;
		try {
			isSuccess = getScheduler().deleteJob(jobKey);
		} catch (SchedulerException e) {
			isSuccess = false;
			ProcessLogger.fatal(e);
		}
		return isSuccess;
	}

	public void stop() throws SchedulerException {
		getScheduler().shutdown();
	}

	public void stopAll() throws SchedulerException {
		if (stdSchedulerFactory != null) {
			stdSchedulerFactory.getAllSchedulers().forEach((scheduler) -> {
				try {
					scheduler.shutdown();
				} catch (SchedulerException e) {
					ProcessLogger.warn(e);
				}
			});
		}
	}

	public void scheduleJob(JobDetail job, Trigger trigger) throws SchedulerException {
		getScheduler().scheduleJob(job, trigger);
	}

}

class DefaultTriggerListener implements TriggerListener {

	@Override
	public String getName() {
		return "DEFAULT-TRIGGER-LISTENER";
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		ProcessLogger.debug("trigger fired:" + trigger);
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		ProcessLogger.debug("trigger misfired:" + trigger);

	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		ProcessLogger.debug("trigger complete:" + trigger);
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
		//check task runtime data present and  its task state not equals TaskState.RUNNING
		if (metaData.isPresent() && metaData.get().getTaskState() != TaskState.RUNNING) {
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
		boolean needSync = false;
		//check task runtime data present and it is the last execution
		if (metaData.isPresent() && ctx.getNextFireTime() == null) {
			metaData.get().setTaskState(TaskState.COMPLETE);
			metaData.get().getLastTaskResult().setSuccess(true);
			metaData.get().getLastTaskResult().setEndTime(new Date());
			needSync = true;
		}
		//check execution exception present and it is the last execution
		if (executionException != null && ctx.getNextFireTime() == null) {
			ProcessLogger.error(executionException);
			if (metaData.isPresent()) {
				metaData.get().getLastTaskResult().setSuccess(false);
				metaData.get().getLastTaskResult().track(executionException.getMessage());
				needSync = true;
			}
		}
		if (needSync) {
			TaskContext.wrapper(ctx).syncTaskRuntimeData();
		}

	}

}
