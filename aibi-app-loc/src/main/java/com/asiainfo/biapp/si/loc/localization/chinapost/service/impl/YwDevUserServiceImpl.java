package com.asiainfo.biapp.si.loc.localization.chinapost.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;

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
@Profile("cp-yw-dev")
@Service("userService")
@Transactional
public class YwDevUserServiceImpl extends DevUserServiceImpl implements IUserService{

	@Value("${jauth-url}")  
    private String jauthUrl; 

	@Value("${acrm-url}")  
    private String acrmUrl; 
	
	@Value("${loadLevel}")
	private Integer loadLevel;
	
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
            LogUtil.debug(e.getMessage());
        }
    }
	@Override
	public User getUserByToken(String token) throws BaseException{
//		return super.getUserByToken(token);
		User user = new User();
		String username = null;
		String userId = null;
		String serviceCode = "21";//TODO
		String districtId = "370000";//TODO
		
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
		//如果是系统
		if(userId.contains("sys_")){
		    return user;
		}
		//拿到用户名称
		try{
		    params.put("operatorId",userId);
//		    params.put("operatorId","2000001");//TODO
	        
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
	        serviceCode = orginfo.getString("SERVICE_CODE");//TODO
	        districtId = districtinfo.getString("DISTRICT_ID");//TODO
	        
	        user.setUserName(username);
        }catch(Exception e){
            throw new UserAuthException("无法通过OCRM获取用户ID["+userId+"]的信息");
        }
		
		//拿到数据权限
		try{
			
			Organization organizationXzqh = organizationService.selectOrganizationByCode("1");
			LinkedHashSet<Organization> organizationSetXzqh = new LinkedHashSet<>();
			organizationSetXzqh.add(organizationXzqh);
			addOrgChildren(organizationXzqh.getChildList(), organizationSetXzqh);
			
			Organization organizationYwx = organizationService.selectOrganizationByCode("2");
			LinkedHashSet<Organization> organizationSetYwx = new LinkedHashSet<>();
			organizationSetYwx.add(organizationYwx);
			addOrgChildren(organizationYwx.getChildList(), organizationSetYwx);
			
			LinkedHashSet<Organization> organizationPrivaliege = new LinkedHashSet<>();
			if(!organizationXzqh.getOrgCode().equals("1")){
			    organizationPrivaliege.add(organizationXzqh);
			}
            organizationPrivaliege.add(organizationYwx);
			
			//行政区划
			for(Organization organization : organizationSetXzqh){
			    if(organization.getOrgCode().equals(districtId)&&organization.getOrgType().equals("3")){
			        if(!organization.getOrgCode().equals("1")){
			            organizationPrivaliege.add(organization);
		            }
			        addXZQHOrgChildren(organization.getChildList(),organizationPrivaliege);
			    }
			}
			//业务线
			for(Organization organization : organizationSetYwx){
			    if(organization.getOrgCode().equals(serviceCode)&&organization.getOrgType().equals("1")){
                    organizationPrivaliege.add(organization);
                    addOrgChildren(organization.getChildList(), organizationPrivaliege);
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
			String menu = HttpUtil.sendPost(jauthUrl+"/api/resource/get", params);
			Resource resMenu = (Resource) JsonUtil.json2CollectionBean(menu, Resource.class);
			params.put("id","LOC_DOM");
			String dom = HttpUtil.sendPost(jauthUrl+"/api/resource/get", params);
			Resource resDom = (Resource) JsonUtil.json2CollectionBean(dom, Resource.class);
			Set<Resource> resourcePrivaliege = new HashSet<Resource>();
			resourcePrivaliege.addAll(resMenu.getChildren());
			resourcePrivaliege.addAll(resDom.getChildren());
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
	
	private Set<Organization> addOrgChildren(List<Organization> children,Set<Organization> organizationSet){
//	    organizationSet.addAll(children);
	    for(Organization o : children){
	        organizationSet.add(o);
	        if(!o.getChildren().isEmpty()){
	            addOrgChildren(o.getChildList(),organizationSet);
	        }
	    }
	    return organizationSet;
	}
	
	
	private Set<Organization> addXZQHOrgChildren(List<Organization> children,Set<Organization> organizationSet){
	    loadLevel--;
//	    organizationSet.addAll(children);
	    if(loadLevel>0){
	        for(Organization o : children){
	            organizationSet.add(o);
	            addOrgChildren(o.getChildList(),organizationSet);
	        }
	    }else{
	        for(Organization o : children){
                organizationSet.add(o);
            }
	    }
        return organizationSet;
    }

	
	
}
