/*
 * @(#)OtherSysXmlBean.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
 
package com.asiainfo.biapp.si.loc.base.utils.model;

import java.io.Serializable;
import java.util.Date;

import com.asiainfo.biapp.si.loc.base.utils.Bean2XMLUtils;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;


/**
 * Title : OtherSysXmlBean
 * <p/>
 * Description : 标签推送设置信息表服务类
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

public class OtherSysXmlBean implements Serializable {
	
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
		private String uploadFileName; //上传zip文件名
		private String MD5Check; //文件MD5验证码
		private String rowNumber; //记录数
		private String userId; //用户ID
		private String customName; //客户群名称
		private String dataTime; //客户群统计时间（月份）
		private String publishDesc; //客户群描述
		private String customRules; //客户群规则
		private String usedLabelOrProduct; //用到的标签或者产品
		
		private String listTableName; 	//清单表名	2016-09-29 Mr.Hongfb add

		//清单表名	2016-10-17 Mr.Hongfb add start
		private String updateCycle;		//表存储周期：0,一次性;1,月周期;2,日周期	
		private String startDate; 		//开始时间
		private String endDate; 		//结束时间
		private String monthLabelDate;	//记录客户群所使用的月标签的数据时间
		private String dayLabelDate;	//记录客户群所使用的日标签的数据时间
		private String customGroupId; 	//客户群ID
		//清单表名	2016-10-17 Mr.Hongfb add end
		
		private String isPrivate;//1是非共享，0是共享

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

		public String getUploadFileName() {
			return uploadFileName;
		}

		public void setUploadFileName(String uploadFileName) {
			this.uploadFileName = uploadFileName;
		}

		public String getMD5Check() {
			return MD5Check;
		}

		public void setMD5Check(String MD5Check) {
			this.MD5Check = MD5Check;
		}

		public String getRowNumber() {
			return rowNumber;
		}

		public void setRowNumber(String rowNumber) {
			this.rowNumber = rowNumber;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getCustomName() {
			return customName;
		}

		public void setCustomName(String customName) {
			this.customName = customName;
		}

		public String getDataTime() {
			return dataTime;
		}

		public void setDataTime(String dataTime) {
			this.dataTime = dataTime;
		}

		public String getPublishDesc() {
			return publishDesc;
		}

		public void setPublishDesc(String publishDesc) {
			this.publishDesc = publishDesc;
		}

		public String getCustomRules() {
			return customRules;
		}

		public void setCustomRules(String customRules) {
			this.customRules = customRules;
		}

		public String getUsedLabelOrProduct() {
			return usedLabelOrProduct;
		}

		public void setUsedLabelOrProduct(String usedLabelOrProduct) {
			this.usedLabelOrProduct = usedLabelOrProduct;
		}

		public String getListTableName() {
			return listTableName;
		}

		public void setListTableName(String listTableName) {
			this.listTableName = listTableName;
		}

		public String getUpdateCycle() {
			return updateCycle;
		}

		public void setUpdateCycle(String updateCycle) {
			this.updateCycle = updateCycle;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getMonthLabelDate() {
			return monthLabelDate;
		}

		public void setMonthLabelDate(String monthLabelDate) {
			this.monthLabelDate = monthLabelDate;
		}

		public String getDayLabelDate() {
			return dayLabelDate;
		}

		public void setDayLabelDate(String dayLabelDate) {
			this.dayLabelDate = dayLabelDate;
		}

		public String getCustomGroupId() {
			return customGroupId;
		}

		public void setCustomGroupId(String customGroupId) {
			this.customGroupId = customGroupId;
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
					+ ", uploadFileName=" + uploadFileName + ", MD5Check="
					+ MD5Check + ", rowNumber=" + rowNumber + ", userId="
					+ userId + ", customName=" + customName + ", dataTime="
					+ dataTime + ", publishDesc=" + publishDesc
					+ ", customRules=" + customRules + ", usedLabelOrProduct="
					+ usedLabelOrProduct + "]";
		}

	}

	@Override
	public String toString() {
		return "OtherSysXmlBean [data=" + data + ", title=" + title + "]";
	}

//	public static void main(String arg[]) {
//		OtherSysXmlBean bean = new OtherSysXmlBean();
//		bean.getTitle().setTaskDesc("广告平台推送任务");
//		bean.getTitle().setSendTime(new Date());
//
//		bean.getData().setReqId("COC20130717151315349");
//		bean.getData().setPlatformCode("COC");
//		bean.getData().setUploadFileName("COC_UserId_20130717151315349.zip");
//		bean.getData().setRowNumber(Long.valueOf("23456").toString());
//		bean.getData().setUserId("admin");
//		bean.getData().setMD5Check("b73723ebd0ad59a49d108b59c27aa7b5");
//		bean.getData().setCustomName("客户群名称");
//		bean.getData().setDataTime("201306");
//		
//		String xmlStr = null;
//		try {
//			xmlStr = Bean2XMLUtils.bean2XmlString(bean);
//		} catch (Exception e) {
//		    LogUtil.error("InputStream is error:", e);
//		}
//		System.out.println(xmlStr);
//	}
}
