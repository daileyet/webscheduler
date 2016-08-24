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
* @Title: WebSecurityController.java 
* @Package com.openthinks.webscheduler.controller 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 24, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.controller;

import java.util.Optional;

import com.openthinks.easyweb.annotation.AutoComponent;
import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Jsonp;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.annotation.ResponseReturn;
import com.openthinks.easyweb.annotation.ResponseReturn.ResponseReturnType;
import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.easyweb.utils.json.OperationJson;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.model.security.User;
import com.openthinks.webscheduler.service.WebSecurityService;

/**
 * @author dailey.yet@outlook.com
 *
 */
@Controller("/security")
public class WebSecurityController {

	@AutoComponent
	WebSecurityService securityService;

	@Mapping("/login")
	@Jsonp
	@ResponseReturn(contentType = ResponseReturnType.TEXT_JAVASCRIPT)
	public String doLogin(WebAttributers was) {
		String userName = was.get(StaticDict.PAGE_PARAM_LOGIN_NAME);
		String userPass = was.get(StaticDict.PAGE_PARAM_LOGIN_PASS);
		Optional<User> userOption = securityService.validateUser(userName, userPass);
		if (userOption.isPresent()) {
			was.getSession().setAttribute(StaticDict.SESSION_ATTR_LOGIN_INFO, userOption.get());
			ProcessLogger.debug("login success");
			return OperationJson.build().sucess().toString();
		}
		ProcessLogger.debug("login failed");
		return OperationJson.build().error().toString();
	}

	@Mapping("/logout")
	public String doLogout(WebAttributers was) {
		was.getSession().removeAttribute(StaticDict.SESSION_ATTR_LOGIN_INFO);
		was.getSession().invalidate();
		return "WEB-INF/jsp/main.jsp";
	}

}
