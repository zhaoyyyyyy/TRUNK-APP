/*
 * @(#)LabelPushCycleServiceImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.BaseConstants;
import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelInfoVo;
import com.asiainfo.biapp.si.loc.core.syspush.common.constant.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.syspush.dao.ILabelPushCycleDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushCycle;
import com.asiainfo.biapp.si.loc.core.syspush.service.ICustomerPublishCommService;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelAttrRelService;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelPushCycleService;
import com.asiainfo.biapp.si.loc.core.syspush.task.ICustomerPublishThread;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelAttrRelVo;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelPushCycleVo;

/**
 * Title : LabelPushCycleServiceImpl
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月17日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月17日
 */

@Service
@Transactional
public class LabelPushCycleServiceImpl extends BaseServiceImpl<LabelPushCycle, String> implements ILabelPushCycleService{

    @Autowired
    private ILabelPushCycleDao iLabelPushCycleDao;
    
    @Autowired
    private ILabelInfoService iLabelInfoService;
    
    @Autowired
    private ILabelAttrRelService iLabelAttrRelService;
    @Autowired
    private IBackSqlService iBackSqlService;
    
    @Autowired
    private ICustomerPublishCommService iCustomerPublishCommService;
    
    @Override
    protected BaseDao<LabelPushCycle, String> getBaseDao() {
        return iLabelPushCycleDao;
    }

    public Page<LabelPushCycle> selectLabelPushCyclePageList(Page<LabelPushCycle> page,
            LabelPushCycleVo labelPushCycleVo) throws BaseException {
        return iLabelPushCycleDao.selectLabelPushCyclePageList(page, labelPushCycleVo);
    }

    public List<LabelPushCycle> selectLabelPushCycleList(LabelPushCycleVo labelPushCycleVo) throws BaseException {
        return iLabelPushCycleDao.selectLabelPushCycleList(labelPushCycleVo);
    }

