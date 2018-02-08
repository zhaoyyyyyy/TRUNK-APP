package com.asiainfo.biapp.si.loc.bd.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.exception.SqlRunException;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.bd.common.dao.IBackSqlDao;
import com.asiainfo.biapp.si.loc.bd.common.util.JDBCUtil;

@Repository("backAdsDaoImpl")
public class BackAdsDaoImpl  extends BaseBackDaoImpl implements IBackSqlDao{
	private Logger log = Logger.getLogger(BackAdsDaoImpl.class);
	@Override
	public List<Map<String, String>> queryTableLikeTableName(String tableName) throws SqlRunException {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public List<Map<String, String>> queryTableColumn(String tableName) throws SqlRunException {
        //desc tableName;
        String sql = new StringBuilder("SHOW COLUMNS IN ").append(tableName).toString();
        log.debug(" ----------   BackAdsDapImpl.queryTableColumn sql =  " + sql );
        List<Map<String, String>> datas = null;
        try{
            datas = this.executeResList(sql);
            for (Map<String, String> map :  datas) {
            	map.put("COLUMN_NAME", map.get("FIELD"));
            	map.put("DATA_TYPE", map.get("TYPE"));
            	map.put("COLUMN_COMMENT", map.get("EXTRA"));
			}
            log.debug(" ----------   BackAdsDapImpl.queryTableColumn datas.size =  " + datas.size() );
        }catch (Exception e){
            throw new SqlRunException(e.getMessage());
        }
		return datas;
	}

	@Override
	public boolean isExistsTable(String tableName) throws SqlRunException {
        boolean res = true;
       // log.debug(" ----------   BackAdsDapImpl.isExistsTable tableName =  " + tableName);
        try{
            List<Map<String, String>> cols = this.queryTableColumn(tableName);
            log.debug(" ----------   BackAdsDapImpl.isExistsTable cols.size =  " + cols.size());
            if (cols.isEmpty()) {
                res = false;
            }
        }catch (Exception e){
            res = false;
            LogUtil.error("isExistsTable出错！"+e);
            throw new SqlRunException(e.getMessage());
        }
        
        return res;
	}

	@Override
	public boolean createTableByTemplete(String newTableName, String templeteTableName) throws SqlRunException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertTableAsSelect(String tableName, String selectSql) throws SqlRunException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createTableAsSelect(String tableName, String selectSql) throws SqlRunException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean alterTable(String tableName, String columnName, String columnType) throws SqlRunException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Map<String, String>> queryForPage(String selectSql, Integer pageStart, Integer pageSize)
			throws SqlRunException {
        pageStart = pageStart == null? 0 : pageStart;
        String limitSql = pageSize == null ? " ": " limit "+ pageStart+","+pageStart+pageSize;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            conn = this.getBackConnection();
            String sql = selectSql+limitSql;
            st = conn.prepareStatement(sql);
            rs =  st.executeQuery();
            return resultSetToList(rs);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
        	JDBCUtil.getInstance().free(conn, st, rs);
        }
		return null;
	}

