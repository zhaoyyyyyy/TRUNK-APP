/*
 * @(#)LabelAttrTemplateInfoDaoImpl.java
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
import com.asiainfo.biapp.si.loc.core.syspush.dao.ILabelAttrTemplateInfoDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrTemplateInfo;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelAttrTemplateInfoVo;

/**
 * Title : LabelAttrTemplateInfoDaoImpl
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
public class LabelAttrTemplateInfoDaoImpl extends BaseDaoImpl<LabelAttrTemplateInfo, String> implements ILabelAttrTemplateInfoDao{

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param LabelAttrTemplateInfo
     * @return
     */
    public Page<LabelAttrTemplateInfo> selectLabelAttrTemplateInfoPageList(Page<LabelAttrTemplateInfo> page,
            LabelAttrTemplateInfoVo labelAttrTemplateInfoVo) {
        Map<String, Object> reMap = fromBean(labelAttrTemplateInfoVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    /**
     * 根据条件查询列表
     *
     * @param SysInfo
     * @return
     */
    public List<LabelAttrTemplateInfo> selectLabelAttrTemplateInfoList(
            LabelAttrTemplateInfoVo labelAttrTemplateInfoVo) {
        Map<String, Object> reMap = fromBean(labelAttrTemplateInfoVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }
    
    public Map<String, Object> fromBean(LabelAttrTemplateInfoVo labelAttrTemplateInfoVo){
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LabelAttrTemplateInfo l where 1=1 ");
        if(StringUtil.isNoneBlank(labelAttrTemplateInfoVo.getTemplateId())){
            hql.append("and l.templateId = :templateId ");
            params.put("templateId", labelAttrTemplateInfoVo.getTemplateId());
        }
        if(StringUtil.isNoneBlank(labelAttrTemplateInfoVo.getTemplateName())){
            hql.append("and l.templateName = :templateName ");
            params.put("templateName", labelAttrTemplateInfoVo.getTemplateName());
        }
        if(null != labelAttrTemplateInfoVo.getConfigId()){
            hql.append("and l.configId = :configId ");
            params.put("configId", labelAttrTemplateInfoVo.getConfigId());
        }
        if(StringUtil.isNoneBlank(labelAttrTemplateInfoVo.getCreateUserId())){
            hql.append("and l.createUserId = :createUserId ");
            params.put("createUserId", labelAttrTemplateInfoVo.getCreateUserId());
        }
        if(null != labelAttrTemplateInfoVo.getCreateTime()){
            hql.append("and l.createTime = :createTime ");
            params.put("createTime", labelAttrTemplateInfoVo.getCreateTime());
        }
        if(null != labelAttrTemplateInfoVo.getStatus()){
            hql.append("and l.status = :status ");
            params.put("status", labelAttrTemplateInfoVo.getStatus());
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }
}
