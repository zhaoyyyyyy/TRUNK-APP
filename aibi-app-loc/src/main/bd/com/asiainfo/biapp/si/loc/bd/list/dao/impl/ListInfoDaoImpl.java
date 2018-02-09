package com.asiainfo.biapp.si.loc.bd.list.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.bd.list.dao.IListInfoDao;
import com.asiainfo.biapp.si.loc.bd.listinfo.entity.ListInfo;

public class ListInfoDaoImpl extends BaseDaoImpl<ListInfo, String> implements IListInfoDao{

	
	@Override
	public Page<ListInfo> selectListInfoPageList(Page<ListInfo> page, ListInfo listInfoVo) {
		Map<String, Object> reMap = fromBean(listInfoVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
	}

	@Override
	public List<ListInfo> selectListInfoList(ListInfo listInfoVo) {
		Map<String, Object> reMap = fromBean(listInfoVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
	}
	
	public Map<String, Object> fromBean(ListInfo listInfoVo){
		Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from ListInfo l where 1=1 ");
        if (StringUtil.isNotBlank(listInfoVo.getListInfoId().getCustomGroupId())) {
            hql.append("and l.customGroupId = :customGroupId and l.dataDate = :dataDate ");
            params.put("customGroupId", listInfoVo.getListInfoId().getCustomGroupId());
            params.put("dataDate", listInfoVo.getListInfoId().getDataDate());
        }
        if(null != listInfoVo.getCustomNum()){
        	hql.append("and l.customNum = :customNum ");
        	params.put("customNum", listInfoVo.getCustomNum());
        }
        if(null != listInfoVo.getDataStatus()){
        	hql.append("and l.dataStatus = :dataStatus ");
        	params.put("dataStatus", listInfoVo.getDataStatus());
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
	}

}
