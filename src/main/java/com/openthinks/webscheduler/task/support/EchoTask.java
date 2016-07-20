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
* @Title: EchoTask.java 
* @Package com.openthinks.webscheduler.task.support 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 20, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.task.support;

import java.util.Enumeration;
import java.util.Optional;

import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.model.TaskMetaData;
import com.openthinks.webscheduler.task.TaskContext;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class EchoTask implements SupportTask {

	@Override
	public void execute(TaskContext context) {
		ProcessLogger.debug(getClass() + " start...");
		Optional<TaskMetaData> optional = getTaskMetaData(context);
		if (optional.isPresent()) {
			TaskMetaData taskMetaData = optional.get();
			if (taskMetaData.getTaskRefContent() == null || taskMetaData.getTaskRefContent().trim().length() == 0) {
				for(int i=0;i<=100;i++){
					echo("Hello World("+i+")");
					sleep();
				}
			} else {
				taskMetaData.preparedTaskRef();
				Enumeration<?> enumeration = taskMetaData.getTaskRef().propertyNames();
				while (enumeration.hasMoreElements()) {
					String propertyName = (String) enumeration.nextElement();
					if ("exception".equalsIgnoreCase(propertyName)) {
						Optional<String> optional2 = taskMetaData.getTaskRef().getProp(propertyName);
						if (optional2.isPresent() && "true".equalsIgnoreCase(optional2.get())) {
							throw new IllegalArgumentException("This is illegal parameter configure items.");
						}
					}
					echo(propertyName + "=" + taskMetaData.getTaskRef().getProp(propertyName));
					sleep();
				}
			}
		}
		ProcessLogger.debug(getClass() + " finished");
	}

	protected void echo(String string) {
		ProcessLogger.info(string);

	}

	protected void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

		}
	}

}
