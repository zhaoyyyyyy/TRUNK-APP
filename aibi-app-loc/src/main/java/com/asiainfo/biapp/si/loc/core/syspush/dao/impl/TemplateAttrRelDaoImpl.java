/*
 * @(#)TemplateAttrRelDaoImpl.java
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
import com.asiainfo.biapp.si.loc.core.syspush.dao.ITemplateAttrRelDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.TemplateAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.vo.TemplateAttrRelVo;

/**
 * Title : TemplateAttrRelDaoImpl
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
 * <pre>1    2018年1月19日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月19日
 */
@Repository
public class TemplateAttrRelDaoImpl extends BaseDaoImpl<TemplateAttrRel, String> implements ITemplateAttrRelDao{

    public Page<TemplateAttrRel> selectTemplateAttrRelPageList(Page<TemplateAttrRel> page,
            TemplateAttrRelVo templateAttrRelVo) {
        Map<String, Object> reMap = fromBean(templateAttrRelVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    public List<TemplateAttrRel> selectTemplateAttrRelList(TemplateAttrRelVo templateAttrRelVo) {
        Map<String, Object> reMap = fromBean(templateAttrRelVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }
    
    public Map<String, Object> fromBean(TemplateAttrRelVo templateAttrRelVo){
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from TemplateAttrRel t where 1=1 ");
        if(StringUtil.isNoneBlank(templateAttrRelVo.getTemplateId())){
            hql.append("and t.templateId = :templateId ");
            params.put("templateId", templateAttrRelVo.getTemplateId());
        }
        if(StringUtil.isNoneBlank(templateAttrRelVo.getLabelId())){
            hql.append("and t.labelId = :labelId ");
            params.put("labelId", templateAttrRelVo.getLabelId());
        }
        if(StringUtil.isNoneBlank(templateAttrRelVo.getLabelColumnId())){
            hql.append("and t.labelColumnId = :labelColumnId ");
            params.put("labelColumnId", templateAttrRelVo.getLabelColumnId());
        }
        if (null != templateAttrRelVo.getIsVerticalAttr()){
            hql.append("and t.isVerticalAttr = :isVerticalAttr ");
            params.put("isVerticalAttr", templateAttrRelVo.getIsVerticalAttr());
        }
        if (null != templateAttrRelVo.getSortNum()){
            hql.append("and t.sortNum = :sortNum ");
            params.put("sortNum", templateAttrRelVo.getSortNum());
        }
        if (null != templateAttrRelVo.getStatus()){
            hql.append("and t.status = :status ");
            params.put("status", templateAttrRelVo.getStatus());
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }

}
