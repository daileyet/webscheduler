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
* @Title: ODBHelper.java 
* @Package com.openthinks.webscheduler.help 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 28, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.help;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.OdbConfiguration;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.libs.utilities.logger.ProcessLogger;

/**
 * Neodatis ODB helper
 * @author dailey.yet@outlook.com
 *
 */
public final class ODBHelper {
	private static String storeDBPath;
	private static ODB instance;
	private static Lock lock = new ReentrantLock();
	static {
		OdbConfiguration.setReconnectObjectsToSession(true);
		try {
			setUp(new File(WebUtils.getWebClassDir()), StaticDict.STORE_DB);
		} catch (Exception e) {
			ProcessLogger.warn(e);
		}
	}

	/**
	 * set location of DB
	 * @param dir File director of DB
	 * @param name String DB name
	 */
	public static final void setUp(File dir, String name) {
		//File dbFile = new File(WebUtils.getWebClassDir(), StaticDict.STORE_DB);
		File dbFile = new File(dir, name);
		storeDBPath = dbFile.getAbsolutePath();
	}

	public static final ODB getSigltonODB() {
		lock.lock();
		try {
			if (instance == null || instance.isClosed()) {
				instance = ODBFactory.open(storeDBPath);
			}
		} finally {
			lock.unlock();
		}
		return instance;
	}

	public static final void release() {
		if (instance != null) {
			lock.lock();
			try {
				if (instance != null && !instance.isClosed())
					instance.close();
			} finally {
				lock.unlock();
			}
		}
	}

	public static final void close(ODB odb) {
		if (odb != null && !odb.isClosed()) {
			odb.close();
		}
	}

	public static final void closeSiglton() {
		release();
	}

	public static final void commitSiglton() {
		if (instance != null) {
			lock.lock();
			try {
				if (instance != null && !instance.isClosed())
					instance.commit();
			} finally {
				lock.unlock();
			}
		}
	}

	public static final void rollbackSiglton() {
		if (instance != null) {
			lock.lock();
			try {
				if (instance != null && !instance.isClosed())
					instance.rollback();
			} finally {
				lock.unlock();
			}
		}
	}

}
