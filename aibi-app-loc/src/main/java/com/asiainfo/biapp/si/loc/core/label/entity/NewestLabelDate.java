/*
 * @(#)NewestLabelDate.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;

/**
 * Title : NewestLabelDate
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
 * <pre>1    2017年11月21日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2017年11月21日
 */
@Entity
@Table(name = "LOC_NEWEST_LABEL_DATE")
public class NewestLabelDate extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 最新日数据日期
     */
    @Id
    @Column(name = "DAY_NEWEST_DATE")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "最新日数据日期")
    private String dayNewestDate;
    
    /**
     * 最新月数据月份
     */
    @Column(name = "MONTH_NEWEST_DATE")
    @ApiParam(value = "最新月数据月份")
    private String monthNewestDate;
    
    /**
     * 是否统计过日数据
     */
    @Column(name = "DAY_NEWEST_STATUS")
    @ApiParam(value = "是否统计过日数据")
    private Integer dayNewestStatus;
    
    /**
     * 是否统计过月数据
     */
    @Column(name = "MONTH_NEWEST_STATUS")
    @ApiParam(value = "是否统计过月数据")
    private Integer monthNewestStatus;

    
    public String getDayNewestDate() {
        return dayNewestDate;
    }

    
    public void setDayNewestDate(String dayNewestDate) {
        this.dayNewestDate = dayNewestDate;
    }

    
    public String getMonthNewestDate() {
        return monthNewestDate;
    }

    
    public void setMonthNewestDate(String monthNewestDate) {
        this.monthNewestDate = monthNewestDate;
    }

    
    public Integer getDayNewestStatus() {
        return dayNewestStatus;
    }

    
    public void setDayNewestStatus(Integer dayNewestStatus) {
        this.dayNewestStatus = dayNewestStatus;
    }

    
    public Integer getMonthNewestStatus() {
        return monthNewestStatus;
    }

    
    public void setMonthNewestStatus(Integer monthNewestStatus) {
        this.monthNewestStatus = monthNewestStatus;
    }
    
    
}
