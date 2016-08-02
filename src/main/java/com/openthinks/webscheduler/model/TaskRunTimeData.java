package com.openthinks.webscheduler.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import com.openthinks.webscheduler.model.task.DefaultTaskRef;
import com.openthinks.webscheduler.model.task.ExecutionResult;
import com.openthinks.webscheduler.model.task.ITaskRef;
import com.openthinks.webscheduler.model.task.TaskResult;
import com.openthinks.webscheduler.model.task.TaskState;
import com.openthinks.webscheduler.task.ITaskDefinition;

/**
 * Task runtime record data
 * @author dailey.yet@outlook.com
 *
 */
public class TaskRunTimeData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9156251287769007398L;

	private String taskId;
	private String taskName;
	private String taskType;
	private String groupName;

	@Deprecated
	private ITaskRef taskRef;
	private String taskRefContent;

	private TaskState taskState;

	private ExecutionResult lastTaskResult;

	public TaskRunTimeData() {
	}

	public void makeDefault() {
		taskId = UUID.randomUUID().toString();
		taskName = "default_task";
		groupName = "default_group";
		taskState = TaskState.UN_SCHEDULE;
		taskRef = new DefaultTaskRef();
		taskRefContent = "";
		lastTaskResult = new TaskResult(taskId);
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

	public ExecutionResult getLastTaskResult() {
		return lastTaskResult;
	}

	public void setLastTaskResult(ExecutionResult lastTaskResult) {
		this.lastTaskResult = lastTaskResult;
	}

	/**
	 * check current record is valid or not
	 * @return boolean
	 */
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
		TaskRunTimeData other = (TaskRunTimeData) obj;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		return true;
	}

	/**
	 * get associated task class
	 * @return Class<T> 
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public <T extends ITaskDefinition> Class<T> getTaskClass() throws ClassNotFoundException {

		return (Class<T>) Class.forName(getTaskType());
	}

	/**
	 * convert the task reference from text to {@link ITaskRef} 
	 */
	public void preparedTaskRef() {
		if (this.taskRef != null) {
			try {
				this.taskRef.readString(getTaskRefContent());
			} catch (IOException e) {
				//ignore
			}
		}
	}

	@Override
	public String toString() {
		return "TaskRunTimeData [taskId=" + taskId + ", taskName=" + taskName + ", taskType=" + taskType
				+ ", groupName=" + groupName + ", taskRef=" + taskRef + ", taskState=" + taskState + "]";
	}

	/**
	 * update current task runtime record data by other
	 * @param taskMetaDataNew TaskRunTimeData New instance of task runtime record data
	 */
	public void update(TaskRunTimeData taskMetaDataNew) {
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
		if (taskMetaDataNew.getTaskRefContent() != null)
			this.setTaskRefContent(taskMetaDataNew.getTaskRefContent());
		if (taskMetaDataNew.getLastTaskResult() != null)
			this.setLastTaskResult(taskMetaDataNew.getLastTaskResult());
	}
}
