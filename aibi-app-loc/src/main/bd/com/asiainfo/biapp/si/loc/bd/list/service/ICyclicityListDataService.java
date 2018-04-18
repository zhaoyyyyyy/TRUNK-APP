package com.asiainfo.biapp.si.loc.bd.list.service;

import java.util.List;
import java.util.Map;

public interface ICyclicityListDataService {
	
	/**
	 * 获取所有需要跑的日周期的周期性客户群id
	 * @return 返回 专区id 为key，客户群id为list的 map
	 */
	public Map<String,List<String>> getAllDayListId();
	
	/**
	 * 获取所有需要跑的月周期的周期性客户群id
	 * @return
	 */
	public Map<String,List<String>> getAllMonthListId();
	
	/**
	 * 处理所以有效专区的日周期清单数据
	 */
	public void runAllDayListData();
	
	/**
	 * 处理所以有效专区的月周期清单数据
	 */
	public void runAllMonthListData();
	
	/**
	 * 处理指定专区的日周期的清单数据
	 * @param configId
	 */
	public void runDayListDataByConfigId(String configId);
	
	/**
	 * 处理指定专区的月周期的清单数据
	 * @param configId
	 */
	public void runMonthListDataByConfigId(String configId);
	
	/**
	 * 处理指定专区的某一个周期的日周期的清单数据
	 * @param configId
	 * @param dataDate
	 */
	public void runDayListDataByConfigId(String configId,String dataDate);
	
	/**
	 * 处理指定专区的某一个周期的月周期的清单数据
	 * @param configId
	 * @param dataDate
	 */
	public void runMonthListDataByConfigId(String configId,String dataDate);
	

}
