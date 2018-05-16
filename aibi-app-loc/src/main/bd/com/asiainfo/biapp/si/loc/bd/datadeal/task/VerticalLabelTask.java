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

import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.bd.datadeal.util.TimeUtil;
import com.asiainfo.biapp.si.loc.bd.list.service.ICyclicityListDataService;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.bd.datadeal.component.LabelDealComponent;
import com.asiainfo.biapp.si.loc.bd.datadeal.util.BackJdbcManager;
import com.asiainfo.biapp.si.loc.bd.datadeal.util.JdbcManager;

/**
 * Created by pwj on 2018/3/30.
 */
@Component
public class VerticalLabelTask implements Runnable {
    private ICyclicityListDataService cyclicityListDataService;
    private String threadNumber;
    private String data_date = "";
    private String config_id = "";
    private Integer data_cycle = 0;
    private String otherColumnName = "";
    private String ALL_USERS_TABLE_NAME = "";
    private String ALL_USERS_TABLE_SELECT_COLUMN = "";
    private String ALL_USER_JOIN_COLUMN_NAME = "";
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

    public void run() {
        cyclicityListDataService = (ICyclicityListDataService) SpringContextHolder.getBean("cyclicityListDataServiceImpl");
        Long startTime=System.currentTimeMillis();
        threadNumber = Thread.currentThread().getName() + "————" + config_id + "：\n";
        JdbcManager jdbcManager=new JdbcManager();
        ALL_USER_MAP = jdbcManager.getAllUserTable(config_id);
        if (ALL_USER_MAP.size() > 0) {
            //生成纵表标签
            Label();
        } else {
            LogUtil.error(threadNumber+"加载用户全量表失败,专区ID为：" + config_id);
        }
        LogUtil.info(new StringBuffer(threadNumber+"专区生成数据耗时").append(" cost:").append((System.currentTimeMillis()-startTime)/1000).append("s."));
        CocCacheProxy.getCacheProxy().reflashAllCache();      
	    cyclicityListDataService.runDayListDataByConfigId(config_id, data_date);
    }

    private void Label() {
        String getLabel = "select t3.SOURCE_TABLE_ID,t1.COLUMN_NAME SOURCE_COLUMN_NAME,t2.SOURCE_TABLE_NAME,t2.TABLE_SCHEMA SOURCE_TABLE_SCHEMA,t2.DATA_STORE, t2.DATE_COLUMN_NAME,t2.WHERE_SQL,t2.ID_COLUMN " +
                "from LOC_SOURCE_INFO t1 " +
                "left JOIN LOC_SOURCE_TABLE_INFO t2 ON t1.SOURCE_TABLE_ID=t2.SOURCE_TABLE_ID " +
                "left JOIN DIM_TARGET_TABLE_STATUS t3 ON t1.SOURCE_TABLE_ID=t3.SOURCE_TABLE_ID " +
                "WHERE t2.STATUS_ID=1 " +
                "AND t3.DATA_DATE= '" + data_date + "' " +
                "AND t3.DATA_STATUS=1 " +
                "AND t2.CONFIG_ID='" + config_id + "' " +
                "AND t2.READ_CYCLE=  " +data_cycle+" "+
                "AND t2.SOURCE_TABLE_TYPE=2";
        //存放列信息
        List<String> columnList = new ArrayList<String>();
        Map<String, List<String>> source_table_id2column_nameMap = new HashMap<String, List<String>>();
        Map<String, String> source_table_id2source_table_nameMap = new HashMap<String, String>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = new JdbcManager().getConnection();
            LogUtil.info(threadNumber + "通过Sql获取指标信息：" + getLabel);
            preparedStatement = connection.prepareStatement(getLabel);
            resultSet = preparedStatement.executeQuery();
            String source_table_id;
            String source_column_name;
            String source_table_name;
            String source_table_schema;
            Integer data_store;
            String date_column_name;
            String WHERE_SQL;
            String ID_COLUMN;
            while (resultSet.next()) {
                source_table_id = resultSet.getString("source_table_id");
                source_column_name = resultSet.getString("source_column_name");
                source_table_name = resultSet.getString("source_table_name");
                source_table_schema = resultSet.getString("source_table_schema");
                data_store = resultSet.getInt("data_store");
                date_column_name = resultSet.getString("date_column_name");
                WHERE_SQL = resultSet.getString("WHERE_SQL");
                ID_COLUMN = resultSet.getString("ID_COLUMN");
                //002用来判断表是否存在
                source_table_name = source_table_schema + "." + source_table_name;
                if (!source_table_id2source_table_nameMap.containsKey(source_table_id)) {
                    if (StringUtils.isEmpty(date_column_name)) {
                        date_column_name = " ";
                    }
                    if (StringUtils.isEmpty(WHERE_SQL)) {
                        WHERE_SQL = " ";
                    }
                    source_table_id2source_table_nameMap.put(source_table_id, source_table_name + ":" + data_store + ":" + date_column_name + ":" + ID_COLUMN + ":" + WHERE_SQL);
                }
                //003表对应的具体字段
                if (source_table_id2column_nameMap.containsKey(source_table_id)) {
                    columnList = source_table_id2column_nameMap.get(source_table_id);
                } else {
                    columnList = new ArrayList<String>();
                }
                columnList.add(source_column_name);
                source_table_id2column_nameMap.put(source_table_id, columnList);
                //004表对应源表
            }
        } catch (SQLException e) {
            LogUtil.error(threadNumber + "通过Sql获取指标信息：" + getLabel);
            return;
        } finally {
            JdbcManager.closeAll(connection, preparedStatement, resultSet);
        }

