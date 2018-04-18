/*
 * @(#)LabelPushCycleServiceImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service.impl;

import java.io.File;
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
import com.asiainfo.biapp.si.loc.base.utils.ThreadPool;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelInfoVo;
import com.asiainfo.biapp.si.loc.core.syspush.common.constant.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.syspush.dao.ICustomDownloadRecordDao;
import com.asiainfo.biapp.si.loc.core.syspush.dao.ILabelPushCycleDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.CustomDownloadRecord;
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
    private ICustomDownloadRecordDao iCustomDownloadRecordDao;
    
    @Autowired
    private ILabelInfoService iLabelInfoService;
    
    @Autowired
    private ILabelAttrRelService iLabelAttrRelService;
    
    @Autowired
    private ILabelPushCycleService iLabelPushCycleService;
    
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
      	 //先判断此客户群的推送历史，如果有，修改status为失效
      	LabelAttrRelVo labelAttrRelVo = new LabelAttrRelVo();
      	labelAttrRelVo.setLabelId(labelPushCycle.getCustomGroupId());
      	labelAttrRelVo.setStatus(ServiceConstants.LabelAttrRel.STATUS_SUCCESS);
      	labelAttrRelVo.setAttrSettingType(ServiceConstants.LabelAttrRel.ATTR_SETTING_TYPE_PUSH);
      	if(iLabelAttrRelService.selectLabelAttrRelList(labelAttrRelVo) != null){
      		List<LabelAttrRel> labelAttrRelList=iLabelAttrRelService.selectLabelAttrRelList(labelAttrRelVo);
      		for(int i=0;i<labelAttrRelList.size();i++){
      			LabelAttrRel labelAttrRel =labelAttrRelList.get(i);
      			labelAttrRel.setStatus(ServiceConstants.LabelAttrRel.STATUS_FAILED);
      			iLabelAttrRelService.modifyLabelAttrRel(labelAttrRel);
      		}
      	}
      	//labelPushCycle和labelAttrRel存新数据
      	String[] sysIdsList = labelPushCycle.getSysIds().split(",");//推送的平台list
      	String[] attrbuteIdList = labelPushCycle.getAttrbuteId().split(",");//推送时附带的属性list
      	List<LabelPushCycle> lPCycles = new ArrayList<>();
      	for(int m=0;m<sysIdsList.length;m++){//遍历需要推送的平台
      		LabelPushCycleVo labelPushCycleVo = new LabelPushCycleVo();  //查看labelPushCycle表中 此次推送的客户群在此平台上的推送，如果存在，则修改status为0
      		labelPushCycleVo.setCustomGroupId(labelPushCycle.getCustomGroupId());
      		labelPushCycleVo.setStatus(ServiceConstants.LabelAttrRel.STATUS_SUCCESS);
      		labelPushCycleVo.setSysId(sysIdsList[m]);
          	if(iLabelPushCycleService.selectLabelPushCycleList(labelPushCycleVo)!= null){
          		List<LabelPushCycle> labelPushCycleList=iLabelPushCycleService.selectLabelPushCycleList(labelPushCycleVo);
          		for(int i=0;i<labelPushCycleList.size();i++){
          			LabelPushCycle labelPushCycles =labelPushCycleList.get(i);
          			labelPushCycles.setStatus(ServiceConstants.LabelAttrRel.STATUS_FAILED);
          			iLabelPushCycleService.modifyLabelPushCycle(labelPushCycles);
          		}
          	}
      		LabelPushCycle newLabelPushCycle =new LabelPushCycle(labelPushCycle.getCustomGroupId(),sysIdsList[m],labelPushCycle.getPushCycle(),new Date(),1);
      		super.saveOrUpdate(newLabelPushCycle);
      		lPCycles.add(newLabelPushCycle);
            for(int i=0;i<attrbuteIdList.length;i++){//遍历推送时附带的属性
                if(!("").equals(attrbuteIdList[i])){
                    LabelInfo labelInfo = iLabelInfoService.selectLabelInfoById(attrbuteIdList[i]);
                    LabelAttrRel labelAttrRel = new LabelAttrRel();
                    if(StringUtil.isNoneBlank(labelPushCycle.getSortAttrAndType())){
                   	 String[] sortAttrAndTypeList = labelPushCycle.getSortAttrAndType().split(";");
                   	 for(int j=0;j<sortAttrAndTypeList.length;j++){//遍历推送时各属性的排序及排序的类型
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
                    labelAttrRel.setRecordId(newLabelPushCycle.getRecordId());
                    labelAttrRel.setAttrCol("attr_col"+(i+1));
                    labelAttrRel.setStatus(ServiceConstants.LabelAttrRel.STATUS_SUCCESS);
                    labelAttrRel.setLabelId(labelPushCycle.getCustomGroupId());	
                    labelAttrRel.setModifyTime(new Date());
                    labelAttrRel.setAttrColName(labelInfo.getLabelName());
                    labelAttrRel.setAttrSource(ServiceConstants.LabelAttrRel.ATTR_SOURCE_LABEL);
                    labelAttrRel.setAttrSettingType(ServiceConstants.LabelAttrRel.ATTR_SETTING_TYPE_PUSH);
                    labelAttrRel.setLabelOrCustomId(labelInfo.getLabelId());
                    labelAttrRel.setAttrColType(labelInfo.getLabelTypeId().toString());
                    labelAttrRel.setAttrCreateUserId(userName);
                    labelAttrRel.setPageSortNum(i+1);
                    iLabelAttrRelService.addLabelAttrRel(labelAttrRel);
                }
            }
      	}
      	
        //推送
        ICustomerPublishThread curCustomerPublishThread = (ICustomerPublishThread) SpringContextHolder.getBean("customerPublishDefaultThread");
        curCustomerPublishThread.initParamter(lPCycles, false, new ArrayList<Map<String, Object>>());
//        Executors.newFixedThreadPool(10).execute(curCustomerPublishThread);
		ThreadPool.getInstance().execute(curCustomerPublishThread);
    
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
        String title = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey("LOC_CONFIG_APP_RELATED_COLUMN_CN_NAME");
        if (StringUtil.isEmpty(title)) {
            title = "手机号码";
        }
        col0.setAttrColName(title);
        attrRelList.add(0, col0);
        
        return attrRelList;
    }
    
    @Override
    public Page<Map<String, String>> findGroupList(Page<Map<String, String>> page, LabelInfoVo customGroup) throws BaseException {
        long s = System.currentTimeMillis();
    		//获取属性列
        List<LabelAttrRel> attrRelList = null;
        int attrType = ServiceConstants.LabelAttrRel.ATTR_SETTING_TYPE_PREVIEW;
        try {
            attrRelList = iCustomerPublishCommService.getLabelAttrRelsByCustom(customGroup, attrType);
        } catch (Exception e) {
            LogUtil.error("查询客户群关联的属性错误！", e);
        }
        
        String customListSql = iCustomerPublishCommService.getCustomListSql(customGroup, attrRelList, false);
        
        LogUtil.debug("清单预览sql："+customListSql);
        
        //清单数据
//        List<Map<String, String>> pageMap = iBackSqlService.queryForPage(customListSql, page.getPageStart(), page.getPageSize());
        List<Map<String, String>> pageMap = iBackSqlService.queryBySql(customListSql);
        
        String productNoHasPrivacy = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey(BaseConstants.PRODUCT_NO_HAS_PRIVACY);
        boolean isPrivate = true;	//是隐私的吗？
        if (StringUtil.isNotBlank(productNoHasPrivacy) && "true".equalsIgnoreCase(productNoHasPrivacy)) {
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
                        map.put(LabelInfoContants.KHQ_CROSS_COLUMN, productNo);
                    }
                }
            }
            page.setData(pageMap);
//            Integer count = iBackSqlService.queryCount(customListSql);
//			page.setTotalCount(count);
//			page.getTotalPageCount();
        }
        
        LogUtil.debug("find customListData cost:"+((System.currentTimeMillis()-s)/1000.0)+" s.");
        
        return page;
    }

    
    /**
     * Description:根据查询条件查询标签推送设置信息
     * @param LabelPushCycle 查询条件
     * @return List<LabelPushCycle>
     */
    public List<LabelPushCycle> queryLabelPushCycle(LabelPushCycleVo labelPushCycleVo) throws BaseException{
        return this.iLabelPushCycleDao.selectLabelPushCycle(labelPushCycleVo);
    }
    
    /**
     * Description:根据条件生成清单
     * @param LabelPushCycle 条件
     * @return 
     */
    @Override
    public void preDownloadGroupList(String localPathTmp, CustomDownloadRecord customDownloadRecord) throws BaseException{
        iCustomDownloadRecordDao.save(customDownloadRecord);
    }
    
    
}
