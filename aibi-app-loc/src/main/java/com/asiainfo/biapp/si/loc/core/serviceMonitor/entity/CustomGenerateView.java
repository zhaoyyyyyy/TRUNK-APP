/*
 * @(#)CustomGenerateView.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.core.serviceMonitor.entity;

import io.swagger.annotations.ApiParam;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

/**
 * 
 * Title : 运营监控明细：客户群生成表格
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
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年4月24日    admin        Created</pre>
 * <p/>
 *
 * @author  shaosq
 * @version 1.0.0.2018年4月24日
 */
@Entity
@Table(name = "V_CUSTOM_GENERATE_VIEW")
public class CustomGenerateView extends BaseEntity{

    /**  */
    private static final long serialVersionUID = 5562677056576283026L;
    
    @Id
    @Column(name = "LABEL_ID")
    @ApiParam(value = "客户群编码")
    private String labelId;
    
    @Column(name = "LABEL_NAME")
    @ApiParam(value = "客户群名称")
    private String labelName;
    
    @Column(name = "DATA_STATUS")
    @ApiParam(value = "生成状态")
    private Integer dataStatus;
    
    @Column(name = "DATA_DATE")
    @ApiParam(value = "生成时间")
    private String dataDate;
    
    public String getLabelId() {
        return labelId;
    }

    
    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    
    public String getLabelName() {
        return labelName;
    }

    
    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    
    public Integer getDataStatus() {
        return dataStatus;
    }

    
    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }
    
    public String getDataDate() {
        return dataDate;
    }


    
    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }
}
