package com.asiainfo.biapp.si.coc.jauth.frame;

import com.asiainfo.biapp.si.coc.jauth.frame.util.ConfigurableContants;

public class Constants extends ConfigurableContants {
	

	/**
	 * 用户状态
	 */
	public static final Integer USER_ENABLE_STATUS = 1;
	public static final Integer USER_LOCKED_STATUS = 2;
	public static final Integer USER_DELETE_STATUS = 3;

	
	public static final Integer USER_IS_ADMIN = 1;
	public static final Integer USER_IS_NOT_ADMIN = 2;


	/**
	 * 默认每页数据条数
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	/**
	 * 默认主键列名
	 */
	public static final String DEFAULT_ID_NAME = "id";
	
	/**
	 * 默认时间格式化
	 */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	
	/**
	 * 后台菜单ID
	 */
	public static final String BACKGROUND_ID="3";
	
	
	/**
	 * oracle时间转换格式
	 */
	public static final String ORACLEFORMAT = "YYYY-MM-DD HH24:MI:SS";

	/**组织（是否是管理员）*/
	public static final String ADMIN="ADMIN";
	
	/** 组织用户身份 */
	public static class ORGUSER_TYPE {
		public static final String CREATER = "CREATER";//创建者
		public static final String MANAGER = "MANAGER";//管理者
		public static final String USER = "USER";//一般用户
	}
	
	/**
	 * 分割符
	 */
	public static final String SPLIT_CHAR = "_";
}
