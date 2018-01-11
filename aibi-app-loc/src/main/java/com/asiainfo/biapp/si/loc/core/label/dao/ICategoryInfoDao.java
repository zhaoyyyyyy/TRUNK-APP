/*
 * @(#)ICategoryInfoDao.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.core.label.entity.CategoryInfo;
import com.asiainfo.biapp.si.loc.core.label.vo.CategoryInfoVo;

/**
 * Title : ICategoryInfoDao
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
public interface ICategoryInfoDao extends BaseDao<CategoryInfo, String> {

    /**
     * 根据条件查询列表
     *
     * @param CategoryInfo
     * @return
     */
    public List<CategoryInfo> selectCategoryInfoList(CategoryInfoVo categoryInfoVo);
    
    /**
     * 根据分类名称查询
     *
     * @param CategoryName
     * @return
     */
    public CategoryInfo selectCategoryInfoByCategoryName(String categoryName);
}
