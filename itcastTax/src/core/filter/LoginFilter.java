package core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsfw.user.entity.User;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import core.constant.Constant;
import core.permission.PermissionCheck;



public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		String uri = request.getRequestURI();
		//判断访问的是否是登录界面
		if(!uri.contains("sys/login_")){
			
			//判断是否登陆
			if(request.getSession().getAttribute(Constant.USER) != null){
				if(uri.contains("/nsfw/")){					
					User user = (User)request.getSession().getAttribute(Constant.USER);//从session中获取登陆信息
					//获取spring容器(开始时创建)
					WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
					PermissionCheck pc = (PermissionCheck)applicationContext.getBean("permissionCheck");
					if(pc.isAccessible(user, "nsfw")){
						//说明有权限，放行
						chain.doFilter(request, response);
					} else {
						//没有权限，跳转到没有权限提示页面
						response.sendRedirect(request.getContextPath() + "/sys/login_toNoPermissionUI.action");
					}
				}else{
					chain.doFilter(request, response);
				}
			}else{
				//没有登陆跳转到登陆界面
				response.sendRedirect(request.getContextPath() + "/sys/login_toLoginUI.action");
			}
			
		}else{
			//访问登录界面，直接放行
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}


	

}
