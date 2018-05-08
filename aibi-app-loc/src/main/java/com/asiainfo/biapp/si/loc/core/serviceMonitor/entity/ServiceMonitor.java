/*
 * @(#)ServiceMonitor.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.serviceMonitor.entity;

import io.swagger.annotations.ApiParam;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.asiainfo.biapp.si.loc.base.utils.DateUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

/**
 * 
 * Title : ServiceMonitor
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
 * <pre>1    2018年4月20日    shaosq        Created</pre>
 * <p/>
 *
 * @author  shaosq
 * @version 1.0.0.2018年4月19日
 */

@Entity
@Table(name = "V_MONITOR_CONFIG_DATA_STATUS")
public class ServiceMonitor extends BaseEntity {


    /**  */
    private static final long serialVersionUID = -5585790011414262800L;

    /**
     * 专区Id
     */
    @Id
    @Column(name = "CONFIG_ID")
    @ApiParam(value = "专区Id")
    private String configId;

    /**
     * 专区名称
     */
    @Column(name = "CONFIG_NAME")
    @ApiParam(value = "专区名称")
    private String sourceName;
    /**
     * 最新数据时间
     */
    @Column(name = "DATA_DATE")
    @ApiParam(value = "最新数据时间")
    private String dataDate;
    
    /**
     * 数据准备视图：v_data_prepare
     * 数据状态 0未准备  1准备完成
     * 抽取状态 0抽取中  1抽取完成  2抽取失败
     * 未准备数量
     */
    @Column(name = "NOT_PREPARE_COUNT")
    @ApiParam(value = "未准备数量")
    private Integer notPrepareCount;
    
    /**
     * 准备完成数量
     */
    @Column(name = "PREPARED_COUNT")
    @ApiParam(value = "准备完成数量")
    private Integer preparedCount;
    
    /**
     * 抽取中数量
     */
    @Column(name = "EXTRACTING_COUNT")
    @ApiParam(value = "抽取中数量")
    private Integer extractingCount;
    
    /**
     * 抽取完成数量
     */
    @Column(name = "EXTRACT_SUCCESS_COUNT")
    @ApiParam(value = "抽取完成状态")
    private Integer extractSuccessCount;
    
    /**
     * 抽取失败数量
     */
    @Column(name = "EXTRACT_FAIL_COUNT")
    @ApiParam(value = "抽取失败数量")
    private Integer extractFailCount;

    /**
     * 标签生成视图：v_label_generate
     * 标签生成状态  0失败  1成功  2客户群暂存
     * 生成失败数量
     */
    @Column(name = "GEN_FAIL_COUNT")
    @ApiParam(value = "生成失败数量")
    private Integer genFailCount;
    
    /**
     * 抽取成功数量
     */
    @Column(name = "GEN_SUCCESS_COUNT")
    @ApiParam(value = "生成成功数量")
    private Integer genSuccessCount;
    
    /**
     * 客户群暂存数量
     */
    @Column(name = "CUR_SAVE_COUNT")
    @ApiParam(value = "客户群暂存数量")
    private Integer curSaveCount;
    
    /**
     * 客户群生成视图：v_custom_generate
     * 标签生成状态 0,生成失败；1,待创建，2创建中，3成功，4预约
     * 客户群生成失败数量
     */
    @Column(name = "CUSTOM_FAIL_COUNT")
    @ApiParam(value = "客户群生成失败数量")
    private Integer customFailCount;
    
    /**
     * 客户群待创建数量
     */
    @Column(name = "CUSTOM_PREPARE_COUNT")
    @ApiParam(value = "客户群待创建数量")
    private Integer customPrepareCount;
    
    /**
     * 客户群创建中数量
     */
    @Column(name = "CUSTOM_CREATING_COUNT")
    @ApiParam(value = "客户群创建中数量")
    private Integer customCreatingCount;
    
    /**
     * 客户群创建成功数量
     */
    @Column(name = "CUSTOM_SUCCESS_COUNT")
    @ApiParam(value = "客户群创建成功数量")
    private Integer customSuccessCount;
    
    /**
     * 客户群预约数量
     */
    @Column(name = "CUSTOM_APPOINT_COUNT")
    @ApiParam(value = "客户群预约数量")
    private Integer customAppointCount;
    
    /**
     * 客户群推送视图：v_custom_push
     * 客户群推送状态     1：等待推送 2：推送中 3：推送成功 4：推送失败
     * 客户群推送失败数量
     */
    @Column(name = "NOT_PUSH_COUNT")
    @ApiParam(value = "客户群推送其他状态数量")
    private Integer notPushCount;
    
    @Column(name = "PUSH_WAIT_COUNT")
    @ApiParam(value = "客户群等待推送数量")
    private Integer pushWaitCount;
    
    @Column(name = "PUSH_FAIL_COUNT")
    @ApiParam(value = "客户群推送失败数量")
    private Integer pushFailCount;
    
