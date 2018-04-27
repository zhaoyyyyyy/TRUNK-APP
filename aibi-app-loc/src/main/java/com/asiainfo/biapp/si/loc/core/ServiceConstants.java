/*
 * @(#)ServiceConstants.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core;

/**
 * Title : ServiceConstants
 * <p/>
 * Description : 业务常量类,定义业务相关的常量类信息<br>
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date         Modified By    Why & What is modified</pre>
 * <pre>1    2018年2月26日     hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年2月26日
 */

public class ServiceConstants {
    
    //只与业务相关，不与表相关的常量 start
    
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
	 * 指标汇总表前缀
	 */
	public static final String KPI_L_PREF_ = "KPI_L_PREF_";
	/**
	 * 纵表标签目标表前缀
	 */
	public static final String LV = "LV_";

	/**
	 * 标签周期类型，2=月周期
	 */
	public static final int LABEL_CYCLE_TYPE_M = 2;
	/**
	 * 标签周期类型，1=日周期
	 */
	public static final int LABEL_CYCLE_TYPE_D = 1;
	/**
	 * 标签主键字段
	 */
	public static final String LABEL_PRODUCT_NO = "product_no";
	/**
	 * 用户全量表字段前缀
	 */
	public static final String ALL_USER_ORG_LEVEL_ = "ORG_LEVEL_";
	/**
	 * hive库建表的分桶数量
	 */
	public static final Integer LABEL_BUCKET_NUMBER = 200;
	/**
	 * 日周期指标汇总表的保存最大时间
	 */
	public static final Integer MAX_STAT_HISTORY_DAY_INDEX = 31;
	/**
	 * 月周期指标汇总表的保存最大时间
	 */
	public static final Integer MAX_STAT_HISTORY_MONTH_INDEX = 12;
	/**
	 * 日周期标签汇总表保存最大时间
	 */
	public static final Integer MAX_STAT_HISTORY_DAY_DW = 31;
	/**
	 * 月周期标签汇总表保存最大时间
	 */
	public static final Integer MAX_STAT_HISTORY_MONTH_DW = 12;


    //只与业务相关，不与表相关的常量 end
    //只与表相关的常量 start
	/** sysinfo */
	public interface SysInfo{
	    /**
	     * 是否显示在页面上：1-页面显示; 0-不再页面显示
	     */
	    /** 是否显示在页面上：1：页面显示 */
	    public static final Integer SHOW_IN_PAGE_YES = 1;
	    /** 是否显示在页面上：0:不再页面显示 */
	    public static final Integer SHOW_IN_PAGE_NO = 0;
	    
	    /**
	     * 协议类型：默认是1。 1：ftp,2:sftp
	     */
	    /** 协议类型：1：ftp **/
	    public static final int PROTOCO_TYPE_FTP = 1;
	    /** 协议类型：2:sftp **/
	    public static final int PROTOCO_TYPE_SFTP = 2;
	    
	    /**
	     * 协议类型：1:文件推送；2：表接口
	     */
	    /** 协议类型：1:文件推送 **/
	    public static final int PUSH_TYPE_FILE = 1;
	    /** 协议类型：2：表接口 **/
	    public static final int PUSH_TYPE_TABLE = 2;

        /**
         * 是否需要压缩 不需要压缩：0；需要压缩：1
         */
        /** 0：不需要压缩 */
        public static final int IS_NEED_COMPRESS_NO = 0;
        /** 1：需要压缩 */
        public static final int IS_NEED_COMPRESS_YES = 1;
        
        /**
         * 是否需要表头 不需要表头：0；需要表头：1
         */
        /** 0:不需要表头 */
        public static final int IS_NEED_TITLE_NO = 0;
        /** 1:需要表头 */
        public static final int IS_NEED_TITLE_YES = 1;

        /**
         * 是否需要DES加密 不需要：0；需要：1
         */
        /** 0:不需要DES加密 */
        public static final int IS_NEED_DES_NO = 0;
        /** 1:需要DES加密 */
        public static final int IS_NEED_DES_YES = 1;
        
        /**
         * 是否需要XML加密 不需要：0；需要：1
         */
        /** 0:不需要XML加密 */
        public static final int IS_NEED_XML_NO = 0;
        /** 1:需要XML加密 */
        public static final int IS_NEED_XML_YES = 1;
        
        /**
         * 是否允许推送属性：不允许：0；允许：1
         */
        /** 是否允许推送属性：不允许：0 */
        public static final int IS_ALLOW_ATTR_NO = 0;
        /** 是否允许推送属性：允许：1 */
        public static final int IS_ALLOW_ATTR_YES = 1;
	}
	