        if (source_table_id2source_table_nameMap.size() > 0) {
            //过滤准备好的表
            source_table_id2source_table_nameMap = existsTable(source_table_id2source_table_nameMap);
            createLabelTable(source_table_id2source_table_nameMap, source_table_id2column_nameMap);
        } else {
            LogUtil.info(threadNumber + "没找到可跑的标签，专区结束");
        }

    }

    /**
     * 判断表是否存在，不存在的更新状态
     *
     * @param source_table_id2source_table_nameMap
     * @return
     */
    private Map<String, String> existsTable(Map<String, String> source_table_id2source_table_nameMap) {
        LabelDealComponent labelDealComponent=new LabelDealComponent();
        labelDealComponent.setThreadNumber(threadNumber);
        List<String> notExistsSourceTableIdList = new ArrayList<String>();
        Map<String, String> new_source_table_id2source_table_nameMap = new HashMap<String, String>();
        Iterator<Map.Entry<String, String>> entryIterator = source_table_id2source_table_nameMap.entrySet().iterator();
        boolean isOk = true;
        String value = "";
        String[] split = null;
        Map.Entry<String, String> entry = null;
        String source_table_name = "";
        String data_store = "";
        String date_column_name = "";
        BackJdbcManager backJdbcManager = new BackJdbcManager();
        backJdbcManager.setThreadNumber(threadNumber);
        while (entryIterator.hasNext()) {
            entry = entryIterator.next();
            value = entry.getValue();
            split = value.split(":");
            // source_table_name + ":" + data_store + ":" + date_column_name + ":" + ID_COLUMN + ":" + WHERE_SQL
            source_table_name = split[0];
            data_store = split[1];
            date_column_name = split[2];
            //1分区2分表
            if (data_store.equals("1")) {
                isOk = backJdbcManager.tableExists(source_table_name, date_column_name + "=" + data_date);
            } else if (data_store.equals("2")) {
                isOk = backJdbcManager.tableExists(source_table_name,0);
            }
            if (isOk) {
                new_source_table_id2source_table_nameMap.put(entry.getKey(), value);
            } else {
                notExistsSourceTableIdList.add(entry.getKey());
            }
        }
        labelDealComponent.setThreadNumber(threadNumber);
        labelDealComponent.updateDimTargetTableStatusNotExistsTable(notExistsSourceTableIdList);
        return new_source_table_id2source_table_nameMap;
    }

    private boolean createLabelTable(Map<String, String> source_table_id2source_table_nameMap, Map<String, List<String>> source_table_id2column_nameMap) {
      BackJdbcManager backJdbcManager=new BackJdbcManager();
        boolean isOk = true;
        String preTargetTableName = ServiceConstants.LV + config_id.toUpperCase() + "_";
        LabelDealComponent labelDealComponent=new LabelDealComponent();
        labelDealComponent.setThreadNumber(threadNumber);
        labelDealComponent.setData_date(data_date);
        labelDealComponent.setDate_cycle(data_cycle);
        List<String> sourceTableIdList = new ArrayList<String>();
        //更新源表状态为：1,1
        LogUtil.info(threadNumber + "更新即将抽取的表对应的数据状态");
        labelDealComponent.updateDimTargetTableStatus(1, 1, sourceTableIdList,null);
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
        columnNameAndTypeByName = backJdbcManager.getColumnNameAndTypeByName(ALL_USERS_TABLE_NAME);
        List<String> allUserColumnList = new ArrayList<String>();
        allUserColumnList.add(ServiceConstants.LABEL_PRODUCT_NO + " " + columnNameAndTypeByName.get(ALL_USER_JOIN_COLUMN_NAME.toUpperCase()));
        for (int i = 0; i < splitColumnName.length; i++) {
            otherColumn += ", t." + splitColumnName[i];
            if (!lis.contains(splitColumnName[i].split(" ")[0])) {
                lis.add(splitColumnName[i].split(" ")[0]);
                others += "," + splitColumnName[i].split(" ")[0];
            }
            String[] split = splitColumnName[i].split(" ");
            allUserColumnList.add(split[split.length - 1] + " " + columnNameAndTypeByName.get(split[0].toUpperCase()));
        }
        //直接根据源表建表：
        Iterator<Map.Entry<String, String>> targetEntry = source_table_id2source_table_nameMap.entrySet().iterator();
        String value = "";
        List<String> columnList = null;
        String targetTableName = "";
        String select = "select ";
        String source_table_name = "";
        String data_store = "";
        String date_column_name = "";
        String ID_COLUMN = "";
        String WHERE_SQL = "";
        String key = "";
        String t = "t";
        int num = 1;
        String from = "";
        String targetTableSql = "";
        String[] split = null;
        List<String> allColumns = new ArrayList<String>();
        Map<String, String> targetTable2SQL = new HashMap<String, String>();
        Map<String, String> target_table_name2source_table_idMap = new HashMap<String, String>();
        List<String> columnsList = new ArrayList<String>();
        while (targetEntry.hasNext()) {
            allColumns.clear();
            targetTableSql = "select " + "t." + ALL_USER_JOIN_COLUMN_NAME + " " + ServiceConstants.LABEL_PRODUCT_NO + otherColumn;
            from = " from  ";
            String allUserTable=" ( select " + ALL_USER_JOIN_COLUMN_NAME + others + " from " + ALL_USERS_TABLE_NAME + where + " ) t";
            Map.Entry<String, String> entry = targetEntry.next();
            value = entry.getValue();
            key = entry.getKey();
            where = "";
            select = "select ";
            columnList = source_table_id2column_nameMap.get(key);
            // source_table_name + ":" + data_store + ":" + date_column_name + ":" + ID_COLUMN + ":" + WHERE_SQL
            split = value.split(":");
            source_table_name = split[0];
            data_store = split[1];
            date_column_name = split[2];
            ID_COLUMN = split[3];
            WHERE_SQL = split[4];
            targetTableName = preTargetTableName;
            String[] split1 = source_table_name.split("\\.");
            String schema = labelDealComponent.getSchema();
            targetTableName += split1[split1.length - 1];
            targetTableName = schema + "." + targetTableName;
            select += ID_COLUMN;
            if (data_store.equals("2")) {
                source_table_name += data_date;
            }
            if (data_store.equals("1") && !date_column_name.equals(" ")) {
                where += " where " + date_column_name + "='" + data_date + "'";
            }
            if (StringUtils.isNotEmpty(WHERE_SQL) && !WHERE_SQL.equals(" ")) {
                if (StringUtils.isNotEmpty(where)) {
                    where += " and " + WHERE_SQL;
                } else {
                    where += " where " + WHERE_SQL;
                }
            }
            columnNameAndTypeByName.clear();
            columnNameAndTypeByName = backJdbcManager.getColumnNameAndTypeByName(source_table_name);
            columnsList.clear();
            for (int i = 0; i < allUserColumnList.size(); i++) {
                columnsList.add(allUserColumnList.get(i));
            }
            for (int j = 0; j < columnList.size(); j++) {
                select += "," + columnList.get(j);
                allColumns.add(t + num + "." + columnList.get(j));
                columnsList.add(columnList.get(j) + " " + columnNameAndTypeByName.get(columnList.get(j).toUpperCase()));
            }
            select += " from " + source_table_name;
            String sourceName=" (" + select + where + ") t"+ num;
            from += sourceName +" left outer join "+allUserTable+ " on t" + num + "." + ID_COLUMN + "=" + "t." + ALL_USER_JOIN_COLUMN_NAME;
            for (int k = 0; k < allColumns.size(); k++) {
                targetTableSql += "," + allColumns.get(k);
            }
            targetTableSql = targetTableSql + from;
            System.out.println("=================");
            System.out.println(targetTableSql);
            System.out.println("=================");
            targetTable2SQL.put(targetTableName, targetTableSql);
            target_table_name2source_table_idMap.put(targetTableName, key);
            LogUtil.debug(columnsList);
            boolean tableIfExists = labelDealComponent.createTableIfExists(3, targetTableName + "_" + data_date, targetTableSql, columnsList);
            String source_table_id = target_table_name2source_table_idMap.get(targetTableName);
            List<String> list = new ArrayList<String>();
            list.add(source_table_id);
            if (tableIfExists) {
                //成功更改指标源表状态：0,0
                LogUtil.info(threadNumber + "更新抽取完成的表对应的数据状态");
                labelDealComponent.updateDimTargetTableStatus(0, 0, list, TimeUtil.getCurrentTime());
                //更新标签状态：
                List<String> list1 = getLabelIdByTargetName(targetTableName + "_");
                //todo 更新标签状态
                if (list1 != null && list1.size() > 0) {
                    labelDealComponent.updateLocLabelInfo(list1);
                    labelDealComponent.updateDimLabelStatus(list1);
                    labelDealComponent.insertIntoDimLabelStatus(list1);
                    labelDealComponent.updateLocNewestLabelDate(config_id);
                    LogUtil.info(threadNumber + "专区" + config_id + "标签生成结束");
                } else {
                    LogUtil.info(threadNumber+"没有标签需要更新");
                }
            } else {
                //成功更改指标源表状态：1,0
                LogUtil.info(threadNumber + "更新未进行抽取的表对应的数据状态");
                labelDealComponent.updateDimTargetTableStatus(1, 0, list,null);
            }
        }
        return isOk;
    }

    private List<String> getLabelIdByTargetName(String targetTableName) {
        String[] split = targetTableName.split("\\.");
        List<String> labelList = new ArrayList<String>();
        String schema = split[0];
        String tableName = split[1];
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String label_id = null;
        String data_time = data_date;
        if (data_cycle == 1) {
            data_time = data_date;
        } else if (data_cycle == 2) {
            data_time = data_date + "01";
        }
        String sql = " SELECT distinct T1.LABEL_ID " +
                "FROM LOC_LABEL_INFO T1 " +
                "left JOIN LOC_MDA_SYS_TABLE_COLUMN T2 ON T1.LABEL_ID=T2.LABEL_ID " +
                "left JOIN LOC_MDA_SYS_TABLE T3 ON T2.TABLE_ID=T3.TABLE_ID " +
                "WHERE T1.DATA_STATUS_ID IN (2,4) " +
                "AND '" + data_time + "' >=T1.EFFEC_TIME " +
                "AND '" + data_time + "'<=T1.FAIL_TIME " +
                "AND T2.COLUMN_STATUS=1 " +
                "AND T1.UPDATE_CYCLE=" +data_cycle+" "+
                "AND T1.GROUP_TYPE=0 " +
                "AND T1.CONFIG_ID='" + config_id + "' " +
                "AND T1.LABEL_TYPE_ID=8 " +
                "AND T3.TABLE_TYPE=3 " +
                "and (table_schema='" + schema + "' and table_name='" + tableName + "')";
        try {
            connection = new JdbcManager().getConnection();
            LogUtil.info(threadNumber + "通过Sql获取指标信息：" + sql);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                label_id = resultSet.getString("label_id");
                labelList.add(label_id);
            }
        } catch (SQLException e) {
            LogUtil.error(threadNumber + "通过Sql获取指标信息：" + "失败" + e);
            return null;
        } finally {
            JdbcManager.closeAll(connection, preparedStatement, resultSet);
        }
        return labelList;
    }

}
