/**
 * 
 */
package com.openthinks.webscheduler.model.security;

import com.openthinks.easyweb.WebUtils;

/**
 * @author daile
 *
 */
public final class DefaultRole extends Role {
	private static final long serialVersionUID = -1464704598180655481L;
	
	private DefaultRole(){
		this.id="00000000-0000-0000-0000-000000000000";
		this.name="@default-role";
		this.desc="anonymous role for who without account";
	}
	
	private static final class DefaultRoleHolder{
		private static final DefaultRole sigltonRole = new DefaultRole();
	}

	public static final DefaultRole get(){
		return DefaultRoleHolder.sigltonRole;
	}
	
	/**
	 * 
	 * @param requestShortMapingPath String
	 * @return boolean
	 */
	public boolean addRoleMap(String requestShortMapingPath){
		String path=WebUtils.getFullRequestMapingPath(requestShortMapingPath);
		return get().getRoleMaps().add(RoleMap.valueOf(path));
	}
	
	@Override
	public void setDesc(String desc) {
	
	}
	
	@Override
	public void setId(String id) {
		
	}
	
	@Override
	public void setName(String name) {
		
	}
}
