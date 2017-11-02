package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao;

import java.util.List;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Coconfig;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.CoconfigVo;

/**
 * @describe
 * @author zhangnan
 * @date 2017年9月27日 下午2:49:04
 */
public interface CoconfigDao extends BaseDao<Coconfig, String> {
	/**
	 * 通过key拿配置
	 */
	public Coconfig getCoconfigByKey(String configKey);
	
	public List<Coconfig> getCoconfigByParentKey(String configKey);
	
	public JQGridPage<Coconfig> findCoconfigList(JQGridPage<Coconfig> page, CoconfigVo coconfigVo);

}
