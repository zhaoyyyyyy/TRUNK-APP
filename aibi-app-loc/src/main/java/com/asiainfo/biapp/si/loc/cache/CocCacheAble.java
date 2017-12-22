package com.asiainfo.biapp.si.loc.cache;

import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;

/**
 * 
 * 标签库项目-缓存处理接口定义
 * 
 * 
 * @author wanghf5
 * @since 2017-12-08 
 *
 */
public interface CocCacheAble {

	/**
	 * 整体缓存数据刷新 接口定义
	 */
	public void reflashAllCache();
	
	/**
	 * 根据key获取 系统配置数据 
	 * @param key
	 * @return value
	 */
	public String getSYSConfigInfoByKey(String key);
	
	/**
	 * 根据标签id获取标签信息对象 接口定义
	 * @param id
	 * @return
	 */
	public LabelInfo getLabelInfoById(String labelid);
	
	/**
	 * 往java Map中写入需要缓存得东西
	 * @param key
	 * @param value
	 */
	public void addSessionValue(String token,String key,String obj);
	
	/**
	 * 删除 java Map 中的 kv 数据
	 * @param key
	 */
	public String getSessionvalue(String token,String key);
	
	
}
