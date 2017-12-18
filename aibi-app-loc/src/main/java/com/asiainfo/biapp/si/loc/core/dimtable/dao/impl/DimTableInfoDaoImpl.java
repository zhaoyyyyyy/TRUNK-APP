/*
 * @(#)DimTableInfoDaoImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.dimtable.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.dimtable.dao.IDimTableInfoDao;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;
import com.asiainfo.biapp.si.loc.core.dimtable.vo.DimTableInfoVo;

/**
 * Title : DimTableInfoDaoImpl
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年11月27日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2017年11月27日
 */
@Repository
public class DimTableInfoDaoImpl extends BaseDaoImpl<DimTableInfo, String> implements IDimTableInfoDao{

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param DimTableInfo
     * @return
     */
    public Page<DimTableInfo> selectDimTableInfoPageList(Page<DimTableInfo> page, DimTableInfoVo dimTableInfoVo) {
        Map<String, Object> reMap = fromBean(dimTableInfoVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    /**
     * 根据条件查询列表
     *
     * @param DimTableInfo
     * @return
     */
    public List<DimTableInfo> selectDimTableInfoList(DimTableInfoVo dimTableInfoVo) {
        Map<String, Object> reMap = fromBean(dimTableInfoVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }
    
    public Map<String, Object> fromBean(DimTableInfoVo dimTableInfoVo){
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from DimTableInfo d where 1=1 ");
        if(StringUtil.isNoneBlank(dimTableInfoVo.getDimId())){
            hql.append("and d.dimId = :dimId ");
            params.put("dimId", dimTableInfoVo.getDimId());
        }
        if(StringUtil.isNoneBlank(dimTableInfoVo.getDimTableName())){
            hql.append("and d.dimTableName LIKE :dimTableName ");
            params.put("dimTableName","%" + dimTableInfoVo.getDimTableName()+"%");
        }
        if(StringUtil.isNoneBlank(dimTableInfoVo.getDimComment())){
            hql.append("and d.dimComment = :dimComment ");
            params.put("dimComment", dimTableInfoVo.getDimComment());
        }
        if(StringUtil.isNoneBlank(dimTableInfoVo.getDimCodeCol())){
            hql.append("and d.dimCodeCol = :dimCodeCol ");
            params.put("dimCodeCol", dimTableInfoVo.getDimCodeCol());
        }
        if(StringUtil.isNoneBlank(dimTableInfoVo.getDimValueCol())){
            hql.append("and d.dimValueCol = :dimValueCol ");
            params.put("dimValueCol", dimTableInfoVo.getDimValueCol());
        }
        if(StringUtil.isNoneBlank(dimTableInfoVo.getConfigId())){
            hql.append("and d.configId = :configId ");
            params.put("configId", dimTableInfoVo.getConfigId());
        }
        if(StringUtil.isNoneBlank(dimTableInfoVo.getCodeColType())){
            hql.append("and d.codeColType = :codeColType ");
            params.put("codeColType", dimTableInfoVo.getCodeColType());
        }
        if(StringUtil.isNoneBlank(dimTableInfoVo.getDimWhere())){
            hql.append("and d.dimWhere = :dimWhere ");
            params.put("dimWhere", dimTableInfoVo.getDimWhere());
        }
        if (null != dimTableInfoVo.getCreateTime()) {
            hql.append("and d.createTime = :createTime ");
            params.put("createTime", dimTableInfoVo.getCreateTime());
        }
        if(StringUtil.isNoneBlank(dimTableInfoVo.getCreateUserId())){
            hql.append("and d.createUserId = :createUserId ");
            params.put("createUserId", dimTableInfoVo.getCreateUserId());
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }

}
