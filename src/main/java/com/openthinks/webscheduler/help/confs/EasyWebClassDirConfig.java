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
* @Title: EasyWebClassDirConfig.java 
* @Package com.openthinks.webscheduler.help.confs 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Sep 5, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.help.confs;

import java.io.File;

import com.openthinks.easyweb.WebStatic;
import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.exception.FailedConfigPath;
import com.openthinks.webscheduler.exception.UnSupportConfigPath;
import com.openthinks.webscheduler.help.StaticDict;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class EasyWebClassDirConfig extends AbstractConfigObject {

	public EasyWebClassDirConfig(String configPath, ConfigObject parent) {
		super(configPath, parent);
	}

	public EasyWebClassDirConfig(String configPath) {
		super(configPath);
	}

	@Override
	public void config() {
		ProcessLogger.debug(getClass() + " start config...");
		if (configPath == null || configPath.trim().length() == 0) {
			ProcessLogger.debug(getClass() + " will use default setting:[WEB-INF/classes]");
			return;
		}
		String classDir = null;
		if (configPath.startsWith(StaticDict.FILE_PREFIX)) {//file:R:/MyGit/webscheduler/target/classes/conf/security.xml
			String dirPath = configPath.substring(StaticDict.FILE_PREFIX.length());
			File file = new File(dirPath), absoulteFile = file, relativeFile = null;
			if (!absoulteFile.exists() || !absoulteFile.isDirectory()) {
				relativeFile = new File(WebContexts.getServletContext().getRealPath(""), dirPath);
				file = relativeFile;
			}
			if (file.exists() && file.isDirectory()) {
				classDir = absoulteFile.getAbsolutePath();
				WebContexts.getServletContext().setAttribute(WebStatic.WEB_CLASS_DIR, classDir);
			}
		} else {
			throw new UnSupportConfigPath(configPath);
		}
		if (classDir == null) {
			throw new FailedConfigPath(configPath);
		}
	}

}
