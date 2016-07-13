package com.openthinks.webscheduler.model;

import java.util.UUID;

import com.openthinks.webscheduler.model.task.TaskState;

public class TaskMetaData {
	private String taskId;
	private String taskName;
	private String taskType;
	private String groupName;

	private Object taskRef;

	private TaskState taskState;

	public TaskMetaData() {
		taskId = UUID.randomUUID().toString();
		taskName = "default_task";
		groupName = "default_group";
		taskState = TaskState.NOT_RUNNING;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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

	public boolean isValid() {
		return taskState != null && taskState != TaskState.INVALID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskMetaData other = (TaskMetaData) obj;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaskMetaData [taskId=" + taskId + ", taskName=" + taskName + ", taskType=" + taskType + ", groupName="
				+ groupName + ", taskRef=" + taskRef + ", taskState=" + taskState + "]";
	}

	public void update(TaskMetaData taskMetaDataNew) {
		// TODO Auto-generated method stub

	}

}
