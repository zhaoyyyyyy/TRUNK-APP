/*
 * @(#)DimOrgLevelServiceImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.back.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.core.back.dao.IDimOrgLevelDao;
import com.asiainfo.biapp.si.loc.core.back.entity.DimOrgLevel;
import com.asiainfo.biapp.si.loc.core.back.entity.DimOrgLevelId;
import com.asiainfo.biapp.si.loc.core.back.service.IDimOrgLevelService;
import com.asiainfo.biapp.si.loc.core.back.vo.DimOrgLevelVo;

/**
 * Title : DimOrgLevelServiceImpl
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2018年1月24日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2018年1月24日
 */
@Service
@Transactional
public class DimOrgLevelServiceImpl extends BaseServiceImpl<DimOrgLevel, String>implements IDimOrgLevelService {

    @Autowired
    private IDimOrgLevelDao iDimOrgLevelDao;

    @Override
    protected BaseDao<DimOrgLevel, String> getBaseDao() {
        return iDimOrgLevelDao;
    }

    public Page<DimOrgLevel> selectDimOrgLevelPageList(Page<DimOrgLevel> page, DimOrgLevelVo dimOrgLevelVo)
            throws BaseException {
        return iDimOrgLevelDao.selectDimOrgLevelPageList(page, dimOrgLevelVo);
    }

    public List<DimOrgLevel> selectDimOrgLevelList(DimOrgLevelVo dimOrgLevelVo) throws BaseException {
        return iDimOrgLevelDao.selectDimOrgLevelList(dimOrgLevelVo);
    }
    
    public DimOrgLevel selectDimOrgLevelById(DimOrgLevelId dimOrgLevelId) throws BaseException{
        return super.get(dimOrgLevelId);
    }

    public void addDimOrgLevel(DimOrgLevel dimOrgLevel) throws BaseException {
        DimOrgLevelVo dimOrgLevelVo = new DimOrgLevelVo();
        DimOrgLevelId dimOrgLevelId = new DimOrgLevelId();
        dimOrgLevelId.setPriKey(dimOrgLevel.getDimOrgLevelId().getPriKey());
        dimOrgLevelVo.setDimOrgLevelId(dimOrgLevelId);
        dimOrgLevelVo.setLevelId(dimOrgLevel.getLevelId());
        List<DimOrgLevel> dimOrgLevelList = selectDimOrgLevelList(dimOrgLevelVo);
        if(!dimOrgLevelList.isEmpty()){
            throw new ParamRequiredException("该层级已存在");
        }
        super.saveOrUpdate(dimOrgLevel);
    }

    public void modifyDimOrgLevel(DimOrgLevel dimOrgLevel) throws BaseException {
        super.saveOrUpdate(dimOrgLevel);
    }

    public void deleteDimOrgLevel(DimOrgLevelId dimOrgLevelId) throws BaseException {
        super.delete(dimOrgLevelId);
    }

}
