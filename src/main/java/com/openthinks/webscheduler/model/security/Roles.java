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
* @Title: Roles.java 
* @Package com.openthinks.webscheduler.model 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 23, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.model.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.model.Statable.DefaultStatable;

/**
 * Role definition set
 * @author dailey.yet@outlook.com
 *
 */
@XmlRootElement(name = "roles")
@XmlAccessorType(XmlAccessType.FIELD)
public class Roles extends DefaultStatable {
	@XmlElement(name = "role")
	private Set<Role> roles;

	public Roles() {
		this.roles = new HashSet<>();
	}

	@Override
	public State getState() {
		if (this.roles != null) {
			for (Role role : roles) {
				if (role.getState() != State.SAVED) {
					moveToChanged();
					break;
				}
			}
		}
		return super.getState();
	}

	@Override
	public void moveTo(State nextState) {
		if (this.roles != null) {
			for (Role role : roles) {
				role.moveTo(nextState);
			}
		}
		super.moveTo(nextState);
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean add(Role role) {
		if (this.roles != null) {
			return this.roles.add(role);
		}
		return false;
	}

	public Role findById(String roleId) {
		List<Role> result = roles.stream().filter((role) -> {
			if (role.getId().equals(roleId))
				return true;
			return false;
		}).collect(Collectors.toList());
		return result.isEmpty() ? null : result.get(0);
	}

	public List<Role> findByIds(String roleIds) {
		List<Role> result = roles.stream().filter((role) -> {
			if (roleIds.indexOf(role.getId()) >= 0)
				return true;
			return false;
		}).collect(Collectors.toList());
		return result;
	}

	public Role findByName(String roleName) {
		List<Role> result = roles.stream().filter((role) -> {
			if (role.getName() != null && role.getName().equals(roleName))
				return true;
			return false;
		}).collect(Collectors.toList());
		return result.isEmpty() ? null : result.get(0);
	}

	public boolean removeById(String roleId) {
		boolean result = false;
		Role role = findById(roleId);
		try {
			result = roles.remove(role);
			this.moveToChanged();
		} catch (Exception e) {
			ProcessLogger.warn(e);
			result = false;
		}
		return result;
	}

}
