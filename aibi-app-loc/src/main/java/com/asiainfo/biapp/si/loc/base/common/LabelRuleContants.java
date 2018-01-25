
package com.asiainfo.biapp.si.loc.base.common;
/**
 * 
 * Title : LabelRuleContants
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 6.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年12月18日    tianxy3        Created</pre>
 * <p/>
 *
 * @author  tianxy3
 * @version 1.0.0.2017年12月18日
 */
public interface LabelRuleContants {

	/**
	 * 购物车在session中存放的名称
	 */
	/** 具体规则存放的名称 */
	public static final String SHOP_CART_RULE = "sessionModelList";
	/** 标签和客户群规则的数量名称 */
	public static final String SHOP_CART_RULE_NUM = "calcElementNum";
	
	/**
	 * 购物车session使用
	 */
	/** 标签 **/
	public static String LABEL_INFO_CALCULATIONS_TYPEID = "1";
	/** 客户群 **/
	public static String CUSTOM_GROUP_INFO_CALCULATIONS_TYPEID = "2";
	
	/**
	 * 元素类型
	 */
	/** 元素类型，运算符 */
	public static final int ELEMENT_TYPE_OPERATOR = 1;
	/** 元素类型，标签ID */
	public static final int ELEMENT_TYPE_LABEL_ID = 2;
	/** 元素类型，括号 */
	public static final int ELEMENT_TYPE_BRACKET = 3;
	/** 元素类型，产品ID */
	public static final int ELEMENT_TYPE_PRODUCT_ID = 4;
	/** 元素类型，清单表名 */
	public static final int ELEMENT_TYPE_LIST_ID = 5;
	/** 元素类型，客户群规则 */
	public static final int ELEMENT_TYPE_CUSTOM_RULES = 6;
	
	/**
	 * 标签规则是否取“非” 0：取非，1：不取非
	 */
	/** 标签规则“非”的值 */
	public static final int LABEL_RULE_FLAG_NO = 0;
	/** 标签规则“是”的值 */
	public static final int LABEL_RULE_FLAG_YES = 1;
	
	/**日期类标签是否偏移量**/
	/** 1:需要 **/
	public static int IS_NEED_OFFSET_YES = 1;
	/** 0：不需要 **/
	public static int IS_NEED_OFFSET_NO = 0;
	
	/**
	 * 1客户群，2模板
	 */
	/** 1客户群 */
	public static final int LABEL_RULE_FROM_COSTOMER = 1;
	/** 2模板 */
	public static final int LABEL_RULE_FROM_TEMPLATE = 2;
	
	/**
	 * 标签运算运算符规则
	 */
	/** 运算符 and */
	public static final String CALCULATE_ELEMENT_TYPE_OPT_AND = "and";
	/** 运算符 or */
	public static final String CALCULATE_ELEMENT_TYPE_OPT_OR = "or";
	/** 运算符 剔除 */
	public static final String CALCULATE_ELEMENT_TYPE_OPT_EXCEPT = "-";
	/** 运算符 剔除 */
	public static final String CUSTOM_CALC_ELEMENT_TYPE_OPT_REMOVE = "REMOVE";
	
}
