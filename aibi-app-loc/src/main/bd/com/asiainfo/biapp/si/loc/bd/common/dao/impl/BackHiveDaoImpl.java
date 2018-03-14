/*
 * @(#)BackHiveDaoImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.bd.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.base.exception.SqlRunException;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.bd.common.dao.IBackSqlDao;
import com.asiainfo.biapp.si.loc.bd.common.util.JDBCUtil;

/**
 * Title : BackHiveDaoImpl
 * <p/>
 * Description : 后台库HIVE的访问接口实现类
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年12月25日    hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2017年12月25日
 */

@Repository("backHiveDaoImpl")
public class BackHiveDaoImpl extends BaseBackDaoImpl implements IBackSqlDao{
	private Logger log = Logger.getLogger(BackHiveDaoImpl.class);
	


    public String getCurBackDbSchema() throws SqlRunException{
        return super.getCurBackDbSchema();
        
    }
	
	@Override
	public List<Map<String, String>> queryTableLikeTableName(String tableName) throws SqlRunException {
	    List<Map<String, String>> res = new ArrayList<>();
        String sql = new StringBuilder("show tables ").toString();
        List<Map<String, String>> datas = null;
        try{
            datas = this.executeResList(sql);
        }catch (Exception e){
            throw new SqlRunException(e.getMessage());
        }
        
        if (null != datas && !datas.isEmpty()) {
            LogUtil.debug("There are "+datas.size()+" tables in the database."+datas.toString());
            for (Map<String, String> map : datas) {
                if (map.get("tableName").contains(tableName)) {
                    res.add(map);
                }
            }
        }
        
        return res;
	}

