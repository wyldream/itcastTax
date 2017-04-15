package login.action;

import java.util.List;

import javax.annotation.Resource;

import nsfw.user.entity.User;
import nsfw.user.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import core.constant.Constant;

public class LoginAction extends ActionSupport {
	
	@Resource
	private UserService userService;
	
	private User user;
	private String loginResult;
	//跳转到登录页面
	public String toLoginUI(){
		return "loginUI";
	}
	
	/*//登录
	public String login(){
		if(user != null){
			if(StringUtils.isNotBlank(user.getAccount()) && StringUtils.isNotBlank(user.getPassword()) ){
				//根据用户的帐号和密码查询用户列表
				List<User> list = userService.findUserByAccountAndPass(user.getAccount(), user.getPassword());
				if(list != null && list.size() > 0){//说明登录成功
					//2.1、登录成功
					User user = list.get(0);
					//2.1.1、根据用户id查询该用户的所有角色
					user.setUserRoles(userService.getUserRolesByUserId(user.getId()));//在另一张表中
					//2.1.2、将用户信息保存到session中
					ServletActionContext.getRequest().getSession().setAttribute(Constant.USER, user);
					//2.1.3、将用户登录记录到日志文件
					Log log = LogFactory.getLog(getClass());
					log.info("用户名称为：" + user.getName() + " 的用户登录了系统。");
					//2.1.4、重定向跳转到首页
					return "home";
				} else {//登录失败
					loginResult = "帐号或密码不正确！";
				}
			} else {
				loginResult = "帐号或密码不能为空！";
			}
		} else {
			loginResult = "请输入帐号和密码！";
		}
		return toLoginUI();
	}*/
	//登陆
	public String login(){
		//判断用户是否为空
		if(user != null){
			
			//判断用户名、密码是否为空
			if(StringUtils.isNotBlank(user.getAccount()) && StringUtils.isNotBlank(user.getPassword())){
				
				//根据用户名密码去数据库中查询
				List<User> list = userService.findUserByAccountAndPass(user.getAccount(), user.getPassword());
				//判断查询结果是否为空
				if(list != null && list.size() > 0){
					//获取user值
					User user = list.get(0);
					//根据查询结果的id查询角色关系（ 为权限检查做铺垫，避免每次访问权限验证时都要查询数据库 PermissionCheck）
					userService.getUserRolesByUserId(user.getId());
					//把查询结果保存到session中
					ActionContext.getContext().getSession().put(Constant.USER, user);
					//打印日志
					Log log = LogFactory.getLog(getClass());
					log.info("用户名称为：" + user.getName() + " 的用户登录了系统。");
					//重定向跳转到首页
					return "home";
				}else{
					loginResult = "用户名或密码错误";
				}
			}else{
				loginResult = "用户名或密码不能为空";
			}
		}
		return toLoginUI();
	}
	
	//退出，注销
	public String logout(){
		//清除session中保存的用户信息
		ServletActionContext.getRequest().getSession().removeAttribute(Constant.USER);
		return toLoginUI();
	}
	//跳转到没有权限提示页面
	public String toNoPermissionUI(){
		return "noPermissionUI";
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLoginResult() {
		return loginResult;
	}

	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}

}
