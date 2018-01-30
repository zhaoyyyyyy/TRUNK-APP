package com.asiainfo.biapp.si.loc.localization.chinapost.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.auth.model.Organization;
import com.asiainfo.biapp.si.loc.auth.model.Resource;
import com.asiainfo.biapp.si.loc.auth.model.TokenModel;
import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.auth.service.IUserService;
import com.asiainfo.biapp.si.loc.auth.service.impl.DevUserServiceImpl;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.UserAuthException;
import com.asiainfo.biapp.si.loc.base.utils.HttpUtil;
import com.asiainfo.biapp.si.loc.base.utils.JsonUtil;

import net.sf.json.JSONObject;

/**
 * 
 * Title : 中国邮政集团，邮政业务，用户相关业务实现层
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
@Profile("cp-yw")
@Service("userService")
@Transactional
public class YwUserServiceImpl extends DevUserServiceImpl implements IUserService{

	@Value("${jauth-url}")  
    private String jauthUrl; 

	@Value("${acrm-url}")  
    private String acrmUrl; 
	
	@Override
	protected BaseDao<User, String> getBaseDao() {
		return null;
	}
	
	/**
	 * 通过用户名拿到token
	 * 要经过现场的服务认证
	 * {@inheritDoc}
	 * @see com.asiainfo.biapp.si.loc.auth.service.impl.DevUserServiceImpl#getTokenByUsername(java.lang.String)
	 */
	@Override
	public TokenModel getTokenByUsername(String username) throws BaseException{
		return super.getTokenByUsername(username);
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
					params.put("orgCode", "1");
					String dataJsonXzqh = HttpUtil.sendPost(jauthUrl+"/api/organization/get", params);
					Organization organizationXzqh = (Organization) JsonUtil.json2CollectionBean(dataJsonXzqh, Organization.class);
					Set<Organization> organizationSetXzqh = organizationXzqh.getChildren();
					
					params.put("orgCode", "2");
					String dataJsonYwx = HttpUtil.sendPost(jauthUrl+"/api/organization/get", params);
					Organization organizationYwx = (Organization) JsonUtil.json2CollectionBean(dataJsonYwx, Organization.class);
					Set<Organization> organizationSetYwx = organizationYwx.getChildren();
					
					
					Set<Organization> organizationPrivaliege = new HashSet<Organization>();
					organizationPrivaliege.addAll(organizationSetXzqh);
					organizationPrivaliege.addAll(organizationSetYwx);
					
					for(Organization organization : organizationSetXzqh){
						organizationPrivaliege.addAll(organization.getChildren());
					}
					for(Organization organization : organizationSetYwx){
						organizationPrivaliege.addAll(organization.getChildren());
					}
					
					
					
					//组织权限
					Map<String,List<Organization>> orgPrivaliege = new HashMap<String,List<Organization>>();
					
					//数据权限
					Map<String,List<Organization>> dataPrivaliege = new HashMap<String,List<Organization>>();
					
					//通过组织类型来赋予用户的组织权限跟数据权限
					for(Organization organization : organizationPrivaliege){
						if("3".equals(organization.getOrgType())){
							String xzqh = organization.getOrgType();
							//organization.setLevel(2);
							if(dataPrivaliege.containsKey(xzqh)){
								List<Organization> organizationList = dataPrivaliege.get(xzqh);
								organizationList.add(organization);
								dataPrivaliege.put(xzqh, organizationList);
							}else{
								List<Organization> organizationList = new ArrayList<Organization>();
								organizationList.add(organization);
								dataPrivaliege.put(xzqh, organizationList);
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
			String resourceJson = HttpUtil.sendGet(jauthUrl+"/api/resource/parentResource/get", params);
			List<Resource> resourcePrivaliege = (List<Resource>) JsonUtil.json2CollectionBean(resourceJson, List.class, Resource.class);
			if(resourcePrivaliege != null && resourcePrivaliege.size() > 0){
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
			}
		}catch(Exception e){
			//throw new UserAuthException("获取用户资源权限失败",e);
		}
		
		return user;
	}

	
	
}
