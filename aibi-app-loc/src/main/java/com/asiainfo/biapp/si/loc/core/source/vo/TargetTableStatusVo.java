/*
 * @(#)TargetTableStatusVo.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.vo;

import com.asiainfo.biapp.si.loc.core.source.entity.TargetTableStatus;


/**
 * Title : TargetTableStatusVo
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年11月15日    zhangnan7        Created</pre>
 * <p/>
 *
 * @author  zhangnan7
 * @version 1.0.0.2017年11月15日
 */
public class TargetTableStatusVo extends TargetTableStatus {
    
    private static final long serialVersionUID = 2035013017939483936L;
    
    private String configId;
    private String dataStatuses;
    private String isDoings;
    
    public String getConfigId() {
        return configId;
    }
    
    public void setConfigId(String configId) {
        this.configId = configId;
    }

    
    public String getDataStatuses() {
        return dataStatuses;
    }

    
    public void setDataStatuses(String dataStatuses) {
        this.dataStatuses = dataStatuses;
    }

    
    public String getIsDoings() {
        return isDoings;
    }

    
    public void setIsDoings(String isDoings) {
        this.isDoings = isDoings;
    }
    
}
