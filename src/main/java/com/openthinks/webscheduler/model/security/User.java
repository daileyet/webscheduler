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
* @Title: User.java 
* @Package com.openthinks.webscheduler.model 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 23, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.model.security;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.SerializationUtils;

import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.libs.utilities.Checker;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.model.Statable.DefaultStatable;
import com.openthinks.webscheduler.service.WebSecurityService;

/**
 * User definition
 * @author dailey.yet@outlook.com
 *
 */
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends DefaultStatable implements Serializable {
	private static final long serialVersionUID = -363092122443174954L;
	@XmlAttribute
	private String id;
	@XmlElement(name = "user-name")
	private String name;
	@XmlElement(name = "user-email")
	private String email;
	@XmlElement(name = "user-pass")
	private String pass;
	@XmlTransient
	private Set<Role> roles;
	@XmlElement(name = "user-roles")
	private RoleKeys roleKeys;
	@XmlElement(name = "user-cookie")
	private RememberMeCookie cookie;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		notifyChanged(this.id, id);
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		notifyChanged(this.name, name);
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		notifyChanged(this.email, email);
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		notifyChanged(this.pass, pass);
		this.pass = pass;
	}

	public Set<Role> getRoles() {
		if ((this.roles == null || this.roles.isEmpty()) && this.roleKeys != null) {
			try {
				this.roles = this.roleKeys.getRoles().stream().map((roleKey) -> {
					Role role = null;
					Roles roles = WebContexts.get().lookup(WebSecurityService.class).getRoles();
					if (roleKey.getId() != null) {
						role = roles.findById(roleKey.getId());
					}
					if (role == null && roleKey.getName() != null) {
						role = roles.findByName(roleKey.getName());
					}
					Checker.require(role).notNull();
					return role;
				}).collect(Collectors.toSet());
			} catch (Exception e) {
				ProcessLogger.error(e);
				this.roles = Collections.emptySet();
			}
		}
		return roles;
	}

	public String getJoinedKeys() {
		String joinedKeys = getRoles().stream().map((role) -> {
			return role.getId();
		}).collect(Collectors.joining(StaticDict.PAGE_PARAM_LIST_JOIN));
		return joinedKeys;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
		if (roles != null) {
			RoleKeys roleKeys = new RoleKeys();
			roles.forEach((role) -> {
				RoleKey key = new RoleKey();
				key.setId(role.getId());
				key.setName(role.getName());
				roleKeys.add(key);
			});
			setRoleKeys(roleKeys);
		}
	}

	public RoleKeys getRoleKeys() {
		return roleKeys;
	}

	public void setRoleKeys(RoleKeys roleKeys) {
		notifyChanged(this.roleKeys, roleKeys);
		this.roleKeys = roleKeys;
	}

	public RememberMeCookie getCookie() {
		return cookie;
	}

	public void setCookie(RememberMeCookie cookie) {
		this.cookie = cookie;
	}

	@Override
	public User clone() {
		return SerializationUtils.clone(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != null && id.equals(other.id)) {
			return true;
		}
		if (name != null && name.equals(other.name)) {
			return true;
		}
		if (email != null && email.equals(other.email)) {
			return true;
		}
		return false;
	}

}
