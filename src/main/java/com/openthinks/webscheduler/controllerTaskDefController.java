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
import com.openthinks.easyweb.annotation.AutoComponent;
import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Jsonp;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.annotation.ResponseReturn;
import com.openthinks.easyweb.annotation.ResponseReturn.ResponseReturnType;
import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.easyweb.context.handler.WebAttributers.WebScope;
import com.openthinks.easyweb.utils.json.OperationJson;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.help.JavaCompileHelper;
import com.openthinks.webscheduler.help.JavaCompileHelper.JCompiler;
import com.openthinks.webscheduler.help.PageMap;
import com.openthinks.webscheduler.help.SourceCodeHelper;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.help.StaticUtils;
import com.openthinks.webscheduler.help.source.SourceCodeInfo;
import com.openthinks.webscheduler.model.task.def.TaskDefRuntimeData;
import com.openthinks.webscheduler.service.TaskService;
import com.openthinks.webscheduler.task.TaskTypes;

/**
 * @author dailey.yet@outlook.com
 *
 */
@Controller("/task/def")
public class TaskDefController {
	@AutoComponent
	private TaskService taskService;

	private PageMap newPageMap() {
		return PageMap.build().push(StaticDict.PAGE_ATTRIBUTE_ACTIVESIDEBAR, "tasks");
	}

