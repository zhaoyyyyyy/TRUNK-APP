package com.asiainfo.biapp.si.loc.cache;

import java.io.Serializable;
import java.util.List;

import com.asiainfo.biapp.si.loc.auth.model.DicData;
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
	public void addSessionValue(String token,String key,Serializable obj);
	
	/**
	 * 删除 java Map 中的 kv 数据
	 * @param key
	 */
	public <T extends Serializable> T getSessionvalue(String token,String key);
	
	/**
	 * 根据编码获取指定的数据字典值
	 * @param code
	 * @return
	 */
	public List<DicData> getDicDataByCode(String code);
	
	/**
	 * 获取日标签最新数据日期
	 * @return
	 */
	public String getNewLabelDay();
	
	/**
	 * 获取日标签最新数据日期是否统计过用户数
	 * @return
	 */
	public Integer getNewLabelDayStatus();
	
	/**
	 * 获取月标签最新数据月份
	 * @return
	 */
	public String getNewLabelMonth();
	
	/**
	 * 获取月标签最新数据月份是否统计过用户数
	 * @return
	 */
	public Integer getNewLabelMonthStatus();
	
	/**
	 * 根据专区id 获取该专区对应的coc权限字段名称
	 * @param configId
	 * @return
	 */
	public List<String> getAllOrgColumnByConfig(String configId);
}
