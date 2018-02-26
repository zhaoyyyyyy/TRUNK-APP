
package com.asiainfo.biapp.si.loc.core.label.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.base.common.LabelRuleContants;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.bd.list.service.IListInfoService;
import com.asiainfo.biapp.si.loc.bd.listinfo.entity.ListInfo;
import com.asiainfo.biapp.si.loc.bd.listinfo.entity.ListInfoId;
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
		LabelInfo customGroup = null;
		LabelExtInfo labelExtInfo = null ;
		ListInfo listInfo=new ListInfo();
		try {
			// 1.获取sql
		    customGroup = labelInfoService.get(customId);
		    model.setOrgId(customGroup.getOrgId());// 权限
			List<LabelRuleVo> labelRuleList = ruleService.queryCiLabelRuleList(customId,
					LabelRuleContants.LABEL_RULE_FROM_COSTOMER);
			String countSqlStr = exploreServiceImpl.getCountSqlStr(labelRuleList, model);
			// 2.生成表
			String tableName = "no table";
			if (LabelInfoContants.CUSTOM_CYCLE_TYPE_ONE == customGroup.getUpdateCycle()) {
				tableName = LabelInfoContants.KHQ_CROSS_ONCE_TABLE + customGroup.getConfigId() + "_"+ customGroup.getDataDate();
			} else {
				tableName = LabelInfoContants.KHQ_CROSS_TABLE + customGroup.getConfigId() + "_"+ customGroup.getDataDate();
			}
			backServiceImpl.insertCustomerData(countSqlStr, tableName, customId);
			// 3.发通知 setCustomNum
			int customNum = backServiceImpl.queryCount("select count(1) " + countSqlStr);
		    labelExtInfo = customGroup.getLabelExtInfo();
			labelExtInfo.setCustomNum(customNum);
			customGroup.setDataStatusId(LabelInfoContants.CUSTOM_DATA_STATUS_SUCCESS);
			//4、处理清单表LOC_LIST_INFO
			ListInfoId id=new ListInfoId();
			id.setCustomGroupId(customId);
			id.setDataDate(model.getDataDate());
			listInfo.setListInfoId(id);
			listInfo.setCustomNum(customNum);
			listInfo.setDataStatus(LabelInfoContants.CUSTOM_DATA_STATUS_SUCCESS);
			listInfo.setDataTime(new Date());
			listInfoService.addListInfo(listInfo);
		} catch (Exception e) {
			customGroup.setDataStatusId(LabelInfoContants.CUSTOM_DATA_STATUS_FAILED);
			LogUtil.error("生成客户群的清单过程异常", e);
		} finally {
			labelInfoService.syncUpdateCustomGroupInfo(customGroup, labelExtInfo);
		}
		return true;
	}

}
