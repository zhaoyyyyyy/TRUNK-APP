/*
 * @(#)LabelStatus.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.label.service.IMdaSysTableColService;
import com.asiainfo.biapp.si.loc.core.label.service.IMdaSysTableService;

import io.swagger.annotations.ApiParam;

/**
 * Title : LabelStatus
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
 * <pre>1    2017年11月20日     wangrd        Created</pre>
 * <p/>
 *
 * @author   wangrd
 * @version 1.0.0.2017年11月20日
 */
@Entity
@Table(name = "DIM_LABEL_STATUS")
public class LabelStatus extends BaseEntity{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 标签编码
     */
    @Id
    @Column(name = "LABEL_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "标签编码")
    private String labelId;
    
    /**
     * 数据日期
     */
    @Column(name = "DATA_DATE")
    @ApiParam(value = "数据日期")
    private String dataDate;
    
    /**
     * 数据生成状态
     */
    @Column(name = "DATA_STATUS")
    @ApiParam(value = "数据生成状态")
    private Integer dataStatus;
    
    /**
     * 数据插入时间
     */
    @Column(name = "DATA_INSERT_TIME")
    @ApiParam(value = "数据插入时间")
    private Date dataInsertTime;
    
    /**
     * 错误信息描述
     */
    @Column(name = "EXCEPTION_DESC")
    @ApiParam(value = "错误信息描述")
    private String exceptionDesc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LABEL_ID", referencedColumnName = "LABEL_ID", insertable = false, updatable = false)
    private LabelInfo labelInfo;
    
    @Transient
    private String mdaSysTableColumnIds;
    
    @Transient
    private String mdaSysTableColumnNames;
    
    @Transient
    private MdaSysTable mdaSysTable;
    
    @Transient
    private String configId;

    public String getLabelId() {
        return labelId;
    }

    
    public void setLabelId(String labelId) {
        this.labelId = labelId;
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

    
    public Date getDataInsertTime() {
        return dataInsertTime;
    }

    
    public void setDataInsertTime(Date dataInsertTime) {
        this.dataInsertTime = dataInsertTime;
    }

    
    public String getExceptionDesc() {
        return exceptionDesc;
    }

    
    public void setExceptionDesc(String exceptionDesc) {
        this.exceptionDesc = exceptionDesc;
    }


    public LabelInfo getLabelInfo() {
        return labelInfo;
    }


    public void setLabelInfo(LabelInfo labelInfo) {
        this.labelInfo = labelInfo;
    }

    public String getMdaSysTableColumnIds() {
        StringBuilder columnIds = new StringBuilder();
        try {
            IMdaSysTableColService mdaSysTableColService = (IMdaSysTableColService) SpringContextHolder
                .getBean("mdaSysTableColServiceImpl");
            IMdaSysTableService mdaSysTableService = (IMdaSysTableService) SpringContextHolder
                .getBean("mdaSysTableServiceImpl");
            List<MdaSysTableColumn> mdaSysTableColumnList = mdaSysTableColService
                .selectMdaSysTableColListBylabelId(labelId);
            if (mdaSysTableColumnList.size() > 0) {
                for (MdaSysTableColumn mdaSysTableColumn : mdaSysTableColumnList) {
                    if (StringUtil.isNotEmpty(mdaSysTableColumn.getColumnId())) {
                        columnIds.append(mdaSysTableColumn.getColumnId()).append(",");
                    }
                    if (StringUtil.isNotEmpty(mdaSysTableColumn.getTableId())) {
                        this.setMdaSysTable(mdaSysTableService.selectMdaSysTableById(mdaSysTableColumn.getTableId()));
                    }
                }
                columnIds.deleteCharAt(columnIds.length() - 1);
            }
        } catch (BaseException e) {
            LogUtil.error(e);
        }
        return columnIds.toString();
    }

    
    public void setMdaSysTableColumnIds(String mdaSysTableColumnIds) {
        this.mdaSysTableColumnIds = mdaSysTableColumnIds;
    }

    
    public String getMdaSysTableColumnNames() {
        IMdaSysTableColService mdaSysTableColService = (IMdaSysTableColService) SpringContextHolder
            .getBean("mdaSysTableColServiceImpl");
        List<MdaSysTableColumn> mdaSysTableColumnList = mdaSysTableColService
            .selectMdaSysTableColListBylabelId(labelId);
        StringBuilder columnNames = new StringBuilder();
        if (mdaSysTableColumnList.size() > 0) {
            for (MdaSysTableColumn mdaSysTableColumn : mdaSysTableColumnList) {
                if (StringUtil.isNotEmpty(mdaSysTableColumn.getColumnName())) {
                    columnNames.append(mdaSysTableColumn.getColumnName()).append(",");
                }
            }
            columnNames.deleteCharAt(columnNames.length()-1);
        }
        return columnNames.toString();
    }

    
    public void setMdaSysTableColumnNames(String mdaSysTableColumnNames) {
        this.mdaSysTableColumnNames = mdaSysTableColumnNames;
    }


    public MdaSysTable getMdaSysTable() {
        return mdaSysTable;
    }


    public void setMdaSysTable(MdaSysTable mdaSysTable) {
        this.mdaSysTable = mdaSysTable;
    }
    
    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

}
