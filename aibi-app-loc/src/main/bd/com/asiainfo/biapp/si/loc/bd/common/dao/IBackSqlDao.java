/*
 * @(#)IBackSqlDao.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.bd.common.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asiainfo.biapp.si.loc.base.exception.SqlRunException;

/**
 * 后台库操作接口(持久层)
 * Title : IBackSqlDao
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年12月26日    zhougz3        Created</pre>
 * <p/>
 *
 * @author  zhougz3
 * @version 1.0.0.2017年12月26日
 */

public interface IBackSqlDao {
    
    /**
     * Description: 查询当前后台库的schema
     *
     * @return 查询当前后台库的schema
     */
    public String getCurBackDbSchema() throws SqlRunException;
    
    /**
     * 查询后台库中的表
     * Description: 
     *
     * @param tableName 表名
     * @return
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
	 * @deprecated
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
    
    /**
     * 前台创建 纵表   一般有两个 场景 ： 1，某专区的一次性标签的存放；2，某专区临时客户群标签的存放；
     * @param tableName   纵表表名
     * @param columnName  只能是一个列名
     * @param partionDate  分区字段 1：日期分区字段名 
     * @param partionID		分区字段2：客户群id分区字段名
     * @return
     * @deprecated
     */
    public boolean createVerticalTable(String tableName,String columnName) throws SqlRunException;
    
    /**
     * 数据文件方式  指定分区 覆盖式导入指定 表
     * @param fileName  数据文件的绝对路径
     * @param tableName
     * @param partionDate
     * @return
     */
    public boolean loadDataToTabByPartion(String fileName, String tableName,String partionID) throws SqlRunException;
    
    /**
     * 表数据方式  指定分区 覆盖式导入 指定表
     * @param sql
     * @param tableName
     * @param partionDate
     * @return
     */
    public boolean insertDataToTabByPartion(String sql,String tableName,String partionID) throws SqlRunException;
    
    /**
     * 指定表名， 列名，以及列名里谁是主键 建表
     * @param tableName
     * @param columnName key: 列名;value :；列属性
     * @param primaryKey 针对ads 传入主键， 针对hive传入的是 分区字段，  这个参数里的字段名 必须在columnName 里存在
     * @return
     * @throws SqlRunException
     */
    public boolean createTableByName(String tableName,Map<String,String> columnName,List<String> primaryKey) throws SqlRunException;
    
    
    
}
