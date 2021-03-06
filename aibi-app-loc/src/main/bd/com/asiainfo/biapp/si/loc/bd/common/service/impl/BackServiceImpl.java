/*
 * @(#)BackServiceImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.bd.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.BaseConstants;
import com.asiainfo.biapp.si.loc.base.exception.SqlRunException;
import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.bd.common.dao.IBackSqlDao;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.ServiceConstants;

/**
 * 后台库操作接口(业务层)
 * Title : BackServiceImpl
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
 * <pre>1    2017年12月26日    zhougz        Created</pre>
 * <p/>
 *
 * @author  zhougz
 * @version 1.0.0.2017年12月26日
 */

@Service
@Transactional
public class BackServiceImpl implements IBackSqlService{
	private Logger log = Logger.getLogger(BackServiceImpl.class);
    
    /**
     * Description: 查询当前后台库的schema
     *
     * @return 查询当前后台库的schema
     */
    public String getCurBackDbSchema() throws SqlRunException{
            
        return getBackDaoBean().getCurBackDbSchema();
    }

	/**
     * 通过配置文件拿到适配的daoImpl的beanId
     * @return
     */
    private IBackSqlDao getBackDaoBean() {
    	String dbType = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey(BaseConstants.SYS_BGDB_TYPE);
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
    public List<Map<String, String>> queryBySql(String sql) throws SqlRunException {
		return getBackDaoBean().queryBySql(sql);
	}

	@Override
	public Integer queryCount(String selectSql) throws SqlRunException {
		long s = System.currentTimeMillis();
		Integer count = getBackDaoBean().queryCount(selectSql);
		LogUtil.debug(new StringBuffer("queryCount cost:").append(System.currentTimeMillis()-s).append("ms.sql:").append(selectSql));
		return count;
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
	public boolean insertCustomerData(String sql, String tableName, String customerId,String configId) throws SqlRunException {
		log.debug("BackServiceImpl.insertCustomerData sql = " + sql);
		log.debug("BackServiceImpl.insertCustomerData tableName = " + tableName);
		log.debug("BackServiceImpl.insertCustomerData customerId = " + customerId);
		
		boolean isInsertTable = true;
		
		String backType =  CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey("LOC_CONFIG_SYS_BGDB_TYPE");
		log.debug(backType);
		if(backType.equals("Hive")||backType.equals("SparkSql")){
			isInsertTable = this.insertCustomDataHive(sql, tableName, customerId,configId);
		}else if(backType.equals("Ads")){
			isInsertTable = this.insertCustomDataAds(sql, tableName, customerId);
		}
		return isInsertTable;
	}
	
	private boolean insertCustomDataHive(String sql, String tableName, String customerId,String configId) throws SqlRunException{
		log.debug("BackServiceImpl.insertCustomDataHive sql = " + sql);
		log.debug("BackServiceImpl.insertCustomDataHive tableName = " + tableName);
		log.debug("BackServiceImpl.insertCustomDataHive customerId = " + customerId);
		boolean isExistsTable = getBackDaoBean().isExistsTable(tableName);
		boolean isCreateTable = true;
		boolean isInsertTable = true;
		log.debug("-------------------- BackServiceImpl.insertCustomDataHive isExistsTable = " + isExistsTable);
		List<String> primaryKey = new ArrayList<String>();
		Map<String,String> columnName = new LinkedHashMap<String,String>();
		String insertcolumn = "";
		insertcolumn= customerId;
		primaryKey.add(ServiceConstants.KHQ_CROSS_ID_PARTION);
		columnName.put(ServiceConstants.KHQ_CROSS_ID_PARTION, "string");
		columnName.put(ServiceConstants.KHQ_CROSS_COLUMN, "string");
		List<String> orgColumns = CocCacheProxy.getCacheProxy().getAllOrgColumnByConfig(configId);
		if(null != orgColumns && !orgColumns.isEmpty()){
			for(String org: orgColumns){
				columnName.put(org, "String");
			}
		}
		if(!isExistsTable){
			isCreateTable = getBackDaoBean().createTableByName(tableName, columnName, primaryKey);
			log.debug("-------------------- BackServiceImpl.insertCustomDataHive isCreateTable = " + isCreateTable);
			if(!isCreateTable){
				//建表失败
				return isCreateTable;
			}
		}
		LogUtil.info("-------------------- BackServiceImpl.insertCustomerData sql = " + sql);
		isInsertTable = getBackDaoBean().insertDataToTabByPartion(sql, tableName, insertcolumn);
		log.debug("-------------------- BackServiceImpl.insertCustomDataHive isInsertTable = " + isInsertTable);
		return isInsertTable;
	
	}
	
	private boolean insertCustomDataAds(String sql, String tableName, String customerId) throws SqlRunException{

		log.debug("-------------------- BackServiceImpl.insertCustomDataAds sql = " + sql);
		log.debug("-------------------- BackServiceImpl.insertCustomDataAds tableName = " + tableName);
		log.debug("-------------------- BackServiceImpl.insertCustomDataAds customerId = " + customerId);
		
		boolean isExistsTable = getBackDaoBean().isExistsTable(tableName);
		boolean isCreateTable = true;
		boolean isInsertTable = true;
		log.debug("-------------------- BackServiceImpl.insertCustomDataAds isExistsTable = " + isExistsTable);
		
		List<String> primaryKey = new ArrayList<String>();
		Map<String,String> columnName = new HashMap<String,String>();
		StringBuffer sqlBuffer = new StringBuffer();
		String insertcolumn = "";
		primaryKey.add(ServiceConstants.KHQ_CROSS_COLUMN);
			
		sqlBuffer.append("SELECT ").append(ServiceConstants.KHQ_CROSS_COLUMN);
		sqlBuffer.append(",'").append(customerId).append("' ");
		sqlBuffer.append(sql);
			
		insertcolumn = ServiceConstants.KHQ_CROSS_COLUMN+","+ServiceConstants.KHQ_CROSS_ID_PARTION;
		primaryKey.add(ServiceConstants.KHQ_CROSS_ID_PARTION);
		columnName.put(ServiceConstants.KHQ_CROSS_ID_PARTION, "varchar(32)");
		columnName.put(ServiceConstants.KHQ_CROSS_COLUMN, "varchar(32)");
		if(!isExistsTable){
			isCreateTable = getBackDaoBean().createTableByName(tableName, columnName, primaryKey);
			log.debug("-------------------- BackServiceImpl.insertCustomDataAds isCreateTable = " + isCreateTable);
			if(!isCreateTable){
				//建表失败
				return isCreateTable;
			}
		}
		
		log.debug("-------------------- BackServiceImpl.insertCustomDataAds sqlBuffer = " + sqlBuffer.toString());
		LogUtil.info("-------------------- BackServiceImpl.insertCustomerData sqlBuffer = " + sqlBuffer.toString());
		isInsertTable = getBackDaoBean().insertDataToTabByPartion(sqlBuffer.toString(), tableName, insertcolumn);
		log.debug("-------------------- BackServiceImpl.insertCustomDataAds isInsertTable = " + isInsertTable);
		return isInsertTable;
	
	}
}