    /**
     * 客户群推送成功数量
     */
    @Column(name = "PUSH_SUCCESS_COUNT")
    @ApiParam(value = "客户群推送成功数量")
    private Integer pushSuccessCount;
    
    /**
     * 客户群推送中数量
     */
    @Column(name = "PUSHING_COUNT")
    @ApiParam(value = "客户群推送中数量")
    private Integer pushingCount;

    /**
     * 排序字段
     */
    @Transient
    private Integer sortOrder;
    
    
    public String getConfigId() {
        return configId;
    }

    
    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getSourceName() {
        return sourceName;
    }


    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
    
    public String getDataDate() {
        if(StringUtil.isNotEmpty(dataDate)){
            if(dataDate.length()==6){
                Date formatDate = DateUtil.string2Date(dataDate, DateUtil.FORMAT_YYYYMM);
                dataDate = DateUtil.date2String(formatDate,DateUtil.FORMAT_YYYY_MM);
            }else if(dataDate.length()==8){
                Date formatDate = DateUtil.string2Date(dataDate, DateUtil.FORMAT_YYYYMMDD);
                dataDate = DateUtil.date2String(formatDate,DateUtil.FORMAT_YYYY_MM_DD);
            }
        }
       return dataDate;
    }

    
    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }

    
    public Integer getNotPrepareCount() {
        return notPrepareCount;
    }

    
    public void setNotPrepareCount(Integer notPrepareCount) {
        this.notPrepareCount = notPrepareCount;
    }

    
    public Integer getPreparedCount() {
        return preparedCount;
    }

    
    public void setPreparedCount(Integer preparedCount) {
        this.preparedCount = preparedCount;
    }

    
    public Integer getExtractingCount() {
        return extractingCount;
    }

    
    public void setExtractingCount(Integer extractingCount) {
        this.extractingCount = extractingCount;
    }

    
    public Integer getExtractSuccessCount() {
        return extractSuccessCount;
    }

    
    public void setExtractSuccessCount(Integer extractSuccessCount) {
        this.extractSuccessCount = extractSuccessCount;
    }

    
    public Integer getExtractFailCount() {
        return extractFailCount;
    }

    
    public void setExtractFailCount(Integer extractFailCount) {
        this.extractFailCount = extractFailCount;
    }

    
    public Integer getGenFailCount() {
        return genFailCount;
    }

    
    public void setGenFailCount(Integer genFailCount) {
        this.genFailCount = genFailCount;
    }

    
    public Integer getGenSuccessCount() {
        return genSuccessCount;
    }

    
    public void setGenSuccessCount(Integer genSuccessCount) {
        this.genSuccessCount = genSuccessCount;
    }

    
    public Integer getCurSaveCount() {
        return curSaveCount;
    }

    
    public void setCurSaveCount(Integer curSaveCount) {
        this.curSaveCount = curSaveCount;
    }

    
    public Integer getCustomFailCount() {
        return customFailCount;
    }

    
    public void setCustomFailCount(Integer customFailCount) {
        this.customFailCount = customFailCount;
    }

    
    public Integer getCustomPrepareCount() {
        return customPrepareCount;
    }

    
    public void setCustomPrepareCount(Integer customPrepareCount) {
        this.customPrepareCount = customPrepareCount;
    }

    
    public Integer getCustomCreatingCount() {
        return customCreatingCount;
    }

    
    public void setCustomCreatingCount(Integer customCreatingCount) {
        this.customCreatingCount = customCreatingCount;
    }

    
    public Integer getCustomSuccessCount() {
        return customSuccessCount;
    }

    
    public void setCustomSuccessCount(Integer customSuccessCount) {
        this.customSuccessCount = customSuccessCount;
    }

    
    public Integer getCustomAppointCount() {
        return customAppointCount;
    }

    
    public void setCustomAppointCount(Integer customAppointCount) {
        this.customAppointCount = customAppointCount;
    }

    
    
    public Integer getNotPushCount() {
        return notPushCount;
    }


    
    public void setNotPushCount(Integer notPushCount) {
        this.notPushCount = notPushCount;
    }


    
    public Integer getPushWaitCount() {
        return pushWaitCount;
    }


    
    public void setPushWaitCount(Integer pushWaitCount) {
        this.pushWaitCount = pushWaitCount;
    }


    public Integer getPushFailCount() {
        return pushFailCount;
    }

    
    public void setPushFailCount(Integer pushFailCount) {
        this.pushFailCount = pushFailCount;
    }

    
    public Integer getPushSuccessCount() {
        return pushSuccessCount;
    }

    
    public void setPushSuccessCount(Integer pushSuccessCount) {
        this.pushSuccessCount = pushSuccessCount;
    }

    
    public Integer getPushingCount() {
        return pushingCount;
    }

    
    public void setPushingCount(Integer pushingCount) {
        this.pushingCount = pushingCount;
    }


    
    public Integer getSortOrder() {
        return sortOrder;
    }


    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
  
}
