/**
 * 
 */
package com.openthinks.webscheduler.model.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.openthinks.webscheduler.model.Statable.DefaultStatable;

@XmlRootElement(name = "role-maps")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleMaps extends DefaultStatable implements Serializable{

	private static final long serialVersionUID = -5611825406523647550L;
	@XmlElement(name = "role-map")
	private Set<RoleMap> roleMaps;

	public RoleMaps() {
		super();
		this.roleMaps= new HashSet<>();
	}
	
	public Set<RoleMap> getRoleMaps() {
		return roleMaps;
	}
}
