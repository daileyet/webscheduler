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
* @Title: TaskDefController.java 
* @Package com.openthinks.webscheduler.controller 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 4, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.controller;

import java.io.IOException;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.easyweb.context.handler.WebAttributers.WebScope;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.help.JavaCompileHelper;
import com.openthinks.webscheduler.help.JavaCompileHelper.JCompiler;
import com.openthinks.webscheduler.help.PageMap;
import com.openthinks.webscheduler.help.SourceCodeHelper;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.help.StaticUtils;
import com.openthinks.webscheduler.help.source.SourceCodeInfo;
import com.openthinks.webscheduler.model.task.def.TaskDefRuntimeData;
import com.openthinks.webscheduler.task.TaskTypes;

/**
 * @author dailey.yet@outlook.com
 *
 */
@Controller("/task/def")
public class TaskDefController {
	private PageMap newPageMap() {
		return PageMap.build().push(StaticDict.PAGE_ATTRIBUTE_ACTIVESIDEBAR, "tasks");
	}

	@Mapping("/index")
	public String index(WebAttributers was) {
		PageMap pm = newPageMap();
		pm.push(StaticDict.PAGE_ATTRIBUTE_CUSTOM_TASKS, TaskTypes.getCustomTaskMetaData());
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_MAP, pm);
		return "WEB-INF/jsp/task/def/index.jsp";
	}

	@Mapping("/apply")
	public String definition(WebAttributers was) {
		PageMap pm = newPageMap();
		String defSourceCode = was.get(StaticDict.PAGE_PARAM_TASK_DEF);
		TaskDefRuntimeData defRuntimeData = new TaskDefRuntimeData();

		SourceCodeInfo sci = SourceCodeHelper.buildTaskDef(defSourceCode);
		if (!sci.validate()) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1, "Invalid custom task definition source code.",
					WebScope.REQUEST);
			return StaticUtils.errorPage(was, pm);
		}
		defRuntimeData.setSourceCode(defSourceCode);
		defRuntimeData.setFileName(sci.getFileName());
		defRuntimeData.setFullName(sci.getClassFullName());
		defRuntimeData.setKeepSourceFile(true);
		defRuntimeData.makeDefault();
		if (defRuntimeData.isKeepSourceFile())
			try {
				StaticUtils.writeSourceCode(defRuntimeData);
			} catch (IOException e) {
				ProcessLogger.error(e);
				was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_2, "Failed to save source code to local java file.",
						WebScope.REQUEST);
				return StaticUtils.errorPage(was, pm);
			}
		JCompiler compiler = JavaCompileHelper.getCompiler(defRuntimeData);
		boolean isSuccess = compiler.exec();
		if (!isSuccess) {
			DiagnosticCollector<JavaFileObject> diagnosticCollector = compiler.getDiagnostics();
			List<Diagnostic<? extends JavaFileObject>> diagnostics = diagnosticCollector.getDiagnostics();
			int index = 0;
			for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
				index++;
				ProcessLogger.error(diagnostic.toString());
				was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_PRE + index, diagnostic.toString(), WebScope.REQUEST);
			}
			return StaticUtils.errorPage(was, pm);
		}
		pm.push("title", "Task - Defining").push("redirectUrl", WebUtils.path("/task/index"));
		TaskTypes.scan();
		return StaticUtils.intermediatePage(was, pm);
	}
}
