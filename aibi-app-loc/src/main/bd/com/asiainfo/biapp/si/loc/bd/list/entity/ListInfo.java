package com.asiainfo.biapp.si.loc.bd.list.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;
/**
 * Title : LOC_LIST_INFO
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月20日    lilin7        Created
 * </pre>
 * <p/>
 *
 * @author wanghf5
 * @version 1.0.0.2017年11月20日
 */
@Entity
@Table(name = "LOC_LIST_INFO")
public class ListInfo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ListInfoId listInfo;
	
	/**
	 * 清单总数
	 */
	@Column(name = "GROUP_NUM")
	@ApiParam(value = "清单总数")
	private Integer customNum;
	
	@Transient
	private String customGroupName;
	
	/**
	 * 清单状态
	 */
	@Column(name = "DATA_STATUS")
	@ApiParam(value = "清单状态")
	private Integer dataStatus;
	
	/**
	 * 数据生成时间
	 */
	@Column(name = "DATA_TIME")
	@ApiParam(value = "数据生成时间")
	private Date dataTime;
	
	/**
	 * 清单临时表名
	 */
	@Column(name = "LIST_TABLE_NAME")
	@ApiParam(value = "清单临时表名")
	private String listTableName;


	public Integer getCustomNum() {
		return customNum;
	}

	public void setCustomNum(Integer customNum) {
		this.customNum = customNum;
	}

	public Integer getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
	}

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

	public String getListTableName() {
		return listTableName;
	}

	public void setListTableName(String listTableName) {
		this.listTableName = listTableName;
	}

	public ListInfoId getListInfoId() {
		return listInfo;
	}

	public void setListInfoId(ListInfoId listInfo) {
		this.listInfo = listInfo;
	}

	public String getCustomGroupName() {
		return customGroupName;
	}

	public void setCustomGroupName(String customGroupName) {
		this.customGroupName = customGroupName;
	}

	
}
