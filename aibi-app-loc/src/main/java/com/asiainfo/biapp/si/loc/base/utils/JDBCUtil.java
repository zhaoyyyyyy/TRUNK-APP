package com.asiainfo.biapp.si.loc.base.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * jdbc工具类， 提供如下功能： 1， 获取前台库连接 
 * 2，获取后台库连接 
 * 3，在前台库执行 updata / insert / delete三种语句执行 方法 
 * 4，在后台库执行 updata / insert / delete 三种语句执行 方法 
 * 5，提供连接关闭资源关闭功能
 * 
 * @author wanghf5
 * @since 20170928
 *
 */
public final class JDBCUtil {
	private Logger logger = Logger.getLogger(this.getClass());

	private String web_url = null;
	private String web_user = null;
	private String web_driver = null;
	private String web_password = null;

	private String back_url = null;
	private String back_user = null;
	private String back_driver = null;
	private String back_password = null;

	private static JDBCUtil instance = null;

	private JDBCUtil() {
	}

	public static JDBCUtil getInstance() {
		if (instance == null) {
			synchronized (JDBCUtil.class) {
				if (instance == null) {
					instance = new JDBCUtil();
					instance.initJdbcPrepro();
				}

			}
		}
		return instance;
	}
	
	/**
	 * 初始化 前台库 配置数据
	 */
	public void initJdbcPrepro() {
//		web_url = PropertiesUtils.getProperties("CI_DATABASE_URL");
//		web_user = PropertiesUtils.getProperties("CI_DATABASE_USERNAME");
//		web_password = PropertiesUtils.getProperties("CI_DATABASE_PASSWORD");
//		web_driver = PropertiesUtils.getProperties("CI_DATABASE_DRIVER");
		
//		web_url = "jdbc:mysql://10.1.253.202:3306/opg";
//		web_user = "opg";
//		web_password = "opg";
//		web_driver = "com.mysql.jdbc.Driver";
		
//		back_url = PropertiesUtils.getProperties("CI_BACK_DATABASE_URL");
//		back_user = PropertiesUtils.getProperties("CI_BACK_DATABASE_USERNAME");
//		back_password = PropertiesUtils.getProperties("CI_BACK_DATABASE_PASSWORD");
//		back_driver = PropertiesUtils.getProperties("CI_BACK_DATABASE_DRIVER");
	}

	/**
	 * 获取 前台库jdbc 连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection getWebConnection() throws Exception {
		Class.forName(web_driver);
		return DriverManager.getConnection(web_url, web_user, web_password);
	}

	/**
	 * 获取 后台库jdbc 连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection getBackConnection() throws Exception {
		Class.forName(back_driver);
		return DriverManager.getConnection(back_url, back_user, back_password);
	}

	/**
	 * 在前台库执行 updata / insert / delete 语句
	 * @param sql
	 * @param parameters
	 * @throws Exception
	 */
	public void webExecuteUpdate(String sql, String[] parameters) throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			this.initJdbcPrepro();
			conn =this.getWebConnection();
			ps = conn.prepareStatement(sql);
			
			if (parameters != null) {
			    for (int i=0; i<parameters.length; i++) {
			        ps.setString(i+1, parameters[i]);
			    }
			}
			//执行语句
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			throw new RuntimeException(e.getMessage());
		}finally{
			this.free(conn, ps, null);
		}
		
	}
	
	/**
	 * 在前台库执行 updata / insert / delete 语句
	 * @param sql
	 * @param parameters
	 * @throws Exception
	 */
	public void backExecuteUpdate(String sql, String[] parameters) throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			this.initJdbcPrepro();
			conn =this.getBackConnection();
			ps = conn.prepareStatement(sql);
			
			if (parameters != null) {
			    for (int i=0; i<parameters.length; i++) {
			        ps.setString(i+1, parameters[i]);
			    }
			}
			//执行语句
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			throw new RuntimeException(e.getMessage());
		}finally{
			this.free(conn, ps, null);
		}
		
	}
	
	/**
	 * 释放 数据库连接资源
	 * @param conn
	 * @param st
	 * @param rs
	 */
	public void free(Connection conn, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(),e);
			} finally {
				if (st != null) {
					try {
						st.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						if (conn != null) {
							try {
								conn.close();
							} catch (SQLException e) {

								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
