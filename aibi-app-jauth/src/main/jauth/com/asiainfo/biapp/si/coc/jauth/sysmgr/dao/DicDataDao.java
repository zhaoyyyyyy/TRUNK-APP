/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao;

import java.util.List;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.DicData;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.DicDataVo;

/**
 * @author zhougz
 * @date 2013-5-24
 */
public interface DicDataDao extends BaseDao<DicData,String>{
	
	/**
	 * @describe 通过字典代码 找到数据
	 * @author zhougz
	 * @param
	 * @date 2013-6-25
	 */
	public List<DicData> findDataByDicCode(String dicCode);
	/**
	 * 
	 * @describe 查找父代码
	 * @author liukai
	 * @param
	 * @date 2013-7-24
	 */
	public List<DicData> findDataParentByCode(String dicCode) ;
	/**
	 * 根据两个CODE查询
	 * 
	 */
	public List<DicData> findDataByDicCodeAndCode(String dicCode,String code);
	
	
	public JQGridPage<DicData> findDicDataList(JQGridPage<DicData> page,DicDataVo dicDataVo);
	
}
