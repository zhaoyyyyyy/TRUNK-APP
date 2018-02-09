package com.asiainfo.biapp.si.loc.bd.list.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.bd.listinfo.entity.ListInfo;
import com.asiainfo.biapp.si.loc.bd.listinfo.entity.ListInfoId;

public interface IListInfoService  extends BaseService<ListInfo, String>{

	public Page<ListInfo> selectListInfoPageList(Page<ListInfo> page, ListInfo listInfoVo) throws BaseException;
	
	public List<ListInfo> selectListInfoList(ListInfo listInfoVo) throws BaseException;
	
	public void addListInfo(ListInfo listInfoVo) throws BaseException;
	
	public void modifyListInfo(ListInfo listInfoVo) throws BaseException;
	
	public void deleteListInfoById(ListInfoId id) throws BaseException;
}
