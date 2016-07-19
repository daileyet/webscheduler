package com.openthinks.webscheduler.service;

import java.util.List;

import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.webscheduler.dao.ITaskDao;
import com.openthinks.webscheduler.dao.impl.TaskStore;
import com.openthinks.webscheduler.model.TaskMetaData;

public class TaskService {
	private ITaskDao taskStore = WebContexts.get().lookup(TaskStore.class);

	public List<TaskMetaData> getValidTasks() {
		return taskStore.getTasks((task) -> {
			return task.isValid();
		});
	}

	public void saveTask(TaskMetaData taskMetaData) {
		taskStore.save(taskMetaData);
	}

	public TaskMetaData getTask(String id) {
		return taskStore.get(id);
	}
}
