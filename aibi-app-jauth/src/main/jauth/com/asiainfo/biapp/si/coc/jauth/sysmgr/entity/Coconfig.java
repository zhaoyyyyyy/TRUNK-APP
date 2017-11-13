package com.asiainfo.biapp.si.coc.jauth.sysmgr.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.OrderBy;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;

/**
 * @describe 配置
 * @author zhangnan
 * @date 2017-9-27
 */
@Entity
@Table(name = "LOC_CONFIG_INFO")
public class Coconfig extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 配置键
	 */
	@Id@Column(name = "config_key")
	private String configKey;
	/**
	 * 系统ID
	 */
	@Column(name = "sys_id")
	private String sysId;
	/**
	 * 配置值
	 */
	@Column(name = "config_val")
	private String configVal;
	/**
	 * 配置值类型
	 */
	@Column(name = "config_val_type")
	private Integer configValType;
	@Transient
	private String configValTypes;

	public String getConfigValTypes() {
		return configValTypes;
	}

	public void setConfigValTypes(String configValTypes) {
		this.configValTypes = configValTypes;
	}

	/**
	 * 配置描述
	 */
	@Column(name = "config_desc")
	private String configDesc;
	/**
	 * 是否显示
	 */
	@Column(name = "is_show_page")
	private Integer isShowPage;
	/**
	 * 状态
	 */
	@Column(name = "status")
	private Integer status;
	/**
	 * 父配置键
	 */
	@Column(name = "parent_key")
	private String parentKey;
	/**
	 * 字典编码
	 */
	@Column(name = "dim_code")
	private String dimCode;
	/** 子配置列表 */
	@OrderBy(clause = "config_key")
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "parent_key")
	private Set<Coconfig> children = new HashSet<>(0);
	@Column(name = "config_name")
	private String configName;

	public String getConfigName() {
		if (StringUtils.isNotEmpty(configName)) {
			return configName;
		} else if (StringUtils.isNotEmpty(getConfigDesc())) {
			return getConfigDesc();
		} else {
			return getConfigKey();
		}
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getConfigVal() {
		return configVal;
	}

	public void setConfigVal(String configVal) {
		this.configVal = configVal;
	}

	public Integer getConfigValType() {
		return configValType;
	}

	public void setConfigValType(Integer configValType) {
		this.configValType = configValType;
	}

	public String getConfigDesc() {
		return configDesc;
	}

	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}

	public Integer getIsShowPage() {
		return isShowPage;
	}

	public void setIsShowPage(Integer isShowPage) {
		this.isShowPage = isShowPage;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public String getDimCode() {
		return dimCode;
	}

	public void setDimCode(String dimCode) {
		this.dimCode = dimCode;
	}

	public Set<Coconfig> getChildren() {
		return children;
	}

	public void setChildren(Set<Coconfig> children) {
		this.children = children;
	}

}
