package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.DicDataDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.DicData;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.DicDataVo;

/**
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
		Map<String,Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder("from DicData where 1=1 ");
		if(StringUtils.isNotBlank(dicDataVo.getDataName())){
			hql.append(" and dataName like :dataName");
			params.put("dataName", "%"+dicDataVo.getDataName()+"%");
		}
		if(StringUtils.isNotBlank(dicDataVo.getDicCode())){
			hql.append(" and dicCode = :dicCode");
			params.put("dicCode", dicDataVo.getDicCode());
		}
		if(StringUtils.isNotBlank(dicDataVo.getCode())){
			hql.append(" and code like :code");
			params.put("code","%"+ dicDataVo.getCode()+"%");
		}
		if(StringUtils.isNotBlank(dicDataVo.getNote())){
			hql.append(" and note like :note");
			params.put("note", "%"+dicDataVo.getNote()+"%");
		}
		if(StringUtils.isNotBlank(page.getSortCol())){
			hql.append(" order by "+page.getSortCol()+" "+page.getSortOrder());
		}
		return (JQGridPage<DicData>) super.findPageByHql(page, hql.toString(), params);
	}
}
