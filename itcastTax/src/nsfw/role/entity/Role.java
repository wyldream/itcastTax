package nsfw.role.entity;

import java.io.Serializable;
import java.util.Set;
/**
 * 角色
 * @author LBJ
 *
 */
/**
 * 对应主表
 * @author LBJ
 *
 */
public class Role implements Serializable{
	private String roleId;
	private String name;
	private String state;

	private Set<RolePrivilege> rolePrivilege;
	
	public static String ROLE_STATE_VALID = "1";
	public static String ROLE_STATE_INVALID = "0";
	
	
	
	public Role() {
	}
	
	
	
	public Role(String roleId) {
		this.roleId = roleId;
	}



	public Role(String roleId, String name, String state,
			Set<RolePrivilege> rolePrivilege) {
		this.roleId = roleId;
		this.name = name;
		this.state = state;
		this.rolePrivilege = rolePrivilege;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Set<RolePrivilege> getRolePrivilege() {
		return rolePrivilege;
	}
	public void setRolePrivilege(Set<RolePrivilege> rolePrivilege) {
		this.rolePrivilege = rolePrivilege;
	}

	
}
