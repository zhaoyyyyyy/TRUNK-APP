
package com.asiainfo.biapp.si.loc.core.label.service;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.core.label.model.ExploreQueryParam;

/**
 * 
 * Title : ICustomerManagerService
 * <p/>
 * Description : 客户群相关
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 6.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2018年2月26日    tianxy3        Created
 * </pre>
 * <p/>
 *
 * @author tianxy3
 * @version 1.0.0.2018年2月26日
 */
public interface ICustomerManagerService {
	/**
	 * 
	 * Description: 根据客户群id和日期创建清单
	 *
	 * @param customId
	 * @param dataDate
	 * @throws BaseException
	 *
	 * @author  tianxy3
	 * @date 2018年2月26日
	 */
	public boolean createCustomerList(String customId,ExploreQueryParam customRunModel) throws BaseException;

	
	/**
	 * 根据给定的月和日，判别规则是否都具备该月份或者该日的数据，根据不同清单生成策略，返回不同值
	 * 返回值：	1、等待创建，等下次日、月周期性定时调度调用；
	 * 			2、立即执行；
	 * 			3、不会出现在日、月调度中，单独的job夜间执行
	 * @param customId
	 * @param month yyyyMM
	 * @param day yyyyMMdd
	 * @return
	 * @version ZJ
	 */
	public String validateLabelDataDate(String customId, String month, String day) throws BaseException;
	
	
}
