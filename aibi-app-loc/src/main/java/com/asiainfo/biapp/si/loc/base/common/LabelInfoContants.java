
package com.asiainfo.biapp.si.loc.base.common;
/**
 * 
 * Title : LabelInfoContants
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
public interface LabelInfoContants {
	
	/**
	 * 客户群标签纵表的表头， 后面需要拼写上 DW_KHQ_CROSS_configid_date
	 */
	public static final String KHQ_CROSS_TABLE = "DW_KHQ_CROSS_";
	/**
	 * 客户群标签纵表的表头， 后面需要拼写上 DW_KHQ_CROSS_ONCE_configid
	 */
	public static final String KHQ_CROSS_ONCE_TABLE = "DW_KHQ_CROSS_ONCE_";
	/**
	 * 客户群当天数据写入的纵表列名
	 */
	public static final String KHQ_CROSS_COLUMN = "product_no";
	/**
	 * 客群群当天写入数据的纵表的分区字段名
	 */
	public static final String KHQ_CROSS_ID_PARTION = "custom_id";
	/**
	 * 客群群当天写入数据的纵表的分区字段名
	 */
	public static final String KHQ_CROSS_DATE_PARTION = "STAT_DATE";

	
	
	/**
	 * 清单策略
	 */
	/** 预约策略 清单策略一：按统一的最新数据执行，没有数据，延迟生成清单 **/
	public static final String LIST_TABLE_TACTICS_ID_ONE = "1";
	/** 保守策略 清单策略三：按最晚（用户选定的日期或者月份）执行跑清单 **/
	public static final String LIST_TABLE_TACTICS_ID_THREE = "2";
	
	/**
	 * 验证标签数据以确定清单如何处理
	 */
	/** 标签失效 **/
	public static final String VALIDATE_RESULT_INVALID = "-1";
	/** 等待创建 **/
	public static final String VALIDATE_RESULT_WAIT = "1";
	/** 立即执行 **/
	public static final String VALIDATE_RESULT_GO = "2";
	/** 等待周期job执行 **/
	public static final String VALIDATE_RESULT_NEW = "3";
	
	
}
