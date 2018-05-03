package com.asiainfo.biapp.si.loc.bd.datadeal.component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.asiainfo.biapp.si.loc.base.exception.SqlRunException;
import com.asiainfo.biapp.si.loc.bd.common.service.impl.BackServiceImpl;
import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.bd.datadeal.util.BackJdbcManager;
import com.asiainfo.biapp.si.loc.bd.datadeal.util.JdbcManager;
import com.asiainfo.biapp.si.loc.bd.datadeal.util.TimeUtil;
import com.asiainfo.biapp.si.loc.bd.datadeal.vo.BackParamVo;

/**
 * Created by pwj on 2018/3/16.
 */
@Configuration
public class LabelDealComponent {
    private String threadNumber = "主线程：";
    private String data_date = "";
    private Integer data_cycle = 0;
    Long startTime=0L;
    private static String LOC_SOURCE_TABLE_INFO = "LOC_SOURCE_TABLE_INFO";
    private static String DIM_TARGET_TABLE_STATUS = "DIM_TARGET_TABLE_STATUS";
    private static String LOC_PRE_CONFIG_INFO = "LOC_PRE_CONFIG_INFO";
    private static String LOC_LABEL_INFO="LOC_LABEL_INFO";
    private static String DIM_LABEL_STATUS="DIM_LABEL_STATUS";
    private static String LOC_NEWEST_LABEL_DATE="LOC_NEWEST_LABEL_DATE";

    public void setData_date(String data_date) {
        this.data_date = data_date;
    }

    public void setThreadNumber(String threadNumber) {
        this.threadNumber = threadNumber;
    }

    public void setDate_cycle(Integer data_cycle) {
        this.data_cycle = data_cycle;
    }