    public LabelPushCycle selectLabelPushCycleById(String recordId) throws BaseException {
        if(StringUtil.isBlank(recordId)){
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(recordId);
    }

    public void addLabelPushCycle(LabelPushCycle labelPushCycle,String userName) throws BaseException {
    	LabelAttrRelVo labelAttrRelVo = new LabelAttrRelVo();
    	labelAttrRelVo.setLabelId(labelPushCycle.getCustomGroupId());
    	labelAttrRelVo.setStatus(0);
    	labelAttrRelVo.setAttrSettingType(1);
    	if(iLabelAttrRelService.selectLabelAttrRelList(labelAttrRelVo) != null){
    		List<LabelAttrRel> labelAttrRelList=iLabelAttrRelService.selectLabelAttrRelList(labelAttrRelVo);
    		for(int i=0;i<labelAttrRelList.size();i++){
    			LabelAttrRel labelAttrRel =labelAttrRelList.get(i);
    			labelAttrRel.setStatus(1);
    			iLabelAttrRelService.modifyLabelAttrRel(labelAttrRel);
    		}
    	}
    	 String[] attrbuteIdList = labelPushCycle.getAttrbuteId().split(",");
             for(int i=0;i<attrbuteIdList.length;i++){
                 if(!("").equals(attrbuteIdList[i])){
                     LabelInfo labelInfo = iLabelInfoService.selectLabelInfoById(attrbuteIdList[i]);
                     LabelAttrRel labelAttrRel = new LabelAttrRel();
                     if(StringUtil.isNoneBlank(labelPushCycle.getSortAttrAndType())){
                    	 String[] sortAttrAndTypeList = labelPushCycle.getSortAttrAndType().split(";");
                    	 for(int j=0;j<sortAttrAndTypeList.length;j++){
                          	if(sortAttrAndTypeList[j].indexOf(labelInfo.getLabelName()) != -1){
                          		String attrSortType =sortAttrAndTypeList[j].split(",")[1];
                              	if(attrSortType.equals("升序")){
                              		labelAttrRel.setSortType("asc");
                              	}else if(attrSortType.equals("降序")){
                              		labelAttrRel.setSortType("desc");
                              	}
                              	labelAttrRel.setSortNum(j+1);
                          	}
                         }
                     }
                     labelAttrRel.setStatus(0);
                     labelAttrRel.setRecordId(labelPushCycle.getRecordId());
                     labelAttrRel.setLabelId(labelPushCycle.getCustomGroupId());	
                     labelAttrRel.setModifyTime(new Date());
                     labelAttrRel.setAttrColName(labelInfo.getLabelName());
                     labelAttrRel.setAttrSource(2);
                     labelAttrRel.setAttrSettingType(1);
                     labelAttrRel.setLabelOrCustomId(labelInfo.getLabelId());
                     labelAttrRel.setAttrColType(labelInfo.getLabelTypeId().toString());
                     labelAttrRel.setAttrCreateUserId(userName);
                     labelAttrRel.setPageSortNum(i+1);
                     iLabelAttrRelService.addLabelAttrRel(labelAttrRel);
                 }
             }
        super.saveOrUpdate(labelPushCycle);

        //推送
        ICustomerPublishThread curCustomerPublishThread = (ICustomerPublishThread) SpringContextHolder.getBean("customerPublishDefaultThread");
        List<LabelPushCycle> labelPushCycles = new ArrayList<>();
        labelPushCycles.add(labelPushCycle);
        curCustomerPublishThread.initParamter(labelPushCycles, false, new ArrayList<Map<String, Object>>());
        Executors.newFixedThreadPool(10).execute(curCustomerPublishThread);
    
    }

    public void modifyLabelPushCycle(LabelPushCycle labelPushCycle) throws BaseException {
        super.saveOrUpdate(labelPushCycle);
    }

    public void deleteLabelPushCycleById(String recordId) throws BaseException {
        if(StringUtil.isBlank(recordId)){
            throw new ParamRequiredException("ID不能为空");
        }
        super.delete(recordId);
    }
    
    public Integer deleteLabelPushCycle(LabelPushCycle labelPushCycle) throws BaseException{
        return this.iLabelPushCycleDao.deleteByLabelPushCycle(labelPushCycle);
    }

    @Override
    public List<LabelAttrRel> findGroupListCols(LabelInfo customInfo) throws BaseException{
        if(StringUtil.isBlank(customInfo.getLabelId())){
            throw new ParamRequiredException("标签ID不能为空");
        }
        
        List<LabelAttrRel> attrRelList = new ArrayList<>();
        
        //获取属性列
        int attrType = ServiceConstants.LabelAttrRel.ATTR_SETTING_TYPE_PREVIEW;
        attrRelList = iCustomerPublishCommService.getLabelAttrRelsByCustom(customInfo, attrType);

        //默认列
        LabelAttrRel col0 = new LabelAttrRel();
        col0.setAttrCol(LabelInfoContants.KHQ_CROSS_COLUMN);
        col0.setAttrColName("手机号");
        attrRelList.add(0, col0);
        
        return attrRelList;
    }
    
    @Override
    public Page<Map<String, String>> findGroupList(Page<Map<String, String>> page, LabelInfoVo customGroup) throws BaseException {
        //获取属性列
        List<LabelAttrRel> attrRelList = null;
        int attrType = ServiceConstants.LabelAttrRel.ATTR_SETTING_TYPE_PREVIEW;
        try {
            attrRelList = iCustomerPublishCommService.getLabelAttrRelsByCustom(customGroup, attrType);
        } catch (Exception e) {
            LogUtil.error("查询客户群关联的属性错误！", e);
        }
        
        String customListSql = iCustomerPublishCommService.getCustomListSql(customGroup, attrRelList);
        
        LogUtil.debug("清单预览sql："+customListSql);
        
        //清单数据
        List<Map<String, String>> pageMap = iBackSqlService.queryForPage(customListSql, page.getPageStart(), page.getPageSize());
        
        String productNoHasPrivacy = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey(BaseConstants.PRODUCT_NO_HAS_PRIVACY);
        boolean isPrivate = true;
        if (StringUtil.isBlank(productNoHasPrivacy) || "false".equalsIgnoreCase(productNoHasPrivacy)) {
            isPrivate = false;
        }
        if (null != pageMap) {
            for (Map<String, String> map : pageMap) {
                //对手机号做保密处理
                String productNo = map.get(LabelInfoContants.KHQ_CROSS_COLUMN);
                if (StringUtil.isNotBlank(productNo)) {
                    if (isPrivate) {
                        if (productNo.length() > 3) {
                            String start = productNo.substring(0, 3);
                            String end = productNo.substring(3, productNo.length());
                            if (end.length() >= 4) {
                                end = end.replaceFirst("[0-9]{4}", "****");
                            } else {
                                end = end.replaceFirst("[0-9]", "*");
                            }
                            productNo = start + end;
                        }
                    }
                }
                map.put(LabelInfoContants.KHQ_CROSS_COLUMN, productNo);
            }
        }
        
        return page;
    }


}