	@Override
	public List<Map<String, String>> queryTableColumn(String tableName) throws SqlRunException {
        List<Map<String, String>> res = new ArrayList<>();
        Map<String,String> rsMap = null;
        //desc tableName;
        String sql = new StringBuilder("desc ").append(tableName).toString();

        List<Map<String, String>> datas = null;
        try{
            datas = this.executeResList(sql);
        }catch (Exception e){
            throw new SqlRunException(e.getMessage());
        }
        
        if (null != datas && !datas.isEmpty()) {
            LogUtil.debug("There are "+datas.size()+" cols in the table."+datas.toString());
            for (Map<String, String> map : datas) {
                if (!map.get("col_name").contains("#")) {
                	rsMap = new HashMap<String,String>();
                	rsMap.put("COLUMN_NAME", map.get("col_name"));
                	rsMap.put("DATA_TYPE", map.get("data_type"));
                	rsMap.put("COLUMN_COMMENT", map.get("col_name"));
//                	if(StringUtils.isNoneBlank(map.get("comment"))){
//                		rsMap.put("COLUMN_COMMENT", map.get("comment"));
//                	}else{
//                		
//                	}
                	
                	res.add(rsMap);
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
        try{
            List<Map<String, String>> cols = this.queryTableColumn(super.getCurBackDbSchema()+"."+tableName);
            if (cols.isEmpty()) {
                res = false;
            }
        }catch (Exception e){
            res = false;
           // throw new SqlRunException(e.getMessage());
        }
        
        return res;
	}

	@Override
	public boolean createTableByTemplete(String newTableName, String templeteTableName) throws SqlRunException {
        boolean res = true;
        String sql = new StringBuilder("CREATE TABLE ").append(newTableName).append(" AS select a.* from ")
            .append(templeteTableName).append(" a where 1=2").toString();
        
        try{
            res = this.executeResBoolean(sql);
        }catch (Exception e){
            res = false;
            throw new SqlRunException(e.getMessage());
        }
        return res;
	}

	@Override
	public boolean insertTableAsSelect(String tableName, String selectSql) throws SqlRunException {
        boolean res = true;
        String sql = new StringBuilder("insert into table ").append(tableName).append(" ").append(selectSql).toString();

        try{
            res = this.executeResBoolean(sql);
        }catch (Exception e){
            res = false;
            throw new SqlRunException(e.getMessage());
        }
        
        return res;
	}

	@Override
	public boolean createTableAsSelect(String tableName, String selectSql) throws SqlRunException {
	    boolean res = true;
        String sql = new StringBuilder("CREATE TABLE ").append(tableName).append(" AS ").append(selectSql).toString();

        try{
            res = this.executeResBoolean(sql);
        }catch (Exception e){
            res = false;
            throw new SqlRunException(e.getMessage());
        }
        
		return res;
	}

	@Override
	public boolean alterTable(String tableName, String columnName, String columnType) throws SqlRunException {
	    //ALTER TABLE DIM_SCENE ADD columns(hfbcol int);
        boolean res = true;
        String sql = new StringBuilder("ALTER TABLE ").append(tableName).append(" ADD columns(").append(columnName)
                .append(" ").append(columnType).append(")").toString();

        try{
            res = this.executeResBoolean(sql);
        }catch (Exception e){
            res = false;
            throw new SqlRunException(e.getMessage());
        }
        
        return res;
	}

	@Override
	public List<Map<String, String>> queryForPage(String selectSql, Integer pageStart, Integer pageSize) throws SqlRunException {
        if (pageStart < 1) {
            pageStart = 1;
        }
	    int begin = (pageStart - 1) * pageSize;
        int end = begin + pageSize;
        selectSql = selectSql.trim();
        String orderByStr = null;
		if (selectSql.contains("order by") || selectSql.contains("ORDER BY")) {
			int start = selectSql.indexOf("order by");
			if (start < 0) {
				start = selectSql.indexOf("ORDER BY");
			}
			orderByStr = selectSql.substring(start, selectSql.length());
			selectSql = selectSql.replace(orderByStr, "");
		}
        if (selectSql.startsWith("SELECT")){
            if (StringUtil.isNotEmpty(orderByStr)) {
                selectSql = selectSql.replaceFirst("SELECT", "select row_number() over("+orderByStr+") as rownum,");
            } else {
                selectSql = selectSql.replaceFirst("SELECT", "select row_number() over() as rownum,");
            }
        } else if (selectSql.startsWith("select")) {
            if (StringUtil.isNotEmpty(orderByStr)) {
                selectSql = selectSql.replaceFirst("select", "select row_number() over("+orderByStr+") as rownum,");
            } else {
                selectSql = selectSql.replaceFirst("select", "select row_number() over() as rownum,");
            }
        } else {
            throw new RuntimeException("sql大小写不统一！");
        }   
        
        String sql = new StringBuilder("select * from (").append(selectSql).append(") a where a.rownum >")
            .append(begin).append(" and a.rownum <=").append(end).toString();
        
        LogUtil.debug("sql:" + sql);
        
        try{
            return this.executeResList(sql);
        }catch (Exception e){
            throw new SqlRunException(e.getMessage());
        }
	}
    public List<Map<String, String>> queryBySql(String sql) throws SqlRunException{
        LogUtil.debug("sql:" + sql);
        
        try{
            return this.executeResList(sql);
        }catch (Exception e){
            throw new SqlRunException(e.getMessage());
        }
    }

	@Override
	public Integer queryCount(String selectSql) throws SqlRunException {
        int rows = 0;
//      原因是按照缺省方式打开的ResultSet不支持结果集cursor的回滚
//      如果想要完成上述操作，要在生成Statement对象时加入如下两个参数：
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try{
            connection = this.getBackConnection();
            preparedStatement = connection.prepareStatement(selectSql);
            rs =  preparedStatement.executeQuery();

            if(rs != null && rs.next()) {
                if (selectSql.contains("count") || selectSql.contains("COUNT")) {
                    rows = rs.getInt(1); 
                } else {
                    while(rs.next()){
                        rows += 1;
                    }
                }
            }
        }catch (Exception e){
            throw new SqlRunException(e.getMessage());
        }finally{
        	JDBCUtil.getInstance().free(connection, preparedStatement, rs);
        }
		return rows;
	}

    private List<Map<String, String>> executeResList(String sql) throws SqlRunException {
        long s = System.currentTimeMillis();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
        	conn = this.getBackConnection();
        	st = conn.prepareStatement(sql);
            rs = st.executeQuery();

            LogUtil.debug(new StringBuffer(sql).append(" cost:").append((System.currentTimeMillis()-s)/1000.0).append(" s."));
            
            return this.resultSetToList(rs);
        }catch (Exception e){
            throw new SqlRunException(e.getMessage());
        }finally{
        	JDBCUtil.getInstance().free(conn, st, rs);
        }
    }

    private Boolean executeResBoolean(String sql) throws SqlRunException {
        Boolean res = true;
        long s = System.currentTimeMillis();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            conn = this.getBackConnection();
            //st = conn.prepareStatement(sql);
            //rs =  st.executeQuery();
            boolean execute = conn.createStatement().execute(sql);
            LogUtil.debug(new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis()-s).append("ms."));
            
        }catch (Exception e){
            res = false;
            throw new SqlRunException(e.getMessage()+"--errorSql:"+sql);
        }finally{
        	JDBCUtil.getInstance().free(conn, st, rs);
        }
        return res;
    }

