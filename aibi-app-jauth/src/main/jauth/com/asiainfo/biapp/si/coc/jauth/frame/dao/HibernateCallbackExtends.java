/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.frame.dao;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;

/**
 * @author zhougz
 * @date 2013-5-27
 */
public class HibernateCallbackExtends implements HibernateCallback{

	
	private Page page ;
	private String sql;
	private Object[] params;
	private boolean isUpdate;
	private HibernateDaoHelper daoHelper;
	public HibernateCallbackExtends (Page page,String sql,Object[] params,HibernateDaoHelper daoHelper){
		this.page = page;
		this.sql = sql;
		this.params = params;
		this.daoHelper = daoHelper;
	}
	
	public HibernateCallbackExtends (String sql,Object[] params,HibernateDaoHelper daoHelper){
		this.page = null;
		this.sql = sql;
		this.params = params;
		this.daoHelper = daoHelper;
	}
	
	public HibernateCallbackExtends (String sql,Object[] params,HibernateDaoHelper daoHelper,boolean isUpdate){
		this.sql = sql;
		this.params = params;
		this.daoHelper = daoHelper;
		this.isUpdate = isUpdate;
	}
	
	
	@Override
	public Object doInHibernate(Session session) throws HibernateException,
			SQLException {
		if(isUpdate){
			return excuteSql( session.createSQLQuery(sql), params);
		}else if(page != null){
			SQLQuery countQuery = null;
            if (page.isAutoCount()) {
              String countSql = this.daoHelper.buildCountQueryString(sql);
              countQuery = session.createSQLQuery(countSql);
            }
            return this.getPage(page, countQuery, session.createSQLQuery(sql), params);
		}else{
	         return this.getList( session.createSQLQuery(sql), params);
			
		}
	}

	protected Integer excuteSql( Query resultQuery, Object[] values)
	  {
	      Query query = this.daoHelper.getQuery(resultQuery, values);
	    return query.executeUpdate();
	  }
	
	protected List<?> getList(Query resultQuery, Object[] values)
	  {
	      Query query = this.daoHelper.getQuery(resultQuery, values);
	    return query.list();
	  }
	
	  protected Page<?> getPage(Page<?> page, Query countQuery, Query resultQuery, Object[] values)
	  {
	    if (page.isAutoCount()) {
	      try {
	        page.setTotalCount(this.daoHelper.countResult(countQuery, values));
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	    }
	    if (page.getTotalCount() > 0) {
	      Query query = this.daoHelper.getQuery(resultQuery, values);
	      query.setFirstResult(page.getStart());
	      query.setMaxResults(page.getPageSize());
	      page.setData(query.list());
	    }
	    return page;
	  }
}
