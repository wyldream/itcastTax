package core.permission.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import nsfw.role.entity.Role;
import nsfw.role.entity.RolePrivilege;
import nsfw.user.entity.User;
import nsfw.user.entity.UserRole;
import nsfw.user.service.UserService;
import core.permission.PermissionCheck;

public class PermissionCheckImpl implements PermissionCheck{

	@Resource
	private UserService userService;
	
	@Override
	public boolean isAccessible(User user, String code) {
		// TODO Auto-generated method stub
		//用户、角色、权限都是多对多的关系
		//1、获取获取用户角色
		List<UserRole> list = user.getUserRoles();
		//list为空值，则取数据库中查询
		if(list == null){
			list = userService.getUserRolesByUserId(user.getId());
		}
		//2、根据用户角色权限与当前所需权限比较
		if(list != null&&list.size()>0){
			for(UserRole userRole : list){
				//获取角色
				Role role = userRole.getId().getRole();
				//获取角色中所有的权限
				Set<RolePrivilege> rolePrivilege = role.getRolePrivilege();
				for(RolePrivilege rp:rolePrivilege){
					if(code.equals(rp.getId().getCode())){
						return true;
					}
				}
			}
		}
        //遍历结束依然没有匹配，则不具有权限		
		return false;
	}
	
}
