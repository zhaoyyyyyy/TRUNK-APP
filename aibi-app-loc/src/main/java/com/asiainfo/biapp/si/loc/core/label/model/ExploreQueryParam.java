
package com.asiainfo.biapp.si.loc.core.label.model;

/**
 * 
 * Title : ExploreQueryParam
 * <p/>
 * Description : 探索查询所需参数
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 6.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年12月18日    tianxy3        Created
 * </pre>
 * <p/>
 *
 * @author tianxy3
 * @version 1.0.0.2017年12月18日
 */
public class ExploreQueryParam {
	/**
	 * @param monthDate
	 *            客户群所使用的月标签的数据时间
	 * @param dayDate
	 *            客户群所使用的日标签的数据时间
	 * @param userId
	 *            获取当前Session中的用户ID
	 * @param configId
	 *            专区ID
	 * @param cityId
	 *            地市ID
	 */
	private String monthDate;
	private String dayDate;
	private String userId;
	private Integer configId;
	private String cityId;

	private Integer interval;
	private Integer updateCycle;
	private boolean isValidate;

	public String getMonthDate() {
		return monthDate;
	}

	public void setMonthDate(String monthDate) {
		this.monthDate = monthDate;
	}

	public String getDayDate() {
		return dayDate;
	}

	public void setDayDate(String dayDate) {
		this.dayDate = dayDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public Integer getUpdateCycle() {
		return updateCycle;
	}

	public void setUpdateCycle(Integer updateCycle) {
		this.updateCycle = updateCycle;
	}

	public boolean isValidate() {
		return isValidate;
	}

	public void setValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}

}
