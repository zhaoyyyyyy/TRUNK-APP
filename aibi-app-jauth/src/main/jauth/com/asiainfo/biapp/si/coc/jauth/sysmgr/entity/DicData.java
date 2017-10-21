package com.asiainfo.biapp.si.coc.jauth.sysmgr.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;

/**
 * @describe 字典内容
 * @author zhougz
 * @date 2013-5-13
 */
@Entity
@Table(name="CI_SYS_DIC_DATA")
public class DicData extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id @Column(name="ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="idGenerator", strategy="uuid") 
	@GeneratedValue(generator="idGenerator") //使用uuid的生成策略  
    public String id;
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
	@Column(name="dic_code")
	public String dicCode ;
	
	@Column(name="code")
	public String code ;
	
	@Column(name="parent_id")
	public String parentId ;
	
	@Column(name="status")
	public String status ;
	
	@Column(name="note")
	public String note ;
	
	@Column(name="dataname")
	public String dataName;
	
	@Column(name="ordernum")
	public Integer orderNum;
	
	@Column(name="appsyscode")
	public String appSysCode ;
	
//	@OneToMany
//	@JoinColumn(name="parent_id",insertable=false,updatable=false)
//	public Set<DicData> children;
	  
	public String getDicCode() {
		return dicCode;
	}
	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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
	public String getAppSysCode() {
		return appSysCode;
	}
	public void setAppSysCode(String appSysCode) {
		this.appSysCode = appSysCode;
	}
//	public Set<DicData> getChildren() {
//		return children;
//	}
//	public void setChildren(Set<DicData> children) {
//		this.children = children;
//	}
	
}
