/**
 * 
 */
package com.openthinks.webscheduler.model.security;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.openthinks.libs.utilities.Checker;
import com.openthinks.webscheduler.model.Statable.DefaultStatable;

@XmlRootElement(name = "role-map")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleMap extends DefaultStatable implements Serializable {

	private static final long serialVersionUID = -5755447443298357142L;
	@XmlAttribute
	private String path;
	@XmlAttribute
	private String include;

	public String getPath() {
		return path;
	}

	public String getInclude() {
		return include;
	}

	public void setPath(String path) {
		notifyChanged(this.path, path);
		this.path = path;
	}

	public void setInclude(String include) {
		notifyChanged(this.include, include);
		this.include = include;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((include == null) ? 0 : include.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		RoleMap other = (RoleMap) obj;
		if (path != null && path.equals(other.path))
			return true;
		if (include != null && include.equals(other.include))
			return true;
		return false;
	}

	public static RoleMap valueOf(String str) {
		Checker.require(str).notEmpty();
		RoleMap roleMap = new RoleMap();
		if (str.contains("/")) {//it is path
			roleMap.setPath(str);
		} else {//it is include other role id
			roleMap.setInclude(str);
		}
		return roleMap;
	}

}
