package com.openthinks.webscheduler.model.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.openthinks.libs.utilities.Checker;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.model.Statable.DefaultStatable;

@XmlRootElement(name = "role-maps")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleMaps extends DefaultStatable implements Serializable {

	/**
	 * build instance of {@link RoleMaps} from joined path which joined with default split token {@link StaticDict#PAGE_PARAM_LIST_JOIN}
	 * @param rolemaps String joined path
	 * @return RoleMaps
	 */
	public static RoleMaps valueOf(String rolemaps) {
		return valueOf(rolemaps, StaticDict.PAGE_PARAM_LIST_JOIN);
	}

	/**
	 * build instance of {@link RoleMaps} from joined path
	 * @param rolemaps String joined path
	 * @param splitToken String split token
	 * @return RoleMaps
	 */
	public static RoleMaps valueOf(String rolemaps, String splitToken) {
		Checker.require(rolemaps).notEmpty();
		Checker.require(splitToken).notNull();
		String[] arrayStrs = rolemaps.split(splitToken);
		RoleMaps roleMaps = new RoleMaps();
		for (String str : arrayStrs) {
			roleMaps.add(RoleMap.valueOf(str));
		}
		return roleMaps;
	}

	private static final long serialVersionUID = -5611825406523647550L;
	@XmlElement(name = "role-map")
	private Set<RoleMap> roleMaps;

	public RoleMaps() {
		super();
		this.roleMaps = new HashSet<>();
	}

	public Set<RoleMap> getRoleMaps() {
		return roleMaps;
	}

	public boolean add(RoleMap alais) {
		if (this.roleMaps != null)
			return this.roleMaps.add(alais);
		return false;
	}

	public RoleMap findPath(String mappingPath) {
		RoleMap result = null;
		for (RoleMap roleMap : roleMaps) {
			if (roleMap.getAllPathsWithInclude().contains(mappingPath)) {
				result = roleMap;
				break;
			}
		}
		return result;
	}

	public boolean existPath(String mappingPath) {
		return roleMaps.stream().anyMatch((roleMap) -> {
			return mappingPath != null && roleMap.getAllPathsWithInclude().contains(mappingPath);
		});
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleMaps other = (RoleMaps) obj;
		if (roleMaps == null) {
			if (other.roleMaps != null)
				return false;
		} else if (other.roleMaps != null
				&& (roleMaps.size() != other.roleMaps.size() || !roleMaps.containsAll(other.roleMaps)))
			return false;
		return true;
	}

}
