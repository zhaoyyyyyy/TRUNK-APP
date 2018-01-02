/*
 * @(#)ApproveInfoServiceImpl.java
 *
 * CopyRight (c) 2017 鍖椾含浜氫俊鏅烘収鏁版嵁绉戞妧鏈夐檺鍏徃 淇濈暀鎵�鏈夋潈鍒┿��
 */

package com.asiainfo.biapp.si.loc.bd.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.bd.common.dao.IBackSqlDao;

/**
 * Title : BackHiveDaoImpl
 * <p/>
 * Description : 鍚庡彴搴揌IVE鐨勮闂帴鍙ｅ疄鐜扮被
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 鍖椾含浜氫俊鏅烘収鏁版嵁绉戞妧鏈夐檺鍏徃
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017骞�12鏈�25鏃�    hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2017骞�12鏈�25鏃�
 */

@Repository("backHiveDaoImpl")
public class BackHiveDaoImpl extends BaseBackDaoImpl implements IBackSqlDao{

	@Override
	public List<Map<String, String>> queryTableLikeTableName(String tableName) {
	    List<Map<String, String>> res = new ArrayList<>();
        String sql = new StringBuilder("show tables ").toString();
        List<Map<String, String>> datas = null;
        try{
            datas = this.executeResList(sql);
        }catch (Exception e){
            LogUtil.error("鎿嶄綔鍚庡彴搴撳嚭閿�", e);
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
	public List<Map<String, String>> queryTableColumn(String tableName) {
        List<Map<String, String>> res = new ArrayList<>();
        //desc tableName;
        String sql = new StringBuilder("desc ").append(tableName).toString();

        List<Map<String, String>> datas = null;
        try{
            datas = this.executeResList(sql);
        }catch (Exception e){
            LogUtil.error("鎿嶄綔鍚庡彴搴撳嚭閿�", e);
        }
        
        if (null != datas && !datas.isEmpty()) {
            LogUtil.debug("There are "+datas.size()+" cols in the table."+datas.toString());
            for (Map<String, String> map : datas) {
                if (!map.get("col_name").contains("#")) {
                    res.add(map);
                } else {    //鍦╤ive鐨剅s涓幓鎺変互[#]寮�濮嬬殑浠ヤ笅鐨勫垪
                    break;
                }
            }
        }
        
		return res;
	}

	@Override
	public boolean isExistsTable(String tableName) {
        boolean res = true;
        try{
            List<Map<String, String>> cols = this.queryTableColumn(tableName);
            if (cols.isEmpty()) {
                res = false;
            }
        }catch (Exception e){
            res = false;
            LogUtil.error("鎿嶄綔鍚庡彴搴撳嚭閿�", e);
        }
        
        return res;
	}

	@Override
	public boolean createTableByTemplete(String newTableName, String templeteTableName) {
        boolean res = true;
        String sql = new StringBuilder("CREATE TABLE ").append(newTableName).append(" AS select a.* from ")
            .append(templeteTableName).append(" a where 1=2").toString();
        
        try{
            res = this.executeResBoolean(sql);
        }catch (Exception e){
            res = false;
            LogUtil.error("鎿嶄綔鍚庡彴搴撳嚭閿�", e);
        }
        return res;
	}

	@Override
	public boolean insertTableAsSelect(String tableName, String selectSql) {
        boolean res = true;
        String sql = new StringBuilder("insert into table ").append(tableName).append(" ").append(selectSql).toString();

        try{
            res = this.executeResBoolean(sql);
        }catch (Exception e){
            res = false;
            LogUtil.error("鎿嶄綔鍚庡彴搴撳嚭閿�", e);
        }
        
        return res;
	}

	@Override
	public boolean createTableAsSelect(String tableName, String selectSql) {
	    boolean res = true;
        String sql = new StringBuilder("CREATE TABLE ").append(tableName).append(" AS ").append(selectSql).toString();

        try{
            res = this.executeResBoolean(sql);
        }catch (Exception e){
            res = false;
            LogUtil.error("鎿嶄綔鍚庡彴搴撳嚭閿�", e);
        }
        
		return res;
	}

	@Override
	public boolean alterTable(String tableName, String columnName, String columnType) {
	    //ALTER TABLE DIM_SCENE ADD columns(hfbcol int);
        boolean res = true;
        String sql = new StringBuilder("ALTER TABLE ").append(tableName).append(" ADD columns(").append(columnName)
                .append(" ").append(columnType).append(")").toString();

        try{
            res = this.executeResBoolean(sql);
        }catch (Exception e){
            res = false;
            LogUtil.error("鎿嶄綔鍚庡彴搴撳嚭閿�", e);
        }
        
        return res;
	}

	@Override
	public List<Map<String, String>> queryForPage(String selectSql, Integer pageStart, Integer pageSize) {
        int begin = (pageStart - 1) * pageSize;
        int end = begin + pageSize;
//        String keyColumn = PropertiesUtils.getProperties("RELATED_COLUMN");
        String keyColumn = "";
        selectSql = selectSql.trim();
        if (selectSql.startsWith("SELECT")){
            if (StringUtil.isNotEmpty(keyColumn) && selectSql.contains(keyColumn)) {
                selectSql = selectSql.replaceFirst("SELECT", "select row_number() over(order by "+keyColumn+") as rownum,");
            } else {
                selectSql = selectSql.replaceFirst("SELECT", "select row_number() over() as rownum,");
            }
        } else if (selectSql.startsWith("select")) {
            if (StringUtil.isNotEmpty(keyColumn) && selectSql.contains(keyColumn)) {
                selectSql = selectSql.replaceFirst("select", "select row_number() over(order by "+keyColumn+") as rownum,");
            } else {
                selectSql = selectSql.replaceFirst("select", "select row_number() over() as rownum,");
            }
        } else {
            throw new RuntimeException("sql澶у皬鍐欎笉缁熶竴锛�");
        }   
        
        String sql = new StringBuilder("select * from (").append(selectSql).append(") a where a.rownum >")
            .append(begin).append(" and a.rownum <=").append(end).toString();
        
        try{
            return this.executeResList(sql);
        }catch (Exception e){
            LogUtil.error("鎿嶄綔鍚庡彴搴撳嚭閿�", e);
        }
		return null;
	}

	@Override
	public Integer queryCount(String selectSql) {
        int rows = 0;
//        鍘熷洜鏄寜鐓х己鐪佹柟寮忔墦寮�鐨凴esultSet涓嶆敮鎸佺粨鏋滈泦cursor鐨勫洖婊�
//        濡傛灉鎯宠瀹屾垚涓婅堪鎿嶄綔锛岃鍦ㄧ敓鎴怱tatement瀵硅薄鏃跺姞鍏ュ涓嬩袱涓弬鏁帮細
        try{
            Connection connection = this.getBackConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs =  preparedStatement.executeQuery();

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
            LogUtil.error("鎿嶄綔鍚庡彴搴撳嚭閿�", e);
        }
		return rows;
	}

    private List<Map<String, String>> executeResList(String sql) {
        long s = System.currentTimeMillis();
        try{
            Connection connection = this.getBackConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet =  preparedStatement.executeQuery();

            LogUtil.debug(new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis()-s).append("ms."));
            
            return this.resultSetToList(resultSet);
        }catch (Exception e){
            LogUtil.error("鎿嶄綔鍚庡彴搴撳嚭閿�:"+sql, e);
        }
        return null;
    }

    private Boolean executeResBoolean(String sql) {
        Boolean res = true;
        long s = System.currentTimeMillis();
        try{
            Connection connection = this.getBackConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet =  preparedStatement.executeQuery();

//            LogUtil.info(sql);
            LogUtil.debug(new StringBuffer(sql).append(" cost:").append(System.currentTimeMillis()-s).append("ms."));
            
        }catch (Exception e){
            res = false;
            LogUtil.error("鎿嶄綔鍚庡彴搴撳嚭閿�:"+sql, e);
        }
        return res;
    }

    @Override
    public boolean dropTable(String tableName) {
        boolean res = true;
        //DROP TABLE pv_users;
        String sql = new StringBuilder("DROP TABLE ").append(tableName).toString();

        try{
            res = this.executeResBoolean(sql);
        }catch (Exception e){
            res = false;
            LogUtil.error("鎿嶄綔鍚庡彴搴撳嚭閿�", e);
        }
        
        return res;
    }

    @Override
    public boolean renameTable(String oldTableName, String newTableName) {
        boolean res = true;
        //ALTER TABLE oldTableName RENAME TO newTableName;
        String sql = new StringBuilder("ALTER TABLE ").append(oldTableName)
                .append(" RENAME TO ").append(newTableName).toString();

        try{
            res = this.executeResBoolean(sql);
        }catch (Exception e){
            res = false;
            LogUtil.error("鎿嶄綔鍚庡彴搴撳嚭閿�", e);
        }
        
        return res;
    }

	@Override
	public boolean createVerticalTable(String tableName, String columnName, String partionName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadDataToTabByPartion(String sql, String tableName, String partionValue) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertDataToTabByPartion(String sql, String tableName, String partionValue) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
