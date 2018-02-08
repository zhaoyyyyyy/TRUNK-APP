package com.asiainfo.biapp.si.loc.base.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.biapp.si.loc.auth.utils.LocConfigUtil;
import com.asiainfo.biapp.si.loc.base.BaseConstants;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

public class RedisUtils {

	private static final Log log = LogFactory.getLog(RedisUtils.class);

	private static JedisPool jedisPool;// 非切片连接池

	private static final Object lock = new Object();

	public static final int DEFAULT_TIME_OUT = 30000;
	public static final int ONE_HOUR_TIME = 600000;

	private static String redisIp = null;

	private static Integer redisPort = null;

	public static int redisExpire = 86400;

	/** 日 */
	public static final int Day_Expire = 86400;
	/** 月 */
	public static final int Month_Expire = 86400 * 30;
	/** 永久 */
	public static final int NO_Expire = -1;

	/**
	 * 构建redis切片连接池
	 * 
	 * @param ip
	 * @param port
	 * @return JedisPool
	 * @throws BaseException
	 */
	public static JedisPool getJedisPool() throws BaseException {
		if (jedisPool == null) {
			synchronized (lock) {
				log.debug("redis server IP = "
						+ LocConfigUtil.getInstance(BaseConstants.JAUTH_URL).getProperties(BaseConstants.REDIS_IP)
						+ "; port = "
						+ LocConfigUtil.getInstance(BaseConstants.JAUTH_URL).getProperties(BaseConstants.REDIS_PORT));
				redisIp = LocConfigUtil.getInstance(BaseConstants.JAUTH_URL).getProperties(BaseConstants.REDIS_IP);
				redisPort = Integer.valueOf(
						LocConfigUtil.getInstance(BaseConstants.JAUTH_URL).getProperties(BaseConstants.REDIS_PORT));

				if (jedisPool == null) {
					JedisPoolConfig config = new JedisPoolConfig();
					// 设置连接池初始化大小和最大容量

					// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
					// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
					config.setMaxTotal(-1);
					// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
					config.setMaxIdle(1000);
					// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
					config.setMaxWaitMillis(1000 * 30);
					// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
					config.setTestOnBorrow(true);
					// 写
					jedisPool = new JedisPool(config, redisIp, redisPort, DEFAULT_TIME_OUT);

				}
			}
		}
		return jedisPool;
	}

	/**
	 * 返还到连接池
	 * 
	 * @param pool
	 * @param redis
	 */
	public static void returnJedisResource(Jedis redis) {
		if (redis != null) {
			redis.close();
		}
	}

	/**
	 * 设置redis的IP地址和端口号
	 * 
	 * @param serviceAddress
	 * @param serverPort
	 */
	public static void setAdressAndPort(String serverAddress, String serverPort) {
		redisIp = serverAddress;
		redisPort = Integer.valueOf(serverPort);
	}

