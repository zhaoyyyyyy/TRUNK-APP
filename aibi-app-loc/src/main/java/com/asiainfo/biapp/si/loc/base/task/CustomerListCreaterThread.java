
package com.asiainfo.biapp.si.loc.base.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.base.common.LabelRuleContants;
import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.bd.common.dao.IBackSqlDao;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.model.CustomRunModel;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelExploreService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelRuleService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelRuleVo;

/**
 * 
 * Title : CustomerListCreaterThread
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 6.0 +
 * <p/>
 * Modification History :生成一次性客户群的清单
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2018年1月12日    tianxy3        Created
 * </pre>
 * <p/>
 *
 * @author tianxy3
 * @version 1.0.0.2018年1月12日
 */
@Service
@Scope("prototype")
public class CustomerListCreaterThread extends Thread {

	private String customGroupId;// 客户群id

	private CustomRunModel customRunModel;

	@Autowired
	private ILabelExploreService exploreServiceImpl;

	@Autowired
	private ILabelRuleService ruleService;

	@Autowired
    private ILabelInfoService labelInfoService;
	
	@Autowired
	private IBackSqlService backServiceImpl;
	
	@Override
	public void run() {
		try {
			try {
				Thread.sleep(5000);//之前的事务提交需要一定时间
			} catch (InterruptedException e) {
				LogUtil.error("InterruptedException", e);
			}
			// 1.获取sql
			LabelInfo customGroup = labelInfoService.get(customGroupId);
			customRunModel.setOrgId(customGroup.getOrgId());//权限
			List<LabelRuleVo> labelRuleList = ruleService.queryCiLabelRuleList(customGroupId, LabelRuleContants.LABEL_RULE_FROM_COSTOMER);
			String countSqlStr = exploreServiceImpl.getCountSqlStr(labelRuleList, customRunModel);
			// 2.生成表
			String tableName="no table";
			if(LabelInfoContants.CUSTOM_CYCLE_TYPE_ONE==customGroup.getUpdateCycle()){
				 tableName=LabelInfoContants.KHQ_CROSS_ONCE_TABLE+customGroup.getConfigId()+"_"+customGroup.getDataDate();
			}else{
				 tableName=LabelInfoContants.KHQ_CROSS_TABLE+customGroup.getConfigId()+"_"+customGroup.getDataDate();
			}
	    	backServiceImpl.insertCustomerData(countSqlStr, tableName, customGroupId);
			// 3.发通知  TODO setCustomNum
			//int num = backServiceImpl.queryCount("select count(1) "+ countSqlStr);
	    	customGroup.setDataStatusId(LabelInfoContants.CUSTOM_DATA_STATUS_SUCCESS);
	    	labelInfoService.syncUpdateCustomGroupInfo(customGroup);
		} catch (Exception e) {
			LogUtil.error("生成客户群的清单异常", e);
		}
		

	}

	public void setCustomRunModel(CustomRunModel customRunModel) {
		this.customRunModel = customRunModel;
		this.customGroupId = customRunModel.getCustomGroupId();
		if (this.customGroupId == null) {
			throw new RuntimeException("customRunModel.customGroupId can not be null!" + customRunModel);
		}
	}

}
