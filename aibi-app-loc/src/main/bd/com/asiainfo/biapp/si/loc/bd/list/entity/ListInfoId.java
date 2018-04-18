package com.asiainfo.biapp.si.loc.bd.list.entity;

import javax.persistence.Column;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;

public class ListInfoId extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 清单ID
	 */
	@Column(name = "GROUP_ID")
	@ApiParam(value = "清单ID")
	private String customGroupId;
	
	/**
	 * 数据日期
	 */
	@Column(name = "DATA_DATE")
	@ApiParam(value = "数据日期")
	private String dataDate;

	
	public String getCustomGroupId() {
		return customGroupId;
	}

	public void setCustomGroupId(String customGroupId) {
		this.customGroupId = customGroupId;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}
	
	
}
