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
* @Title: TriggerGenerator.java 
* @Package com.openthinks.webscheduler.help 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 10, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.help.trigger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.model.task.ITaskTrigger;
import com.openthinks.webscheduler.model.task.SupportedTrigger;
import com.openthinks.webscheduler.model.task.trigger.SimpleTaskTrigger;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class TriggerGenerator implements ITriggerGenerator {

	private static final Map<SupportedTrigger, ITriggerGenerator> generatorMap = new ConcurrentHashMap<SupportedTrigger, ITriggerGenerator>() {
		private static final long serialVersionUID = -7216131610964422502L;
		{
			this.put(SupportedTrigger.START_NOW, new StartNowTriggerGenerator());
			this.put(SupportedTrigger.START_FIX_DATE, new StartFixDateTriggerGenerator());
			this.put(SupportedTrigger.CRON, new CronTriggerGenerator());
		}
	};

	public static TriggerGenerator valueOf(String supportedTriggerName) {
		SupportedTrigger supportedTrigger = SupportedTrigger.START_NOW;
		try {
			supportedTrigger = SupportedTrigger.valueOf(supportedTriggerName);
		} catch (Exception e) {
			ProcessLogger.warn(e);
		}
		return new TriggerGenerator(supportedTrigger);
	}

	private SupportedTrigger triggerType;

	private TriggerGenerator(SupportedTrigger triggerType) {
		this.triggerType = triggerType;
	}

	@Override
	public ITaskTrigger generate(WebAttributers was) {
		try {
			ITriggerGenerator triggerGenerator = generatorMap.get(getTriggerType());
			return triggerGenerator.generate(was);
		} catch (Exception e) {
			ProcessLogger.warn("Generate task trigger error; will use default task trigger, which type is "
					+ SupportedTrigger.START_NOW + "", e);
			return new SimpleTaskTrigger();
		}

	}

	public SupportedTrigger getTriggerType() {
		return triggerType;
	}

}
