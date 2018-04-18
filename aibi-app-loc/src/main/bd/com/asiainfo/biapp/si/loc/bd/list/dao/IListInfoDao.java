package com.asiainfo.biapp.si.loc.bd.list.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.bd.list.entity.ListInfo;

/**
 * 清单清单操作dao
 * @author wanghf5
 * @since 2018-04-18
 *
 */
public interface IListInfoDao extends BaseDao<ListInfo, String>{
	
	public Page<ListInfo> selectListInfoPageList(Page<ListInfo> page, ListInfo listInfoVo);
	
	public List<ListInfo> selectListInfoList(ListInfo listInfoVo);
}
