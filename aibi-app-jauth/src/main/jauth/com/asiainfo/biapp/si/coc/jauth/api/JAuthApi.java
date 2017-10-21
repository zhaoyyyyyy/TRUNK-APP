package com.asiainfo.biapp.si.coc.jauth.api;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.coc.jauth.security.auth.JwtAuthenticationToken;
import com.asiainfo.biapp.si.coc.jauth.security.auth.jwt.extractor.TokenExtractor;
import com.asiainfo.biapp.si.coc.jauth.security.auth.jwt.verifier.TokenVerifier;
import com.asiainfo.biapp.si.coc.jauth.security.config.JwtSettings;
import com.asiainfo.biapp.si.coc.jauth.security.config.WebSecurityConfig;
import com.asiainfo.biapp.si.coc.jauth.security.exceptions.InvalidJwtToken;
import com.asiainfo.biapp.si.coc.jauth.security.model.UserContext;
import com.asiainfo.biapp.si.coc.jauth.security.model.token.JwtToken;
import com.asiainfo.biapp.si.coc.jauth.security.model.token.JwtTokenFactory;
import com.asiainfo.biapp.si.coc.jauth.security.model.token.RawAccessJwtToken;
import com.asiainfo.biapp.si.coc.jauth.security.model.token.RefreshToken;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * End-point for retrieving logged-in user details.
 * 
 * @author vladimir.stankovic
 *
 * Aug 4, 2016
 */
@Api(value = "认证与授权接口",description = "用户登录,角色,资源等接口")
@RequestMapping("api/auth")
@RestController
public class JAuthApi {
	@Autowired private JwtTokenFactory tokenFactory;
	@Autowired private JwtSettings jwtSettings;
	@Autowired private UserService userService;
	@Autowired private TokenVerifier tokenVerifier;
	@Autowired @Qualifier("jwtHeaderTokenExtractor") private TokenExtractor tokenExtractor;
	
	
    @RequestMapping(value="/me", method=RequestMethod.GET)
    public @ResponseBody UserContext get(JwtAuthenticationToken token) {
        return (UserContext) token.getPrincipal();
    }
    
    
    @ApiOperation(value="通过用户名密码得到token", notes="通过用户名密码得到token,{\"username\":\"svlada@gmail.com\",\"password\": \"test1234\"}")
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public @ResponseBody JwtToken login(String loginRequest) {
        return null;
    }
    
    @RequestMapping(value="/token", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));
        
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtToken());

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
        User user = userService.getUserByName(subject);

        if (user.getRoleSet() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
        List<GrantedAuthority> authorities = user.getRoleSet().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRoleName()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getUserName(), authorities);

        return tokenFactory.createAccessJwtToken(userContext);
    }
}
