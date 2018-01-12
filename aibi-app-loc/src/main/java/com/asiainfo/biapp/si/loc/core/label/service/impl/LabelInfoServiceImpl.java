/*
 * @(#)LabelInfoServiceImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.task.CustomerListCreaterThread;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.ThreadPool;
import com.asiainfo.biapp.si.loc.core.dimtable.dao.IDimTableInfoDao;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;
import com.asiainfo.biapp.si.loc.core.dimtable.service.IDimTableInfoService;
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelInfoDao;
import com.asiainfo.biapp.si.loc.core.label.dao.IMdaSysTableDao;
import com.asiainfo.biapp.si.loc.core.label.entity.ApproveInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelCountRules;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelExtInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelRule;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTable;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.model.CustomRunModel;
import com.asiainfo.biapp.si.loc.core.label.model.ExploreQueryParam;
import com.asiainfo.biapp.si.loc.core.label.service.IApproveInfoService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelCountRulesService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelExtInfoService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelRuleService;
import com.asiainfo.biapp.si.loc.core.label.service.IMdaSysTableColService;
import com.asiainfo.biapp.si.loc.core.label.service.IMdaSysTableService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelInfoVo;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelRuleVo;

/**
 * Title : LabelInfoServiceImpl
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月16日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月16日
 */
@Service
@Transactional
public class LabelInfoServiceImpl extends BaseServiceImpl<LabelInfo, String> implements ILabelInfoService,ApplicationContextAware {

    @Autowired
    private ILabelInfoDao iLabelInfoDao;
    
    @Autowired
    private IMdaSysTableDao iMdaSysTableDao;
    
    @Autowired
    private IDimTableInfoDao iDimTableInfoDao;
    
    @Autowired 
    private IApproveInfoService iApproveInfoService;
    
    @Autowired 
    private ILabelCountRulesService iLabelCountRulesService;
    
    @Autowired
    private ILabelExtInfoService iLabelExtInfoService;
    
    @Autowired 
    private IDimTableInfoService iDimTableInfoService;
    
    @Autowired
    private IMdaSysTableService iMdaSysTableService;
    
    @Autowired
    private IMdaSysTableColService iMdaSysTableColService;
    
    @Autowired
    private ILabelRuleService ruleService;
    
	private static ApplicationContext context;  
    
    @Override
    protected BaseDao<LabelInfo, String> getBaseDao() {
        return iLabelInfoDao;
    }

    public Page<LabelInfo> selectLabelInfoPageList(Page<LabelInfo> page, LabelInfoVo labelInfoVo) {
        return iLabelInfoDao.selectLabelInfoPageList(page, labelInfoVo);
    }

    public List<LabelInfo> selectLabelInfoList(LabelInfoVo labelInfoVo) {
        return iLabelInfoDao.selectLabelInfoList(labelInfoVo);
    }

