/*
 * @(#)CategoryInfoServiceImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.labelconfig.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.dao.ICategoryInfoDao;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.entity.CategoryInfo;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.service.ICategoryInfoService;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.vo.CategoryInfoVo;

/**
 * Title : CategoryInfoServiceImpl
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
@Service
@Transactional
public class CategoryInfoServiceImpl extends BaseServiceImpl<CategoryInfo, String> implements ICategoryInfoService{

    @Autowired
    private ICategoryInfoDao iCategoryInfoDao;
    

    @Override
    protected BaseDao<CategoryInfo, String> getBaseDao() {
        return iCategoryInfoDao;
    }

    public Page<CategoryInfo> findCategoryInfoPageList(Page<CategoryInfo> page, CategoryInfoVo categoryInfoVo) throws BaseException{
        return iCategoryInfoDao.findCategoryInfoPageList(page, categoryInfoVo);
    }

    public List<CategoryInfo> findCategoryInfoList(CategoryInfoVo categoryInfoVo) throws BaseException{
        return iCategoryInfoDao.findCategoryInfoList(categoryInfoVo);
    }

    public CategoryInfo getById(String categoryId) throws BaseException {
        if(StringUtils.isBlank(categoryId)){
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(categoryId);
    }

    public void saveT(CategoryInfo categoryInfo) throws BaseException {
        super.saveOrUpdate(categoryInfo);
    }

    public void updateT(CategoryInfo categoryInfo) throws BaseException{
        super.saveOrUpdate(categoryInfo);
        
    }

    public void deleteById(String categoryId) throws BaseException {
        super.delete(categoryId);
        
    }


}
