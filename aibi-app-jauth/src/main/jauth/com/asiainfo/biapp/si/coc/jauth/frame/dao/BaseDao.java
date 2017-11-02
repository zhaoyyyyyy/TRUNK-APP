/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.frame.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;

/**
 * @author zhougz
 * @date 2013-5-22
 */
@NoRepositoryBean
public interface BaseDao<T,  ID extends Serializable> {
	/**
	 * 保存某个实体(没有ID)
	 * @param entity
	 * @return
	 */
    public Serializable save(T entity);

    /**
	 * 查看是否有主键从而角色是否更新
	 * @param entity
	 * @return
	 */
    public void saveOrUpdate(T entity);
    
    /**
     * 更新某个实体
     * @param entity
     */
    public void update(T entity);

    /**
     * 通过主键删除实体
     * @param id
     */
    public void delete(Object id);

    /**
     * 删除某个实体
     * @param entity
     */
    public void deleteObject(T entity);

    /**
     * 通过主键得到实体
     * @param id
     * @return
     */
    public T get(Object id);
    
    /**
     * 执行SQL
     * @param sql
     */
    public void excuteSql(String sql,Object... params);
    
    /**
     * 通过参数查询集合
     * @param hql
     * @param args
     * @return
     */
    public List<T> findListByHql(String hql,Object... args);
    
    /**
     * 通过HQL查询集合
     * @param hql
     * @param params
     * @return
     */
    public List<T> findListByHql(String hql,Map<String,Object> params);
    
    /**
     * 通过SQL查询集合
     * @param sql
     * @param params
     * @return
     */
    public List<T> findListBySql(String sql,Object... args);
    
    /**
     * 通过SQL查询集合
     * @param sql
     * @param params
     * @return
     */
    public List<T> findListBySql(String sql,Map<String,Object> params);
    /**
     * 通过SQL以及分页组件是查询分页 
     * @param page
     * @param hql
     * @param params
     * @return
     */
    public Page<T> findPageByHql(Page<T> page,String hql,Map<String,Object> params);
    
    

    
    /**
     * @describe 得到系统时间
     * @author zhougz
     * @param
     * @date 2013-5-17
     */
    public Date getSystemDate();
    
    /**
     * 获取系统时间
     * @author ljs
     * @param
     * @date 2013-6-24
     */
    public Timestamp getSystemTime();
    
	/**
	 * @see 其他操作(增删改返回受影响的行数)
	 * @author ljs
	 * @date 2013-6-25
	 * @param hql hql语句
	 * @param args 参数列表(可以为null)
	 * @return Object
	 */
	public Integer excuteHql(String hql, Object...args);
	
	/**
	 * @see 从数据库中获得一个实体或一个值
	 * @author ljs
	 * @date 2013-6-25
	 * @param hql hql语句
	 * @param args 参数列表(可以为null)
	 * @return Object
	 */
	public T findOneByHql(String hql, Object...args);
	
	/**
	 * 根据条件获取查询到的记录数
	 * @param hql
	 * @param params
	 * @return
	 */
	public int getCount(String hql, Object...params);
	
	/**
	 * 
	 * @describe 获取所有实体
	 * @author ljs
	 * @date 2013-8-12
	 * @return
	 */
	public List<T> getAll();
	
}
