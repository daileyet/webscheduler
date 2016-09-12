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
* @Title: DefaultTaskRef.java 
* @Package com.openthinks.webscheduler.model 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 13, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.model.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.task.TaskRefDefinitionDescriber;

/**
 * Default implementation by extends {@link Properties}
 * @author dailey.yet@outlook.com
 *
 */
public class DefaultTaskRef extends Properties implements ITaskRef {

	private static final long serialVersionUID = 128536257786984503L;

	public static final DefaultTaskRef readXML(InputStream is) throws InvalidPropertiesFormatException, IOException {
		DefaultTaskRef instance = new DefaultTaskRef();
		instance.loadFromXML(is);
		return instance;
	}

	public static final DefaultTaskRef readProps(InputStream is) throws IOException {
		DefaultTaskRef instance = new DefaultTaskRef();
		instance.load(is);
		return instance;
	}

	public static final TaskRefDefinitionDescriber getTaskRefDescriber() {
		return new TaskRefDefinitionDescriber(DefaultTaskRef.class) {
			@SuppressWarnings("unchecked")
			@Override
			public <T extends ITaskRef> T createTaskRef() {
				return (T) new DefaultTaskRef();
			}
		};
	}

	public static final TaskRefDefinitionDescriber getTaskRefDescriber(String description) {
		return new TaskRefDefinitionDescriber(DefaultTaskRef.class, description) {
			@SuppressWarnings("unchecked")
			@Override
			public <T extends ITaskRef> T createTaskRef() {
				return (T) new DefaultTaskRef();
			}
		};
	}

	public String getContent() {
		StringWriter stringWriter = new StringWriter();
		try {
			this.store(stringWriter, "");
		} catch (IOException e) {
			ProcessLogger.warn(e);
		} finally {
			try {
				stringWriter.close();
			} catch (IOException e) {
				ProcessLogger.warn(e);
			}
		}
		return stringWriter.toString();
	}
}
