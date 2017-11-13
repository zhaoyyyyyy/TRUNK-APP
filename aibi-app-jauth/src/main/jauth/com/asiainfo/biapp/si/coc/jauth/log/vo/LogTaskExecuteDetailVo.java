/*
 * @(#)LogTaskExecuteDetailVo.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.coc.jauth.log.vo;

import com.asiainfo.biapp.si.coc.jauth.log.entity.LogTaskExecuteDetail;

/**
 * Title : LogTaskExecuteDetailVo
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
 * 1    2017年11月7日    李林        Created
 * </pre>
 * <p/>
 *
 * @author 李林
 * @version 1.0.0.2017年11月7日
 */
public class LogTaskExecuteDetailVo extends LogTaskExecuteDetail {

    private static final long serialVersionUID = 1L;

    private String startTimeStart;

    private String startTimeEnd;

    public String getStartTimeStart() {
        return startTimeStart;
    }

    public void setStartTimeStart(String startTimeStart) {
        this.startTimeStart = startTimeStart;
    }

    public String getStartTimeEnd() {
        return startTimeEnd;
    }

    public void setStartTimeEnd(String startTimeEnd) {
        this.startTimeEnd = startTimeEnd;
    }

}
