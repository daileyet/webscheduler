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
* @Title: Updateable.java 
* @Package com.openthinks.webscheduler.model 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 8, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.openthinks.libs.utilities.logger.ProcessLogger;

/**
 * @author dailey.yet@outlook.com
 */
public interface Updateable<T> {

	public default void update(T newValue) {
		if (newValue == null)
			return;
		//TODO get all fields include inherited 
		Field[] oldFields = this.getClass().getDeclaredFields();
		Field[] newFields = newValue.getClass().getDeclaredFields();
		for (Field newField : newFields) {
			try {
				if (Modifier.isFinal(newField.getModifiers()))
					continue;
				newField.setAccessible(true);
				Object newFieldVal = newField.get(newValue);
				if (newFieldVal == null)
					continue;
				for (Field oldField : oldFields) {
					if (oldField == newField || (oldField.equals(newField))) {
						oldField.setAccessible(true);
						//						if (Updateable.class.isAssignableFrom(oldField.getType())) {
						//							Updateable oldFieldVal = (Updateable) oldField.get(this);
						//							if (oldFieldVal == null) {
						//								oldField.set(this, newFieldVal);
						//							} else {
						//								oldFieldVal.update(newFieldVal);
						//								oldField.set(this, oldFieldVal);
						//							}
						//						} else {
						//							oldField.set(this, newFieldVal);
						//						}
						oldField.set(this, newFieldVal);
						break;
					}
				}
			} catch (IllegalArgumentException e) {
				ProcessLogger.warn(e);
			} catch (IllegalAccessException e) {
				ProcessLogger.warn(e);
			}
		}
	}

}
