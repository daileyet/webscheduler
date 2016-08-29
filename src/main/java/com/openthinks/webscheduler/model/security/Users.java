package com.openthinks.webscheduler.model.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User definition set
 * @author dailey.yet@outlook.com
 *
 */
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class Users implements Serializable {
	private static final long serialVersionUID = -8626028704451201036L;
	@XmlElement(name = "user")
	private List<User> users;

	public Users() {
		this.users = new ArrayList<>();
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public boolean add(User user) {
		if (this.users != null)
			return this.users.add(user);
		return false;
	}

	public User findById(String userId) {
		List<User> result = users.stream().filter((user) -> {
			if (user.getId().equals(userId))
				return true;
			return false;
		}).collect(Collectors.toList());
		return result.isEmpty() ? null : result.get(0);
	}

	public User findByName(String userName) {
		List<User> result = users.stream().filter((user) -> {
			if (user.getName() != null && user.getName().equals(userName))
				return true;
			return false;
		}).collect(Collectors.toList());
		return result.isEmpty() ? null : result.get(0);
	}
}
