package com.openthinks.webscheduler.controller;

import com.openthinks.easyweb.annotation.AutoComponent;
import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Jsonp;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.annotation.ResponseReturn;
import com.openthinks.easyweb.annotation.ResponseReturn.ResponseReturnType;
import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.easyweb.utils.json.OperationJson;
import com.openthinks.webscheduler.model.task.TaskState;
import com.openthinks.webscheduler.service.TaskService;
import com.openthinks.webscheduler.task.TaskTypes;

@Controller
public class IndexController {
	@AutoComponent
	TaskService taskService;

	@Mapping("/index")
	public String index(WebAttributers was) {
		return "WEB-INF/jsp/main.jsp";
	}

	@Mapping("/help")
	public String test(WebAttributers was) {
		return "WEB-INF/jsp/help.jsp";
	}

	@Mapping("/chart1")
	@Jsonp
	@ResponseReturn(contentType = ResponseReturnType.TEXT_JAVASCRIPT)
	public String chart1(WebAttributers was) {
		Integer[] datas = new Integer[] { TaskTypes.getSupportTasks().size(), TaskTypes.getCustomTasks().size() };
		return OperationJson.build().sucess().setOther(datas).toString();
	}

	@Mapping("/chart2")
	@Jsonp
	@ResponseReturn(contentType = ResponseReturnType.TEXT_JAVASCRIPT)
	public String chart2(WebAttributers was) {
		Integer[] datas = new Integer[] { taskService.getTasksByState(TaskState.UN_SCHEDULE).size(),
				taskService.getTasksByState(TaskState.SCHEDULED).size(),
				taskService.getTasksByState(TaskState.RUNNING).size(),
				taskService.getTasksByState(TaskState.COMPLETE).size(),
				taskService.getTasksByState(TaskState.INTERRUPT).size(),
				taskService.getTasksByState(TaskState.INVALID).size() };
		return OperationJson.build().sucess().setOther(datas).toString();
	}
}
