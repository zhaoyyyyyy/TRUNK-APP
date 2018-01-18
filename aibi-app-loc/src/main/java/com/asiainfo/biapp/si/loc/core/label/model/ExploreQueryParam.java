
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
	private String dataDate;// 统计周期
	private String monthDate;// 客户群所使用的月标签的数据时间
	private String dayDate;// 客户群所使用的日标签的数据时间
	
	private String userId;// 获取当前Session中的用户ID
	private Integer configId;// 专区ID
	private String cityId;// 地市ID

	private Integer interval;//偏移量	日期型标签使用	
	private Integer updateCycle;//客户群周期性	日期型标签使用
	private boolean isValidate;//是否生成校验sql

	/**数据权限 20180117 */
	private String orgId;
	
	public ExploreQueryParam() {
		super();
	}

	public ExploreQueryParam(String dataDate, String monthDate, String dayDate) {
		super();
		this.dataDate = dataDate;
		this.monthDate = monthDate;
		this.dayDate = dayDate;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
