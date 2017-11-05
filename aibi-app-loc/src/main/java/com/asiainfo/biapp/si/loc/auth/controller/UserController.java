package com.asiainfo.biapp.si.loc.auth.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.auth.model.DicData;
import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.auth.service.IUserService;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Api(value = "用户权限相关接口")
@RequestMapping("api/user")
@RestController
public class UserController extends BaseController<User>{
	
	@Autowired
	private IUserService userService;
	/**
	 * 
	 * @param userName
	 * @param password
	 */
	@ApiImplicitParams({
		@ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "string") 
	})
	@RequestMapping(value="/login", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody WebResult<String> login(String username,String password){
		WebResult<String> webResult = new WebResult<String>();
		
		String token = null;
		try {
			token = userService.getTokenByUsernamePassword(username, password);
		} catch (BaseException e) {
			return webResult.fail(e);
		}
		return webResult.success("登录成功",token );
	}
}
