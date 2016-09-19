/**
 * 
 */
package com.openthinks.webscheduler.model.security;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.openthinks.webscheduler.model.Statable.DefaultStatable;

@XmlRootElement(name = "role-map")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleMap extends DefaultStatable implements Serializable {

	private static final long serialVersionUID = -5755447443298357142L;
	private String path;
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		notifyChanged(this.path, path);
		this.path = path;
	}

}
