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
* @Title: WebSecurityService.java 
* @Package com.openthinks.webscheduler.service 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 24, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.codec.digest.DigestUtils;

import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.libs.utilities.Checker;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.help.StaticUtils;
import com.openthinks.webscheduler.help.confs.SecurityConfig;
import com.openthinks.webscheduler.model.security.Roles;
import com.openthinks.webscheduler.model.security.User;
import com.openthinks.webscheduler.model.security.Users;
import com.openthinks.webscheduler.model.security.WebSecurity;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class WebSecurityService {
	private WebSecurity webSecurity;
	private SecurityConfig securityConfig;

	public void init(SecurityConfig securityConfig) {
		ProcessLogger.debug(getClass() + " start init...");
		this.securityConfig = securityConfig;
		this.webSecurity = getWebSecurity();
	}

	public WebSecurity getWebSecurity() {
		if (this.securityConfig != null) {
			return this.securityConfig.getWebSecurity();
		} else {
			return new WebSecurity();
		}
	}

	public Roles getRoles() {
		Checker.require(this.webSecurity).notNull();
		return this.webSecurity.getRoles();
	}

	public Users getUsers() {
		Checker.require(this.webSecurity).notNull();
		return this.webSecurity.getUsers();
	}

	public Optional<User> validateUser(String userName, String userPass) {
		User user = getUsers().findByName(userName);
		if (user != null && userPass != null) {
			String encryptPass = DigestUtils.md5Hex(userPass);
			if (user.getPass().equals(encryptPass)) {
				User loginUserInfo = user.clone();//fix second login failed issue
				loginUserInfo.setPass(null);
				return Optional.of(loginUserInfo);
			}
		}
		return Optional.empty();
	}

	public Optional<User> validateUserByCookie(WebAttributers was) {
		Cookie[] cookies = was.getRequest().getCookies();
		if (cookies == null)
			return Optional.empty();
		Cookie rememberCookie = null;
		for (Cookie cookie : cookies) {
			if (StaticDict.COOKIE_REMEMBER_ME.equals(cookie.getName()) && cookie.getValue() != null) {
				rememberCookie = cookie;
				break;
			}
		}
		if (rememberCookie != null) {
			User user = getUsers().findByCookie(rememberCookie.getValue());
			if (user != null) {
				User loginUserInfo = user.clone();//fix second login failed issue
				loginUserInfo.setPass(null);
				return Optional.of(loginUserInfo);
			}
		}
		return Optional.empty();
	}

	public Cookie createRememberMeCookie() {
		Cookie cookie = new Cookie(StaticDict.COOKIE_REMEMBER_ME, DigestUtils.sha1Hex(StaticUtils.UUID()));
		cookie.setMaxAge(StaticDict.COOKIE_REMEMBER_ME_EXPIRE_TIME);
		cookie.setPath(StaticUtils.getRootContext());
		return cookie;
	}

	/**
	 * save changes to disk file
	 */
	public void saveToDisk() {
		Checker.require(this.securityConfig).notNull();
		Checker.require(this.webSecurity).notNull();
		File file = this.securityConfig.getConfigFile();
		try {
			FileOutputStream fos = new FileOutputStream(file);
			JAXBContext jaxbContext = JAXBContext.newInstance(WebSecurity.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(this.webSecurity, fos);
			fos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
