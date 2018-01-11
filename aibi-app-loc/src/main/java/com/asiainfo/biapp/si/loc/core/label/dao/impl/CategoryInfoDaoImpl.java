/*
 * @(#)CategoryInfoDaoImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;
import com.asiainfo.biapp.si.loc.core.dimtable.vo.DimTableInfoVo;
import com.asiainfo.biapp.si.loc.core.label.dao.ICategoryInfoDao;
import com.asiainfo.biapp.si.loc.core.label.entity.CategoryInfo;
import com.asiainfo.biapp.si.loc.core.label.vo.CategoryInfoVo;

/**
 * Title : CategoryInfoDaoImpl
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月20日     wangrd        Created
 * </pre>
 * <p/>
 *
 * @author wangrd
 * @version 1.0.0.2017年11月20日
 */
@Repository
public class CategoryInfoDaoImpl extends BaseDaoImpl<CategoryInfo, String> implements ICategoryInfoDao {

    public CategoryInfo selectCategoryInfoByCategoryName(String categoryName) {
        return super.findOneByHql("from CategoryInfo c where c.categoryName = ?0", categoryName);
    }
    
    public List<CategoryInfo> selectCategoryInfoList(CategoryInfoVo categoryInfoVo) {
        Map<String, Object> reMap = fromBean(categoryInfoVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }
    public Map<String, Object> fromBean(CategoryInfoVo categoryInfoVo){
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from CategoryInfo c where 1=1 ");
        if (StringUtil.isNotBlank(categoryInfoVo.getCategoryId())) {
            hql.append("and c.categoryId = :categoryId ");
            params.put("categoryId", categoryInfoVo.getCategoryId());
        }
        if (StringUtil.isNotBlank(categoryInfoVo.getSysId())) {
            hql.append("and c.sysId = :sysId ");
            params.put("sysId", categoryInfoVo.getSysId());
        }
        if (StringUtil.isNotBlank(categoryInfoVo.getSysType())) {
            hql.append("and c.sysType = :sysType ");
            params.put("sysType", categoryInfoVo.getSysType());
        }
        if (StringUtil.isNotBlank(categoryInfoVo.getCategoryDesc())) {
            hql.append("and c.categoryDesc = :categoryDesc ");
            params.put("categoryDesc", categoryInfoVo.getCategoryDesc());
        }
        if (StringUtil.isNotBlank(categoryInfoVo.getCategoryName())) {
            hql.append("and c.categoryName = :categoryName ");
            params.put("categoryName", categoryInfoVo.getCategoryName());
        }
        if (StringUtil.isNotBlank(categoryInfoVo.getParentId())) {
            hql.append("and c.parentId = :parentId ");
            params.put("parentId", categoryInfoVo.getParentId());
        } else {
            hql.append("and c.parentId is null ");
        }
        if (StringUtil.isNotBlank(categoryInfoVo.getCategoryPath())) {
            hql.append("and c.categoryPath = :categoryPath ");
            params.put("categoryPath", categoryInfoVo.getCategoryPath());
        }
        if (null != categoryInfoVo.getIsLeaf()) {
            hql.append("and c.isLeaf = :isLeaf ");
            params.put("isLeaf", categoryInfoVo.getIsLeaf());
        }
        if (null != categoryInfoVo.getStatusId()) {
            hql.append("and c.statusId = :statusId ");
            params.put("statusId", categoryInfoVo.getStatusId());
        }
        if (null != categoryInfoVo.getSortNum()) {
            hql.append("and c.sortNum = :sortNum ");
            params.put("sortNum", categoryInfoVo.getSortNum());
        }
        if (null != categoryInfoVo.getLevelId()) {
            hql.append("and c.levelId = :levelId ");
            params.put("levelId", categoryInfoVo.getLevelId());
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }
}
