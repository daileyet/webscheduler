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
* @Title: ProfileController.java 
* @Package com.openthinks.webscheduler.controller 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Sep 27, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.controller;

import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;

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
import com.openthinks.webscheduler.model.security.User;
import com.openthinks.webscheduler.service.WebSecurityService;

/**
 * @author dailey.yet@outlook.com
 *
 */
@Controller("/profile")
public class ProfileController {

	@AutoComponent
	WebSecurityService securityService;

	private Optional<User> getCurrentUser(WebAttributers was) {
		User currentUser = was.getSession(StaticDict.SESSION_ATTR_LOGIN_INFO);
		if (currentUser != null) {
			currentUser = securityService.getUsers().findById(currentUser.getId());
			return Optional.of(currentUser);
		}
		return Optional.empty();
	}

	@Mapping("/index")
	public String index(WebAttributers was) {

		Optional<User> currentUser = getCurrentUser(was);
		if (!currentUser.isPresent()) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1, "Session timeout, please login again.", WebScope.REQUEST);
			return StaticUtils.errorPage(was, PageMap.build());
		}
		was.storeRequest(StaticDict.PAGE_ATTRIBUTE_USER, currentUser.get());
		return "WEB-INF/jsp/profile.jsp";
	}

	@Mapping("/base/edit")
	public String editBaseInfo(WebAttributers was) {
		String email = was.get(StaticDict.PAGE_PARAM_USER_EMAIL);
		try {
			Checker.require(email).notEmpty("User email can not be empty.");
		} catch (Exception e) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1, e.getMessage(), WebScope.REQUEST);
			return StaticUtils.errorPage(was, PageMap.build());
		}
		Optional<User> currentUser = getCurrentUser(was);

		if (!currentUser.isPresent()) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1, "Session timeout, please login again.", WebScope.REQUEST);
			return StaticUtils.errorPage(was, PageMap.build());
		}
		User user = currentUser.get();
		if (!email.equals(user.getEmail())) {
			User duplicateUser = securityService.getUsers().findByEmail(email);
			if (duplicateUser != null) {
				was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1,
						"Couldn't update profile, maybe this email has already been used.", WebScope.REQUEST);
				return StaticUtils.errorPage(was, PageMap.build());
			}
		}
		user.setEmail(email);
		securityService.saveToDisk();
		return WebUtils.redirect("/profile/index");
	}

	@Mapping("/pwd/edit")
	public String editPwd(WebAttributers was) {
		String oldPwd = was.get(StaticDict.PAGE_PARAM_USER_OLD_PWD);
		String newPwd = was.get(StaticDict.PAGE_PARAM_USER_NEW_PWD);
		String newPwdAgain = was.get(StaticDict.PAGE_PARAM_USER_NEW_PWD_AGAIN);

		try {
			Checker.require(oldPwd).notEmpty("Old user password can not be empty.");
			Checker.require(newPwd).notEmpty("New user password can not be empty.");
			Checker.require(newPwdAgain).notEmpty("New user password again can not be empty.");
		} catch (Exception e) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1, e.getMessage(), WebScope.REQUEST);
			return StaticUtils.errorPage(was, PageMap.build());
		}
		Optional<User> currentUser = getCurrentUser(was);
		if (!currentUser.isPresent()) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1, "Session timeout, please login again.", WebScope.REQUEST);
			return StaticUtils.errorPage(was, PageMap.build());
		}
		User user = currentUser.get();
		if (!user.getPass().equals(DigestUtils.md5Hex(oldPwd))) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1, "Invalid old password.", WebScope.REQUEST);
			return StaticUtils.errorPage(was, PageMap.build());
		}

		if (!newPwd.equals(newPwdAgain)) {
			was.addError(StaticDict.PAGE_ATTRIBUTE_ERROR_1, "The twice new password are not same, please check.",
					WebScope.REQUEST);
			return StaticUtils.errorPage(was, PageMap.build());
		}
		user.setPass(DigestUtils.md5Hex(newPwd));
		securityService.saveToDisk();
		return WebUtils.redirect("/security/logout");
	}

}
