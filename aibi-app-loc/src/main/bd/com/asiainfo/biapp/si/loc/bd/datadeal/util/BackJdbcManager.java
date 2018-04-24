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

import com.asiainfo.biapp.si.loc.base.BaseConstants;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import org.springframework.context.annotation.Configuration;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.bd.common.util.JDBCUtil;

@Configuration
public class BackJdbcManager {
    private String threadNumber = "主线程：";
    public void setThreadNumber(String threadNumber) {
        this.threadNumber = threadNumber;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = JDBCUtil.getInstance().getBackConnection();
        } catch (Exception e) {
            LogUtil.error(threadNumber + "获取后台库连接失败" + e);
        }
        return conn;
    }

    public void closeAll(Connection conn, Statement st, ResultSet rs) {
        JDBCUtil.getInstance().free(conn, st, rs);
    }

    public boolean tableExists(String tableName) {
        Long startTime = System.currentTimeMillis();
        boolean tableFlag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        StringBuffer sql = new StringBuffer();
        sql.append("show columns in ").append(tableName);
        try {
            conn = new BackJdbcManager().getConnection();
            pstm = conn.prepareStatement(sql.toString());
            final ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                tableFlag = true;
                break;
            }
        } catch (Exception e) {
            LogUtil.error(threadNumber + "table " + tableName + " is not exists!");
            tableFlag = false;
        } finally {
            closeAll(conn, pstm, null);
            LogUtil.debug(threadNumber + new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis() - startTime).append("ms."));
        }
        return tableFlag;
    }

    public Boolean dropTable(String tableName) {
        Long startTime = System.currentTimeMillis();
        boolean execute = true;
        Connection conn = null;
        PreparedStatement ps = null;
        conn = getConnection();
        String sql = "drop table IF EXISTS " + tableName;
        LogUtil.debug(threadNumber + "删除表" + tableName + "：" + tableName);
        try {
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            execute = false;
            LogUtil.error(threadNumber + "删除表：" + tableName + "失败" + e + "\n" + "请手动删除：" + sql);
        } finally {
            closeAll(conn, ps, null);
            LogUtil.debug(threadNumber + new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis() - startTime).append("ms."));
        }
        return execute;
    }

    public List<String> getColumnNameByName(String tableName) {
        Long startTime=System.currentTimeMillis();
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
            LogUtil.error(threadNumber + "获取表" + tableName + "列信息失败" + e);
        } finally {
            closeAll(conn, stm, rs);
            LogUtil.debug(threadNumber + new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        return list;
    }

    public Map<String, String> getColumnNameAndTypeByName(String tableName) {
        Long startTime=System.currentTimeMillis();
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
                columnsMap.put(columnName, columnType);
            }
        } catch (SQLException e) {
            LogUtil.error(threadNumber + "获取表" + tableName + "列信息失败" + e);
        } finally {
            closeAll(conn, stm, rs);
            LogUtil.debug(threadNumber + new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        return columnsMap;
    }

    public boolean createTable(String sql) {
        Long startTime=System.currentTimeMillis();
        Boolean boo = true;
        Connection conn = new BackJdbcManager().getConnection();
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            LogUtil.debug(threadNumber + "根据sql建表：" + sql);
            pst = conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            LogUtil.error(threadNumber + "创建表失败：" + e);
            boo = false;
        } finally {
            closeAll(conn, pst, rs);
            LogUtil.debug(threadNumber + new StringBuffer(sql).append(" cost:").append((System.currentTimeMillis()-startTime)/1000).append("s."));
        }
        return boo;
    }

    public Boolean renameTableName(String oldName, String newName) {
        Long startTime = System.currentTimeMillis();
        boolean boo = true;
        Connection connection = null;
        ResultSet res = null;
        PreparedStatement pst = null;
        connection = new BackJdbcManager().getConnection();
        String sql = "ALTER TABLE " + oldName + " RENAME TO " + newName;
        LogUtil.debug(threadNumber + "将" + oldName + "重命名为" + newName + "SQL为：" + sql);
        try {
            pst = connection.prepareStatement(sql);
            int i = pst.executeUpdate();
        } catch (SQLException e) {
            LogUtil.error(threadNumber + "重命名失败：" + e);
            boo = false;
        } finally {
            closeAll(connection, pst, res);
            LogUtil.debug(threadNumber + new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis() - startTime).append("ms."));
        }
        return boo;
    }

    public boolean insertSql(String sql) {
        Long startTime=System.currentTimeMillis();
        Boolean boo = true;
        Connection conn = new BackJdbcManager().getConnection();
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            LogUtil.debug(threadNumber + "插入数据：" + sql);
            pst = conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            LogUtil.error(threadNumber + "插入数据：" + e);
            boo = false;
        } finally {
            closeAll(conn, pst, rs);
            LogUtil.debug(threadNumber + new StringBuffer(sql).append(" cost:").append((System.currentTimeMillis()-startTime)/1000).append("s."));
        }
        return boo;
    }

    public boolean tableExists(String tableName, String partition) {
        Long startTime=System.currentTimeMillis();
        boolean tableFlag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        String sql = new StringBuffer("show partitions ").append(tableName).toString();
        try {
            conn = new BackJdbcManager().getConnection();
            LogUtil.debug(threadNumber + "查询分区数据是否准备好：" + sql + " " + "分区字段为:" + partition);
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
            LogUtil.error(threadNumber + "table " + tableName + " is not exists!" + e);
            tableFlag = false;
        } finally {
            closeAll(conn, pstm, null);
            LogUtil.debug(threadNumber + new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis()-startTime).append("ms."));
        }
        return tableFlag;
    }

    public boolean createTable(String tableName, List<String> columns) {
        String STORED = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey(BaseConstants.STORAGE_FORMATE);
        boolean isOk = true;
        String BUCKET_NUMBER = ServiceConstants.LABEL_BUCKET_NUMBER + "";
        String createSql = "create table " + tableName + "( ";
        for (int i = 0; i < columns.size(); i++) {
            createSql += columns.get(i) + " String,";
        }
        createSql = createSql.substring(0, createSql.length() - 1);
        createSql += ")" +
                " clustered by(" + ServiceConstants.LABEL_PRODUCT_NO + ") into " + BUCKET_NUMBER + " buckets stored as " + STORED;
        if (createTable(createSql)) {
            LogUtil.debug(threadNumber + "创建表" + tableName + "成功");
        } else {
            isOk = false;
        }
        return isOk;
    }

    public boolean createTableByColumns(String tableName, List<String> columns, String selectSql) {
        String STORED = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey(BaseConstants.STORAGE_FORMATE);
        boolean isOk = true;
        String BUCKET_NUMBER = ServiceConstants.LABEL_BUCKET_NUMBER + "";
        String createSql = "create table " + tableName + "( ";
        for (int i = 0; i < columns.size(); i++) {
            createSql += columns.get(i) + ",";
        }
        createSql = createSql.substring(0, createSql.length() - 1);
        createSql += ")" +
                " clustered by(" + ServiceConstants.LABEL_PRODUCT_NO + ") into " + BUCKET_NUMBER + " buckets stored as " + STORED;
        if (createTable(createSql)) {
            LogUtil.debug(threadNumber + "创建表" + tableName + "成功");
            String sql = "insert into " + tableName + " " + selectSql;
            isOk = insertSql(sql);
            if (!isOk) {
                dropTable(tableName);
            }
        } else {
            isOk = false;
        }
        return isOk;
    }

}
