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
* @Title: TaskDefinitionDescriber.java 
* @Package com.openthinks.webscheduler.model.task 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 25, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.task;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class TaskDefinitionDescriber {
	private Class<? extends ITaskDefinition> taskClass;
	private StringBuilder description;

	public static final <E extends ITaskDefinition> TaskDefinitionDescriber build(Class<E> taskClass) {
		return new TaskDefinitionDescriber(taskClass);
	}

	public <T extends ITaskDefinition> TaskDefinitionDescriber(Class<T> taskClass) {
		super();
		this.taskClass = taskClass;
		this.description = new StringBuilder();
	}

	public <T extends ITaskDefinition> TaskDefinitionDescriber(Class<T> taskClass, String description) {
		super();
		this.taskClass = taskClass;
		this.description = new StringBuilder(description);
	}

	@SuppressWarnings("unchecked")
	public <T extends ITaskDefinition> Class<T> getTaskClass() {
		return (Class<T>) this.taskClass;
	}

	public String getDescription() {
		return description.toString();
	}

	public void setDescription(String description) {
		this.description = new StringBuilder(description);
	}

	public <T extends ITaskDefinition> void setTaskClass(Class<T> taskClass) {
		this.taskClass = taskClass;
	}

	public TaskDefinitionDescriber push(String itemDesc) {
		description.append(itemDesc);
		return this;
	}
}
