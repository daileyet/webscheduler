package com.openthinks.webscheduler.controller;

import java.io.FileInputStream;
import java.io.IOException;

import openthinks.others.safaribook.SafariBookConfigure;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.annotation.ResponseReturn;
import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.libs.utilities.CommonUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.model.task.SafaribooksonlineGetterTask;
import com.openthinks.webscheduler.service.SchedulerService;

@Controller("/task")
public class TaskController {

	SchedulerService schedulerService = WebContexts.get().lookup(SchedulerService.class);

	@Mapping("/add")
	@ResponseReturn(contentType = "text/html")
	public String add() {
		try {
			SafariBookConfigure bookConfigure = SafariBookConfigure.readXML(new FileInputStream(
					"W:\\Book2\\config_default.xml"));
			JobDetail job = JobBuilder.newJob(SafaribooksonlineGetterTask.class).withIdentity("job1", "group1").build();
			job.getJobDataMap().put(SafaribooksonlineGetterTask.CONFIGURE, bookConfigure);

			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("job1-trigger", "group1").startNow().build();
			schedulerService.scheduleJob(job, trigger);
		} catch (IOException | SchedulerException e) {
			ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod(), e.getMessage());
		}
		return "<span>Done.</span>";
	}
}
