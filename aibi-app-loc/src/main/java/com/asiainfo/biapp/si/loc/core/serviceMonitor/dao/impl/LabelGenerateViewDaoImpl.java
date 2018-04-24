/*
 * @(#)LabelGenerateViewDaoImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.core.serviceMonitor.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.dao.ILabelGenerateViewDao;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.entity.LabelGenerateView;

/**
 * 
 * Title : LabelGenerateViewDaoImpl
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
 * <pre>1    2018年4月24日    shaosq        Created</pre>
 * <p/>
 *
 * @author  shaosq
 * @version 1.0.0.2018年4月24日
 */
@Repository("labelGenerateViewDaoImpl")
public class LabelGenerateViewDaoImpl extends BaseDaoImpl<LabelGenerateView, String> implements ILabelGenerateViewDao{

    @Override
    public Page<LabelGenerateView> queryLabelGenerateViewPage(Page<LabelGenerateView> page, String configId) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hqls = new StringBuffer("from LabelGenerateView s where s.labelId in( select a.labelId from LabelInfo a where a.configId= :configId) ");
        hqls.append("order by s.dataDate desc ");
        params.put("configId", configId);
        return super.findPageByHql(page, hqls.toString(), params);
    }
    
}
