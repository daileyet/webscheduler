package com.openthinks.webscheduler.service;

import java.util.Collection;

import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.webscheduler.dao.ITaskDao;
import com.openthinks.webscheduler.dao.ITaskDefDao;
import com.openthinks.webscheduler.dao.impl.mapdb.TaskDefMapDBStore;
import com.openthinks.webscheduler.dao.impl.mapdb.TaskMapDBStore;
import com.openthinks.webscheduler.model.TaskRunTimeData;
import com.openthinks.webscheduler.model.task.def.TaskDefRuntimeData;

/**
 * Task business service
 * @author dailey.yet@outlook.com
 *
 */
public class TaskService {
	private ITaskDao taskStore = WebContexts.get().lookup(TaskMapDBStore.class);
	private ITaskDefDao taskDefStore = WebContexts.get().lookup(TaskDefMapDBStore.class);

	public Collection<TaskRunTimeData> getValidTasks() {
		return taskStore.getTasks((task) -> {
			return task.isValid();
		});
	}

	public void saveTask(TaskRunTimeData taskRunTimeData) {
		taskStore.save(taskRunTimeData);
	}

	public TaskRunTimeData getTask(String id) {
		return taskStore.get(id);
	}

	public boolean remove(TaskRunTimeData taskRunTimeData) {
		return taskStore.delete(taskRunTimeData.getTaskId());
	}

	//////////////////////////////////////////////////////////////////
	public Collection<TaskDefRuntimeData> getTaskDefs() {
		return taskDefStore.getTaskDefs((task) -> {
			return true;
		});
	}

	public void saveTaskDef(TaskDefRuntimeData taskDefRunTimeData) {
		taskDefStore.save(taskDefRunTimeData);
	}

	public TaskDefRuntimeData getTaskDef(String fullclassname) {
		return taskDefStore.get(fullclassname);
	}

	public boolean remove(TaskDefRuntimeData taskDefRunTimeData) {
		return taskDefStore.delete(taskDefRunTimeData.getFullName());
	}

}
