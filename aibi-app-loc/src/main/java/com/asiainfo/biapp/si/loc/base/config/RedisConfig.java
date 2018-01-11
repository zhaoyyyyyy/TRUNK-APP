
package com.asiainfo.biapp.si.loc.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.asiainfo.biapp.si.loc.auth.utils.LocConfigUtil;
import com.asiainfo.biapp.si.loc.base.BaseConstants;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;


/**
 * Redis缓存配置类
 * @author szekinwin
 *
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{

	
	@Value("${jauth-url}")  
    private String jauthUrl; 
	
    public static String host;
    public static Integer port;
    
    
    //缓存管理器
    @Primary
    @Bean("redisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory() {

    	
    	System.out.println("-------------------------------------LocBaseListener--------------------------------------");
		BaseConstants.JAUTH_URL = jauthUrl;
	
		try {
			RedisConfig.host = LocConfigUtil.getInstance(BaseConstants.JAUTH_URL).getProperties(BaseConstants.REDIS_IP);
			RedisConfig.port = Integer.valueOf(LocConfigUtil.getInstance(BaseConstants.JAUTH_URL).getProperties(BaseConstants.REDIS_PORT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		// 设置连接池初始化大小和最大容量

		// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
		// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
		poolConfig.setMaxTotal(-1);
		// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		poolConfig.setMaxIdle(1000);
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		poolConfig.setMaxWaitMillis(1000 * 30);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		poolConfig.setTestOnBorrow(true);
        
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);  
        jedisConnectionFactory.setHostName(host);  
        jedisConnectionFactory.setPort(port);  
        
        
        return jedisConnectionFactory;
    }
    
    @Bean 
    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
    	RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间 
        cacheManager.setDefaultExpiration(10000);
        return cacheManager;
    }
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
    	
    	 RedisTemplate<Object, Object> template = new RedisTemplate<>();
         template.setConnectionFactory(redisConnectionFactory);

         Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
         ObjectMapper om = new ObjectMapper();
         om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
         om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
         jackson2JsonRedisSerializer.setObjectMapper(om);

         // redis value使用的序列化器
         template.setValueSerializer(jackson2JsonRedisSerializer);
         // redis key使用的序列化器
         template.setKeySerializer(jackson2JsonRedisSerializer);


         template.afterPropertiesSet();
         return template;
    }
}