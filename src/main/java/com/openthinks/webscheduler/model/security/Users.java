package com.openthinks.webscheduler.model.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.help.StaticUtils;
import com.openthinks.webscheduler.model.Statable;
import com.openthinks.webscheduler.model.Statable.DefaultStatable;

/**
 * User definition set
 * @author dailey.yet@outlook.com
 *
 */
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class Users extends DefaultStatable implements Serializable {
	private static final long serialVersionUID = -8626028704451201036L;
	@XmlElement(name = "user")
	private Set<User> users;

	public Users() {
		super();
		this.users = new HashSet<>();
	}

	@Override
	public State getState() {
		if (this.users != null) {
			for (Statable user : users) {
				if (user.getState() != State.SAVED) {
					moveToChanged();
					break;
				}
			}
		}
		return super.getState();
	}

	@Override
	public void moveTo(State nextState) {
		if (this.users != null) {
			for (Statable user : users) {
				user.moveTo(nextState);
			}
		}
		super.moveTo(nextState);
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public boolean add(User user) {
		if (this.users != null)
			return this.users.add(user);
		return false;
	}

	/**
	 * find {@link User} by id
	 * @param userId String
	 * @return {@link User} or null
	 */
	public User findById(String userId) {
		//		List<User> result = users.stream().filter((user) -> {
		//			if (user.getId().equals(userId))
		//				return true;
		//			return false;
		//		}).collect(Collectors.toList());
		//		return result.isEmpty() ? null : result.get(0);
		for (User user : users) {
			if (user.getId().equals(userId)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * find {@link User} by name
	 * @param userName String
	 * @return {@link User} or null
	 */
	public User findByName(String userName) {
		//		List<User> result = users.stream().filter((user) -> {
		//			if (user.getName() != null && user.getName().equals(userName))
		//				return true;
		//			return false;
		//		}).collect(Collectors.toList());
		//		return result.isEmpty() ? null : result.get(0);
		for (User user : users) {
			if (user.getName() != null && user.getName().equals(userName)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * find {@link User} by email
	 * @param userEmail String
	 * @return {@link User} or null
	 */
	public User findByEmail(String userEmail) {
		//		List<User> result = users.stream().filter((user) -> {
		//			if (user.getEmail() != null && user.getEmail().equals(userEmail))
		//				return true;
		//			return false;
		//		}).collect(Collectors.toList());
		//		return result.isEmpty() ? null : result.get(0);
		for (User user : users) {
			if (user.getEmail() != null && user.getEmail().equals(userEmail)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * find {@link User} by cookie
	 * @param cookieValue String
	 * @return {@link User} or null
	 */
	public User findByCookie(String cookieValue) {
		//		List<User> result = users.stream().filter((user) -> {
		//			RememberMeCookie rememberCookie = user.getCookie();
		//			if (rememberCookie != null && rememberCookie.getToken().equals(cookieValue)) {
		//				String expireTime = rememberCookie.getExpireTime();
		//				if (expireTime != null) {
		//					try {
		//						if (StaticUtils.parseDate(expireTime).before(new Date()))//validate expire time
		//							return false;
		//					} catch (Exception e) {
		//						rememberCookie.setExpireTime(StaticUtils.formatNow());
		//					}
		//				}
		//				return true;
		//			}
		//			return false;
		//		}).collect(Collectors.toList());
		//		return result.isEmpty() ? null : result.get(0);
		for (User user : users) {
			RememberMeCookie rememberCookie = user.getCookie();
			if (rememberCookie != null && rememberCookie.getToken().equals(cookieValue)) {
				String expireTime = rememberCookie.getExpireTime();
				if (expireTime != null) {
					try {
						if (StaticUtils.parseDate(expireTime).before(new Date()))//validate expire time
							continue;
					} catch (Exception e) {
						rememberCookie.setExpireTime(StaticUtils.formatNow());
					}
				} else {
					return user;
				}
			}
		}
		return null;

	}

	public boolean removeById(String userId) {
		boolean result = false;
		Statable user = findById(userId);
		try {
			result = users.remove(user);
			this.moveToChanged();
		} catch (Exception e) {
			ProcessLogger.warn(e);
			result = false;
		}
		return result;
	}
}
