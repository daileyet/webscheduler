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
* @Title: Role.java 
* @Package com.openthinks.webscheduler.model 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 23, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.model.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.model.Statable.DefaultStatable;

/**
 * Role definition
 * @author dailey.yet@outlook.com
 *
 */
@XmlRootElement(name = "role")
@XmlAccessorType(XmlAccessType.FIELD)
public class Role extends DefaultStatable implements Serializable {
	private static final long serialVersionUID = -7271233095825354386L;
	@XmlAttribute
	protected String id;
	@XmlElement(name = "role-name")
	protected String name;
	@XmlElement(name = "role-desc")
	protected String desc;
	@XmlElement(name = "role-maps")
	private RoleMaps roleMaps;

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		notifyChanged(this.desc, desc);
		this.desc = desc;
	}

	public RoleMaps getRoleMaps() {
		if (roleMaps == null) {
			this.roleMaps = new RoleMaps();
		}
		return roleMaps;
	}

	public String getJoinedRoleMaps() {
		String joinedMaps = getRoleMaps().getRoleMaps().stream().map((roleMap) -> {
			Set<String> joinedSet = new HashSet<>();
			if (roleMap.getInclude() != null && roleMap.getInclude().trim().length() > 0) {
				joinedSet.add(roleMap.getInclude());
			}
			if (roleMap.getPath() != null && roleMap.getPath().trim().length() > 0) {
				joinedSet.add(roleMap.getPath());
			}
			return joinedSet.stream().collect(Collectors.joining(StaticDict.PAGE_PARAM_LIST_JOIN));
		}).collect(Collectors.joining(StaticDict.PAGE_PARAM_LIST_JOIN));
		return joinedMaps;
	}

	public void setRoleMaps(RoleMaps roleMaps) {
		notifyChanged(this.roleMaps, roleMaps);
		this.roleMaps = roleMaps;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Role other = (Role) obj;
		if (id != null && id.equals(other.id))
			return true;
		if (name != null && name.equals(other.name))
			return true;
		return false;
	}

}
