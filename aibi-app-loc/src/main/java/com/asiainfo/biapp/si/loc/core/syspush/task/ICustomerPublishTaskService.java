/*
 * @(#)ICustomerPublishService.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.core.syspush.task;


/**
 * Title : ICustomerPublishService
 * <p/>
 * Description : 客户群推送接口
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.7 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date         Modified By    Why & What is modified</pre>
 * <pre>1    2018年3月8日     hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年3月8日
 */

public interface ICustomerPublishTaskService {

    /**
     * 周期性客户群推送接口
     */
    public boolean excutor(int updateCycle);

}
