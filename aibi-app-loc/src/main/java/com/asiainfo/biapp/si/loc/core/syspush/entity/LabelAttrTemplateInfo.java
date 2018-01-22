/*
 * @(#)LabelAttrTemplateInfo.java
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
 * Title : LabelAttrTemplateInfo
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
 * <pre>1    2018年1月19日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月19日
 */
@Entity
@Table(name = "LOC_LABEL_ATTR_TEMPLATE_INFO")
public class LabelAttrTemplateInfo extends BaseEntity{

    private static final long serialVersionUID = 1L;
    
    /**
     * 模板编码
     */
    @Id
    @Column(name = "TEMPLATE_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "模板编码")
    private String templateId;
    
    /**
     * 模板名称
     */
    @Column(name = "TEMPLATE_NAME")
    @ApiParam(value = "模板名称")
    private String templateName;
    
    /**
     * 专区ID
     */
    @Column(name = "CONFIG_ID")
    @ApiParam(value = "专区ID")
    private Integer configId;
    
    /**
     * 创建用户
     */
    @Column(name = "CREATE_USER_ID")
    @ApiParam(value = "创建用户")
    private String createUserId;
    
    /**
     * 创建时间 
     */
    @Column(name = "CREATE_TIME")
    @ApiParam(value = "创建时间")
    private Date createTime;
    
    /**
     * 状态
     */
    @Column(name = "STATUS")
    @ApiParam(value = "状态")
    private Integer status;

    
    public String getTemplateId() {
        return templateId;
    }

    
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    
    public String getTemplateName() {
        return templateName;
    }

    
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    
    public Integer getConfigId() {
        return configId;
    }

    
    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    
    public String getCreateUserId() {
        return createUserId;
    }

    
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    
    public Date getCreateTime() {
        return createTime;
    }

    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }
    

}