    @Override
    public boolean dropTable(String tableName) throws SqlRunException {
        boolean res = true;
        //DROP TABLE pv_users;
        String sql = new StringBuilder("DROP TABLE ").append(tableName).toString();

        try{
            res = this.executeResBoolean(sql);
        }catch (Exception e){
            res = false;
            throw new SqlRunException(e.getMessage());
        }
        
        return res;
    }

    @Override
    public boolean renameTable(String oldTableName, String newTableName) throws SqlRunException {
        boolean res = true;
        //ALTER TABLE oldTableName RENAME TO newTableName;
        String sql = new StringBuilder("ALTER TABLE ").append(oldTableName)
                .append(" RENAME TO ").append(newTableName).toString();

        try{
            res = this.executeResBoolean(sql);
        }catch (Exception e){
            res = false;
            throw new SqlRunException(e.getMessage());
        }
        
        return res;
    }

	@Override
	public boolean createVerticalTable(String tableName,String columnName) throws SqlRunException{
		StringBuffer sql = new StringBuffer();
		sql.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (").append(columnName).append(" string )  PARTITIONED BY (");
		sql.append(LabelInfoContants.KHQ_CROSS_ID_PARTION).append(" string) stored as ").append(this.Tab_Format);
//		String sql ="CREATE TABLE IF NOT EXISTS "+tableName+" ("+
//					columnName+" string )  PARTITIONED BY ("+LabelInfoContants.KHQ_CROSS_ID_PARTION+" string) stored as " + this.Tab_Format;
		log.debug(" ----------------------  BackHiveDaoImpl.createVerticalTable  sql=" + sql);
		return this.executeResBoolean(sql.toString());
	}

	@Override
	public boolean loadDataToTabByPartion(String fileName, String tableName,String partionID) throws SqlRunException{
		StringBuffer sqlstr = new StringBuffer();
		sqlstr.append("load data local inpath ").append(fileName);
		sqlstr.append(" OVERWRITE into table ").append(tableName);
		sqlstr.append(" PARTITION (").append(LabelInfoContants.KHQ_CROSS_ID_PARTION);
		sqlstr.append(" = ").append(partionID).append(") ");
		log.debug(" ----------------------  BackHiveDaoImpl.loadDataToTabByPartion  sql=" + sqlstr.toString());
		return this.executeResBoolean(sqlstr.toString());
	}

	@Override
	public boolean insertDataToTabByPartion(String sql,String tableName,String partionID) throws SqlRunException{
		StringBuffer sqlstr = new StringBuffer();
		sqlstr.append("insert overwrite TABLE ").append(super.getCurBackDbSchema()).append(".").append(tableName);
		sqlstr.append(" PARTITION (").append(LabelInfoContants.KHQ_CROSS_ID_PARTION);
		sqlstr.append(" = '").append(partionID).append("') ").append(sql);
		log.debug(" ----------------------  BackHiveDaoImpl.insertDataToTabByPartion  sql=" + sqlstr.toString());
		return this.executeResBoolean(sqlstr.toString());
	}

	@Override
	public boolean createTableByName(String tableName, Map<String, String> columnName, List<String> primaryKey)
			throws SqlRunException {
		StringBuffer primaryKeyStr = new StringBuffer();
		boolean ifPartition = false;
		//参数判断，表名，列名 主键不能为空； 主键必须存在与列名里
		if(StringUtils.isNoneBlank(tableName)&&null != columnName && !columnName.isEmpty()){
			if(null != primaryKey && !primaryKey.isEmpty()){
				ifPartition = true;
				for(String mykey:primaryKey){
					primaryKeyStr.append(mykey).append(" ").append(columnName.get(mykey)).append(",");
				}
				
			}
			
		}else{
			return false;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE ").append(super.getCurBackDbSchema()).append(".").append(tableName);
		sb.append("(");
		Set<String> keySet = columnName.keySet();
		for(String colName : keySet){
			if(primaryKey.contains(colName)){
				continue;
			}
			sb.append(colName).append(" ").append(columnName.get(colName)).append(")");
		}
		
		if(ifPartition){
			sb.append(" PARTITIONED BY (").append(primaryKeyStr.toString().substring(0,primaryKeyStr.toString().length()-1)).append(") stored as ").append(this.Tab_Format);
		}
		
		
		try{
            return this.executeResBoolean(sb.toString());
        }catch (Exception e){
            throw new SqlRunException(e.getMessage()+"--errorSql:"+sb.toString());
        }
	}
	
	
}
