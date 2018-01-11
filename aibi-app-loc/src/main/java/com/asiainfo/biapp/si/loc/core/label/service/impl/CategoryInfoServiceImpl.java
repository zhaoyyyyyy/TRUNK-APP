/*
 * @(#)CategoryInfoServiceImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.label.dao.ICategoryInfoDao;
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelInfoDao;
import com.asiainfo.biapp.si.loc.core.label.entity.CategoryInfo;
import com.asiainfo.biapp.si.loc.core.label.service.ICategoryInfoService;
import com.asiainfo.biapp.si.loc.core.label.vo.CategoryInfoVo;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelInfoVo;

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
    
    @Autowired
    private ILabelInfoDao iLabelInfoDao;
    

    @Override
    protected BaseDao<CategoryInfo, String> getBaseDao() {
        return iCategoryInfoDao;
    }

    public List<CategoryInfo> selectCategoryInfoList(CategoryInfoVo categoryInfoVo) throws BaseException{
        if(categoryInfoVo.getSysId()==null){
            throw new ParamRequiredException("专区ID不能为空");
        }
//        if(categoryInfoVo.getSysType()==null){
//            throw new ParamRequiredException("专区类型不能为空");
//        }
        return iCategoryInfoDao.selectCategoryInfoList(categoryInfoVo);
    }

    public CategoryInfo selectCategoryInfoById(String categoryId) throws BaseException {
        if(StringUtils.isBlank(categoryId)){
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(categoryId);
    }
    
    public CategoryInfo selectCategoryInfoByCategoryName(String categoryName) throws BaseException {
        if (StringUtils.isBlank(categoryName)) {
            throw new ParamRequiredException("名称不能为空");
        }
        return iCategoryInfoDao.selectCategoryInfoByCategoryName(categoryName);
    }

    public void addCategoryInfo(CategoryInfo categoryInfo) throws BaseException {
        if(categoryInfo != null && StringUtil.isEmpty(categoryInfo.getParentId())){
            categoryInfo.setParentId(null);
        }
        if(StringUtils.isBlank(categoryInfo.getCategoryName())){
            throw new ParamRequiredException("分类名称不能为空");
        }
        if(categoryInfo.getCategoryName().length()>8){
            throw new ParamRequiredException("分类名称过长");
        }
        super.saveOrUpdate(categoryInfo);
    }

    public void modifyCategoryInfo(CategoryInfo categoryInfo) throws BaseException{
        if(StringUtils.isBlank(categoryInfo.getCategoryName())){
            throw new ParamRequiredException("分类名称不能为空");
        }
        if(categoryInfo.getCategoryName().length()>8){
            throw new ParamRequiredException("分类名称过长");
        }
        super.saveOrUpdate(categoryInfo);
        
    }

    public void deleteCategoryInfoById(String categoryId) throws BaseException {
        if (selectCategoryInfoById(categoryId)==null){
            throw new ParamRequiredException("ID不存在");
        }
        if(StringUtils.isBlank(categoryId)){
            throw new ParamRequiredException("ID不能为空");
        }
        if(selectCategoryInfoById(categoryId).getChildren().size() != 0){
            throw new ParamRequiredException("该分类下还有分类，不可以删除");
        }
        LabelInfoVo labelInfoVo = new LabelInfoVo();
        labelInfoVo.setCategoryId(categoryId);
        if(iLabelInfoDao.selectLabelInfoList(labelInfoVo).size() != 0){
            throw new ParamRequiredException("该分类下还有标签，不可以删除");
        }
        super.delete(categoryId);
        
    }


}
