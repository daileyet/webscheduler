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
* @Title: StaticUtils.java 
* @Package com.openthinks.webscheduler.help 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 21, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.help;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.webscheduler.model.task.def.TaskDefRuntimeData;

/**
 * @author dailey.yet@outlook.com
 *
 */
public final class StaticUtils {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(StaticDict.DEFAULT_DATE_FORMAT_STYLE);

	public static String formatDate(Date date) {
		return DATE_FORMAT.format(date);
	}

	public static String getDefaultCustomTaskSourceDir() {
		File webInfDir = new File(WebUtils.getWebClassDir()).getParentFile();
		File sourceDir = new File(webInfDir, "ext/src");
		makeFileExist(sourceDir);
		return sourceDir.getAbsolutePath();
	}

	public static void makeFileExist(File sourceDir) {
		if (!sourceDir.exists()) {
			sourceDir.mkdirs();
		}
	}

	public static String getDefaultCustomTaskTargetDir() {
		//		File webInfDir = new File(WebUtils.getWebClassDir()).getParentFile();
		//		File targetClassDir = new File(webInfDir, "ext/classes");
		//		makeFileExist(targetClassDir);
		//		return targetClassDir.getAbsolutePath();
		return WebUtils.getWebClassDir();
	}

	public static void writeSourceCode(TaskDefRuntimeData defRuntimeData) throws IOException {
		if (defRuntimeData.getSourceCode() == null || defRuntimeData.getSourceFile() == null) {
			return;
		}
		File storeFile = defRuntimeData.getSourceFile();
		FileWriter fw = new FileWriter(storeFile);
		fw.write(defRuntimeData.getSourceCode());
		fw.flush();
		fw.close();
	}

	public static String intermediatePage(WebAttributers was, PageMap pm) {
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_MAP, pm);
		return "WEB-INF/jsp/template/intermediate.jsp";
	}

	public static String errorPage(WebAttributers was, PageMap pm) {
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_MAP, pm);
		return "WEB-INF/jsp/template/business.error.jsp";
	}

}
