package com.asiainfo.biapp.si.coc.jauth.sysmgr.service;

import java.util.List;

import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Coconfig;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.CoconfigVo;

/**
 * @describe 配置实体服务层
 * @author zhangnan
 * @date 2017年9月27日 下午2:36:07
 */
public interface CoconfigService extends BaseService<Coconfig, String> {

	public Coconfig getCoconfigByKey(String configKey);
	
	public List<Coconfig> getCoconfigByParentKey(String configKey);
	
	public JQGridPage<Coconfig> findCoconfigList(JQGridPage<Coconfig> page, CoconfigVo coconfigVo);

}
