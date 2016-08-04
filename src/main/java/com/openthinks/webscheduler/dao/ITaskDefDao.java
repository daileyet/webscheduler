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
* @Title: ITaskDefDao.java 
* @Package com.openthinks.webscheduler.dao 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 4, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.dao;

import java.util.Collection;
import java.util.function.Predicate;

import com.openthinks.webscheduler.model.task.def.TaskDefRuntimeData;

/**
 * @author dailey.yet@outlook.com
 *
 */
public interface ITaskDefDao {
	/**
	 * fetch task list by predicate
	 * @param predicate Predicate<TaskRunTimeData>
	 * @return List<TaskRunTimeData>
	 */
	public Collection<TaskDefRuntimeData> getTaskDefs(Predicate<TaskDefRuntimeData> predicate);

	public void save(TaskDefRuntimeData taskDefRunTimeData);

	public TaskDefRuntimeData get(String classFullName);

	public boolean delete(String classFullName);
}
