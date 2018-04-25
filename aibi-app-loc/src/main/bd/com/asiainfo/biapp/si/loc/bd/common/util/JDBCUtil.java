package com.asiainfo.biapp.si.loc.bd.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.asiainfo.biapp.si.loc.base.BaseConstants;
import com.asiainfo.biapp.si.loc.base.config.DataSourceConfig;
import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;

public final class JDBCUtil {
	
	private Logger log = Logger.getLogger(this.getClass());

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
//		LocDataSource ds = (LocDataSource)SpringContextHolder.getBean("locDataSource");
		DataSourceConfig dataSource =  (DataSourceConfig)SpringContextHolder.getBean("dataSourceConfig");
		web_url = dataSource.dataSource().getUrl();
		web_user = dataSource.dataSource().getUsername();
		web_password = dataSource.dataSource().getPassword();
		web_driver = dataSource.dataSource().getDriverClassName();
		
		LogUtil.info("-----------------------------  web_url = " + web_url);
		LogUtil.info("-----------------------------  web_user = " + web_user);
		LogUtil.info("-----------------------------  web_password = " + web_password);
		LogUtil.info("-----------------------------  web_driver = " + web_driver);
//		log.debug("-----------------------------  web_url = " + web_url);
//		log.debug("-----------------------------  web_user = " + web_user);
//		log.debug("-----------------------------  web_password = " + web_password);
//		log.debug("-----------------------------  web_driver = " + web_driver);
		
		back_url = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey(BaseConstants.SYS_BGDB_URL);
		back_user = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey(BaseConstants.SYS_BGDB_USERNAME);
		back_password = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey(BaseConstants.SYS_BGDB_PASSWORD);
		back_driver = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey(BaseConstants.SYS_BGDB_DRIVER);
		
//		log.debug("-----------------------------  back_url = " + back_url);
//		log.debug("-----------------------------  back_user = " + back_user);
//		log.debug("-----------------------------  back_password = " + back_password);
//		log.debug("-----------------------------  back_driver = " + back_driver);
//		
//		back_url = "jdbc:hive2://10.19.58.81:10015/default";
//		back_user = "coc";
//		back_password = "coc";
//		back_driver = "org.apache.hive.jdbc.HiveDriver";
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
//			logger.error(e.getMessage(),e);
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
			log.error(e.getMessage(),e);
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
				log.error(e.getMessage(),e);
			} finally {
				if (st != null) {
					try {
						st.close();
					} catch (SQLException e) {
						log.error(e.getMessage(),e);
					} finally {
						if (conn != null) {
							try {
								conn.close();
							} catch (SQLException e) {
								log.error(e.getMessage(),e);
							}
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			JDBCUtil.getInstance().backExecuteUpdate("create table backJdbcTest(id string,age int)", null);
		} catch (Exception e) {
		}

	}

}
