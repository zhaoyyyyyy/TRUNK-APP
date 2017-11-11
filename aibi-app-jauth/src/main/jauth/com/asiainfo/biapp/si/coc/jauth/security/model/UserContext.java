package com.asiainfo.biapp.si.coc.jauth.security.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author vladimir.stankovic
 *
 * Aug 4, 2016
 */
public class UserContext {
    private final String username;
    private final String userId;
	private final List<GrantedAuthority> authorities;

    private UserContext(String userId,String username, List<GrantedAuthority> authorities) {
    	this.userId = userId;
    	this.username = username;
        this.authorities = authorities;
    }
    
    public static UserContext create(String userId,String username, List<GrantedAuthority> authorities) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("Username is blank: " + username);
        if (StringUtils.isBlank(userId)) throw new IllegalArgumentException("userId is blank: " + userId);
        return new UserContext(userId,username, authorities);
    }

    public String getUsername() {
        return username;
    }
    public String getUserId() {
		return userId;
	}
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
