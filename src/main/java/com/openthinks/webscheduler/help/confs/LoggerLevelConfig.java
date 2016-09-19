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
* @Title: LoggerLevelConfig.java 
* @Package com.openthinks.webscheduler.help.confs 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Sep 5, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.help.confs;

import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.libs.utilities.logger.ProcessLogger.PLLevel;

/**
 * @author dailey.yet@outlook.com
 *
 */
public final class LoggerLevelConfig implements ConfigObject {
	private String level;

	LoggerLevelConfig(String loglevel) {
		this.level = loglevel;
	}

	@Override
	public void config() {
		if (level != null) {
			PLLevel pLevel = ProcessLogger.PLLevel.build(level);
			if (pLevel != null)
				ProcessLogger.currentLevel = pLevel;
		}
	}

}
