package com.asiainfo.biapp.si.loc.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.auth.model.Organization;
import com.asiainfo.biapp.si.loc.auth.model.Resource;
import com.asiainfo.biapp.si.loc.auth.model.TokenModel;
import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.auth.service.IUserService;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;

@Api(value = "001->-用户权限相关接口")
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
	@ApiOperation(value = "通过用户名密码登录")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "string") 
	})
	@RequestMapping(value="/login", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public WebResult<TokenModel> login(String username,String password){
		WebResult<TokenModel> webResult = new WebResult<>();
		
		TokenModel token = null;
		try {
			token = userService.getTokenByUsernamePassword(username, password);
		} catch (BaseException e) {
			return webResult.fail(e);
		}
		return webResult.success("登录成功",token );
	}
	
	
	/**
	 * 
	 * @param userName
	 * @param password
	 */
	@ApiOperation(value = "单纯的申请一个token")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "string"),
	})
	@RequestMapping(value="/applyToken", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public WebResult<TokenModel> applyToken(String username){
		WebResult<TokenModel> webResult = new WebResult<>();
		TokenModel token = null;
		try {
			token = userService.getTokenByUsername(username);
		} catch (BaseException e) {
			return webResult.fail(e);
		}
		return webResult.success("登录成功",token );
	}
	
	
	/**
	 * 通过token拿到当前登录用户
	 * Description: 
	 * @return
	 */
	@ApiOperation(value = "通过token拿到当前登录用户(建议使用)")
	@RequestMapping(value="/get", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public WebResult<User> get(){
		WebResult<User> webResult = new WebResult<>();
        User user;
        try {
            user = super.getLoginUser();
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取用户组织访问权限与同专区权限成功",user);
	}
	
	@ApiOperation(value = "通过token拿到当前登录用户的用户名")
    @RequestMapping(value="/getUserId", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public WebResult<String> getUserId(){
        WebResult<String> webResult = new WebResult<>();
        User user;
        try {
            user = super.getLoginUser();
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取用户组织访问权限跟同专区权限成功",user.getUserName());
    }
	
	
	@ApiOperation(value = "返回当前用户的组织访问权限跟同专区权限(不建议使用)")
	@RequestMapping(value="/privaliegeOrg/query", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public WebResult<Map<String,List<Organization>>> findOrgPrivaliege(){
        WebResult<Map<String,List<Organization>>> webResult = new WebResult<>();
        User user;
        try {
            user = super.getLoginUser();
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取用户组织访问权限跟同专区权限成功",user.getOrgPrivaliege());
    }
	
	@ApiOperation(value = "返回当前用户的数据权限同行政区划权限(不建议使用)")
    @RequestMapping(value="/privaliegeData/query", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public WebResult<Map<String,List<Organization>>> findDataPrivaliege(){
        WebResult<Map<String,List<Organization>>> webResult = new WebResult<>();
        User user;
        try {
            user = super.getLoginUser();
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取数据权限同行政区划权限成功",user.getDataPrivaliege());
    }
	
	@ApiOperation(value = "返回当前用户的api访问权限(不建议使用)")
    @RequestMapping(value="/resourceApi/query", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public WebResult<List<Resource>> findApiResource(){
        WebResult<List<Resource>> webResult = new WebResult<>();
        User user;
        try {
            user = super.getLoginUser();
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("查询api访问权限成功",user.getApiResource());
    }
	
	@ApiOperation(value = "返回当前用户的页面元素权限(不建议使用)")
    @RequestMapping(value="/resourceDom/query", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public WebResult<List<Resource>> findDomResource(){
        WebResult<List<Resource>> webResult = new WebResult<>();
        User user;
        try {
            user = super.getLoginUser();
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("查询页面元素权限成功",user.getDomResource());
    }
	
	@ApiOperation(value = "返回当前用户的页面元素权限")
    @RequestMapping(value="/resourceDom/queryDomCodeList", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public WebResult<List<String>> findDomResourceMap(){
        WebResult<List<String>> webResult = new WebResult<>();
        List<String> list = new ArrayList<String>();
        User user;
        try {
            user = super.getLoginUser();
            if(user.getDomResource()!=null && user.getDomResource().size()>0){
                for(Resource resource : user.getDomResource()){
                    list.add(resource.getResourceCode());
                }
            }
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("查询页面元素权限成功",list);
    }
	
	@ApiOperation(value = "查询菜单权限")
    @RequestMapping(value="/resourceMenu/query", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public WebResult<List<Resource>> findMenuResource(){
        WebResult<List<Resource>> webResult = new WebResult<>();
        User user;
        try {
            user = super.getLoginUser();
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("查询菜单权限成功",user.getMenuResource());
    }
}
