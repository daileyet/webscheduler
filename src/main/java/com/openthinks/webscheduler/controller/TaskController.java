package com.openthinks.webscheduler.controller;

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
		boolean isSuccess = true;
		String taskId = was.get(StaticDict.PAGE_PARAM_TASK_ID);
		TaskMetaData taskMetaData = taskService.getTask(taskId);
		Class<? extends ITask> clazz = null;
		try {
			clazz = taskMetaData.getTaskClass();
		} catch (ClassNotFoundException e) {
			ProcessLogger.error(e);
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1, "Can not found this task type.", WebScope.REQUEST);
		}
		JobDetail job = JobBuilder.newJob(clazz).withIdentity(taskMetaData.getTaskId(), taskMetaData.getGroupName())
				.build();
		job.getJobDataMap().put(ITask.TASK_META, taskMetaData);
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(taskMetaData.getTaskId() + "-trigger", taskMetaData.getGroupName()).startNow().build();

		try {
			schedulerService.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			isSuccess = false;
			ProcessLogger.fatal(e);
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_2, "Can not schedule this task, " + e.getMessage(),
					WebScope.REQUEST);
		}
		if (!isSuccess) {
			errorPage(was, this.newPageMap());
		}
		return index(was);
	}

	@Mapping("/add")
	public String add(WebAttributers was) {
		boolean isSuccess = true;
		TaskMetaData taskMetaData = new TaskMetaData();
		taskMetaData.setTaskName(was.get(StaticDict.PAGE_PARAM_TASK_NAME));
		taskMetaData.setTaskType(was.get(StaticDict.PAGE_PARAM_TASK_TYPE));
		taskMetaData.setTaskRefContent(was.get(StaticDict.PAGE_PARAM_TASK_REF));
		PageMap pm = newPageMap();
		try {
			taskService.saveTask(taskMetaData);
		} catch (Exception e) {
			isSuccess = false;
			ProcessLogger.error(e);
			was.addError(e.getClass().getName(), "Save new task failed." + e.getMessage(), WebScope.REQUEST);
		}
		if (!isSuccess) {
			return errorPage(was, pm);
		}
		pm.push("title", "Task - Adding").push("redirectUrl", WebUtils.path("/task/index"));
		return intermediatePage(was, pm);
	}

	private String intermediatePage(WebAttributers was, PageMap pm) {
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_MAP, pm);
		return "WEB-INF/jsp/template/intermediate.jsp";
	}

	private String errorPage(WebAttributers was, PageMap pm) {
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_MAP, pm);
		return "WEB-INF/jsp/template/business.error.jsp";
	}

	@Mapping("/to/add")
	public String goToAdd(WebAttributers was) {
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_TASK_TYPES, TaskTypes.getSupportTasks());
		return "WEB-INF/jsp/task/add.jsp";
	}

	@Mapping("/to/edit")
	public String goToEdit(WebAttributers was) {
		String taskId = was.get(StaticDict.PAGE_PARAM_TASK_ID);
		TaskMetaData taskMetaData = taskService.getTask(taskId);
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_TASK_TYPES, TaskTypes.getSupportTasks());
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_MAP,
				PageMap.build().push(StaticDict.PAGE_ATTRIBUTE_TASK_META, taskMetaData));
		return "WEB-INF/jsp/task/edit.jsp";
	}

	@Mapping("/edit")
	public String edit(WebAttributers was) {
		boolean isSuccess = true;
		TaskMetaData taskMetaData = new TaskMetaData();
		taskMetaData.setTaskId(was.get(StaticDict.PAGE_PARAM_TASK_ID));
		taskMetaData.setTaskName(was.get(StaticDict.PAGE_PARAM_TASK_NAME));
		taskMetaData.setTaskType(was.get(StaticDict.PAGE_PARAM_TASK_TYPE));
		taskMetaData.setTaskRefContent(was.get(StaticDict.PAGE_PARAM_TASK_REF));
		PageMap pm = newPageMap();

		try {
			taskService.saveTask(taskMetaData);
		} catch (Exception e) {
			isSuccess = false;
			ProcessLogger.error(e);
			was.addError(e.getClass().getName(), "Change task failed." + e.getMessage(), WebScope.REQUEST);
		}
		if (!isSuccess) {
			return errorPage(was, pm);
		}
		pm.push("title", "Task - Editing").push("redirectUrl", WebUtils.path("/task/index"));
		return intermediatePage(was, pm);
	}

	@Mapping("/index")
	public String index(WebAttributers was) {
		List<TaskMetaData> tasks = taskService.getValidTasks();
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_TASK_LIST, tasks);
		return "WEB-INF/jsp/task/index.jsp";
	}

	private PageMap newPageMap() {
		return PageMap.build().push("activeSidebar", "tasks");
	}
}
