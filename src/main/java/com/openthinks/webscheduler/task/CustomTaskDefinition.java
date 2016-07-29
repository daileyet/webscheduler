package com.openthinks.webscheduler.task;

/**
 * used for customized task definition
 * @author dailey.yet@outlook.com
 *
 */
public interface CustomTaskDefinition extends ITaskDefinition {
	@Override
	default TaskDefinitionDescriber getTaskDescriber() {
		return TaskDefinitionDescriber.build(getClass()).push("Custom Task: " + getClass().getName());
	}
}