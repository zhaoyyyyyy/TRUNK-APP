package com.asiainfo.biapp.si.coc.jauth.sysmgr.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;
import com.asiainfo.biapp.si.coc.jauth.frame.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.DicDataDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.DicData;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.DicDataService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.DicDataVo;

/**
 * @describe 数据字典业务逻辑层
 * @author zhougz
 * @date 2013-5-13
 */
@Service
@Transactional
public class DicDataServiceImpl extends BaseServiceImpl<DicData,String> implements 
	DicDataService {
	
	@Autowired
	private DicDataDao dicDataDao;

	/**
	 * 通过字典索引ID，找到字典内容
	 * @return
	 */
	public List<DicData> findDicDataListByDicID(Object dicId){
		this.findListByHql("from DicData d where d.dicid = ? ",new Object[]{dicId});
		return null;
	}

	/**
	 * @describe 全局校验dicCode
	 * @author wangzhao
	 * @param
	 * @date 2013-11-21
	 */
	@Override
	public List<DicData> findDataListByCode(String code) {
	    return this.findListByHql(" from DicData d where d.code = ?0 ", new Object[]{code});
	}
	
	public List<DicData> findDataListByDicCode(String dicCode) {
	    return this.findListByHql(" from DicData d where d.dicCode = ?0 ", new Object[]{dicCode});
	}

	@Override
	protected BaseDao<DicData, String> getBaseDao() {
		return dicDataDao;
	}

	public JQGridPage<DicData> findDicDataList(JQGridPage<DicData> page,DicDataVo dicDataVo) {
		return dicDataDao.findDicDataList(page, dicDataVo);
	}
}
