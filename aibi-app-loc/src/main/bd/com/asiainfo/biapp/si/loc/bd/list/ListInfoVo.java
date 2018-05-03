/*
 * @(#)ListInfoVo.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.bd.list;

import com.asiainfo.biapp.si.loc.bd.list.entity.ListInfo;

/**
 * 
 * Title : ListInfoVo
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
 * <pre>1    2018年5月3日    admin        Created</pre>
 * <p/>
 *
 * @author  shaosq
 * @version 1.0.0.2018年5月3日
 */
public class ListInfoVo extends ListInfo{

    /**  */
    private static final long serialVersionUID = -8811746237843893212L;
    /**
     * 专区ID
     */
    private String configId;
    
    /**
     * 客户群名称
     */
    private String labelName;
    
    /**
     * 客户群生成状态（多个）
     */
    private String dataStatuses;

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    
    public String getLabelName() {
        return labelName;
    }

    
    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getDataStatuses() {
        return dataStatuses;
    }

    
    public void setDataStatuses(String dataStatuses) {
        this.dataStatuses = dataStatuses;
    }
    
}
