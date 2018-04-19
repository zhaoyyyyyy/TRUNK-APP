/*
 * @(#)StandardPushXmlBean.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
 
package com.asiainfo.biapp.si.loc.core.syspush.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.base.utils.Bean2XMLUtils;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;


/**
 * Title : StandardPushXmlBean
 * <p/>
 * Description : 推送其他系统的xml的bean
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date         Modified By    Why & What is modified</pre>
 * <pre>1    2018年2月26日     hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年2月26日
 */

public class StandardPushXmlBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Data data = new Data();
	private Title title = new Title();

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public class Title {

		private String taskDesc; //任务描述
		private Date sendTime; //发送时间

		public String getTaskDesc() {
			return taskDesc;
		}

		public void setTaskDesc(String taskDesc) {
			this.taskDesc = taskDesc;
		}

		public Date getSendTime() {
			return sendTime;
		}

		public void setSendTime(Date sendTime) {
			this.sendTime = sendTime;
		}

		@Override
		public String toString() {
			return "Title [taskDesc=" + taskDesc + ", sendTime=" + sendTime
					+ "]";
		}

	}

	public class Data {
		private String reqId; //推送请求ID
		private String platformCode; //平台Code：COC，用于外部系统判断推送方
		private Long rowNumber; //记录数
		private String userId; //用户ID
		private String uploadFileName; //上传文件名
		private String uploadFileType; //上传文件类型
		private String uploadFileDesc; //上传文件描述
		private Integer dataCycle; //周期类型：1,一次性;2,月周期;3,日周期;
		private String dataCycleDesc; //周期类型描述：1,一次性;2,月周期;3,日周期;
		private String customGroupId; //客户群Id:KHQ + 地市ID + 8位顺序码
		private String customGroupName; //客户群名称
		private String customGroupDesc; //客户群描述
		private String dataDate; //统计周期：如果是月周期就是yyyyMM，如果是日周期就是yyyyMMdd
		private List<Column> columns; //列集合
		
		private String customRules; //客户群规则
		private String crtPersnName; //创建人名称
		private Date crtTime; //创建时间
		private Date effective_time;//生效时间
		private Date fail_time; //失效时间
		private String pushToUserIds;//推送给其他人ID，逗句分隔
		private Integer isHasList ;//是否推送清单标志  0：未推送清单、1：已推送清单
		
		private String isPrivate;//1是非共享，0是共享

		public void addColumn(Column column) {
			if (columns == null) {
				columns = new ArrayList<Column>();
			}
			columns.add(column);
		}

		public String getReqId() {
			return reqId;
		}

		public void setReqId(String reqId) {
			this.reqId = reqId;
		}

		public String getPlatformCode() {
			return platformCode;
		}

		public void setPlatformCode(String platformCode) {
			this.platformCode = platformCode;
		}

		public Long getRowNumber() {
			return rowNumber;
		}

		public void setRowNumber(Long rowNumber) {
			this.rowNumber = rowNumber;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getUploadFileName() {
			return uploadFileName;
		}

		public void setUploadFileName(String uploadFileName) {
			this.uploadFileName = uploadFileName;
		}

		public String getUploadFileType() {
			return uploadFileType;
		}

		public void setUploadFileType(String uploadFileType) {
			this.uploadFileType = uploadFileType;
		}

		public String getUploadFileDesc() {
			return uploadFileDesc;
		}

		public void setUploadFileDesc(String uploadFileDesc) {
			this.uploadFileDesc = uploadFileDesc;
		}

		public Integer getDataCycle() {
			return dataCycle;
		}

		public void setDataCycle(Integer dataCycle) {
			this.dataCycle = dataCycle;
		}

		public String getDataCycleDesc() {
			return dataCycleDesc;
		}

		public void setDataCycleDesc(String dataCycleDesc) {
			this.dataCycleDesc = dataCycleDesc;
		}

		public String getCustomGroupId() {
			return customGroupId;
		}

		public void setCustomGroupId(String customGroupId) {
			this.customGroupId = customGroupId;
		}

		public String getCustomGroupName() {
			return customGroupName;
		}

		public void setCustomGroupName(String customGroupName) {
			this.customGroupName = customGroupName;
		}

		public String getCustomGroupDesc() {
			return customGroupDesc;
		}

		public void setCustomGroupDesc(String customGroupDesc) {
			this.customGroupDesc = customGroupDesc;
		}

		public String getDataDate() {
			return dataDate;
		}

		public void setDataDate(String dataDate) {
			this.dataDate = dataDate;
		}

		public List<Column> getColumns() {
			return columns;
		}

		public void setColumns(List<Column> columns) {
			this.columns = columns;
		}
		
		public String getCustomRules() {
			return customRules;
		}

		public void setCustomRules(String customRules) {
			this.customRules = customRules;
		}

		public String getCrtPersnName() {
			return crtPersnName;
		}

		public void setCrtPersnName(String crtPersnName) {
			this.crtPersnName = crtPersnName;
		}

		public Date getCrtTime() {
			return crtTime;
		}

		public void setCrtTime(Date crtTime) {
			this.crtTime = crtTime;
		}

		public Date getEffective_time() {
			return effective_time;
		}

		public void setEffective_time(Date effective_time) {
			this.effective_time = effective_time;
		}

		public Date getFail_time() {
			return fail_time;
		}

		public void setFail_time(Date fail_time) {
			this.fail_time = fail_time;
		}

		public String getPushToUserIds() {
			return pushToUserIds;
		}

		public void setPushToUserIds(String pushToUserIds) {
			this.pushToUserIds = pushToUserIds;
		}

		
        public Integer getIsHasList() {
            return isHasList;
        }

        
        public void setIsHasList(Integer isHasList) {
            this.isHasList = isHasList;
        }

		public String getIsPrivate() {
			return isPrivate;
		}

		public void setIsPrivate(String isPrivate) {
			this.isPrivate = isPrivate;
		}

        @Override
		public String toString() {
			return "Data [reqId=" + reqId + ", platformCode=" + platformCode
					+ ", rowNumber=" + rowNumber + ", userId=" + userId
					+ ", uploadFileName=" + uploadFileName
					+ ", uploadFileType=" + uploadFileType
					+ ", uploadFileDesc=" + uploadFileDesc + ", dataCycle="
					+ dataCycle + ", dataCycleDesc=" + dataCycleDesc
					+ ", customGroupId=" + customGroupId + ", customGroupName="
					+ customGroupName + ", customGroupDesc=" + customGroupDesc
					+ ", dataDate=" + dataDate + ", columns=" + columns 
					+ ", isPrivate="+ isPrivate + "]";
		}

		public Column newColumn() {
			Column col = new Column();
			return col;
		}
		
		public class Column {
			private String columnName; //列名（数据库中的列名）
			private String columnCnName; //列的中文名称
			private String columnDataType; //列数据类型（数据库中的类型）
			private String columnLength;//列数据类型长度
			private Integer isPrimaryKey; //是否主键：1、是；0、否

			public String getColumnName() {
				return columnName;
			}

			public void setColumnName(String columnName) {
				this.columnName = columnName;
			}

			public String getColumnCnName() {
				return columnCnName;
			}

			public void setColumnCnName(String columnCnName) {
				this.columnCnName = columnCnName;
			}

			public String getColumnDataType() {
				return columnDataType;
			}

			public void setColumnDataType(String columnDataType) {
				this.columnDataType = columnDataType;
			}

			public Integer getIsPrimaryKey() {
				return isPrimaryKey;
			}

			public void setIsPrimaryKey(Integer isPrimaryKey) {
				this.isPrimaryKey = isPrimaryKey;
			}
			
			public String getColumnLength() {
				return columnLength;
			}

			public void setColumnLength(String columnLength) {
				this.columnLength = columnLength;
			}

			@Override
			public String toString() {
				return "Column [columnName=" + columnName + ", columnCnName="
						+ columnCnName + ", columnDataType=" + columnDataType
						+ ", isPrimaryKey=" + isPrimaryKey + "]";
			}

		}
	}

	@Override
	public String toString() {
		return "StandardPushXmlBean [data=" + data + ", title=" + title + "]";
	}

	public static void main(String arg[]) {
		StandardPushXmlBean bean = new StandardPushXmlBean();
		bean.getTitle().setTaskDesc("广告平台推送任务");
		bean.getTitle().setSendTime(new Date());

		bean.getData().setReqId("COC20130717151315349");
		bean.getData().setPlatformCode("COC");
		bean.getData().setRowNumber(23456L);
		bean.getData().setUserId("admin");
		bean.getData().setUploadFileName("COC_UserId_20130717151315349.zip");
		bean.getData().setUploadFileType("zip"); //上传文件类型
		bean.getData().setUploadFileDesc("描述一下"); //上传文件描述
		bean.getData().setDataCycle(2); //周期类型：1,一次性;2,月周期;3,日周期;
		bean.getData().setDataCycleDesc("月周期"); //周期类型描述：1,一次性;2,月周期;3,日周期;
		bean.getData().setCustomGroupId("KHQ57100000388"); //客户群Id:KHQ + 地市ID + 8位顺序码
		bean.getData().setCustomGroupName("客户群名称"); //客户群名称
		bean.getData().setCustomGroupDesc("客户群描述"); //客户群描述
		bean.getData().setDataDate("201408"); //统计周期：如果是月周期就是yyyyMM，如果是日周期就是yyyyMMdd
		//获取配置文件中手机号列名称 20160615 add (spark中是phone_no)
//		String product_no = Configure.getInstance().getProperty("RELATED_COLUMN");
		String product_no = LabelInfoContants.KHQ_CROSS_COLUMN;
		
		
		List<StandardPushXmlBean.Data.Column> columns = new ArrayList<StandardPushXmlBean.Data.Column>();
		StandardPushXmlBean.Data.Column col1 = bean.getData().newColumn();
		//col1.setColumnName("PRODUCT_NO");
		col1.setColumnName(product_no);
		col1.setColumnCnName("手机号");
		col1.setColumnDataType("varchar2(32)");
		col1.setIsPrimaryKey(1);
		columns.add(col1);
		StandardPushXmlBean.Data.Column col2 = bean.getData().newColumn();
		//col2.setColumnName("PRODUCT_NO");
		col2.setColumnName(product_no);
		col2.setColumnCnName("手机号");
		col2.setColumnDataType("varchar2(32)");
		col2.setIsPrimaryKey(1);
		columns.add(col2);
		bean.getData().setColumns(columns); //列集合
		
		String xmlStr = null;
		try {
			xmlStr = Bean2XMLUtils.bean2XmlString(bean);
		} catch (Exception e) {
		    LogUtil.error("bean2Xml Exception:", e);
		}
		System.out.println(xmlStr);
	}
}
