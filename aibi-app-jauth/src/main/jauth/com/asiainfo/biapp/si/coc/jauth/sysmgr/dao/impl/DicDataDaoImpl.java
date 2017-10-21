package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.Constants;
import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.DicDataDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.DicData;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.DicDataVo;

/**
 * @describe TODO
 * @author zhougz
 * @date 2013-5-24
 */
@Repository
public class DicDataDaoImpl extends BaseDaoImpl<DicData,String> implements DicDataDao {


	public List<DicData> findDataByDicCode(String dicCode) {
		return this.findListByHql(
				"from DicData b where b.dicCode = ? order by orderNum,code",
				dicCode);
	}

	public List<DicData> findDataParentByCode(String dicCode) {
		return this
				.findListByHql(
						"from DicData b where (b.parentId is null or b.parentId = '') and b.dicCode = ? order by code",
						dicCode);
	}
	/**
	 * 根据两个CODE查询
	 * 
	 */
	public List<DicData> findDataByDicCodeAndCode(String dicCode,String code) {
		return this.findListByHql(
				"from DicData b where b.dicCode = ? and b.code=?",
				dicCode,code);
	}
	
	/**
	 * 保存某个实体(没有ID)
	 * @param entity
	 * @return
	 */
    public Serializable create(DicData entity){
		return save(entity);
	}
    
    public void delete(String id) {
		super.delete(id);
	}

    
    /**
     * 更新某个实体
     * @param entity
     */
    public void update(DicData entity){
		super.update(entity);
	}
	
	
	public JQGridPage<DicData> findDicDataList(JQGridPage<DicData> page,DicDataVo dicDataVo) {
		//拼装hql 及参数
		Map<String,Object> params = new HashMap<String,Object>();
		StringBuilder hql = new StringBuilder("from DicData where 1=1 ");
		if(StringUtils.isNotBlank(dicDataVo.dataName)){
			hql.append(" and dataName like :dataName");
			params.put("dataName", "%"+dicDataVo.dataName+"%");
		}
		if(StringUtils.isNotBlank(dicDataVo.dicCode)){
			hql.append(" and dicCode = :dicCode");
			params.put("dicCode", dicDataVo.dicCode);
		}
		if(StringUtils.isNotBlank(dicDataVo.code)){
			hql.append(" and code like :code");
			params.put("code","%"+ dicDataVo.code+"%");
		}
		if(StringUtils.isNotBlank(dicDataVo.note)){
			hql.append(" and note like :note");
			params.put("note", "%"+dicDataVo.note+"%");
		}
		if(StringUtils.isNotBlank(page.getSortCol())){
			hql.append(" order by "+page.getSortCol()+" "+page.getSortOrder());
		}
		return (JQGridPage<DicData>) super.findPageByHql(page, hql.toString(), params);
	}
}
