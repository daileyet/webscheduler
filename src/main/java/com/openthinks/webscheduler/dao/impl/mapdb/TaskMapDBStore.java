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
* @Title: TaskMapDBStore.java 
* @Package com.openthinks.webscheduler.dao.impl.mapdb 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 8, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.dao.impl.mapdb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import com.openthinks.libs.utilities.Checker;
import com.openthinks.webscheduler.dao.ITaskDao;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.model.TaskRunTimeData;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class TaskMapDBStore implements ITaskDao {
	private HTreeMap<String, TaskRunTimeData> taskMap_onDisk;

	public TaskMapDBStore() {
		taskMap_onDisk = MapDBHelper.getDiskDB()
				.hashMap(StaticDict.DISK_STORE_TASK, Serializer.STRING, MapDBHelper.taskSerializer).createOrOpen();
		//		taskMap_inMemory = MapDBHelper.getMemoryDB()
		//				.hashMap(StaticDict.MEMORY_STORE_TASK, Serializer.STRING, MapDBHelper.taskSerializer)
		//				.expireAfterGet(30, TimeUnit.MINUTES).expireOverflow(taskMap_onDisk)
		//				.expireExecutor(Executors.newScheduledThreadPool(2)).createOrOpen();
	}

	@Override
	public Collection<TaskRunTimeData> getTasks(Predicate<TaskRunTimeData> predicate) {
		Collection<TaskRunTimeData> collects = new ArrayList<>();
		taskMap_onDisk.forEach((key, val) -> {
			if (predicate.test(val)) {
				collects.add(val);
			}
		});
		return collects;
	}

	@Override
	public void save(TaskRunTimeData taskRunTimeData) {
		Checker.require(taskRunTimeData).notNull();
		String taskId = taskRunTimeData.getTaskId();
		if (taskMap_onDisk.containsKey(taskId)) {
			TaskRunTimeData old = taskMap_onDisk.get(taskId);
			old.update(taskRunTimeData);
		}
		taskMap_onDisk.put(taskId, taskRunTimeData);
		MapDBHelper.getDiskDB().commit();
	}

	@Override
	public TaskRunTimeData get(String taskId) {
		return taskMap_onDisk.get(taskId);
	}

	@Override
	public boolean delete(String taskId) {
		boolean result = taskMap_onDisk.remove(taskId) != null;
		MapDBHelper.getDiskDB().commit();
		return result;
	}

}
