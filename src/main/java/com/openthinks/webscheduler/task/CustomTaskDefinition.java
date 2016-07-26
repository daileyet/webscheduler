package com.openthinks.webscheduler.task;

public interface CustomTaskDefinition extends ITaskDefinition {
	@Override
	default TaskDefinitionDescriber getTaskDescriber() {
		return TaskDefinitionDescriber.build(getClass()).push("Custom Task: " + getClass().getName());
	}
}