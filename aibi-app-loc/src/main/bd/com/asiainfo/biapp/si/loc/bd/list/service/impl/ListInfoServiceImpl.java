package com.asiainfo.biapp.si.loc.bd.list.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.bd.common.util.JDBCUtil;
import com.asiainfo.biapp.si.loc.bd.list.dao.IListInfoDao;
import com.asiainfo.biapp.si.loc.bd.list.service.IListInfoService;
import com.asiainfo.biapp.si.loc.bd.listinfo.entity.ListInfo;
import com.asiainfo.biapp.si.loc.bd.listinfo.entity.ListInfoId;

@Service
@Transactional
public class ListInfoServiceImpl extends BaseServiceImpl<ListInfo, String>  implements IListInfoService{
	
	@Autowired
	private IListInfoDao listInfoDao;

	@Override
	public Page<ListInfo> selectListInfoPageList(Page<ListInfo> page, ListInfo listInfoVo) throws BaseException {
		if(null==page){
			throw new ParamRequiredException("selectListInfoPageList :page is null!");
		}
		return listInfoDao.selectListInfoPageList(page, listInfoVo);
	}

	@Override
	public List<ListInfo> selectListInfoList(ListInfo listInfoVo) throws BaseException {
		if(null==listInfoVo){
			throw new ParamRequiredException("selectListInfoPageList :page is null!");
		}
		return listInfoDao.selectListInfoList(listInfoVo);
	}

	@Override
	public void addListInfo(ListInfo listInfoVo) throws BaseException {
		if(null==listInfoVo){
			throw new ParamRequiredException("addListInfo :page is null!");
		}
		super.save(listInfoVo);
	}

	@Override
	public void modifyListInfo(ListInfo listInfoVo) throws BaseException {
		super.saveOrUpdate(listInfoVo);
		
	}

	@Override
	public void deleteListInfoById(ListInfoId id) throws BaseException {
		
	}

	@Override
	protected BaseDao<ListInfo, String> getBaseDao() {
		return listInfoDao;
	}

}
