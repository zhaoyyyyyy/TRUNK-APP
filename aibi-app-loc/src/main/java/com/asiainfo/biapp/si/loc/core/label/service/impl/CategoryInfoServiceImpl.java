/*
 * @(#)CategoryInfoServiceImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.FileUtil;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.label.dao.ICategoryInfoDao;
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelInfoDao;
import com.asiainfo.biapp.si.loc.core.label.entity.CategoryInfo;
import com.asiainfo.biapp.si.loc.core.label.service.ICategoryInfoService;
import com.asiainfo.biapp.si.loc.core.label.vo.CategoryInfoVo;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelInfoVo;

import au.com.bytecode.opencsv.CSVReader;

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
    
    @Cacheable(value="LabelInfo", key="'selectCategoryInfoList_'+#categoryInfoVo.sysId+#categoryInfoVo.parentId")
    @Override
    public List<CategoryInfo> selectCategoryInfoList(CategoryInfoVo categoryInfoVo) throws BaseException{
        if(categoryInfoVo.getSysId()==null){
            throw new ParamRequiredException("专区ID不能为空");
        }
//        if(categoryInfoVo.getSysType()==null){
//            throw new ParamRequiredException("专区类型不能为空");
//        }
        return iCategoryInfoDao.selectCategoryInfoList(categoryInfoVo);
    }
    
    @Cacheable(value="LabelInfo", key="'selectCategoryPath_'+#categoryId")
    @Override
    public String selectCategoryPath(String categoryId) throws BaseException{
    	return getParentName(categoryId,"");
    }
    private String getParentName(String categoryId,String lastPath) throws BaseException {
    	ICategoryInfoService categoryInfoService = (ICategoryInfoService)SpringContextHolder.getBean("categoryInfoServiceImpl");
    	CategoryInfo categoryInfo = categoryInfoService.selectCategoryInfoById(categoryId);
    	if(categoryInfo != null){
    		categoryInfo.getChildren();
    		if(categoryInfo != null && categoryInfo.getParentId() != null){
        		String parentId = categoryInfo.getParentId();
        		return getParentName(parentId,categoryInfo.getCategoryName()+"/"+lastPath);
        	}else{
        		return categoryInfo.getCategoryName()+"/"+lastPath;
        	}
    	}else{
    		return lastPath;
    	}
    }
    
    @Cacheable(value="LabelInfo", key="'selectCategoryInfoById_'+#categoryId")
    @Override
    public CategoryInfo selectCategoryInfoById(String categoryId) throws BaseException {
        if(StringUtils.isBlank(categoryId)){
            throw new ParamRequiredException("ID不能为空");
        }
        CategoryInfo categoryInfo = this.findOneByHql("from CategoryInfo b where b.categoryId = ?0 ", categoryId);
        if(categoryInfo != null){
        	categoryInfo.getChildren();
        }
        return categoryInfo;
    }
    
    @Cacheable(value="LabelInfo", key="'selectCategoryInfoByCategoryName_'+#categoryName+#sysId")
    public CategoryInfo selectCategoryInfoByCategoryName(String categoryName,String sysId) throws BaseException {
        if (StringUtils.isBlank(categoryName)) {
            throw new ParamRequiredException("名称不能为空");
        }
        if(StringUtils.isBlank(sysId)){
            throw new ParamRequiredException("专区ID出错");
        }
        return iCategoryInfoDao.selectCategoryInfoByCategoryName(categoryName,sysId);
    }

    @CacheEvict(value="LabelInfo",allEntries=true)
    public void addCategoryInfo(CategoryInfo categoryInfo) throws BaseException {
        if(categoryInfo != null && StringUtil.isEmpty(categoryInfo.getParentId())){
            categoryInfo.setParentId(null);
        }
        if(StringUtils.isBlank(categoryInfo.getCategoryName())){
            throw new ParamRequiredException("分类名称不能为空");
        }
        if(categoryInfo.getCategoryName().length()>10){
            throw new ParamRequiredException("分类名称过长");
        }
        CategoryInfoVo categoryInfoVo = new CategoryInfoVo();
        categoryInfoVo.setSysId(categoryInfo.getSysId());
        categoryInfo.setSortNum(iCategoryInfoDao.selectAllCategoryInfoList(categoryInfoVo).size()+1);
        super.saveOrUpdate(categoryInfo);
    }

    @CacheEvict(value="LabelInfo",allEntries=true)
    public void modifyCategoryInfo(CategoryInfo categoryInfo) throws BaseException{
        if(StringUtils.isBlank(categoryInfo.getCategoryName())){
            throw new ParamRequiredException("分类名称不能为空");
        }
       /* if(categoryInfo.getCategoryName().length()>8){
            throw new ParamRequiredException("分类名称过长");
        }*/
        CategoryInfo categoryInfo2 = iCategoryInfoDao.get(categoryInfo.getCategoryId());
        categoryInfo2.setCategoryName(categoryInfo.getCategoryName());
        categoryInfo2.setSortNum(categoryInfo.getSortNum());
        categoryInfo2.setParentId(categoryInfo.getParentId());
        super.saveOrUpdate(categoryInfo2);
        
    }

    @CacheEvict(value="LabelInfo",allEntries=true)
    public void deleteCategoryInfoById(String categoryId) throws BaseException {
        if (selectCategoryInfoById(categoryId)==null){
            throw new ParamRequiredException("ID不存在");
        }
        if(StringUtils.isBlank(categoryId)){
            throw new ParamRequiredException("ID不能为空");
        }
        if(selectCategoryInfoById(categoryId).getChildren().size() != 0){
            throw new ParamRequiredException("该分类下存在有效标签或者分类，不能删除");
        }
//        LabelInfoVo labelInfoVo = new LabelInfoVo();
//        labelInfoVo.setCategoryId(categoryId);
//        if(iLabelInfoDao.selectLabelAllEffectiveInfoList(labelInfoVo).size() != 0){
//            throw new ParamRequiredException("该分类下存在有效标签或者分类，不能删除");
//        }
        ICategoryInfoService categoryInfoService = (ICategoryInfoService)SpringContextHolder.getBean("categoryInfoServiceImpl");
        if(null !=categoryInfoService.selectCategoryInfoById(categoryId).getSortNum()){
        	String sysId =categoryInfoService.selectCategoryInfoById(categoryId).getSysId();
            int sortNum = categoryInfoService.selectCategoryInfoById(categoryId).getSortNum();
            CategoryInfoVo categoryInfoVo = new CategoryInfoVo();
            categoryInfoVo.setSysId(sysId);
            List<CategoryInfo> categoryInfoList =iCategoryInfoDao.selectAllCategoryInfoList(categoryInfoVo);
            if(categoryInfoList.size()>0){
            	for(int i=0;i<categoryInfoList.size();i++){
                	if(categoryInfoList.get(i).getSortNum() > sortNum){
                		CategoryInfo categoryInfoLists = categoryInfoList.get(i);
                		categoryInfoLists.setSortNum(categoryInfoLists.getSortNum()-1);
                		categoryInfoService.modifyCategoryInfo(categoryInfoLists);
                	}
                }	
            }
        }
        super.delete(categoryId);
    }
    
    @CacheEvict(value="LabelInfo",allEntries=true)
    @Transactional(rollbackOn = Exception.class)
    public String parseColumnInfoFile(InputStream inputStream, String fileName,String configId) throws Exception {
        String filePath = null;
        try {
            filePath = FileUtil.uploadTargetUserFile(inputStream, fileName);
        } catch (Exception e1) {
            LogUtil.error("获取文件目录信息报错", e1);
        }
        // 解析模版文件
        CSVReader reader = null;
        // 当前记录条数
        int currentRowNum = 0;
        // 数据行数
        int dataCount = 0;
        // 重复行数
        int exitCount = 0;
        //存放要返回的错误信息
        StringBuffer result = new StringBuffer();
        //存放分类名称
        List<String> nameList = new ArrayList<>();
        //存放分类名称和分类ID
        Map<String,String> map = new HashMap<>();
        CategoryInfoVo categoryInfoVo = new CategoryInfoVo();
        categoryInfoVo.setSysId(configId);
        List<CategoryInfo> categoryInfoList = iCategoryInfoDao.selectAllCategoryInfoList(categoryInfoVo);
        int sortNum = categoryInfoList.size();//标签分类序号
        for(CategoryInfo c : categoryInfoList){
            nameList.add(c.getCategoryName());
            map.put(c.getCategoryName(), c.getCategoryId());
        }
        
        InputStream in = new FileInputStream(new File(filePath));
        Charset charset = FileUtil.getInputStreamCharset(in);
        reader = new CSVReader(new InputStreamReader(new FileInputStream(new File(filePath)), charset));
        String[] nextLine;
        
        try {
            while ((nextLine = reader.readNext()) != null) {
                currentRowNum++;
                int num = -1;//从前到后有几个连续的有效单元格
                int len = -1;//一共有几个有效的单元格
                // 跳过注释行
                if (nextLine[0].startsWith("#")) {
                    continue;
                }
                
                for(int i=0;i<nextLine.length;i++){
                    if(StringUtil.isNotBlank(nextLine[i])){
                        num++;
                    }else{
                        break;
                    }
                }
                for(int i=0;i<nextLine.length;i++){
                    if(StringUtil.isNotBlank(nextLine[i])){
                        len++;
                    }
                }
                
                if(num!=len){
                    result.append("第["+currentRowNum+"]行数据错误存在空的单元格;");
                    continue;
                }
                
                if(nextLine[num].length()>10){
                    result.append("第["+currentRowNum+"]行["+(num+1)+"]级分类名称过长;");
                    continue;
                }
                
                CategoryInfo categoryInfo = new CategoryInfo();
                categoryInfo.setCategoryName(nextLine[num]);
                categoryInfo.setSysId(configId);
                if(num!=0){
                    String parentId = map.get(nextLine[num-1]);
                    if(StringUtil.isBlank(parentId)){
                        result.append("第["+currentRowNum+"]行["+num+"]级分类名称不存在;");
                        continue;
                    }
                    categoryInfo.setParentId(map.get(nextLine[num-1]));
                }
                if(!nameList.contains(categoryInfo.getCategoryName())){
                    sortNum++;
                    categoryInfo.setSortNum(sortNum);
                    super.saveOrUpdate(categoryInfo);
                    dataCount++;
                }else{
                    exitCount++;
                    continue;
                }
                map.put(categoryInfo.getCategoryName(), categoryInfo.getCategoryId());
                nameList.add(categoryInfo.getCategoryName());
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (reader != null) {
                reader.close();
                reader = null;
            }
        }
        if(StringUtil.isNotBlank(result)){
            throw new ParamRequiredException(result.toString());
        }
        String msg;
        if(exitCount!=0){
            msg = "成功导入["+dataCount+"]个分类,共有["+exitCount+"]个分类已存在"; 
        }else{
            msg = "成功导入["+dataCount+"]个分类";
        }
        return msg;
    }

    
    @CacheEvict(value="LabelInfo",allEntries=true)
    public void moveCategoryInfo(CategoryInfo categoryInfo) throws BaseException{
    	ICategoryInfoService categoryInfoService = (ICategoryInfoService)SpringContextHolder.getBean("categoryInfoServiceImpl");
    	if(null !=categoryInfo.getSortNum()){
	    	int targetSortNum = Integer.parseInt(categoryInfo.getTargetSortNum());
	    	String parentId =categoryInfo.getParentId();
	    	CategoryInfoVo categoryInfoVo = new CategoryInfoVo();
	        categoryInfoVo.setSysId(categoryInfo.getSysId());
	        List<CategoryInfo> categoryInfoList =iCategoryInfoDao.selectAllCategoryInfoList(categoryInfoVo);
	        categoryInfo=categoryInfoService.selectCategoryInfoById(categoryInfo.getCategoryId());
	        for(int i=0;i<categoryInfoList.size();i++){
	        	if(categoryInfoList.get(i).getSortNum()!=null){
		        	//从下往上移
		        	if(categoryInfoList.get(i).getSortNum() >= targetSortNum && categoryInfoList.get(i).getSortNum() < categoryInfo.getSortNum()){
		        		CategoryInfo categoryInfoLists = categoryInfoList.get(i);
		        		categoryInfoLists.setSortNum(categoryInfoLists.getSortNum()+1);
		        		categoryInfoService.modifyCategoryInfo(categoryInfoLists);
		        	}
		        	//从上往下移
		        	if(categoryInfoList.get(i).getSortNum() < targetSortNum && categoryInfoList.get(i).getSortNum() > categoryInfo.getSortNum()){
		        		CategoryInfo categoryInfoLists = categoryInfoList.get(i);
		        		categoryInfoLists.setSortNum(categoryInfoLists.getSortNum()-1);
		        		categoryInfoService.modifyCategoryInfo(categoryInfoLists);
		        	}
	        	}
	        }
	        if(targetSortNum<categoryInfo.getSortNum()){
	       	 categoryInfo.setSortNum(targetSortNum);
	        }
	        if(targetSortNum>categoryInfo.getSortNum()){
	        	categoryInfo.setSortNum(targetSortNum-1);
	        }
	        if(StringUtil.isNotBlank(parentId) ){
	        	 categoryInfo.setParentId(parentId);
	        }else{
	        	categoryInfo.setParentId(null);
	        }
    	}
        categoryInfoService.modifyCategoryInfo(categoryInfo);
    }

}
