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
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.dimtable.dao.IDimTableDataDao;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableData;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableDataId;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;
import com.asiainfo.biapp.si.loc.core.dimtable.service.IDimTableDataService;
import com.asiainfo.biapp.si.loc.core.label.dao.IMdaSysTableDao;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTable;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;

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

    @Autowired
    private IMdaSysTableDao iMdaSysTableDao;
    
    @Override
    protected BaseDao<DimTableData, DimTableDataId> getBaseDao() {
        return iDimTableDataDao;
    }
        
    public Page<DimTableData> selectDimTableDataPageList(Page<DimTableData> page, DimTableData dimTableData)
            throws BaseException {
        return iDimTableDataDao.selectDimTableDataPageList(page, dimTableData);
    }

    public List<DimTableData> selectDimTableDataList(DimTableData dimTableData) throws BaseException {
        return iDimTableDataDao.selectDimTableDataList(dimTableData);
    }

    public Page<DimTableData> selectDimTableDataPageList(Page<DimTableData> page, String labelId, String dimName) throws BaseException{
        //过滤条件
        DimTableData dimTableData = new DimTableData();
        
        if (StringUtil.isBlank(labelId)) {
            if (null != page) {
                List<DimTableData> rows = page.getRows();
                if (null != rows && !rows.isEmpty()) {
                    //默认把page里的第一个元素作为参数
                    dimTableData = rows.get(0); 
                }
            }
        } else {
            //获取维表表名
            LabelInfo labelInfo = CocCacheProxy.getCacheProxy().getLabelInfoById(labelId);
            MdaSysTableColumn mdaSysTableColumn = labelInfo.getMdaSysTableColumn();
            DimTableInfo dimTableInfo = mdaSysTableColumn.getDimtableInfo();
            System.out.println(dimTableInfo);
            String dimTableName = null;
            if (null != dimTableInfo) {
                dimTableName = dimTableInfo.getDimTableName();
            } else {
                MdaSysTable mdaSysTable = iMdaSysTableDao.get(mdaSysTableColumn.getTableId());
                dimTableName = mdaSysTable.getTableName();
            }
            
            if (StringUtil.isBlank(dimTableName)) {
                throw new ParamRequiredException("dimTableName不能为空");
            }
            dimTableData.setId(new DimTableDataId(dimTableName));
            if (StringUtil.isNoneBlank(dimName)) {
                dimTableData.setDimValue(dimName);
            }
        }
        
        
        return this.selectDimTableDataPageList(page, dimTableData);
    }
    
    
}
