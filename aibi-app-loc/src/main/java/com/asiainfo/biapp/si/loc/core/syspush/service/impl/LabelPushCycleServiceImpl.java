/*
 * @(#)LabelPushCycleServiceImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.BaseConstants;
import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelExploreService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelInfoVo;
import com.asiainfo.biapp.si.loc.core.syspush.dao.ILabelPushCycleDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushCycle;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelAttrRelService;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelPushCycleService;
import com.asiainfo.biapp.si.loc.core.syspush.vo.CustomGroupListVo;
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
    private ILabelExploreService iLabelExploreService;
    @Autowired
    private IBackSqlService iBackSqlService;
    
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

    public void addLabelPushCycle(LabelPushCycle labelPushCycle) throws BaseException {
        String[] attrbuteIdList = labelPushCycle.getAttrbuteId().split(",");
            for(int i=0;i<attrbuteIdList.length;i++){
                if(!("").equals(attrbuteIdList[i])){
                    LabelInfo labelInfo = iLabelInfoService.selectLabelInfoById(attrbuteIdList[i]);
                    LabelAttrRel labelAttrRel = new LabelAttrRel();
                    labelAttrRel.setRecordId(labelPushCycle.getRecordId());
                    labelAttrRel.setLabelId(labelPushCycle.getCustomGroupId());
                    labelAttrRel.setAttrCol(labelInfo.getLabelName());
                    labelAttrRel.setAttrColType(labelInfo.getLabelTypeId().toString());
                    iLabelAttrRelService.addLabelAttrRel(labelAttrRel);
                }
            }
        super.saveOrUpdate(labelPushCycle);
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
    

    public Page<CustomGroupListVo> findGroupList(Page<CustomGroupListVo> page, LabelInfoVo customGroup) throws BaseException {
        String sql = this.getGroupListSql(customGroup);
        LogUtil.debug("清单预览sql："+sql);
        //清单
        List<Map<String, String>> pageMap = iBackSqlService.queryForPage(sql, page.getPageStart(), page.getPageSize());
        String productNoHasPrivacy = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey(BaseConstants.PRODUCT_NO_HAS_PRIVACY);
        boolean isPrivate = true;
        if (StringUtil.isBlank(productNoHasPrivacy) || "false".equalsIgnoreCase(productNoHasPrivacy)) {
            isPrivate = false;
        }
        if (null != pageMap) {
            List<CustomGroupListVo> customGroupListVos = new ArrayList<>();
            for (Map<String, String> map : pageMap) {
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
                customGroupListVos.add(new CustomGroupListVo(productNo));
            }
            page.setData(customGroupListVos);
        }
        return page;
        
    }

    /** 拼接查询客户群清单预览sql
     * @param customGroup
     * @return
     * @throws BaseException
     */
    private String getGroupListSql(LabelInfoVo customGroup) throws BaseException {
        String fromSql = iLabelExploreService.getListTableSql(customGroup.getLabelId(), customGroup.getDataDate());
        
        StringBuilder sql = new StringBuilder("select ").append(LabelInfoContants.KHQ_CROSS_COLUMN).append(" FROM ");
        if (!fromSql.contains(".")) {
            sql.append(iBackSqlService.getCurBackDbSchema()).append(".");
        }
        
        return sql.append(fromSql).append(" order by ").append(LabelInfoContants.KHQ_CROSS_COLUMN).append(" asc").toString();
    }
    


}
