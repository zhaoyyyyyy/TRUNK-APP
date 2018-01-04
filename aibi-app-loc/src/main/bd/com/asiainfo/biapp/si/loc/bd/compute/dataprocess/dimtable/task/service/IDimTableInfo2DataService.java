/*
 * @(#)IDimTableInfo2DataService.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.bd.compute.dataprocess.dimtable.task.service;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;

/**
 * Title : IDimTableInfo2DataService
 * <p/>
 * Description : 本接口类是dim_table的跑数服务，数据流是：DimTableInfo(前台) ——>后台——>DimTableData(前台)
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 8.0 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月3日    hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年1月3日
 */

public interface IDimTableInfo2DataService {

    /**
     * @Description: dim_table的跑数入口，数据流是：DimTableInfo(前台) ——>后台——>DimTableData(前台)
     * @param String tableName 维表表名
     *
     */
    public void dimTableInfo2Data(String tableName) throws BaseException;

	
}
