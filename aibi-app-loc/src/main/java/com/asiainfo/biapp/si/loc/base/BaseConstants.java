package com.asiainfo.biapp.si.loc.base;

public class BaseConstants {
	
	
	/**
	 * 默认主键列名
	 */
	public static final String DEFAULT_ID_NAME = "id";
	
	/**
	 * 默认时间格式化
	 */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * JAUTH_URL 常量，系统加载时初始化该参数，由于无法在理立马初始化，所以无法用 final 修饰
	 */
	public static String JAUTH_URL = "";
	/**
	 * REDIS地址
	 */
	public static final String REDIS_IP = "LOC_CONFIG_SYS_REDIS_URL";
	/**
	 * REDIS端口
	 */
	public static final String REDIS_PORT = "LOC_CONFIG_SYS_REDIS_PORT ";
	/**
	 * REDIS端口
	 */
	public static final String REDIS_PASSWORD = "LOC_CONFIG_SYS_REDIS_PASSWORD ";
	
	
	public static final String SYS_BGDB_DRIVER = "LOC_CONFIG_SYS_BGDB_DRIVER";
	
	public static final String SYS_BGDB_TYPE = "LOC_CONFIG_SYS_BGDB_TYPE";
	
	public static final String SYS_BGDB_URL = "LOC_CONFIG_SYS_BGDB_URL";
	
	public static final String SYS_BGDB_USERNAME = "LOC_CONFIG_SYS_BGDB_USERNAME";
	
	public static final String SYS_BGDB_PASSWORD = "LOC_CONFIG_SYS_BGDB_PASSWORD";

	
	/**
	 * 后台数据库类型：MYSQL、ADS、TERA、GBASE、GBASE、HIVE、vertica、gaussmpp
	 */
    /** MYSQL */
    public static final String DBMS_MYSQL = "MYSQL";
    /** ADS */
    public static final String DBMS_ADS = "ADS";
    /** TERA */
    public static final String DBMS_TERA = "TERA";
    /** GBASE */
    public static final String DBMS_GBASE = "GBASE";
    /** HIVE(sparksql) */
    public static final String DBMS_SPARKSQL = "HIVE";
    /** vertica */
    public static final String DBMS_VERTICA = "VERTICA";
    /** gaussmpp */
    public static final String DBMS_GAUSSMPP = "GAUSSMPP";
    
	
}
