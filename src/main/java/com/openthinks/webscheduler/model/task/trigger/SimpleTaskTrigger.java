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
* @Title: SimpleTaskTrigger.java 
* @Package com.openthinks.webscheduler.model.task.trigger 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 9, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.model.task.trigger;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.openthinks.libs.utilities.Checker;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.help.StaticUtils;
import com.openthinks.webscheduler.model.task.SupportedTrigger;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class SimpleTaskTrigger extends AbstractTaskTrigger {
	private static final long serialVersionUID = 1325006353416694668L;
	private Date startTime, endTime;
	private int repeatCount = StaticDict.NO_REPEAT_TRIGGER;
	private int intervalInSeconds;

	public SimpleTaskTrigger() {
	}

	public SimpleTaskTrigger(TriggerKey triggerKey) {
		super(triggerKey);
	}

	public SimpleTaskTrigger(String name, String group) {
		super(name, group);
	}

	@Override
	public Trigger getTrigger() {
		Checker.require(triggerKey).notNull();
		TriggerBuilder<Trigger> triggerBuilder = newTrigger().withIdentity(triggerKey);
		if (getStartTime() == null) {
			triggerBuilder.startNow();
		} else {
			triggerBuilder.startAt(getStartTime());
		}
		if (getEndTime() != null) {
			triggerBuilder.endAt(getEndTime());
		}
		if (isRepeatable()) {
			SimpleScheduleBuilder scheduleBuilder = simpleSchedule();
			scheduleBuilder.withIntervalInSeconds(getIntervalInSeconds());
			if (isRepeatForever()) {
				scheduleBuilder.repeatForever();
			} else {
				scheduleBuilder.withRepeatCount(getRepeatCount());
			}
			triggerBuilder.withSchedule(scheduleBuilder);
		}
		return triggerBuilder.build();
	}

	public Date getStartTime() {
		return startTime;
	}

	public String getStartTimeString() {
		try {
			return StaticUtils.formatDate(getStartTime());
		} catch (Exception e) {
			return "";
		}
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getEndTimeString() {
		try {
			return StaticUtils.formatDate(getEndTime());
		} catch (Exception e) {
			return "";
		}
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public int getIntervalInSeconds() {
		return intervalInSeconds;
	}

	public void setIntervalInSeconds(int intervalInSeconds) {
		this.intervalInSeconds = intervalInSeconds;
	}

	public boolean isRepeatable() {
		return this.repeatCount > StaticDict.NO_REPEAT_TRIGGER;
	}

	public boolean isRepeatForever() {
		return this.repeatCount == StaticDict.FOREVER_REPEAT_TRIGGER;
	}

	@Override
	public String toString() {
		return "SimpleTaskTrigger [startTime=" + startTime + ", endTime=" + endTime + ", repeatCount=" + repeatCount
				+ ", intervalInSeconds=" + intervalInSeconds + ", toString()=" + super.toString() + "]";
	}

	@Override
	public SupportedTrigger getTriggerType() {
		if (this.startTime != null)
			return SupportedTrigger.START_FIX_DATE;
		return SupportedTrigger.START_NOW;
	}

}
