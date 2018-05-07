/*
 * @(#)LabelAttrRelDaoImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.syspush.dao.ILabelAttrRelDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelAttrRelVo;

/**
 * Title : LabelAttrRelDaoImpl
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
 * <pre>1    2018年1月18日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月18日
 */
@Repository
public class LabelAttrRelDaoImpl extends BaseDaoImpl<LabelAttrRel, String> implements ILabelAttrRelDao{

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param labelAttrRelVo
     * @return
     */
    public Page<LabelAttrRel> selectLabelAttrRelPageList(Page<LabelAttrRel> page, LabelAttrRelVo labelAttrRelVo) {
        Map<String, Object> reMap = fromBean(labelAttrRelVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    /**
     * 根据条件查询列表
     *
     * @param labelAttrRelVo
     * @return
     */
    public List<LabelAttrRel> selectLabelAttrRelList(LabelAttrRelVo labelAttrRelVo) {
        Map<String, Object> reMap = fromBean(labelAttrRelVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }
    
    public Map<String, Object> fromBean(LabelAttrRelVo labelAttrRelVo){
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LabelAttrRel l where 1=1 ");
        if(StringUtil.isNoneBlank(labelAttrRelVo.getPriKey())){
            hql.append("and l.priKey = :priKey ");
            params.put("priKey", labelAttrRelVo.getPriKey());
        }
        if(StringUtil.isNoneBlank(labelAttrRelVo.getRecordId())){
            hql.append("and l.recordId = :recordId ");
            params.put("recordId", labelAttrRelVo.getRecordId());
        }
        if(StringUtil.isNoneBlank(labelAttrRelVo.getLabelId())){
            hql.append("and l.labelId = :labelId ");
            params.put("labelId", labelAttrRelVo.getLabelId());
        }
        if(StringUtil.isNoneBlank(labelAttrRelVo.getAttrCol())){
            hql.append("and l.attrCol = :attrCol ");
            params.put("attrCol", labelAttrRelVo.getAttrCol());
        }
        if(null!=labelAttrRelVo.getModifyTimeStart() && null!=labelAttrRelVo.getModifyTimeEnd()){
            if(null != labelAttrRelVo.getModifyTimeStart()){
                hql.append("and l.modifyTime >= "+labelAttrRelVo.getModifyTimeStart()+" ");
            }
            if(null != labelAttrRelVo.getModifyTimeEnd()){
                hql.append("and l.modifyTime <= "+labelAttrRelVo.getModifyTimeEnd()+" ");
            }
        } else if(null!=labelAttrRelVo.getModifyTimeStart() && null==labelAttrRelVo.getModifyTimeEnd()){
            if(null != labelAttrRelVo.getModifyTimeStart()){
                hql.append("and l.modifyTime = "+labelAttrRelVo.getModifyTimeStart()+" ");
            }
        }
            
        if(StringUtil.isNoneBlank(labelAttrRelVo.getAttrColType())){
            hql.append("and l.attrColType = :attrColType ");
            params.put("attrColType", labelAttrRelVo.getAttrColType());
        }
        if(StringUtil.isNoneBlank(labelAttrRelVo.getAttrColName())){
            hql.append("and l.attrColName = :attrColName ");
            params.put("attrColName", labelAttrRelVo.getAttrColName());
        }
        if(null != labelAttrRelVo.getAttrSource()){
            hql.append("and l.attrSource = :attrSource ");
            params.put("attrSource", labelAttrRelVo.getAttrSource());
        }
        if(StringUtil.isNoneBlank(labelAttrRelVo.getLabelOrCustomId())){
            hql.append("and l.labelOrCustomId = :labelOrCustomId ");
            params.put("labelOrCustomId", labelAttrRelVo.getLabelOrCustomId());
        }
        if(StringUtil.isNoneBlank(labelAttrRelVo.getLabelOrCustomColumn())){
            hql.append("and l.labelOrCustomColumn = :labelOrCustomColumn ");
            params.put("labelOrCustomColumn", labelAttrRelVo.getLabelOrCustomColumn());
        }
        if(null != labelAttrRelVo.getIsVerticalAttr()){
            hql.append("and l.isVerticalAttr = :isVerticalAttr ");
            params.put("isVerticalAttr", labelAttrRelVo.getIsVerticalAttr());
        }
        if(StringUtil.isNoneBlank(labelAttrRelVo.getListTableName())){
            hql.append("and l.listTableName = :listTableName ");
            params.put("listTableName", labelAttrRelVo.getListTableName());
        }
        if(null != labelAttrRelVo.getStatus()){
            hql.append("and l.status = :status ");
            params.put("status", labelAttrRelVo.getStatus());
        }
        if(StringUtil.isNoneBlank(labelAttrRelVo.getAttrVal())){
            hql.append("and l.attrVal = :attrVal ");
            params.put("attrVal", labelAttrRelVo.getAttrVal());
        }
        if(StringUtil.isNoneBlank(labelAttrRelVo.getTableName())){
            hql.append("and l.tableName = :tableName ");
            params.put("tableName", labelAttrRelVo.getTableName());
        }
        if(StringUtil.isNoneBlank(labelAttrRelVo.getSortType())){
            hql.append("and l.sortType = :sortType ");
            params.put("sortType", labelAttrRelVo.getSortType());
        }
        if(null != labelAttrRelVo.getSortNum()){
            hql.append("and l.sortNum = :sortNum ");
            params.put("sortNum", labelAttrRelVo.getSortNum());
        }
        if(null != labelAttrRelVo.getAttrSettingType()){
            hql.append("and l.attrSettingType = :attrSettingType ");
            params.put("attrSettingType", labelAttrRelVo.getAttrSettingType());
        }
        if(StringUtil.isNoneBlank(labelAttrRelVo.getAttrCreateUserId())){
            hql.append("and l.attrCreateUserId = :attrCreateUserId ");
            params.put("attrCreateUserId", labelAttrRelVo.getAttrCreateUserId());
        }
        if(null != labelAttrRelVo.getPageSortNum()){
            hql.append("and l.pageSortNum = :pageSortNum ");
            params.put("pageSortNum", labelAttrRelVo.getPageSortNum());
        }
		
		if(null != labelAttrRelVo.getOrderBy()){
            hql.append("ORDER BY ").append(labelAttrRelVo.getOrderBy());
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }

}
