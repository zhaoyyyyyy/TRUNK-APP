package com.asiainfo.biapp.si.loc.auth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.auth.model.Organization;
import com.asiainfo.biapp.si.loc.auth.model.Resource;
import com.asiainfo.biapp.si.loc.auth.model.TokenModel;
import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.auth.service.IUserService;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.exception.UserAuthException;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.HttpUtil;
import com.asiainfo.biapp.si.loc.base.utils.JsonUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 
 * Title : 用户相关业务实现层
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年11月7日    Administrator        Created</pre>
 * <p/>
 *
 * @author  zhougz3
 * @version 1.0.0.2017年11月7日
 */
@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, String> implements IUserService{

	@Value("${jauth-url}")  
    private String jauthUrl; 

	@Override
	protected BaseDao<User, String> getBaseDao() {
		return null;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * @see com.asiainfo.biapp.si.loc.auth.service.IUserService#getTokenByUsernamePassword(java.lang.String, java.lang.String)
	 */
	@Override
	public TokenModel getTokenByUsernamePassword(String username, String password) throws BaseException{
		if(StringUtil.isEmpty(username)){
			throw new ParamRequiredException("用户名不能为空");
		}
		if(StringUtil.isEmpty(password)){
			throw new ParamRequiredException("密码不能为空");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("username", username);
		map.put("password", password);
		
		try{
			String	tokenStr = HttpUtil.sendPost(jauthUrl+"/api/auth/login", map);
			JSONObject jsObject = JSONObject.fromObject(tokenStr);
			TokenModel tokenModel = new TokenModel();
			tokenModel.setToken(jsObject.getString("token"));
			tokenModel.setRefreshToken(jsObject.getString("refreshToken"));
			return tokenModel;
		}catch(Exception e){
			throw new UserAuthException("错误的用户名/密码");
			
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * @see com.asiainfo.biapp.si.loc.auth.service.IUserService#getUserByToken(java.lang.String)
	 */
	@Override
	public User getUserByToken(String token) throws BaseException{
		
		String username = null;
		String userId = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("token", token);
		
		//拿到用户名
		try{
			
			String tokenStr = HttpUtil.sendGet(jauthUrl+"/api/auth/me", params);
			JSONObject jsObject = JSONObject.fromObject(tokenStr);
			username = jsObject.getString("username");
			userId = jsObject.getString("userId");
		}catch(Exception e){
			throw new UserAuthException("无效的token");
		}
		User user = new User();
		user.setUserName(username);
		user.setUserId(userId);
		
		//拿到数据权限
				try{
					String dataJson = HttpUtil.sendGet(jauthUrl+"/api/auth/permission/data", params);
					List<Organization> organizationPrivaliege = (List<Organization>) JsonUtil.json2CollectionBean(dataJson, List.class, Organization.class);
					
					//组织权限
					Map<String,List<Organization>> orgPrivaliege = new HashMap<String,List<Organization>>();
					
					//数据权限
					Map<String,List<Organization>> dataPrivaliege = new HashMap<String,List<Organization>>();
					
					//通过组织类型来赋予用户的组织权限跟数据权限
					for(Organization organization : organizationPrivaliege){
						if("3".equals(organization.getOrgType())){
							String level = organization.getOrgCode().length()+"";
							if(dataPrivaliege.containsKey(level)){
								List<Organization> organizationList = dataPrivaliege.get(level);
								organizationList.add(organization);
								dataPrivaliege.put(level, organizationList);
							}else{
								List<Organization> organizationList = new ArrayList<Organization>();
								organizationList.add(organization);
								dataPrivaliege.put(level, organizationList);
							}
						}else{
							if(orgPrivaliege.containsKey(organization.getOrgType())){
								List<Organization> organizationList = orgPrivaliege.get(organization.getOrgType());
								organizationList.add(organization);
								orgPrivaliege.put(organization.getOrgType(), organizationList);
							}else{
								List<Organization> organizationList = new ArrayList<Organization>();
								organizationList.add(organization);
								orgPrivaliege.put(organization.getOrgType(), organizationList);
							}
						}
					}
					user.setOrgPrivaliege(orgPrivaliege);
		            user.setDataPrivaliege(dataPrivaliege);
				}catch(Exception e){
					throw new UserAuthException("获取用户数据权限失败",e);
				}
		
		//拿到资源权限
		try{
			List<Resource> domResource = new ArrayList<Resource>();
			List<Resource> menuResource = new ArrayList<Resource>();
			List<Resource> apiResource = new ArrayList<Resource>();
			String resourceJson = HttpUtil.sendGet(jauthUrl+"/api/auth/permission/resource", params);
			List<Resource> resourcePrivaliege = (List<Resource>) JsonUtil.json2CollectionBean(resourceJson, List.class, Resource.class);
			for(Resource resource : resourcePrivaliege){
				if(Resource.API.equals(resource.getParentId()) ){
					apiResource.add(resource);
				}else if(Resource.MENU.equals(resource.getParentId()) ){
					menuResource.add(resource);
				}else if(Resource.DOM.equals(resource.getParentId()) ){
					domResource.add(resource);
				}
			}
			user.setDomResource(domResource);
			user.setMenuResource(menuResource);
			user.setApiResource(apiResource);
		}catch(Exception e){
			throw new UserAuthException("获取用户资源权限失败",e);
		}
		
		return user;
	}

	
	
}
