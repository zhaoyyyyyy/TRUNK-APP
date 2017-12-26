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
	
}
