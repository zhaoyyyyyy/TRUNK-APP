
package com.asiainfo.biapp.si.loc.bd.common.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;

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
	
	/**
	 * 
	 * Description: 拿到后台数据DataSource
	 *	
	 * @return
	 * @throws SQLException 
	 */
	public Connection getBackConnection() throws SQLException{
	        
		//从配置缓存中拿到后台库的配置
		String driverClassName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://10.1.245.175:3306/cocdev";
		String username = "cocdev";
		String password = "cocdev";
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
    public static List resultSetToList(ResultSet rs) throws java.sql.SQLException {
        ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等
        int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数
        List list = new ArrayList();
        Map rowData = new LinkedHashMap();
        while (rs.next()) {
            rowData = new HashMap(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                Object object = rs.getObject(i);
//                if (object != null) {
//                    object = object.toString();
//                }
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }
}