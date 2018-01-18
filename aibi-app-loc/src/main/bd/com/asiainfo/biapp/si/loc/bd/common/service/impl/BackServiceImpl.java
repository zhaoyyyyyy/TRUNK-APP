
package com.asiainfo.biapp.si.loc.bd.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.asiainfo.biapp.si.loc.base.BaseConstants;
import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.base.exception.SqlRunException;
import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.bd.common.dao.IBackSqlDao;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;

@Service
@Transactional
public class BackServiceImpl implements IBackSqlService{
	private Logger log = Logger.getLogger(BackServiceImpl.class);
    private static final String REGEX_BEVEL = "/";
    
    /** 缓冲当前的schema */
    private static String SYS_BGDB_SCHEMA = null;
    
    /**
     * Description: 查询当前后台库的schema
     *
     * @return 查询当前后台库的schema
     */
    public String getCurBackDbSchema() throws SqlRunException{
        String schema = null;
        if (null == SYS_BGDB_SCHEMA) {
            String url = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey(BaseConstants.SYS_BGDB_URL);
            if (url.endsWith(REGEX_BEVEL)) { //去掉末尾[/]
                url = new StringBuilder(url).deleteCharAt(url.length() - 1).toString();
            }
            String[] split = url.split(REGEX_BEVEL);
            schema = split[split.length-1];
        } else {
            schema = SYS_BGDB_SCHEMA;
        }
            
        return schema;
    }

	/**
     * 通过配置文件拿到适配的daoImpl的beanId
     * @return
     */
    private IBackSqlDao getBackDaoBean() {
    	String dbType = "Hive";
    	String backDaoBeanId = "back"+dbType+"DaoImpl";
    	IBackSqlDao backSqlDao = (IBackSqlDao)SpringContextHolder.getBean(backDaoBeanId);
        return backSqlDao;
    }

	@Override
	public List<Map<String, String>> queryTableLikeTableName(String tableName) throws SqlRunException {
		return getBackDaoBean().queryTableLikeTableName(tableName);
	}

	@Override
	public List<Map<String, String>> queryTableColumn(String tableName) throws SqlRunException {
		return getBackDaoBean().queryTableColumn(tableName);
	}

	@Override
	public boolean isExistsTable(String tableName) throws SqlRunException {
		return getBackDaoBean().isExistsTable(tableName);
	}

	@Override
	public boolean insertTableAsSelect(String tableName, String selectSql) throws SqlRunException {
		return getBackDaoBean().insertTableAsSelect(tableName,selectSql);
	}

	@Override
	public boolean createTableAsSelect(String tableName, String selectSql) throws SqlRunException {
		return getBackDaoBean().createTableAsSelect(tableName,selectSql);
	}

	@Override
	public boolean alterTable(String tableName, String columnName, String columnType) throws SqlRunException {
		return getBackDaoBean().alterTable(tableName,columnName,columnType);
	}

	@Override
	public List<Map<String, String>> queryForPage(String selectSql, Integer pageStart, Integer pageSize) throws SqlRunException {
		return getBackDaoBean().queryForPage(selectSql,pageStart,pageSize);
	}

	@Override
	public Integer queryCount(String selectSql) throws SqlRunException {
		return getBackDaoBean().queryCount(selectSql);
	}

    @Override
    public boolean dropTable(String tableName) throws SqlRunException {
        return getBackDaoBean().dropTable(tableName);
    }
    
    /**
     * 
     * Description: 重命名表
     *
     * @param oldTableName 旧表名
     * @param newTableName 新表明
     * @return
     */
    public boolean renameTable(String oldTableName, String newTableName) throws SqlRunException {
        return getBackDaoBean().renameTable(oldTableName, newTableName);
    }

	@Override
	public boolean insertCustomerData(String sql, String tableName, String customerId) throws SqlRunException {
		log.debug("-------------------- BackServiceImpl.insertCustomerData sql = " + sql);
		log.debug("-------------------- BackServiceImpl.insertCustomerData tableName = " + tableName);
		log.debug("-------------------- BackServiceImpl.insertCustomerData customerId = " + customerId);
		
		boolean isExistsTable = getBackDaoBean().isExistsTable(tableName);
		boolean isCreateTable = true;
		boolean isInsertTable = true;
		log.debug("-------------------- BackServiceImpl.insertCustomerData isExistsTable = " + isExistsTable);
		
		Map<String,String> columnName = new HashMap<String,String>();
		List<String> primaryKey = new ArrayList<String>();
		primaryKey.add(LabelInfoContants.KHQ_CROSS_COLUMN);
		primaryKey.add(LabelInfoContants.KHQ_CROSS_ID_PARTION);
		columnName.put(LabelInfoContants.KHQ_CROSS_COLUMN, "varchar(32)");
		columnName.put(LabelInfoContants.KHQ_CROSS_ID_PARTION, "varchar(32)");
		if(!isExistsTable){
			isCreateTable = getBackDaoBean().createTableByName(tableName, columnName, primaryKey);
			log.debug("-------------------- BackServiceImpl.insertCustomerData isCreateTable = " + isCreateTable);
			if(!isCreateTable){
				//建表失败
				return isCreateTable;
			}
		}
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT ").append(LabelInfoContants.KHQ_CROSS_COLUMN);
		sqlBuffer.append(",'").append(customerId).append("' ");
		sqlBuffer.append(sql);
		log.debug("-------------------- BackServiceImpl.insertCustomerData sqlBuffer = " + sqlBuffer.toString());
		String insertcolumn = LabelInfoContants.KHQ_CROSS_COLUMN+","+LabelInfoContants.KHQ_CROSS_ID_PARTION;
		isInsertTable = getBackDaoBean().insertDataToTabByPartion(sql, tableName, insertcolumn);
		log.debug("-------------------- BackServiceImpl.insertCustomerData isInsertTable = " + isInsertTable);
		return isInsertTable;
	}


}
