package com.asiainfo.biapp.si.loc.core.label.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.common.CommonConstants;
import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.core.label.service.IGroupCalcSqlPaser;

@Service("sparkSqlPaser")
public class SparkSqlPaserImpl implements IGroupCalcSqlPaser {

	// private Logger log = Logger.getLogger(SparkSqlPaserImpl.class);
	/**
	 * 并集sql模板
	 */
	private String UNION_SQL = "";
	/**
	 * 交集sql模板
	 */
	private String INTERSECT_SQL = "";
	/**
	 * 差集sql模板
	 */
	private String EXCEPT_SQL = "";
	/**
	 * 表名正则
	 */
	private static String TABLE_NAME_PARTTERN = "\\w*";

	private int index = 0;

	public SparkSqlPaserImpl() {
		String pk = LabelInfoContants.KHQ_CROSS_COLUMN;
		// 根据不同的数据库类型进行优化
		UNION_SQL = "select distinct tableName3.pk from (select  tableName1.pk from table1 tableName1 UNION all select pk from table2 tableName2) tableName3";
		INTERSECT_SQL = "select tableName1.pk from table1 tableName1 join table2 tableName2 on tableName1.pk = tableName2.pk ";
		EXCEPT_SQL = " select tableName1.pk from table1 tableName1 left join table2 tableName2 on tableName1.pk = tableName2.pk where tableName2.pk is null ";
		UNION_SQL = UNION_SQL.replace("pk", pk);
		INTERSECT_SQL = INTERSECT_SQL.replace("pk", pk);
		EXCEPT_SQL = EXCEPT_SQL.replace("pk", pk);
	}

	public boolean isDbType(String dbType) {
		return true;
	}

	/**
	 * 返回并集的sql
	 * 
	 * @param table1
	 * @param table2
	 * @return
	 */
	public String getUinionOfTable(String table1, String table2, Map<String, String> labelRuleToSql) {
		if (labelRuleToSql != null) {
			if (labelRuleToSql.containsKey(table1)) {
				table1 = labelRuleToSql.get(table1);
			}
			if (labelRuleToSql.containsKey(table2)) {
				table2 = labelRuleToSql.get(table2);
			}
		}

		return UNION_SQL.replace("table1", handelTableName(table1)).replace("tableName1", getTableName())
				.replace("table2", handelTableName(table2)).replace("tableName2", getTableName())
				.replace("tableName3", getTableName());
	}

	/**
	 * 返回并集的sql
	 * 
	 * @param table1
	 * @param table2
	 * @return
	 */
	public String getUinionOfTable(String table1, String table2) {
		return UNION_SQL.replace("table1", handelTableName(table1)).replace("tableName1", getTableName())
				.replace("table2", handelTableName(table2)).replace("tableName2", getTableName());
	}

	/**
	 * 别名
	 * 
	 * @return
	 */
	public String getTableName() {
		return "temp" + index++;
	}

	/**
	 * 处理表名
	 * 
	 * @param table
	 * @return
	 */
	public String handelTableName(String table) {
		if (isVerticalLabel(table)) {
			return String.valueOf(CommonConstants.LEFT_Q) + " select pk from " + table
					+ String.valueOf(CommonConstants.RIGHT_Q);
		} else if (isInnerTable(table)) {
			return String.valueOf(CommonConstants.LEFT_Q) + table + String.valueOf(CommonConstants.RIGHT_Q);
		} else if (isSubquery(table)) {
			return table;
		} else if (isWhereSql(table)) {
			return String.valueOf(CommonConstants.LEFT_Q) + " select pk from " + table
					+ String.valueOf(CommonConstants.RIGHT_Q);
		} else {
			return table;
		}
	}

	/**
	 * 返回交集的sql
	 * 
	 * @param table1
	 * @param table2
	 * @return
	 */
	public String getIntersectOfTable(String table1, String table2, Map<String, String> labelRuleToSql) {
		if (labelRuleToSql != null) {
			if (labelRuleToSql.containsKey(table1)) {
				table1 = labelRuleToSql.get(table1);
			}
			if (labelRuleToSql.containsKey(table2)) {
				table2 = labelRuleToSql.get(table2);
			}
		}

		return INTERSECT_SQL.replace("table1", handelTableName(table1)).replace("tableName1", getTableName())
				.replace("table2", handelTableName(table2)).replace("tableName2", getTableName());
	}

	/**
	 * 返回交集的sql
	 * 
	 * @param table1
	 * @param table2
	 * @return
	 */
	public String getIntersectOfTable(String table1, String table2) {
		return INTERSECT_SQL.replace("table1", handelTableName(table1)).replace("tableName1", getTableName())
				.replace("table2", handelTableName(table2)).replace("tableName2", getTableName());
	}

