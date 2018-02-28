package com.asiainfo.biapp.si.loc.localization.chinapost.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.auth.model.Organization;
import com.asiainfo.biapp.si.loc.auth.model.Resource;
import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.auth.service.IOrganizationService;
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
	
	@Autowired
    private IOrganizationService organizationService; 
	
	@Override
	protected BaseDao<User, String> getBaseDao() {
		return null;
	}
	

	/**
	 * 
	 * {@inheritDoc}
	 * @see com.asiainfo.biapp.si.loc.auth.service.IUserService#getUserByToken(java.lang.String)
	 */
	
	public static void main(String[] args) {
	    Map<String,Object> params = new HashMap<String,Object>();
        params.put("token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMDAwMDAxIiwic2NvcGVzIjpbIuaZrumAmueUqOaItyJdLCJ1c2VySWQiOiIyMDAwMDAxIiwiaXNzIjoiaHR0cDovL2FzaWFpbmZvLmNvbSIsImlhdCI6MTUxOTc5MTIzMCwiZXhwIjoxNTE5ODIwMDMwfQ.yImvQ18QJBg7lHmETTcif3f87WJoVJQ0QAUvZuLWGOitnS9NcD-LAUoDIUMVSq7X7nZ7zZZtiwT2_O3hkgmX2g");
        params.put("staffId", "2000001");
	    String acrmUrl = "http://127.0.0.1:8442/acrm";
        try {
            String tokenStr1 = HttpUtil.sendPost(acrmUrl+"/api/auth/userdata", params);
            System.out.println(tokenStr1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	@Override
	public User getUserByToken(String token) throws BaseException{
//		return super.getUserByToken(token);
		User user = new User();
		String username = null;
		String userId = null;
		String orgId = "21";
		String districtId = "370000";
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("token", token);
		//拿到用户ID
		try{
			String tokenStr = HttpUtil.sendGet(jauthUrl+"/api/auth/me", params);
			JSONObject jsObject = JSONObject.fromObject(tokenStr);
			userId = jsObject.getString("userId");
			user.setUserId(userId);
		}catch(Exception e){
			throw new UserAuthException("无效的token");
		}
		
		//拿到用户名称
		try{
		    params.put("staffId","2000001");
	        
	        String staffInfo = HttpUtil.sendPost(acrmUrl+"/api/auth/userinfo", params);
            JSONObject staffInfoObj = JSONObject.fromObject(staffInfo);
	        
            //从获取的信息中分出
            //用户信息
            JSONObject staffvalue = JSONObject.fromObject(staffInfoObj.getJSONObject("data").get("staffvalue"));
	        //组织信息
            JSONObject orginfo = JSONObject.fromObject(staffInfoObj.getJSONObject("data").get("orginfo"));
	        //区域信息
            JSONObject districtinfo = JSONObject.fromObject(staffInfoObj.getJSONObject("data").get("districtinfo"));
	        
	        
	        username = staffvalue.getString("STAFF_NAME");
//	        orgId = orginfo.getString("ORGANIZE_ID");
//	        districtId = districtinfo.getString("DISTRICT_ID");
	        
	        user.setUserName(username);
        }catch(Exception e){
            throw new UserAuthException("无法通过OCRM获取用户ID["+userId+"]的信息");
        }
		
		//拿到数据权限
		try{
			
			Organization organizationXzqh = organizationService.selectOrganizationByCode("1");
			Set<Organization> organizationSetXzqh = new HashSet<>();
			organizationSetXzqh.add(organizationXzqh);
			addOrgChildren(organizationXzqh.getChildren(), organizationSetXzqh);
			
			Organization organizationYwx = organizationService.selectOrganizationByCode("2");
			Set<Organization> organizationSetYwx = new HashSet<>();
			organizationSetYwx.add(organizationYwx);
			addOrgChildren(organizationYwx.getChildren(), organizationSetYwx);
			
			Set<Organization> organizationPrivaliege = new HashSet<Organization>();
			
//			organizationPrivaliege.add(organizationXzqh);
//			organizationPrivaliege.add(organizationYwx);
//			
//			organizationPrivaliege.addAll(organizationSetXzqh);
//			organizationPrivaliege.addAll(organizationSetYwx);
			
			for(Organization organization : organizationSetXzqh){
			    if(organization.getOrgCode().equals(districtId)){
			        organizationPrivaliege.add(organization);
			        addOrgChildren(organization.getChildren(), organizationPrivaliege);
			    }
			}
			for(Organization organization : organizationSetYwx){
			    if(organization.getOrgCode().equals(orgId)){
                    organizationPrivaliege.add(organization);
                    addOrgChildren(organization.getChildren(), organizationPrivaliege);
                }
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
			params.put("id","LOC_MENU");
			String resourceJson = HttpUtil.sendPost(jauthUrl+"/api/resource/get", params);
			Resource resAll = (Resource) JsonUtil.json2CollectionBean(resourceJson, Resource.class);
			Set<Resource> resourcePrivaliege = new HashSet<Resource>();
			resourcePrivaliege.addAll(resAll.getChildren());
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
			throw new UserAuthException("获取用户资源权限失败",e);
		}
		
		return user;
	}
	
	private Set<Organization> addOrgChildren(Set<Organization> children,Set<Organization> organizationSet){
	    for(Organization o : children){
	        organizationSet.add(o);
	        if(!o.getChildren().isEmpty()){
	            addOrgChildren(o.getChildren(),organizationSet);
	        }
	    }
	    return organizationSet;
	}

	
	
}
