/*
 * @(#)BackMysqlDaoImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.bd.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.exception.SqlRunException;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.bd.common.dao.IBackSqlDao;

/**
 * 后台库是mysql库的情况下
 * Title : BackMysqlDaoImpl
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年12月26日    zhougz3        Created</pre>
 * <p/>
 *
 * @author  zhougz3
 * @version 1.0.0.2017年12月26日
 */

@Repository("backMysqlDaoImpl")
public class BackMysqlDaoImpl extends BaseBackDaoImpl implements IBackSqlDao{


    public String getCurBackDbSchema() throws SqlRunException{
        return super.getCurBackDbSchema();
    }

	@Override
	public List<Map<String, String>> queryTableLikeTableName(String tableName) {
		try{
            Connection connection = this.getBackConnection();
            String sql = "SELECT table_name FROM information_schema.tables WHERE table_name like '%"+tableName+"%' ORDER BY table_name DESC";
            LogUtil.info(sql);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet =  preparedStatement.executeQuery();
            List ls = resultSetToList(resultSet);
            return ls;
        }catch (Exception e){
            e.printStackTrace();
        }
		return null;
	}

	@Override
	public List<Map<String, String>> queryTableColumn(String tableName) {
        try{
            Connection connection = this.getBackConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("Select distinct COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT from INFORMATION_SCHEMA.COLUMNS Where table_name='"+tableName+"'");
            ResultSet resultSet =  preparedStatement.executeQuery();
            List ls = resultSetToList(resultSet);
            return ls;
        }catch (Exception e){
            e.printStackTrace();
        }
		return null;
	}

	@Override
	public boolean isExistsTable(String tableName) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean createTableByTemplete(String newTableName, String templeteTableName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertTableAsSelect(String tableName, String selectSql) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createTableAsSelect(String tableName, String selectSql) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean alterTable(String tableName, String columnName, String columnType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Map<String, String>> queryForPage(String selectSql, Integer pageStart, Integer pageSize) {
        pageStart = pageStart == null? 0 : pageStart;
        String limitSql = pageSize == null ? " ": " limit "+ pageSize+","+pageSize;
        try{
            Connection connection = this.getBackConnection();
            String sql = selectSql+limitSql;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet =  preparedStatement.executeQuery();
            return resultSetToList(resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }
		return null;
	}
	
	@Override
	public Integer queryCount(String selectSql) throws SqlRunException {
	    selectSql = selectSql.toUpperCase();
        if (!selectSql.contains("COUNT")) {
            selectSql = new StringBuilder("SELECT COUNT(1) FROM (").append(selectSql).append(") a").toString();
        }
        int rows = 0;
        try{
            ResultSet rs =  this.getBackConnection().prepareStatement(selectSql).executeQuery();
            if(rs != null && rs.next()) {
                rows = rs.getInt(1);
            }
        }catch (Exception e){
            throw new SqlRunException("操作后台库出错:"+e.getMessage());
        }
        
        return rows;
	}

    @Override
    public boolean dropTable(String tableName) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean renameTable(String oldTableName, String newTableName) {
        // TODO Auto-generated method stub
        return false;
    }

	@Override
	public boolean createVerticalTable(String tableName,String columnName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadDataToTabByPartion(String sql, String tableName,String partionID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertDataToTabByPartion(String sql,String tableName,String partionID) {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ").append(tableName).append("(").append(partionID).append(") ").append(sql);
		Boolean res = true;
		try {
			Connection connection = this.getBackConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
			res = preparedStatement.execute();
		} catch (Exception e) {
			res = false;
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean createTableByName(String tableName, Map<String, String> columnName, List<String> primaryKey)
			throws SqlRunException {
		// TODO Auto-generated method stub
		return false;
	}




}
