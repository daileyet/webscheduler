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
* @Title: TaskRefProtected.java 
* @Package com.openthinks.webscheduler.task 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 19, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.task;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.openthinks.libs.utilities.Checker;
import com.openthinks.libs.utilities.exception.CheckerNoPassException;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.help.StaticUtils;
import com.openthinks.webscheduler.model.Statable;
import com.openthinks.webscheduler.model.task.DefaultTaskRef;
import com.openthinks.webscheduler.model.task.ITaskRef;
import com.openthinks.webscheduler.task.support.SimpleDownloadFileTask;

/**
 * Protect entry in {@link ITaskRef}, some {@link ITaskDefinition} will have the permission problem after deploy on production.<BR>
 * For example: <BR>
 * {@link SimpleDownloadFileTask} has a configure entry "save-dir" in its {@link ITaskRef}, most of time the value of entry cannot be set by yourself.<BR>
 * It should be protected by system administrator to set a global value for this entry 'save-dir', which deploy server user(jetty/tomcat) has the directory write permission.  
 * @author dailey.yet@outlook.com
 *
 */
public final class TaskRefProtected implements Statable{
	private static Map<Class<? extends ITaskDefinition>, TaskRefProtected> protectedCache = new ConcurrentHashMap<>();
	private static String unChangeRefsDir = null;

	public static TaskRefProtected valueOf(Class<? extends ITaskDefinition> taskDefClass) {
		TaskRefProtected taskRefProtected = protectedCache.get(taskDefClass);
		if (taskRefProtected == null) {
			taskRefProtected = new TaskRefProtected();
			taskRefProtected.taskDefClass = taskDefClass;
			taskRefProtected.initialize();
			protectedCache.put(taskDefClass, taskRefProtected);
		}
		return taskRefProtected;
	}

	@SuppressWarnings("unchecked")
	public static TaskRefProtected valueOf(String protectedClassName) throws ClassNotFoundException {
		Checker.require(protectedClassName).notNull();
		Class<?> clazz = Class.forName(protectedClassName);
		if (ITaskDefinition.class.isAssignableFrom(clazz)) {
			return valueOf((Class<? extends ITaskDefinition>) clazz);
		} else {
			throw new CheckerNoPassException(clazz + " need extends or implements " + ITaskDefinition.class);
		}
	}

	public static void setUnChangeRefsDir(String unChangeRefsDir) {
		TaskRefProtected.unChangeRefsDir = unChangeRefsDir;
	}

	public static Collection<TaskRefProtected> getAllProtectedRefs() {
		return protectedCache.values();
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////

	public TaskRefProtected() {
		this.currentState = State.OUT_SYNC;
	}
	
	private void initialize() {
		if (taskDefClass == null)
			return;
		//TODO get from MapDB firstly

		// if not found, then try to load from local configure file
		String dir = (unChangeRefsDir == null ? StaticUtils.getUnChangeRefsDefaultDir(false) : unChangeRefsDir);
		File protecteRefFile = new File(dir, taskDefClass.getName());
		if (protecteRefFile.exists() && protecteRefFile.isFile()) {
			taskRefUnChange = new DefaultTaskRef();
			try {
				taskRefUnChange.load(new FileReader(protecteRefFile));
				this.moveTo(State.IN_SYNC);
			} catch (IOException e) {
				ProcessLogger.warn(e);
			}
		}
	}

	private Class<? extends ITaskDefinition> taskDefClass;

	private ITaskRef taskRefUnChange;
	
	private State currentState ;

	public Class<? extends ITaskDefinition> getTaskDefClass() {
		return taskDefClass;
	}

	public ITaskRef getTaskRefUnChange() {
		return taskRefUnChange;
	}
	
	public boolean isInSync(){
		return currentState==State.IN_SYNC;
	}
	
	public State getCurrentState() {
		return currentState;
	}

	public TaskRefDefinitionDescriber getTaskRefDescriber() {
		Optional<TaskDefinitionMetaData> optioanl = TaskTypes.getTaskMetaData(taskDefClass);
		if (optioanl.isPresent()) {
			return optioanl.get().getRefDescriber();
		}
		return DefaultTaskRef.getTaskRefDescriber();
	}

	public String getTaskRefFormat() {
		if (taskRefUnChange != null) {
			return taskRefUnChange.getContent();
		}
		return "";
	}

	public int getProtectedCount() {
		if (taskRefUnChange != null) {
			return taskRefUnChange.stringPropertyNames().size();
		}
		return 0;
	}

	/**
	 * reload again from configure file
	 */
	public void refresh() {
		initialize();
	}

	/**
	 * do the override for protected entry
	 * @param taskRef ITaskRef
	 */
	public void protect(ITaskRef taskRef) {
		if (taskRefUnChange != null) {
			Enumeration<?> enums = taskRefUnChange.propertyNames();
			while (enums.hasMoreElements()) {
				String key = (String) enums.nextElement();
				String value = taskRefUnChange.getProperty(key);
				taskRef.setProperty(key, value);
			}
		}

	}

	@Override
	public State getState() {
		return currentState;
	}
	
	@Override
	public void moveTo(State nextState) {
		this.currentState = nextState;
	}
}
