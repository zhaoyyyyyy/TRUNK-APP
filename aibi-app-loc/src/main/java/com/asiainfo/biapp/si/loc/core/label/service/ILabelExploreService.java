
package com.asiainfo.biapp.si.loc.core.label.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.core.label.model.ExploreQueryParam;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelRuleVo;

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
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年12月18日    tianxy3        Created
 * </pre>
 * <p/>
 *
 * @author tianxy3
 * @version 1.0.0.2017年12月18日
 */
public interface ILabelExploreService {

	/**
	 * Description: 根据计算规则获得总数量的SQL
	 * 
	 * @param ciLabelRuleList
	 *            客户群规则list
	 * 
	 * @return 成功
	 *         </p>
	 *         得到总数量的sql
	 *         </p>
	 *         失败
	 *         </p>
	 */
	public String getCountSqlStr(List<LabelRuleVo> ciLabelRuleList, ExploreQueryParam queryParam) throws BaseException;

	/**
	 * 
	 * Description:  获取混合标签生成客户群运算的SQL
	 *
	 * @param ciLabelRuleList	混合标签规则
	 * @param queryParam
	 * @return
	 * @throws BaseException
	 *
	 * @author  tianxy3
	 * @date 2018年1月8日
	 */
	public String getFromSqlForMultiLabel(List<LabelRuleVo> labelRuleList, ExploreQueryParam queryParam) throws BaseException;
	
	/**
	 * 
	 * Description: 根据纵表标签返回其对应的sql
	 *
	 * @param verticalLabelRule	纵表标签规则
	 * @param queryParam
	 * @return
	 * @throws BaseException
	 *
	 * @author  tianxy3
	 * @date 2018年1月5日
	 */
	public String getVerticalLabelSql(LabelRuleVo verticalLabelRule, ExploreQueryParam queryParam)
			throws BaseException;
	/**
	 * Description: 获取客户群清单sql
	 * 				(FROM dw_khq_cross_once_p002_20180125 where custom_id='L0000071')
	 * @param customId
	 * @param dataDate
	 * @return
	 * @throws BaseException
	 *
	 * @author  tianxy3
	 * @date 2018年1月29日
	 */
	public String getListTableSql(String customId,String dataDate)throws BaseException;
	
}
