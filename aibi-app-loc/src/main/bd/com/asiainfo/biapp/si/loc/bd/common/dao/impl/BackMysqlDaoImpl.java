/*
 * @(#)BackGbaseDaoImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.bd.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

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
 * <pre>1    2017年12月26日    Administrator        Created</pre>
 * <p/>
 *
 * @author  zhougz3
 * @version 1.0.0.2017年12月26日
 */

@Repository("backMysqlDaoImpl")
public class BackMysqlDaoImpl extends BaseBackDaoImpl implements IBackSqlDao{
    

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
            PreparedStatement preparedStatement = connection.prepareStatement("Select COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT from INFORMATION_SCHEMA.COLUMNS Where table_name='"+tableName+"'");
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
		return false;
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
	public Integer queryCount(String selectSql) {
		// TODO Auto-generated method stub
		return null;
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
	public boolean loadDataToTabByPartion(String sql, String tableName,String partionDate,String partionID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertDataToTabByPartion(String sql,String tableName,String partionDate,String partionID) {
		// TODO Auto-generated method stub
		return false;
	}




}