    /** 标签信息表 */
	public interface LabelInfo{
	    /**
	     * 标签类型：LABEL_TYPE_ID
	     */
	    /** 标签类型，1=标志型，用0、1来作为值的； */
	    public static final int LABEL_TYPE_ID_SIGN = 1;
	    /** 标签类型，2=得分型，用0到1的小数作为值； */
	    public static final int LABEL_TYPE_ID_SCORE = 2;
	    /** 标签类型，3=属性型，单独列，每个值不同的。 */
	    public static final int LABEL_TYPE_ID_ATTR = 3;
	    /** 标签类型，4=指标型，存具体的指标值； */
	    public static final int LABEL_TYPE_ID_KPI = 4;
	    /** 标签类型，5=枚举型，列的值有对应的维表，下拉展示； */
	    public static final int LABEL_TYPE_ID_ENUM = 5;
	    /** 标签类型，6=日期型，字符串类型的日期值。 */
	    public static final int LABEL_TYPE_ID_DATE = 6;
	    /** 标签类型，7=模糊型，存字符串，like查询 */
	    public static final int LABEL_TYPE_ID_TEXT = 7;
	    /** 标签类型，8=复合型，对应多个列，数据是纵表存储。 */
	    public static final int LABEL_TYPE_ID_VERT = 8;
	    /** 标签类型，9=按位与标签 */
	    public static final int LABEL_TYPE_ID_BIT = 9;
	    /** 标签类型，10=组合型 */
	    public static final int LABEL_TYPE_ID_COM = 10;
	    /** 数值泛化后的类型 */
	    public static final int LABEL_TYPE_ID_GEN = 11;
	    /** 标签类型，12=地图型 */
	    public static final int LABEL_TYPE_ID_POSITION = 12;
	    /** 标签类型，13=虚拟型 */
	    public static final int LABEL_TYPE_ID_VIRTUAL = 13;
	    

	    /**
	     * 客户群生成周期:UPDATE_CYCLE：3,一次性;2,月周期;1,日周期;4,无周期
	     */
	    /** 客户群生成周期:1,一次性; */
	    public static final int UPDATE_CYCLE_O = 3;
	    /** 客户群生成周期:2,月周期; */
	    public static final int UPDATE_CYCLE_M = 2;
	    /** 客户群生成周期:3,日周期 */
	    public static final int UPDATE_CYCLE_D = 1;
	    /** 客户群生成周期:4,无周期 */
	    public static final int UPDATE_CYCLE_N = 4;
	    
	    /** 客户群生成周期:1,一次性文本; */
	    public static final String UPDATE_CYCLE_ONE_TEXT = "一次性";
        /** 客户群生成周期:2,月周期文本; */
	    public static final String UPDATE_CYCLE_M_TEXT = "月周期";
        /** 客户群生成周期:3,日周期文本 */
	    public static final String UPDATE_CYCLE_D_TEXT = "日周期";
	    
	    
	    /**
	     * 群类型: 0、标签;1、客户群
	     */
        /** 群类型: 0、标签 */
        public static final int GROUP_TYPE_L = 0;
        /** 群类型:1、客户群 */
        public static final int GROUP_TYPE_G = 1;
        
        
        /**
         * 标签数据状态ID: 未生效,已生效,已失效，冷冻期，已下线
         */
        /** 标签数据状态: 1、未生效 */
        public static final int DATA_STATUS_ID_L_NOT_EFFECT = 1;
        /** 标签数据状态: 2、已生效 */
        public static final int DATA_STATUS_ID_L_EFFECT = 2;
        /** 标签数据状态: 3、已失效 */
        public static final int DATA_STATUS_ID_L_FAILURE = 3;
        /** 标签数据状态: 4、冷冻期 */
        public static final int DATA_STATUS_ID_L_FREEZED = 4;
        /** 标签数据状态: 5、已下线 */
        public static final int DATA_STATUS_ID_L_UNDER = 5;
        /** 标签数据状态: 6、已删除 */
        public static final int DATA_STATUS_ID_L_DELETED = 6;
        
