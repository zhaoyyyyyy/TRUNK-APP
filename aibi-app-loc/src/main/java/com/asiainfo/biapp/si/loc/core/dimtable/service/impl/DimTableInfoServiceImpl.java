/*
 * @(#)DimTableInfoServiceImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.dimtable.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
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
import com.asiainfo.biapp.si.loc.core.prefecture.entity.PreConfigInfo;
import com.asiainfo.biapp.si.loc.core.source.entity.SourceInfo;

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
    
    public DimTableInfo selectOneByDimTableName(String dimTableName) throws BaseException {
        if (StringUtils.isBlank(dimTableName)) {
            throw new ParamRequiredException("名称不能为空");
        }
        return iDimTableInfoDao.selectOneByDimTableName(dimTableName);
    }

    public void addDimTableInfo(DimTableInfo dimTableInfo) throws BaseException {
        if(StringUtils.isEmpty(dimTableInfo.getDimTableName())){
            throw new ParamRequiredException("维表名称不能为空");
        }
        if(StringUtils.isEmpty(dimTableInfo.getCodeColType())){
            throw new ParamRequiredException("主键类型不能为空");
        }
        if(StringUtils.isEmpty(dimTableInfo.getDimValueCol()) && !StringUtils.isEmpty(dimTableInfo.getDimCodeCol())){
            throw new ParamRequiredException("请输入描述字段名");
        }
        if(!StringUtils.isEmpty(dimTableInfo.getDimValueCol()) && StringUtils.isEmpty(dimTableInfo.getDimCodeCol())){
            throw new ParamRequiredException("请输入主键字段名");
        }
        super.saveOrUpdate(dimTableInfo);
    }

    public void modifyDimTableInfo(DimTableInfo dimTableInfo) throws BaseException {
        if(StringUtils.isEmpty(dimTableInfo.getDimTableName())){
            throw new ParamRequiredException("维表名称不能为空");
        }
        if(StringUtils.isEmpty(dimTableInfo.getCodeColType())){
            throw new ParamRequiredException("主键类型不能为空");
        }
        if(StringUtils.isEmpty(dimTableInfo.getDimValueCol()) && !StringUtils.isEmpty(dimTableInfo.getDimCodeCol())){
            throw new ParamRequiredException("请输入描述字段名");
        }
        if(!StringUtils.isEmpty(dimTableInfo.getDimValueCol()) && StringUtils.isEmpty(dimTableInfo.getDimCodeCol())){
            throw new ParamRequiredException("请输入主键字段名");
        }
        super.saveOrUpdate(dimTableInfo);
    }

    public void deleteDimTableInfoById(String dimId) throws BaseException {
        if (selectDimTableInfoById(dimId)==null){
            throw new ParamRequiredException("ID不存在");
        }
        if (StringUtil.isBlank(dimId)){
            throw new ParamRequiredException("ID不能为空");
        }
        super.delete(dimId);
    }

}
