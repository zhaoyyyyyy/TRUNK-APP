/*
 * @(#)StatusAndMessage.java
 * 
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.base.entity;

/**
 * Title : StatusAndMessage
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2018
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
 * 1    2018年1月3日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2018年1月3日
 */
public class StatusAndMessage {

    private String errorRow;

    private String errorMessage;

    public String getErrorRow() {
        return errorRow;
    }

    public void setErrorRow(String errorRow) {
        this.errorRow = errorRow;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
