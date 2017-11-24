/*
 * @(#)MdaSysTableColumnImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.labelconfig.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.dao.IMdaSysTableColumnDao;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.vo.MdaSysTableColumnVo;

/**
 * Title : MdaSysTableColumnImpl
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
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
 * 1    2017年11月21日    lilin7        Created
 * </pre>
 * <p/>
 *
 * @author lilin7
 * @version 1.0.0.2017年11月21日
 */
@Repository
public class MdaSysTableColumnDaoImpl extends BaseDaoImpl<MdaSysTableColumn, String> implements IMdaSysTableColumnDao {

    @Override
    public Page<MdaSysTableColumn> findMdaSysTableColPageList(Page<MdaSysTableColumn> page,
            MdaSysTableColumnVo mdaSysTableColumnVo) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from MdaSysTableColumn m where 1=1");
        if (StringUtils.isNotBlank(mdaSysTableColumnVo.getLabelId())) {
            hql.append(" and m.lableId = :lableId");
            params.put("lableId", mdaSysTableColumnVo.getLabelId());
        }
        if (null != mdaSysTableColumnVo.getTableId()) {
            hql.append(" and m.tableId = :tableId");
            params.put("tableId", mdaSysTableColumnVo.getTableId());
        }
        if (StringUtils.isNotBlank(mdaSysTableColumnVo.getColumnName())) {
            hql.append(" and m.columnName = :columnName");
            params.put("columnName", mdaSysTableColumnVo.getColumnName());
        }
        if (StringUtils.isNotBlank(mdaSysTableColumnVo.getColumnCnName())) {
            hql.append(" and m.columnCnName = :columnCnName");
            params.put("columnCnName", mdaSysTableColumnVo.getColumnCnName());
        }
        if (null != mdaSysTableColumnVo.getColumnDataTypeId()) {
            hql.append(" and m.columnDataTypeId = :columnDataTypeId");
            params.put("columnDataTypeId", mdaSysTableColumnVo.getColumnDataTypeId());
        }
        if (StringUtils.isNotBlank(mdaSysTableColumnVo.getDimTransId())) {
            hql.append(" and m.dimTransId = :dimTransId");
            params.put("dimTransId", mdaSysTableColumnVo.getDimTransId());
        }
        if (StringUtils.isNotBlank(mdaSysTableColumnVo.getUnit())) {
            hql.append(" and m.unit = :unit");
            params.put("unit", mdaSysTableColumnVo.getUnit());
        }
        if (StringUtils.isNotBlank(mdaSysTableColumnVo.getDataType())) {
            hql.append(" and m.dataType = :dataType");
            params.put("datatype", mdaSysTableColumnVo.getDataType());
        }
        return super.findPageByHql(page, hql.toString(), params);
    }

    @Override
    public List<MdaSysTableColumn> findMdaSysTableColList(MdaSysTableColumnVo mdaSysTableColumnVo) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from MdaSysTableColumn m where 1=1");
        if (StringUtils.isNotBlank(mdaSysTableColumnVo.getLabelId())) {
            hql.append(" and m.lableId = :lableId");
            params.put("lableId", mdaSysTableColumnVo.getLabelId());
        }
        if (null != mdaSysTableColumnVo.getTableId()) {
            hql.append(" and m.tableId = :tableId");
            params.put("tableId", mdaSysTableColumnVo.getTableId());
        }
        if (StringUtils.isNotBlank(mdaSysTableColumnVo.getColumnName())) {
            hql.append(" and m.columnName = :columnName");
            params.put("columnName", mdaSysTableColumnVo.getColumnName());
        }
        if (StringUtils.isNotBlank(mdaSysTableColumnVo.getColumnCnName())) {
            hql.append(" and m.columnCnName = :columnCnName");
            params.put("columnCnName", mdaSysTableColumnVo.getColumnCnName());
        }
        if (null != mdaSysTableColumnVo.getColumnDataTypeId()) {
            hql.append(" and m.columnDataTypeId = :columnDataTypeId");
            params.put("columnDataTypeId", mdaSysTableColumnVo.getColumnDataTypeId());
        }
        if (StringUtils.isNotBlank(mdaSysTableColumnVo.getDimTransId())) {
            hql.append(" and m.dimTransId = :dimTransId");
            params.put("dimTransId", mdaSysTableColumnVo.getDimTransId());
        }
        if (StringUtils.isNotBlank(mdaSysTableColumnVo.getUnit())) {
            hql.append(" and m.unit = :unit");
            params.put("unit", mdaSysTableColumnVo.getUnit());
        }
        if (StringUtils.isNotBlank(mdaSysTableColumnVo.getDataType())) {
            hql.append(" and m.dataType = :dataType");
            params.put("datatype", mdaSysTableColumnVo.getDataType());
        }
        return super.findListByHql(hql.toString(), params);
    }

}
