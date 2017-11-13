
package com.asiainfo.biapp.si.coc.jauth.log.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;
/**
 * 
 * Title : LogTaskExecuteDetail
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
 * <pre>1    2017年10月24日    panweiwei        Created</pre>
 * <p/>
 *
 * @author  panweiwei
 * @version 1.0.0.2017年10月24日
 */
@Entity
@Table(name = "loc_log_taskexe_detail")
public class LogTaskExecuteDetail extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@Id @Column(name="log_id")
	@GenericGenerator(name="idGenerator", strategy="uuid") 
	@GeneratedValue(generator="idGenerator") //使用uuid的生成策略 
	private String logId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="sys_id")
	private String sysId;
	
	@Column(name="task_ext_id")
	private String taskExtId;
	
	@Column(name="exe_params")
	private String exeParams;
	
	@Column(name="exe_type")
	private String exeType;
	
	@Column(name="start_time")
	private Date startTime;
	
	@Column(name="status")
	private String status;
	
	@Column(name="return_msg")
	private String returnMsg;
	
	@Column(name="node_name")
	private String nodeName;

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

	public String getTaskExtId() {
		return taskExtId;
	}

	public void setTaskExtId(String taskExeId) {
		this.taskExtId = taskExeId;
	}

	public String getExeParams() {
		return exeParams;
	}

	public void setExeParams(String exeParams) {
		this.exeParams = exeParams;
	}

    public String getExeType() {
        return exeType;
    }

    
    public void setExeType(String exeType) {
        this.exeType = exeType;
    }

    
    public Date getStartTime() {
        return startTime;
    }

    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

}
