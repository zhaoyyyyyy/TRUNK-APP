/*
 * @(#)ICustomerPublishService.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service;


import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushCycle;

/**
 * Title : ICustomerPublishService
 * <p/>
 * Description : <pre>客户群推送接口类：
 *      用于现场本地在使用COC的推送线程类但是要对推送内容进行个性化时继承本接口，
 *      再在对应的推送平台(sysinfo)里的PushClassName配上本地的beanID.</pre>
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
 * <pre>1    2017年12月12日     hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2017年12月12日
 */

public interface ICustomerPublishService {
	
	
    /**
     * Description: 推送类入口方法
     * 
     * @param customPushReqList 推送请求列表
     * @param pushCycle 推送周期
     * @param isJobTask 是否是自动启动的任务
     * @param reservedParameters 预留传入参数
     */
	public boolean push(List<LabelPushCycle> labelPushCycleList, boolean isJobTask, Map<String, Object> reservedParameters);
    
	
}
