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

import com.asiainfo.biapp.si.loc.base.exception.AuthException;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;

@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/*", filterName = "tokenAuthFilter")
public class TokenAuthFilter implements Filter {
	
	public static final String JWT_TOKEN_REQUSET_PARAM = "token";
	public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";
	public static final String JAUTH_ME_URL = "/api/me";
	
	
    Log log = LogFactory.getLog(TokenAuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	 //1.判断IP地址是否在白名单范围内
    	 //TODO
    	
         //2.拿到token（参数带着token 或者header带着X-Authorization ）
    	HttpServletRequest request = (HttpServletRequest)servletRequest;
    	String token = request.getParameter(JWT_TOKEN_REQUSET_PARAM);
    	if(StringUtil.isEmpty(token)){
    		token = request.getHeader(JWT_TOKEN_HEADER_PARAM);
    	}
    	if(StringUtil.isEmpty(token)){
    		throw new AuthException();
    	}
        
    	//2.拿到用户
    	
    	
    	//3.判断用户是否能够访问该地址接口权限
    	
    	
        //4 将用户及权限近期放入全局 kv缓存
        
        
        
        
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}