/*
 * @(#)LabelExeInfo.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.core.label.entity;

import java.util.Date;

import io.swagger.annotations.ApiParam;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

/**
 * 
 * Title : LabelExeInfo
 * <p/>
 * Description : 标签(客户群）执行信息表
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
 * <pre>1    2018年5月14日    admin        Created</pre>
 * <p/>
 *
 * @author  shaosq
 * @version 1.0.0.2018年5月14日
 */
@Entity
@Table(name = "LOC_CUSTOM_LIST_EXE_INFO")
public class LabelExeInfo  extends BaseEntity{

    /**  */
    private static final long serialVersionUID = -2346606375785693415L;
    
    /**
     * 执行信息 ID
     */
    @Id
    @Column(name = "EXE_INFO_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "执行信息 ID")
    private String exeInfoId;
    
    /**
     * 客户群标签ID
     */
    @Column(name = "LABEL_ID")
    @ApiParam(value = "客户群标签ID")
    private String labelId;
    
    /**
     * 临时清单表名
     */
    @Column(name = "LIST_TABLE_NAME")
    @ApiParam(value = "临时清单表名")
    private String listTableName;
    
    /**
     * 数据日期
     */
    @Column(name = "DATA_DATE")
    @ApiParam(value = "数据日期")
    private String dataDate;
    
    /**
     * sql语句
     */
    @Column(name = "EXPRESSION")
    @ApiParam(value = "sql语句")
    private String expression;
    
    /**
     * 异常信息
     */
    @Column(name = "EXCP_INFO")
    @ApiParam(value = "异常信息")
    private String excpInfo;
    
    /**
     * 开始时间
     */
    @Column(name = "START_TIME")
    @ApiParam(value = "开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "END_TIME")
    @ApiParam(value = "结束时间")
    private Date endTime;

    
    public String getExeInfoId() {
        return exeInfoId;
    }

    
    public void setExeInfoId(String exeInfoId) {
        this.exeInfoId = exeInfoId;
    }

    
    public String getLabelId() {
        return labelId;
    }

    
    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    
    public String getListTableName() {
        return listTableName;
    }

    
    public void setListTableName(String listTableName) {
        this.listTableName = listTableName;
    }

    
    public String getDataDate() {
        return dataDate;
    }

    
    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }

    
    public String getExpression() {
        return expression;
    }

    
    public void setExpression(String expression) {
        this.expression = expression;
    }

    
    public String getExcpInfo() {
        return excpInfo;
    }

    
    public void setExcpInfo(String excpInfo) {
        this.excpInfo = excpInfo;
    }

    
    public Date getStartTime() {
        return startTime;
    }

    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    
    public Date getEndTime() {
        return endTime;
    }

    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    

}
