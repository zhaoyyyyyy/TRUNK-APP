package com.asiainfo.biapp.si.loc.base;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.JsonUtil;
import com.asiainfo.biapp.si.loc.base.utils.RedisUtils;

/**
 * 
 * Title : ICacheBase
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2016
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 6.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2016-9-12    wanghf5        Created</pre>
 * <p/>
 *
 * @author  wanghf5
 * @version 1.0.0.2016-9-12
 */
public abstract class ICacheBase {
	
	private Logger log = Logger.getLogger(ICacheBase.class);
	
	public static boolean ifCluster = false;
	//模块缓存名称前缀
	private final String appKey;
		
	public ICacheBase(String appKey){
		this.appKey = appKey;
	}
	
	/**
	 * 添加缓存数据到缓存数据库
	 * @param key
	 * @param value
	 */
	public void set(String key,String value) throws Exception{
		RedisUtils.setForString(key, value,RedisUtils.NO_Expire);
	}
	
	 /**
     * 从KV缓存中取出Object,Object Map对象
     * @param tableName
     * @return
	 * @throws Exception 
     */
    @SuppressWarnings("unchecked")
	public Map<Object,Object> getKVObject(String tableName) throws Exception{
    	
    	String kvJsonValue = RedisUtils.getForString(appKey+Prefix.KV+tableName);
    	if(kvJsonValue == null){
            throw new Exception("缓存未加载:[" + tableName + "]");
        }
        try {
			return (Map<Object,Object>) JsonUtil.json2CollectionBean(kvJsonValue, Map.class, Object.class,Object.class);
		} catch (Exception e) {
            throw new Exception(e);
        }
    }
    
	/**
     * 添加对象数据到缓存数据库[hash]
     * @param key
     * @param obj
     */
    public void setHashObject(String key,Map<String,Object> objs) throws Exception{
    	log.info("  ##############################   ifCluser =  " + ifCluster);
    	try {
			if(objs == null)
			    return;
			Map<String,String> values = new HashMap<String, String>(objs.size());
			for(Iterator<Map.Entry<String,Object>> it =objs.entrySet().iterator();it.hasNext();) {
			    Map.Entry<String,Object> entry = it.next();
			    values.put(entry.getKey(), JsonUtil.toJsonString(entry.getValue()));
			}
			RedisUtils.setForHashObj(key, values, RedisUtils.NO_Expire);
		} catch (BaseException e) {
			log.error("object to json exception:",e);
		}
    	
    }
    
    /**
     * 添加对象数据到缓存数据库[hash]
     * @param key
     * @param obj
     */
    public void setHashMap(String key,Map<String,String> objs) throws Exception{
    	log.info("  ##############################   ifCluser =  " + ifCluster);
    	try {
			if(objs == null)
			    return;
			Map<String,String> values = new HashMap<String, String>(objs.size());
			for(Iterator<Map.Entry<String,String>> it =objs.entrySet().iterator();it.hasNext();) {
			    Map.Entry<String,String> entry = it.next();
			    values.put(entry.getKey(), entry.getValue());
			}
			RedisUtils.setForHashObj(key, values, RedisUtils.NO_Expire);
		} catch (Exception e) {
			log.error("object to json exception:",e);
		}
    	
    }
    
    
    /**
     * 5.0 session缓存 存放 接口， 默认设置为 30秒失效
     * @param key1
     * @param key2
     * @param value
     * @throws Exception
     */
    public void setSessionHashMap(String key1,String key2,Object value) throws Exception{
    	try {
			if(StringUtils.isBlank(key1) || StringUtils.isBlank(key2) || value==null){
				log.error("key1 or key2 or value is null.");
				throw new Exception("key1 or key2 or value is null.");
			}
			RedisUtils.setHashMapFeild(key1,key2,value,RedisUtils.ONE_HOUR_TIME);
		} catch (Exception e) {
			throw new Exception(e);
		}
    }
    
    public <T extends Serializable> T getSessionHashMap(String key1,String key2) throws Exception{
    	
    	try {
			if(StringUtils.isBlank(key1) || StringUtils.isBlank(key2)){
				log.error("key1 or key2 is null.");
				throw new Exception("key1 or key2 or value is null.");
			}
			return RedisUtils.mgetForHashObj2(key1, key2);
		} catch (Exception e) {
			throw new Exception(e);
		}
    }
    
    
    
