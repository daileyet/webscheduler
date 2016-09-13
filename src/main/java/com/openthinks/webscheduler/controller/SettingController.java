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
import java.util.List;
import java.util.Set;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.AutoComponent;
import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.easyweb.context.handler.WebAttributers.WebScope;
import com.openthinks.libs.utilities.Checker;
import com.openthinks.webscheduler.help.PageMap;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.help.StaticUtils;
import com.openthinks.webscheduler.model.security.Role;
import com.openthinks.webscheduler.model.security.User;
import com.openthinks.webscheduler.model.security.Users;
import com.openthinks.webscheduler.model.task.ITaskRef;
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
		Users usersObj = securityService.getUsers();
		Set<User> users = usersObj.getUsers();
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_USER_LIST, users);
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_IS_IN_SYNC, usersObj.isInSync());
		return "WEB-INF/jsp/setting/user/index.jsp";
	}

	@Mapping("/user/to/add")
	public String toAddUser(WebAttributers was) {
		List<Role> roles = securityService.getRoles().getRoles();
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_ROLE_LIST, roles);
		return "WEB-INF/jsp/setting/user/add.jsp";
	}

	@Mapping("/user/add")
	public String addUser(WebAttributers was) {
		boolean isSuccess = false;
		PageMap pm = newPageMap();
		String userName = was.get(StaticDict.PAGE_PARAM_USER_NAME);
		String userEmail = was.get(StaticDict.PAGE_PARAM_USER_EMAIL);
		String userPass = was.get(StaticDict.PAGE_PARAM_USER_PASS);
		String userRoles = was.get(StaticDict.PAGE_PARAM_USER_ROLES);
		try {
			Checker.require(userName).notEmpty("User name can not be empty.");
			Checker.require(userEmail).notEmpty("User email can not be empty.");
			Checker.require(userPass).notEmpty("User password can not be empty.");
			Checker.require(userRoles).notEmpty("User roles can not be empty.");
		} catch (Exception e) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1, e.getMessage(), WebScope.REQUEST);
			return StaticUtils.errorPage(was, pm);
		}
		User user = new User();
		user.setId(StaticUtils.UUID());
		user.setName(userName);
		user.setEmail(userEmail);
		user.setPass(userPass);
		List<Role> roles = securityService.getRoles().findByIds(userRoles);
		user.setRoles(roles);
		if (roles == null || roles.isEmpty()) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1, "Invalid user roles, please check again.",
					WebScope.REQUEST);
			return StaticUtils.errorPage(was, pm);
		}
		isSuccess = securityService.getUsers().add(user);
		if (!isSuccess) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1,
					"Couldn't add this user, maybe this user name/email has already been used.", WebScope.REQUEST);
			return StaticUtils.errorPage(was, pm);
		}
		pm.push("title", "Setting - Adding user").push("redirectUrl", WebUtils.path("/setting/user"));
		return StaticUtils.intermediatePage(was, pm);
	}

	@Mapping("/user/to/edit")
	public String toEditUser(WebAttributers was) {
		String userId = was.get(StaticDict.PAGE_PARAM_USER_ID);
		User user = securityService.getUsers().findById(userId);
		if (user == null) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1, "Couldn't found this user, please check and do it again.",
					WebScope.REQUEST);
			return StaticUtils.errorPage(was, this.newPageMap());
		}
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_USER, "user");
		return "WEB-INF/jsp/setting/user/edit.jsp";
	}

	@Mapping("/user/edit")
	public String editUser(WebAttributers was) {
		boolean isSuccess = false;
		PageMap pm = newPageMap();
		//TODO
		if (!isSuccess) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1,
					"Couldn't save this user, maybe this email has already been used.", WebScope.REQUEST);
			return StaticUtils.errorPage(was, pm);
		}
		pm.push("title", "Setting - Editing user").push("redirectUrl", WebUtils.path("/setting/user"));
		return StaticUtils.intermediatePage(was, pm);
	}

	@Mapping("/user/sync")
	public String syncUser(WebAttributers was) {
		PageMap pm = newPageMap();
		try {
			securityService.saveToDisk();
		} catch (Exception e) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1, "Couldn't sync users, root cause:" + e.getMessage(),
					WebScope.REQUEST);
			return StaticUtils.errorPage(was, pm);
		}
		pm.push("title", "Setting - Syncing Users").push("redirectUrl", WebUtils.path("/setting/user"));
		return StaticUtils.intermediatePage(was, pm);
	}

	@Mapping("/ref")
	public String taskRef(WebAttributers was) {
		List<Class<? extends ITaskDefinition>> taskDefs = TaskTypes.getSupportTasks();
		taskDefs.addAll(TaskTypes.getCustomTasks());
		boolean isInSync = true;
		for (Class<? extends ITaskDefinition> defClazz : taskDefs) {
			TaskRefProtected trp = TaskRefProtected.valueOf(defClazz);
			if (!trp.isInSync()) {
				isInSync = false;
			}
		}
		Collection<TaskRefProtected> trps = TaskRefProtected.getAllProtectedRefs();
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_PROTECTED_REF_LIST, trps);
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_IS_IN_SYNC, isInSync);
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
	public String editTaskRef(WebAttributers was) {
		String protectedClassName = was.get(StaticDict.PAGE_PARAM_TASK_REF_PROTECTED_ID);
		String protectedRef = was.get(StaticDict.PAGE_PARAM_TASK_REF_PROTECTED_CONTENT);
		TaskRefProtected trp = null;
		boolean isSuccess = false;
		PageMap pm = newPageMap();
		try {
			trp = TaskRefProtected.valueOf(protectedClassName);
			ITaskRef taskRef = trp.getTaskRefUnChange();
			if (taskRef == null) {//not exist this protected REF file
				taskRef = trp.makeDefaultTaskRef();
			}
			taskRef.readString(protectedRef);
			isSuccess = true;
			trp.moveToChanged();
		} catch (ClassNotFoundException e) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_PRE + "Param",
					"Couldn't found this protected task class type.", WebScope.REQUEST);
		} catch (IOException e) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_PRE + "Param", "Couldn't load this new protected refs.",
					WebScope.REQUEST);
		} catch (Exception e) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_PRE + "Param",
					"Protected task class name could not be null or not a task class type.", WebScope.REQUEST);
		}
		if (!isSuccess) {
			return StaticUtils.errorPage(was, pm);
		}
		pm.push("title", "Setting - Editing REFs").push("redirectUrl", WebUtils.path("/setting/ref"));
		return StaticUtils.intermediatePage(was, pm);
	}

	@Mapping("/ref/sync")
	public String syncRef(WebAttributers was) {
		TaskRefProtected.sync();
		PageMap pm = newPageMap();
		pm.push("title", "Setting - Syncing REFs").push("redirectUrl", WebUtils.path("/setting/ref"));
		return StaticUtils.intermediatePage(was, pm);
	}

	private PageMap newPageMap() {
		return PageMap.build().push(StaticDict.PAGE_ATTRIBUTE_ACTIVESIDEBAR, "settings");
	}

}
