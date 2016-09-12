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
* @Title: SettingController.java 
* @Package com.openthinks.webscheduler.controller 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 24, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.AutoComponent;
import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.easyweb.context.handler.WebAttributers.WebScope;
import com.openthinks.webscheduler.help.PageMap;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.help.StaticUtils;
import com.openthinks.webscheduler.model.security.Role;
import com.openthinks.webscheduler.model.security.User;
import com.openthinks.webscheduler.service.WebSecurityService;
import com.openthinks.webscheduler.task.ITaskDefinition;
import com.openthinks.webscheduler.task.TaskRefProtected;
import com.openthinks.webscheduler.task.TaskTypes;

/**
 * @author dailey.yet@outlook.com
 *
 */
@Controller("/setting")
public class SettingController {
	@AutoComponent
	WebSecurityService securityService;

	@Mapping("/index")
	public String index() {
		return "WEB-INF/jsp/setting/index.jsp";
	}

	@Mapping("/role")
	public String role(WebAttributers was) {
		List<Role> roles = securityService.getRoles().getRoles();
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_ROLE_LIST, roles);
		return "WEB-INF/jsp/setting/role/index.jsp";
	}

	@Mapping("/user")
	public String user(WebAttributers was) {
		List<User> users = securityService.getUsers().getUsers();
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_USER_LIST, users);
		return "WEB-INF/jsp/setting/user/index.jsp";
	}

	@Mapping("/ref")
	public String taskRef(WebAttributers was) {
		List<Class<? extends ITaskDefinition>> taskDefs = TaskTypes.getSupportTasks();
		taskDefs.addAll(TaskTypes.getCustomTasks());
		for(Class<? extends ITaskDefinition> defClazz:taskDefs) {
			TaskRefProtected.valueOf(defClazz);
		}
		Collection<TaskRefProtected> trps = TaskRefProtected.getAllProtectedRefs();
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_PROTECTED_REF_LIST, trps);
		return "WEB-INF/jsp/setting/ref/index.jsp";
	}

	@Mapping("/ref/to/edit")
	public String toEditTaskRef(WebAttributers was) {
		String protectedClassName = was.get(StaticDict.PAGE_PARAM_TASK_REF_PROTECTED_ID);
		TaskRefProtected trp = null;
		boolean isSuccess = false;
		try {
			trp = TaskRefProtected.valueOf(protectedClassName);
			isSuccess = true;
		} catch (ClassNotFoundException e) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_PRE + "Param",
					"Couldn't found this protected task class type.", WebScope.REQUEST);
		} catch (Exception e) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_PRE + "Param",
					"Protected task class name could not be null or not a task class type.", WebScope.REQUEST);
		}
		if (!isSuccess) {
			return StaticUtils.errorPage(was, this.newPageMap());
		}
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_PROTECTED_REF, trp);
		return "WEB-INF/jsp/setting/ref/edit.jsp";
	}
	
	@Mapping("/ref/edit")
	public String editTaskRef(WebAttributers was){
		String protectedClassName = was.get(StaticDict.PAGE_PARAM_TASK_REF_PROTECTED_ID);
		String protectedRef = was.get(StaticDict.PAGE_PARAM_TASK_REF_PROTECTED_CONTENT);
		TaskRefProtected trp = null;
		boolean isSuccess = false;
		PageMap pm = newPageMap();
		try{
			trp = TaskRefProtected.valueOf(protectedClassName);
			trp.getTaskRefUnChange().readString(protectedRef);
			isSuccess = true;
		}catch (ClassNotFoundException e) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_PRE + "Param",
					"Couldn't found this protected task class type.", WebScope.REQUEST);
		}catch(IOException e){
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_PRE + "Param",
					"Couldn't load this new protected refs.", WebScope.REQUEST);
		}
		catch (Exception e) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_PRE + "Param",
					"Protected task class name could not be null or not a task class type.", WebScope.REQUEST);
		}
		if (!isSuccess) {
			return StaticUtils.errorPage(was, pm);
		}
		pm.push("title", "Setting - Editing REFs").push("redirectUrl", WebUtils.path("/setting/ref"));
		return StaticUtils.intermediatePage(was, pm);
	}

	private PageMap newPageMap() {
		return PageMap.build().push(StaticDict.PAGE_ATTRIBUTE_ACTIVESIDEBAR, "settings");
	}

}
