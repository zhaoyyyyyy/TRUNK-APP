/*
 * @(#)ICustomDownloadRecordDao.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.syspush.entity.CustomDownloadRecord;

/**
 * Title : ICustomDownloadRecordDao
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
 * <pre>1    2018年3月30日    hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年3月30日
 */

public interface ICustomDownloadRecordDao extends BaseDao<CustomDownloadRecord, String>{

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param CustomDownloadRecord
     * @return
     */
    public Page<CustomDownloadRecord> selectCustomDownloadRecordPageList(Page<CustomDownloadRecord> page, CustomDownloadRecord customDownloadRecord);

    /**
     * 根据条件查询列表
     *
     * @param CustomDownloadRecord
     * @return
     */
    public List<CustomDownloadRecord> selectCustomDownloadRecordList(CustomDownloadRecord customDownloadRecord);


}
