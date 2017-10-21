
package com.asiainfo.biapp.si.coc.jauth.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asiainfo.biapp.si.coc.jauth.frame.json.JSONResult;
import com.asiainfo.biapp.si.coc.jauth.frame.util.WebResult;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Resource;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Role;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.UserService;
/**
 * 
 * Title : ApiAuthFilter
 * <p/>
 * Description : 权限控制开放API接口过滤器
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 6.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年9月19日    tianxy3        Created</pre>
 * <p/>
 *
 * @author  tianxy3
 * @version 1.0.0.2017年9月19日
 */
@Component("apiAuthFilter")
public class ApiAuthFilter implements Filter {

	private Logger log = Logger.getLogger(ApiAuthFilter.class);

	@Autowired
	private UserService userService;

	@Override
	public void destroy() {
		log.info("过滤器销毁");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String uri = request.getRequestURI();
		String queryString = request.getQueryString();
		/**
		 * 根据用户id查询角色，角色查询资源
		 */
		log.info(String.format("请求参数, uri: %s, params: %s", uri,  queryString));
//		// 1、判断是否是需要拦截的URL
		// 2、判断此用户是否有权限
		boolean flagAuth = false;
		String userId = request.getParameter("userId");
		if (userId == null) {
			flagAuth = true;
		} else {
			User user = userService.getUserByName(userId);
			if(user==null){
				// 返回异常信息
				responseMsg(response,"该用户在后台管理系统不存在！");
				return;
			}
			Set<Role> roleSet = user.getRoleSet();
			for (Role role : roleSet) {
				Set<Resource> resourceSet = role.getResourceSet();
				for (Resource resource : resourceSet) {
					String address = resource.getAddress();
					if (address != null && uri.indexOf(address) != -1) {
						flagAuth = true;
						break;
					}
				}
			}
		}
		if (flagAuth) {
			chain.doFilter(request, response);
		} else {
			// 返回异常信息
			responseMsg(response,"没有权限访问！");
		}

	}
	/**
	 * 
	 * Description: 返回异常信息
	 *
	 * @param response
	 *
	 * @author  tianxy3
	 * @date 2017年9月19日
	 */
	private void responseMsg(HttpServletResponse response,String msg) {
		//没有权限      、将实体对象转换为JSON Object转换
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(JSONResult.map2Json(WebResult.fail(msg)));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		log.info("权限控制开放API接口过滤器初始化");
	}

}
