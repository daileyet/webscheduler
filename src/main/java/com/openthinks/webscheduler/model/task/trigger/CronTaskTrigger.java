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
* @Title: CronTaskTrigger.java 
* @Package com.openthinks.webscheduler.model.task.trigger 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 9, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.model.task.trigger;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.Trigger;
import org.quartz.TriggerKey;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class CronTaskTrigger extends AbstractTaskTrigger {

	private static final long serialVersionUID = -2226334860757677179L;
	private String cronExpression;

	public CronTaskTrigger(TriggerKey triggerKey) {
		super(triggerKey);
	}

	public CronTaskTrigger(String name, String group) {
		super(name, group);
	}

	@Override
	public Trigger getTrigger() {
		return newTrigger().withIdentity(triggerKey).withSchedule(cronSchedule(getCronExpression())).build();
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	@Override
	public String toString() {
		return "CronTaskTrigger [cronExpression=" + cronExpression + ", toString()=" + super.toString() + "]";
	}

}
