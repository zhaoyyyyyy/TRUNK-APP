package com.asiainfo.biapp.si.coc.jauth.sysmgr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;

/**
 * @describe 白名单
 * @author lilin
 * @date 2017-10-23
 */

@Entity
@Table(name="LOC_IP_WHITE_LIST")
public class IpWhiteList extends BaseEntity{

	/**
	 * @describe 用户白名单
	 * @author lilin
	 * @date 2017-10-23
	 */
	private static final long serialVersionUID = 1L;
	@Id @Column(name = "LIST_ID")
//	@GenericGenerator(name="idGenerator", strategy="uuid") 
	@GeneratedValue //使用uuid的生成策略
	private Integer listId;

	@Column(name = "IP_ADDRESS")
	public String ipAddress;

	@Column(name = "REQUEST_ADDRESS")
	public String requestAddress;

	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getRequestAddress() {
		return requestAddress;
	}

	public void setRequestAddress(String requestAddress) {
		this.requestAddress = requestAddress;
	}

}
