
package com.asiainfo.biapp.si.loc.auth.service;

import java.util.Map;

import com.asiainfo.biapp.si.loc.auth.model.Organization;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;

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
public interface IOrganizationService {
	
	/**
	 * 
	 * Description: 通过组织编码拿到组织对象
	 *
	 * @param orgCode
	 * @return
	 */
	public Organization selectOrganizationByCode(String orgCode) throws BaseException;
	
	/**
	 * 
	 * Description: 拿到所有组织对象
	 *
	 * @return Map<组织编码,组织>
	 */
	public Map<String,Organization> selectAllOrganization() throws BaseException;

}
