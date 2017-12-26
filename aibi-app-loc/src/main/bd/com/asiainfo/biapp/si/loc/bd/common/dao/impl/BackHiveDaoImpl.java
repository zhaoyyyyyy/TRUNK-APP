
package com.asiainfo.biapp.si.loc.bd.common.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.bd.common.dao.IBackSqlDao;

@Repository("backHiveDaoImpl")
public class BackHiveDaoImpl extends BaseBackDaoImpl implements IBackSqlDao{

	@Override
	public List<Map<String, Object>> queryTableLikeTableName(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> queryTableColumn(String tableName) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer queryCount(String selectSql) {
		// TODO Auto-generated method stub
		return null;
	}


}
