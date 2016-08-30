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
* @Title: RememberMeCookie.java 
* @Package com.openthinks.webscheduler.model.security 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 30, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.model.security;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.time.DateUtils;

import com.openthinks.webscheduler.help.StaticUtils;

/**
 * @author dailey.yet@outlook.com
 *
 */
@XmlRootElement(name = "cookie")
@XmlAccessorType(XmlAccessType.FIELD)
public class RememberMeCookie implements Serializable {
	private static final long serialVersionUID = 8656062059581251369L;
	@XmlElement(name = "token")
	private String token;
	@XmlElement(name = "expire")
	private String expireTime;//StaticUtils.formatDate(new Date())

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public static RememberMeCookie valueOf(final Cookie cookie) {
		RememberMeCookie rememberMeCookie = new RememberMeCookie();
		rememberMeCookie.setToken(cookie.getValue());
		Date expireDate = DateUtils.addSeconds(new Date(), cookie.getMaxAge());
		rememberMeCookie.setExpireTime(StaticUtils.formatDate(expireDate));
		return rememberMeCookie;
	}

}
