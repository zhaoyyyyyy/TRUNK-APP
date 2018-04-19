package com.asiainfo.biapp.si.loc.bd.datadeal.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.bd.common.util.JDBCUtil;
import com.asiainfo.biapp.si.loc.bd.datadeal.DataDealConstants;

@Component
public class BackJdbcManager {
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = JDBCUtil.getInstance().getBackConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeAll(Connection conn, Statement st, ResultSet rs) {
        JDBCUtil.getInstance().free(conn, st, rs);
    }

    public boolean tableExists(String tableName) {
        boolean tableFlag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = new BackJdbcManager().getConnection();
            StringBuffer sql = new StringBuffer();
            sql.append("show columns in ").append(tableName);
            pstm = conn.prepareStatement(sql.toString());
            final ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                tableFlag = true;
                break;
            }
        } catch (Exception e) {
            LogUtil.debug("table " + tableName + " is not exists!");
            tableFlag = false;
        } finally {
            closeAll(conn, pstm, null);
        }
        return tableFlag;
    }

    public Boolean dropTable(String tableName) {
        boolean execute = true;
        Connection conn = null;
        PreparedStatement ps = null;
        conn = getConnection();
        String sql = "drop table IF EXISTS " + tableName;
        System.out.println("删除表" + tableName + "：" + tableName);
        try {
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            execute = false;
            System.out.println("删除表：" + tableName + "失败" + e);
            LogUtil.info("请手动删除：" + sql);
        } finally {
            closeAll(conn, ps, null);
        }
        return execute;
    }

    public List<String> getColumnNameByName(String tableName) {
        String sql = "select * from " + tableName + " where 1!=1";
        Connection conn = new BackJdbcManager().getConnection();
        ResultSet rs = null;
        Statement stm = null;
        List<String> list = new ArrayList<String>();
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.next();
            ResultSetMetaData metadata = rs.getMetaData();
            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                list.add(metadata.getColumnName(i).toUpperCase());
            }
        } catch (SQLException e) {
            LogUtil.debug("获取表" + tableName + "列信息失败" + e);
        } finally {
            closeAll(conn, null, rs);
        }
        return list;
    }

    public Map<String, String> getColumnNameAndTypeByName(String tableName) {
        //  List<String> list = new ArrayList<String>();
        Map<String, String> columnsMap = new HashMap<String, String>();
        String sql = "select * from " + tableName + " where 1!=1";
        Connection conn = new BackJdbcManager().getConnection();
        ResultSet rs = null;
        Statement stm = null;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rs.next();
            ResultSetMetaData metadata = rs.getMetaData();
            String columnType = "";
            String columnName = "";
            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                columnType = metadata.getColumnTypeName(i);
                columnName = metadata.getColumnName(i).toUpperCase();
                //  list.add(columnName+" "+columnType);
                columnsMap.put(columnName, columnType);
            }
        } catch (SQLException e) {
            LogUtil.debug("获取表" + tableName + "列信息失败" + e);
        } finally {
            closeAll(conn, null, rs);
        }
        return columnsMap;
    }

    public boolean createTable(String sql) {
        Boolean boo = true;
        Connection conn = new BackJdbcManager().getConnection();
        ResultSet rs = null;
        try {
            LogUtil.info("根据sql建表：" + sql);
            conn.prepareStatement(sql).execute();
        } catch (SQLException e) {
            LogUtil.debug("创建表失败：" + e);
            boo = false;
        } finally {
            closeAll(conn, null, rs);
        }
        return boo;
    }

    public boolean createTable(String tableName, String sql) {
        boolean isOk = true;
        List<String> columnsList = getColumnsBySql(sql);
        isOk = createTable(tableName, columnsList);
        if (isOk) {
            String insertSql = "insert into " + tableName + " " + sql;
            isOk = insertSql(insertSql);
        }
        return isOk;
    }

    public Boolean renameTableName(String oldName, String newName) {
        boolean boo = true;
        Connection connection = null;
        ResultSet res = null;
        PreparedStatement pstm = null;
        connection = new BackJdbcManager().getConnection();
        String sql = "ALTER TABLE " + oldName + " RENAME TO " + newName;
        LogUtil.info("将" + oldName + "重命名为" + newName + "SQL为：" + sql);
        try {
            int i = connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            LogUtil.debug("重命名失败：" + e);
            boo = false;
        } finally {
            closeAll(connection, pstm, res);
        }
        return boo;
    }

    public boolean insertSql(String sql) {
        Boolean boo = true;
        Connection conn = new BackJdbcManager().getConnection();
        ResultSet rs = null;
        try {
            LogUtil.info("插入数据：" + sql);
            conn.prepareStatement(sql).execute();
        } catch (SQLException e) {
            LogUtil.debug("插入数据：" + e);
            boo = false;
        } finally {
            closeAll(conn, null, rs);
        }
        return boo;
    }

    public boolean tableExists(String tableName, String partition) {
        boolean tableFlag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = new BackJdbcManager().getConnection();
            String sql = "show partitions " + tableName;
            LogUtil.info("查询分区数据是否准备好：" + sql);
            LogUtil.info("分区字段为:" + partition);
            pstm = conn.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            String partitions = "";
            while (resultSet.next()) {
                partitions = resultSet.getString(1);
                String[] split = partitions.split("\\/");
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(partition)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.debug("table " + tableName + " is not exists!" + e);
            tableFlag = false;
        } finally {
            closeAll(conn, pstm, null);
        }
        return tableFlag;
    }

    public boolean createTable(String tableName, List<String> columns) {
        boolean isOk = true;
        String BUCKET_NUMBER = DataDealConstants.BUCKET_NUMBER + "";
        String createSql = "create table " + tableName + "( ";
        for (int i = 0; i < columns.size(); i++) {
            createSql += columns.get(i) + " String,";
        }
        createSql = createSql.substring(0, createSql.length() - 1);
        createSql += ")" +
                " clustered by(" + DataDealConstants.PRODUCT_NO + ") into " + BUCKET_NUMBER + " buckets stored as " + DataDealConstants.STORED;
        if (createTable(createSql)) {
            LogUtil.info("创建表" + tableName + "成功");
        } else {
            LogUtil.info("创建表" + tableName + "失败");
            isOk = false;
        }
        return isOk;
    }

    public List<String> getColumnsBySql(String sql) {
        LogUtil.info(sql);
        List<String> columnList = new ArrayList<String>();
        int a = sql.indexOf("select");
        int b = sql.indexOf("from");
        String substring = sql.substring(a + 6, b);
        String[] columns = substring.split(",");
        String trim = "";
        String[] split = null;
        String column = "";
        for (int i = 0; i < columns.length; i++) {
            trim = columns[i].trim();
            split = trim.split(" ");
            column = split[split.length - 1];
            if (column.contains(".")) {
                column = column.split("\\.")[1];
            }
            columnList.add(column);
        }
        return columnList;
    }

    public boolean createTableByColumns(String tableName, List<String> columns, String selectSql) {
        boolean isOk = true;
        String BUCKET_NUMBER = DataDealConstants.BUCKET_NUMBER + "";
        String createSql = "create table " + tableName + "( ";
        for (int i = 0; i < columns.size(); i++) {
            createSql += columns.get(i) + ",";
        }
        createSql = createSql.substring(0, createSql.length() - 1);
        createSql += ")" +
                " clustered by(" + DataDealConstants.PRODUCT_NO + ") into " + BUCKET_NUMBER + " buckets stored as " + DataDealConstants.STORED;
        if (createTable(createSql)) {
            LogUtil.info("创建表" + tableName + "成功");
            String sql = "insert into " + tableName + " " + selectSql;
            isOk = insertSql(sql);
            if (!isOk) {
                dropTable(tableName);
            }
        } else {
            LogUtil.info("创建表" + tableName + "失败");
            isOk = false;
        }
        return isOk;
    }

}
