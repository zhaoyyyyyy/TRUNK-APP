
package com.asiainfo.biapp.si.loc.base.config;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.asiainfo.biapp.si.loc.auth.utils.LocConfigUtil;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;

/**
 * 
 * Title : DataSourceConfig
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年4月5日    zhougz3        Created</pre>
 * <p/>
 *
 * @author  zhougz3
 * @version 1.0.0.2018年4月5日
 */
@Configuration
public class DataSourceConfig {

	@Value("${jauth-url}")
	private String jauthUrl;

	/**
	 * 初始化
	 * Description: 
	 *
	 * @return
	 */
	@Bean(name = "dataSource")
	public DruidDataSource dataSource() {
		DruidDataSource dataSource = null;
		try {
			Map<String, String> configMap = LocConfigUtil.getInstance(jauthUrl).getPropertiesByParentCode("LOC_CONFIG_SYS_FRDB");
			if (configMap != null && configMap.size() > 0) { //去jauth拿前台配置
				dataSource = getDataSourceJauth(configMap);
			} else {        								  //去spring配置文件拿配置
				dataSource = getDataSourceSpring();
			}
			dataSource.setConnectionProperties("druid.stat.mergeSql=false;druid.stat.slowSqlMillis=800 ");
		} catch (Exception e) {
			LogUtil.error(e);
		}
		return dataSource;
	}

	/**
	 * Description: 使用jauth的配置初始化数据源
	 * 
	 * @param configMap
	 * @return
	 */
	private DruidDataSource getDataSourceJauth(Map<String, String> configMap) {
		DruidDataSource dataSource = new DruidDataSource();

		dataSource.setDriverClassName(configMap.get("LOC_CONFIG_SYS_FRDB_DRIVER"));
		dataSource.setUrl(configMap.get("LOC_CONFIG_SYS_FRDB_URL"));
		dataSource.setUsername(configMap.get("LOC_CONFIG_SYS_FRDB_USERNAME"));
		dataSource.setPassword(configMap.get("LOC_CONFIG_SYS_FRDB_PASSWORD"));
		if(configMap.containsKey("LOC_CONFIG_SYS_FRDB_DBTYPE")){
			dataSource.setDbType(configMap.get("LOC_CONFIG_SYS_FRDB_DBTYPE"));
		}else{
			dataSource.setDbType("com.alibaba.druid.pool.DruiddataSource");
		}
		try {
			if(configMap.containsKey("LOC_CONFIG_SYS_FRDB_FILTERS")){
				dataSource.setFilters(configMap.get("LOC_CONFIG_SYS_FRDB_FILTERS"));
			}else{
				dataSource.setFilters("stat");
			}
		} catch (SQLException e) {}
		if(configMap.containsKey("LOC_CONFIG_SYS_FRDB_initialSize".toUpperCase())){
			dataSource.setInitialSize(Integer.valueOf(configMap.get("LOC_CONFIG_SYS_FRDB_initialSize".toUpperCase())));
		}
		if(configMap.containsKey("LOC_CONFIG_SYS_FRDB_minIdle".toUpperCase())){
			dataSource.setMinIdle(Integer.valueOf(configMap.get("LOC_CONFIG_SYS_FRDB_minIdle".toUpperCase())));
		}
		if(configMap.containsKey("LOC_CONFIG_SYS_FRDB_maxActive".toUpperCase())){
			dataSource.setMaxIdle(Integer.valueOf(configMap.get("LOC_CONFIG_SYS_FRDB_maxActive".toUpperCase())));
		}
		if(configMap.containsKey("LOC_CONFIG_SYS_FRDB_maxWait".toUpperCase())){
			dataSource.setMaxWait(Integer.valueOf(configMap.get("LOC_CONFIG_SYS_FRDB_maxWait".toUpperCase())));
		}
		if(configMap.containsKey("LOC_CONFIG_SYS_FRDB_validationQuery".toUpperCase())){
			dataSource.setValidationQuery(configMap.get("LOC_CONFIG_SYS_FRDB_validationQuery".toUpperCase()));
		}
		if(configMap.containsKey("LOC_CONFIG_SYS_FRDB_timeBetweenEvictionRunsMillis".toUpperCase())){
			dataSource.setTimeBetweenEvictionRunsMillis(Integer.valueOf(configMap.get("LOC_CONFIG_SYS_FRDB_timeBetweenEvictionRunsMillis".toUpperCase())));
		}
		
		if(configMap.containsKey("LOC_CONFIG_SYS_FRDB_minEvictableIdleTimeMillis".toUpperCase())){
			dataSource.setMinEvictableIdleTimeMillis(Integer.valueOf(configMap.get("LOC_CONFIG_SYS_FRDB_minEvictableIdleTimeMillis".toUpperCase())));
		}
		if(configMap.containsKey("LOC_CONFIG_SYS_FRDB_testWhileIdle".toUpperCase())){
			dataSource.setTestWhileIdle(Boolean.valueOf(configMap.get("LOC_CONFIG_SYS_FRDB_testWhileIdle".toUpperCase())));
		}
		if(configMap.containsKey("LOC_CONFIG_SYS_FRDB_testOnBorrow".toUpperCase())){
			dataSource.setTestOnBorrow(Boolean.valueOf(configMap.get("LOC_CONFIG_SYS_FRDB_testOnBorrow".toUpperCase())));
		}
		if(configMap.containsKey("LOC_CONFIG_SYS_FRDB_testOnReturn".toUpperCase())){
			dataSource.setTestOnReturn(Boolean.valueOf(configMap.get("LOC_CONFIG_SYS_FRDB_testOnReturn".toUpperCase())));
		}
		if(configMap.containsKey("LOC_CONFIG_SYS_FRDB_poolPreparedStatements".toUpperCase())){
			dataSource.setPoolPreparedStatements(Boolean.valueOf(configMap.get("LOC_CONFIG_SYS_FRDB_poolPreparedStatements".toUpperCase())));
		}
		if(configMap.containsKey("LOC_CONFIG_SYS_FRDB_maxPoolPreparedStatementPerConnectionSize".toUpperCase())){
			dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.valueOf(configMap.get("LOC_CONFIG_SYS_FRDB_maxPoolPreparedStatementPerConnectionSize".toUpperCase())));
		}

