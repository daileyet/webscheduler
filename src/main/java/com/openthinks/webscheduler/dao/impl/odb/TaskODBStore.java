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
* @Title: TaskODBStore.java 
* @Package com.openthinks.webscheduler.dao.impl 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 28, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.dao.impl.odb;

import java.util.Collection;
import java.util.function.Predicate;

import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;

import com.openthinks.webscheduler.dao.ITaskDao;
import com.openthinks.webscheduler.model.TaskRunTimeData;
import com.openthinks.webscheduler.model.task.TaskState;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class TaskODBStore implements ITaskDao {

	@Override
	public Collection<TaskRunTimeData> getTasks(Predicate<TaskRunTimeData> predicate) {
		IQuery query = new PredicateNativeQuery<>(predicate, TaskRunTimeData.class);
		ODBHelper.closeSiglton();
		Objects<TaskRunTimeData> taskRunTimeDatas = ODBHelper.getSigltonODB().getObjects(query);
		return taskRunTimeDatas;
	}

	@Override
	public void save(TaskRunTimeData taskRunTimeData) {
		ODB odb = ODBHelper.getSigltonODB();
		odb.store(taskRunTimeData);
		odb.commit();
	}

	@Override
	public TaskRunTimeData get(String taskId) {
		IQuery query = new PredicateNativeQuery<>(taskData -> {
			return taskData.getTaskId() != null && taskData.getTaskId().equals(taskId);
		}, TaskRunTimeData.class);

		Objects<TaskRunTimeData> taskRunTimeDatas = ODBHelper.getSigltonODB().getObjects(query);
		TaskRunTimeData taskRunTimeData = null;
		if (!taskRunTimeDatas.isEmpty())
			taskRunTimeData = taskRunTimeDatas.getFirst();
		return taskRunTimeData;
	}

	@Override
	public boolean delete(String taskId) {
		boolean isSuccess = false;
		TaskRunTimeData taskRunTimeData = get(taskId);
		ODB odb = ODBHelper.getSigltonODB();
		if (taskRunTimeData != null) {
			taskRunTimeData.setTaskState(TaskState.INVALID);
			odb.store(taskRunTimeData);
			//odb.delete(taskRunTimeData);
			odb.commit();
			isSuccess = true;
		}
		return isSuccess;
	}

}
