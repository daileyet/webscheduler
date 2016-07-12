package com.openthinks.webscheduler.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import openthinks.others.safaribook.SafariBookConfigure;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.openthinks.easyweb.annotation.AutoComponent;
import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.annotation.ResponseReturn;
import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.easyweb.context.handler.WebAttributers.WebScope;
import com.openthinks.libs.utilities.CommonUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.model.TaskMetaData;
import com.openthinks.webscheduler.service.SchedulerService;
import com.openthinks.webscheduler.service.TaskService;
import com.openthinks.webscheduler.task.Task;
import com.openthinks.webscheduler.task.support.SafaribooksonlineGetterTask;

@Controller("/task")
public class TaskController {

	@AutoComponent
	SchedulerService schedulerService;
	@AutoComponent
	TaskService taskService;

	@Mapping("/add")
	@ResponseReturn(contentType = "text/html")
	public String add() {
		try {
			SafariBookConfigure bookConfigure = SafariBookConfigure.readXML(new FileInputStream(
					"W:\\Book2\\config_default.xml"));
			JobDetail job = JobBuilder.newJob(SafaribooksonlineGetterTask.class).withIdentity("job1", "group1").build();
			job.getJobDataMap().put(Task.TASK_REF, bookConfigure);

			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("job1-trigger", "group1").startNow().build();
			schedulerService.scheduleJob(job, trigger);
		} catch (IOException | SchedulerException e) {
			ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod(), e.getMessage());
		}
		return "<span>Done.</span>";
	}

	@Mapping("/index")
	public String index(WebAttributers was) {
		List<TaskMetaData> tasks = taskService.getValidTasks();
		was.addAttribute(StaticDict.PAGE_TASK_LIST, tasks, WebScope.REQUEST);
		return "WEB-INF/jsp/main.jsp";
	}
}
