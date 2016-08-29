package com.openthinks.webscheduler.model.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Reference role key set in User definition
 * @author dailey.yet@outlook.com
 *
 */
@XmlRootElement(name = "user-roles")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleKeys implements Serializable {
	private static final long serialVersionUID = 6587102605087687524L;
	@XmlElement(name = "user-role")
	private List<RoleKey> roles;

	public RoleKeys() {
		this.roles = new ArrayList<>();
	}

	public List<RoleKey> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleKey> roles) {
		this.roles = roles;
	}

	public boolean add(RoleKey alais) {
		if (this.roles != null)
			return this.roles.add(alais);
		return false;

	}

}
