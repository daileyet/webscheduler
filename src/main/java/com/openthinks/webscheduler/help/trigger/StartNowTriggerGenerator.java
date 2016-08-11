package com.openthinks.webscheduler.help.trigger;

import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.help.StaticUtils;
import com.openthinks.webscheduler.model.task.ITaskTrigger;
import com.openthinks.webscheduler.model.task.SupportedTrigger;
import com.openthinks.webscheduler.model.task.trigger.SimpleTaskTrigger;

/**
 * 
 * @author dailey.yet@outlook.com
 * @see {@link SupportedTrigger}
 */
class StartNowTriggerGenerator implements ITriggerGenerator {
	@Override
	public ITaskTrigger generate(WebAttributers was) {
		boolean repeatable = StaticDict.PAGE_PARAM_TASK_TRIGGER_REPEATABLE_ENABLE
				.equalsIgnoreCase(was.get(StaticDict.PAGE_PARAM_TASK_TRIGGER_REPEATABLE)) ? true : false;
		SimpleTaskTrigger taskTrigger = new SimpleTaskTrigger();
		if (!repeatable)
			return taskTrigger;
		Integer repeatCount = StaticDict.NO_REPEAT_TRIGGER;
		Integer repeatInterval = StaticDict.DEFAULT_REPEAT_INTERVAL;
		try {
			repeatCount = Integer.valueOf(was.get(StaticDict.PAGE_PARAM_TASK_TRIGGER_REPEAT_COUNT));
		} catch (Exception e) {
			ProcessLogger.warn(e);
		}
		ProcessLogger.debug(
				was.get("repeatcount:" + StaticDict.PAGE_PARAM_TASK_TRIGGER_REPEAT_COUNT) + " -> " + repeatCount);
		taskTrigger.setRepeatCount(repeatCount);
		try {
			repeatInterval = Integer.valueOf(was.get(StaticDict.PAGE_PARAM_TASK_TRIGGER_REPEAT_INTERVAL));
		} catch (Exception e) {
			ProcessLogger.warn(e);
		}
		taskTrigger.setIntervalInSeconds(repeatInterval);

		String endDate = was.get(StaticDict.PAGE_PARAM_TASK_TRIGGER_ENDDATE);
		if (endDate != null && !"".equals(endDate))
			taskTrigger.setEndTime(StaticUtils.parseDate(endDate));
		return taskTrigger;
	}
}