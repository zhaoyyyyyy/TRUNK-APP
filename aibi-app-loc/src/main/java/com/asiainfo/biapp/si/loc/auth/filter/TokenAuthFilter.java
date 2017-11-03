package com.asiainfo.biapp.si.loc.auth.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.auth.utils.AuthUtils;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;

@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/api/*", filterName = "tokenAuthFilter")
public class TokenAuthFilter implements Filter {
	
	
	
	
    Log log = LogFactory.getLog(TokenAuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	//1.请求JAUTH 判断IP地址是否在白名单范围内
    	
    	
    	//3.拿到用户信息
    	HttpServletRequest request = (HttpServletRequest)servletRequest;
    	
    	User user = null;
    	try {
    		user = AuthUtils.getLoginUser(request);
		} catch (BaseException e) {
			// TODO: handle exception
		}
    	if(user != null){
    		filterChain.doFilter(servletRequest,servletResponse);
    	}else{
    		//抛出异常
    	}
    	//4.判断用户是否能够访问该地址接口权限
    	
    	
        //5. 将用户及权限近期放入全局 kv缓存
        
    	filterChain.doFilter(servletRequest,servletResponse);
        
        
        
    }

    @Override
    public void destroy() {

    }
}