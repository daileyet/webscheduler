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
* @Title: TaskMemoryStore.java 
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
import com.openthinks.webscheduler.model.TaskRunTimeData;
import com.openthinks.webscheduler.model.task.TaskState;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class TaskMemoryStore implements ITaskDao {
	private static final List<TaskRunTimeData> taskDB = Collections.synchronizedList(new ArrayList<TaskRunTimeData>());
	private static final Map<String, TaskRunTimeData> taskMap = new ConcurrentHashMap<>();
	private Lock lock = new ReentrantLock();

	/* (non-Javadoc)
	 * @see com.openthinks.webscheduler.dao.ITaskDao#getTasks(java.util.function.Predicate)
	 */
	@Override
	public List<TaskRunTimeData> getTasks(Predicate<TaskRunTimeData> predicate) {
		final List<TaskRunTimeData> taskRunTimeDatas = new ArrayList<>();
		taskDB.forEach((task) -> {
			if (predicate.test(task)) {
				taskRunTimeDatas.add(task);
			}
		});
		return taskRunTimeDatas;
	}

	/* (non-Javadoc)
	 * @see com.openthinks.webscheduler.dao.ITaskDao#save(com.openthinks.webscheduler.model.TaskRunTimeData)
	 */
	@Override
	public void save(TaskRunTimeData taskMetaDataNew) {
		boolean isExist = taskMap.containsKey(taskMetaDataNew.getTaskId());
		if (isExist) {
			lock.lock();
			try {
				TaskRunTimeData taskMetaDataOld = taskMap.get(taskMetaDataNew.getTaskId());
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
	public TaskRunTimeData get(String id) {
		if (id != null)
			return taskMap.get(id);
		return null;
	}

	@Override
	public boolean delete(String taskId) {
		try {
			TaskRunTimeData taskRunTimeData = taskMap.get(taskId);
			taskRunTimeData.setTaskState(TaskState.INVALID);
			//			taskDB.remove(taskRunTimeData);
			//			taskMap.remove(taskId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
