/*
 * @(#)DimTableInfoServiceImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.dimtable.service.impl;

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
import com.asiainfo.biapp.si.loc.core.dimtable.dao.IDimTableInfoDao;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;
import com.asiainfo.biapp.si.loc.core.dimtable.service.IDimTableInfoService;
import com.asiainfo.biapp.si.loc.core.dimtable.vo.DimTableInfoVo;

/**
 * Title : DimTableInfoServiceImpl
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年11月27日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2017年11月27日
 */
@Service
@Transactional
public class DimTableInfoServiceImpl extends BaseServiceImpl<DimTableInfo, String> implements IDimTableInfoService{

    @Autowired
    private IDimTableInfoDao iDimTableInfoDao;
    
    @Override
    protected BaseDao<DimTableInfo, String> getBaseDao() {
        return iDimTableInfoDao;
    }
        
    public Page<DimTableInfo> selectDimTableInfoPageList(Page<DimTableInfo> page, DimTableInfoVo dimTableInfoVo)
            throws BaseException {
        return iDimTableInfoDao.selectDimTableInfoPageList(page, dimTableInfoVo);
    }

    public List<DimTableInfo> selectDimTableInfoList(DimTableInfoVo dimTableInfoVo) throws BaseException {
        return iDimTableInfoDao.selectDimTableInfoList(dimTableInfoVo);
    }

    public DimTableInfo selectDimTableInfoById(String dimId) throws BaseException {
        if(StringUtil.isBlank(dimId)){
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(dimId);
    }

    public void addDimTableInfo(DimTableInfo dimTableInfo) throws BaseException {
        super.saveOrUpdate(dimTableInfo);
    }

    public void modifyDimTableInfo(DimTableInfo dimTableInfo) throws BaseException {
        super.saveOrUpdate(dimTableInfo);
    }

    public void deleteDimTableInfoById(String dimId) throws BaseException {
        super.delete(dimId);
    }

}
