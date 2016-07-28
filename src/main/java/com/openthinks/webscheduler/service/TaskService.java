package com.openthinks.webscheduler.service;

import java.util.Collection;

import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.webscheduler.dao.ITaskDao;
import com.openthinks.webscheduler.dao.impl.TaskODBStore;
import com.openthinks.webscheduler.model.TaskRunTimeData;

public class TaskService {
	private ITaskDao taskStore = WebContexts.get().lookup(TaskODBStore.class);

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

}
