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
* @Title: TaskExecuteResult.java 
* @Package com.openthinks.webscheduler.model.task 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 20, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.model.task;

/**
 * Result for task execution
 * @author dailey.yet@outlook.com
 *
 */
public final class TaskResult extends ExecutionResult {
	private static final long serialVersionUID = -9178343032918042351L;
	private String taskId;

	public TaskResult() {
		this.logContent = "";
	}

	public TaskResult(String taskId) {
		this.taskId = taskId;
		this.logContent = "";
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public void update(ExecutionResult newValue) {
		super.update(newValue);
		if (newValue instanceof TaskResult) {
			String newtaskId = ((TaskResult) newValue).getTaskId();
			if (newtaskId != null) {
				this.setTaskId(newtaskId);
			}
		}
	}

}
