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
* @Title: RootConfig.java 
* @Package com.openthinks.webscheduler.help.confs 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 23, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.help.confs;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.exception.FailedConfigPath;
import com.openthinks.webscheduler.exception.UnSupportConfigPath;
import com.openthinks.webscheduler.help.StaticDict;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class RootConfig implements ConfigObject {
	private final Properties properties;
	private final ConfigObjects children;

	public RootConfig() {
		this.properties = new Properties();
		this.children = new ConfigObjects();
	}

	@Override
	public void config() {
		ProcessLogger.debug(getClass() + " start config...");
		String webConfigurePath = WebContexts.getServletContext()
				.getInitParameter(StaticDict.SERVLET_INIT_PARAM_WEBCONFIGUREPATH);
		if (webConfigurePath.startsWith(StaticDict.CLASS_PATH_PREFIX)) {//classpath:/ws-conf.properties
			String classpath = webConfigurePath.substring(StaticDict.CLASS_PATH_PREFIX.length());
			InputStream in = RootConfig.class.getResourceAsStream(classpath);
			try {
				this.properties.load(in);
			} catch (IOException e) {
				throw new FailedConfigPath(webConfigurePath, e);
			}
		} else if (webConfigurePath.startsWith(StaticDict.FILE_PREFIX)) {//file:R:/MyGit/webscheduler/target/classes/ws-conf.properties
			String filePath = webConfigurePath.substring(StaticDict.FILE_PREFIX.length());
			File file = new File(filePath), absoulteFile = file, relativeFile = null;
			if (!absoulteFile.exists()) {
				relativeFile = new File(WebUtils.getWebClassDir(), filePath);
				file = relativeFile;
			}
			if (file == null || !file.exists()) {
				throw new FailedConfigPath(webConfigurePath);
			}
			try {
				this.properties.load(new FileReader(file));
			} catch (Exception e) {
				throw new FailedConfigPath(webConfigurePath, e);
			}
		} else {
			throw new UnSupportConfigPath(webConfigurePath);
		}
		this.initChildren();
		this.configChildren();
	}

	private void configChildren() {
		this.children().forEach((configObj) -> {
			configObj.config();
		});

	}

	private void initChildren() {
		this.children.clear();
		String namespace = this.properties.getProperty(StaticDict.CONF_NAMESPACE);
		String loglevel = getProperty4Namespace(StaticDict.CONF_LOGGER_LEVEL, namespace);
		LoggerLevelConfig loggerConfig = new LoggerLevelConfig(loglevel);
		children.add(loggerConfig);
		String easywebClassDir = getProperty4Namespace(StaticDict.CONF_EASYWEBCLASSDIR, namespace);
		EasyWebClassDirConfig webClassConfig = new EasyWebClassDirConfig(easywebClassDir, this);
		children.add(webClassConfig);
		//mapDB configure
		String mapdbFile = getProperty4Namespace(StaticDict.CONF_MAPDB_FILE, namespace);
		MapDBConfig mapdbConfig = new MapDBConfig(mapdbFile, this);
		children.add(mapdbConfig);
		//security configure
		String securityFile = getProperty4Namespace(StaticDict.CONF_SECURITY_FILE, namespace);
		SecurityConfig securityConfig = new SecurityConfig(securityFile, this);
		children.add(securityConfig);
		//task reference protected configure
		String unchangeRefPath = getProperty4Namespace(StaticDict.CONF_REFS_UNCHANGE_PATH, namespace);
		TaskRefProtectedConfig protectedConfig = new TaskRefProtectedConfig(unchangeRefPath, this);
		children.add(protectedConfig);

	}

	protected String getProperty4Namespace(final String propertyName, final String namespace) {
		String propertyValue = null;
		if (namespace != null && namespace.trim().length() > 0) {
			propertyValue = this.properties.getProperty(namespace + "." + propertyName);
		}
		if (propertyValue == null) {
			propertyValue = this.properties.getProperty(propertyName);
		}
		return propertyValue;
	}

	@Override
	public ConfigObjects children() {
		return children;
	}

	public String getConfigContent() {
		if (this.properties == null)
			return "";
		Set<String> properties = this.properties.stringPropertyNames();
		StringBuffer sb = new StringBuffer();
		properties.forEach((propertyName) -> {
			String propertyValue = this.properties.getProperty(propertyName);
			sb.append(propertyName + "=" + propertyValue);
			sb.append("\r\n");
		});
		return sb.toString();
	}

}
