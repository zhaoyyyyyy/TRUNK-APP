package com.asiainfo.biapp.si.bd.cache;
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
	 * 根据key获取缓存中的value 接口定义
	 * @param key
	 * @return value
	 */
	public String getValueByKey(String key);
	
	/**
	 * 根据标签id获取标签信息对象 接口定义
	 * @param id
	 * @return
	 */
	public Object getLabelInfoById(String id);
}
