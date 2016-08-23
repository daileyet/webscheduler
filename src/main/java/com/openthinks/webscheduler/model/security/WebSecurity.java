package com.openthinks.webscheduler.model.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "web-security")
@XmlAccessorType(XmlAccessType.FIELD)
public class WebSecurity {
	@XmlElement(name = "roles")
	private Roles roles;
	@XmlElement(name = "users")
	private Users users;

	public WebSecurity() {
		this.roles = new Roles();
		this.users = new Users();
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}
