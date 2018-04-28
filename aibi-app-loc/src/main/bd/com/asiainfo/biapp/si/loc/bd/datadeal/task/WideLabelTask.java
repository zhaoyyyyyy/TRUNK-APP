package com.asiainfo.biapp.si.loc.bd.datadeal.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.si.loc.bd.datadeal.util.TimeUtil;
import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.bd.datadeal.component.LabelDealComponent;
import com.asiainfo.biapp.si.loc.bd.datadeal.util.BackJdbcManager;
import com.asiainfo.biapp.si.loc.bd.datadeal.util.JdbcManager;

/**
 * Created by pwj on 2018/1/3.
 */
@Component
public class WideLabelTask implements Runnable {
    private String threadNumber;
    private String data_date = "";
    private String config_id = "";
    private Integer data_cycle = 0;
    private String LOC_LABEL_INFO = "LOC_LABEL_INFO";
    private String DIM_LABEL_COUNT_RULES = "DIM_LABEL_COUNT_RULES";
    private String LOC_MDA_SYS_TABLE = "LOC_MDA_SYS_TABLE";
    private String LOC_MDA_SYS_TABLE_COLUMN = "LOC_MDA_SYS_TABLE_COLUMN";
    private String LOC_SOURCE_INFO = "LOC_SOURCE_INFO";
    private String LOC_SOURCE_TABLE_INFO = "LOC_SOURCE_TABLE_INFO";
    private String DIM_TARGET_TABLE_STATUS = "DIM_TARGET_TABLE_STATUS";
    private String otherColumnName = "";
    private String ALL_USERS_TABLE_NAME = "";
    private String ALL_USERS_TABLE_SELECT_COLUMN = "";
    private String ALL_USER_JOIN_COLUMN_NAME = "";
    private List<String> sourceTableIdList = new ArrayList<String>();
    private String indexTable = "";
    private Map<String, String> ALL_USER_MAP = null;

    public void setConfig_id(String config_id) {
        this.config_id = config_id;
    }

    public void setData_date(String data_date) {
        this.data_date = data_date;
    }

    public void setDate_cycle(Integer data_cycle) {
        this.data_cycle = data_cycle;
    }

    List<String> filterLabelList = new ArrayList<String>();

    public void run() {
        Long startTime=System.currentTimeMillis();
        threadNumber = Thread.currentThread().getName() + "————" + config_id + "：\n";
        JdbcManager jdbcManager = new JdbcManager();
        jdbcManager.setThreadNumber(threadNumber);
        ALL_USER_MAP = jdbcManager.getAllUserTable(config_id);
        LabelDealComponent labelDealComponent=new LabelDealComponent();
        labelDealComponent.setThreadNumber(threadNumber);
        filterLabelList = labelDealComponent.filterLabelId(data_date, config_id);
        LogUtil.info(threadNumber+"根据源表初步过滤标签"+filterLabelList);
        if (filterLabelList.size() < 0) {
            LogUtil.info(threadNumber+"没有可跑的标签，专区结束");
            return;
        }
        if (ALL_USER_MAP.size() > 0) {
            Boolean isOk = createIndexTable(data_date, config_id, data_cycle);
            if (isOk) {
                handleLabel(data_date, config_id, data_cycle);
            } else {
                LogUtil.info(threadNumber+"创建指标汇总表失败，终止生成标签");
                return;
            }
        } else {
            LogUtil.info(threadNumber+"加载用户全量表失败,专区ID为：" + config_id);
        }
        LogUtil.info(new StringBuffer(threadNumber+"专区生成数据耗时").append(" cost:").append((System.currentTimeMillis()-startTime)/1000).append("ms."));
    }

