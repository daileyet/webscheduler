package com.openthinks.webscheduler.model;

import java.util.UUID;

public class TaskMetaData {

	private String taskName;
	private String taskType;
	private String groupName;

	private Object taskRef;

	private TaskState taskState;

	public TaskMetaData() {
		taskName = UUID.randomUUID().toString();
		groupName = "default_group";
		taskState = TaskState.NOT_RUNNING;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Object getTaskRef() {
		return taskRef;
	}

	public void setTaskRef(Object taskRef) {
		this.taskRef = taskRef;
	}

	public TaskState getTaskState() {
		return taskState;
	}

	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;
	}

}
