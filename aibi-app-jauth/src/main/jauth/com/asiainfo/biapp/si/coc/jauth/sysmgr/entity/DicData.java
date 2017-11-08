package com.asiainfo.biapp.si.coc.jauth.sysmgr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;

/**
 * @describe 字典内容
 * @author zhougz
 * @date 2013-5-13
 */
@Entity
@Table(name="LOC_SYS_DIC_DATA")
public class DicData extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id @Column(name="ID")
	@GenericGenerator(name="idGenerator", strategy="uuid") 
	@GeneratedValue(generator="idGenerator") //使用uuid的生成策略  
    private String id;
	
	@Column(name="dic_code")
	private String dicCode ;
	
	@Column(name="code")
	private String code ;
	
	@Column(name="parent_id")
	private String parentId ;
	
	@Column(name="status")
	private String status ;
	
	@Column(name="note")
	private String note ;
	
	@Column(name="dataname")
	private String dataName;
	
	@Column(name="ordernum")
	private Integer orderNum;
	
	@Column(name="appsyscode")
	private String appSysCode ;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getAppSysCode() {
		return appSysCode;
	}

	public void setAppSysCode(String appSysCode) {
		this.appSysCode = appSysCode;
	}

}
