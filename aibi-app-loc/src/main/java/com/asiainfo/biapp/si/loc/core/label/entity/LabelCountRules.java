/*
 * @(#)LabelCountRules.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.entity;

import io.swagger.annotations.ApiParam;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

/**
 * Title : LabelCountRules
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
 * 1    2017年11月20日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月20日
 */
@Entity
@Table(name = "DIM_LABEL_COUNT_RULES")
public class LabelCountRules extends BaseEntity {

    private static final long serialVersionUID = 2035013017939483936L;

    /**
     * 规则编码
     */
    @Id
    @Column(name = "COUNT_RULES_CODE")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "规则编码")
    private String countRulesCode;

    /**
     * 规则依赖的指标
     */
    @Column(name = "DEPEND_INDEX")
    @ApiParam(value = "规则依赖的指标")
    private String dependIndex;

    /**
     * 具体规则
     */
    @Column(name = "COUNT_RULES")
    @ApiParam(value = "具体规则")
    private String countRules;

    /**
     * 规则描述
     */
    @Column(name = "COUNT_RULES_DESC")
    @ApiParam(value = "规则描述")
    private String countRulesDesc;

    /**
     * 过滤条件
     */
    @Column(name = "WHERE_SQL")
    @ApiParam(value = "过滤条件")
    private String whereSql;

    public String getCountRulesCode() {
        return countRulesCode;
    }

    public void setCountRulesCode(String countRulesCode) {
        this.countRulesCode = countRulesCode;
    }

    public String getDependIndex() {
        return dependIndex;
    }

    public void setDependIndex(String dependIndex) {
        this.dependIndex = dependIndex;
    }

    public String getCountRules() {
        return countRules;
    }

    public void setCountRules(String countRules) {
        this.countRules = countRules;
    }

    public String getCountRulesDesc() {
        return countRulesDesc;
    }

    public void setCountRulesDesc(String countRulesDesc) {
        this.countRulesDesc = countRulesDesc;
    }

    public String getWhereSql() {
        return whereSql;
    }

    public void setWhereSql(String whereSql) {
        this.whereSql = whereSql;
    }

}
