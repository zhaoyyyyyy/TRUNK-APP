/*
 * @(#)AllUserMsg.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;

/**
 * Title : AllUserMsg
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
 * 1    2018年1月24日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2018年1月24日
 */

@Entity
@Table(name = "LOC_ALL_USER_MSG")
public class AllUserMsg extends BaseEntity {

    private static final long serialVersionUID = 2035013017939483936L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "PRI_KEY")
    @ApiParam(value = "主键ID")
    private String priKey;

    @Column(name = "TABLE_DESC")
    @ApiParam(value = "表描述信息")
    private String tableDesc;

    @Column(name = "IS_PARTITION")
    @ApiParam(value = "是否分区")
    private String isPartition;

    @Column(name = "DAY_TABLE_NAME")
    @ApiParam(value = "日表表名")
    private String dayTableName;

    @Column(name = "DAY_MAIN_COLUMN")
    @ApiParam(value = "日表主键字段")
    private String dayMainColumn;

    @Column(name = "DAY_PARTITION_COLUMN")
    @ApiParam(value = "日表分区字段")
    private String dayPartitionColumn;

    @Column(name = "MONTH_TABLE_NAME")
    @ApiParam(value = "月表表名")
    private String monthTableName;

    @Column(name = "MONTH_MAIN_COLUMN")
    @ApiParam(value = "月表主键字段")
    private String monthMainColumn;

    @Column(name = "MONTH_PARTITION_COLUMN")
    @ApiParam(value = "月表分区字段")
    private String monthPartitionColumn;

    @Column(name = "OTHER_COLUMN")
    @ApiParam(value = "其他字段")
    private String otherColumn;

    @Transient
    private String dimOrgLevelStr;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "LOC_CONFIG_TABLE_REL", joinColumns = { @JoinColumn(name = "PRI_KEY") }, inverseJoinColumns = {
            @JoinColumn(name = "CONFIG_ID") })
    private Set<PreConfigInfo> preConfigInfoSet;

    public String getDimOrgLevelStr() {
        return dimOrgLevelStr;
    }

    public void setDimOrgLevelStr(String dimOrgLevelStr) {
        this.dimOrgLevelStr = dimOrgLevelStr;
    }

    public Set<PreConfigInfo> getPreConfigInfoSet() {
        return preConfigInfoSet;
    }

    public void setPreConfigInfoSet(Set<PreConfigInfo> preConfigInfoSet) {
        this.preConfigInfoSet = preConfigInfoSet;
    }

    public String getPriKey() {
        return priKey;
    }

    public void setPriKey(String priKey) {
        this.priKey = priKey;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public String getDayTableName() {
        return dayTableName;
    }

    public void setDayTableName(String dayTableName) {
        this.dayTableName = dayTableName;
    }

    public String getMonthTableName() {
        return monthTableName;
    }

    public void setMonthTableName(String monthTableName) {
        this.monthTableName = monthTableName;
    }

    public String getDayMainColumn() {
        return dayMainColumn;
    }

    public void setDayMainColumn(String dayMainColumn) {
        this.dayMainColumn = dayMainColumn;
    }

    public String getMonthMainColumn() {
        return monthMainColumn;
    }

    public void setMonthMainColumn(String monthMainColumn) {
        this.monthMainColumn = monthMainColumn;
    }

    public String getIsPartition() {
        return isPartition;
    }

    public void setIsPartition(String isPartition) {
        this.isPartition = isPartition;
    }

    public String getDayPartitionColumn() {
        return dayPartitionColumn;
    }

    public void setDayPartitionColumn(String dayPartitionColumn) {
        this.dayPartitionColumn = dayPartitionColumn;
    }

    public String getMonthPartitionColumn() {
        return monthPartitionColumn;
    }

    public void setMonthPartitionColumn(String monthPartitionColumn) {
        this.monthPartitionColumn = monthPartitionColumn;
    }

    public String getOtherColumn() {
        return otherColumn;
    }

    public void setOtherColumn(String otherColumn) {
        this.otherColumn = otherColumn;
    }

}
