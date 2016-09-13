package com.openthinks.webscheduler.model.security;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.openthinks.webscheduler.model.Statable.DefaultStatable;

@XmlRootElement(name = "web-security")
@XmlAccessorType(XmlAccessType.FIELD)
public class WebSecurity extends DefaultStatable implements Serializable {
	private static final long serialVersionUID = -6611150406707085265L;
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

	protected void setRoles(Roles roles) {
		this.roles = roles;
	}

	public Users getUsers() {
		return users;
	}

	protected void setUsers(Users users) {
		this.users = users;
	}

	@Override
	public void moveTo(State nextState) {
		if (this.roles != null) {
			this.roles.moveTo(nextState);
		}
		if (this.users != null) {
			this.users.moveTo(nextState);
		}
	}

}
