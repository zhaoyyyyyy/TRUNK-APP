/*
 * @(#)LabelVerticalColumnRelId.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.entity;

import javax.persistence.Column;
import javax.persistence.Id;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;

/**
 * Title : LabelVerticalColumnRelId
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
 * <pre>1    2018年2月8日    lilin7        Created</pre>
 * <p/>
 *
 * @author  lilin7
 * @version 1.0.0.2018年2月8日
 */
public class LabelVerticalColumnRelId extends BaseEntity{
    /**  */
    private static final long serialVersionUID = 1L;

    /**
     * 标签ID
     */
    @Column(name = "LABEL_ID")
    @ApiParam(value = "标签ID")
    private String labelId;
    
    /**
     * 列ID
     */
    @Column(name = "COLUMN_ID")
    @ApiParam(value = "列ID")
    private String columnId;

    
    public String getLabelId() {
        return labelId;
    }

    
    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    
    public String getColumnId() {
        return columnId;
    }

    
    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }
    
    

}
