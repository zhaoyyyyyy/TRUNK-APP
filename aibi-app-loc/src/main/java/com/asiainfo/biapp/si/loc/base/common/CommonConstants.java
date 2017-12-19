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
	/** 表示权限及日志是使用北京还是南京（BJ、NJ） */
	public static String base = "BJ";
	/** 南研日志登录资源类型 **/
	public static String loginResourceType = "6008";
	/** 南研日志登出资源类型 **/
	public static String logoutResourceType = "6009";
	
	/**
	 * oracle select \/* + parallel(auto)*\/ 
	 */
	public static String sqlParallel = "";
	
	/**
	 * oracle insert \/*+append*\/ into 
	 */
	public static String sqlAppend = "";
	
	/**
	 * 项目模式
	 */
	/** 开发态 **/
	public static final String PROJECT_MODEL_DEV = "DEV";
	/** 部署态 **/
	public static final String PROJECT_MODEL_DEP = "DEP";
	
	/**
	 * 日期格式
	 */
	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	public static final String DATE_FORMAT_YYYYMM = "yyyyMM";
	public static final String DATE_FORMAT_YYYY = "yyyy";
	public static final String DATE_FORMAT_YYMM = "yyMM";
	public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String DATE_FORMAT_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

	// =================================add by fuyu
	// begin===========================================
	/**
	 * 维表数据中全部的默认值
	 */
	public static final int ALL_VALUE = -1;
	
	/**
	 * 无数据常量
	 */
	public static final String NO_DATA_TO_DISPLAY = "暂时没有数据！";
	
	/**
	 * 构成分析历史趋势常量字符串
	 */
	public static final int FORM_TREND_VALUE = 6;
	
	/**
	 * 构成分析表格每页数据量
	 */
	public static final int FORM_PAGE_SIZE = 6;
	
	/**
	 * 宽表标签构成维度表，对应properties的属性名
	 */
	public static final String DW_LABEL_FORM_TABLE = "DW_LABEL_FORM_TABLE";

	/**
	 * 宽表标签构成维度表最新月份表后面不带日期后缀，对应properties的属性名
	 */
	public static final String DW_LABEL_FORM_TABLE_NO_DATE = "DW_LABEL_FORM_TABLE_NO_DATE";
	
	/**
	 * 最新数据时间在缓存中的key
	 */
	public static final String NEW_DATE = "NEW_DATE";
	// =================================add by fuyu
	// end===========================================

	/**
	 * 每页记录条数
	 */
	public static int PAGESIZE = 10;

	/**
	 * 排序常量：递增
	 */
	public static final int ORDER_ASC = 0;

	/**
	 * 排序常量：递减
	 */
	public static final int ORDER_DESC = 1;
	
	/**
	 * 配置文件路径
	 */
	public static final String CONFIG_PATH = "/config/aibi_ci/ci.properties";
	
	/**
	 * 上传文件的子目录名
	 */
	public static final String CI_STORE_SUB_PATH = "ci";
	/**
	 * 清单模板表名
	 */
	public static final String CUST_LIST_TMP_TABLE = "CUST_LIST_TMP_TABLE";

	/**
	 * 客户群宽表主表
	 */
	public static final String CUSTOMER_MAIN_TABEL = "CUSTOMER_MAIN_TABEL";
	
	/**
	 * 清单表名后缀
	 */
	public static final String CUST_LIST_TMP_TABLE_POSTFIX = "YYMMDDHHMISSTTTTTT";
	
	/**
	 * 清单表主键
	 */
	public static final String MAIN_COLUMN = "MAIN_COLUMN";
	
	/**
	 * 清单表主键列类型
	 */
	public static String MAIN_COLUMN_TYPE = "varchar(32)";
	
	/**
	 * 客户群文件导入批量提交数
	 */
	public static final String BATCH_COUNT = "BATCH_COUNT";

	/**************** 维表缓存表名常量定义开始 *********************************/
	/** 维表_标签使用类型:DIM_LABEL_USE_TYPE */
	public static final String TABLE_DIM_LABEL_USE_TYPE = "DIM_LABEL_USE_TYPE";
	/** 维表_审批级别定义表:DIM_APPROVE_LEVEL */
	public static final String TABLE_DIM_APPROVE_LEVEL = "DIM_APPROVE_LEVEL";
	/**维表_审批资源类型：DIM_APPROVE_RESOURCE_TYPE*/
	public static final String TABLE_DIM_APPROVE_RESOURCE_TYPE = "DIM_APPROVE_RESOURCE_TYPE";
	/** 维表_标签数据状态:DIM_LABEL_DATA_STATUS */
	public static final String TABLE_DIM_LABEL_DATA_STATUS = "DIM_LABEL_DATA_STATUS";
	/** 维表_标签审批状态:TABLE_DIM_APPROVE_STATUS */
	public static final String TABLE_DIM_APPROVE_STATUS = "DIM_APPROVE_STATUS";
	/** 维表_客户群创建方式:DIM_CUSTOM_CREATE_TYPE */
	public static final String TABLE_DIM_CUSTOM_CREATE_TYPE = "DIM_CUSTOM_CREATE_TYPE";
	/** 维表_客户群数据状态:DIM_CUSTOM_DATA_STATUS */
	public static final String TABLE_DIM_CUSTOM_DATA_STATUS = "DIM_CUSTOM_DATA_STATUS";
	/** 维表_品牌:DIM_BRAND */
	public static final String TABLE_DIM_BRAND = "DIM_BRAND";
	/** 维表_VIP等级:DIM_VIP_LEVEL */
	public static final String TABLE_DIM_VIP_LEVEL = "DIM_VIP_LEVEL";
	/** 维表_地域(分公司):DIM_CITY */
	public static final String TABLE_DIM_CITY = "DIM_CITY";
	/** 维表_操作日志类型明细表:DIM_OP_LOG_TYPE_DETAIL */
	public static final String TABLE_DIM_OP_LOG_TYPE_DETAIL = "DIM_OP_LOG_TYPE_DETAIL";
	/** 维表_操作日志类型表:DIM_OP_LOG_TYPE */
	public static final String TABLE_DIM_OP_LOG_TYPE = "DIM_OP_LOG_TYPE";
	/** 最新标签数据日期:CI_NEWEST_DATE */
	public static final String TABLE_CI_NEWEST_LABEL_DATE = "CI_NEWEST_LABEL_DATE";
	/**维表——模板场景*/
	public static final String TABLE_DIM_SCENE="DIM_SCENE";
	/** 维表_地市线程池:DIM_CITY_THREAD_CONFIG */
	public static final String TABLE_DIM_CITY_THREAD_CONFIG = "DIM_CITY_THREAD_CONFIG";
	/** 维表_地市日客户群数:DIM_CITY_DIALY_CUSTOMGROUP_NUM */
	public static final String TABLE_DIM_CITY_DIALY_CUSTOMGROUP_NUM = "DIM_CITY_DIALY_CUSTOMGROUP_NUM";
	/**公告类型码表**/
	public static final String TABLE_DIM_ANNOUNCEMENT_TYPE ="DIM_ANNOUNCEMENT_TYPE";
	/**维表_清单生成策略**/
	public static final String TABLE_DIM_LIST_TACTICS ="DIM_LIST_TACTICS";
	/**维表_标签类型**/
	public static final String TABLE_DIM_LABEL_TYPE ="DIM_LABEL_TYPE";
	/**************** 维表缓存表名常量定义结束 *********************************/

	/**
	 * 标签缓存使用
	 */
	/** 有效的标签前缀 */
	public static final String EFFECTIVE_LABEL_PREFIX = "LABEL_";
	/** 所有有效的标签集合 */
	public static final String ALL_EFFECTIVE_LABEL_MAP = "ALL_EFFECTIVE_LABEL_MAP";
	/** 所有有效的地市标签集合 */
	public static final String ALL_CITY_EFFECTIVE_LABEL_MAP = "ALL_CITY_EFFECTIVE_LABEL_MAP";
	/** 根据地市ID存储有效的地市标签集合 */
	public static final String ALL_CITY_EFFECTIVE_LABEL_BYCITY_MAP = "ALL_CITY_EFFECTIVE_LABEL_BYCITY_MAP";
	/** 有效的标签对应的column的前缀 */
	public static final String EFFECTIVE_LABEL_COLUMN_PREFIX = "COLUMN_";
	/** 有效地市标签对应的column的前缀 */
	public static final String CITY_EFFECTIVE_LABEL_COLUMN_PREFIX = "CITY_COLUMN_";
	/** 所有有效的标签对应的column集合 */
	public static final String ALL_EFFECTIVE_LABEL_COLUMNS = "ALL_EFFECTIVE_LABEL_COLUMNS";
	/** 地市标签前缀 */
    public static final String CITY_LABEL = "CITY_";
    /** 按地市存放地市标签前缀 */
    public static final String CITY_LABEL_BY_CITY = "CITY_LABEL_BY_CITY_";
    /** 所有地市标签集合 */
	public static final String CITY_LABEL_MAP = "CITY_LABEL_MAP";
	/** 所有city_id,不包含county_id */
	public static final String CITY_IDS_LIST = "CITY_IDS_LIST";

	/**
	 * 产品缓存使用
	 */
	/** 有效的产品前缀 */
	public static final String EFFECTIVE_PRODUCT_PREFIX = "PRODUCT_";
	/** 所有有效的产品集合 */
	public static final String ALL_EFFECTIVE_PRODUCT_MAP = "ALL_EFFECTIVE_PRODUCT_MAP";

	/**
	 * 产品分类缓存使用
	 */
	/** 产品分类前缀 */
	public static final String PRODUCT_CATEGORY_PREFIX = "PRODUCT_CATEGORY_";
	/** 所有产品分类集合 */
	public static final String ALL_PRODUCT_CATEGORY_MAP = "ALL_PRODUCT_CATEGORY_MAP";

	public static final String USER_MENU_ITEM = "USER_MENU_ITEM";

	/**
	 * 客户群清单缓存
	 */
	public static final String CI_CUSTOM_LIST_INFO_MAP = "CI_CUSTOM_LIST_INFO_MAP";
	
	/**
	 * 营销任务缓存
	 */
	public static final String CI_MARKET_TASK_MAP = "CI_MARKET_TASK_MAP";
	
	/**
	 * 首页营销任务树形数据缓存
	 */
	public static final String CI_MARKET_TASK_TREE = "CI_MARKET_TASK_TREE";

	/**
	 * 模板管理列表页，每次加载记录数
	 */
	public static final int TEMPLATE_PAGESIZE = 5;

	/**
	 * 客户群管理列表页，每次加载记录数
	 */
	public static final int CUSTOMER_PAGESIZE = 10;

	/**
	 * 群乐享管理列表页，每次加载记录数
	 */
	public static final int CUSTOMERLIST_PAGESIZE = 5;
	
	/**
	 * 日志统计列表，每页显示记录数
	 */
	public static final int LOGSTAT_PAGESIZE = 10;

	/**
	 * 日志操作类型父id。即当操作类型为根节点类型，没有父id时，表dim_op_log_type中parent_id字段的值
	 */
	public static final String LOG_OP_TYPE_PARENTID = "-1";

	/**
	 *菜单MenuId以93开头的都是coc项目菜单
	 */
	public static final String MENU_ID_PREFIX = "93";

	/**
	 * MONGODB配置文件路径
	 */
	public static final String MONGODB_CONFIG_PATH = "/config/aibi_ci/mongodb.properties";
	/**
	 * MONGODB 2 的配置文件路径
	 */
	public static final String MONGODB_CONFIG_PATH_2 = "/config/aibi_ci/mongodb2.properties";
	// mcd相关配置 start
	/**
	 * mcd 数据源 jndi在 配置文件中的key
	 */
	public static final String JNDI_MCD = "JNDI_MCD";
	/**
	 * mcd 数据库类型 配置文件中的key
	 */
	public static final String MCD_DBTYPE = "MCD_DBTYPE";

	/**
	 * mcd 数据库类型 配置文件中的key
	 */
	public static final String MCD_TABLESPACE = "MCD_TABLESPACE";
	/**
	 * mcd 数据库名在北京移动建表时候用 配置文件中的key
	 */
	public static final String MCD_SCHEMA = "MCD_SCHEMA";
	/**
	 * mcd清单表模板表 配置文件中的key
	 */
	public static final String MCD_TMP_TABLE = "MCD_TMP_TABLE";
	/**
	 * mcd的清单表的主键 配置文件中的key
	 */
	public static final String MCD_TMP_TABLE_KEYCOLUMN = "MCD_TMP_TABLE_KEYCOLUMN";
	// mcd相关配置 end
	/**
	 * 城市相关配置
	 */
	public static final String ALL_DIM_CITY = "999";

	// 小数位数
	public static final int BIG_DECIMAL_SCALE = 2;

	// 老ci相关配置 start
	/**
	 * 老ci 前台 数据源 jndi在 配置文件中的key
	 */
	public static final String OLD_CI_JNDI = "OLD_CI_JNDI";

	/**
	 * 老ci 后台 数据源 jndi在 配置文件中的key
	 */
	public static final String OLD_CI_BACK_JNDI = "OLD_CI_BACK_JNDI";
	/**
	 * 老ci 前台 数据库类型 配置文件中的key
	 */
	public static final String OLD_CI_DBTYPE = "OLD_CI_DBTYPE";

	/**
	 * 老ci 后台 数据库类型 配置文件中的key
	 */
	public static final String OLD_CI_BACK_DBTYPE = "OLD_CI_BACK_DBTYPE";

	/**
	 * 老ci 后台 数据库表空间 配置文件中的key
	 */
	public static final String OLD_CI_BACK_TABLESPACE = "OLD_CI_TABLESPACE_BACK";
	/**
	 * 老ci 后台数据库schema 配置文件中的key
	 */
	public static final String OLD_CI_BACK_SCHEMA = "OLD_CI_BACK_SCHEMA";
	/**
	 * 老ci清单表模板表 配置文件中的key
	 */
	public static final String OLD_CI_TMP_TABLE = "OLD_CI_TMP_TABLE";
	/**
	 * 老ci的清单表的主键 配置文件中的key
	 */
	public static final String OLD_CI_KEYCOLUMN = "OLD_CI_KEYCOLUMN";
	/**
	 * 老ci的系统信息配置的id
	 */
	public static final String OLD_CI_SYS_ID = "OLD_CI_SYS_ID";
	/**
	 * export sql of coc to ci
	 */
	public static final String OLD_CI_EXPORT_SQL = "OLD_CI_EXPORT_SQL";
	/**
	 * insert sql of ci**temp to ci**
	 */
	public static final String OLD_CI_INSERT_DATA_SQL = "OLD_CI_INSERT_DATA_SQL";
	/**
	 * insert sql of CI_TARGET_CUSTOMERS
	 */
	public static final String OLD_CI_INSERT_GROUPINFO_SQL = "OLD_CI_INSERT_GROUPINFO_SQL";
	// 老ci 相关配置 end

	/**
	 * CM数据源 JNDI在 配置文件中的key
	 */
	public static final String CM_JNDI = "CM_JNDI";

	/**
	 * CM后台 数据源 JNDI在 配置文件中的key
	 */
	public static final String CM_BACK_JNDI = "CM_BACK_JNDI";
	/**
	 * CM前台 数据库类型 配置文件中的key
	 */
	public static final String CM_DBTYPE = "CM_DBTYPE";

	/**
	 * CM后台 数据库类型 配置文件中的key
	 */
	public static final String CM_BACK_DBTYPE = "CM_BACK_DBTYPE";

	/**
	 * CM后台 数据库表空间 配置文件中的key
	 */
	public static final String CM_BACK_TABLESPACE = "CM_TABLESPACE_BACK";
	/**
	 * CM后台数据库schema 配置文件中的key
	 */
	public static final String CM_BACK_SCHEMA = "CM_BACK_SCHEMA";
	/**
	 * CM清单表模板表 配置文件中的key
	 */
	public static final String CM_TMP_TABLE = "CM_TMP_TABLE";
	/**
	 * CM的清单表的主键 配置文件中的key
	 */
	public static final String CM_KEYCOLUMN = "CM_KEYCOLUMN";
	/**
	 * CM的系统信息配置的id
	 */
	public static final String CM_SYS_ID = "CM_SYS_ID";
	/**
	 * export SQL of COC to CM
	 */
	public static final String CM_EXPORT_SQL = "CM_EXPORT_SQL";
	/**
	 * insert SQL of COC**TEMP to CM**
	 */
	public static final String CM_INSERT_DATA_SQL = "CM_INSERT_DATA_SQL";
	/**
	 * insert SQL of CM_GROUP_INFO
	 */
	public static final String CM_INSERT_GROUPINFO_SQL = "CM_INSERT_GROUPINFO_SQL";
	
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
	 * 最热标签、客户群
	 */
	public static final String HOT_LABELS = "HOT_LABELS";
	public static final String HOT_CUSTOMS = "HOT_CUSTOMS";
	/**
	 * 枚举标签及文本标签精确值表
	 */
	public static final String LABEL_EXACT_VALUE_TABLE = "LABEL_EXACT_VALUE_TABLE";
	/**
	 * 枚举标签及文本标签精确值表后缀
	 */
	public static final String LABEL_EXACT_VALUE_TABLE_POSTFIX = "YYMMDDHHMISSTTTTTT";
	/**
	 * 精确值表中列名
	 * 
	 */
	public static final String LABEL_EXACT_VALUE_TABLE_COLUMN = "VALUE_ID";

	/**
	 * 系统配置宽表缓存
	 */
	public static final String CI_SYS_TABLE_MAP = "CI_SYS_TABLE_MAP";
	/** 标签列名称**/
	public static final String TABEL_ALL_LABEL_COLUMN_NAME = "TABEL_ALL_LABEL_COLUMN_NAME";
	
	/**
	 * 属性标签维表缓存
	 */
	public static final String CI_DIM_TABLE_MAP = "CI_DIM_TABLE_MAP";
	
	public static final String CI_MARKET_SCENE_MAP = "CI_MARKET_SCENE_MAP";
	/**
	 * 浙江 新旧版本用户权限表
	 */
	public static final String ZJ_USER = "ZJ_USER";
	/**
     * 新增首页 客户群、标签排行榜
     */
	public static final String HOME_PAGE_LABEL = "HOME_PAGE_LABEL";
	public static final String HOME_PAGE_CUSTOM = "HOME_PAGE_CUSTOM";
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
	
	public static final String BLANK = "";
	/**
	 * %
	 */
	public static final String SIGN_PERCENT = "%";
	/**
	 * =
	 */
	public static final String SIGN_EQUALS = "=";
	/**
	 * =
	 */
	public static final String SINGL_EQUOTES = "'";
	
	
}