	/********************************
	 * key-value
	 ***********************************/
	/**
	 * add by ducongcong 通过key从redis里取出value值
	 * 
	 * @param key
	 * @return
	 */
	public static String getForString(String key) {
		String values = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getJedisPool();
			jedis = pool.getResource();
			values = jedis.get(key);
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
		return values;
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public static List<String> getForString(String... key) {
		List<String> value = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getJedisPool();
			jedis = pool.getResource();
			value = jedis.mget(key);
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
		return value;
	}

	/**
	 * 获取同一个键下的哈希对象
	 * 
	 * @param key
	 * @return
	 */
	public static Map<String, String> getForHashObjByKey(String key) {
		Map<String, String> result = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			if (jedisPool != null) {
				pool = jedisPool;
				jedis = pool.getResource();
				result = jedis.hgetAll(key);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
		return result;
	}

	/**
	 * 
	 * Description: 读取一个map中的一个特定的key的值
	 *
	 * @param key
	 * @param fields
	 * @return
	 */
	public static String getStringFromMap(String key, String field) {
		List<String> values = mgetForHashObj(key, field);
		if (values == null || values.size() == 0) {
			return null;
		} else {
			return values.get(0);
		}
	}

	/**
	 * 获取哈希结构的值
	 * 
	 * @param key
	 * @param value
	 *            : map的key组成的数组
	 * @return 根据key的顺序返回values
	 */
	public static List<String> mgetForHashObj(String key, String... value) {
		List<String> result = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			if (jedisPool != null) {
				pool = jedisPool;
				jedis = pool.getResource();
				result = jedis.hmget(key, value);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
		return result;
	}
	
	/**
	 * 获取哈希结构的值
	 * 
	 * @param key
	 * @param value
	 *            : map的key组成的数组
	 * @return 根据key的顺序返回values
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T mgetForHashObj2(String key, String value) {
		T result = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			if (jedisPool != null) {
				pool = jedisPool;
				jedis = pool.getResource();
				result = (T) SerializeUtil.unserialize(jedis.hget(key.getBytes(), value.getBytes()));
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
		return result;
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public static void setForString(String key, String value, int expTime) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getJedisPool();
			jedis = pool.getResource();
			jedis.set(key, value);
			if (expTime != RedisUtils.NO_Expire) {
				jedis.expire(key, expTime);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public static void setPForString(Map<String, String> ketValue, int expTime) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getJedisPool();
			jedis = pool.getResource();
			Pipeline pp = jedis.pipelined();
			Set<String> keys = ketValue.keySet();
			for (String key : keys) {
				pp.set(key, ketValue.get(key));
				if (expTime != RedisUtils.NO_Expire) {
					pp.expire(key, expTime);
				}
			}
			pp.sync();
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
	}

	/**
	 * 向list中添加值,并设置失效时间
	 * 
	 * @param key
	 * @param expTime
	 *            失效时间设置 -1为不设置失效时间，单位为秒
	 * @param 多个
	 *            list-value
	 */
	public static void setForList(String key, int expTime, String... strings) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getJedisPool();
			jedis = pool.getResource();
			jedis.lpush(key, strings);
			if (expTime != RedisUtils.NO_Expire) {
				jedis.expire(key, expTime);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
	}

	/********************************
	 * key-map
	 ***********************************/
	/**
	 * 设置哈希结构的字段和值，在原有的map上追加,不存在则新建
	 * 
	 * @param key
	 * @param value
	 */
	public static void setForHashObj(String key, Map<String, String> value, int expTime) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getJedisPool();
			jedis = pool.getResource();
			jedis.hmset(key, value);
			if (expTime != RedisUtils.NO_Expire) {
				jedis.expire(key, expTime);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
	}

	/**
	 * 设置 redis map 得 field value
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @param expTime
	 */
	public static void setHashMapFeild(String key, String field, String value, int expTime) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getJedisPool();
			jedis = pool.getResource();
			jedis.hset(key, field, value);
			if (expTime != RedisUtils.NO_Expire) {
				jedis.expire(key, expTime);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}

	}

	/**
	 * 设置 redis map 得 field value
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @param expTime
	 */
	public static void setHashMapFeild(String key, String field, Object value, int expTime) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getJedisPool();
			jedis = pool.getResource();
			jedis.hset(key.getBytes(), field.getBytes(), SerializeUtil.serialize(value));
			if (expTime != RedisUtils.NO_Expire) {
				jedis.expire(key, expTime);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}

	}

	/**
	 * 单机模式下，删除一个二级key对应的值
	 * 
	 * @param key
	 */
	public static void delForKey(String key, String... key2) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getJedisPool();
			jedis = pool.getResource();
			jedis.hdel(key, key2);
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
	}

	/**
	 * 单机模式下，删除一个key对应的值
	 * 
	 * @param key
	 */
	public static void delForKey(String key) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getJedisPool();
			jedis = pool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
	}

	/**
	 * 单机模式下，删除一个key对应的值
	 * 
	 * @param key
	 */
	public static void delForKey(List<String> key) {
		JedisPool pool = null;
		Jedis jedis = null;
		String[] arr = new String[key.size()];
		key.toArray(arr);
		try {
			pool = getJedisPool();
			jedis = pool.getResource();
			jedis.del(arr);
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
	}

	/**
	 * 
	 * Description: 单机判断key是否存在
	 *
	 * @param key
	 * @return
	 */
	public static boolean redisExistsKey(String key) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			if (StringUtils.isNotBlank(key)) {
				pool = getJedisPool();
				jedis = pool.getResource();
				return jedis.exists(key);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
		return false;
	}

	/*
	 * 清空redis
	 */
	public static boolean flushAll() {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getJedisPool();
			jedis = pool.getResource();
			jedis.flushAll();
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
		return true;
	}

	/**
	 * author ducong test Redis Connet
	 **/
	public static String getRandomValue() {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getJedisPool();
			jedis = pool.getResource();
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
		return jedis.randomKey();
	}

	/**
	 * 单机模式下，判断某个 SET-key 下 某个元素是否存在
	 * 
	 * @param key
	 * @param feild
	 * @return
	 */
	public static boolean existsListFieldByKey(String key, String field) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getJedisPool();
			jedis = pool.getResource();
			return jedis.sismember(key, field);
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
		return false;
	}

	/**
	 * 单机模式下 SET-key 写入
	 * 
	 * @param key
	 * @param feild
	 * @return
	 */
	public static void oneSetSet(String key, String... field) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getJedisPool();
			jedis = pool.getResource();
			jedis.sadd(key, field);
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 返还到连接池
			returnJedisResource(jedis);
		}
	}

}