	/**
	 * 返回差集的sql (table1 - table2)
	 * 
	 * @param table1
	 * @param table2
	 * @return
	 */
	public String getDifferenceOfTable(String table1, String table2, Map<String, String> labelRuleToSql) {
		if (labelRuleToSql != null) {
			if (labelRuleToSql.containsKey(table1)) {
				table1 = labelRuleToSql.get(table1);
			}
			if (labelRuleToSql.containsKey(table2)) {
				table2 = labelRuleToSql.get(table2);
			}
		}

		return EXCEPT_SQL.replace("table1", handelTableName(table1)).replace("tableName1", getTableName())
				.replace("table2", handelTableName(table2)).replace("tableName2", getTableName());
	}

	/**
	 * 返回差集的sql (table1 - table2)
	 * 
	 * @param table1
	 * @param table2
	 * @return
	 */
	public String getDifferenceOfTable(String table1, String table2) {
		return EXCEPT_SQL.replace("table1", handelTableName(table1)).replace("tableName1", getTableName())
				.replace("table2", handelTableName(table2)).replace("tableName2", getTableName());
	}

	/**
	 * 将表达式转换为sql
	 * 
	 * @param expr
	 * @return
	 */
	public String parseExprToSql(String expr, Map<String, String> labelRuleToSql) {
		String parsed = "";
		while (expr.length() > 0) {
			String v1 = getNextVal(expr);
			expr = expr.substring(v1.length());
			if (isExpr(v1)) {
				if (v1.startsWith(String.valueOf(CommonConstants.LEFT_Q))
						&& v1.endsWith(String.valueOf(CommonConstants.RIGHT_Q))) {
					v1 = String.valueOf(CommonConstants.LEFT_Q)
							+ parseExprToSql(v1.substring(1, v1.length() - 1), labelRuleToSql)
							+ String.valueOf(CommonConstants.RIGHT_Q);
				}
			} else {
				v1 = v1.replace(String.valueOf(CommonConstants.LEFT_Q), "");
				v1 = v1.replace(String.valueOf(CommonConstants.RIGHT_Q), "");
			}
			if (expr.length() == 0) {
				if (v1.startsWith(String.valueOf(CommonConstants.LEFT_Q))
						&& v1.endsWith(String.valueOf(CommonConstants.RIGHT_Q))) {
					v1 = v1.substring(1, v1.length() - 1);
				}
				return v1;
			}
			String v2 = getNextVal(expr);
			expr = expr.substring(v2.length());
			if (isOpt(v1)) {
				if (isExpr(v2)) {
					if (v2.startsWith(String.valueOf(CommonConstants.LEFT_Q))
							&& v2.endsWith(String.valueOf(CommonConstants.RIGHT_Q))) {
						v2 = String.valueOf(CommonConstants.LEFT_Q)
								+ parseExprToSql(v2.substring(1, v2.length() - 1), labelRuleToSql)
								+ String.valueOf(CommonConstants.RIGHT_Q);
					}
				} else {
					v2 = v2.replace(String.valueOf(CommonConstants.LEFT_Q), "");
					v2 = v2.replace(String.valueOf(CommonConstants.RIGHT_Q), "");
				}
				// 第一轮while之后的v1就是opt了，用已经生成的sql（parsed）做第一个参数继续处理
				parsed = toSql(parsed, v1, v2, labelRuleToSql);
				continue;
			}
			String v3 = getNextVal(expr);
			if (expr.length() > v3.length()) {
				expr = expr.substring(v3.length());
			} else if (expr.length() == v3.length()) {
				expr = expr.replace(v3, "");
			}
			if (isExpr(v3)) {
				if (v3.startsWith(String.valueOf(CommonConstants.LEFT_Q))
						&& v3.endsWith(String.valueOf(CommonConstants.RIGHT_Q))) {
					v3 = String.valueOf(CommonConstants.LEFT_Q)
							+ parseExprToSql(v3.substring(1, v3.length() - 1), labelRuleToSql)
							+ String.valueOf(CommonConstants.RIGHT_Q);
				}
			} else {
				v3 = v3.replace(String.valueOf(CommonConstants.LEFT_Q), "");
				v3 = v3.replace(String.valueOf(CommonConstants.RIGHT_Q), "");
			}
			parsed = toSql(v1, v2, v3, labelRuleToSql);
		}

		return parsed;
	}

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
	public String toSql(String v1, String v2, String v3) {
		return toSql(v1, v2, v3, null);
	}

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
	public String toSql(String v1, String v2, String v3, Map<String, String> labelRuleToSql) {
		String resultSql = "";
		if (CommonConstants.UNION == v2.charAt(0)) {
			resultSql = getUinionOfTable(v1, v3, labelRuleToSql);
		} else if (CommonConstants.INTERSECT == v2.charAt(0)) {
			resultSql = getIntersectOfTable(v1, v3, labelRuleToSql);
		} else if (CommonConstants.EXCEPT == v2.charAt(0)) {
			resultSql = getDifferenceOfTable(v1, v3, labelRuleToSql);
		} else {
			throw new RuntimeException("not well format[" + v1 + v2 + v3 + "]");
		}
		String pk = LabelInfoContants.KHQ_CROSS_COLUMN;
		resultSql = resultSql.replace("pk", pk);
		return resultSql;
	}

