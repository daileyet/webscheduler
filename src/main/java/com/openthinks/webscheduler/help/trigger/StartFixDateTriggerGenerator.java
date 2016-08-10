package com.openthinks.webscheduler.help.trigger;

import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.help.StaticUtils;
import com.openthinks.webscheduler.model.task.ITaskTrigger;
import com.openthinks.webscheduler.model.task.trigger.SimpleTaskTrigger;

class StartFixDateTriggerGenerator extends StartNowTriggerGenerator {

	@Override
	public ITaskTrigger generate(WebAttributers was) {
		ITaskTrigger taskTrigger = super.generate(was);
		String startDate = was.get(StaticDict.PAGE_PARAM_TASK_TRIGGER_STARTDATE);
		if (startDate == null || "".equals(startDate))
			return taskTrigger;
		if (taskTrigger instanceof SimpleTaskTrigger) {
			((SimpleTaskTrigger) taskTrigger).setStartTime(StaticUtils.parseDate(startDate));
		}
		return taskTrigger;
	}

}