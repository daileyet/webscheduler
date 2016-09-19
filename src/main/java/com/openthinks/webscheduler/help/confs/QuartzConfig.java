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
* @Title: QuartzConfig.java 
* @Package com.openthinks.webscheduler.help.confs 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Sep 19, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.help.confs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.quartz.SchedulerException;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.exception.FailedConfigPath;
import com.openthinks.webscheduler.exception.UnSupportConfigPath;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.help.StaticUtils;
import com.openthinks.webscheduler.service.SchedulerService;

/**
 * @author dailey.yet@outlook.com
 *
 */
public final class QuartzConfig extends AbstractConfigObject {
	private final Properties quartzProps;
	private File quartzFile = null;

	QuartzConfig(String configPath) {
		super(configPath);
		this.quartzProps = new Properties();
	}

	QuartzConfig(String configPath, ConfigObject parent) {
		super(configPath, parent);
		this.quartzProps = new Properties();
	}

	public File getQuartzFile() {
		return quartzFile;
	}

	public Properties getQuartzProps() {
		return quartzProps;
	}

	@Override
	public void config() {
		ProcessLogger.debug(getClass() + " start config...");
		if (configPath.startsWith(StaticDict.CLASS_PATH_PREFIX)) {//classpath:/conf/quartz.properties
			String classpath = configPath.substring(StaticDict.CLASS_PATH_PREFIX.length());
			quartzFile = new File(WebUtils.getWebClassDir(), classpath);
		} else if (configPath.startsWith(StaticDict.FILE_PREFIX)) {//file:R:/MyGit/webscheduler/target/classes/conf/quartz.properties
			String filePath = configPath.substring(StaticDict.FILE_PREFIX.length());
			File file = new File(filePath), absoulteFile = file, relativeFile = null;
			if (!absoulteFile.getParentFile().exists()) {
				relativeFile = new File(WebUtils.getWebClassDir(), filePath);
				file = relativeFile;
			}
			quartzFile = file;
		} else {
			throw new UnSupportConfigPath(configPath);
		}
		if (quartzFile == null || !quartzFile.isFile()) {
			throw new FailedConfigPath(configPath);
		}
		try {
			this.quartzProps.load(new FileReader(quartzFile));
		} catch (FileNotFoundException e) {
			throw new FailedConfigPath(e);
		} catch (IOException e) {
			throw new FailedConfigPath(e);
		}

		try {
			WebContexts.get().lookup(SchedulerService.class).init(this);
		} catch (SchedulerException e) {
			throw new FailedConfigPath(e);
		}
	}

	public String getConfigContent() {
		return StaticUtils.getPropertiesContent(this.quartzProps);
	}

}
