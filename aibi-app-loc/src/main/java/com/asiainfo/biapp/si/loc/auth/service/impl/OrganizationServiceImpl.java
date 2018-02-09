
package com.asiainfo.biapp.si.loc.auth.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.asiainfo.biapp.si.loc.auth.model.Organization;
import com.asiainfo.biapp.si.loc.auth.service.IOrganizationService;
import com.asiainfo.biapp.si.loc.auth.service.IUserService;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.JauthServerException;
import com.asiainfo.biapp.si.loc.base.utils.HttpUtil;
import com.asiainfo.biapp.si.loc.base.utils.JsonUtil;

/**
 * 
 * Title : IOrganizationService
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年2月6日    Administrator        Created</pre>
 * <p/>
 *
 * @author  组织数据对外输出接口
 * @version 1.0.0.2018年2月6日
 */
@Service("organizationService")
public class OrganizationServiceImpl implements IOrganizationService {
	
	
	@Value("${jauth-url}")  
    private String jauthUrl;
	
	@Autowired
	private IUserService userService; 
	
	private static final String ORG_ROOT_CODE = "999";
	
	/**
	 * 
	 * {@inheritDoc}
	 * @see com.asiainfo.biapp.si.loc.auth.service.IOrganizationService#selectOrganizationByCode(java.lang.String)
	 */
	public Organization selectOrganizationByCode(String orgCode) throws BaseException{
		Organization organization = null;
		try {
			organization = selectAllOrganization().get(orgCode);
		} catch (BaseException baseException) {
			throw baseException;
		}
		return organization;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * @see com.asiainfo.biapp.si.loc.auth.service.IOrganizationService#selectAllOrganization()
	 */
	//@Cacheable(value="Organization", key="'selectAllOrganization'")
	public Map<String,Organization> selectAllOrganization() throws BaseException{
		Map<String,Organization> result =  new HashMap<String,Organization>();
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("token", userService.getSysToken().getToken());
			params.put("orgCode", ORG_ROOT_CODE);
			String dataJsonXzqh = HttpUtil.sendPost(jauthUrl+"/api/organization/get", params);
			Organization organization = (Organization) JsonUtil.json2CollectionBean(dataJsonXzqh, Organization.class);
			result.put(organization.getOrgCode(), organization);
			putOrgcode(organization.getChildren(), result);
		} catch (BaseException baseException) {
			throw baseException;
		} catch (Exception e) {
			throw new JauthServerException("查询全量组织失败");
		}
		return result;
	}
	
	/** （私有）递归调用，将子机构放入 */
	private Map<String,Organization> putOrgcode(Set<Organization> children,Map<String,Organization> result){
		for(Organization organization : children){
				result.put(organization.getOrgCode(), organization);
				if(organization.getChildren() != null){
					putOrgcode(organization.getChildren(), result);
				}
		}
		return result;
	}

}
