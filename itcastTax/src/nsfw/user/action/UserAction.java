package nsfw.user.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import nsfw.role.service.RoleService;
import nsfw.user.entity.User;
import nsfw.user.entity.UserRole;
import nsfw.user.service.UserService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import core.exception.ServiceException;

public class UserAction extends ActionSupport{
	
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	
	private List<User> userList;
	private User user;
	private String[] selectedRow;
	private File headImg;
	private String headImgContentType;
	private String headImgFileName;
	
	private File userExcel;
	private String userExcelContentType;
	private String userExcelFileName;
	private String[] userRoleIds;
	
	//列表界面
	public String listUI(){
		try {
			userList = userService.findObjects();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "listUI";
	}
	//跳转到新增界面
	public String addUI(){
		ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
		return "addUI";
	}
	//保存新增
	public String add(){
		try {
			//处理头像上传
			if(user != null){
				if(headImg != null){//这里的headImg是从jsp页面传过来的文件
					//1、保存头像到upload/user文件夹中（需要保存地址和文件名）
					//获取保存路径的绝对地址
				    String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
				    //给文件一个唯一的名称  去除-的uuid+后缀名
				    String fileName = UUID.randomUUID().toString().replaceAll("-", "") + headImgFileName.substring(headImgFileName.lastIndexOf("."));
					//复制文件
				    FileUtils.copyFile(headImg, new File(filePath,fileName));
					//2、设置用户头像路径
				    user.setHeadImg("user/"+fileName);//user对象里的headImg是img保存的路径
				}
				userService.saveUserAndRole(user, userRoleIds);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "list";
	}
	public String editUI(){
		ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
		//跳转到修改界面,获得之前保存的用户展示出来
		if(user != null && user.getId() != ""){
			user = userService.findObjectById(user.getId());
			//处理角色回显(向userRoleIdS中填充数据)
			List<UserRole> list = userService.getUserRolesByUserId(user.getId());
			if(list != null && list.size() > 0){
				userRoleIds = new String[list.size()];
				for(int i = 0; i < list.size(); i++){
					//把RoleId设置到字符串数组中
					userRoleIds[i] = list.get(i).getId().getRole().getRoleId();
				}
			}
		}
		return "editUI";
	}
	//保存修改
	public String edit(){
		try {
			if(user != null){
				if(headImg != null){
					//处理头像
					//1、保存文件
					String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
					String fileName = UUID.randomUUID().toString().replaceAll("-", "") + headImgFileName.substring(headImgFileName.lastIndexOf("."));
					FileUtils.copyFile(headImg, new File(filePath, fileName));
					//2、设置文件路径（用于在jsp页面中访问图像）
					user.setHeadImg("user/"+fileName);
				}
				System.out.println(userRoleIds);
				userService.updateUserAndRole(user, userRoleIds);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "list";
	}
	//删除
	public String delete(){
		if(user != null && user.getId() != null){
			userService.delete(user.getId());
		}
		return "list";
	}
	//批量删除
	public String deleteSelected(){
		if(selectedRow != null ){//selectedRow中存储的是被选中的id
			for(String id:selectedRow){
				userService.delete(id);
			}
		}
		return "list";
	}
	//导出用户列表
	public void exportExcel(){
		try {
			//1查找用户列表
			userList = userService.findObjects();
			//2、导出
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/x-excel");
			//弹出下载框
			response.setHeader("Content-Disposition", "attachment;filename=" + new String("用户列表.xls".getBytes(), "ISO-8859-1"));
			ServletOutputStream outputStream = response.getOutputStream();
			userService.exportExcel(userList, outputStream);
			if(outputStream != null){
				outputStream.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	//导入用户列表
	public String importExcel(){
		if(userExcel != null){
			System.out.println("AA");
			if(userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
				userService.importExcel(userExcel, userExcelFileName);
			}
		}
		return "list";
	}
	
	//用户账号校验
	public void verifyAccount(){
		try {
			if(user != null && StringUtils.isNotBlank(user.getAccount())){//检查是否为空
				List<User> list = userService.findUserByAccountAndId(user.getAccount(),user.getId());
				String result = "true";
				if(list != null && list.size() > 0){//说明账号已经存在
					result = "false";
				}
				//获取输出流，输出
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(result.getBytes());
				outputStream.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	public File getHeadImg() {
		return headImg;
	}
	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}
	public String getHeadImgContentType() {
		return headImgContentType;
	}
	public void setHeadImgContentType(String headImgContentType) {
		this.headImgContentType = headImgContentType;
	}
	public String getHeadImgFileName() {
		return headImgFileName;
	}
	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}
	public File getUserExcel() {
		return userExcel;
	}
	public void setUserExcel(File userExcel) {
		this.userExcel = userExcel;
	}
	public String getUserExcelContentType() {
		return userExcelContentType;
	}
	public void setUserExcelContentType(String userExcelContentType) {
		this.userExcelContentType = userExcelContentType;
	}
	public String getUserExcelFileName() {
		return userExcelFileName;
	}
	public void setUserExcelFileName(String userExcelFileName) {
		this.userExcelFileName = userExcelFileName;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public String[] getUserRoleIds() {
		return userRoleIds;
	}
	public void setUserRoleIds(String[] userRoleIds) {
		this.userRoleIds = userRoleIds;
	}
}
