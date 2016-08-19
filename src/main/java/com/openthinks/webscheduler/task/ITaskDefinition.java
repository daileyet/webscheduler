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
* @Title: ITaskDefinition.java 
* @Package com.openthinks.webscheduler.task 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 8, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.task;

import java.util.Optional;

import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

import com.openthinks.webscheduler.model.TaskRunTimeData;
import com.openthinks.webscheduler.model.task.DefaultTaskRef;

/**
 * represent task definition, which extends interface {@link Job} and {@link InterruptableJob}
 * @author dailey.yet@outlook.com
 *
 */
public interface ITaskDefinition extends InterruptableJob {
	String TASK_REF = "task-ref";
	String TASK_DATA = "task-data";

	/**
	 * This is extends from parent interface, this method has been implemented, so this method should not be override again.<BR>
	 * It will call {@link #execute(TaskContext)} to do real task
	 */
	@Override
	@Deprecated
	public default void execute(JobExecutionContext context) throws JobExecutionException {
		TaskContext taskContext = new TaskContext(context);
		try {
			execute(taskContext);
		} catch (Exception e) {
			throw new JobExecutionException(e);
		}
	}

	/**
	 * the primary place to do task
	 * @param context TaskContext
	 */
	public void execute(TaskContext context);

	/**
	 * get this task definition describer
	 * @return TaskDefinitionDescriber
	 */
	public default TaskDefinitionDescriber getTaskDescriber() {
		TaskDefinitionDescriber taskDefinitionDescriber = TaskDefinitionDescriber.build(this.getClass());
		return taskDefinitionDescriber;
	}

	/**
	 * get this task definition reference describer 
	 * @return TaskRefDefinitionDescriber
	 */
	public default TaskRefDefinitionDescriber getTaskRefDescriber() {
		return DefaultTaskRef.getTaskRefDescriber();
	}

	/**
	 * get {@link TaskRunTimeData} instance from {@link TaskContext}
	 * @param context TaskContext
	 * @return Optional<TaskRunTimeData>
	 */
	public default Optional<TaskRunTimeData> getTaskRunTimeData(TaskContext context) {
		TaskRunTimeData taskRunTimeData = context.get(TASK_DATA);
		return Optional.ofNullable(taskRunTimeData);
	}

	/**
	 * the empty implement for default
	 */
	@Override
	default void interrupt() throws UnableToInterruptJobException {
	}

}
