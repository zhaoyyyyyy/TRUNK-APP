
package com.asiainfo.biapp.si.loc.core.label.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.bd.list.entity.ListInfo;
import com.asiainfo.biapp.si.loc.bd.list.entity.ListInfoId;
import com.asiainfo.biapp.si.loc.bd.list.service.IListInfoService;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelExtInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.model.ExploreQueryParam;
import com.asiainfo.biapp.si.loc.core.label.service.ICustomerManagerService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelExploreService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelRuleService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelRuleVo;

@Service
@Transactional
public class CustomerManagerServiceImpl implements ICustomerManagerService {

	@Autowired
	private ILabelExploreService exploreServiceImpl;

	@Autowired
	private ILabelRuleService ruleService;

	@Autowired
	private ILabelInfoService labelInfoService;

	@Autowired
	private IBackSqlService backServiceImpl;
	
	@Autowired
	private IListInfoService listInfoService;
	
	@Override
	public boolean createCustomerList(String customId,ExploreQueryParam model) throws BaseException {
		LabelInfo customGroup = new LabelInfo();
		LabelExtInfo labelExtInfo = new LabelExtInfo() ;
		ListInfo listInfo=new ListInfo();
		try {
			// 1.获取sql
		    customGroup = labelInfoService.get(customId);
		    model.setOrgId(customGroup.getOrgId());// 权限
		    model.setCreateCustom(true);
		    model.setConfigId(customGroup.getConfigId());
			List<LabelRuleVo> labelRuleList = ruleService.queryCiLabelRuleList(customId,ServiceConstants.LabelRule.CUSTOM_TYPE_COSTOMER);
			String countSqlStr = "";
			if (haveCustomOrVerticalLabel(labelRuleList)) {
				countSqlStr = exploreServiceImpl.getFromSqlForMultiLabel(labelRuleList, model);
			} else {
				countSqlStr = exploreServiceImpl.getCountSqlStr(labelRuleList, model);
			}
			// 2.生成表插入数据
			String tableName = "no table";
			if (ServiceConstants.LabelInfo.UPDATE_CYCLE_O == customGroup.getUpdateCycle()) {
				tableName = ServiceConstants.KHQ_CROSS_ONCE_TABLE + customGroup.getConfigId() + "_"+ model.getDataDate();
			} else {
				tableName = ServiceConstants.KHQ_CROSS_TABLE + customGroup.getConfigId() + "_"+ model.getDataDate();
			}
			backServiceImpl.insertCustomerData(countSqlStr, tableName, customId,customGroup.getConfigId());
			// 3.发通知 setCustomNum
			String listTableSql = exploreServiceImpl.getListTableSql(customId, model.getDataDate());
			int customNum = backServiceImpl.queryCount("select count(1) from "+listTableSql);
			labelExtInfo = customGroup.getLabelExtInfo();
			labelExtInfo.setCustomNum(customNum);
			customGroup.setDataDate(model.getDataDate());
			customGroup.setDataStatusId(ServiceConstants.LabelInfo.DATA_STATUS_ID_G_SUCCESS);
			//4、处理清单表LOC_LIST_INFO  区分第一次
			ListInfoId id=new ListInfoId();
			id.setCustomGroupId(customId);
			id.setDataDate(model.getDataDate());
			listInfo.setListInfoId(id);
			listInfo.setCustomNum(customNum);
			listInfo.setDataStatus(ServiceConstants.LabelInfo.DATA_STATUS_ID_G_SUCCESS);
			listInfo.setDataTime(new Date());
			listInfoService.modifyListInfo(listInfo);
		} catch (Exception e) {
			customGroup.setDataStatusId(ServiceConstants.LabelInfo.DATA_STATUS_ID_G_FAILED);
			LogUtil.error("生成客户群的清单过程异常---customId:"+customId, e);
		} finally {
			labelInfoService.syncUpdateCustomGroupInfo(customGroup, labelExtInfo);
		}
		return true;
	}
	
