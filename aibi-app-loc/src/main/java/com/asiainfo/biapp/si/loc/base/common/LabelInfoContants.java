
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
	
	/**LABELINFO 群类型 0、标签;1、客户群 */
    public static final int LABEL_GROUP_TYPE_LABEL = 0;
  
    public static final int LABEL_GROUP_TYPE_CUST = 1;
	
	/**
	 * 标签类型
	 */
	/** 标签类型，1=标识型，用0、1来作为值的； */
	public static final int LABEL_TYPE_SIGN = 1;
	/** 标签类型，2=客户群型，客户群标签； */
	public static final int LABEL_TYPE_CUST = 2;
	/** 标签类型，3=属性型，单独列，每个值不同的。 */
	public static final int LABEL_TYPE_ATTR = 3;
	/** 标签类型，4=指标型，存具体的指标值； */
	public static final int LABEL_TYPE_KPI = 4;
	/** 标签类型，5=枚举型，列的值有对应的维表，下拉展示； */
	public static final int LABEL_TYPE_ENUM = 5;
	/** 标签类型，6=日期型，字符串类型的日期值。 */
	public static final int LABEL_TYPE_DATE = 6;
	/** 标签类型，7=文本型，存字符串，like查询 */
	public static final int LABEL_TYPE_TEXT = 7;
	/** 标签类型，8=复合型，对应多个列，数据是纵表存储。 */
	public static final int LABEL_TYPE_VERT = 8;
	/** 标签类型，9=按位与标签 */
	public static final int LABEL_TYPE_BIT = 9;
	/** 标签类型，10=组合型 */
	public static final int LABEL_TYPE_COM = 10;
	/** 数值泛化后的类型 */
	public static final int LABEL_TYPE_GEN = 11;
	/** 标签类型，12=地图型 */
    public static final int LABEL_TYPE_POSITION = 12;
    /** 标签类型，13=虚拟型 */
    public static final int LABEL_TYPE_VIRTUAL = 13;
	
    
	/** 标签类型，1=标志型，用0、1来作为值的； */
	public static final String LABEL_TYPE_SIGN_STR = "标识型";
	/** 标签类型，2=得分型，用0到1的小数作为值； */
	public static final String LABEL_TYPE_SCORE_STR = "得分型";
	/** 标签类型，3=属性型，单独列，每个值不同的。 */
	public static final String LABEL_TYPE_ATTR_STR = "属性型";
	/** 标签类型，4=指标型，存具体的指标值； */
	public static final String LABEL_TYPE_KPI_STR = "指标型";
	/** 标签类型，5=枚举型，列的值有对应的维表，下拉展示； */
	public static final String LABEL_TYPE_ENUM_STR = "枚举型";
	/** 标签类型，6=日期型，字符串类型的日期值。 */
	public static final String LABEL_TYPE_DATE_STR = "日期型";
	/** 标签类型，7=文本型，存字符串，like查询 */
	public static final String LABEL_TYPE_TEXT_STR = "文本型";
	/** 标签类型，8=组合型，对应多个列，数据是纵表存储。 */
	public static final String LABEL_TYPE_VERT_STR = "组合型";
	/** 标签类型，9=按位与标签。 */
	public static final String LABEL_TYPE_BIT_STR = "按位与标签";
	/** 标签类型，10=组合型 */
	public static final String LABEL_TYPE_COM_STR = "组合型 ";
	/** 标签类型，11=数值泛化*/
	public static final String LABEL_TYPE_GEN_STR = "数值泛化";
	/** 标签类型，12=地图型 */
    public static final String LABEL_TYPE_POSITION_STR = "位置型";
    /** 标签类型，13=虚标签型 */
    public static final String LABEL_TYPE_VIR_STR = "虚标签";
    
    /**
	 * 标签周期类型
	 */
	/** 标签周期类型，1=日周期  */
	public static final int LABEL_CYCLE_TYPE_D = 1;
	/** 标签周期类型，2=月周期  */
	public static final int LABEL_CYCLE_TYPE_M = 2;
	/** 标签周期类型，日周期:1 */
	public static final String DAY_CYCLE_LABEL = "日周期";
	/** 标签周期类型，月周期:2 */
	public static final String MONTH_CYCLE_LABEL = "月周期";
    
	/**
	 * 客户群生成周期:1,一次性;2,月周期;3,日周期;4,无周期
	 */
	/** 客户群生成周期:1,一次性; */
	public static final int CUSTOM_CYCLE_TYPE_ONE = 3;
	/** 客户群生成周期:2,月周期; */
	public static final int CUSTOM_CYCLE_TYPE_M = 2;
	/** 客户群生成周期:3,日周期 */
	public static final int CUSTOM_CYCLE_TYPE_D = 1;
	/** 客户群生成周期:4,无周期 */
	public static final int CUSTOM_CYCLE_TYPE_N = 4;

	public static final String CUSTOM_CYCLE_ONE = "一次性";

	public static final String CUSTOM_CYCLE_M = "月周期";

	public static final String CUSTOM_CYCLE_D = "日周期";
	
    
    /** 
     * 标签对应的元数据列类型:column_data_type_id
	 */
	/** 元数据列类型，数字类型:1 */
	public static final int COLUMN_DATA_TYPE_NUM = 1;
	/** 元数据列类型，字符串类型:2 */
	public static final int COLUMN_DATA_TYPE_VARCHAR = 2;
	/** 元数据列类型，数字类型:number */
	public static final String COLUMN_TYPE_NUMBER = "number";
	/** 元数据列类型，字符串类型:2 */
	public static final String COLUMN_TYPE_CHAR = "char";
	/** 元数据列类型，小数类型:decimal */
	public static final String COLUMN_TYPE_DECIMAL = "decimal";
	/** 元数据列类型，数字类型:integer */
	public static final String COLUMN_TYPE_INTEGER = "integer";
	/** 元数据列默认类型，字符串类型:varchar */
	public static final String COLUMN_TYPE_VARCHAR = "VARCHAR";
	/** 元数据列默认长度，字符串类型:varchar */
	public static final String COLUMN_TYPE_LENGTH = "32";
    
	/** 标签数据状态: 1、未生效 */
	public static final int LABEL_DATA_STATUS_ID_NOT_EFFECT = 1;
	/** 标签数据状态: 2、已生效 */
	public static final int LABEL_DATA_STATUS_ID_EFFECT = 2;
	/** 标签数据状态: 3、已失效 */
	public static final int LABEL_DATA_STATUS_ID_FAILURE = 3;
	/** 标签数据状态: 4、冷冻期 */
	public static final int LABEL_DATA_STATUS_ID_FREEZED = 4;
	/** 标签数据状态: 5、已下线 */
	public static final int LABEL_DATA_STATUS_ID_UNDER = 5;
	/** 标签数据状态: 6、已删除 */
	public static final int LABEL_DATA_STATUS_ID_DELETED = 6;

	/**
	 * 客户群数据状态
	 */
	/** 客户群数据状态：0、 创建失败 */
	public static final int CUSTOM_DATA_STATUS_FAILED = 0;
	/** 客户群数据状态：1、待 创建 */
	public static final int CUSTOM_DATA_STATUS_WAIT = 1;
	/** 客户群数据状态：2、 创建中 */
	public static final int CUSTOM_DATA_STATUS_CREATING = 2;
	/** 客户群数据状态：3、 创建成功 */
	public static final int CUSTOM_DATA_STATUS_SUCCESS = 3;
	/** 客户群数据状态：4、 预约状态 */
	public static final int CUSTOM_DATA_STATUS_ORDER = 4;
	
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