    private void handleLabel(String data_date, String config_id, Integer data_cycle) {
        LabelDealComponent labelDealComponent = new LabelDealComponent();
        labelDealComponent.setDate_cycle(data_cycle);
        labelDealComponent.setData_date(data_date);
        labelDealComponent.setThreadNumber(threadNumber);
        LogUtil.info(threadNumber + "Step002——————————————————开始创建标签汇总表——————————————————");
        Map indexMap = getIndexMap();
        LogUtil.info(threadNumber + "指标信息："+indexMap);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> labelIdList = new ArrayList<String>();
        //表名，列集合
        Map<String, List<String>> createTableMap = new HashMap<String, List<String>>();
        //表名、label_id集合
        Map<String, List<String>> sourceIdMap = new HashMap<String, List<String>>();
        String dataTime = "";
        if (data_cycle == ServiceConstants.LABEL_CYCLE_TYPE_D) {
            dataTime = data_date;
        } else if (data_cycle == ServiceConstants.LABEL_CYCLE_TYPE_M) {
            dataTime = data_date + "01";
        }
        String getLabelSql = "select t1.label_id,t2.COLUMN_NAME,t3.table_schema,t3.table_name,t4.DEPEND_INDEX" +
                ",t4.COUNT_RULES" +
                " from " + LOC_LABEL_INFO + " t1" +
                " left join " + LOC_MDA_SYS_TABLE_COLUMN + " t2" +
                " on t1.label_id=t2.label_id" +
                " left join " + LOC_MDA_SYS_TABLE + " t3" +
                " on t2.table_id=t3.table_id" +
                " left join " + DIM_LABEL_COUNT_RULES + " t4" +
                " on t2.COUNT_RULES_CODE=t4.COUNT_RULES_CODE" +
                " where t1.DATA_STATUS_ID in (2,4) " +
                " and '" + dataTime + "'>=t1.EFFEC_TIME " +
                " and '" + dataTime + "'<=t1.FAIL_TIME" +
                " and t2.COLUMN_STATUS=1 " +
                " and t1.update_cycle= " + data_cycle + " " +
                " and t1.LABEL_TYPE_ID<>8 " +
                " and t1.GROUP_TYPE=0 " +
                " and t3.table_type<>8 " +
                " and t1.config_id='" + config_id + "'";
        if (filterLabelList.size() > 0) {
            getLabelSql+=" and (1 = 0 ";
            for(int i=0;i<filterLabelList.size();i++){
                getLabelSql+=" or t1.label_id='" + filterLabelList.get(i) + "' ";
            }
            getLabelSql+=")";
        } else {
            return;
        }
        String label_id = "";
        String column_name = "";
        String table_schema;
        String table_name;
        String depend_index;
        String count_rules;
        Boolean aBoolean;
        try {
            LogUtil.info(threadNumber + "获取标签信息SQL：" + getLabelSql);
            connection = new JdbcManager().getConnection();
            preparedStatement = connection.prepareStatement(getLabelSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                label_id = resultSet.getString("label_id");
                column_name = resultSet.getString("COLUMN_NAME");
                table_schema = resultSet.getString("table_schema");
                table_name = resultSet.getString("table_name");
                depend_index = resultSet.getString("DEPEND_INDEX");
                count_rules = resultSet.getString("COUNT_RULES");
                aBoolean = ifExistsIndex(depend_index, indexMap);
                if (aBoolean) {
                    labelIdList.add(label_id);
                    count_rules = columnReplace(count_rules, indexMap, depend_index);
                    List<String> lis = new ArrayList<String>();
                    List<String> labelIdLists = new ArrayList<String>();
                    String tableName = table_schema + "." + table_name + data_date;
                    if (createTableMap.containsKey(tableName)) {
                        lis = createTableMap.get(tableName);
                        labelIdLists = sourceIdMap.get(tableName);
                    } else {
                        lis = new ArrayList<String>();
                        labelIdLists = new ArrayList<String>();
                    }
                    lis.add(count_rules + " " + column_name);
                    labelIdLists.add(label_id);
                    createTableMap.put(tableName, lis);
                    sourceIdMap.put(tableName, labelIdLists);
                } else {
                    LogUtil.debug(threadNumber + "标签：" + label_id + "的数据还未准备好！");
                }
            }
        } catch (SQLException e) {
            LogUtil.debug(threadNumber + "获取标签信息失败" + e);
            return;
        } finally {
            JdbcManager.closeAll(connection, preparedStatement, resultSet);
        }
        LogUtil.info(threadNumber + "预抽取的标签为：" + labelIdList);
        labelIdList = createLabelTable(createTableMap, sourceIdMap, labelIdList);
        LogUtil.info(threadNumber + "本次实际完成的标签为：" + labelIdList);
        if (labelIdList.size() > 0) {
            //todo 更新源表状态表
            if (sourceTableIdList.size() > 0) {
                LogUtil.info(threadNumber + "更新数据抽取完成的表对应的数据状态");
                labelDealComponent.updateDimTargetTableStatus(0, 0, sourceTableIdList,TimeUtil.getCurrentTime());
            }
            LogUtil.info(threadNumber + "指标表生成完成，开始更新状态");
            labelDealComponent.updateLocLabelInfo(labelIdList);
            labelDealComponent.updateLocNewestLabelDate(config_id);
            labelDealComponent.updateDimLabelStatus(labelIdList);
            labelDealComponent.insertIntoDimLabelStatus(labelIdList);
            LogUtil.info(threadNumber + "专区" + config_id + "标签生成结束");
        } else {
            if (sourceTableIdList.size() > 0) {
                labelDealComponent.updateDimTargetTableStatus(1, 0, sourceTableIdList,null);
            }
            LogUtil.info(threadNumber + "抽取的标签数量为：" + 0+"不进行标准标签相关状态的更新");
        }
    }