	@Override
	public Integer queryCount(String selectSql) throws SqlRunException {
		log.debug(" ----------   BackAdsDapImpl.queryCount selectSql =  " + selectSql);
        int rows = 0;
//      原因是按照缺省方式打开的ResultSet不支持结果集cursor的回滚
//      如果想要完成上述操作，要在生成Statement对象时加入如下两个参数：
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            conn = this.getBackConnection();
            st = conn.prepareStatement(selectSql);
            rs =  st.executeQuery();
            log.debug(" ----------   BackAdsDapImpl.queryCount rs.getRow() =  " + rs.getRow());
            if(rs != null && rs.next()) {
                if (selectSql.contains("count") || selectSql.contains("COUNT")) {
                    rows = rs.getInt(1); 
                    log.debug(" ----------   BackAdsDapImpl.queryCount rows =  " + rows);
                } else {
                    while(rs.next()){
                        rows += 1;
                    }
                    log.debug(" ----------   BackAdsDapImpl.queryCount rows =  " + rows);
                }
            }
        }catch (Exception e){
        	LogUtil.error("操作后台库出错", e);
            throw new SqlRunException(e.getMessage());
        }finally{
        	JDBCUtil.getInstance().free(conn, st, rs);
        }
		return rows;
	}

	@Override
	public boolean dropTable(String tableName) throws SqlRunException {
		String sql = "drop table " + tableName;
		return this.executeResBoolean(sql);
	}

	/**
	 * ads 不支持 这个 操作
	 */
	@Override
	public boolean renameTable(String oldTableName, String newTableName) throws SqlRunException {
		return false;
	}

	@Override
	public boolean createVerticalTable(String tableName, String columnName) throws SqlRunException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadDataToTabByPartion(String fileName, String tableName, String partionID) throws SqlRunException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertDataToTabByPartion(String sql, String tableName, String partionID) throws SqlRunException {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ").append(tableName).append("(").append(partionID).append(") ").append(sql);
		
		log.debug(" ----------   BackAdsDapImpl.insertDataToTabByPartion sql =  " + sb.toString());
		try{
            return this.executeResBoolean(sb.toString());
        }catch (Exception e){
        	LogUtil.error("插入数据出错！"+e+"----executeSql:----"+sb.toString());
            throw new SqlRunException(e.getMessage());
        }
	}

	@Override
	public boolean createTableByName(String tableName, Map<String,String> columnName, List<String> primaryKey)
			throws SqlRunException {
		StringBuffer primaryKeyStr = new StringBuffer();
		//参数判断，表名，列名 主键不能为空； 主键必须存在与列名里
		if(StringUtils.isNoneBlank(tableName)&&null != columnName && !columnName.isEmpty() && null != primaryKey && !primaryKey.isEmpty()){
			Set<String> keySet = columnName.keySet();
			for(String mykey:primaryKey){
				if(!keySet.contains(mykey)){
					return false;
				}
				primaryKeyStr.append(mykey).append(",");
			}
			
		}else{
			return false;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE ").append(super.getCurBackDbSchema()).append(".").append(tableName);
		sb.append("(");
		Set<String> keySet = columnName.keySet();
		for(String colName : keySet){
			sb.append(colName).append(" ").append(columnName.get(colName)).append(",");
		}
		sb.append(" PRIMARY KEY (").append(primaryKeyStr.toString().substring(0,primaryKeyStr.toString().length()-1)).append(")");
		sb.append(") ");
		sb.append("PARTITION BY HASH KEY (").append(primaryKey.get(0)).append(") PARTITION NUM 256 ");
		sb.append("TABLEGROUP loc_ads ");
		sb.append("OPTIONS (UPDATETYPE='realtime') ");
		
		try{
            boolean res = this.executeResBoolean(sb.toString());
            Thread.sleep(50000);
            return res;
        }catch (Exception e){
        	LogUtil.error("createTableByName出错！"+e);
            throw new SqlRunException(e.getMessage());
        }
	}
	
	private Boolean executeResBoolean(String sql) throws SqlRunException {
        Boolean res = true;
        long s = System.currentTimeMillis();
        log.debug(" ----------   BackAdsDapImpl.executeResBoolean sql =  " + sql);
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            conn = this.getBackConnection();
            st = conn.prepareStatement(sql);
           // res =  preparedStatement.execute();
            st.execute();
            log.debug(" ----------   BackAdsDapImpl.executeResBoolean res =  " + res);
            LogUtil.debug(new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis()-s).append("ms."));
            
        }catch (Exception e){
        	LogUtil.error("executeResBoolean出错！"+e+"----executeSql:----"+sql);
            res = false;
            throw new SqlRunException(e.getMessage());
        }finally{
        	JDBCUtil.getInstance().free(conn, st, rs);
        }
        return res;
    }
	
	private List<Map<String, String>> executeResList(String sql) throws SqlRunException {
        long s = System.currentTimeMillis();
       // log.debug(" ----------   BackAdsDapImpl.executeResList sql =  " + sql);
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            conn = this.getBackConnection();
            st = conn.prepareStatement(sql);
            rs =  st.executeQuery();
           // log.debug(" ----------   BackAdsDapImpl.executeResList resultSet.getRow() =  " + resultSet.getRow());
            LogUtil.debug(new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis()-s).append("ms."));
            
            return this.resultSetToList(rs);
        }catch (Exception e){
        	LogUtil.error("BackAdsDapImpl.executeResList出错！"+e+"----executeQuerySql:----"+sql);
            throw new SqlRunException(e.getMessage());
        }finally{
        	JDBCUtil.getInstance().free(conn, st, rs);
        }
    }

}
