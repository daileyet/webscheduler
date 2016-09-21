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
* @Title: WebSecurityFilter.java 
* @Package com.openthinks.webscheduler.filter 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 26, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.filter;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.AutoComponent;
import com.openthinks.easyweb.annotation.Filter;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.model.security.Role;
import com.openthinks.webscheduler.model.security.User;
import com.openthinks.webscheduler.service.WebSecurityService;

/**
 * @author dailey.yet@outlook.com
 *
 */
@Filter
public class WebSecurityFilter {
	@AutoComponent
	WebSecurityService securityService;

	@Mapping("/")
	//@Mapping("/.*")
	public String auth0(WebAttributers was) {
		return newAuth(was);
	}

	@Mapping("/(index|help|chart1|chart2|security/login)")
	public String anonymousPath() {
		return WebUtils.filterPass();
	}

	private String newAuth(WebAttributers was) {
		Set<Role> roles = getCurrentSessionRole(was);
		String requestURI = was.getRequest().getRequestURI();
		String mappingPath = WebUtils.getRequestMapingPath(requestURI);
		boolean isPass = roles.stream().anyMatch((role) -> {
			return role.getRoleMaps().existPath(mappingPath);
		});
		if (isPass) {
			return WebUtils.filterPass();
		}
		return WebUtils.redirect("/index");
	}

	private Set<Role> getCurrentSessionRole(WebAttributers was) {
		if (was.getSession(StaticDict.SESSION_ATTR_LOGIN_INFO) == null) {
			Optional<User> userOption = securityService.validateUserByCookie(was);
			if (userOption.isPresent()) {
				return userOption.get().getRoles();
			} else {
				//				Role anonymousRole = securityService.getRoles().findByName("anonymous");
				//				if (anonymousRole != null) {
				//					Set<Role> roles = new HashSet<>();
				//					roles.add(anonymousRole);
				//					return roles;
				//				}
			}
		} else {
			try {
				User user = was.getSession(StaticDict.SESSION_ATTR_LOGIN_INFO);
				return user.getRoles();
			} catch (Exception e) {
				ProcessLogger.error(e);
			}
		}
		return Collections.emptySet();
	}
}
