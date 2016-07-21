/**   
 *  Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
* @Title: TaskStore.java 
* @Package com.openthinks.webscheduler.dao.impl 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 12, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

import com.openthinks.webscheduler.dao.ITaskDao;
import com.openthinks.webscheduler.model.TaskMetaData;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class TaskStore implements ITaskDao {
	private static final List<TaskMetaData> taskDB = Collections.synchronizedList(new ArrayList<TaskMetaData>());
	private static final Map<String, TaskMetaData> taskMap = new ConcurrentHashMap<>();
	private Lock lock = new ReentrantLock();

	/* (non-Javadoc)
	 * @see com.openthinks.webscheduler.dao.ITaskDao#getTasks(java.util.function.Predicate)
	 */
	@Override
	public List<TaskMetaData> getTasks(Predicate<TaskMetaData> predicate) {
		final List<TaskMetaData> taskMetaDatas = new ArrayList<>();
		taskDB.forEach((task) -> {
			if (predicate.test(task)) {
				taskMetaDatas.add(task);
			}
		});
		return taskMetaDatas;
	}

	/* (non-Javadoc)
	 * @see com.openthinks.webscheduler.dao.ITaskDao#save(com.openthinks.webscheduler.model.TaskMetaData)
	 */
	@Override
	public void save(TaskMetaData taskMetaDataNew) {
		boolean isExist = taskMap.containsKey(taskMetaDataNew.getTaskId());
		if (isExist) {
			lock.lock();
			try {
				TaskMetaData taskMetaDataOld = taskMap.get(taskMetaDataNew.getTaskId());
				if (taskMetaDataOld != null)
					taskMetaDataOld.update(taskMetaDataNew);
			} finally {
				lock.unlock();
			}
		} else {
			lock.lock();
			try {
				taskDB.add(taskMetaDataNew);
				taskMap.put(taskMetaDataNew.getTaskId(), taskMetaDataNew);
				taskMetaDataNew.setTaskSeq(taskDB.size());
			} finally {
				lock.unlock();
			}
		}

	}

	@Override
	public TaskMetaData get(String id) {
		if (id != null)
			return taskMap.get(id);
		return null;
	}

	@Override
	public boolean delete(String taskId) {
		try {
			TaskMetaData taskMetaData = taskMap.get(taskId);
			taskDB.remove(taskMetaData);
			taskMap.remove(taskId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
