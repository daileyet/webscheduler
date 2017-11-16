package com.openthinks.webscheduler.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.model.security.User;
import com.openthinks.webscheduler.model.task.DefaultTaskRef;
import com.openthinks.webscheduler.model.task.ITaskRef;
import com.openthinks.webscheduler.model.task.ITaskTrigger;
import com.openthinks.webscheduler.model.task.TaskResult;
import com.openthinks.webscheduler.model.task.TaskState;
import com.openthinks.webscheduler.task.ITaskDefinition;

/**
 * Task runtime record data
 * @author dailey.yet@outlook.com
 *
 */
public class TaskRunTimeData implements Serializable, Updateable<TaskRunTimeData> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9156251287769007398L;

	private String taskId;
	private String taskName;
	private String taskType;
	private String groupName;

	private ITaskRef taskRef;
	private String taskRefContent;

	private TaskState taskState;

	private TaskResult lastTaskResult;

	private ITaskTrigger taskTrigger;

	//user id
	private String createdBy = null;

	//shared for other guys or private
	private boolean shared = true;

	public TaskRunTimeData() {
	}

	public void makeDefault() {
		taskId = UUID.randomUUID().toString();
		taskName = "default_task";
		groupName = StaticDict.DEFAULT_TASK_GROUP_NAME;
		taskState = TaskState.UN_SCHEDULE;
		taskRef = new DefaultTaskRef();
		taskRefContent = "";
		lastTaskResult = new TaskResult(taskId);
		createdBy = null;
		shared = true;
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

	/**
	 * Task definition class full name; like com.openthinks.webscheduler.task.support.EchoTask
	 * @return String
	 */
	public String getTaskType() {
		return taskType;
	}

	/**
	 * Task definition class simple name; like EchoTask
	 * @return String
	 */
	public String getTaskTypeShort() {
		try {
			return getTaskClass().getSimpleName();
		} catch (ClassNotFoundException e) {
			String[] names = getTaskType().split("\\.");
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

	public TaskResult getLastTaskResult() {
		return lastTaskResult;
	}

	public void setLastTaskResult(TaskResult lastTaskResult) {
		this.lastTaskResult = lastTaskResult;
	}

	public ITaskTrigger getTaskTrigger() {
		return taskTrigger;
	}

	public void setTaskTrigger(ITaskTrigger taskTrigger) {
		this.taskTrigger = taskTrigger;
	}

	/**
	 * check current record is valid or not
	 * @return boolean
	 */
	public boolean isValid() {
		return taskState != null && taskState != TaskState.INVALID;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * when createdBy is empty, then whatever the value of shared, still take shared as true 
	 * @return this task runtime data is shared or private
	 */
	public boolean isShared() {
		return shared == true ? true : getCreatedBy() == null;
	}
	
	public boolean getRelShared(){
		return this.shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
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
	 * @see DefaultTaskRef
	 */
	public void preparedTaskRef() {
		if (!this.hasTaskRef()) {
			this.taskRef = new DefaultTaskRef();
		}
		try {
			this.taskRef.readString(getTaskRefContent());
		} catch (IOException e) {
			//ignore
		}
	}

	/**
	 * when {@link #getTaskRef()} return null, use the given @param taskRef
	 * @param taskRef ITaskRef
	 */
	public void preparedTaskRef(ITaskRef taskRef) {
		if (taskRef != null) {
			this.taskRef = taskRef;
		}
		if (!this.hasTaskRef()) {
			try {
				taskRef.readString(this.getTaskRefContent());
				setTaskRef(taskRef);
			} catch (IOException e) {
				//ignore
			}
		} else {
			this.preparedTaskRef();
		}
	}

	public boolean hasTaskRef() {
		return this.taskRef != null;
	}

	public Optional<String> getRefProp(String propertyName) {
		if (this.taskRef != null) {
			return this.taskRef.getProp(propertyName);
		}
		return Optional.empty();
	}

	@Override
	public String toString() {
		return "TaskRunTimeData [taskId=" + taskId + ", taskName=" + taskName + ", taskType=" + taskType
				+ ", groupName=" + groupName + ", taskRef=" + taskRef + ", taskRefContent=" + taskRefContent
				+ ", taskState=" + taskState + ", lastTaskResult=" + lastTaskResult + ", taskTrigger=" + taskTrigger
				+ ", createdBy=" + createdBy + ", shared=" + shared + "]";
	}

	//	/**
	//	 * update current task runtime record data by other
	//	 * @param taskMetaDataNew TaskRunTimeData New instance of task runtime record data
	//	 */
	//	@Override
	//	public void update(TaskRunTimeData taskMetaDataNew) {
	//		if (taskMetaDataNew.getTaskId() != null)
	//			this.setTaskId(taskMetaDataNew.getTaskId());
	//		if (taskMetaDataNew.getTaskName() != null)
	//			this.setTaskName(taskMetaDataNew.getTaskName());
	//		if (taskMetaDataNew.getTaskType() != null)
	//			this.setTaskType(taskMetaDataNew.getTaskType());
	//		if (taskMetaDataNew.getGroupName() != null)
	//			this.setGroupName(taskMetaDataNew.getGroupName());
	//		if (taskMetaDataNew.getTaskRef() != null)
	//			this.setTaskRef(taskMetaDataNew.getTaskRef());
	//		if (taskMetaDataNew.getTaskState() != null)
	//			this.setTaskState(taskMetaDataNew.getTaskState());
	//		if (taskMetaDataNew.getTaskRefContent() != null)
	//			this.setTaskRefContent(taskMetaDataNew.getTaskRefContent());
	//		if (taskMetaDataNew.getLastTaskResult() != null)
	//			this.setLastTaskResult(taskMetaDataNew.getLastTaskResult());
	//	}
}
