package com.asiainfo.biapp.si.loc.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import com.asiainfo.biapp.si.loc.auth.utils.LocConfigUtil;
import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.base.utils.RedisUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.asiainfo.biapp.si.loc.core.label.dao.ILabelInfoDao;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelExtInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelVerticalColumnRel;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;

/**
 * LOC 缓存存取 工具类
 * @author wanghf5
 *
 */
public class LocCacheBase extends ICacheBase implements ApplicationContextAware{
	
	private Logger log = Logger.getLogger(LocCacheBase.class);
	
	private static volatile LocCacheBase instance = null;
	
	private static ApplicationContext context;
	
	
    private ILabelInfoDao iLabelInfoDao;
	
	public LocCacheBase() {
		super(Prefix.LOC);
	}
	
	public static LocCacheBase getInstance() {
		
		if(instance == null) {
			synchronized(LocCacheBase.class){
	            if(instance == null) {
	               instance = new LocCacheBase();
	            }
	         }
	      }
		return instance;
		
	}
	
	public synchronized void init(){
//		this.iLabelInfoDao= labelInfoDao;
		this.iLabelInfoDao = (ILabelInfoDao)SpringContextHolder.getBean("labelInfoDaoImpl");
		this.initAllLabelInfo();
		this.initAllConfigInfo();
	}
	
//	public synchronized void refreshCache(){
//		this.initAllLabelInfo();
//		this.initAllConfigInfo();
//	}
	
	/**
	 * 将有效标签的相关属性类 数据刷入redis
	 */
	private void initAllLabelInfo(){
		log.debug("------------------------------------ LocCacheBase -initAllLabelInfo beginning -------------------------------");
		List<LabelInfo> LabelInfoTemp = iLabelInfoDao.selectEffectiveCiLabelInfo();
		log.debug("##############################   initAllLabelInfo LabelInfoTemp = " + LabelInfoTemp.size());
		CopyOnWriteArrayList<LabelInfo> LabelInfoList = new CopyOnWriteArrayList<LabelInfo>(LabelInfoTemp);
		log.debug("##############################   initAllLabelInfo LabelInfoList = " + LabelInfoList.size());
		Map<String, Object> LabelInfoMap = new ConcurrentHashMap<String, Object>();
		Map<String, Object> columnMap = new ConcurrentHashMap<String, Object>();
		//cityId与标签对应关系
		//		Map<Object, Object> cityLabelMap = new ConcurrentHashMap<Object, Object>();
		CopyOnWriteArrayList<String> labelIds = new CopyOnWriteArrayList<String>();
		for (LabelInfo c : LabelInfoList) {
			labelIds.add(c.getLabelId() + "");
			LabelInfoMap.put(CacheKey.EFFECTIVE_LABEL_PREFIX + c.getLabelId(), c);
			
			//缓存对应的COLUMN,如果是下列报空指异常，说明labelTypeId为空，由Integer类型转换成int类型就会发生空指针异常
			if(c.getLabelTypeId()==null){
			    log.error("标签ID为"+c.getLabelId()+"的标签中，labelTypeId为空！");
			}
			int labelType = c.getLabelTypeId();
			LabelExtInfo ciLabelExtInfo = c.getLabelExtInfo();
			if(ciLabelExtInfo == null)
			    continue;
			MdaSysTableColumn col = c.getMdaSysTableColumn();
			if (null != col) {
				columnMap.put(CacheKey.EFFECTIVE_LABEL_COLUMN_PREFIX + col.getColumnId(), col);
				if (LabelInfoContants.LABEL_TYPE_VERT == labelType) {
					Set<LabelVerticalColumnRel> relSet = c.getVerticalColumnRels();
					for (LabelVerticalColumnRel rel : relSet) {
						MdaSysTableColumn child = rel.getMdaSysTableColumn();
						columnMap.put(CacheKey.EFFECTIVE_LABEL_COLUMN_PREFIX + child.getColumnId(), child);
					}
				}
			}
		}
		try {
			//地市类标签缓存
			this.setHashObject(Prefix.LOC+Prefix.KV+CacheKey.ALL_EFFECTIVE_LABEL_MAP, LabelInfoMap);
			this.setObject(Prefix.LOC+Prefix.LIST+CacheKey.ALL_EFFECTIVE_LABEL_MAP, LabelInfoList);
			this.setObject(Prefix.LOC+Prefix.KEY+CacheKey.ALL_EFFECTIVE_LABEL_MAP, labelIds);
			this.set(Prefix.LOC+Prefix.CLAZZ+CacheKey.ALL_EFFECTIVE_LABEL_MAP, LabelInfo.class.getName());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		log.debug("------------------------------------ LocCacheBase -initAllLabelInfo end -------------------------------");
	}
	
	/**
	 * 清理 全部redis的标签相关的配置数据
	 */
	public void delAllLabelInfo(){
		RedisUtils.delForKey(Prefix.LOC+Prefix.KV+CacheKey.ALL_EFFECTIVE_LABEL_MAP);
		RedisUtils.delForKey(Prefix.LOC+Prefix.LIST+CacheKey.ALL_EFFECTIVE_LABEL_MAP);
		RedisUtils.delForKey(Prefix.LOC+Prefix.KEY+CacheKey.ALL_EFFECTIVE_LABEL_MAP);
		RedisUtils.delForKey(Prefix.LOC+Prefix.CLAZZ+CacheKey.ALL_EFFECTIVE_LABEL_MAP);
	}
	
	/**
	 * 标签Id获得标签
	 * @param key (=labelId)
	 * @return CiLabelInfo
	 * @throws Exception 
	 */
	public LabelInfo getEffectiveLabel(String key) throws Exception {
		return (LabelInfo) this.getObjectMap2(CacheKey.ALL_EFFECTIVE_LABEL_MAP,CacheKey.EFFECTIVE_LABEL_PREFIX + key);
	}
	
	/**
	 * 将 全部 系统配置数据 刷入 redis 缓存。
	 * @throws Exception
	 */
	public void initAllConfigInfo(){
		try {
			Map<String,String> configInfos = LocConfigUtil.getInstance(BaseConstants.JAUTH_URL).selectAll();
			this.setHashMap(Prefix.LOC+Prefix.CONFIG+CacheKey.CI_CONFIG_INFO_MAP, configInfos);
		} catch (BaseException e) {
			log.error(e.getMessage(),e);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 根据key获取 对应 系统配置 value
	 * @param key
	 * @return
	 */
	public String getSysConfigInfoByKey(String key){
		try {
			return this.getStringByKey(CacheKey.CI_CONFIG_INFO_MAP, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 5.0 session缓存 存放 接口， 默认设置为 30秒失效
	 * @param token
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void setSessionCache(String token,String key,Serializable value) throws Exception{
		this.setSessionHashMap(Prefix.LOC+Prefix.SESSION+token, key,value);
	}
	
	public <T extends Serializable> T getSessionCache(String token,String key) throws Exception{
		return this.getSessionHashMap(Prefix.LOC+Prefix.SESSION+token, key);
	}
	

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		LocCacheBase.context = applicationContext; 
	}

}
