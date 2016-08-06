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
* @Title: TaskDefODBStore.java 
* @Package com.openthinks.webscheduler.dao.impl 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 4, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.dao.impl.odb;

import java.util.Collection;
import java.util.function.Predicate;

import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;

import com.openthinks.webscheduler.dao.ITaskDefDao;
import com.openthinks.webscheduler.model.task.def.TaskDefRuntimeData;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class TaskDefODBStore implements ITaskDefDao {

	@Override
	public Collection<TaskDefRuntimeData> getTaskDefs(Predicate<TaskDefRuntimeData> predicate) {
		IQuery query = new PredicateNativeQuery<>(predicate, TaskDefRuntimeData.class);
		ODBHelper.closeSiglton();
		Objects<TaskDefRuntimeData> taskDefRunTimeDatas = ODBHelper.getSigltonODB().getObjects(query);
		return taskDefRunTimeDatas;
	}

	@Override
	public void save(TaskDefRuntimeData taskDefRunTimeData) {
		ODB odb = ODBHelper.getSigltonODB();
		odb.store(taskDefRunTimeData);
		odb.commit();
	}

	@Override
	public TaskDefRuntimeData get(String classFullName) {
		IQuery query = new PredicateNativeQuery<>(taskDefData -> {
			return taskDefData.getFullName() != null && taskDefData.getFullName().equals(classFullName);
		}, TaskDefRuntimeData.class);

		Objects<TaskDefRuntimeData> taskDefRunTimeDatas = ODBHelper.getSigltonODB().getObjects(query);
		TaskDefRuntimeData taskDefRunTimeData = null;
		if (taskDefRunTimeDatas.isEmpty()) {
			taskDefRunTimeDatas = ODBHelper.getSigltonODB().getObjects(query);
		}
		if (!taskDefRunTimeDatas.isEmpty())
			taskDefRunTimeData = taskDefRunTimeDatas.getFirst();
		return taskDefRunTimeData;
	}

	@Override
	public boolean delete(String classFullName) {
		boolean isSuccess = false;
		TaskDefRuntimeData taskDefRunTimeData = get(classFullName);
		ODB odb = ODBHelper.getSigltonODB();
		if (taskDefRunTimeData != null) {
			odb.delete(taskDefRunTimeData);
			odb.close();
			isSuccess = true;
		}
		return isSuccess;
	}

}
