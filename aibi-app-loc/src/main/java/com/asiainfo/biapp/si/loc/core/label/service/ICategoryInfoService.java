/*
 * @(#)ICategoryInfoService.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service;

import java.util.List;




import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.label.entity.CategoryInfo;
import com.asiainfo.biapp.si.loc.core.label.vo.CategoryInfoVo;

/**
 * Title : ICategoryInfoService
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
 * <pre>1    2017年11月20日     wangrd        Created</pre>
 * <p/>
 *
 * @author   wangrd
 * @version 1.0.0.2017年11月20日
 */
public interface ICategoryInfoService extends BaseService<CategoryInfo, String>{

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param categoryInfoVo
     * @return
     */
    public Page<CategoryInfo> selectCategoryInfoPageList(Page<CategoryInfo> page, CategoryInfoVo categoryInfoVo) throws BaseException;
    
    /**
     * 根据条件查询列表
     *
     * @param categoryInfoVo
     * @return
     */
    public List<CategoryInfo> selectCategoryInfoList(CategoryInfoVo categoryInfoVo) throws BaseException;

    /**
     * 通过ID得到一个实体 Description:
     *
     * @param categoryId
     * @return
     * @throws BaseException
     */
    public CategoryInfo selectCategoryInfoById(String categoryId) throws BaseException;

    /**
     * 新增或修改一个实体 Description:
     *
     * @param categoryInfo
     * @throws BaseException
     */
    public void addCategoryInfo(CategoryInfo categoryInfo) throws BaseException;

    /**
     * 修改一个实体 Description:
     *
     * @param categoryInfo
     * @throws BaseException
     */
    public void modifyCategoryInfo(CategoryInfo categoryInfo) throws BaseException;

    /**
     * 通过ID删除一个实体 Description:
     *
     * @param categoryId
     * @throws BaseException
     */
    public void deleteCategoryInfoById(String categoryId) throws BaseException;

}
