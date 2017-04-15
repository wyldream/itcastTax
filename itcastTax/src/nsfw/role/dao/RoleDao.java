package nsfw.role.dao;

import nsfw.role.entity.Role;
import core.dao.BaseDao;

public interface RoleDao extends BaseDao<Role>{
	//删除该角色对应的所有权限
	public void deleteRolePrivilegeByRoleId(String roleId);
}
