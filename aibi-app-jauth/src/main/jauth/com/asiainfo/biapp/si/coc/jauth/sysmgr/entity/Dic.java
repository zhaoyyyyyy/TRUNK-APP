package com.asiainfo.biapp.si.coc.jauth.sysmgr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;

/**
 * @describe 字典索引
 * @author zhougz
 * @date 2013-5-13
 */
@Entity
@Table(name="LOC_SYS_DIC")
public class Dic extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id @Column(name="ID")
	@GenericGenerator(name="idGenerator", strategy="uuid") 
	@GeneratedValue(generator="idGenerator") //使用uuid的生成策略  
    private String id;
	
	@Column(name="note")
	private String note;
	
	@Column(name="diccode")
	private String dicCode;
	
	@Column(name="dicname")
	private String dicName;
	
	@Column(name="dictype")
	private String dicType;
	
	@Column(name="appsyscode")
	private String appSysCode;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public String getDicType() {
		return dicType;
	}

	public void setDicType(String dicType) {
		this.dicType = dicType;
	}

	public String getAppSysCode() {
		return appSysCode;
	}

	public void setAppSysCode(String appSysCode) {
		this.appSysCode = appSysCode;
	}

}
