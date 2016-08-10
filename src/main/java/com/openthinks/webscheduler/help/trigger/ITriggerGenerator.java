package com.openthinks.webscheduler.help.trigger;

import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.webscheduler.model.task.ITaskTrigger;

/**
 * ITaskTrigger generator
 * @author dailey.yet@outlook.com
 *
 */
public interface ITriggerGenerator {
	/**
	 * 
	 * @param was WebAttributers
	 * @return ITaskTrigger
	 */
	public ITaskTrigger generate(WebAttributers was);
}