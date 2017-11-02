package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.CoconfigDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Coconfig;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Resource;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Role;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.CoconfigVo;

/**
 * @describe
 * @author zhangnan
 * @date 2017年9月27日 下午2:49:21
 */
@Repository
public class CoconfigDaoImpl extends BaseDaoImpl<Coconfig, String> implements CoconfigDao {

	/**
	 * @describe 通过key拿到配置
	 */
	@Override
	public Coconfig getCoconfigByKey(String configKey) {
		return this.findOneByHql("from Coconfig c where c.configKey = ?0 ", configKey);
	}
	
	/**
	 * 通过父KEY取
	 */
	@Override
	public List<Coconfig> getCoconfigByParentKey(String configKey) {
		return this.findListByHql("from Coconfig c where c.parentKey = ?0 ", configKey);
	}

	/**
	 * @describe 保存
	 */
	@Override
	public void saveOrUpdate(Coconfig coconfig) {
		
		super.saveOrUpdate(coconfig);
	}
	
	
	/**
	 * 得到全部
	 */
	@Override
	public JQGridPage<Coconfig> findCoconfigList(JQGridPage<Coconfig> page, CoconfigVo coconfigVo) {
		Map<String, Object> params = new HashMap<>();
		StringBuffer hql = new StringBuffer("from Coconfig c where 1=1 ");
		if (StringUtils.isNotBlank(coconfigVo.getParentKey())) {
			hql.append("and c.parentKey = :parentKey ");
			params.put("parentKey", coconfigVo.getParentKey());
		}
		if (StringUtils.isNotBlank(coconfigVo.getConfigKey())) {
			hql.append("and c.configKey = :configKey ");
			params.put("configKey", coconfigVo.getConfigKey());
		}
		if (StringUtils.isNotBlank(coconfigVo.getConfigDesc())) {
			hql.append("and c.configDesc LIKE :configDesc ");
			params.put("configDesc","%"+ coconfigVo.getConfigDesc() + "%");
		}
		if(StringUtils.isNotBlank(page.getSortCol())){
			hql.append(" order by c."+page.getSortCol()+" "+page.getSortOrder());
		}else{
			hql.append(" order by c.configKey desc");
		}
		return (JQGridPage<Coconfig>) super.findPageByHql(page, hql.toString(), params);
	}

}
