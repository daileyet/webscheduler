package com.openthinks.webscheduler.task.support;

import com.openthinks.webscheduler.task.ITaskDefinition;
import com.openthinks.webscheduler.task.TaskDefinitionDescriber;

/**
 * used for build-in task definition
 * @author dailey.yet@outlook.com
 *
 */
interface SupportTaskDefinition extends ITaskDefinition {
	@Override
	default TaskDefinitionDescriber getTaskDescriber() {
		return TaskDefinitionDescriber.build(getClass()).push("Build-in Task: " + getClass().getName());
	}
}