        /**
         * 客户群数据状态
         */
        /** 客户群数据状态：0、 创建失败 */
        public static final int DATA_STATUS_ID_G_FAILED = 0;
        /** 客户群数据状态：1、待 创建 */
        public static final int DATA_STATUS_ID_G_WAIT = 1;
        /** 客户群数据状态：2、 创建中 */
        public static final int DATA_STATUS_ID_G_CREATING = 2;
        /** 客户群数据状态：3、 创建成功 */
        public static final int DATA_STATUS_ID_G_SUCCESS = 3;
        /** 客户群数据状态：4、 预约状态 */
        public static final int DATA_STATUS_ID_G_ORDER = 4;
        
	}
	/** 标签规则表*/
	public interface LabelRule{
	    /** 
	     * 计算元素:CALCU_ELEMENT
	     */
	    /** 运算符 and */
	    public static final String CALCU_ELEMENT_AND = "and";
	    /** 运算符 or */
	    public static final String CALCU_ELEMENT_OR = "or";
	    /** 运算符 剔除 */
	    public static final String CALCU_ELEMENT_EXCEPT = "-";
	    /** 运算符 剔除 */
	    public static final String CALCU_ELEMENT_REMOVE = "REMOVE";
	    
	    /**
	     * 计算元素类型: 1,运算符，2,标签(指标)ID，3,括号，4,产品ID，5清单表名，6.客户群
	     */
	    /** 计算元素类型:1-运算符 */
	    public static final int ELEMENT_TYPE_OPERATOR = 1;
	    /** 计算元素类型:2-标签(指标)ID */
	    public static final int ELEMENT_TYPE_LABEL_ID = 2;
	    /** 计算元素类型: 3-括号 */
	    public static final int ELEMENT_TYPE_BRACKET = 3;
	    /** 计算元素类型: 4-产品ID */
	    public static final int ELEMENT_TYPE_PRODUCT_ID = 4;
	    /** 计算元素类型: 5-清单表名 */
	    public static final int ELEMENT_TYPE_LIST_ID = 5;
	    /** 计算元素类型: 6-客户群 */
	    public static final int ELEMENT_TYPE_CUSTOM_RULES = 6;
	    
	    /**
	     * 是否标识 一、是否取反 1是；2否 二、标识清单数据周期1，日；2，月
	     */
	    /** 是否标识 一、是否取反 1是；2否 二、标识清单数据周期1，日；2，月 */
	    public static final int LABEL_FLAG_YES = 1;
	    /** 是否标识 一、是否取反 1是；2否 二、标识清单数据周期1，日；2，月 */
	    public static final int LABEL_FLAG_NO = 2;

	    /**
	     * 类型：1-客户群，2-模板
	     */
	    /** 类型：1-客户群 */
	    public static final int CUSTOM_TYPE_COSTOMER = 1;
	    /** 类型：2-模板 */
	    public static final int CUSTOM_TYPE_TEMPLATE = 2;
	    
	}
	/** 指标源表状态表  */
    public interface TargetTableStatus{
        /**
         * 数据准备状态：0-未准备，1-准备完成
         */
        /** 数据准备状态：0-未准备 */
        public static final int TARGET_TABLE_NOT_PREPARED= 0;
        /** 数据准备状态：1-准备完成*/
        public static final int TARGET_TABLE_PREPARED = 1;
        
        /**
         * 数据抽取状态：0-抽取中，1-抽取完成，2-抽取失败
         */
        /** 类型：0-抽取中 */
        public static final int TARGET_TABLE_EXTRACTING = 0;
        /** 类型：1-抽取完成 */
        public static final int TARGET_TABLE_EXTRACT_SUCCESS = 1;
        /** 类型：2-抽取失败 */
        public static final int TARGET_TABLE_EXTRACT_FAIL = 2;
    }
    /** 标签状态表    */
    public interface LabelStatus{
        /**
         * 标签生成状态：0-生成失败，1-生成失败，2-客户群暂存
         */
        /** 标签生成状态：0-生成失败 */
        public static final int LABEL_GENERATE_FAIL= 0;
        /** 标签生成状态：1-生成成功*/
        public static final int LABEL_GENERATE_SUCCESS = 1;
        /** 标签生成状态：2-客户群暂存*/
        public static final int CUSTOM_CUR_SAVE = 2;
    }
    /** 清单信息表   */
    public interface ListInfo{
        /**
         * 客户群生成状态：0-创建失败，1-待创建，2-创建中,3-创建成功,4-预约
         */
        /** 客户群生成状态：0-创建失败 */
        public static final int CUSTOM_GENERATE_FAIL= 0;
        /** 客户群生成状态：1-待创建*/
        public static final int CUSTOM_GENERATE_PREPARE = 1;
        /** 客户群生成状态：2-创建中*/
        public static final int CUSTOM_GENERATE_CREATING = 2;
        /** 客户群生成状态：3-创建成功*/
        public static final int CUSTOM_GENERATE_SUCCESS = 3;
        /** 客户群生成状态：4-预约*/
        public static final int CUSTOM_GENERATE_APPOINT = 4;
    }
    