    public LabelInfo selectLabelInfoById(String labelId) throws BaseException {
        if (StringUtils.isBlank(labelId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(labelId);
    }

    public void addLabelInfo(LabelInfo labelInfo) throws BaseException {
        //封装标签规则维表信息
        LabelCountRules labelCountRules = new LabelCountRules();
        labelCountRules.setDependIndex(labelInfo.getDependIndex());
        labelCountRules.setCountRules(labelInfo.getCountRules());
        iLabelCountRulesService.addLabelCountRules(labelCountRules);        
        
        //封装标签信息
        labelInfo.setCreateTime(new Date());
        labelInfo.setDataStatusId(1);
        labelInfo.setGroupType(0);
        labelInfo.setCategoryId(labelInfo.getCategoryId());
        labelInfo.setCountRulesCode(labelCountRules.getCountRulesCode());
        super.saveOrUpdate(labelInfo);
        
        //封装审批信息
        ApproveInfo approveInfo = new ApproveInfo();
        approveInfo.setResourceId(labelInfo.getLabelId());
        approveInfo.setApproveStatusId("1");
        approveInfo.setApproveUserId(labelInfo.getCreateUserId());
        labelInfo.setApproveInfo(approveInfo); 
        iApproveInfoService.addApproveInfo(approveInfo);
        
        //封装标签扩展信息
        LabelExtInfo labelExtInfo = new LabelExtInfo();
        labelExtInfo.setLabelId(labelInfo.getLabelId());
        labelExtInfo.setOriginalTableType(labelInfo.getSourceTableType());
        iLabelExtInfoService.addLabelExtInfo(labelExtInfo);
        
        //封装元数据表列信息
        MdaSysTableColumn mdaSysTableColumn = new MdaSysTableColumn();
        mdaSysTableColumn.setLabelId(labelInfo.getLabelId());
    	MdaSysTable mdaSysTable = iMdaSysTableService.queryMdaSysTable(labelInfo.getConfigId(),labelInfo.getUpdateCycle(),1);
    	mdaSysTableColumn.setTableId(mdaSysTable.getTableId());
        mdaSysTableColumn.setColumnName(labelInfo.getLabelId());
        mdaSysTableColumn.setColumnCnName(labelInfo.getLabelName());
        if (StringUtil.isNotBlank(labelInfo.getDimId())) {
            DimTableInfo dimTable =iDimTableInfoService.selectDimTableInfoById(labelInfo.getDimId());
            mdaSysTableColumn.setDimTransId(labelInfo.getDimId());
            mdaSysTableColumn.setDataType(labelInfo.getDataType());
            int columnDataTypeId = Integer.parseInt(dimTable.getCodeColType());
            mdaSysTableColumn.setColumnDataTypeId(columnDataTypeId);
        }
        mdaSysTableColumn.setUnit(labelInfo.getUnit());
        mdaSysTableColumn.setColumnStatus(1);
        iMdaSysTableColService.addMdaSysTableColumn(mdaSysTableColumn);
    }

    public void modifyLabelInfo(LabelInfo labelInfo) throws BaseException {
        if (labelInfo.getDataStatusId()==4 ||labelInfo.getDataStatusId()==5||labelInfo.getDataStatusId()==6) {
            labelInfo.setFailTime(new Date());
        }else if (labelInfo.getDataStatusId()==2) {
            labelInfo.setEffecTime(new Date());
        }
        super.saveOrUpdate(labelInfo);
        
        //修改标签规则维表信息
        LabelCountRules labelCountRules = iLabelCountRulesService.selectLabelCountRulesById(labelInfo.getCountRulesCode());
        if (StringUtil.isNotBlank(labelInfo.getDependIndex())) {
            labelCountRules.setDependIndex(labelInfo.getDependIndex());
        }
        if (StringUtil.isNotBlank(labelInfo.getCountRules())) {
            labelCountRules.setCountRules(labelInfo.getCountRules());
        }
        iLabelCountRulesService.modifyLabelCountRules(labelCountRules);
        
        //修改元数据表列信息
        MdaSysTableColumn mdaSysTableColumn = iMdaSysTableColService.selectMdaSysTableColBylabelId(labelInfo.getLabelId());
        if (StringUtil.isNotBlank(labelInfo.getDimId())) {
            DimTableInfo dimTable = iDimTableInfoService.selectDimTableInfoById(labelInfo.getDimId());
            mdaSysTableColumn.setDimTransId(labelInfo.getDimId());
            mdaSysTableColumn.setDataType(labelInfo.getDataType());
            int columnDataTypeId = Integer.parseInt(dimTable.getCodeColType());
            mdaSysTableColumn.setColumnDataTypeId(columnDataTypeId);
        }
        if (StringUtil.isNotBlank(labelInfo.getUnit())) {
            mdaSysTableColumn.setUnit(labelInfo.getUnit());
        }
        iMdaSysTableColService.modifyMdaSysTableColumn(mdaSysTableColumn);
    }

    public void deleteLabelInfo(String labelId) throws BaseException {
        if (StringUtils.isBlank(labelId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        if (selectLabelInfoById(labelId)==null){
            throw new ParamRequiredException("ID不存在");
        }
        super.delete(labelId);
        iApproveInfoService.deleteApproveInfo(labelId);
    }

    @Override
    public LabelInfo selectOneByLabelName(String labelName) throws BaseException {
        if (StringUtils.isBlank(labelName)) {
            throw new ParamRequiredException("名称不许为空");
        }
        return iLabelInfoDao.selectOneByLabelName(labelName);
    }

    @Override
    public String selectDimNameBylabelId(String labelId) throws BaseException{
        LabelInfo labelInfo = iLabelInfoDao.get(labelId);
        MdaSysTableColumn mdaSysTableColumn = labelInfo.getMdaSysTableColumn();
        DimTableInfo dimTableInfo = iDimTableInfoDao.get(mdaSysTableColumn.getDimTransId());
        String dimTableName = dimTableInfo.getDimTableName();
        if (StringUtil.isBlank(dimTableName)) {
            throw new ParamRequiredException("找不到对应的维表名");
        }
        return dimTableName;
    }

	@Override
	public void saveCustomerLabelInfo(LabelExtInfo labelExtInfo, LabelInfo labelInfo, List<LabelRuleVo> labelRuleList,
			ExploreQueryParam queryParam) throws BaseException {
		// 基本信息
		super.saveOrUpdate(labelInfo);
		String customId = labelInfo.getLabelId();
		labelExtInfo.setLabelId(customId);
		iLabelExtInfoService.addLabelExtInfo(labelExtInfo);
		// 元数据列
		MdaSysTable mdaSysTable = iMdaSysTableService.queryMdaSysTable(labelInfo.getConfigId(),
				labelInfo.getUpdateCycle(),2);
		MdaSysTableColumn mdaSysTableColumn = new MdaSysTableColumn();
		mdaSysTableColumn.setLabelId(labelInfo.getLabelId());
		mdaSysTableColumn.setTableId(mdaSysTable.getTableId());
		mdaSysTableColumn.setColumnName(labelInfo.getLabelId());
		mdaSysTableColumn.setColumnCnName(labelInfo.getLabelName());
		mdaSysTableColumn.setColumnStatus(1);
		iMdaSysTableColService.addMdaSysTableColumn(mdaSysTableColumn);
		// 保存标签规则
		for (LabelRuleVo labelRuleVo : labelRuleList) {
			LabelRule labelRule = new LabelRule();
			try {
				BeanUtils.copyProperties(labelRule, labelRuleVo);
			} catch (Exception e) {
			}
			labelRule.setCustomId(customId);
			ruleService.addLabelRule(labelRule);
		}
		//生成清单
		CustomerListCreaterThread creator = null;
		try {
			creator = (CustomerListCreaterThread) context.getBean("customerListCreaterThread");
			creator.setCustomRunModel( new CustomRunModel(customId));
			ThreadPool.getInstance().execute(creator, false);
		} catch (Exception e) {
			LogUtil.error("线程池异常", e);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		LabelInfoServiceImpl.context = applicationContext; 
		
	}
}
