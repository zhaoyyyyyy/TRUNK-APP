/*
 * @(#)CustomDownloadRecordDaoImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.syspush.dao.ICustomDownloadRecordDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.CustomDownloadRecord;

/**
 * Title : CustomDownloadRecordDaoImpl
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
 * <pre>1    2018年1月18日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月18日
 */

@Repository
public class CustomDownloadRecordDaoImpl extends BaseDaoImpl<CustomDownloadRecord, String> implements ICustomDownloadRecordDao{

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param CustomDownloadRecord
     * @return
     */
    public Page<CustomDownloadRecord> selectCustomDownloadRecordPageList(Page<CustomDownloadRecord> page, CustomDownloadRecord customDownloadRecord) {
        Map<String, Object> reMap = fromBean(customDownloadRecord);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    /**
     * 根据条件查询列表
     *
     * @param CustomDownloadRecord
     * @return
     */
    public List<CustomDownloadRecord> selectCustomDownloadRecordList(CustomDownloadRecord customDownloadRecord) {
        Map<String, Object> reMap = fromBean(customDownloadRecord);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }
    
    public Map<String, Object> fromBean(CustomDownloadRecord customDownloadRecord){
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from CustomDownloadRecord l where 1=1 ");
        if(StringUtil.isNoneBlank(customDownloadRecord.getRecordId())){
            hql.append("and l.recordId = :recordId ");
            params.put("recordId", customDownloadRecord.getRecordId());
        }
        if(StringUtil.isNoneBlank(customDownloadRecord.getFileName())){
            hql.append("and l.fileName = :fileName ");
            params.put("fileName", customDownloadRecord.getFileName());
        }
        if(StringUtil.isNoneBlank(customDownloadRecord.getCustomId())){
            hql.append("and l.customId = :customId ");
            params.put("customId", customDownloadRecord.getCustomId());
        }
        if(StringUtil.isNoneBlank(customDownloadRecord.getDataDate())){
            hql.append("and l.dataDate = :dataDate ");
            params.put("dataDate", customDownloadRecord.getDataDate());
        }
        if(null != customDownloadRecord.getDataStatus()){
            hql.append("and l.dataStatus = :dataStatus ");
            params.put("dataStatus", customDownloadRecord.getDataStatus());
        }
        if(null != customDownloadRecord.getDataTime()){
            hql.append("and l.dataTime like :dataTime ");
            params.put("dataTime", customDownloadRecord.getDataTime());
        }
        if(StringUtil.isNoneBlank(customDownloadRecord.getDownloadNum())){
            hql.append("and l.downloadNum = :downloadNum ");
            params.put("downloadNum", customDownloadRecord.getDownloadNum());
        }
		
        hql.append("ORDER BY l.dataDate ASC ");
        
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }


}
