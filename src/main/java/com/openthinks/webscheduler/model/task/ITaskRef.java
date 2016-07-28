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
* @Title: ITaskRef.java 
* @Package com.openthinks.webscheduler.model 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 13, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.model.task;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Optional;
import java.util.Properties;

import com.openthinks.webscheduler.help.StaticChecker;

/**
 * @author dailey.yet@outlook.com
 *
 */
public interface ITaskRef extends Serializable {

	/**
	 * {@link Properties#getProperty(String)}
	 * @param key
	 * @return
	 */
	public String getProperty(String key);

	/**
	 * {@link Properties#getProperty(String, String)}
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getProperty(String key, String defaultValue);

	/**
	 * {@link Properties#setProperty(String, String)}
	 * @param key
	 * @param value
	 * @return
	 */
	public Object setProperty(String key, String value);

	/**
	 * 
	 * {@link Properties#load(Reader)}
	 * @param reader
	 * @throws IOException
	 */
	public void load(Reader reader) throws IOException;

	public void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException;

	public Enumeration<?> propertyNames();

	public default Optional<String> getProp(String propertyName) {
		try {
			return Optional.of(this.getProperty(propertyName));
		} catch (Exception e) {
		}
		return Optional.empty();
	}

	public default void readString(String refContent) throws IOException {
		if (StaticChecker.isRefXML(refContent)) {
			this.loadFromXML(new ByteArrayInputStream(refContent.getBytes()));
		} else {
			this.load(new StringReader(refContent));
		}
	}

}
