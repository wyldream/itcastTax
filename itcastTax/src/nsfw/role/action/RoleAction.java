package nsfw.role.action;

import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import nsfw.role.entity.Role;
import nsfw.role.entity.RolePrivilege;
import nsfw.role.entity.RolePrivilegeId;
import nsfw.role.service.RoleService;

import com.opensymphony.xwork2.ActionContext;

import core.Action.BaseAction;
import core.constant.Constant;
import core.util.QueryHelper;

public class RoleAction extends BaseAction {
	@Resource
	private RoleService roleService;
	private List<Role> roleList;
	private Role role;
	private String[] privilegeIds;//接收前台传过来的选择的权限
	
	//列表页面
	public String listUI() throws Exception{
		//加载权限集合
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		//创建queryHelper对象，设置queryHelper参数（组装出一个查询语句）
		QueryHelper qh = new QueryHelper(Role.class, "i");
		try {
			if(role != null){
				if(StringUtils.isNotBlank(role.getName())){
					role.setName(URLDecoder.decode(role.getName(), "utf-8"));//解决回显时乱码问题
					qh.addCondition("i.name like ?", "%" + role.getName() + "%");
				}
			}
/*			//根据创建时间降序排序
			qh.addOrderByProperty("i.createTime", QueryHelper.ORDER_BY_DESC);*/
			//传入queryHelper参数查询
			//roleList = roleService.findObjects(qh);
			//roleList = roleService.findObjects();
			//查询分页数据，填充到pageResult中（当前页和页大小为手动设置）
			pageResult = roleService.getPageResult(qh, getPageNo(), getPageSize());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "listUI";
	}
	//跳转到新增页面
	public String addUI(){
		//加载权限集合
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		
		return "addUI";
	}
	//保存新增
	public String add(){
		try {
			if(role != null){
				//处理权限保存
				if(privilegeIds != null){
					HashSet<RolePrivilege> set = new HashSet<RolePrivilege>();
					for(int i = 0; i < privilegeIds.length; i++){
						//把选中的权限加载到set集合中，再设置到role中，通过级联修改保存到数据库中
						set.add(new RolePrivilege(new RolePrivilegeId(role, privilegeIds[i])));
					}
					role.setRolePrivilege(set);
				}
				roleService.save(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	//跳转到编辑页面
	public String editUI(){
		//加载权限集合
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		if (role != null && role.getRoleId() != null) {
			role = (Role) roleService.findObjectById(role.getRoleId());
			//处理权限回显
			if(role.getRolePrivilege() != null){
				//和add方法中的处理方式相反
				//从数据库中取得role对象，从role对象中取得保存的权限，再设置到字符串数组中，在前台迭代显示
				privilegeIds = new String[role.getRolePrivilege().size()];
				int i = 0;
				for(RolePrivilege rp: role.getRolePrivilege()){
					privilegeIds[i++] = rp.getId().getCode();
				}
			}
		}
		return "editUI";
	}
	//保存编辑
	public String edit(){
		try {
			if(role != null){
				//处理权限保存
				if(privilegeIds != null){
					HashSet<RolePrivilege> set = new HashSet<RolePrivilege>();
					for(int i = 0; i < privilegeIds.length; i++){
						set.add(new RolePrivilege(new RolePrivilegeId(role, privilegeIds[i])));
					}
					role.setRolePrivilege(set);
				}
				roleService.update(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	//删除
	public String delete(){
		if(role != null && role.getRoleId() != null){
			roleService.delete(role.getRoleId());
		}
		return "list";
	}
	//批量删除
	public String deleteSelected(){
		if(selectedRow != null){
			for(String id: selectedRow){
				roleService.delete(id);
			}
		}
		return "list";
	}
	
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String[] getPrivilegeIds() {
		return privilegeIds;
	}
	public void setPrivilegeIds(String[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
}
