package com.asiainfo.biapp.si.loc.base.common;

/**
 * 
 * Title: CommonConstants.java <br>
 * Description: 系统常量类,定义一些与业务无关的代码级常量 <br>
 * Copyright: (C) Copyright 1993-2020 AsiaInfo Holdings, Inc<br>
 * Company: 亚信联创科技（中国）有限公司<br>
 * 
 * @author chengjia 2013-5-7 上午10:57:07
 * @version 1.0
 */
public class CommonConstants {

	/**
	 * 上传文件的子目录名
	 */
	public static final String CI_STORE_SUB_PATH = "loc";
	
	/**
	 * 最新数据时间在缓存中的key
	 */
	public static final String NEW_DATE = "NEW_DATE_";
	
	/**
	 * 精确值表中列名
	 * 
	 */
	public static final String LABEL_EXACT_VALUE_TABLE_COLUMN = "VALUE_ID";
	
	/** 当前专区id key */
	public static final String CURRENT_CONFIG_ID = "currentConfigId";
	
	/**
	 * 数据库运算符号
	 */
	/* 小于 */
	public static final String LT = "<";
	/* 大于 */
	public static final String GT = ">";
	/* 小于等于 */
	public static final String LE = "<=";
	/* 大于等于 */
	public static final String GE = ">=";
	/* 不等于 */
	public static final String NE = "<>";
	/* 等于 */
	public static final String EQ = "=";

	/**
	 * 并
	 */
	public static final char UNION = '∪';
	/**
	 * 交
	 */
	public static final char INTERSECT = '∩';
	/**
	 * 差
	 */
	public static final char EXCEPT = '-';
	/**
	 * 左括号
	 */
	public static final char LEFT_Q = '(';
	/**
	 * 右括号
	 */
	public static final char RIGHT_Q = ')';

}
