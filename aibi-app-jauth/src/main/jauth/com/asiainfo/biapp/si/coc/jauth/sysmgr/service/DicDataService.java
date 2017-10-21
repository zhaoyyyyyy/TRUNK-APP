package com.asiainfo.biapp.si.coc.jauth.sysmgr.service;

import java.util.List;

import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.DicData;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.DicDataVo;

public interface DicDataService extends BaseService<DicData,String>{
	/**
	 * 通过字典索引ID，找到字典内容
	 * @return
	 */
	public List<DicData> findDicDataListByDicID(Object dicId);

	/**
	 * @describe 全局校验dicCode
	 * @author wangzhao
	 * @param
	 * @date 2013-11-21
	 */
	public List<DicData> findDataListByCode(String code);
	
	public List<DicData> findDataListByDicCode(String dicCode);
	
	public JQGridPage<DicData> findDicDataList(JQGridPage<DicData> page,DicDataVo dicDataVo);
}
