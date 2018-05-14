/*
 * @(#)LabelExeInfoDaoImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.core.label.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelExeInfoDao;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelExeInfo;

/**
 * 
 * Title : LabelExeInfoDaoImpl
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
 * <pre>1    2018年5月14日    admin        Created</pre>
 * <p/>
 *
 * @author  shaosq
 * @version 1.0.0.2018年5月14日
 */
@Repository
public class LabelExeInfoDaoImpl extends BaseDaoImpl<LabelExeInfo, String> implements ILabelExeInfoDao{
    
    public LabelExeInfo selectLabelExeInfoByLabelId(String labelId,String dataDate) {
        StringBuffer hql = new StringBuffer("from LabelExeInfo c where 1=1 ");
        Map<String, Object> params = new HashMap<>();
        if(StringUtil.isNotBlank(labelId)){
            hql.append("and c.labelId = :labelId ");
            params.put("labelId", labelId);
        }
        if(StringUtil.isNotBlank(dataDate)){
            hql.append("and c.dataDate = :dataDate ");
            params.put("dataDate", dataDate);
        }
        return super.findOneByHql(hql.toString(),params );
    }
    
}
