package com.asiainfo.biapp.si.loc.bd.datadeal.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.bd.common.util.JDBCUtil;
import com.asiainfo.biapp.si.loc.bd.datadeal.component.LabelDealComponent;

@Configuration
public class JdbcManager {
    private String threadNumber = "主线程：";
    public void setThreadNumber(String threadNumber) {
        this.threadNumber = threadNumber;
    }
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = JDBCUtil.getInstance().getWebConnection();
        } catch (Exception e) {
            LogUtil.error(threadNumber + "获取前台连接失败" + e);
        }
        return conn;
    }

    public static void closeAll(Connection conn, Statement st, ResultSet rs) {
        JDBCUtil.getInstance().free(conn, st, rs);
    }


    public Map<String, String> getAllUserTable(String config_id) {
        Map<String, String> map = new HashMap<String, String>();
        String sql = "select day_table_name,month_table_name,day_main_column,month_main_column,is_partition,day_partition_column,month_partition_column,other_column,org_column_name,level_id from loc_all_user_msg t1 left join loc_config_table_rel t2 on t1.PRI_KEY=t2.PRI_KEY " +
                " left join DIM_ORG_LEVEL t3 on t1.PRI_KEY=t3.PRI_KEY" +
                " where t2.config_id='" + config_id + "'" +
                " order by t3.sort_num;";
        Connection conn = getConnection();
        ResultSet rs = null;
        PreparedStatement stm = null;
        LabelDealComponent labelDealComponent = new LabelDealComponent();
        String schema = labelDealComponent.getSchema();
        try {
            LogUtil.debug("获取用户全量表信息：" + sql);
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            int i = 0;
            String otehrColumnName = "";
            while (rs.next()) {
                if (i == 0) {
                    if (rs.getString("day_table_name").split("\\.").length > 1) {
                        map.put("ALL_USERS_TABLE_NAME_DM", rs.getString("day_table_name"));
                    } else {
                        map.put("ALL_USERS_TABLE_NAME_DM", schema + "." + rs.getString("day_table_name"));
                    }
                    if (rs.getString("month_table_name").split("\\.").length > 1) {
                        map.put("ALL_USERS_TABLE_NAME_MM", rs.getString("month_table_name"));
                    } else {
                        map.put("ALL_USERS_TABLE_NAME_MM", schema + "." + rs.getString("month_table_name"));
                    }

                    map.put("ALL_USER_JOIN_COLUMN_NAME_DM", rs.getString("day_main_column"));
                    map.put("ALL_USER_JOIN_COLUMN_NAME_MM", rs.getString("month_main_column"));
                    map.put("SOURCE_TABLES_HAS_DATE_SUFFIX", rs.getString("IS_PARTITION"));
                    map.put("ALL_USERS_TABLE_DM_SELECT_COLUMN", rs.getString("day_partition_column"));
                    map.put("ALL_USERS_TABLE_MM_SELECT_COLUMN", rs.getString("month_partition_column"));
                    if (!StringUtils.isEmpty(rs.getString("other_column"))) {
                        String[] other_columns = rs.getString("other_column").split(",");
                        for (int j = 0; j < other_columns.length; j++) {
                            if (!StringUtils.isEmpty(other_columns[j])) {
                                otehrColumnName += other_columns[j] + " " + other_columns[j] + ",";
                            }
                        }
                        map.put("OTHER_NEED_COLUMNS", otehrColumnName);
                    }
                    i++;
                }
                otehrColumnName = "";
                if (map.containsKey("OTHER_NEED_COLUMNS")) {
                    otehrColumnName = map.get("OTHER_NEED_COLUMNS");
                }
                String oldColumn = rs.getString("ORG_COLUMN_NAME");
                String newColumn = ServiceConstants.ALL_USER_ORG_LEVEL_ + rs.getInt("LEVEL_ID");
                otehrColumnName += oldColumn + " " + newColumn + ",";
                map.put("OTHER_NEED_COLUMNS", otehrColumnName);
            }
        } catch (SQLException e) {
            LogUtil.error("加载用户全量表失败" + e);
        } finally {
            JdbcManager.closeAll(conn, stm, rs);
        }
        if (map.containsKey("OTHER_NEED_COLUMNS")) {
            String other_need_columns = map.get("OTHER_NEED_COLUMNS");
            if (other_need_columns.length() > 0) {
                other_need_columns = other_need_columns.substring(0, other_need_columns.length() - 1);
            }
            map.put("OTHER_NEED_COLUMNS", other_need_columns);
        }
        return map;
    }


    public boolean insertSql(String sql) {
        Boolean boo = true;
        Connection conn = new JdbcManager().getConnection();
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            LogUtil.debug("插入数据：" + sql);
            pst = conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            LogUtil.error("插入数据：" + e);
            boo = false;
        } finally {
            closeAll(conn, pst, rs);
        }
        return boo;
    }

}