    public Boolean isnumber(String str) {
        LogUtil.info(threadNumber+"判断传入是日期是否满足要求");
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            LogUtil.error(threadNumber+"请传入日期为纯数字类型:月周期：YYYYMM；日周期：YYYYMMDD");
            return false;
        }
        return true;
    }

    public Boolean updateDimTargetTableStatusBySourceTableId(Integer num, List<String> sourceTableIdList) {
        startTime=System.currentTimeMillis();
        Boolean isOk = true;
        LogUtil.info(threadNumber + "更新指标状态表");
        String sql = "update " + DIM_TARGET_TABLE_STATUS + " set DATA_STATUS = " + num + ",IS_DOING=0,exception_desc='0' where data_date='" + data_date + "'";
        sql += " and  (1 = 0";
        for (int i = 0; i < sourceTableIdList.size(); i++) {
            sql += " or source_table_id='" + sourceTableIdList.get(i) + "' ";
        }
        sql += ")";
        Connection connection = null;
        ResultSet res = null;
        PreparedStatement pstm = null;
        connection = new JdbcManager().getConnection();
        try {
            LogUtil.info(threadNumber + "更新" + DIM_TARGET_TABLE_STATUS + "sql:" + sql);
            pstm = connection.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (SQLException e) {
            isOk = false;
            LogUtil.error(threadNumber + "更新 " + DIM_TARGET_TABLE_STATUS + "失败！" + e);
        } finally {
            LogUtil.debug(threadNumber+new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
            JdbcManager.closeAll(connection, pstm, res);
        }
        return isOk;
    }

    public String getSchema() {
        String schema = null;
        try {
            BackServiceImpl iBackSqlService = new BackServiceImpl();
            schema = iBackSqlService.getCurBackDbSchema();
        } catch (SqlRunException e) {
            LogUtil.error(threadNumber+"获取后台schema失败" + e);
        }
        return schema;

    }

    public boolean initDate(String data_date) {
        startTime=System.currentTimeMillis();
        String addDateSQL = "insert into " + DIM_TARGET_TABLE_STATUS + "(source_table_id,table_schema,source_table_name,data_date,data_status,is_doing) " +
                "select t1.source_table_id,t2.table_schema,t2.source_table_name,t2.data_date,1,0 from loc_source_table_info t1 " +
                "left join " + DIM_TARGET_TABLE_STATUS + " t2  on t1.table_schema=t2.table_schema and t1.source_table_name=t2.source_table_name " +
                "where t2.source_table_id='0' and t2.data_status=1 and t2.data_date='" + data_date + "'" +
                " and t1.source_table_id  not in (select source_table_id from DIM_TARGET_TABLE_STATUS where data_date='" + data_date + "' and source_table_id<>'0')";
        Connection connection = null;
        ResultSet res = null;
        PreparedStatement pstm = null;
        connection = new JdbcManager().getConnection();
        try {
            LogUtil.info(threadNumber+"初始化数据" + DIM_TARGET_TABLE_STATUS + "sql：" + addDateSQL);
            pstm = connection.prepareStatement(addDateSQL);
            pstm.execute();
        } catch (SQLException e) {
            LogUtil.error(threadNumber+"初始化数据 " + DIM_TARGET_TABLE_STATUS + "失败！" + e);
            return false;
        } finally {
            JdbcManager.closeAll(connection, pstm, res);
            LogUtil.debug(threadNumber+new StringBuffer(addDateSQL).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        String updateSql = "update " + DIM_TARGET_TABLE_STATUS + " set data_status=0 where source_table_id='0' and data_date='" + data_date + "'";
        connection = new JdbcManager().getConnection();
         startTime=System.currentTimeMillis();
        try {
            LogUtil.info(threadNumber+"还原状态" + DIM_TARGET_TABLE_STATUS + "sql：" + updateSql);
            pstm = connection.prepareStatement(updateSql);
            int i = pstm.executeUpdate();
        } catch (SQLException e) {
            LogUtil.error(threadNumber+"还原状态 " + DIM_TARGET_TABLE_STATUS + "失败！" + e);
            return false;
        } finally {
            JdbcManager.closeAll(connection, pstm, res);
            LogUtil.debug(threadNumber+new StringBuffer(updateSql).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        return true;
    }

    public List<String> configIsOk(List<String> configId) {
        startTime=System.currentTimeMillis();
        List<String> configIds = new ArrayList<String>();
        String findConfig_id = " select config_id from LOC_PRE_CONFIG_INFO where config_status=1 and config_id in(";
        for (int i = 0; i < configId.size(); i++) {
            findConfig_id += "'" + configId.get(i) + "',";
        }
        findConfig_id = findConfig_id.substring(0, findConfig_id.length() - 1);
        findConfig_id += ")";
        Connection connection = null;
        String config_id;
        ResultSet res = null;
        PreparedStatement pstm = null;
        try {
            LogUtil.info(threadNumber + "查找启用的专区：" + findConfig_id);
            connection = new JdbcManager().getConnection();
            pstm = connection.prepareStatement(findConfig_id);
            res = pstm.executeQuery();
            while (res.next()) {
                config_id = res.getString("config_id");
                configIds.add(config_id);
            }
        } catch (SQLException e) {
            LogUtil.error(threadNumber + "获取config_id失败：" + e);
            return null;
        } finally {
            JdbcManager.closeAll(connection, pstm, res);
            LogUtil.debug(threadNumber+new StringBuffer(findConfig_id).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        return configIds;
    }

    public boolean updateDimTargetTableStatusNotExistsTable(List<String> notExistsTableList) {
        startTime=System.currentTimeMillis();
        String sql = "update " + DIM_TARGET_TABLE_STATUS + " set DATA_STATUS =0,IS_DOING=0,exception_desc = 'table not exists' where data_date='" + data_date + "'and (1 = 0 ";
        for (int i = 0; i < notExistsTableList.size(); i++) {
            sql += " or source_table_id='" + notExistsTableList.get(i) + "' ";
        }
        sql += ")";
        Connection connection = null;
        ResultSet res = null;
        PreparedStatement pstm = null;
        connection = new JdbcManager().getConnection();
        try {
            LogUtil.info(threadNumber + "更新指标状态表的SQL为：" + sql);
            pstm = connection.prepareStatement(sql);
            int i = pstm.executeUpdate();
        } catch (SQLException e) {
            LogUtil.error(threadNumber + "更新 " + DIM_TARGET_TABLE_STATUS + "失败！" + e);
            return false;
        } finally {
            JdbcManager.closeAll(connection, pstm, res);
            LogUtil.debug(threadNumber+new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        return true;
    }

    public boolean updateLocLabelInfo(List<String> labelIdList) {
         startTime=System.currentTimeMillis();
        String updateSql = "update " + LOC_LABEL_INFO + " set data_date = " + data_date + " where " +
                "(data_date<" + data_date + " or data_date is null )" +
                " and (1 = 0 ";
        Connection connection = null;
        ResultSet res = null;
        PreparedStatement pstm = null;
        for (int i = 0; i < labelIdList.size(); i++) {
            updateSql += " or label_id='" + labelIdList.get(i) + "' ";
        }
        updateSql += ")";
        connection = new JdbcManager().getConnection();
        try {
            LogUtil.info(threadNumber + "更新指标信息表" + updateSql);
            pstm = connection.prepareStatement(updateSql);
            boolean execute = pstm.execute();
        } catch (SQLException e) {
            LogUtil.error(threadNumber + "更新" + LOC_LABEL_INFO + "失败" + e);
            return false;
        } finally {
            JdbcManager.closeAll(connection, pstm, res);
            LogUtil.debug(threadNumber+new StringBuffer(updateSql).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        return true;
    }

    public boolean insertIntoDimLabelStatus(List<String> labelIdList) {
         startTime=System.currentTimeMillis();
        List<String> labelIdLists = labelIdList;
        LogUtil.info(threadNumber + "插入指标状态表");
        String sql = "select label_id from " + DIM_LABEL_STATUS;
        LogUtil.info(threadNumber + "获取" + DIM_LABEL_STATUS + "中不存在的标签"+sql);
        Connection connection = null;
        ResultSet res = null;
        PreparedStatement pstm = null;
        connection = new JdbcManager().getConnection();
        try {
            pstm = connection.prepareStatement(sql);
            res = pstm.executeQuery();
            while (res.next()) {
                String label_id = res.getString(1);
                if (labelIdList.contains(label_id)) {
                    labelIdLists.remove(label_id);
                }
            }
        } catch (SQLException e) {
            LogUtil.error(threadNumber + "获取" + DIM_LABEL_STATUS + "中已经存在的标签失败" + e);
            return false;
        } finally {
            JdbcManager.closeAll(connection, pstm, res);
            LogUtil.debug(threadNumber+new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        startTime=System.currentTimeMillis();
        connection = new JdbcManager().getConnection();
        String insertSql = "insert into " + DIM_LABEL_STATUS + " values(?," + data_date + "," + 0 + ",'" + TimeUtil.getCurrentDayYYYYMMDD() + "',0)";
        try {
            connection.setAutoCommit(false);
            LogUtil.info(threadNumber + DIM_LABEL_STATUS + "中不存在的标签为：" + labelIdLists);
            pstm = connection.prepareStatement(insertSql);
            for (int i = 0; i < labelIdLists.size(); i++) {
                pstm.setString(1, labelIdLists.get(i));
                pstm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            LogUtil.error(threadNumber + "插入" + DIM_LABEL_STATUS + "异常"+e);
            return false;
        } finally {
            JdbcManager.closeAll(connection, pstm, res);
            LogUtil.debug(threadNumber+new StringBuffer(insertSql).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        return true;
    }

    public boolean updateDimLabelStatus(List<String> labelIdList) {
         startTime=System.currentTimeMillis();
        LogUtil.debug(threadNumber + "更新" + DIM_LABEL_STATUS);
        Connection connection = null;
        ResultSet res = null;
        PreparedStatement pstm = null;
        String updateSql = "update " + DIM_LABEL_STATUS + " set data_date='" + data_date + "',data_status=1 where " +
                "(data_date<'" + data_date + "' or data_date is null )" +
                " and ( 1=2";
        connection = new JdbcManager().getConnection();
        try {
            for (int i = 0; i < labelIdList.size(); i++) {
                updateSql += " or label_id='" + labelIdList.get(i) + "'";
            }
            updateSql += ")";
            LogUtil.info(threadNumber + "更新" + DIM_LABEL_STATUS + "的sql为：" + updateSql);
            pstm = connection.prepareStatement(updateSql);
            int i = pstm.executeUpdate();
        } catch (SQLException e) {
            LogUtil.error(threadNumber + "批量更新" + DIM_LABEL_STATUS + "异常：" + e);
            return false;
        } finally {
            JdbcManager.closeAll(connection, pstm, res);
            LogUtil.debug(threadNumber+new StringBuffer(updateSql).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        return true;
    }

    public void dropHistoryTables(String data_date, Integer data_cycle, String config_id) {
        Integer MAX_STAT_HISTORY_INDEX = 0;
        Integer MAX_STAT_HISTORY_DW = 0;
        String indexDate = "";
        String dwDate = "";
        if (data_cycle == 1) {
            MAX_STAT_HISTORY_INDEX = ServiceConstants.MAX_STAT_HISTORY_DAY_INDEX;
            MAX_STAT_HISTORY_DW = ServiceConstants.MAX_STAT_HISTORY_DAY_DW;
            indexDate = TimeUtil.getFrontDay(MAX_STAT_HISTORY_INDEX, data_date);
            dwDate = TimeUtil.getFrontDay(MAX_STAT_HISTORY_DW, data_date);
        } else {
            MAX_STAT_HISTORY_INDEX = ServiceConstants.MAX_STAT_HISTORY_MONTH_INDEX;
            MAX_STAT_HISTORY_DW = ServiceConstants.MAX_STAT_HISTORY_MONTH_DW;
            indexDate = TimeUtil.getFrontMonth(MAX_STAT_HISTORY_INDEX, data_date);
            dwDate = TimeUtil.getFrontMonth(MAX_STAT_HISTORY_DW, data_date);
        }
        String indexTable = "kpi_l_pref_" + config_id + "_" + indexDate;
        String dwTable = "dw_l_pref_" + config_id + "_" + dwDate;
        LogUtil.info(threadNumber+"清除历史数据：" + indexTable + "," + dwTable);
        if (new BackJdbcManager().dropTable(indexTable)) {
            LogUtil.debug(threadNumber+"清除历史数据：" + indexTable + "成功");
        } else {
            LogUtil.error(threadNumber+"清除历史数据：" + indexTable + "失败，请手动删除表：" + indexTable);
        }
        if (new BackJdbcManager().dropTable(dwTable)) {
            LogUtil.debug(threadNumber+"清除历史数据：" + dwTable + "成功");
        } else {
            LogUtil.error(threadNumber+"清除历史数据：" + dwTable + "失败，请手动删除表：" + dwTable);
        }
    }

    /**
     * 1、生成---->t2
     * 2、合表---->t1
     * 3、t------>t3
     * 4、t1------>t
     *
     * @param num                   1指标汇总表 合表，2标签汇总表 create table as，合表 ,3纵表 不合表
     * @param tableName
     * @param sql
     * @param columnNameAndTypeList
     * @return
     */
    public boolean createTableIfExists(Integer num, String tableName, String sql, List<String> columnNameAndTypeList) {
        boolean isOk = true;
        String createSql = "";
        BackJdbcManager backJdbcManager = new BackJdbcManager();
        backJdbcManager.setThreadNumber(threadNumber);
        LogUtil.info(threadNumber + "判断目标表是否存在" + tableName);
        boolean b = backJdbcManager.tableExists(tableName,1);
        String tableName_01 = tableName + "_01";
        String tableName_02 = tableName + "_02";
        String tableName_03 = tableName + "_03";
        List<String> columnList = new ArrayList<String>();
        if (num == 1 || num == 3) {
            if (b) {
                LogUtil.info(threadNumber + tableName+"已经存在，创建临时表" + tableName_02);
                if (backJdbcManager.createTableByColumns(tableName_02, columnNameAndTypeList, sql)) {
                    LogUtil.debug(threadNumber + "创建临时表：" + tableName_02 + " 成功");
                    if (num == 3) {
                        if (backJdbcManager.renameTableName(tableName, tableName_01)) {
                            if (backJdbcManager.renameTableName(tableName_02, tableName)) {
                                backJdbcManager.dropTable(tableName_01);
                            } else {
                                backJdbcManager.renameTableName(tableName_01, tableName);
                                backJdbcManager.dropTable(tableName_02);
                                isOk = false;
                            }
                        } else {
                            backJdbcManager.dropTable(tableName_02);
                            isOk = false;
                        }
                        return isOk;
                    }
                    //已经存在的列
                    List<String> columnNameList = backJdbcManager.getColumnNameByName(tableName);
                    Map<String, String> oldColumnNameAndTypeByName = backJdbcManager.getColumnNameAndTypeByName(tableName);
                    if (columnNameList.size() > 0) {
                        LogUtil.debug(threadNumber + "根据目标表表名获取列：" + tableName+"\n"+ "已存在的目标表列：" + columnNameList);
                    }
                    List<String> columnNameList_02 = backJdbcManager.getColumnNameByName(tableName_02);
                    Map<String, String> newColumnNameAndTypeByName = backJdbcManager.getColumnNameAndTypeByName(tableName_02);
                    if (columnNameList_02.size() > 0) {
                        LogUtil.debug(threadNumber + "根据目标表表名获取列：" + tableName_02+"\n"+"已存在的目标表列：" + columnNameList_02);
                    }
                    //新表、源表关联字段
                    String joinName = ServiceConstants.LABEL_PRODUCT_NO;
                    LogUtil.info(threadNumber + "关联字段为：" + joinName);
                    List<String> allColumnName = new ArrayList<String>();
                    //sql t1,tableNmae t2
                    for (int i = 0; i < columnNameList.size(); i++) {
                        if (columnNameList_02.contains(columnNameList.get(i))) {
                            allColumnName.add("t2." + columnNameList.get(i));
                            columnList.add(columnNameList.get(i) + " " + newColumnNameAndTypeByName.get(columnNameList.get(i).toUpperCase()));
                        } else {
                            allColumnName.add("t1." + columnNameList.get(i));
                            columnList.add(columnNameList.get(i) + " " + oldColumnNameAndTypeByName.get(columnNameList.get(i).toUpperCase()));
                        }
                    }
                    for (int i = 0; i < columnNameList_02.size(); i++) {
                        if (!columnNameList.contains(columnNameList_02.get(i))) {
                            allColumnName.add("t2." + columnNameList_02.get(i));
                            columnList.add(columnNameList_02.get(i) + " " + newColumnNameAndTypeByName.get(columnNameList_02.get(i).toUpperCase()));
                        }
                    }
                    String createTableSql = "select ";
                    for (int i = 0; i < allColumnName.size(); i++) {
                        createTableSql += allColumnName.get(i) + ",";
                    }
                    createTableSql = createTableSql.substring(0, createTableSql.length() - 1);
                    createTableSql += " from " + tableName + " t1 left join " + tableName_02 + " t2 on t1." + joinName + "=t2." + joinName;
                    // final boolean table1 = backJdbcManager.createTable(tableName_01, createTableSql);
                    boolean table1 = backJdbcManager.createTableByColumns(tableName_01, columnList, createTableSql);
                    if (table1) {
                        Boolean aBoolean = backJdbcManager.renameTableName(tableName, tableName_03);
                        if (aBoolean) {
                            backJdbcManager.renameTableName(tableName_01, tableName);
                        }
                        LogUtil.info(threadNumber + "删除临时表：" + tableName_03 + "," + tableName_02);
                        backJdbcManager.dropTable(tableName_02);
                        backJdbcManager.dropTable(tableName_03);
                    } else {
                        LogUtil.error(threadNumber + "创建目标表：" + tableName + " 失败");
                        LogUtil.info(threadNumber + "将临时表" + tableName_03 + "重命名为" + tableName);
                        final Boolean boo = backJdbcManager.renameTableName(tableName_03, tableName);
                        if (boo) {
                            LogUtil.info(threadNumber + "将临时表" + tableName_03 + "重命名为" + tableName + "成功");
                        }
                        isOk = false;
                    }
                } else {
                    LogUtil.error(threadNumber + "创建临时表：" + tableName_02 + " 失败");
                    isOk = false;
                }
            } else {
                isOk = backJdbcManager.createTableByColumns(tableName, columnNameAndTypeList, sql);
            }
        } else if (num == 2) {
            if (!b) {
                createSql = "create table " + tableName + " as " + sql;
                if (backJdbcManager.createTable(createSql)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                createSql = "create table " + tableName_02 + " as " + sql;
                if (backJdbcManager.createTable(createSql)) {
                    //合表：
                    //已经存在的列
                    List<String> columnNameList = backJdbcManager.getColumnNameByName(tableName);
                    Map<String, String> oldColumnNameAndTypeByName = backJdbcManager.getColumnNameAndTypeByName(tableName);
                    if (columnNameList.size() > 0) {
                        LogUtil.info(threadNumber + "根据目标表表名获取列：" + tableName);
                        LogUtil.info(threadNumber + "已存在的目标表列：" + columnNameList);
                    }
                    List<String> columnNameList_02 = backJdbcManager.getColumnNameByName(tableName_02);
                    Map<String, String> newColumnNameAndTypeByName = backJdbcManager.getColumnNameAndTypeByName(tableName_02);
                    if (columnNameList_02.size() > 0) {
                        LogUtil.info(threadNumber + "根据目标表表名获取列：" + tableName_02);
                        LogUtil.info(threadNumber + "已存在的目标表列：" + columnNameList_02);
                    }
                    //新表、源表关联字段
                    String joinName = ServiceConstants.LABEL_PRODUCT_NO;
                    LogUtil.info(threadNumber + "关联字段为：" + joinName);
                    List<String> allColumnName = new ArrayList<String>();
                    //sql t1,tableNmae t2
                    for (int i = 0; i < columnNameList.size(); i++) {
                        if (columnNameList_02.contains(columnNameList.get(i))) {
                            allColumnName.add("t2." + columnNameList.get(i));
                            columnList.add(columnNameList.get(i) + " " + newColumnNameAndTypeByName.get(columnNameList.get(i).toUpperCase()));
                        } else {
                            allColumnName.add("t1." + columnNameList.get(i));
                            columnList.add(columnNameList.get(i) + " " + oldColumnNameAndTypeByName.get(columnNameList.get(i).toUpperCase()));
                        }
                    }
                    for (int i = 0; i < columnNameList_02.size(); i++) {
                        if (!columnNameList.contains(columnNameList_02.get(i))) {
                            allColumnName.add("t2." + columnNameList_02.get(i));
                            columnList.add(columnNameList_02.get(i) + " " + newColumnNameAndTypeByName.get(columnNameList_02.get(i).toUpperCase()));
                        }
                    }
                    String createTableSql = "select ";
                    for (int i = 0; i < allColumnName.size(); i++) {
                        createTableSql += allColumnName.get(i) + ",";
                    }
                    createTableSql = createTableSql.substring(0, createTableSql.length() - 1);
                    createTableSql += " from " + tableName + " t1 left join " + tableName_02 + " t2 on t1." + joinName + "=t2." + joinName;
                    // final boolean table1 = backJdbcManager.createTable(tableName_01, createTableSql);
                    boolean table1 = backJdbcManager.createTableByColumns(tableName_01, columnList, createTableSql);
                    if (table1) {
                        Boolean aBoolean = backJdbcManager.renameTableName(tableName, tableName_03);
                        if (aBoolean) {
                            backJdbcManager.renameTableName(tableName_01, tableName);
                        }
                        LogUtil.info(threadNumber + "删除临时表：" + tableName_03 + "," + tableName_02);
                        if (!backJdbcManager.dropTable(tableName_02)) {
                            LogUtil.info(threadNumber + "删除表：" + tableName_02 + "失败,请手动删除");
                        }
                        if (!backJdbcManager.dropTable(tableName_03)) {
                            LogUtil.info(threadNumber + "删除表：" + tableName_03 + "失败,请手动删除");
                        }
                    } else {
                        LogUtil.info(threadNumber + "将临时表" + tableName_03 + "重命名为" + tableName);
                        final Boolean boo = backJdbcManager.renameTableName(tableName_03, tableName);
                        if (boo) {
                            LogUtil.info(threadNumber + "将临时表" + tableName_03 + "重命名为" + tableName + "成功");
                        }
                        isOk = false;
                    }
                } else {
                    return false;
                }
            }
        }
        return isOk;
    }

    public List<String> filterLabelId(String data_date, String config_id) {
        List<String> filterLabel = new ArrayList<String>();
        Map<String, String> map = getLabelId(config_id);
        List<String> sourceIdList = getSourceId(data_date, config_id);
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        String label_id = "";
        String depend = "";
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            label_id = next.getKey();
            depend = next.getValue();
            for (int i = 0; i < sourceIdList.size(); i++) {
                if (depend.contains(sourceIdList.get(i))) {
                    filterLabel.add(label_id);
                    break;
                }
            }
        }
        return filterLabel;
    }

    public Map<String, String> getLabelId(String config_id) {
         startTime=System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        String getLabelIdSQL = "select t1.label_id,depend_index from loc_label_info t1 " +
                "left join  LOC_MDA_SYS_TABLE_COLUMN t2 on t1.label_id=t2.label_id " +
                "left join DIM_LABEL_COUNT_RULES t3 on t2.COUNT_RULES_CODE=t3.COUNT_RULES_CODE " +
                "where config_id='" + config_id + "' and DEPEND_INDEX is not null";
        Connection conn = new JdbcManager().getConnection();
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            LogUtil.info(threadNumber + "初步获取标签信息：" + getLabelIdSQL);
            preparedStatement = conn.prepareStatement(getLabelIdSQL);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("label_id"), rs.getString("depend_index").toUpperCase());
            }
        } catch (SQLException e) {
            LogUtil.error(threadNumber + "初步获取标签信息失败" + e);
        } finally {
            JdbcManager.closeAll(conn, preparedStatement, rs);
            LogUtil.debug(threadNumber+new StringBuffer(getLabelIdSQL).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        return map;
    }

    public List<String> getSourceId(String data_date, String config_id) {
         startTime=System.currentTimeMillis();
        List<String> sourceIdList = new ArrayList<String>();
        String getSourceIdSQL = "select source_id from loc_source_info t1 " +
                "left join LOC_SOURCE_TABLE_INFO t2 on t1.source_table_id=t2.source_table_id " +
                "left join DIM_TARGET_TABLE_STATUS t3 on t3.data_date='" + data_date + "' and t3.source_table_id=t2.source_table_id " +
                "where t3.DATA_STATUS=1 and t2.config_id='" + config_id + "'";
        Connection conn = new JdbcManager().getConnection();
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            LogUtil.info(threadNumber + "初步获取指标信息：" + getSourceIdSQL);
            preparedStatement = conn.prepareStatement(getSourceIdSQL);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                sourceIdList.add(rs.getString("source_id").toUpperCase());
            }
        } catch (SQLException e) {
            LogUtil.error(threadNumber + "初步获取指标信息失败" + e);
        } finally {
            JdbcManager.closeAll(conn, preparedStatement, rs);
            LogUtil.debug(threadNumber+new StringBuffer(getSourceIdSQL).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        return sourceIdList;
    }

    public boolean updateLocNewestLabelDate(String config_id) {
         startTime=System.currentTimeMillis();
        Connection connection = null;
        ResultSet res = null;
        PreparedStatement pstm = null;
        String insertSql = "";
        String getConfig_id = "select config_id from " + LOC_NEWEST_LABEL_DATE + " where config_id='" + config_id + "'";
        boolean boo = false;
        connection = new JdbcManager().getConnection();
        try {
            LogUtil.info(threadNumber + "判断" + LOC_NEWEST_LABEL_DATE + "中是否存在专区：" + config_id+" SQL:"+getConfig_id);
            pstm = connection.prepareStatement(getConfig_id);
            res = pstm.executeQuery();
            while (res.next()) {
                boo = true;
            }
        } catch (SQLException e) {
            LogUtil.error(threadNumber+"更新" + LOC_NEWEST_LABEL_DATE + "失败：" + e);
            return false;
        } finally {
            JdbcManager.closeAll(connection, pstm, res);
            LogUtil.debug(threadNumber+new StringBuffer(getConfig_id).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        startTime=System.currentTimeMillis();
        String updateSql = "";
        if (boo) {
            LogUtil.info(threadNumber + "更新最新数据时间表：" + LOC_NEWEST_LABEL_DATE);
            if (data_date.length() == 8) {
                updateSql = " UPDATE " + LOC_NEWEST_LABEL_DATE + " set day_newest_date = '" + data_date + "', day_newest_status = 0   WHERE '" + data_date + "' >=" + "day_newest_date and config_id='" + config_id + "'";
            } else if (data_date.length() == 6) {
                updateSql = " UPDATE " + LOC_NEWEST_LABEL_DATE + " set month_newest_date = '" + data_date + "', month_newest_status = 0   WHERE '" + data_date + "' >=" + "month_newest_date and config_id='" + config_id + "'";
            }
        } else {
            LogUtil.info(threadNumber + "插入最新数据时间表：" + LOC_NEWEST_LABEL_DATE);
            if (data_date.length() == 8) {
                updateSql = "insert into " + LOC_NEWEST_LABEL_DATE + "(day_newest_date,day_newest_status,config_id) values('" + data_date + "'," + 0 + ",'" + config_id + "')";
            } else if (data_date.length() == 6) {
                updateSql = "insert into " + LOC_NEWEST_LABEL_DATE + "(month_newest_date,month_newest_status,config_id) values('" + data_date + "'," + 0 + ",'" + config_id + "')";
            }
        }
        connection = new JdbcManager().getConnection();
        try {
            LogUtil.info(threadNumber + "更新最新数据时间表：" + LOC_NEWEST_LABEL_DATE + "的SQL为：" + updateSql);
            pstm = connection.prepareStatement(updateSql);
            boolean execute = pstm.execute();
        } catch (SQLException e) {
            LogUtil.error(threadNumber+"更新" + LOC_NEWEST_LABEL_DATE + "失败：" + e);
            return false;
        } finally {
            JdbcManager.closeAll(connection, pstm, res);
            LogUtil.debug(threadNumber+new StringBuffer(updateSql).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        return true;
    }

    public List<String> getConfigId01(BackParamVo backParamVo) {
         startTime=System.currentTimeMillis();
        Connection connection = null;
        ResultSet res = null;
        PreparedStatement pstm = null;
        List<String> configIdList = new ArrayList<String>();
        List<String> sourceTableList = new ArrayList<String>();
        List<String> sourceTableIdList = new ArrayList<String>();
        List<String> getConfigIdList = new ArrayList<String>();
        if (StringUtils.isNotEmpty(backParamVo.getConfigId())) {
            String[] split = backParamVo.getConfigId().split(",");
            for (int i = 0; i < split.length; i++) {
                configIdList.add(split[i]);
            }
        }
        if (StringUtils.isNotEmpty(backParamVo.getTableName())) {
            String tableName = backParamVo.getTableName();
            String[] split = tableName.split(",");
            for (int i = 0; i < split.length; i++) {
                String[] split1 = split[i].split("//.");
                if (split1.length > 1) {
                    sourceTableList.add(split1[0] + " " + split1[1]);
                } else {
                    sourceTableList.add(getSchema() + " " + split1[0]);
                }
            }
        }
        String getConfigIdSql = "select t1.config_id,t1.source_table_id from " + LOC_SOURCE_TABLE_INFO +
                " t1 join " + LOC_PRE_CONFIG_INFO + " t2 on t1.config_id=t2.config_id " +
                " where t2.config_status=1 and t1.READ_CYCLE=" + data_cycle +
                " and ( 1>2 ";
        if (configIdList.size() > 0) {
            for (int i = 0; i < configIdList.size(); i++) {
                getConfigIdSql += " or t1.config_id='" + configIdList.get(i) + "'";
            }
        } else {
            getConfigIdSql += " or 1=1 ";
        }
        getConfigIdSql += ") and ( 1>2 ";
        if (sourceTableList.size() > 0) {
            for (int i = 0; i < sourceTableList.size(); i++) {
                String[] split = sourceTableList.get(i).split(" ");
                getConfigIdSql += " or t1.config_id='" + configIdList.get(i) + "'";
                getConfigIdSql += " or ( table_schema='" + split[0] + "' and source_table_name='" + split[1] + "')";
            }
        } else {
            getConfigIdSql += " or 1=1 ";
        }
        getConfigIdSql += ")";
        connection = new JdbcManager().getConnection();
        String source_table_id;
        String config_id;
        try {
            LogUtil.info(threadNumber+"获取config_id，source_table_id:"+getConfigIdSql);
            pstm = connection.prepareStatement(getConfigIdSql);
            res = pstm.executeQuery();
            while (res.next()) {
                config_id = res.getString("config_id");
                if (!getConfigIdList.contains(config_id)) {
                    getConfigIdList.add(config_id);
                }
                source_table_id = res.getString("source_table_id");
                sourceTableIdList.add(source_table_id);
            }
        } catch (SQLException e) {
            LogUtil.error(threadNumber+"获取config_id、source_table_id 失败：" + e);
            return null;
        } finally {
            JdbcManager.closeAll(connection, pstm, res);
            LogUtil.debug(threadNumber+new StringBuffer(getConfigIdSql).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        LogUtil.info(threadNumber+"本次源表所属于的专区为：" + configIdList);
        LogUtil.info(threadNumber+"本次源表所属于的source_table_id为：" + sourceTableIdList);
        //TODO 去更新数据源表状态表
        if (!(getConfigIdList.size() == 0 && sourceTableList.size() == 0)) {
            Boolean isOk = updateDimTargetTableStatusBySourceTableId(1, sourceTableIdList);
            if (!isOk) {
                getConfigIdList = new ArrayList<String>();
            }
        }
        return getConfigIdList;
    }

    public boolean updateDimTargetTableStatus(Integer DATA_STATUS, Integer IS_DOING, List<String> sourceTableIdList,String endTime) {
         startTime=System.currentTimeMillis();
        String sql = "";
        if (StringUtils.isBlank(endTime)) {
            sql = "update " + DIM_TARGET_TABLE_STATUS + " set DATA_STATUS = " + DATA_STATUS + ",IS_DOING=" + IS_DOING + ",exception_desc = '0',end_time=" + null + " where data_date='" + data_date + "' and (1 = 0 ";
        } else {
            sql = "update " + DIM_TARGET_TABLE_STATUS + " set DATA_STATUS = " + DATA_STATUS + ",IS_DOING=" + IS_DOING + ",exception_desc = '0',end_time='" + endTime + "' where data_date='" + data_date + "' and (1 = 0 ";
        }        for (int i = 0; i < sourceTableIdList.size(); i++) {
            sql += " or source_table_id='" + sourceTableIdList.get(i) + "' ";
        }
        sql += ")";
        LogUtil.info(threadNumber + "更新指标状态表SQL：" + sql);
        Connection connection = null;
        ResultSet res = null;
        PreparedStatement pstm = null;
        connection = new JdbcManager().getConnection();
        try {
            pstm = connection.prepareStatement(sql);
            pstm.executeUpdate();
            LogUtil.info(threadNumber + "更新" + DIM_TARGET_TABLE_STATUS + "成功！");
        } catch (SQLException e) {
            LogUtil.error(threadNumber + "更新 " + DIM_TARGET_TABLE_STATUS + "失败！" + e);
            return false;
        } finally {
            JdbcManager.closeAll(connection, pstm, res);
            LogUtil.debug(threadNumber+new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        return true;
    }
}
