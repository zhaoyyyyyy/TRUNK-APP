/*
 * @(#)IDimOrgLevelDaoImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.back.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.back.dao.IDimOrgLevelDao;
import com.asiainfo.biapp.si.loc.core.back.entity.DimOrgLevel;
import com.asiainfo.biapp.si.loc.core.back.vo.DimOrgLevelVo;

/**
 * Title : IDimOrgLevelDaoImpl
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月25日    zhangnan7        Created</pre>
 * <p/>
 *
 * @author  zhangnan7
 * @version 1.0.0.2018年1月25日
 */
@Repository
public class IDimOrgLevelDaoImpl extends BaseDaoImpl<DimOrgLevel, String> implements IDimOrgLevelDao {
    
    public Page<DimOrgLevel> selectDimOrgLevelPageList(Page<DimOrgLevel> page, DimOrgLevelVo dimOrgLevelVo){
        Map<String, Object> reMap = fromBean(dimOrgLevelVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    public List<DimOrgLevel> selectDimOrgLevelList(DimOrgLevelVo dimOrgLevelVo){
        Map<String, Object> reMap = fromBean(dimOrgLevelVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }
    
    public Map<String, Object> fromBean(DimOrgLevelVo dimOrgLevelVo) {
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from DimOrgLevel d where 1=1 ");
        if (null!=dimOrgLevelVo.getDimOrgLevelId()) {
            if(StringUtil.isNotBlank(dimOrgLevelVo.getDimOrgLevelId().getPriKey())&&StringUtil.isNotBlank(dimOrgLevelVo.getDimOrgLevelId().getOrgColumnName())){
                hql.append("and d.dimOrgLevelId = :dimOrgLevelId ");
                params.put("dimOrgLevelId",dimOrgLevelVo.getDimOrgLevelId());
            }else{
                if(StringUtil.isNotBlank(dimOrgLevelVo.getDimOrgLevelId().getPriKey())){
                    hql.append("and d.dimOrgLevelId.priKey = :priKey ");
                    params.put("priKey",dimOrgLevelVo.getDimOrgLevelId().getPriKey());
                }
                if(StringUtil.isNotBlank(dimOrgLevelVo.getDimOrgLevelId().getOrgColumnName())){
                    hql.append("and d.dimOrgLevelId.orgColumnName = :orgColumnName ");
                    params.put("orgColumnName",dimOrgLevelVo.getDimOrgLevelId().getOrgColumnName());
                }
            }
        }
        if (null!=dimOrgLevelVo.getLevelId()) {
            hql.append("and d.levelId = :levelId ");
            params.put("levelId",dimOrgLevelVo.getLevelId());
        }
        if (null!=dimOrgLevelVo.getSortNum()) {
            hql.append("and d.sortNum = :sortNum ");
            params.put("sortNum",dimOrgLevelVo.getSortNum());
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }

}
