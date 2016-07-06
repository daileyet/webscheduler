package com.openthinks.webscheduler.controller;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.annotation.ResponseReturn;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.MyJob;
import com.openthinks.webscheduler.SchedulerManagement;

@Controller("/task")
public class TaskController {

	@Mapping("/add")
	@ResponseReturn(contentType = "text/html")
	public String add() {
		JobDetail job = newJob(MyJob.class).withIdentity("job1", "group1").build();

		// Trigger the job to run now, and then repeat every 40 seconds
		Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startNow()
				.withSchedule(simpleSchedule().withIntervalInSeconds(40).repeatForever()).build();

		ProcessLogger.info("Schedule job...");
		try {
			SchedulerManagement.get().scheduleJob(job, trigger);
			ProcessLogger.info("Schedule job successfully");
		} catch (SchedulerException e) {
			ProcessLogger.error(e.getMessage());
		}

		return "<span>Done.</span>";
	}
}
