/*
 * @(#)CustomDownloadRecord.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;

/**
 * Title : CustomDownloadRecord-客户群下载记录表
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
 * <pre>1    2018年3月30日    hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年3月30日
 */

@Entity
@Table(name = "LOC_CUSTOM_DOWNLOAD_RECORD")
public class CustomDownloadRecord extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @Column(name = "RECORD_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "主键")
    private String recordId;
    
    /**
     * 文件名
     */
    @Column(name = "FILE_NAME")
    @ApiParam(value = "文件名")
    private String fileName;
    
    /**
     * 客户群ID
     */
    @Column(name = "CUSTOM_ID")
    @ApiParam(value = "客户群ID")
    private String customId;

    /**
     * 数据日期
     */
    @Column(name = "DATA_DATE")
    @ApiParam(value = "数据日期")
    private String dataDate;
    
    /**
     * 数据状态:1:未生成，2：生成中，3：已生成，4：失败
     */
    @Column(name = "DATA_STATUS")
    @ApiParam(value = "数据状态")
    private Integer dataStatus;
    
    /**
     * 数据生成时间
     */
    @Column(name = "DATA_TIME")
    @ApiParam(value = "数据生成时间")
    private Date dataTime;

    /**
     * 下载次数
     */
    @Column(name = "DOWNLOAD_NUM")
    @ApiParam(value = "下载次数")
    private String downloadNum;
    
    public CustomDownloadRecord() {}
    public CustomDownloadRecord(String customId, String dataDate) {
        this.customId = customId;
        this.dataDate = dataDate;
    }

    /**
     * @param fileName
     * @param customId
     * @param dataDate
     * @param dataStatus
     * @param dataTime
     * @param downloadNum
     */
    public CustomDownloadRecord(String fileName, String customId, String dataDate, Integer dataStatus, Date dataTime,
            String downloadNum) {
        super();
        this.fileName = fileName;
        this.customId = customId;
        this.dataDate = dataDate;
        this.dataStatus = dataStatus;
        this.dataTime = dataTime;
        this.downloadNum = downloadNum;
    }


    public String getRecordId() {
        return recordId;
    }

    
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    
    public String getFileName() {
        return fileName;
    }

    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    
    public String getCustomId() {
        return customId;
    }

    
    public void setCustomId(String customId) {
        this.customId = customId;
    }

    
    public String getDataDate() {
        return dataDate;
    }

    
    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }

    
    public Integer getDataStatus() {
        return dataStatus;
    }

    
    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }

    
    public Date getDataTime() {
        return dataTime;
    }

    
    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    
    public String getDownloadNum() {
        return downloadNum;
    }

    
    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    @Override
    public String toString() {
        return "CustomDownloadRecord [recordId=" + recordId + ", fileName=" + fileName + ", customId=" + customId
                + ", dataDate=" + dataDate + ", dataStatus=" + dataStatus + ", dataTime=" + dataTime + ", downloadNum="
                + downloadNum + "]";
    }
    
    
}
