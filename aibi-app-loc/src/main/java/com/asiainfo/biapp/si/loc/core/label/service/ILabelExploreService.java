
package com.asiainfo.biapp.si.loc.core.label.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelRule;
import com.asiainfo.biapp.si.loc.core.label.model.ExploreQueryParam;

/**
 * 
 * Title : ILabelExploreService
 * <p/>
 * Description : 标签探索相关
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 6.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年12月18日    tianxy3        Created</pre>
 * <p/>
 *
 * @author  tianxy3
 * @version 1.0.0.2017年12月18日
 */
public interface ILabelExploreService {
	
	/**
	 * Description:
	 *            根据计算规则获得总数量的SQL
	 * 
	 * @param ciLabelRuleList
	 *            客户群规则list

	 * @return
	 * 成功</p>
	 * 得到总数量的sql</p>
	 * 失败</p>
	 */
	public String getCountSqlStr(List<LabelRule> ciLabelRuleList,ExploreQueryParam queryParam)
			throws BaseException;

}