	@Override
	public String validateLabelDataDate(String customId, String month, String day) throws BaseException {
		String result = null;
		boolean satisfyMonth = true;
		boolean satisfyDay = true;
		int monthNum = 0;
		int dayNum = 0;
		if (StringUtil.isNotEmpty(month)) {
			monthNum = Integer.valueOf(month);
		}
		if (StringUtil.isNotEmpty(day)) {
			dayNum = Integer.valueOf(day);
		}
		List<LabelRuleVo> labelRuleList = ruleService.queryCiLabelRuleList(customId,
		    ServiceConstants.LabelRule.CUSTOM_TYPE_COSTOMER);
		for (LabelRuleVo rule : labelRuleList) {
			int elementType = rule.getElementType();
			if (elementType == ServiceConstants.LabelRule.ELEMENT_TYPE_LABEL_ID) {
				String labelIdStr = rule.getCalcuElement();
				LabelInfo ciLabelInfo = CocCacheProxy.getCacheProxy().getLabelInfoById(labelIdStr);
				if(ciLabelInfo == null){
					LogUtil.error("标签失效 ciCustomGroupInfo："+ customId + ",labelId="+labelIdStr);
					return ServiceConstants.VALIDATE_RESULT_INVALID;
				}
				String dataDate = ciLabelInfo.getDataDate();
				if (ServiceConstants.LabelInfo.UPDATE_CYCLE_D == ciLabelInfo.getUpdateCycle()
						&& StringUtil.isNotEmpty(dataDate) && dayNum != 0) {
					int tempNum = Integer.valueOf(dataDate);
					if (tempNum < dayNum) {
						satisfyDay = false;
					}
				} else if (ServiceConstants.LabelInfo.UPDATE_CYCLE_M == ciLabelInfo.getUpdateCycle()
						&& StringUtil.isNotEmpty(dataDate) && monthNum != 0) {
					int tempNum = Integer.valueOf(dataDate);
					if (tempNum < monthNum) {
						satisfyMonth = false;
					}
				}
				if (!satisfyDay && !satisfyMonth) {
					break;
				}

			} // end LABEL
		}
		LabelInfo customGroup = labelInfoService.get(customId);
		String tactics = customGroup.getLabelExtInfo().getTacticsId();
		int updateCycle = customGroup.getUpdateCycle();
		LogUtil.info("周期：" + updateCycle + "  策略：" + tactics + "    ciCustomGroupInfo：" + customGroup.getLabelId()
				+ "     satisfyDay:=============" + satisfyDay + "     satisfyMonth:=============" + satisfyMonth);
		if (ServiceConstants.LabelInfo.UPDATE_CYCLE_O == updateCycle) { // 一次性
			if (ServiceConstants.LIST_TABLE_TACTICS_ID_ONE.equals(tactics)) {
				if (!satisfyDay) { // 日不满足
					result = ServiceConstants.VALIDATE_RESULT_NEW;
				} else {
					result = ServiceConstants.VALIDATE_RESULT_GO;
				}
			} else if (ServiceConstants.LIST_TABLE_TACTICS_ID_THREE.equals(tactics)) {
				result = ServiceConstants.VALIDATE_RESULT_GO;
			}
		} else if (ServiceConstants.LabelInfo.UPDATE_CYCLE_M == updateCycle) { // 月周期
			if (!satisfyMonth) { // 月不满足
				result = ServiceConstants.VALIDATE_RESULT_WAIT;
			} else if (satisfyMonth && !satisfyDay) { // 月满足，日不满足
				result = ServiceConstants.VALIDATE_RESULT_NEW;
			} else if (satisfyMonth && satisfyDay) { // 日、月都满足
				result = ServiceConstants.VALIDATE_RESULT_GO;
			}
		} else if (ServiceConstants.LabelInfo.UPDATE_CYCLE_D == updateCycle) { // 日周期
			if (!satisfyDay) {
				result = ServiceConstants.VALIDATE_RESULT_WAIT;
			} else {
				result = ServiceConstants.VALIDATE_RESULT_GO;
			}
		}
		return result;
	}
	
	/**
	 * 验证是否包含用户群或者纵表标签
	 * @param rules List<CiLabelRule> 处理过的规则
	 * @return
	 */
	private boolean haveCustomOrVerticalLabel(List<LabelRuleVo> rules) {
		boolean result = false;
		if(rules != null) {
			for (LabelRuleVo rule : rules) {
				if (rule.getElementType() == ServiceConstants.LabelRule.ELEMENT_TYPE_CUSTOM_RULES 
						|| rule.getElementType() == ServiceConstants.LabelRule.ELEMENT_TYPE_LIST_ID) {
					result = true;
					break;
				} else if (rule.getElementType() == ServiceConstants.LabelRule.ELEMENT_TYPE_LABEL_ID) {
					if (rule.getLabelTypeId() == ServiceConstants.LabelInfo.LABEL_TYPE_ID_VERT) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}

}
