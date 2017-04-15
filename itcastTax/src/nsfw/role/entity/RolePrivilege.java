package nsfw.role.entity;

import java.io.Serializable;
/**
 * 角色权限管理（角色权限表 中间表）
 * 
 * @author LBJ
 *
 */
public class RolePrivilege implements Serializable {
	//复合主键
	private RolePrivilegeId id;

	public RolePrivilegeId getId() {
		return id;
	}

	public void setId(RolePrivilegeId id) {
		this.id = id;
	}

	public RolePrivilege(RolePrivilegeId id) {
		this.id = id;
	}

	public RolePrivilege() {
	}
	
}
