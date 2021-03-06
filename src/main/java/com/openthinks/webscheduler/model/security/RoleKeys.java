package com.openthinks.webscheduler.model.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Reference role key set in User definition
 * 
 * @author dailey.yet@outlook.com
 *
 */
@XmlRootElement(name = "user-roles")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleKeys implements Serializable {
	private static final long serialVersionUID = 6587102605087687524L;
	@XmlElement(name = "user-role")
	private Set<RoleKey> roles;

	public RoleKeys() {
		this.roles = new HashSet<>();
	}

	public Set<RoleKey> getRoles() {
		return roles;
	}

	void setRoles(Set<RoleKey> roles) {
		this.roles = roles;
	}

	public boolean add(RoleKey alais) {
		if (this.roles != null)
			return this.roles.add(alais);
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleKeys other = (RoleKeys) obj;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (other.roles != null && (roles.size()!=other.roles.size() || !roles.containsAll(other.roles) ) )
			return false;
		return true;
	}

}
