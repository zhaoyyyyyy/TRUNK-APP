/*
 * @(#)DimTableDataServiceImpl.java
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
import com.asiainfo.biapp.si.loc.core.dimtable.dao.IDimTableDataDao;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableData;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableDataId;
import com.asiainfo.biapp.si.loc.core.dimtable.service.IDimTableDataService;

/**
 * Title : DimTableDataServiceImpl
 * <p/>
 * Description : 维表数据表(前台库存储)服务类
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 8.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月3日    hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年1月3日
 */

@Service
@Transactional
public class DimTableDataServiceImpl extends BaseServiceImpl<DimTableData, DimTableDataId> implements IDimTableDataService{

    @Autowired
    private IDimTableDataDao iDimTableDataDao;
    
    @Override
    protected BaseDao<DimTableData, DimTableDataId> getBaseDao() {
        return iDimTableDataDao;
    }
        
    public Page<DimTableData> selectDimTableDataPageList(Page<DimTableData> page, DimTableData DimTableDataVo)
            throws BaseException {
        return iDimTableDataDao.selectDimTableDataPageList(page, DimTableDataVo);
    }

    public List<DimTableData> selectDimTableDataList(DimTableData DimTableDataVo) throws BaseException {
        return iDimTableDataDao.selectDimTableDataList(DimTableDataVo);
    }

    public DimTableData selectDimTableDataById(DimTableDataId dimTableDataId) throws BaseException {
        if (null != null) {
            if (StringUtil.isBlank(dimTableDataId.getDimTableName()) && StringUtil.isBlank(dimTableDataId.getDimCode()) ) {
                throw new ParamRequiredException("DimTableNameh或DimCode都不能为空");
            }
            return super.get(dimTableDataId);
        }
        return null;
    }

}
