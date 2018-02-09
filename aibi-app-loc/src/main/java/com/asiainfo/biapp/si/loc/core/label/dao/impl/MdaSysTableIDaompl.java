/*
 * @(#)MdaSysTableImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.label.dao.IMdaSysTableDao;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTable;
import com.asiainfo.biapp.si.loc.core.label.vo.MdaSysTableVo;

/**
 * Title : MdaSysTableImpl
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
 * 1    2017年11月20日    lilin7        Created
 * </pre>
 * <p/>
 *
 * @author lilin7
 * @version 1.0.0.2017年11月20日
 */
@Repository
public class MdaSysTableIDaompl extends BaseDaoImpl<MdaSysTable, String> implements IMdaSysTableDao {

    public Page<MdaSysTable> selectMdaSysTablePageList(Page<MdaSysTable> page, MdaSysTableVo mdaSysTableVo) {
        Map<String, Object> reMap = fromBean(mdaSysTableVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");       
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    public List<MdaSysTable> selectMdaSysTableList(MdaSysTableVo mdaSysTableVo) {
        Map<String, Object> reMap = fromBean(mdaSysTableVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");  
        return super.findListByHql(reMap.get("hql").toString(), params);
    }

    public Map<String, Object> fromBean(MdaSysTableVo mdaSysTableVo){
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from MdaSysTable m where 1=1");
        if (StringUtils.isNotBlank(mdaSysTableVo.getConfigId())) {
            hql.append(" and m.configId = :configId");
            params.put("configId", mdaSysTableVo.getConfigId());
        }
        if (StringUtils.isNotBlank(mdaSysTableVo.getTableName())) {
            hql.append(" and m.tableName = :tableName");
            params.put("tableName", mdaSysTableVo.getTableName());
        }
        if (StringUtils.isNotBlank(mdaSysTableVo.getTableCnName())) {
            hql.append(" and m.tableCnName = :tableCnName");
            params.put("tableCnName", mdaSysTableVo.getTableCnName());
        }
        if (StringUtils.isNotBlank(mdaSysTableVo.getTableDesc())) {
            hql.append(" and m.tableDesc = :tableDesc");
            params.put("tableDesc", mdaSysTableVo.getTableDesc());
        }
        if (StringUtils.isNotBlank(mdaSysTableVo.getTablePostfix())) {
            hql.append(" and m.tablePostfix = :tablePostfix");
            params.put("tablePostfix", mdaSysTableVo.getTablePostfix());
        }
        if (StringUtils.isNotBlank(mdaSysTableVo.getTableSchema())) {
            hql.append(" and m.tableSchema = :tableSchema");
            params.put("tableSc", mdaSysTableVo.getTableSchema());
        }
        if (null != mdaSysTableVo.getTableType()) {
            hql.append(" and m.tableType = :tableType");
            params.put("tableType", mdaSysTableVo.getTableType());
        }
        if (null != mdaSysTableVo.getUpdateCycle()) {
            hql.append(" and m.updateCycle = :updateCycle");
            params.put("updateCycle", mdaSysTableVo.getUpdateCycle());
        }
        reMap.put("hql", hql);
        reMap.put("params",params );
        return reMap;
    }
}
