package com.asiainfo.biapp.si.loc.bd.list.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.bd.list.ListInfoVo;
import com.asiainfo.biapp.si.loc.bd.list.entity.ListInfo;
import com.asiainfo.biapp.si.loc.bd.list.entity.ListInfoId;

/**
 * 清单生成service接口
 * @author wanghf5
 * @since 2018-04-18
 *
 */
public interface IListInfoService  extends BaseService<ListInfo, String>{

	public Page<ListInfo> selectListInfoPageList(Page<ListInfo> page, ListInfoVo listInfoVo) throws BaseException;
	
	public List<ListInfo> selectListInfoList(ListInfoVo listInfoVo) throws BaseException;
	
	public void addListInfo(ListInfo listInfoVo) throws BaseException;
	
	public void modifyListInfo(ListInfo listInfoVo) throws BaseException;
	
	public void deleteListInfoById(ListInfoId id) throws BaseException;
}
