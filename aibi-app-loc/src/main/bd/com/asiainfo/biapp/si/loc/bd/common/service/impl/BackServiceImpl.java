
package com.asiainfo.biapp.si.loc.bd.common.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.BaseConstants;
import com.asiainfo.biapp.si.loc.base.exception.SqlRunException;
import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.bd.common.dao.IBackSqlDao;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;

@Service
@Transactional
public class BackServiceImpl implements IBackSqlService{

    private static final String REGEX_BEVEL = "/";
    private static final String REGEX_INTERRO = "?";
    
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
            if (url.contains(REGEX_INTERRO)) { //去掉[?]
                url = new StringBuilder(url).delete(url.indexOf(REGEX_INTERRO) ,url.length()).toString();
            }
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
    	String dbType = "Mysql";
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
	public boolean createTableByTemplete(String newTableName, String templeteTableName) throws SqlRunException {
		return getBackDaoBean().createTableByTemplete(newTableName,templeteTableName);
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


}