	/** 元数据表列表 */
	public interface MdaSysTableColumn{

	    /** 
	     * 标签对应的元数据列类型:column_data_type_id
	     */
	    /** 元数据列类型，数字类型:1 */
	    public static final int COLUMN_DATA_TYPE_ID_NUM = 1;
	    /** 元数据列类型，字符串类型:2 */
	    public static final int COLUMN_DATA_TYPE_ID_VARCHAR = 2;
	    
	    /** 元数据列类型，数字类型:number */
	    public static final String COLUMN_DATA_TYPE_ID_NUM_NUMBER = "number";
	    /** 元数据列类型，小数类型:decimal */
	    public static final String COLUMN_DATA_TYPE_ID_NUM_DECIMAL = "decimal";
	    /** 元数据列类型，数字类型:integer */
	    public static final String COLUMN_DATA_TYPE_ID_NUM_INTEGER = "integer";
	    /** 元数据列类型，字符串类型:2 */
	    public static final String COLUMN_DATA_TYPE_ID_VARCHAR_CHAR = "char";
	    /** 元数据列默认类型，字符串类型:varchar */
	    public static final String COLUMN_DATA_TYPE_ID_VARCHAR_VARCHAR = "VARCHAR";
	    /** 元数据列默认长度，字符串类型:varchar */
	    public static final String COLUMN_DATA_TYPE_ID_LENGTH = "32";
	}
	
	/** 标签推送设置信息表 */
	public interface LabelPushCycle{
	    /** 
	     * 推送周期:PUSH_CYCLE:1,一次性；2,周期性
	     */
        /** 推送周期:1,一次性; */
        public static final int PUSH_CYCLE_O = 1;
        /** 推送周期:2,周期性; */
        public static final int PUSH_CYCLE_C = 2;
        

        /** 
         * 数据状态：1,有效；0，删除；2.失败
         */
        /** 数据状态：0，删除; */
        public static final int STATUS_DEL = 0;
        /** 数据状态：1,有效; */
        public static final int STATUS_YES = 1;
        /** 数据状态：2.失败; */
        public static final int STATUS_NO = 2;
	}
	
	/** 标签推送设置信息表 */
	public interface LabelPushReq{
	  /**
	   * 客户群推送状态：1：等待推送 2：推送中 3：推送成功 0：推送失败
	   */
	  /** 推送，1 = 等待推送  */
	  public static final int PUSH_STATUS_WAIT = 1;
	  /** 推送，2 = 推送ing */
	  public static final int PUSH_STATUS_PUSHING = 2;
	  /** 推送，3 = 推送成功 */
	  public static final int PUSH_STATUS_SUCCESS = 3;
	  /** 推送，0 = 推送失败 */
	  public static final int PUSH_STATUS_FAILED = 0;
	}
	/** 客户群下载记录表 */
	public interface CustomDownloadRecord{
	    /**
	     * 数据状态：1:未生成，2：生成中，3：已生成，4：失败
	     */
	    /** 数据状态:1:未生成  */
	    public static final int DATA_STATUS_WAIT = 1;
	    /** 数据状态:2：生成中 */
	    public static final int DATA_STATUS_DOING = 2;
	    /** 数据状态:3：已生成 */
	    public static final int DATA_STATUS_SUCCESS = 3;
	    /** 数据状态:4：失败 */
	    public static final int DATA_STATUS_FAILED = 0;
	}
	public interface LabelAttrRel{
	    /**
	     * 属性来源：1,导入，2,标签,3.客户群
	     */
	    /** 属性来源：2,标签 */
	    public static final int ATTR_SOURCE_LABEL = 2;

	    /**
	     * 状态：0,已失效;1,有效
	     */
	    /** 状态：0,已失效 */
	    public static final int STATUS_FAILED = 0;
	    /** 状态：1,有效 */
	    public static final int STATUS_SUCCESS = 1;
	    
	    /**
	     * 属性类型：1:客户群推送；2：清单下载；3：清单预览
	     */
	    /** 属性类型：1:客户群推送 */
	    public static final int ATTR_SETTING_TYPE_PUSH = 1;
	    /** 属性类型：3：清单预览 */
	    public static final int ATTR_SETTING_TYPE_PREVIEW = 3;
	    
	}

    //只与表相关的常量 end
	
}