		return dataSource;
	}

	/**
	 * Description:使用spring配置初始化数据源
	 * 
	 * @return
	 */
	private DruidDataSource getDataSourceSpring() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setDbType("com.alibaba.druid.pool.DruiddataSource");
		if(isNotSet(initialSize)){
			dataSource.setInitialSize(Integer.valueOf(initialSize));
		}
		if(isNotSet(minIdle)){
			dataSource.setMinIdle(Integer.valueOf(minIdle));
		}
		if(isNotSet(maxActive)){
			dataSource.setMaxIdle(Integer.valueOf(maxActive));
		}
		if(isNotSet(maxWait)){
			dataSource.setMaxWait(Integer.valueOf(maxWait));
		}
		if(isNotSet(validationQuery)){
			dataSource.setValidationQuery(validationQuery);
		}
		if(isNotSet(timeBetweenEvictionRunsMillis)){
			dataSource.setTimeBetweenEvictionRunsMillis(Integer.valueOf(timeBetweenEvictionRunsMillis));
		}
		
		if(isNotSet(minEvictableIdleTimeMillis)){
			dataSource.setMinEvictableIdleTimeMillis(Integer.valueOf(minEvictableIdleTimeMillis));
		}
		if(isNotSet(testWhileIdle)){
			dataSource.setTestWhileIdle(Boolean.valueOf(testWhileIdle));
		}
		if(isNotSet(testOnBorrow)){
			dataSource.setTestOnBorrow(Boolean.valueOf(testOnBorrow));
		}
		if(isNotSet(testOnReturn)){
			dataSource.setTestOnReturn(Boolean.valueOf(testOnReturn));
		}
		if(isNotSet(poolPreparedStatements)){
			dataSource.setPoolPreparedStatements(Boolean.valueOf(poolPreparedStatements));
		}
		if(isNotSet(maxPoolPreparedStatementPerConnectionSize)){
			dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.valueOf(maxPoolPreparedStatementPerConnectionSize));
		}
		return dataSource;
	}


	
	
	@Value("${spring.datasource.url}")
	private String dbUrl;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	@Value("${spring.datasource.initialSize}")
	private String initialSize;//int
	@Value("${spring.datasource.minIdle}")
	private String minIdle;//int
	@Value("${spring.datasource.maxActive}")
	private String maxActive;//int
	@Value("${spring.datasource.maxWait}")
	private String maxWait;//int
	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private String timeBetweenEvictionRunsMillis;//int
	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private String minEvictableIdleTimeMillis;//int
	@Value("${spring.datasource.validationQuery}")
	private String validationQuery;
	@Value("${spring.datasource.testWhileIdle}")
	private String testWhileIdle;//boolean
	@Value("${spring.datasource.testOnBorrow}")
	private String testOnBorrow;//boolean
	@Value("${spring.datasource.testOnReturn}")
	private String testOnReturn;//boolean
	@Value("${spring.datasource.poolPreparedStatements}")
	private String poolPreparedStatements;//boolean
	@Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
	private String maxPoolPreparedStatementPerConnectionSize;//int
	@Value("${spring.datasource.filters}")
	private String filters;
	@Value("${spring.datasource.connectionProperties}")
	private String connectionProperties;
	@Value("${spring.datasource.useGlobalDataSourceStat}")
	private String useGlobalDataSourceStat;//boolean
	
	private static boolean isNotSet(String propretie){
		if(propretie != null && propretie.indexOf("${") < -1){
			return true;
		}else{
			return false;
		}
	}
}
