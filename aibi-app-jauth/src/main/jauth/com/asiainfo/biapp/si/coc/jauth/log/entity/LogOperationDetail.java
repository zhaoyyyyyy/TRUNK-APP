
package com.asiainfo.biapp.si.coc.jauth.log.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Resource;

/**
 * 
 * Title : LogOperDetail
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年10月20日    panweiwei        Created</pre>
 * <p/>
 *
 * @author  panweiwei
 * @version 1.0.0.2017年10月20日
 */
@Entity
@Table(name="LOC_LOG_OPERATION_DETAIL")
public class LogOperationDetail extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@Id @Column(name="LOG_ID")
	@GenericGenerator(name="idGenerator",strategy="uuid")
	@GeneratedValue(generator="idGenerator")
    private String logId;

	@Column(name="USER_ID")
	public String userId;
	
	@Column(name="SYS_ID")
	public String sysId;
	
	@Column(name="RESOURCE_ID")
	public String resourceId;
	
	@ManyToOne
	@JoinColumn(name="RESOURCE_ID",insertable=false,updatable=false)
	private Resource resource;
	

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
	@Column(name="OP_TIME")
	public Date opTime;
	
	@Column(name="IP_ADDR")
	public String ipAddr;
	
	@Column(name="PARAMS")
	public String params;
	
	
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

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	
	
}
