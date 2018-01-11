/*
 * @(#)SourceTableInfoController.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import springfox.documentation.annotations.ApiIgnore;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.entity.StatusAndMessage;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.source.entity.SourceInfo;
import com.asiainfo.biapp.si.loc.core.source.entity.SourceTableInfo;
import com.asiainfo.biapp.si.loc.core.source.service.ISourceTableInfoService;
import com.asiainfo.biapp.si.loc.core.source.vo.SourceTableInfoVo;

/**
 * Title : SourceTableInfoController
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月15日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月15日
 */
@Api(value = "指标数据源信息配置管理", description = "张楠")
@RequestMapping("api/source")
@RestController
public class SourceTableInfoController extends BaseController<SourceTableInfo> {

    @Autowired
    private ISourceTableInfoService iSourceTableInfoService;

    private static final String SUCCESS = "success";

    @ApiOperation(value = "分页查询指标数据源信息配置")
    @RequestMapping(value = "/sourceTableInfo/queryPage", method = RequestMethod.POST)
    public Page<SourceTableInfo> list(@ModelAttribute Page<SourceTableInfo> page,
            @ModelAttribute SourceTableInfoVo sourceTableInfoVo) {
        Page<SourceTableInfo> sourceTableInfoPage = new Page<>();
        try {
            sourceTableInfoPage = iSourceTableInfoService.selectSourceTableInfoPageList(page, sourceTableInfoVo);
        } catch (BaseException e) {
            sourceTableInfoPage.fail(e);
        }
        return sourceTableInfoPage;
    }

