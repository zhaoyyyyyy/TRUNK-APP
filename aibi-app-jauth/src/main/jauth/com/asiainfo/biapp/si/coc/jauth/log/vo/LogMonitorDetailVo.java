/*
 * @(#)LogMonitorDetailVo.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.coc.jauth.log.vo;

import com.asiainfo.biapp.si.coc.jauth.log.entity.LogMonitorDetail;

/**
 * Title : LogMonitorDetailVo
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年11月6日    lilin        Created</pre>
 * <p/>
 *
 * @author  lilin
 * @version 1.0.0.2017年11月6日
 */
public class LogMonitorDetailVo extends LogMonitorDetail{
    /**  */
    private static final long serialVersionUID = 1L;
    /**
     * 操作时间：开始
     */
    public String opTimeStart;
    /**
     * 操作时间：结束
     */
    public String opTimeEnd;
    
    public String getOpTimeStart() {
        return opTimeStart;
    }
    
    public void setOpTimeStart(String opTimeStart) {
        this.opTimeStart = opTimeStart;
    }
    
    public String getOpTimeEnd() {
        return opTimeEnd;
    }
    
    public void setOpTimeEnd(String opTimeEnd) {
        this.opTimeEnd = opTimeEnd;
    }
    

}
