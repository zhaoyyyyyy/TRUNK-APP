/*
 * @(#)ServiceConstants.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.common.constant;

/**
 * Title : LocLabelPushCycleDaoImpl
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

	/** sysinfo */
	public interface SysInfo{
	    /**
	     * 是否显示在页面上：1-页面显示; 0-不再页面显示
	     */
	    /** 是否显示在页面上：1：页面显示 */
	    public static final Integer SHOW_IN_PAGE_YES = 1;
	    /** 是否显示在页面上：0:不再页面显示 */
	    public static final Integer SHOW_IN_PAGE_NO = 1;
	    
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
	     * 客户群生成周期:UPDATE_CYCLE：1,一次性;2,月周期;3,日周期;4,无周期
	     */
	    /** 客户群生成周期:1,一次性; */
	    public static final int UPDATE_CYCLE_O = 1;
	    /** 客户群生成周期:2,月周期; */
	    public static final int UPDATE_CYCLE_M = 2;
	    /** 客户群生成周期:3,日周期 */
	    public static final int UPDATE_CYCLE_D = 3;
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
         * 数据状态ID: 未生效,已生效,已失效，冷冻期，已下线
         */
        /** 数据状态: 1、未生效 */
        public static final int DATA_STATUS_ID_NOT_EFFECT = 1;
        /** 数据状态: 2、已生效 */
        public static final int DATA_STATUS_ID_EFFECT = 2;
        /** 数据状态: 3、已失效 */
        public static final int DATA_STATUS_ID_FAILURE = 3;
        /** 数据状态: 4、冷冻期 */
        public static final int DATA_STATUS_ID_FREEZED = 4;
        /** 数据状态: 5、已下线 */
        public static final int DATA_STATUS_ID_UNDER = 5;
        /** 数据状态: 6、已删除 */
        public static final int DATA_STATUS_ID_DELETED = 6;
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
	
}
