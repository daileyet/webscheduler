package com.openthinks.webscheduler.controller;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.AutoComponent;
import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.easyweb.context.handler.WebAttributers.WebScope;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.help.PageMap;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.model.TaskMetaData;
import com.openthinks.webscheduler.service.SchedulerService;
import com.openthinks.webscheduler.service.TaskService;
import com.openthinks.webscheduler.task.ITask;
import com.openthinks.webscheduler.task.TaskTypes;

@Controller("/task")
public class TaskController {

	@AutoComponent
	SchedulerService schedulerService;
	@AutoComponent
	TaskService taskService;

	@Mapping("/schedule")
	public String schedule(WebAttributers was) {
		String taskId = (String) was.get(StaticDict.PAGE_PARAM_TASK_ID);
		TaskMetaData taskMetaData = taskService.getTask(taskId);
		List<Exception> errors = new ArrayList<Exception>();
		Class<? extends ITask> clazz = null;
		try {
			clazz = taskMetaData.getTaskClass();
		} catch (ClassNotFoundException e) {
			ProcessLogger.error(e);
			errors.add(e);
		}
		JobDetail job = JobBuilder.newJob(clazz).withIdentity(taskMetaData.getTaskId(), taskMetaData.getGroupName())
				.build();
		job.getJobDataMap().put(ITask.TASK_META, taskMetaData);
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(taskMetaData.getTaskId() + "-trigger", taskMetaData.getGroupName()).startNow().build();

		try {
			schedulerService.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			ProcessLogger.fatal(e);
			errors.add(e);
		}
		was.addError(StaticDict.PAGE_ATTRIBUTE_ERRORS, errors, WebScope.REQUEST);
		return index(was);
	}

	@Mapping("/add")
	public String add(WebAttributers was) {

		TaskMetaData taskMetaData = new TaskMetaData();
		taskMetaData.setTaskName((String) was.get(StaticDict.PAGE_PARAM_TASK_NAME));
		taskMetaData.setTaskType((String) was.get(StaticDict.PAGE_PARAM_TASK_TYPE));
		taskMetaData.setTaskRefContent((String) was.get(StaticDict.PAGE_PARAM_TASK_REF));
		taskService.saveTask(taskMetaData);
		PageMap pm = PageMap.build().push("title", "Task - Adding").push("activeSidebar", "tasks")
				.push("redirectUrl", WebUtils.path("/task/index"));
		return intermediate(was, pm);
	}

	private String intermediate(WebAttributers was, PageMap pm) {
		was.addAttribute(StaticDict.PAGE_ATTRIBUTE_MAP, pm, WebScope.REQUEST);
		return "WEB-INF/jsp/template/intermediate.jsp";
	}

	@Mapping("/to/add")
	public String goToAdd(WebAttributers was) {
		was.addAttribute(StaticDict.PAGE_ATTRIBUTE_TASK_TYPES, TaskTypes.getSupportTasks(), WebScope.REQUEST);
		return "WEB-INF/jsp/task/add.jsp";
	}

	@Mapping("/index")
	public String index(WebAttributers was) {
		List<TaskMetaData> tasks = taskService.getValidTasks();
		was.addAttribute(StaticDict.PAGE_TASK_LIST, tasks, WebScope.REQUEST);
		return "WEB-INF/jsp/task/index.jsp";
	}
}
