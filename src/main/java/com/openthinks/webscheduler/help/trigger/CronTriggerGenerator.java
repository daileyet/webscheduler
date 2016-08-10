package com.openthinks.webscheduler.help.trigger;

import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.model.task.ITaskTrigger;
import com.openthinks.webscheduler.model.task.trigger.CronTaskTrigger;

class CronTriggerGenerator implements ITriggerGenerator {
	@Override
	public ITaskTrigger generate(WebAttributers was) {
		String cronExpr = was.get(StaticDict.PAGE_PARAM_TASK_TRIGGER_CRON_EXPR);
		CronTaskTrigger taskTrigger = new CronTaskTrigger();
		taskTrigger.setCronExpression(cronExpr);
		return taskTrigger;
	}

}