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
* @Title: TaskDefinitionMetaData.java 
* @Package com.openthinks.webscheduler.model.task 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 25, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.task;

/**
 * Description for Task definition, combine {@link TaskDefinitionDescriber} and {@link TaskRefDefinitionDescriber}
 * @author dailey.yet@outlook.com
 *
 */
public final class TaskDefinitionMetaData {
	private TaskDefinitionDescriber describer;
	private TaskRefDefinitionDescriber refDescriber;
	private Class<? extends ITaskDefinition> type;

	public TaskDefinitionMetaData(ITaskDefinition taskInstance) {
		this(taskInstance.getTaskDescriber(), taskInstance.getTaskRefDescriber());
	}

	public TaskDefinitionMetaData(TaskDefinitionDescriber describer, TaskRefDefinitionDescriber refDescriber) {
		super();
		this.describer = describer;
		this.refDescriber = refDescriber;
		if (describer != null)
			this.type = describer.getTaskClass();
	}

	public TaskDefinitionDescriber getDescriber() {
		return describer;
	}

	public void setDescriber(TaskDefinitionDescriber describer) {
		this.describer = describer;
	}

	public TaskRefDefinitionDescriber getRefDescriber() {
		return refDescriber;
	}

	public void setRefDescriber(TaskRefDefinitionDescriber refDescriber) {
		this.refDescriber = refDescriber;
	}

	@SuppressWarnings("unchecked")
	public <T extends ITaskDefinition> Class<T> getType() {
		return (Class<T>) type;
	}

	public <T extends ITaskDefinition> void setType(Class<T> type) {
		this.type = type;
	}

}
