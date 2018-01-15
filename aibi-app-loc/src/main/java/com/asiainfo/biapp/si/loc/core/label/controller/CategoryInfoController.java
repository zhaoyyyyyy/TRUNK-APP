/*
 * @(#)CategoryInfoController.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import springfox.documentation.annotations.ApiIgnore;

import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.label.entity.CategoryInfo;
import com.asiainfo.biapp.si.loc.core.label.service.ICategoryInfoService;
import com.asiainfo.biapp.si.loc.core.label.vo.CategoryInfoVo;

/**
 * Title : CategoryInfoController
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
 * <pre>1    2017年11月20日    wangrd        Created</pre>
 * <p/>
 *
 * @author   wangrd
 * @version 1.0.0.2017年11月20日
 */
@Api(value = "标签分类管理",description="王瑞冬")
@RequestMapping("api/label")
@RestController
public class CategoryInfoController extends BaseController<CategoryInfo>{

    @Autowired
    private ICategoryInfoService iCategoryInfoService;
    
    private static final String SUCCESS = "success";
    
    @ApiOperation(value = "不分页查询标签分类")
    @RequestMapping(value = "/categoryInfo/queryList", method = RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public WebResult<List<CategoryInfo>> findList(@ModelAttribute CategoryInfoVo categoryInfoVo) throws BaseException{
        WebResult<List<CategoryInfo>> webResult = new WebResult<>();
        List<CategoryInfo> categoryInfoList = new ArrayList<>();
        try {
            categoryInfoList = iCategoryInfoService.selectCategoryInfoList(categoryInfoVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取标签分类成功.", categoryInfoList);
    }
    
    @ApiOperation(value = "根据ID查询标签分类")
    @ApiImplicitParam(name = "categoryId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/categoryInfo/get",method = RequestMethod.POST)
    public WebResult<CategoryInfo> findById(String categoryId) throws BaseException{
        WebResult<CategoryInfo> webResult = new WebResult<>();
        CategoryInfo categoryInfo = new CategoryInfo();
        try {
            categoryInfo = iCategoryInfoService.selectCategoryInfoById(categoryId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取标签分类成功", categoryInfo);
    }
    
    @ApiOperation(value = "新增标签分类")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "categoryId", value = "分类ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "sysId", value = "系统ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "sysType", value = "系统类型", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "categoryDesc", value = "分类描述", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "categoryName", value = "分类名称", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "parentId", value = "父分类ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "categoryPath", value = "分类ID路径", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "isLeaf", value = "叶子节点", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "statusId", value = "状态", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "sortNum", value = "排序", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "levelId", value = "层级", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/categoryInfo/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore CategoryInfo categoryInfo) throws BaseException{
            WebResult<String> webResult = new WebResult<>();
            CategoryInfo category = new CategoryInfo();
            category = iCategoryInfoService.selectCategoryInfoByCategoryName(categoryInfo.getCategoryName(),categoryInfo.getSysId());
            if (null != category){
                return webResult.fail("分类名称已存在");
            }
            try {
                iCategoryInfoService.addCategoryInfo(categoryInfo);
            } catch (BaseException e) {
                return webResult.fail(e);
            }
            return webResult.success("新增标签分类成功", SUCCESS);
    }
    
    
    @ApiOperation( value = "修改标签分类")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "categoryId", value = "分类ID", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "sysId", value = "系统ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "sysType", value = "系统类型", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "categoryDesc", value = "分类描述", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "categoryName", value = "分类名称", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "parentId", value = "父分类ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "categoryPath", value = "分类ID路径", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "isLeaf", value = "叶子节点", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "statusId", value = "状态", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "sortNum", value = "排序", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "levelId", value = "层级", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/categoryInfo/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore CategoryInfo categoryInfo) throws BaseException{
        WebResult<String> webResult = new WebResult<>();
        CategoryInfo oldCat = new CategoryInfo();
        CategoryInfo category = new CategoryInfo();
        category = iCategoryInfoService.selectCategoryInfoByCategoryName(categoryInfo.getCategoryName(),categoryInfo.getSysId());
        try {
            oldCat = iCategoryInfoService.selectCategoryInfoById(categoryInfo.getCategoryId());
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        if(!categoryInfo.getCategoryName().equals(oldCat.getCategoryName()) && null != category){
            return webResult.fail("分类名称已存在");
        }
        oldCat = fromToBean(categoryInfo, oldCat);
        iCategoryInfoService.update(oldCat);
        return webResult.success("修改标签分类成功", SUCCESS);
    }
    
    @ApiOperation(value = "删除标签分类")
    @ApiImplicitParam(name = "categoryId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/categoryInfo/delete", method = RequestMethod.POST)
    public WebResult<String> del(String categoryId) throws BaseException{
        WebResult<String> webResult = new WebResult<>();
        try {
            iCategoryInfoService.deleteCategoryInfoById(categoryId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除标签分类成功", SUCCESS);
    }


    /**
     * 封装实体信息
     *
     * @param cat
     * @param oldCat
     * @return
     */
    public CategoryInfo fromToBean(CategoryInfo cat, CategoryInfo oldCat){
        if(StringUtil.isNotBlank(cat.getCategoryId())){
            oldCat.setCategoryId(cat.getCategoryId());
        }
        if(StringUtil.isNotBlank(cat.getSysId())){
            oldCat.setSysId(cat.getSysId());
        }
        if(StringUtil.isNoneBlank(cat.getSysType())){
            oldCat.setSysType(cat.getSysType());
        }
        if(StringUtil.isNoneBlank(cat.getCategoryDesc())){
            oldCat.setCategoryDesc(cat.getCategoryDesc());
        }
        if(StringUtil.isNoneBlank(cat.getCategoryName())){
            oldCat.setCategoryName(cat.getCategoryName());
        }
        if(StringUtil.isNoneBlank(cat.getParentId())){
            oldCat.setParentId(cat.getParentId());
        }
        if(StringUtil.isNoneBlank(cat.getCategoryPath())){
            oldCat.setCategoryPath(cat.getCategoryPath());
        }
        if(null != cat.getIsLeaf()){
            oldCat.setIsLeaf(cat.getIsLeaf());
        }
        if(null != cat.getStatusId()){
            oldCat.setStatusId(cat.getStatusId());
        }
        if(null != cat.getSortNum()){
            oldCat.setSortNum(cat.getSortNum());
        }
        if(null != cat.getLevelId()){
            oldCat.setLevelId(cat.getLevelId());
        }
        return oldCat;
    }
    
    @ApiOperation(value = "导入分类")
    @RequestMapping(value = "/categoryInfo/upload", consumes = "multipart/*", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public WebResult<String> upload(@ApiParam(value = "文件上传", required = true) MultipartFile multipartFile,@ApiParam(value = "专区ID", required = true) String configId)
            throws IOException {
        WebResult<String> webResult = new WebResult<>();
        Map<String,Object> map = new HashMap<>();
        String result = "";
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileFileName = multipartFile.getOriginalFilename();
            try {
                map = iCategoryInfoService.parseColumnInfoFile(multipartFile.getInputStream(), fileFileName,configId);
            } catch (Exception e) {
                LogUtil.error(e);
            }
        }
        result = map.get("msg").toString();
        if((boolean) map.get("success")){
            return webResult.fail(result);
        }
        return webResult.success(result, "success");
    }
    

}
