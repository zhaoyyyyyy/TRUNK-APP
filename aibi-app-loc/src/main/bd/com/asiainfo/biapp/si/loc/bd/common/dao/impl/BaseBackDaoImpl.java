
package com.asiainfo.biapp.si.loc.bd.common.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;

import com.asiainfo.biapp.si.loc.base.BaseConstants;
import com.asiainfo.biapp.si.loc.base.exception.SqlRunException;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;

/**
 * 一些后台库的公共方法
 * Title : BaseBackDaoImpl
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年12月25日    Administrator        Created</pre>
 * <p/>
 *
 * @author  zhougz3
 * @version 1.0.0.2017年12月25日
 */
public class BaseBackDaoImpl {
	
	
	/** 缓冲当前的schema */
    private static String SYS_BGDB_SCHEMA = null;
    private static final String REGEX_BEVEL = "/";
    

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
	 * 
	 * Description: 拿到后台数据DataSource
	 *	
	 * @return
	 * @throws SQLException 
	 */
	public Connection getBackConnection() throws SQLException{
	        
		//从配置缓存中拿到后台库的配置
		String driverClassName = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey(BaseConstants.SYS_BGDB_DRIVER);
		String url =CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey(BaseConstants.SYS_BGDB_URL);
		String username = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey(BaseConstants.SYS_BGDB_USERNAME);
		String password = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey(BaseConstants.SYS_BGDB_PASSWORD);
		DataSource dataSource = this.getDataSourceBuilder(driverClassName,url,username,password).build();
        return dataSource.getConnection();
    }
	
	
	/**
	 * 
	 * Description: 拿到DataSourceBuilder实例
	 *
	 * @param driverClassName 驱动类名
	 * @param url			     地址
	 * @param username		     用户名
	 * @param password		     密码
	 * @return
	 */
	public DataSourceBuilder getDataSourceBuilder(String driverClassName,String url,String username,String password){
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder;
    }
	

	/**
	 * 
	 * Description: 结果变成集合
	 *
	 * @param rs
	 * @return
	 * @throws java.sql.SQLException
	 */
    public static List<Map<String,String>> resultSetToList(ResultSet rs) throws java.sql.SQLException {
        ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等
        int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        Map<String,String> rowData = null;
        while (rs.next()) {
            rowData = new LinkedHashMap<String,String>(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                Object object = rs.getObject(i);
                if (object != null) {
                    rowData.put(md.getColumnName(i), object.toString());
                }else{
                	rowData.put(md.getColumnName(i), null);
                }
                
            }
            list.add(rowData);
        }
        return list;
    }
    
    
}
