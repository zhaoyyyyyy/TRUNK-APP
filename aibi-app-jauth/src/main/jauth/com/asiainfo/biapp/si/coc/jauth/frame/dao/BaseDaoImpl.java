/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.frame.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;

/**
 * @describe Hibernate持久层实现类
 * @author zhougz
 * @date 2013-5-22
 */
@Transactional
public class BaseDaoImpl<T, ID extends Serializable>  implements BaseDao<T,ID> {


	  
	@Autowired
	@PersistenceContext
	private EntityManager em;
	
//	private final EntityManager em;
//	public CommonDaoImpl(Class<T> domainClass, EntityManager em) {
//		super(domainClass, em);
//		this.em = em;
//	}

	
	
	HibernateDaoHelper daoHelper = new HibernateDaoHelper();
	public Logger logger = LoggerFactory.getLogger(super.getClass());
	//protected Class<T> entityClass = GenericsUtil.getGenericClass(super.getClass());
	
	protected Class<T> entityClass = null; 

	protected Class<T> getEntityClass() {
		
		Type t = getClass().getGenericSuperclass();
        if(t instanceof ParameterizedType){
            Type[] p = ((ParameterizedType)t).getActualTypeArguments();
            entityClass = (Class<T>)p[0];
        }
	        
	        
		return this.entityClass;
	}

	
	
	@Override
	public void delete(Object id) {
		T entity = this.get(id);
		em.remove(entity);
	}

	@Override
	public void deleteObject(T entity) {
		em.remove(entity);
	}

	
	@Override
	public void excuteSql(String sql,Object...params) {
		Query query = em.createNativeQuery(sql.toString()); 
		query = addParametersForArr(query, params);
		query.executeUpdate();
	}

//	@Override
//	public Serializable save(T entity) {
//		BaseEntity entitynew  = (BaseEntity)entity;
//		return entitynew.getId();
//	}

	@Override
	public void saveOrUpdate(T entity) {
		em.persist(entity);
	}

	@Override
	public void update(T entity) {
		em.persist(entity);
	}

	@Override
	public T get(Object id) {
		return (T) em.find(getEntityClass(), id);
		//return em.find(getEntityClass(),id);
	}
    
	@Override
	public Page<T> findPageByHql(Page<T> page, String hql, Map<String,Object> params) {
		final Map param = params;
		final String hqlF = hql;
		final int start = page.getStart();
		final int max = page.getPageSize();
		//final int max = page.getStart() + page.getPageSize();
		
		Query query = em.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(max);
		query = this.addParametersForMap(query, params);
		page.setData(query.getResultList());
		
		if (page.isAutoCount()) {
			Query queryCount = em.createQuery(daoHelper.buildCountQueryString(hql));
			queryCount = this.addParametersForMap(queryCount, params);
			page.setTotalCount(Integer.valueOf(queryCount.getSingleResult().toString()));
		}
		return page;
	}


	@Override
	public List<T> findListByHql(String hql, Object... args) {
		Query query = em.createQuery(hql);
		query = this.addParametersForArr(query, args);
		return query.getResultList();
	}

	@Override
	public List<T> findListByHql(String hql, Map<String,Object> params) {
		Query query = em.createQuery(hql);
		query = this.addParametersForMap(query, params);
		return query.getResultList();
	}

	@Override
	public List<T> findListBySql(String sql, Object... params) {
		Query query = em.createNativeQuery(sql);
		query = this.addParametersForArr(query, params);
		return query.getResultList();
	}

	@Override
	public List<T> findListBySql(String sql, Map<String,Object> params) {
		Query query = em.createNativeQuery(sql);
		query = this.addParametersForMap(query, params);
		return query.getResultList();
	}
	
	/**
	 * 
	 * @describe 获取所有实体
	 * @author ljs
	 * @date 2013-8-12
	 * @return
	 */
	@Override
	public List<T> getAll() {
		return em.createQuery(em.getCriteriaBuilder().createQuery(entityClass)).getResultList();
		//return this.findAll();
	}

    /**
     * @describe 得到系统时间
     * @author zhougz
     * @param
     * @date 2013-5-17
     */
	@Override
	public Date getSystemDate() {
		 return new Date();
	}
	
	/**
	 * 获取系统时间
	 * @return Timestamp
	 */
	 public Timestamp getSystemTime(){
		 Timestamp ts = new Timestamp(getSystemDate().getTime());
		 return ts;
	 }
	
	public void flush() {
		em.flush();
    }
	 
	/**
	 * @see 其他操作(增删改返回受影响的行数)
	 * @author ljs
	 * @date 2013-6-25
	 * @param hql hql语句
	 * @param args 参数列表(可以为null)
	 * @return Object
	 */
	@Override
	public Integer excuteHql(String hql, Object...params) {
		flush();
		Query query = em.createQuery(hql);
		query = this.addParametersForArr(query, params);
		return query.executeUpdate();
	}

	

	/**
	 * @see 从数据库中获得一个实体或一个值
	 * @author ljs
	 * @date 2013-6-25
	 * @param hql hql语句
	 * @param args 参数列表(可以为null)
	 * @return Object
	 */
	@Override
	public T findOneByHql(String hql, Object...args) {
		Query query = em.createQuery(hql);
		query = this.addParametersForArr(query, args);
		//return (T) query.getResultList();
		List<T> list = query.getResultList() ;
		if(list != null && list.size() == 1){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
	/**
	 * 根据条件获取查询到的记录数
	 * @param hql
	 * @param params
	 * @return
	 */
	@Override
	public int getCount(String hql, Object...params){
		Query queryCount = em.createQuery(daoHelper.buildCountQueryString(hql));
		queryCount = this.addParametersForArr(queryCount, params);
		return Integer.valueOf(queryCount.getSingleResult().toString());
	}


	/**
	 * 将键值型参数放入Query
	 * zhougz3 20170730
	 * @param query
	 * @param params
	 * @return
	 */
	private Query addParametersForMap(Query query ,Map<String,Object> params){
		if(params != null && params.size() > 0 ){
//			Iterator<String> it = params.keySet().iterator();
//			while(it.hasNext()){
//				String name = it.next();
//				query.setParameter(name, params.get(name));
//			}
			Set<Entry<String, Object>> s = params.entrySet();
			for(Entry<String, Object> e : s) {
				Class clazz = e.getValue().getClass();
				if(clazz.isArray()) {
					query.setParameter(e.getKey(), (Object[])e.getValue());
				}else if(e.getValue() instanceof Collection) {
					query.setParameter(e.getKey(), (Collection)e.getValue());
				}else {
					query.setParameter(e.getKey(), e.getValue());
				}
			}
		}
		return query;
	}
	
	
	/**
	 * 将数据型参数放入Query
	 * zhougz3 20170730
	 * @param query
	 * @param params
	 * @return
	 */
	private Query addParametersForArr(Query query ,Object[] params){
		
		if ((params != null) && (params.length > 0)) {
	      for (int i = 0; i < params.length; ++i) {
	    	if(   params[i] instanceof Map){
	    		 Iterator it = ((Map)params[i]).entrySet().iterator();
	    		  while (it.hasNext()) {
	    		   Map.Entry entry = (Map.Entry) it.next();
	    		   Object key = entry.getKey();
	    		   Object value = entry.getValue();
	    		   query.setParameter(key.toString(), value.toString());
	    		  }
	    	}else{
	    		 query.setParameter(i, params[i]);
	    	}
	      }
	    }
		
		return query;
	}



	@Override
	public Serializable save(T entity) {
		em.persist(entity);
		return null;
	}


}
