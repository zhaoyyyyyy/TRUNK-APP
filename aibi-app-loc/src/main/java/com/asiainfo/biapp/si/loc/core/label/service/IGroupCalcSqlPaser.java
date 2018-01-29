package com.asiainfo.biapp.si.loc.core.label.service;

import java.util.Map;

public interface IGroupCalcSqlPaser {
	/**
	 * 判断数据库类型
	 * 
	 * @param dbType
	 * @return
	 */
	public boolean isDbType(String dbType);

	/**
	 * 将表达式转换为sql
	 * 
	 * @param expr
	 *            表达式
	 * @param labelRuleToSql
	 *            map键值对
	 * @return
	 */
	public String parseExprToSql(String expr, Map<String, String> labelRuleToSql);

	/**
	 * 返回交集的sql
	 * 
	 * @param table1
	 * @param table2
	 * @return
	 */
	public String getIntersectOfTable(String table1, String table2);

	/**
	 * 返回差集的sql
	 * 
	 * @param table1
	 * @param table2
	 * @return
	 */
	public String getDifferenceOfTable(String table1, String table2);

	/**
	 * 返回并集的sql
	 * 
	 * @param table1
	 * @param table2
	 * @return
	 */
	public String getUinionOfTable(String table1, String table2);

	/**
	 * 基本表达式to SQL
	 * 
	 * @param v1
	 *            表名1
	 * @param v2
	 *            操作符
	 * @param v3
	 *            表名2
	 * @return
	 */
	public String toSql(String v1, String v2, String v3);

}