    /**
     * 获取缓存中的ID集合
     * @param appKey
     * @param tableName
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    public List<?> getKeyList(String tableName) throws Exception {
        try {
            String keyJsonValue = RedisUtils.getForString(appKey+Prefix.KEY+tableName);
            if(keyJsonValue == null){
                throw new Exception("缓存未加载:[" + tableName + "]");
            }
            return (List) JsonUtil.json2CollectionBean(keyJsonValue, List.class, String.class);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    

	/**
	 * 添加对象数据到缓存数据库[string]
	 * @param key
	 * @param obj
	 */
	public void setObject(String key,Object obj){
		String value ;
		try {
			value = JsonUtil.toJsonString(obj);
			RedisUtils.setForString(key, value, RedisUtils.NO_Expire);
		} catch (Exception e) {
			log.error("object to json exception:",e);
		}
	}
    
    /**
     * @param tableName
     * @param key
     * @return
     * @throws Exception 
     */
    public Object getObjectMap2(String tableName, Object key) throws Exception {
        if (key == null) {
            return null;
        }
        try {
            StopWatch watch = new StopWatch();
            watch.start();
            // 对象类型
            String clsName = RedisUtils.getForString(appKey + Prefix.CLAZZ + tableName);
            String value = RedisUtils.getStringFromMap(appKey + Prefix.KV + tableName, key.toString());
            watch.stop();
            if(value == null)
                return null;
            // 计算redis get 返回时间
            long rGetTimes = watch.getTime();
            watch.reset();
            watch.start();
            Object obj = JsonUtil.json2Bean(value, Class.forName(clsName));
            watch.stop();
            long desTimes = watch.getTime();
            this.log.debug(String.format("[%s] redis get times is %d ms,deserialization times is %d ms", tableName,
                rGetTimes, desTimes));
            return obj;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    
    public <T extends Serializable> T get(String key){
      T obj = null;
      return obj; 
      } 
    
    /**
     * @param tableName
     * @param key
     * @return
     * @throws Exception 
     */
    public String getStringByKey(String tableName, String key) throws Exception {
        if (key == null) {
            return null;
        }
        try {
            StopWatch watch = new StopWatch();
            watch.start();
            // 对象类型
//            String clsName = RedisUtils.getForString(appKey + Prefix.CLAZZ + tableName);
            String value = RedisUtils.getStringFromMap(appKey + Prefix.CONFIG + tableName, key.toString());
            watch.stop();
            if(StringUtils.isBlank(value))
                return null;
            // 计算redis get 返回时间
            return value;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    
    /**
	 * @param tableName
	 * @param key
	 * @return
     * @throws Exception 
	 */
	public Object getObjectMap(String tableName,Object key) throws Exception{
		if (key == null){
            return null;
        }
		String clsName = this.get(appKey+Prefix.CLAZZ+tableName);
		String listJsonValue = this.get(appKey+Prefix.KV+tableName);
		try {
			Map<?, ?> cacheList = (Map<?, ?>)JsonUtil.json2CollectionBean(listJsonValue, Map.class,String.class,Class.forName(clsName));
			return cacheList.get(key);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
    
    /**
     * @param tableName
     * @param key
     * @return
     * @throws Exception 
     */
    public Object getHashObjects(String tableName) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            StopWatch watch = new StopWatch();
            watch.start();
            // 对象类型
            String clsName = RedisUtils.getForString(appKey + Prefix.CLAZZ + tableName);
            // json字符串
            Map<String, String> value = RedisUtils.getForHashObjByKey(appKey + Prefix.KV + tableName);
            
            watch.stop();
            if (value == null)
                return new HashMap<String, Object>();
            // 计算redis get 返回时间
            long rGetTimes = watch.getTime();
            watch.reset();
            watch.start();
            for (Iterator<Entry<String, String>> it = value.entrySet().iterator(); it.hasNext();) {
                Entry<String, String> entry = it.next();
                result.put(entry.getKey(), JsonUtil.json2Bean(entry.getValue(), Class.forName(clsName)));
            }
            watch.stop();
            long desTimes = watch.getTime();
            this.log.debug(String.format("[%s] redis get times is %d ms,deserialization times is %d ms", tableName,
                rGetTimes, desTimes));
        } catch (Exception e) {
            throw new Exception(e);
        }
        return result;
    }
    

	/**
	 * 缓存类实例对象名称key
	 * @author haozf
	 *
	 */
	public static interface CacheInstance {

		/**
		 * COC
		 */
		String COC_CACHE = "COC";
		
		/**
		 * POC
		 */
		String POC_CACHE = "POC";
		
		/**
		 * 审批
		 */
		String APPROVE_CACHE = "APPROVE";
		
		/**
		 * 消息通知
		 */
		String MESSAGE_CACHE = "MESSAGE";
		
		/**
		 * 智能分析
		 */
		String IA_CACHE = "IA";
	}
	
	protected static interface Prefix {
		String KV =  "KV_";
		String LIST = "LIST_";
		String KEY = "KEY_";
		String CLAZZ = "CLASS_"; 
		String CONFIG = "CONFIG_";
		String SESSION = "SESSION_";
		
		String LOC = "LOC_";
		String POC = "POC_";
		String APPROVE = "APPROVE_";
		String MESSAGE = "MESSAGE_";
		String IA = "IA_";
		String PROTRAIT = "PROTRAIT_";
	}
	
	public static interface CacheKey{

	    //枚举型标签翻译时所需维表
	    String POC_DIM_TABLE_MAP = "POC_DIM_TABLE_MAP";
	    //标签状态维表
	    String TABLE_DIM_PRODUCT_ATTR= "POC_PRODUCT_ATTR";
	    //系统信息，记录推送到/接收的系统的ftp等信息
	    String TABLE_POC_SYS_INFO = "POC_SYS_INFO";
	    //维表_产品数据状态
	    String TABLE_DIM_PRODUCT_DATA_STATUS ="DIM_PRODUCT_DATA_STATUS";
	    //维表_产品类型 
	    String TABLE_DIM_PRODUCT_TYPE = "DIM_PRODUCT_TYPE";
		
		//----------------------------------------------------------//
		/** 维表_审批级别定义表:DIM_APPROVE_LEVEL */
		String TABLE_DIM_APPROVE_LEVEL = "DIM_APPROVE_LEVEL";
		
		/**维表_审批资源类型：DIM_APPROVE_RESOURCE_TYPE*/
		String TABLE_DIM_APPROVE_RESOURCE_TYPE = "DIM_APPROVE_RESOURCE_TYPE";
		
		/** 维表_标签审批状态:TABLE_DIM_APPROVE_STATUS */
		String TABLE_DIM_APPROVE_STATUS = "DIM_APPROVE_STATUS";
		
		/**公告类型码表**/
		String TABLE_DIM_ANNOUNCEMENT_TYPE ="MESSAGE_KV_DIM_ANNOUNCEMENT_TYPE";
		
		/**个人消息容器*/
		String USER_NOTICE_CONTAINER = "USER_NOTICE_CONTAINER";
		
		/** 维表_标签使用类型:DIM_LABEL_USE_TYPE */
		String TABLE_DIM_LABEL_USE_TYPE = "DIM_LABEL_USE_TYPE";
		
		/** 维表_标签数据状态:DIM_LABEL_DATA_STATUS */
		String TABLE_DIM_LABEL_DATA_STATUS = "DIM_LABEL_DATA_STATUS";
		
		/** 维表_客户群创建方式:DIM_CUSTOM_CREATE_TYPE */
		String TABLE_DIM_CUSTOM_CREATE_TYPE = "DIM_CUSTOM_CREATE_TYPE";
		
		/** 维表_客户群数据状态:DIM_CUSTOM_DATA_STATUS */
		String TABLE_DIM_CUSTOM_DATA_STATUS = "DIM_CUSTOM_DATA_STATUS";
		
		/** 维表_品牌:DIM_BRAND */
		String TABLE_DIM_BRAND = "DIM_BRAND";
		
		/** 维表_地域(分公司):DIM_CITY */
		String TABLE_DIM_CITY = "DIM_CITY";
		String CITY_IDS_LIST = "CITY_IDS_LIST";/** 所有city_id,不包含county_id */
		
		/** 最新标签数据日期:CI_NEWEST_DATE */
		String TABLE_CI_NEWEST_LABEL_DATE = "CI_NEWEST_LABEL_DATE";
		
		/** 维表_操作日志类型明细表:DIM_OP_LOG_TYPE_DETAIL */
		String TABLE_DIM_OP_LOG_TYPE_DETAIL = "DIM_OP_LOG_TYPE_DETAIL";
		
		/** 维表_操作日志类型表:DIM_OP_LOG_TYPE */
		String TABLE_DIM_OP_LOG_TYPE = "DIM_OP_LOG_TYPE";
		
		/**维表——模板场景*/
		String TABLE_DIM_SCENE = "DIM_SCENE";
		
		/** 维表_地市线程池:DIM_CITY_THREAD_CONFIG */
		String TABLE_DIM_CITY_THREAD_CONFIG = "DIM_CITY_THREAD_CONFIG";
		
		/** 维表_地市日客户群数:DIM_CITY_DIALY_CUSTOMGROUP_NUM */
		String TABLE_DIM_CITY_DIALY_CUSTOMGROUP_NUM = "DIM_CITY_DIALY_CUSTOMGROUP_NUM";
		
		/**  系统配置宽表缓存 */
		String CI_SYS_TABLE_MAP = "CI_SYS_TABLE_MAP";
		
		/**维表_标签类型**/
		String TABLE_DIM_LABEL_TYPE ="DIM_LABEL_TYPE";
		
		/** 所有有效的标签集合 */
		String ALL_EFFECTIVE_LABEL_MAP = "ALL_EFFECTIVE_LABEL_MAP";
		
		/** 所有有效专区对应的全量表权限字段名称map */
		String ALL_CONFIG_ORG_MAP = "ALL_CONFIG_ORG_MAP";
		
		/** 所有有效的标签对应的column集合 */
		String ALL_EFFECTIVE_LABEL_COLUMNS = "ALL_EFFECTIVE_LABEL_COLUMNS";
		
		/** 客户群清单缓存 */
		String CI_CUSTOM_LIST_INFO_MAP = "COC_KV_CI_CUSTOM_LIST_INFO_MAP";
		
		/** 配置项缓存 */
		String CI_CONFIG_INFO_MAP = "CI_CONFIG_INFO_MAP";
		
		/** 数据字典缓存 */
		String CI_DICDATA_MAP = "CI_DICDATA_MAP";
		
		String CI_SESSION_INFO_MAP = "CI_SESSION_INFO_MAP";
		
		/** 最热标签、客户群 */
		String HOT_LABELS = "HOT_LABELS";
		String HOT_CUSTOMS = "HOT_CUSTOMS";
		
		/** 营销任务缓存 */
		String CI_MARKET_TASK_MAP = "CI_MARKET_TASK_MAP";
		
		/** 标签列名称**/
		String TABEL_ALL_LABEL_COLUMN_NAME = "TABEL_ALL_LABEL_COLUMN_NAME";
		
		/** 属性标签维表缓存 */
		String CI_DIM_TABLE_MAP = "CI_DIM_TABLE_MAP";
		
		/** 场景维表 */
		String CI_MARKET_SCENE_MAP = "CI_MARKET_SCENE_MAP";
		
		/** 浙江 新旧版本用户权限表 */
		String ZJ_USER = "ZJ_USER";
		
		/** 首页营销任务树形数据缓存*/
	    String CI_MARKET_TASK_TREE = "CI_MARKET_TASK_TREE";
	    
	    /** 地市标签前缀 */
		String CITY_LABEL = "CITY_";
		/** 所有地市标签集合 */
		String CITY_LABEL_MAP = "CITY_LABEL_MAP";
		/** 按地市存放地市标签前缀 */
		String CITY_LABEL_BY_CITY = "CITY_LABEL_BY_CITY_";
		/** 地市标签元数据列 **/
		String ALL_CITY_EFFECTIVE_LABEL_COLUMNS = "ALL_CITY_EFFECTIVE_LABEL_COLUMNS";
		
		/** 有效的标签对应的column的前缀 */
		String EFFECTIVE_LABEL_COLUMN_PREFIX = "COLUMN_";
		
		/** 有效的标签前缀 */
		String EFFECTIVE_LABEL_PREFIX = "LABEL_";		
		/**维表_清单生成策略**/
		String TABLE_DIM_LIST_TACTICS ="DIM_LIST_TACTICS";
		
	    /**
	     * 外部系统标签分类List
	     */
	    String EXTERNAL_LABEL_CATEGORY_LIST = "_LABEL_CATEGORY_LIST";
	    /**
	     * 外部系统分类与标签的对应关系List
	     */
	    String EXTERNAL_SYS_LABEL_REL_LIST = "_SYS_LABEL_REL_LIST";
	    /**
	     * 外部系统全部的标签体系List
	     */
	    String EXTERNAL_ALL_LABEL_LIST = "_ALL_LABEL_LIST";
	    /**
	     * 流程ID获取流程对应的审批状态
	     */
	    String TABLE_APPROVE_STATUS_4_PROCESS = "TABLE_APPROVE_STATUS_4_PROCESS";
	    
	    String START_SERVER_RUN_CUSTOM_LIST = "START_SERVER_RUN_CUSTOM_LIST";
	    
        /** ID转换码表 */
        String ID_TRANSFORM_CODE = "CI_ID_TRANSFORM_CODE";

        /** <IMEI,PHONE>对应的map */
        String ID_TRANSFORM_CODE_IMEI = "ID_TRANSFORM_CODE_IMEI";

        /** <PHONE,IMEI>对应的map */
        String ID_TRANSFORM_CODE_PHONE = "ID_TRANSFORM_CODE_PHONE";

        /** <VIRTUAL_ID,PHONE>对应的map */
        String ID_TRANSFORM_CODE_VIRTUAL_IMEI = "ID_TRANSFORM_CODE_VIRTUAL_PHONE";

        /** <VIRTUAL_ID,IMEI>对应的map */
        String ID_TRANSFORM_CODE_VIRTUAL_PHONE = "ID_TRANSFORM_CODE_VIRTUAL_IMEI";
	}
}
