package com.asiainfo.biapp.si.loc.bd.list.dao.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.bd.list.dao.IListInfoDao;
import com.asiainfo.biapp.si.loc.bd.list.entity.ListInfo;
import com.asiainfo.biapp.si.loc.bd.list.vo.ListInfoVo;

/**
 * 清单清单操作dao实现类
 * @author wanghf5
 * @since 2018-04-18
 *
 */
@Repository
public class ListInfoDaoImpl extends BaseDaoImpl<ListInfo, String> implements IListInfoDao{

	
	@SuppressWarnings("unchecked")
    @Override
	public Page<ListInfo> selectListInfoPageList(Page<ListInfo> page, ListInfoVo listInfoVo) {
		Map<String, Object> reMap = fromBean(listInfoVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
	}

	@SuppressWarnings("unchecked")
    @Override
	public List<ListInfo> selectListInfoList(ListInfoVo listInfoVo) {
		Map<String, Object> reMap = fromBean(listInfoVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
	}
	
	public Map<String, Object> fromBean(ListInfoVo listInfoVo){
		Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from ListInfo l where 1=1 ");
        if (null != listInfoVo.getListInfoId() ) {
            if(StringUtil.isNotBlank(listInfoVo.getListInfoId().getCustomGroupId())){
                hql.append("and l.listInfo.customGroupId = :customGroupId ");
                params.put("customGroupId", listInfoVo.getListInfoId().getCustomGroupId());
            }
            if(StringUtil.isNotBlank(listInfoVo.getListInfoId().getDataDate())){
                hql.append("and l.listInfo.dataDate = :dataDate ");
                params.put("dataDate", listInfoVo.getListInfoId().getDataDate());
            }
        }
        if(null != listInfoVo.getCustomNum()){
        	hql.append("and l.customNum = :customNum ");
        	params.put("customNum", listInfoVo.getCustomNum());
        }
        if(null != listInfoVo.getDataStatus()){
        	hql.append("and l.dataStatus = :dataStatus ");
        	params.put("dataStatus", listInfoVo.getDataStatus());
        }
        if(StringUtil.isNotEmpty(listInfoVo.getDataStatuses())){
            Set<Integer> statusSet = new HashSet<Integer>();
            for(String status : listInfoVo.getDataStatuses().split(",")){
                statusSet.add(Integer.parseInt(status));
            }
            hql.append("and l.dataStatus in (:statusSet) ");
            params.put("statusSet", statusSet);
        }
        if(StringUtil.isNotEmpty(listInfoVo.getConfigId())){
            hql.append("and l.labelInfo.configId = :configId ");
            params.put("configId", listInfoVo.getConfigId());
        }
        if(StringUtil.isNotEmpty(listInfoVo.getLabelName())){
            hql.append("and l.labelInfo.labelName LIKE :labelName ");
            params.put("labelName", "%" +listInfoVo.getLabelName()+ "%");
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
	}

}
