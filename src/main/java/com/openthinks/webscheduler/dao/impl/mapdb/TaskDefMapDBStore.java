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
* @Title: TaskDefMapDBStore.java 
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
import com.openthinks.webscheduler.dao.ITaskDefDao;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.model.task.def.TaskDefRuntimeData;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class TaskDefMapDBStore implements ITaskDefDao {
	private HTreeMap<String, TaskDefRuntimeData> taskDefMap_onDisk;

	public TaskDefMapDBStore() {
		taskDefMap_onDisk = MapDBHelper.getDiskDB()
				.hashMap(StaticDict.DISK_STORE_TASKDEF, Serializer.STRING, MapDBHelper.taskDefSerializer)
				.createOrOpen();
		//		taskDefMap_inMemory = MapDBHelper.getMemoryDB()
		//				.hashMap(StaticDict.MEMORY_STORE_TASKDEF, Serializer.STRING, MapDBHelper.taskDefSerializer)
		//				.expireAfterGet(1, TimeUnit.SECONDS).expireOverflow(taskDefMap_onDisk)
		//				.expireExecutor(Executors.newScheduledThreadPool(2)).createOrOpen();
	}

	@Override
	public Collection<TaskDefRuntimeData> getTaskDefs(Predicate<TaskDefRuntimeData> predicate) {
		Collection<TaskDefRuntimeData> collects = new ArrayList<>();
		taskDefMap_onDisk.forEach((key, val) -> {
			if (predicate.test(val)) {
				collects.add(val);
			}
		});
		return collects;
	}

	@Override
	public void save(TaskDefRuntimeData taskDefRunTimeData) {
		Checker.require(taskDefRunTimeData).notNull();
		String fullName = taskDefRunTimeData.getFullName();
		if (taskDefMap_onDisk.containsKey(fullName)) {
			TaskDefRuntimeData old = taskDefMap_onDisk.get(fullName);
			old.update(taskDefRunTimeData);
		}
		taskDefMap_onDisk.put(fullName, taskDefRunTimeData);
		MapDBHelper.getDiskDB().commit();
	}

	@Override
	public TaskDefRuntimeData get(String classFullName) {
		return taskDefMap_onDisk.get(classFullName);
	}

	@Override
	public boolean delete(String classFullName) {
		boolean result = taskDefMap_onDisk.remove(classFullName) != null;
		MapDBHelper.getDiskDB().commit();
		return result;
	}

}