	@Mapping("/index")
	public String index(WebAttributers was) {
		PageMap pm = newPageMap();
		pm.push(StaticDict.PAGE_ATTRIBUTE_CUSTOM_TASKS, TaskTypes.getCustomTaskMetaData());
		String fullclassname = was.get(StaticDict.PAGE_PARAM_TASK_DEF_CLASS_NAME);
		if (fullclassname != null && !"".equals(fullclassname.trim())) {
			TaskDefRuntimeData defRuntimeData = taskService.getTaskDef(fullclassname);
			if (defRuntimeData == null) {
				defRuntimeData = new TaskDefRuntimeData();
				defRuntimeData.setFullName(fullclassname);
				defRuntimeData.makeDefault();
			}
			pm.push(StaticDict.PAGE_ATTRIBUTE_TASK_DEF, defRuntimeData);
		}
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_MAP, pm);
		return "WEB-INF/jsp/task/def/index.jsp";
	}

	@Mapping("/check")
	@Jsonp
	@ResponseReturn(contentType = ResponseReturnType.TEXT_JAVASCRIPT)
	public String checkCompile(WebAttributers was) {
		String defSourceCode = was.get(StaticDict.PAGE_PARAM_TASK_DEF_CODE);
		String defFullclassnmae = was.get(StaticDict.PAGE_PARAM_TASK_DEF_CLASS_NAME);
		TaskDefRuntimeData defRuntimeData = new TaskDefRuntimeData();
		SourceCodeInfo sci = SourceCodeHelper.buildTaskDef(defSourceCode);
		defRuntimeData.setSourceCode(defSourceCode);
		defRuntimeData.setFileName(sci.getFileName());
		defRuntimeData.setFullName(sci.getClassFullName());
		defRuntimeData.setKeepSourceFile(false);
		defRuntimeData.makeDefault();
		if (defFullclassnmae == null || "".equals(defFullclassnmae.trim()))
			defFullclassnmae = sci.getClassFullName();
		JCompiler compiler = JavaCompileHelper.getCompiler(defRuntimeData);
		defRuntimeData.getLastCompileResult().start();
		if (!checkDefinition(null, defRuntimeData, defFullclassnmae, sci)) {
			defRuntimeData.getLastCompileResult().end();
			defRuntimeData.getLastCompileResult().setSuccess(false);
			return OperationJson.build().error(defRuntimeData.getLastCompileResult().toString()).toString();
		}
		boolean isSuccess = compiler.exec();
		defRuntimeData.getLastCompileResult().end();
		defRuntimeData.getLastCompileResult().setSuccess(isSuccess);
		if (!isSuccess) {
			DiagnosticCollector<JavaFileObject> diagnosticCollector = compiler.getDiagnostics();
			List<Diagnostic<? extends JavaFileObject>> diagnostics = diagnosticCollector.getDiagnostics();
			for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
				defRuntimeData.getLastCompileResult().track(diagnostic.toString());
			}
			return OperationJson.build().error(defRuntimeData.getLastCompileResult().toString()).toString();
		}
		return OperationJson.build().sucess(defRuntimeData.getLastCompileResult().toString()).toString();
	}

	@Mapping("/apply")
	public String definition(WebAttributers was) {
		PageMap pm = newPageMap();
		String defSourceCode = was.get(StaticDict.PAGE_PARAM_TASK_DEF_CODE);
		String defFullclassnmae = was.get(StaticDict.PAGE_PARAM_TASK_DEF_CLASS_NAME);
		TaskDefRuntimeData defRuntimeData = taskService.getTaskDef(defFullclassnmae);
		if (defRuntimeData == null) {
			defRuntimeData = new TaskDefRuntimeData();
		}
		SourceCodeInfo sci = SourceCodeHelper.buildTaskDef(defSourceCode);
		if (!checkDefinition(was, null, defFullclassnmae, sci)) {
			return StaticUtils.errorPage(was, pm);
		}
		defRuntimeData.setSourceCode(defSourceCode);
		defRuntimeData.setFileName(sci.getFileName());
		defRuntimeData.setFullName(sci.getClassFullName());
		defRuntimeData.setKeepSourceFile(true);
		defRuntimeData.makeDefault();
		try {
			StaticUtils.writeSourceCode(defRuntimeData);
		} catch (IOException e) {
			ProcessLogger.error(e);
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_2, "Failed to save source code to local java file.",
					WebScope.REQUEST);
			return StaticUtils.errorPage(was, pm);
		}
		JCompiler compiler = JavaCompileHelper.getCompiler(defRuntimeData);
		defRuntimeData.getLastCompileResult().start();
		boolean isSuccess = compiler.exec();
		defRuntimeData.getLastCompileResult().end();
		defRuntimeData.getLastCompileResult().setSuccess(isSuccess);
		if (!isSuccess) {
			DiagnosticCollector<JavaFileObject> diagnosticCollector = compiler.getDiagnostics();
			List<Diagnostic<? extends JavaFileObject>> diagnostics = diagnosticCollector.getDiagnostics();
			int index = 0;
			for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
				index++;
				ProcessLogger.error(diagnostic.toString());
				was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_PRE + index, diagnostic.toString(), WebScope.REQUEST);
				defRuntimeData.getLastCompileResult().track(diagnostic.toString());
			}
		}
		taskService.saveTaskDef(defRuntimeData);
		if (!isSuccess)
			return StaticUtils.errorPage(was, pm);
		pm.push("title", "Task - Defining").push("redirectUrl", WebUtils.path("/task/index"));
		TaskTypes.scan();
		return StaticUtils.intermediatePage(was, pm);
	}

	protected boolean checkDefinition(WebAttributers was, TaskDefRuntimeData defRuntimeData, String defFullclassnmae,
			SourceCodeInfo sci) {
		String error_code = "Invalid custom task definition source code.";
		if (!sci.validate()) {
			if (was != null)
				was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1, error_code, WebScope.REQUEST);
			if (defRuntimeData != null) {
				defRuntimeData.getLastCompileResult().track(error_code);
			}
			return false;
		}
		error_code = "Could not match class name with selected custom task type.";
		if (defFullclassnmae != null && !"".equals(defFullclassnmae.trim())
				&& !defFullclassnmae.equals(sci.getClassFullName())) {
			if (was != null)
				was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_2, error_code, WebScope.REQUEST);
			if (defRuntimeData != null) {
				defRuntimeData.getLastCompileResult().track(error_code);
			}
			return false;
		}
		return true;
	}
}