    @ApiOperation(value = "不分页查询指标数据源信息配置")
    @RequestMapping(value = "/sourceTableInfo/queryList", method = RequestMethod.POST)
    public WebResult<List<SourceTableInfo>> findList(@ModelAttribute SourceTableInfoVo sourceTableInfoVo) {
        WebResult<List<SourceTableInfo>> webResult = new WebResult<>();
        List<SourceTableInfo> sourceTableInfoList = new ArrayList<>();
        try {
            sourceTableInfoList = iSourceTableInfoService.selectSourceTableInfoList(sourceTableInfoVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取指标数据源信息配置成功.", sourceTableInfoList);
    }

    @ApiOperation(value = "根据ID查询指标数据源信息配置")
    @ApiImplicitParam(name = "sourceTableId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/sourceTableInfo/get", method = RequestMethod.POST)
    public WebResult<SourceTableInfo> findById(String sourceTableId) throws BaseException {
        WebResult<SourceTableInfo> webResult = new WebResult<>();
        SourceTableInfo sourceTableInfo = new SourceTableInfo();
        try {
            sourceTableInfo = iSourceTableInfoService.selectSourceTableInfoById(sourceTableId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取指标数据源信息配置成功.", sourceTableInfo);
    }

    @ApiOperation(value = "新增指标数据源信息配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "configId", value = "专区ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceFileName", value = "指标源表文件名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceTableName", value = "指标源表表名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableSchema", value = "表名SCHEMA", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceTableCnName", value = "指标源表中文名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceTableType", value = "指标源表类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "tableSuffix", value = "表/文件后缀日期", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "readCycle", value = "读取周期", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "idType", value = "来源主键标识类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "idColumn", value = "来源主键字段名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "idDataType", value = "来源主键数据类型", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnCaliber", value = "业务口径", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataSourceId", value = "数据源ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataSourceType", value = "数据源类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dateColumnName", value = "日期分区字段", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "whereSql", value = "过滤条件", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "statusId", value = "状态", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sourceInfoList", value = "指标信息列表", required = false, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/sourceTableInfo/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore SourceTableInfo sourceTableInfo) {
        WebResult<String> webResult = new WebResult<>();
        User user = new User();
        try {
            user = this.getLoginUser();
            sourceTableInfo.setCreateUserId(user.getUserId());
            iSourceTableInfoService.addSourceTableInfo(sourceTableInfo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("新增指标数据源信息配置成功", SUCCESS);
    }

    @ApiOperation(value = "修改指标数据源信息配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceTableId", value = "ID", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "configId", value = "专区ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceFileName", value = "指标源表文件名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceTableName", value = "指标源表表名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableSchema", value = "表名SCHEMA", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceTableCnName", value = "指标源表中文名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceTableType", value = "指标源表类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "tableSuffix", value = "表/文件后缀日期", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "readCycle", value = "读取周期", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "idType", value = "来源主键标识类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "idColumn", value = "来源主键字段名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "idDataType", value = "来源主键数据类型", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnCaliber", value = "业务口径", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataSourceId", value = "数据源ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataSourceType", value = "数据源类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dataExtractionType", value = "数据抽取方式", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dateColumnName", value = "日期分区字段", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "whereSql", value = "过滤条件", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "statusId", value = "状态", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/sourceTableInfo/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore SourceTableInfo sourceTableInfo) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iSourceTableInfoService.modifySourceTableInfo(sourceTableInfo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("修改指标数据源信息配置成功", SUCCESS);
    }

    /**
     * 删除
     * 
     * @param id
     */
    @ApiOperation(value = "删除指标数据源信息配置")
    @ApiImplicitParam(name = "sourceTableId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/sourceTableInfo/delete", method = RequestMethod.POST)
    public WebResult<String> del(String sourceTableId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iSourceTableInfoService.deleteSourceTableInfo(sourceTableId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除指标数据源信息配置成功", SUCCESS);
    }


    @ApiOperation(value = "导入列信息")
    @RequestMapping(value = "/sourceTableInfo/upload", consumes = "multipart/*", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public WebResult<Object> upload(@ApiParam(value = "文件上传", required = true) MultipartFile multipartFile) throws IOException{
        WebResult<Object> webResult = new WebResult<>();
        // 限制文件大小
        long fileSize = 5 * 1024 * 1024;

        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();

        String fileSuffix = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        List<SourceInfo> sourceInfoListAll = new ArrayList<>();
        List<SourceInfo> sourceInfoListRight = new ArrayList<>();
        List<StatusAndMessage> sourceInfoListWrong = new ArrayList<>();

        if (!multipartFile.isEmpty()) {
            if (!fileSuffix.equals("xls")) {
                webResult.setStatus("type");
                webResult.setMsg("模板文件类型错误，请上传Excel类型的文件！");
            } else if (!"application/vnd.ms-excel".equals(contentType)) {
                webResult.setStatus("type");
                webResult.setMsg("模板文件类型错误，请上传Excel类型文件！");
            } else if (multipartFile.getSize() > fileSize) {
                webResult.setStatus("size");
                webResult.setMsg("文件大小超过最大限制5M！");
            } else {
                String errorInfo = checkFile(multipartFile, sourceInfoListAll, sourceInfoListRight, sourceInfoListWrong);
                if ("error_all".equals(errorInfo)) {
                    webResult.setStatus("error_all");
                    webResult.setMsg("模板文件格式错误！");
                } else if ("error_some".equals(errorInfo)) {
                    webResult.setStatus("error_some");
                    webResult.setMsg("模板文件内容错误：");
                    webResult.setData(sourceInfoListWrong);
                } else {
                    webResult.setStatus("success");
                    webResult.setMsg("成功读取" + sourceInfoListRight.size() + "条指标列信息。");
                    webResult.setData(sourceInfoListRight);
                }
            }

        } else {
            webResult.setMsg("模板文件为空");
        }

        return webResult;
    }

    public String checkFile(MultipartFile multipartFile, List<SourceInfo> sourceInfoListAll,
            List<SourceInfo> sourceInfoListRight, List<StatusAndMessage> sourceInfoListWrong) throws IOException, ParseException {
        InputStream is = multipartFile.getInputStream();
        POIFSFileSystem poifsFileSystem = new POIFSFileSystem(is);
        HSSFWorkbook hssfWorkbook =  new HSSFWorkbook(poifsFileSystem);
        //上传文件信息
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        HSSFRow xRow= hssfSheet.getRow(3);
        //验证状态
        String message = "error_all";
        //模板最大列数
        long maxColNum = 4;
        
        //模板sheet名称确认
        String tempSheetName = "指标列信息导入模板";
        String uploadSheetName = hssfSheet.getSheetName();//sheet名称
        if(!tempSheetName.equals(uploadSheetName)){
            message = "error_all";   //模板文件有误
            return message;
        }
        
        //隐藏的模板文件验证信息
        String hiddenInfo = "模板文件验证";
        String info = hssfSheet.getRow(1).getCell(0).getStringCellValue();
        if(!hiddenInfo.equals(info)){
            message = "error_all";   //模板文件有误
            return message;
        }
        
        //列数确认
        int colNum = xRow.getLastCellNum();//上传文件列数
        if (maxColNum != colNum){
            message = "error_all";   //模板文件有误
            return message;
        }
        
        //四列标题确认
        if ("字段名称".equals(xRow.getCell(0).getStringCellValue())
                &&"字段类型"  .equals(xRow.getCell(1).getStringCellValue())
                &&"字段中文名"  .equals(xRow.getCell(2).getStringCellValue())
                &&"业务口径".equals(xRow.getCell(3).getStringCellValue())){
            //标题验证成功
        }else{
            message = "error_all";  //模板文件有误
            return message;
        }
        
        int rowNum = hssfWorkbook.getSheetAt(0).getLastRowNum();//上传文件行数
        SourceInfo sourceInfo;
        for(int i=4;i<rowNum;i++){
            HSSFRow row = hssfSheet.getRow(i);
            if(null!=row){
                
                String columnName = "";
                if(null!=row.getCell(0)){
                    switch (row.getCell(0).getCellType())
                    {
                        case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                            DecimalFormat df = new DecimalFormat("0");
                            columnName = df.format(row.getCell(0).getNumericCellValue()); 
                            break;
                        case HSSFCell.CELL_TYPE_STRING: // 字符串
                            columnName = row.getCell(0).getStringCellValue();
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                            columnName = "";
                            break;
                        default:
                            columnName = "";
                            break;
                    }
                }
                
                String cooColumnType = "";
                if(null!=row.getCell(1)){
                    switch (row.getCell(1).getCellType())
                    {
                        case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                            DecimalFormat df = new DecimalFormat("0");
                            cooColumnType = df.format(row.getCell(1).getNumericCellValue()); 
                            break;
                        case HSSFCell.CELL_TYPE_STRING: // 字符串
                            cooColumnType = row.getCell(1).getStringCellValue();
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                            cooColumnType = "";
                            break;
                        default:
                            cooColumnType = "";
                            break;
                    }
                }
                
                String sourceName = "";
                if(null!=row.getCell(2)){
                    switch (row.getCell(2).getCellType())
                    {
                        case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                            DecimalFormat df = new DecimalFormat("0");
                            sourceName = df.format(row.getCell(2).getNumericCellValue()); 
                            break;
                        case HSSFCell.CELL_TYPE_STRING: // 字符串
                            sourceName = row.getCell(2).getStringCellValue();
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                            sourceName = "";
                            break;
                        default:
                            sourceName = "";
                            break;
                    }
                }
                
                String columnCaliber = "";
                if(null!=row.getCell(3)){
                    switch (row.getCell(3).getCellType())
                    {
                        case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                            DecimalFormat df = new DecimalFormat("0");
                            columnCaliber = df.format(row.getCell(3).getNumericCellValue()); 
                            break;
                        case HSSFCell.CELL_TYPE_STRING: // 字符串
                            columnCaliber = row.getCell(3).getStringCellValue();
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                            columnCaliber = "";
                            break;
                        default:
                            columnCaliber = "";
                            break;
                    }
                }
                
                if(StringUtil.isNotBlank(columnName)&&StringUtil.isNotBlank(cooColumnType)&&StringUtil.isNotBlank(sourceName)){
                    sourceInfo = new SourceInfo();
                    sourceInfo.setColumnName(columnName);
                    sourceInfo.setCooColumnType(cooColumnType);
                    sourceInfo.setSourceName(sourceName);
                    sourceInfo.setColumnCaliber(columnCaliber);
                    sourceInfoListAll.add(sourceInfo);
                    sourceInfoListRight.add(sourceInfo);
                }
                
            }else{
                LogUtil.info("存在隔空行或者已经读取完毕");
            }
        }
        
        if(!sourceInfoListWrong.isEmpty()){
            message = "error_some";
        }else{
            message = "all_ok";
        }
        
        return message;
    }

}
