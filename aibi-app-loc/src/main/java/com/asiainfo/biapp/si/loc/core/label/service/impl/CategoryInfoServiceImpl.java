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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVReader;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
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
    
    public CategoryInfo selectCategoryInfoByCategoryName(String categoryName,String sysId) throws BaseException {
        if (StringUtils.isBlank(categoryName)) {
            throw new ParamRequiredException("名称不能为空");
        }
        if(StringUtils.isBlank(sysId)){
            throw new ParamRequiredException("专区ID出错");
        }
        return iCategoryInfoDao.selectCategoryInfoByCategoryName(categoryName,sysId);
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
            throw new ParamRequiredException("该分类下存在有效标签或者分类，不能删除");
        }
        LabelInfoVo labelInfoVo = new LabelInfoVo();
        labelInfoVo.setCategoryId(categoryId);
        if(iLabelInfoDao.selectLabelAllEffectiveInfoList(labelInfoVo).size() != 0){
            throw new ParamRequiredException("该分类下存在有效标签或者分类，不能删除");
        }
        super.delete(categoryId);
        
    }
    
    public Map<String,Object> parseColumnInfoFile(InputStream inputStream, String fileName,String configId) throws Exception {
        Map<String,Object> resultMap = new HashMap<>();
        String error = "";
        boolean success = true;
        
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
        // 列数
        int columnSize = 2;
        // 数据行数
        int dataCount = 0;
        
        List<CategoryInfo> categoryInfoList = new ArrayList<>();
        
        InputStream in = new FileInputStream(new File(filePath));
        Charset charset = FileUtil.getInputStreamCharset(in);
        reader = new CSVReader(new InputStreamReader(new FileInputStream(new File(filePath)), charset));
        String[] nextLine;
        
        try {
            Set<String> set = new HashSet<String>();
            while ((nextLine = reader.readNext()) != null) {
                currentRowNum++;
                // 跳过注释行
                if (nextLine[0].startsWith("#")) {
                    continue;
                }else{
                    dataCount++;
                }
                if (nextLine.length < columnSize) {
                    throw new Exception("导入文件错误");
                }
                
                if (StringUtil.isEmpty(nextLine[0])) {
                    error = "第" + currentRowNum + "行分类名称不能为空!";
                    resultMap.put("error", error);
                    success = false;
                    break;
                }
                if (nextLine[0].length() > 8) {
                    error = "第" + currentRowNum + "行分类名称长度超长!";
                    resultMap.put("error", error);
                    success = false;
                    break;
                }
                set.add(nextLine[0]);
                if (set.size() < dataCount) {
                    error = "第" + currentRowNum + "行分类名称重复!";
                    resultMap.put("error", error);
                    success = false;
                    break;
                }
                CategoryInfo isExit = selectCategoryInfoByCategoryName(nextLine[0],configId);
                if(isExit!=null){
                    error = "第" + currentRowNum + "行分类名称已存在!";
                    resultMap.put("error", error);
                    success = false;
                    break;
                }
                if(nextLine.length < 2) {
                    error = "第" + currentRowNum + "行父分类列为空!";
                    resultMap.put("error", error);
                    success = false;
                    break;
                }
                isExit = selectCategoryInfoByCategoryName(nextLine[1],configId);
                if(isExit==null){
                    error = "第" + currentRowNum + "行找不到父分类！";
                    resultMap.put("error", error);
                    success = false;
                    break;
                }
                CategoryInfo categoryInfo = new CategoryInfo();
                categoryInfo.setCategoryName(nextLine[0]);
                categoryInfo.setParentId(isExit.getCategoryId());
                categoryInfo.setSysId(configId);
                categoryInfoList.add(categoryInfo);
            }
            if(success){
                error = "成功";
            }
            resultMap.put("success", success);
            resultMap.put("msg", error);
        } catch (Exception e) {
            currentRowNum = -1;
            throw new Exception("导入报错" + e.getMessage(), e);
        } finally {
            if (in != null) {
                in.close();
            }
            if (reader != null) {
                reader.close();
                reader = null;
            }
        }
        
        if(success){
            for(CategoryInfo c : categoryInfoList){
                this.addCategoryInfo(c);
            }
        }
        
        return resultMap;
    }


}