    private String columnReplace(String count_rules, Map<String, String> indexMap, String depend_index) {
        String[] split = depend_index.split(",");
        for (int i = 0; i < split.length; i++) {
            count_rules = count_rules.replaceAll(split[i], indexMap.get(split[i]));
        }
        return count_rules;
    }

    private Map<String, String> getIndexMap() {
        Map<String, String> indexMap = new HashMap<String, String>();
        BackJdbcManager backJdbcManager = new BackJdbcManager();
        backJdbcManager.setThreadNumber(threadNumber);
        indexTable = ServiceConstants.KPI_L_PREF_ + config_id + "_" + data_date;
        List<String> indexList = backJdbcManager.getColumnNameByName(indexTable);
        String key = "";
        for (int i = 0; i < indexList.size(); i++) {
            key = indexList.get(i).substring(1, indexList.get(i).length());
            indexMap.put(key, indexList.get(i));
        }
        return indexMap;
    }

    private Boolean ifExistsIndex(String depend_index, Map<String, String> indexMap) {
        Boolean boo = true;
        String[] split = depend_index.split(",");
        for (int i = 0; i < split.length; i++) {
            if (!indexMap.containsKey(split[i])) {
                boo = false;
            }
        }
        return boo;
    }

    private List<String> createLabelTable(Map<String, List<String>> createTableMap, Map<String, List<String>> sourceIdMap, List<String> labelIdList) {
        LabelDealComponent labelDealComponent = new LabelDealComponent();

        labelDealComponent.setDate_cycle(data_cycle);
        labelDealComponent.setData_date(data_date);
        labelDealComponent.setThreadNumber(threadNumber);
        LogUtil.info(threadNumber + createTableMap.size());
        Iterator<Map.Entry<String, List<String>>> createTableMaps = createTableMap.entrySet().iterator();
        String tableName = "";
        while (createTableMaps.hasNext()) {
            Map.Entry<String, List<String>> entry = createTableMaps.next();
            tableName = entry.getKey();
            List<String> columnNames = entry.getValue();

            final String[] split = otherColumnName.split(",");
            String otehrColumn = "";
            for (int i = 0; i < split.length; i++) {
                String[] split1 = split[i].split(" ");
                otehrColumn += "," + split1[1] + " " + split1[1];
            }
            String sql = "  select " + ServiceConstants.LABEL_PRODUCT_NO + " " + ServiceConstants.LABEL_PRODUCT_NO + otehrColumn;
            for (int i = 0; i < columnNames.size(); i++) {
                sql += "," + columnNames.get(i);
            }
            sql += " from " + indexTable;
            boolean isOk = labelDealComponent.createTableIfExists(2, tableName, sql, null);
            if (!isOk) {
                LogUtil.info(threadNumber + "创建标签表失败，查找不进行更新的标签");
                List<String> wrongLabelIdList = sourceIdMap.get(tableName);
                LogUtil.info(threadNumber + "不能进行更新的标签为：" + wrongLabelIdList);
                for (int i = 0; i < wrongLabelIdList.size(); i++) {
                    labelIdList.remove(wrongLabelIdList.get(i));
                }
            }
        }
        return labelIdList;
    }

