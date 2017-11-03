package com.asiainfo.biapp.si.loc.auth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.auth.service.UserService;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;

import io.swagger.annotations.Api;

@Api(value = "用户管理")
@RequestMapping("api")
@RestController
public class UserController extends BaseController<User>{
	
	@Autowired
	private UserService userService;
	/**
	 * 
	 * @param userName
	 * @param password
	 */
	@RequestMapping(value="/login", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
	public Map<String,Object> login(String userName,String password){
		
		String token = null;
		try {
			token = userService.getTokenByUsernamePassword(userName, password);
		} catch (BaseException e) {
			return WebResult.fail(e);
		}
		return WebResult.success("登录成功",token );
	}
}
