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

@Repository("backAdsDaoImpl")
public class BackAdsDapImpl  extends BaseBackDaoImpl implements IBackSqlDao{
	private Logger log = Logger.getLogger(BackAdsDapImpl.class);
	@Override
	public List<Map<String, String>> queryTableLikeTableName(String tableName) throws SqlRunException {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public List<Map<String, String>> queryTableColumn(String tableName) throws SqlRunException {
        List<Map<String, String>> res = new ArrayList<>();
        //desc tableName;
        String sql = new StringBuilder("SHOW COLUMNS IN ").append(tableName).toString();
        log.debug(" ----------   BackAdsDapImpl.queryTableColumn sql =  " + sql );
        List<Map<String, String>> datas = null;
        try{
            datas = this.executeResList(sql);
            log.debug(" ----------   BackAdsDapImpl.queryTableColumn datas.size =  " + datas.size() );
        }catch (Exception e){
            throw new SqlRunException("操作后台库出错");
        }
        
        if (null != datas && !datas.isEmpty()) {
            LogUtil.debug("There are "+datas.size()+" cols in the table."+datas.toString());
            for (Map<String, String> map : datas) {
                if ((map.get("col_name")!=null)&&(!map.get("col_name").contains("#"))) {
                    res.add(map);
                } else {    //在hive的rs中去掉以[#]开始的以下的列
                    break;
                }
            }
        }
        
		return res;
	}

	@Override
	public boolean isExistsTable(String tableName) throws SqlRunException {
        boolean res = true;
        log.debug(" ----------   BackAdsDapImpl.isExistsTable tableName =  " + tableName);
        try{
            List<Map<String, String>> cols = this.queryTableColumn(tableName);
            log.debug(" ----------   BackAdsDapImpl.isExistsTable cols.size =  " + cols.size());
            if (cols.isEmpty()) {
                res = false;
            }
        }catch (Exception e){
            res = false;
            LogUtil.error("isExistsTable出错！"+e);
            throw new SqlRunException("操作后台库出错");
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer queryCount(String selectSql) throws SqlRunException {
		log.debug(" ----------   BackAdsDapImpl.queryCount selectSql =  " + selectSql);
        int rows = 0;
//      原因是按照缺省方式打开的ResultSet不支持结果集cursor的回滚
//      如果想要完成上述操作，要在生成Statement对象时加入如下两个参数：
        try{
            Connection connection = this.getBackConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            ResultSet rs =  preparedStatement.executeQuery();
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
            throw new SqlRunException("操作后台库出错");
        }
		return rows;
	}

	@Override
	public boolean dropTable(String tableName) throws SqlRunException {
		// TODO Auto-generated method stub
		return false;
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
			Thread.sleep(1000);
            return this.executeResBoolean(sb.toString());
        }catch (Exception e){
        	LogUtil.error("插入数据出错！"+e);
            throw new SqlRunException("操作后台库出错");
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
            throw new SqlRunException("操作后台库出错");
        }
	}
	
	private Boolean executeResBoolean(String sql) throws SqlRunException {
        Boolean res = true;
        long s = System.currentTimeMillis();
        log.debug(" ----------   BackAdsDapImpl.executeResBoolean sql =  " + sql);
        try{
            Connection connection = this.getBackConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
           // res =  preparedStatement.execute();
            preparedStatement.execute();
            log.debug(" ----------   BackAdsDapImpl.executeResBoolean res =  " + res);
            LogUtil.debug(new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis()-s).append("ms."));
            
        }catch (Exception e){
        	LogUtil.error("executeResBoolean出错！"+e);
            res = false;
            throw new SqlRunException("操作后台库出错");
        }
        return res;
    }
	
	private List<Map<String, String>> executeResList(String sql) throws SqlRunException {
        long s = System.currentTimeMillis();
        log.debug(" ----------   BackAdsDapImpl.executeResList sql =  " + sql);
        try{
            Connection connection = this.getBackConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet =  preparedStatement.executeQuery();
            log.debug(" ----------   BackAdsDapImpl.executeResList resultSet.getRow() =  " + resultSet.getRow());
            LogUtil.debug(new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis()-s).append("ms."));
            
            return this.resultSetToList(resultSet);
        }catch (Exception e){
            throw new SqlRunException("操作后台库出错");
        }
    }

}
