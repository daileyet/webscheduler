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
package com.openthinks.webscheduler.dao.impl;

import java.util.Collection;
import java.util.function.Predicate;

import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.nq.SimpleNativeQuery;

import com.openthinks.webscheduler.dao.ITaskDao;
import com.openthinks.webscheduler.help.ODBHelper;
import com.openthinks.webscheduler.help.TaskSequenceHelper;
import com.openthinks.webscheduler.model.TaskRunTimeData;
import com.openthinks.webscheduler.model.task.TaskState;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class TaskODBStore implements ITaskDao {

	@Override
	public Collection<TaskRunTimeData> getTasks(Predicate<TaskRunTimeData> predicate) {
		IQuery query = new PredicateNativeQuery(predicate);
		Objects<TaskRunTimeData> taskRunTimeDatas = ODBHelper.getSigltonODB().getObjects(query);
		ODBHelper.closeSiglton();
		return taskRunTimeDatas;
	}

	@Override
	public void save(TaskRunTimeData taskRunTimeData) {
		ODB odb = ODBHelper.getODB();
		if (taskRunTimeData != null && taskRunTimeData.getTaskSeq() == null) {
			taskRunTimeData.setTaskSeq(TaskSequenceHelper.next());
		}
		odb.store(taskRunTimeData);
		odb.commit();
		ODBHelper.close(odb);
	}

	@Override
	public TaskRunTimeData get(String taskId) {
		IQuery query = new PredicateNativeQuery(taskData -> {
			return taskData.getTaskId() != null && taskData.getTaskId().equals(taskId);
		});
		Objects<TaskRunTimeData> taskRunTimeDatas = ODBHelper.getSigltonODB().getObjects(query);
		TaskRunTimeData taskRunTimeData = taskRunTimeDatas.getFirst();
		ODBHelper.closeSiglton();
		return taskRunTimeData;
	}

	@Override
	public boolean delete(String taskId) {
		TaskRunTimeData taskRunTimeData = get(taskId);
		ODB odb = ODBHelper.getODB();
		if (taskRunTimeData != null) {
			taskRunTimeData.setTaskState(TaskState.INVALID);
			odb.store(taskRunTimeData);
			odb.commit();
			return true;
		}
		ODBHelper.close(odb);
		return false;
	}

	class PredicateNativeQuery extends SimpleNativeQuery {
		private static final long serialVersionUID = 4093182978782308969L;
		private Predicate<TaskRunTimeData> predicate;

		public PredicateNativeQuery(Predicate<TaskRunTimeData> predicate) {
			super();
			this.predicate = predicate;
		}

		public boolean match(TaskRunTimeData taskRunTimeData) {
			if (this.predicate != null) {
				return this.predicate.test(taskRunTimeData);
			}
			return false;
		}
	}

}
