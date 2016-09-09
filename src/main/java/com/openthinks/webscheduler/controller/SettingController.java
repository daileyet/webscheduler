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

import java.util.List;

import com.openthinks.easyweb.annotation.AutoComponent;
import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.model.security.Role;
import com.openthinks.webscheduler.model.security.User;
import com.openthinks.webscheduler.service.WebSecurityService;

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

}
