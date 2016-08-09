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
* @Title: AbstractTaskTrigger.java 
* @Package com.openthinks.webscheduler.model.task.trigger 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 9, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.model.task.trigger;

import org.quartz.TriggerKey;

import com.openthinks.webscheduler.model.task.ITaskTrigger;

/**
 * @author dailey.yet@outlook.com
 *
 */
public abstract class AbstractTaskTrigger implements ITaskTrigger {
	private static final long serialVersionUID = -1941036319617531194L;
	protected TriggerKey triggerKey;

	public AbstractTaskTrigger(TriggerKey triggerKey) {
		super();
		this.triggerKey = triggerKey;
	}

	public AbstractTaskTrigger(String name, String group) {
		this.triggerKey = TriggerKey.triggerKey(name, group);
	}

	public TriggerKey getTriggerKey() {
		return triggerKey;
	}

	public void setTriggerKey(TriggerKey triggerKey) {
		this.triggerKey = triggerKey;
	}

	public void setTriggerKey(String name, String group) {
		this.triggerKey = TriggerKey.triggerKey(name, group);
	}

	@Override
	public String toString() {
		return "AbstractTaskTrigger [triggerKey=" + triggerKey + "]";
	}

}
