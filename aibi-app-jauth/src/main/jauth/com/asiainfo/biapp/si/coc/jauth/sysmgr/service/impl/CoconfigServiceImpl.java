package com.asiainfo.biapp.si.coc.jauth.sysmgr.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.CoconfigDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Coconfig;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.CoconfigService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.CoconfigVo;

/**
 * @author zhangnan
 * @date 2017年9月27日 下午2:42:06
 */
@Service
@Transactional
public class CoconfigServiceImpl extends BaseServiceImpl<Coconfig, String> implements CoconfigService {

	@Autowired
	private CoconfigDao coconfigDao;

	@Override
	protected BaseDao<Coconfig, String> getBaseDao() {
		return coconfigDao;
	}

	/**
	 * 通过key拿配置
	 */
	@Override
	public Coconfig getCoconfigByKey(String configKey) {
		return coconfigDao.getCoconfigByKey(configKey);
	}
	@Override
	public List<Coconfig> getCoconfigByParentKey(String configKey){
		return coconfigDao.getCoconfigByParentKey(configKey);
	}
	@Override
	public JQGridPage<Coconfig> findCoconfigList(JQGridPage<Coconfig> page, CoconfigVo coconfigVo){
		return coconfigDao.findCoconfigList(page, coconfigVo);
	}
	@Override
	public void saveOrUpdate(Coconfig coconfig){
		coconfigDao.saveOrUpdate(coconfig);
	}

}
