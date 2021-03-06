/*
 * @(#)LaberInfoVo.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.vo;

import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;

/**
 * Title : LaberInfoVo
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月16日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月16日
 */
public class LabelInfoVo extends LabelInfo {

    private static final long serialVersionUID = 2035013017939483936L;
    
    /**
     * 审批状态
     */
    private String approveStatusId;
    
    private String publishTimeStart;
    
    private String publishTimeEnd;
    
    private String createTimeStart;
    
    private String createTimeEnd;
    
    private String labelTypeIds;
    
    public String getApproveStatusId() {
        return approveStatusId;
    }

    public void setApproveStatusId(String approveStatusId) {
        this.approveStatusId = approveStatusId;
    }

    public String getpublishTimeStart() {
        return publishTimeStart;
    }

    public void setpublishTimeStart(String publishTimeStart) {
        this.publishTimeStart = publishTimeStart;
    }

    public String getpublishTimeEnd() {
        return publishTimeEnd;
    }

    public void setpublishTimeEnd(String publishTimeEnd) {
        this.publishTimeEnd = publishTimeEnd;
    }

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
    
    public String getLabelTypeIds() {
        return labelTypeIds;
    }

    public void setLabelTypeIds(String labelTypeIds) {
        this.labelTypeIds = labelTypeIds;
    }
    
}
