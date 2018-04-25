/*
 * @(#)DimOrgLevelId.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;

/**
 * Title : DimOrgLevelId
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
@Embeddable
public class DimOrgLevelId extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "PRI_KEY")
    @ApiParam(value = "主键ID")
    private String priKey;

    @Column(name = "ORG_COLUMN_NAME")
    @ApiParam(value = "组织字段名称")
    private String orgColumnName;

    public String getPriKey() {
        return priKey;
    }

    public void setPriKey(String priKey) {
        this.priKey = priKey;
    }

    public String getOrgColumnName() {
        return orgColumnName;
    }

    public void setOrgColumnName(String orgColumnName) {
        this.orgColumnName = orgColumnName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orgColumnName == null) ? 0 : orgColumnName.hashCode());
        result = prime * result + ((priKey == null) ? 0 : priKey.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DimOrgLevelId other = (DimOrgLevelId) obj;
        if (orgColumnName == null) {
            if (other.orgColumnName != null)
                return false;
        } else if (!orgColumnName.equals(other.orgColumnName))
            return false;
        if (priKey == null) {
            if (other.priKey != null)
                return false;
        } else if (!priKey.equals(other.priKey))
            return false;
        return true;
    }

}
