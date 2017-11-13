
package com.asiainfo.biapp.si.coc.jauth.log.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;

@Entity
@Table(name="LOC_LOG_INTER_DETAIL")
public class LogInterfaceDetail extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@Id @Column(name="LOG_ID")
	@GenericGenerator(name="idGenerator",strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String logId;

	@Column(name="USER_ID")
	public String userId;

	@Column(name="SYS_ID")
	public String sysId;
	
	@Column(name="INTERFACE_NAME")
	public String interfaceName;
	
	@Column(name="INTERFACE_URL")
	public String interfaceUrl;
	
	@Column(name="OP_TIME")
	public Date opTime;
	
	@Column(name="IP_ADDR")
	public String ipAddr;
	
	@Column(name="OUTPUT_PARAMS")
	public String outputParams;
	
	@Column(name="INPUT_PARAMS")
	public String inputParams;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getInterfaceUrl() {
		return interfaceUrl;
	}

	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}
	
    public Date getOpTime() {
        return opTime;
    }
    
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getOutputParams() {
		return outputParams;
	}

	public void setOutputParams(String outputParams) {
		this.outputParams = outputParams;
	}

	public String getInputParams() {
		return inputParams;
	}

	public void setInputParams(String inputParams) {
		this.inputParams = inputParams;
	}
	
	
}