	/**
	 * 从表达式中获取一个变量或者操作符
	 * 
	 * @param expr
	 * @return
	 */
	public String getNextVal(String expr) {
		char start = expr.charAt(0);
		if (CommonConstants.UNION == start || CommonConstants.INTERSECT == start || CommonConstants.EXCEPT == start) {
			return String.valueOf(start);
		} else if (CommonConstants.LEFT_Q == start && expr.length() > 1) {
			// 找到与之匹配的right
			int leftCount = 0;
			int rightPos = 0;
			for (int i = 1; i < expr.length(); i++) {
				if (expr.charAt(i) == CommonConstants.LEFT_Q) {
					leftCount++;
				}
				if (leftCount == 0 && expr.charAt(i) == CommonConstants.RIGHT_Q) {
					rightPos = i + 1;
					break;
				} else if (expr.charAt(i) == CommonConstants.RIGHT_Q) {
					leftCount--;
				}
			}
			if (rightPos == 0) {
				throw new RuntimeException("not match quot" + expr);
			} else {
				return expr.substring(0, rightPos);
			}
		} else {
			int rightPos = 0;
			for (int i = 1; i < expr.length(); i++) {
				rightPos = i + 1;
				if (CommonConstants.RIGHT_Q == expr.charAt(i) || CommonConstants.LEFT_Q == expr.charAt(i)
						|| CommonConstants.UNION == expr.charAt(i) || CommonConstants.INTERSECT == expr.charAt(i)
						|| CommonConstants.EXCEPT == expr.charAt(i)) {
					rightPos = i;
					break;
				}

			}
			return expr.substring(0, rightPos);
		}
	}

	/**
	 * 是否是真的表 区别于子查询
	 * 
	 * @param str
	 * @return
	 */
	public boolean isRealTable(String str) {
		return str.matches(TABLE_NAME_PARTTERN);
	}

	/**
	 * 是否是子查询
	 * 
	 * @param str
	 * @return
	 */
	public boolean isInnerTable(String str) {
		return str.toLowerCase().trim().startsWith("select ");
	}

	/**
	 * 判断是否where语句
	 * 
	 * @param str
	 * @return
	 */
	private boolean isWhereSql(String str) {
		return !str.toLowerCase().trim().startsWith("select") && !str.startsWith(String.valueOf(CommonConstants.LEFT_Q))
				&& str.toLowerCase().indexOf("where") != -1;
	}

	/**
	 * 是否带括号的子查询
	 * 
	 * @param str
	 * @return
	 */
	public boolean isSubquery(String str) {
		return str.startsWith(String.valueOf(CommonConstants.LEFT_Q))
				&& str.endsWith(String.valueOf(CommonConstants.RIGHT_Q));
	}

	/**
	 * 是否是表达式
	 * 
	 * @param str
	 * @return
	 */
	public boolean isExpr(String str) {
		return str.indexOf(CommonConstants.UNION) > 0 || str.indexOf(CommonConstants.INTERSECT) > 0
				|| str.indexOf(CommonConstants.EXCEPT) > 0;
	}

	/**
	 * 是否是运算符
	 * 
	 * @param str
	 * @return
	 */
	public boolean isOpt(String str) {
		if (str == null || str.length() != 1) {
			return false;
		}
		if (CommonConstants.UNION == str.charAt(0) || CommonConstants.INTERSECT == str.charAt(0)
				|| CommonConstants.EXCEPT == str.charAt(0)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isVerticalLabel(String table) {
		return table.trim().startsWith(String.valueOf(CommonConstants.LEFT_Q))
				&& !table.trim().startsWith(String.valueOf(CommonConstants.RIGHT_Q));
	}

}
