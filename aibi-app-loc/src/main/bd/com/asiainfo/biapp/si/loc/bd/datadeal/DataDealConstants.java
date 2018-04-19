package com.asiainfo.biapp.si.loc.bd.datadeal;


public class DataDealConstants {
    /**
     * 配置文件加载名称
     */
    public static String AIBI_LABEL_PROPERTIES = "LABEL";
    //指标汇总表前缀
    public static String KPI_L_PREF_ = "KPI_L_PREF_";
    //LV_P007_st_a_ocrm_cu_indiv_base_info_yyyymmdd_z
    //纵表表名前缀
    public static String LV = "LV_";

    /**
     * 标签周期类型，2=月周期
     */
    public static final int LABEL_CYCLE_TYPE_M = 2;
    /**
     * 标签周期类型，1=日周期
     */
    public static final int LABEL_CYCLE_TYPE_D = 1;
    public static final String PRODUCT_NO = "product_no";
    public static final String ORG_LEVEL_ = "ORG_LEVEL_";
    public static String LOC_LABEL_INFO = "LOC_LABEL_INFO";
    public static String DIM_LABEL_COUNT_RULES = "DIM_LABEL_COUNT_RULES";
    public static String LOC_MDA_SYS_TABLE = "LOC_MDA_SYS_TABLE";
    public static String LOC_MDA_SYS_TABLE_COLUMN = "LOC_MDA_SYS_TABLE_COLUMN";
    public static String LOC_NEWEST_LABEL_DATE = "LOC_NEWEST_LABEL_DATE";
    public static String DIM_LABEL_STATUS = "DIM_LABEL_STATUS";
    public static String LOC_SOURCE_INFO = "LOC_SOURCE_INFO";
    public static String LOC_SOURCE_TABLE_INFO = "LOC_SOURCE_TABLE_INFO";
    public static String DIM_TARGET_TABLE_STATUS = "DIM_TARGET_TABLE_STATUS";
    public static String LOC_PRE_CONFIG_INFO = "LOC_PRE_CONFIG_INFO";
    public static String LOC_LABEL_VERTICAL_COLUMN_REL = "LOC_LABEL_VERTICAL_COLUMN_REL";
    public static Integer BUCKET_NUMBER = 200;
    public static Integer MAX_STAT_HISTORY_DAY_INDEX = 31;
    public static Integer MAX_STAT_HISTORY_MONTH_INDEX = 12;
    public static Integer MAX_STAT_HISTORY_DAY_DW = 31;
    public static Integer MAX_STAT_HISTORY_MONTH_DW = 12;
    public static String STORED = "orc";
}