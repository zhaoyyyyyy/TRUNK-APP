package com.asiainfo.biapp.si.coc.jauth.sysmgr.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OrderBy;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;

/**
 * 
 * Title : ScheduleController
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年10月23日    chenchao3        Created</pre>
 * <p/>
 *
 * @author  chenchao3
 * @version 5.0.0.2017年10月23日
 */
@Entity
@Table(name = "LOC_TASK_EXE_INFO")
public class LocTaskExeInfo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id@Column(name = "task_exe_id")
	@GeneratedValue
	private Integer taskExeId;
	
	@Column(name = "task_exe_name")
	private String taskExeName;
	
	@Column(name = "parent_exe_id")
	private String parentExeId;
	
	@Column(name = "task_id")
	private String taskId;
	
	@Column(name = "sys_id")
	private String sysId;
	
	@Column(name = "task_exe_time")
	private String taskExeTime;
	
	@Column(name = "exe_type")
	private String exeType;
	
	@Column(name = "exe_status")
	private String exeStatus;

	@OrderBy(clause = "task_exe_id")
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "parent_exe_id")
	private Set<LocTaskExeInfo> children = new HashSet<>(0);

	public Integer getTaskExeId() {
		return taskExeId;
	}

	public void setTaskExeId(Integer taskExeId) {
		this.taskExeId = taskExeId;
	}

	public String getTaskExeName() {
		return taskExeName;
	}

	public void setTaskExeName(String taskExeName) {
		this.taskExeName = taskExeName;
	}

	public String getParentExeId() {
		return parentExeId;
	}

	public void setParentExeId(String parentExeId) {
		this.parentExeId = parentExeId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getTaskExeTime() {
		return taskExeTime;
	}

	public void setTaskExeTime(String taskExeTime) {
		this.taskExeTime = taskExeTime;
	}

	public String getExeType() {
		return exeType;
	}

	public void setExeType(String exeType) {
		this.exeType = exeType;
	}

	public String getExeStatus() {
		return exeStatus;
	}

	public void setExeStatus(String exeStatus) {
		this.exeStatus = exeStatus;
	}

	public Set<LocTaskExeInfo> getChildren() {
		return children;
	}

	public void setChildren(Set<LocTaskExeInfo> children) {
		this.children = children;
	}

}
