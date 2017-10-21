package com.asiainfo.biapp.si.coc.jauth.sysmgr.utils;

import java.util.List;

import com.asiainfo.biapp.si.coc.jauth.frame.ssh.extend.SpringContextHolder;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.DicDataDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.DicData;

/**
 * @describe 数据字典工具
 * @author zhougz
 * @date 2013-5-13
 */
public class DataDicUtil {
	
	/**
	 * @describe 通过字典索引码 找到内容集合
	 * @author zhougz
	 * @param
	 * @date 2013-5-17
	 */
	public static List<DicData> getDicData(String dicCode){
		DicDataDao dicDataDao =  (DicDataDao) SpringContextHolder.getBean("dicDataDao");
		return dicDataDao.findDataByDicCode(dicCode);
	}
	/**
	 * 
	 * @describe 查找父代码
	 * @author liukai
	 * @param
	 * @date 2013-7-24
	 */
	public static List<DicData> getParentDicData(String dicCode){
		DicDataDao dicDataDao =  (DicDataDao) SpringContextHolder.getBean("dicDataDao");
		return dicDataDao.findDataParentByCode(dicCode);
	}
	/**
	 * @describe 通过字典索引码 找到字典内容实体
	 * @author ljs
	 * @param
	 * @date 2013-7-1
	 */
	public static DicData getDataByCode(String dicCode,String dataCode){
		List<DicData> dicDataList = getDicData(dicCode);
		if(dicDataList == null){
			return null;
		}
		for(DicData dicData : dicDataList){
			if(dicData.getCode().equals(dataCode)){
				return dicData;
			}
		}
		return null;
	}
	
	/**
	 * @describe 通过字典索引码 找到内容字符串
	 * @author ljs
	 * @param
	 * @date 2013-7-1
	 */
	public static String getCodeDesc(String dicCode,String dataCode){
		DicData dicData = getDataByCode(dicCode, dataCode);
		if(dicData == null){
			return "";
		}else{
			return getDataByCode(dicCode, dataCode).getDataName();
		}
	}
}
