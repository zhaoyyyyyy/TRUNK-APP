/*
 * @(#)LocLabelPushCycleDaoImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.task;


import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushCycle;

/**
 * Title : LocLabelPushCycleDaoImpl
 * <p/>
 * Description : 标签推送设置信息表服务类
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date         Modified By    Why & What is modified</pre>
 * <pre>1    2018年2月26日     hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年2月26日
 */

public interface ICustomerPublishThread extends Runnable{
	
	
    /**
     * Description: 传入初始化参数
     * 
     * @param customPushReqList 推送请求列表
     * @param pushCycle 推送周期
     * @param isJobTask 是否是自动启动的任务
     */
	public void initParamter(List<LabelPushCycle> customPushReqList,boolean isJobTask, List<Map<String, Object>> reservedParameters);
    
	/**
     * Description: 线程类入口方法
     * 
     */
	public void run();

	
}
