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
* @Title: MapDBConfig.java 
* @Package com.openthinks.webscheduler.help.confs 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 24, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.help.confs;

import java.io.File;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.dao.impl.mapdb.MapDBHelper;
import com.openthinks.webscheduler.exception.FailedConfigPath;
import com.openthinks.webscheduler.exception.UnSupportConfigPath;
import com.openthinks.webscheduler.help.StaticDict;

/**
 * @author dailey.yet@outlook.com
 *
 */
public final class MapDBConfig extends AbstractConfigObject {

	MapDBConfig(String configPath, ConfigObject parent) {
		super(configPath, parent);
	}

	MapDBConfig(String configPath) {
		super(configPath);
	}

	/* (non-Javadoc)
	 * @see com.openthinks.webscheduler.help.confs.ConfigObject#config()
	 */
	@Override
	public void config() {
		ProcessLogger.debug(getClass() + " start config...");
		File dbFile = null;
		if (configPath.startsWith(StaticDict.CLASS_PATH_PREFIX)) {//classpath:/conf/security.xml
			String classpath = configPath.substring(StaticDict.CLASS_PATH_PREFIX.length());
			dbFile = new File(WebUtils.getWebClassDir(), classpath);
		} else if (configPath.startsWith(StaticDict.FILE_PREFIX)) {//file:R:/MyGit/webscheduler/target/classes/conf/security.xml
			String filePath = configPath.substring(StaticDict.FILE_PREFIX.length());
			File file = new File(filePath), absoulteFile = file, relativeFile = null;
			if (!absoulteFile.getParentFile().exists()) {
				relativeFile = new File(WebUtils.getWebClassDir(), filePath);
				file = relativeFile;
			}
			dbFile = file;
		} else {
			throw new UnSupportConfigPath(configPath);
		}
		if (dbFile == null || !dbFile.isFile()) {
			ProcessLogger.debug("DbFile=" + dbFile);
			throw new FailedConfigPath(configPath);
		}
		MapDBHelper.setUp(dbFile);
	}

}
