package com.openthinks.webscheduler;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerManagement {

	public static SchedulerManagement get() {
		return SchedulerManagementHolder.instance;
	}

	private static final class SchedulerManagementHolder {
		private static final SchedulerManagement instance = new SchedulerManagement();
	}

	private Scheduler scheduler;

	private SchedulerManagement() {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
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
