package com.openthinks.webscheduler.model;

import java.util.UUID;

import com.openthinks.webscheduler.model.task.DefaultTaskRef;
import com.openthinks.webscheduler.model.task.ITaskRef;
import com.openthinks.webscheduler.model.task.TaskState;
import com.openthinks.webscheduler.task.ITask;

public class TaskMetaData {
	private int taskSeq;

	private String taskId;
	private String taskName;
	private String taskType;
	private String groupName;

	private ITaskRef taskRef;
	private String taskRefContent;

	private TaskState taskState;

	public TaskMetaData() {
		taskId = UUID.randomUUID().toString();
		taskName = "default_task";
		groupName = "default_group";
		taskState = TaskState.NOT_RUNNING;
		taskRef = new DefaultTaskRef();
	}

	public int getTaskSeq() {
		return taskSeq;
	}

	public void setTaskSeq(int taskSeq) {
		this.taskSeq = taskSeq;
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

	public String getTaskTypeShort() {
		try {
			return getTaskClass().getSimpleName();
		} catch (ClassNotFoundException e) {
			String[] names = getTaskType().split(".");
			return names[names.length - 1];
		}
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

	public ITaskRef getTaskRef() {
		return taskRef;
	}

	public void setTaskRef(ITaskRef taskRef) {
		this.taskRef = taskRef;
	}

	public TaskState getTaskState() {
		return taskState;
	}

	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;
	}

	public String getTaskRefContent() {
		return taskRefContent;
	}

	public void setTaskRefContent(String taskRefContent) {
		this.taskRefContent = taskRefContent;
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

	@SuppressWarnings("unchecked")
	public <T extends ITask> Class<T> getTaskClass() throws ClassNotFoundException {

		return (Class<T>) Class.forName(getTaskType());
	}

	@Override
	public String toString() {
		return "TaskMetaData [taskId=" + taskId + ", taskName=" + taskName + ", taskType=" + taskType + ", groupName="
				+ groupName + ", taskRef=" + taskRef + ", taskState=" + taskState + "]";
	}

	public void update(TaskMetaData taskMetaDataNew) {
		if (taskMetaDataNew.getTaskId() != null)
			this.setTaskId(taskMetaDataNew.getTaskId());
		if (taskMetaDataNew.getTaskName() != null)
			this.setTaskName(taskMetaDataNew.getTaskName());
		if (taskMetaDataNew.getTaskType() != null)
			this.setTaskType(taskMetaDataNew.getTaskType());
		if (taskMetaDataNew.getGroupName() != null)
			this.setGroupName(taskMetaDataNew.getGroupName());
		if (taskMetaDataNew.getTaskRef() != null)
			this.setTaskRef(taskMetaDataNew.getTaskRef());
		if (taskMetaDataNew.getTaskState() != null)
			this.setTaskState(taskMetaDataNew.getTaskState());
	}
}
