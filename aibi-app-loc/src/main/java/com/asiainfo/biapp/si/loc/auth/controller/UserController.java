package com.asiainfo.biapp.si.loc.auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.auth.model.DicData;
import com.asiainfo.biapp.si.loc.auth.model.Organization;
import com.asiainfo.biapp.si.loc.auth.model.Resource;
import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.auth.service.IUserService;
import com.asiainfo.biapp.si.loc.auth.utils.TokenModel;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

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
	public WebResult<TokenModel> login(String username,String password){
		WebResult<TokenModel> webResult = new WebResult<TokenModel>();
		
		TokenModel token = null;
		try {
			token = userService.getTokenByUsernamePassword(username, password);
		} catch (BaseException e) {
			return webResult.fail(e);
		}
		return webResult.success("登录成功",token );
	}
	
	@ApiOperation(value = "查询用户组织访问权限跟同专区权限")
	@ApiImplicitParam(name = "token", value = "秘钥", required = false, paramType = "query", dataType = "string")
	@RequestMapping(value="/orgPrivaliege/query", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public WebResult<Map<String,List<Organization>>> findOrgPrivaliege(String token){
        WebResult<Map<String,List<Organization>>> webResult = new WebResult<>();
        User user = new User();
        try {
            user = userService.getUserByToken(token);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取用户组织访问权限跟同专区权限成功",user.getOrgPrivaliege());
    }
	
	@ApiOperation(value = "查询数据权限同行政区划权限")
    @ApiImplicitParam(name = "token", value = "秘钥", required = false, paramType = "query", dataType = "string")
    @RequestMapping(value="/dataPrivaliege/query", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public WebResult<Map<String,List<Organization>>> findDataPrivaliege(String token){
        WebResult<Map<String,List<Organization>>> webResult = new WebResult<>();
        User user = new User();
        try {
            user = userService.getUserByToken(token);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取数据权限同行政区划权限成功",user.getDataPrivaliege());
    }
	
	@ApiOperation(value = "查询api访问权限")
    @ApiImplicitParam(name = "token", value = "秘钥", required = false, paramType = "query", dataType = "string")
    @RequestMapping(value="/apiResource/query", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public WebResult<List<Resource>> findApiResource(String token){
        WebResult<List<Resource>> webResult = new WebResult<>();
        User user = new User();
        try {
            user = userService.getUserByToken(token);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("查询api访问权限成功",user.getApiResource());
    }
	
	@ApiOperation(value = "查询页面元素权限")
    @ApiImplicitParam(name = "token", value = "秘钥", required = false, paramType = "query", dataType = "string")
    @RequestMapping(value="/domResource/query", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public WebResult<List<Resource>> findDomResource(String token){
        WebResult<List<Resource>> webResult = new WebResult<>();
        User user = new User();
        try {
            user = userService.getUserByToken(token);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("查询页面元素权限成功",user.getDomResource());
    }
	
	@ApiOperation(value = "查询菜单权限")
    @ApiImplicitParam(name = "token", value = "秘钥", required = false, paramType = "query", dataType = "string")
    @RequestMapping(value="/menuResource/query", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public WebResult<List<Resource>> findMenuResource(String token){
        WebResult<List<Resource>> webResult = new WebResult<>();
        User user = new User();
        try {
            user = userService.getUserByToken(token);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("查询菜单权限成功",user.getMenuResource());
    }
}