    private Boolean createIndexTable(String data_date, String config_id, Integer data_cycle) {
        BackJdbcManager backJdbcManager = new BackJdbcManager();
        backJdbcManager.setThreadNumber(threadNumber);
        LabelDealComponent labelDealComponent = new LabelDealComponent();
        labelDealComponent.setDate_cycle(data_cycle);
        labelDealComponent.setData_date(data_date);
        labelDealComponent.setThreadNumber(threadNumber);
        LogUtil.info(threadNumber + "Step001——————————————————开始创建指标汇总表——————————————————");
        String where = "";
        String SOURCE_TABLES_HAS_DATE_SUFFIX = ALL_USER_MAP.get("SOURCE_TABLES_HAS_DATE_SUFFIX");
        if (data_cycle == ServiceConstants.LABEL_CYCLE_TYPE_D) {
            ALL_USERS_TABLE_NAME = ALL_USER_MAP.get("ALL_USERS_TABLE_NAME_DM");
            ALL_USER_JOIN_COLUMN_NAME = ALL_USER_MAP.get("ALL_USER_JOIN_COLUMN_NAME_DM");
        } else if (data_cycle == ServiceConstants.LABEL_CYCLE_TYPE_M) {
            ALL_USERS_TABLE_NAME = ALL_USER_MAP.get("ALL_USERS_TABLE_NAME_MM");
            ALL_USER_JOIN_COLUMN_NAME = ALL_USER_MAP.get("ALL_USER_JOIN_COLUMN_NAME_MM");
        }
        LogUtil.info(threadNumber + "判断用户全量表是否分区");
        if (SOURCE_TABLES_HAS_DATE_SUFFIX != null && SOURCE_TABLES_HAS_DATE_SUFFIX.trim().length() > 0 && "0".equals(SOURCE_TABLES_HAS_DATE_SUFFIX)) {
            LogUtil.info(threadNumber + "用户全量表为分区表");
            if (data_cycle == ServiceConstants.LABEL_CYCLE_TYPE_D) {
                ALL_USERS_TABLE_SELECT_COLUMN = ALL_USER_MAP.get("ALL_USERS_TABLE_DM_SELECT_COLUMN");
            } else if (data_cycle == ServiceConstants.LABEL_CYCLE_TYPE_M) {
                ALL_USERS_TABLE_SELECT_COLUMN = ALL_USER_MAP.get("ALL_USERS_TABLE_MM_SELECT_COLUMN");
            }
            if (!backJdbcManager.tableExists(ALL_USERS_TABLE_NAME, ALL_USERS_TABLE_SELECT_COLUMN + "=" + data_date)) {
                LogUtil.info(threadNumber + "用户全量表" + ALL_USERS_TABLE_NAME + "的数据还未准备好");
                return false;
            }
            where = " where " + ALL_USERS_TABLE_SELECT_COLUMN + "=" + data_date;
        } else {
            ALL_USERS_TABLE_NAME += data_date;
            if (!backJdbcManager.tableExists(ALL_USERS_TABLE_NAME,0)) {
                LogUtil.info(threadNumber + "用户全量表" + ALL_USERS_TABLE_NAME + "的数据还未准备好");
                return false;
            }
        }

        LogUtil.info(threadNumber + "用户全量表为：" + ALL_USERS_TABLE_NAME);
        otherColumnName = ALL_USER_MAP.get("OTHER_NEED_COLUMNS");
        LogUtil.info(threadNumber + "从用户全量表获取其他需要字段字段");
        String[] splitColumnName = otherColumnName.split(",");
        String otherColumn = "";
        String others = "";
        List lis = new ArrayList<String>();
        Map<String, String> columnNameAndTypeByName = null;
        //用来保存汇总表的列信息
        List<String> columnsList = new ArrayList<String>();
        columnNameAndTypeByName = backJdbcManager.getColumnNameAndTypeByName(ALL_USERS_TABLE_NAME);
        columnsList.add(ServiceConstants.LABEL_PRODUCT_NO + " " + columnNameAndTypeByName.get(ALL_USER_JOIN_COLUMN_NAME.toUpperCase()));
        for (int i = 0; i < splitColumnName.length; i++) {
            otherColumn += ", t." + splitColumnName[i];
            if (!lis.contains(splitColumnName[i].split(" ")[0])) {
                lis.add(splitColumnName[i].split(" ")[0]);
                others += "," + splitColumnName[i].split(" ")[0];
            }
            String[] split = splitColumnName[i].split(" ");
            columnsList.add(split[split.length - 1] + " " + columnNameAndTypeByName.get(split[0].toUpperCase()));
        }
        List<String> notExistsTableList = new ArrayList<String>();
        String getIndexSql = "select t3.source_table_id,t1.column_name,t1.deposit_column,t2.source_table_name,t2.table_schema,t2.data_store,t2.date_column_name,t2.WHERE_SQL,t2.ID_COLUMN " +
                " from " + LOC_SOURCE_INFO + " t1 " +
                " join " + LOC_SOURCE_TABLE_INFO + " t2 " +
                " on t1.source_table_id=t2.source_table_id " +
                " join " + DIM_TARGET_TABLE_STATUS + " t3 " +
                " on t1.source_table_id=t3.source_table_id " +
                " where t2.status_id=1  " +
                " and t3.data_date= " + data_date +
                " and t2.config_id='" + config_id + "'" +
                " and t2.source_table_type=1" +
                " and t2.read_cycle=" + data_cycle +
                " and t3.DATA_STATUS=1 " +
                " and t3.IS_DOING=0";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String tableName = "";
        String nickname = "";
        //表名，字段list
        Map<String, List<String>> columnMap = new HashMap<String, List<String>>();
        //表名，source_table_id
        Map<String, String> sourceTableIdMap = new HashMap<String, String>();
        Map<String, String> sourceTableMap = new HashMap<String, String>();
        try {
            connection = new JdbcManager().getConnection();
            LogUtil.info(threadNumber + "通过Sql获取指标信息：" + getIndexSql);
            preparedStatement = connection.prepareStatement(getIndexSql);
            resultSet = preparedStatement.executeQuery();
            String source_table_id;
            String column_name;
            String deposit_column;
            String source_table_name;
            String table_schema;
            Integer data_store;
            String date_column_name;
            String WHERE_SQL;
            String ID_COLUMN;
            while (resultSet.next()) {
                source_table_id = resultSet.getString("source_table_id");
                column_name = resultSet.getString("column_name");
                deposit_column = resultSet.getString("deposit_column");
                source_table_name = resultSet.getString("source_table_name");
                table_schema = resultSet.getString("table_schema");
                data_store = resultSet.getInt("data_store");
                date_column_name = resultSet.getString("date_column_name");
                WHERE_SQL = resultSet.getString("WHERE_SQL");
                ID_COLUMN = resultSet.getString("ID_COLUMN");
                String key = "";
                //获取表名
                if (!StringUtils.isEmpty(table_schema)) {
                    tableName = table_schema + "." + source_table_name;
                } else {
                    tableName = source_table_name;
                }
                //1分区、2分表
                if (data_store == 1) {
                    //处理where条件
                    if (!StringUtils.isEmpty(WHERE_SQL)) {
                        WHERE_SQL = " and " + WHERE_SQL;
                    } else {
                        WHERE_SQL = "";
                    }
                    key += tableName + " where " + date_column_name + "=" + data_date + WHERE_SQL;
                } else if (data_store == 2) {
                    if (!StringUtils.isEmpty(WHERE_SQL)) {
                        WHERE_SQL = " where " + WHERE_SQL;
                    }
                    tableName += data_date;
                    key = tableName + WHERE_SQL;
                }
                //表做为key，字段作为value
                //获取表名对应的字段集合
                List<String> list = null;
                if (columnMap.containsKey(key)) {
                    list = columnMap.get(key);
                } else {
                    list = new ArrayList<String>();
                    list.add(ID_COLUMN);
                }
                //集合添加
                if (!list.contains(column_name + " " + deposit_column)) {
                    list.add(column_name + " " + deposit_column);
                }
                columnMap.put(key, list);
                //1分区、2分表判断数据是否准备好
                sourceTableIdMap.put(tableName, source_table_id + ":" + key);
                String values = source_table_id + ":" + tableName + ":" + data_store + ":" + date_column_name;
                sourceTableMap.put(key, values);
            }
        } catch (SQLException e) {
            LogUtil.error(threadNumber + "获取指标信息失败:" + e);
            return false;
        } finally {
            JdbcManager.closeAll(connection, preparedStatement, resultSet);
        }
        //sourceTableMap.put(key,values);
        //没有准备好的表直接退出
        if (sourceTableIdMap.size() > 0) {
            Iterator<Map.Entry<String, String>> entryIterator = sourceTableMap.entrySet().iterator();
            while (entryIterator.hasNext()) {
                Map.Entry<String, String> entry = entryIterator.next();
                String[] split = entry.getValue().split(":");
                // source_table_id:tableName:data_store:date_column_name;
                if (split[2].equals("1")) {
                    boolean exists = backJdbcManager.tableExists(tableName, split[3] + "=" + data_date);
                    if (exists) {
                        sourceTableIdList.add(split[0]);
                    } else {
                        LogUtil.info(threadNumber + "分区表:" + tableName + "的分区数据还未准备好");
                        notExistsTableList.add(split[0]);
                        sourceTableIdMap.remove(entry.getKey());
                    }
                } else if (split[2].equals("2")) {
                    boolean exists = backJdbcManager.tableExists(tableName,0);
                    if (exists) {
                        sourceTableIdList.add(split[0]);
                    } else {
                        notExistsTableList.add(split[0]);
                        sourceTableIdMap.remove(entry.getKey());
                    }
                }
            }
            //拼接sql
            Iterator<Map.Entry<String, String>> createTableMaps = sourceTableIdMap.entrySet().iterator();
            indexTable = ServiceConstants.KPI_L_PREF_ + config_id + "_" + data_date;
            LogUtil.info(threadNumber + "指标汇总表表名：" + indexTable);
            String createIndexSql = "select " + "t." + ALL_USER_JOIN_COLUMN_NAME + " " + ServiceConstants.LABEL_PRODUCT_NO + otherColumn;
            String from = " from  " + "( select " + ALL_USER_JOIN_COLUMN_NAME + others + " from " + ALL_USERS_TABLE_NAME + where + " ) t";
            int num = 1;
            tableName = "";
            while (createTableMaps.hasNext()) {
                Map.Entry<String, String> entry = createTableMaps.next();
                String sourceTableName = entry.getKey();
                String[] split = entry.getValue().split(":");
                String sourceTableId = split[0];
                String key = split[1];
                sourceTableIdList.add(sourceTableId);
                List<String> columnList = columnMap.get(key);
                String joinName = columnList.get(0);
                String cols = "";
                tableName = key.split(" where")[0];
                columnNameAndTypeByName = backJdbcManager.getColumnNameAndTypeByName(tableName);
                for (int i = 1; i < columnList.size(); i++) {
                    createIndexSql += ",t" + num + "." + columnList.get(i);
                    String[] split1 = columnList.get(i).split(" ");
                    if (split1.length > 1) {
                        cols += split1[0] + ",";
                        columnsList.add(split1[split1.length - 1] + " " + columnNameAndTypeByName.get(split1[0].toUpperCase()));
                    }
                }
                if (cols.length() > 1) {
                    cols = cols.substring(0, cols.length() - 1);
                    nickname = " (select " + joinName + "," + cols + " from  " + key + ")";
                    from += " left outer join " + nickname + " t" + num + " on t." + ALL_USER_JOIN_COLUMN_NAME + "=t" + num + "." + joinName + " ";
                    num++;
                }
            }
            createIndexSql += from;
            if (sourceTableIdList.size() > 0) {
                LogUtil.info(threadNumber + "更新即将抽取的表对应的数据状态");
                labelDealComponent.updateDimTargetTableStatus(1, 1, sourceTableIdList,null);
            }
            if (notExistsTableList.size() > 0) {
                LogUtil.info(threadNumber + "更新数据不存在的表对应的数据状态");
                labelDealComponent.updateDimTargetTableStatusNotExistsTable(notExistsTableList);
            }
            final boolean isOk = labelDealComponent.createTableIfExists(1, indexTable, createIndexSql, columnsList);
            if (!isOk && sourceTableIdList.size() > 0) {
                LogUtil.info(threadNumber + "更新数据未正常抽取的表对应的数据状态");
                labelDealComponent.updateDimTargetTableStatus(1, 0, sourceTableIdList, TimeUtil.getCurrentTime());
            }
            return isOk;
        } else {
            LogUtil.info(threadNumber + "没有可跑的元数据，终止标签抽取");
            return false;
        }
    }
}
