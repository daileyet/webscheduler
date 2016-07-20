package com.openthinks.webscheduler.service;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.openthinks.libs.utilities.logger.ProcessLogger;

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
	}

}

class DefaultJobListener implements JobListener {

	@Override
	public String getName() {
		return "DEFAULT-JOB-LISTENER";
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext arg0) {
		ProcessLogger.debug("jobExecutionVetoed");

	}

	@Override
	public void jobToBeExecuted(JobExecutionContext arg0) {
		ProcessLogger.debug("jobToBeExecuted");

	}

	@Override
	public void jobWasExecuted(JobExecutionContext arg0, JobExecutionException arg1) {
		ProcessLogger.debug("jobWasExecuted");
		if (arg1 != null) {

		}

	}

}
