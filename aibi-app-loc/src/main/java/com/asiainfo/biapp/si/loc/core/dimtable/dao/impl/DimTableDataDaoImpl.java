/*
 * @(#)DimTableDataDaoImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.dimtable.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.dimtable.dao.IDimTableDataDao;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableData;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableDataId;

/**
 * Title : DimTableDataDaoImpl
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
 * <pre>1    2017年12月27日    hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2017年12月27日
 */
@Repository
public class DimTableDataDaoImpl extends BaseDaoImpl<DimTableData, DimTableDataId> implements IDimTableDataDao{

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param DimTableDataId
     * @return
     */
    public Page<DimTableData> selectDimTableDataPageList(Page<DimTableData> page, DimTableData dimTableData) throws BaseException {
        Map<String, Object> reMap = fromBean(dimTableData);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    /**
     * 根据条件查询列表
     *
     * @param DimTableDataId
     * @return
     */
    public List<DimTableData> selectDimTableDataList(DimTableData DimTableData) throws BaseException {
        Map<String, Object> reMap = fromBean(DimTableData);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        
        return super.findListByHql(reMap.get("hql").toString(), params);
    }
    
    public Map<String, Object> fromBean(DimTableData dimTableData){
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from DimTableData d where 1=1 ");
        if(null != dimTableData.getId()){
            if(StringUtil.isNoneBlank(dimTableData.getId().getDimTableName())){
                hql.append("and d.id.dimTableName = :dimTableName ");
                params.put("dimTableName", dimTableData.getId().getDimTableName());
            }
            if(StringUtil.isNoneBlank(dimTableData.getId().getDimCode())){
                hql.append("and d.id.dimCode = :dimCode ");
                params.put("dimCode", dimTableData.getId().getDimCode());
            }
        }
        if(StringUtil.isNoneBlank(dimTableData.getDimValue())){
            hql.append("and d.dimValue LIKE :dimValue ");
            params.put("dimValue", "%" + dimTableData.getDimValue()+"%");
        }
        
        reMap.put("hql", hql);
        reMap.put("params", params);
        
        return reMap;
    }



}
