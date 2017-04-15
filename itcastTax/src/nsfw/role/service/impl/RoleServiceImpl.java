package nsfw.role.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import nsfw.role.dao.RoleDao;
import nsfw.role.entity.Role;
import nsfw.role.service.RoleService;

import org.springframework.stereotype.Service;

import core.service.impl.BaseServiceImpl;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService{

	private RoleDao roleDao;
	
	@Resource
	public void setRoleDao(RoleDao roleDao) {
		super.setBaseDao(roleDao);//
		this.roleDao = roleDao;
	}
	
	public void update(Role role) {
		//1、删除该角色对于的所有权限
		roleDao.deleteRolePrivilegeByRoleId(role.getRoleId());
		//2、更新角色及其权限
		roleDao.update(role);
	}
/*	@Override
	public void save(Role role) {
		// TODO Auto-generated method stub
		roleDao.save(role);
	}

	*//**
	 * 更新时会保存之前的记录，以至于不会删除只会增加
	 * 所以在更新时要将之前的记录全部删除，再设置新的值
	 *//*
	@Override
	public void update(Role role) {//更新有变化
		// TODO Auto-generated method stub
		//1、删除该角色对于的所有权限
		roleDao.deleteRolePrivilegeByRoleId(role.getRoleId());
		//2、更新角色及其权限
		roleDao.update(role);
	}

	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		roleDao.delete(id);
	}

	@Override
	public Role findObjectById(Serializable id) {
		// TODO Auto-generated method stub
		
		return roleDao.findObjectById(id);
	}

	@Override
	public List<Role> findObjects() {
		// TODO Auto-generated method stub
		return roleDao.findObjects();
	}*/

}
