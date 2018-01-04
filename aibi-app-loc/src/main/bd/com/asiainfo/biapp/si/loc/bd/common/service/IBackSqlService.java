
package com.asiainfo.biapp.si.loc.bd.common.service;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.si.loc.base.exception.SqlRunException;

public interface IBackSqlService {

    /**
     * 查询后台库中的表
     * Description: 
     *
     * @param tableName 表名
     * @return table_name
     */
    public List<Map<String, String>> queryTableLikeTableName(String tableName) throws SqlRunException;

    /**
     * 查询某表中的列
     * Description: 
     *
     * @param tableName 表名
     * @return  COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT
     */
    public List<Map<String, String>> queryTableColumn(String tableName) throws SqlRunException;

	
	/**
	 * 是否存在某标
	 * Description: 
	 *
	 * @param tableName
	 * @return
	 */
	public boolean isExistsTable(String tableName) throws SqlRunException;
	 
	/**
	 * 
	 * Description: 通过模板创建新表
	 *
	 * @param newTableName 新表表名
	 * @param templeteTableName  模板表表明
	 * @return
	 */
	public boolean createTableByTemplete(String newTableName,final String templeteTableName) throws SqlRunException;
	
	 /**
     * 
     * Description: 通过一个查询语句，将数据插入指定表中
     *
     * @param tableName 表名
     * @param selectSql 标准ddl查询语句
     * @return
     */
    public boolean insertTableAsSelect(String tableName,String selectSql) throws SqlRunException;
    
    /**
     * 
     * Description: 通过一个查询语句，创建一个新表并且将数据插入
     *
     * @param tableName 表名
     * @param selectSql 标准ddl查询语句
     * @return
     */
    public boolean createTableAsSelect(String tableName,String selectSql) throws SqlRunException;
    
    /**
     * 给某表加入某列
     * @param tableName
     * @return boolean  是否成功
     */
    public boolean alterTable(String tableName,String columnName,final String columnType) throws SqlRunException;
    
    
    /**
     * 
     * Description: 查询一页数据集合
     *
     * @param selectSql 标准ddl查询语句
     * @param pageStart 开始页  
     * @param pageSize  每页多少条
     * @return
     */
    public List<Map<String,String>> queryForPage(String selectSql,Integer pageStart,Integer pageSize) throws SqlRunException;
    
    /**
     * 
     * Description: 查询共多少条数据
     *
     * @param selectSql 标准ddl查询语句
     * @param pageStart 开始页  
     * @param pageSize  每页多少条
     * @return
     */
    public Integer queryCount(String selectSql) throws SqlRunException;
    
    /**
     * 
     * Description: 删除表
     *
     * @param tableName 表名
     * @return
     */
    public boolean dropTable(String tableName) throws SqlRunException;
    
    /**
     * 
     * Description: 重命名表
     *
     * @param oldTableName 旧表名
     * @param newTableName 新表明
     * @return
     */
    public boolean renameTable(String oldTableName, String newTableName) throws SqlRunException;
}
