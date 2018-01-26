/*
 * @(#)LabelPushCycleServiceImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.syspush.dao.ILabelPushCycleDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushCycle;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelAttrRelService;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelPushCycleService;
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
            LabelInfo labelInfo = iLabelInfoService.selectLabelInfoById(attrbuteIdList[i]);
            LabelAttrRel labelAttrRel = new LabelAttrRel();
            labelAttrRel.setRecordId(labelPushCycle.getRecordId());
            labelAttrRel.setLabelId(labelPushCycle.getCustomGroupId());
            labelAttrRel.setAttrCol(labelInfo.getLabelName());
            labelAttrRel.setAttrColType(labelInfo.getLabelTypeId().toString());
            iLabelAttrRelService.addLabelAttrRel(labelAttrRel);
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
}
