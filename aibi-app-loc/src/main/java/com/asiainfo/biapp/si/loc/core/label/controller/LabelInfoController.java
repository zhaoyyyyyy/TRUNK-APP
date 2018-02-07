/*
 * @(#)LabelInfoController.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.base.common.LabelRuleContants;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.DateUtil;
import com.asiainfo.biapp.si.loc.base.utils.JsonUtil;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.label.entity.CategoryInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelExtInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.service.IApproveInfoService;
import com.asiainfo.biapp.si.loc.core.label.service.ICategoryInfoService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelRuleService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelInfoVo;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelRuleVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Title : LabelInfoController
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
 * 1    2017年11月16日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月16日
 */
@SuppressWarnings("rawtypes")
@Api(value = "标签信息管理", description = "张楠")
@RequestMapping("api/label")
@RestController
public class LabelInfoController extends BaseController {

    @Autowired
    private ILabelInfoService iLabelInfoService;
    
    @Autowired 
    private IApproveInfoService iApproveInfoService;
    
    @Autowired 
    private ICategoryInfoService iCategoryInfoService;

    @Autowired
	private ILabelRuleService ruleService;
    
    private static final String SUCCESS = "success";
    
    @ApiOperation(value = "查询客户群规则")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "customGroupId", value = "客户群ID", required = true, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/labelInfo/findCustomRuleById", method = RequestMethod.POST)
    public WebResult<List<LabelRuleVo>> findCustomRuleById(String customGroupId) {
        WebResult<List<LabelRuleVo>> webResult = new WebResult<>();
        List<LabelRuleVo> ruleList = new ArrayList<>();
        String msg="查询客户群规则失败！";
        try {
        	 ruleList = ruleService.queryCiLabelRuleList(customGroupId, LabelRuleContants.LABEL_RULE_FROM_COSTOMER);
        } catch (Exception e) {
            return webResult.fail(msg);
        }
        return webResult.success("查询客户群规则成功", ruleList);
    }
    
    
	@ApiOperation(value = "保存客户群型标签")
	@ApiImplicitParams({
		    @ApiImplicitParam(name = "labelName", value = "客户群名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "updateCycle", value = "更新周期", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "orgId", value = "数据范围", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tacticsId", value = "策略ID", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "busiLegend", value = "客户群描述", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "configId", value = "专区ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataDate", value = "日期", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "dayLabelDate", value = "数据日期(日)", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "monthLabelDate", value = "数据日期(月)", required = true, paramType = "query", dataType = "string") })
	@RequestMapping(value = "/labelInfo/saveCustomerLabelInfo", method = RequestMethod.POST)
	public WebResult<String> saveCustomerLabelInfo(String labelName,Integer updateCycle,String orgId,String tacticsId,String busiLegend,String dataDate,String configId, String dayLabelDate, String monthLabelDate) {
		WebResult<String> webResult = new WebResult<>();
		try {
			//基本信息
			LabelInfo labelInfo =new LabelInfo();
			labelInfo.setLabelName(labelName);
			labelInfo.setUpdateCycle(updateCycle);
			labelInfo.setOrgId(orgId);
			labelInfo.setBusiLegend(busiLegend);
			labelInfo.setConfigId(configId);
			if (LabelInfoContants.CUSTOM_CYCLE_TYPE_D == updateCycle) {
				labelInfo.setDataDate(dayLabelDate);
			} else if (LabelInfoContants.CUSTOM_CYCLE_TYPE_D == updateCycle) {
				labelInfo.setDataDate(monthLabelDate);
			} else if (LabelInfoContants.CUSTOM_CYCLE_TYPE_ONE == updateCycle) {
				labelInfo.setDataDate(DateUtil.date2String(new Date(), DateUtil.FORMAT_YYYYMMDD));
			}
			// 拓展信息
			LabelExtInfo labelExtInfo = new LabelExtInfo();
	        labelExtInfo.setTacticsId(tacticsId);
	        labelExtInfo.setDayLabelDate(dayLabelDate);
	        labelExtInfo.setMonthLabelDate(monthLabelDate);
			//标签规则
			List<LabelRuleVo> labelRules =getSessionLabelRuleList();
			
			iLabelInfoService.saveCustomerLabelInfo(labelExtInfo, labelInfo, labelRules);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error("保存客户群型标签异常", e);
			return webResult.fail("保存客户群型标签异常");
		}
		return webResult.success("保存客户群型标签信息成功", SUCCESS);
	}
    
    @ApiOperation(value = "分页查询标签信息")
    @RequestMapping(value = "/labelInfo/queryPage", method = RequestMethod.POST)
    public Page<LabelInfo> list(@ModelAttribute Page<LabelInfo> page, @ModelAttribute LabelInfoVo labelInfoVo) {
        Page<LabelInfo> labelInfoPage = new Page<>();
        Set<String> categoryIdSet = new HashSet<>();
        try {
            if(StringUtil.isNotBlank(labelInfoVo.getCategoryId())){
                CategoryInfo categoryInfo = iCategoryInfoService.selectCategoryInfoById(labelInfoVo.getCategoryId());
                categoryIdSet.add(categoryInfo.getCategoryId());
                if(!categoryInfo.getChildren().isEmpty()){
                    getCategoryChildren(categoryInfo.getChildren(),categoryIdSet);
                }
                labelInfoVo.setCategoryIdSet(categoryIdSet);
            }
            labelInfoPage = iLabelInfoService.selectLabelInfoPageList(page, labelInfoVo);           
        } catch (BaseException e) {
            labelInfoPage.fail(e);
        }
        return labelInfoPage;
    }
    
    public Set<String> getCategoryChildren(Set<CategoryInfo> categoryInfoSet,Set<String> categoryIdSet){
        for(CategoryInfo c : categoryInfoSet){
            categoryIdSet.add(c.getCategoryId());
            if(!c.getChildren().isEmpty()){
                getCategoryChildren(c.getChildren(),categoryIdSet);
            }
        }
        return categoryIdSet;
    }

    @ApiOperation(value = "不分页查询标签信息列表")
    @RequestMapping(value = "/labelInfo/queryList", method = RequestMethod.POST)
    public WebResult<List<LabelInfo>> findList(@ModelAttribute LabelInfoVo labelInfoVo) {
        WebResult<List<LabelInfo>> webResult = new WebResult<>();
        List<LabelInfo> labelInfoList = new ArrayList<>();
        try {
            labelInfoList = iLabelInfoService.selectLabelInfoList(labelInfoVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取标签信息成功.", labelInfoList);
    }

    @ApiOperation(value = "通过ID得到标签信息")
    @ApiImplicitParam(name = "labelId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelInfo/get", method = RequestMethod.POST)
    public WebResult<LabelInfo> findById(String labelId) throws BaseException {
        WebResult<LabelInfo> webResult = new WebResult<>();
        LabelInfo labelInfo = new LabelInfo();
        try {
            labelInfo = iLabelInfoService.selectLabelInfoById(labelId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取标签信息成功.", labelInfo);
    }

    @ApiOperation(value = "新增标签信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyType", value = "主键标识类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "configId", value = "专区ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "orgId", value = "组织ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "countRulesCode", value = "规则编码", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "labelName", value = "标签名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "updateCycle", value = "更新周期", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "labelTypeId", value = "标签类型ID", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "categoryId", value = "标签分类ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "createTypeId", value = "创建方式ID", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dataStatusId", value = "数据状态ID", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dataDate", value = "最新数据时间", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "busiCaliber", value = "业务口径说明", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "effecTime", value = "生效时间", required = false, paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "failTime", value = "失效时间", required = false, paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "publishTime", value = "发布时间", required = false, paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "publishDesc", value = "发布描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "busiLegend", value = "业务说明", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "applySuggest", value = "应用建议", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "labelIdLevelDesc", value = "标签ID层级描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "isRegular", value = "是否正式标签", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "groupType", value = "群类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sortNum", value = "排序字段", required = false, paramType = "query", dataType = "int") ,
            @ApiImplicitParam(name = "dependIndex", value = "规则依赖的指标",required=false,paramType = "query", dataType= "string")})
    @RequestMapping(value = "/labelInfo/save", method = RequestMethod.POST)
    public WebResult<LabelInfo> save(@ApiIgnore LabelInfo labelInfo){
        WebResult<LabelInfo> webResult = new WebResult<>();
        User user = new User();  
        try {
            user = this.getLoginUser();
            labelInfo.setCreateUserId(user.getUserId());
            iLabelInfoService.addLabelInfo(labelInfo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("新增标签信息成功", labelInfo);
    }
    
    @ApiOperation(value = "修改标签信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "labelId", value = "ID", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "keyType", value = "主键标识类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "configId", value = "专区ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "orgId", value = "组织ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "countRulesCode", value = "规则编码", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "labelName", value = "标签名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "updateCycle", value = "更新周期", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "labelTypeId", value = "标签类型ID", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "categoryId", value = "标签分类ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "createTypeId", value = "创建方式ID", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dataStatusId", value = "数据状态ID", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dataDate", value = "最新数据时间", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "busiCaliber", value = "业务口径说明", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "effecTime", value = "生效时间", required = false, paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "failTime", value = "失效时间", required = false, paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "publishTime", value = "发布时间", required = false, paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "publishDesc", value = "发布描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "busiLegend", value = "业务说明", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "applySuggest", value = "应用建议", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "labelIdLevelDesc", value = "标签ID层级描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "isRegular", value = "是否正式标签", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "groupType", value = "群类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sortNum", value = "排序字段", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/labelInfo/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore LabelInfo labelInfo) {
        WebResult<String> webResult = new WebResult<>();
        String approveStatusId = request.getParameter("approveStatusId");
        LabelInfo oldLab = new LabelInfo();
        try {
            oldLab = iLabelInfoService.selectLabelInfoById(labelInfo.getLabelId());
            LabelInfo label = null;
            if (StringUtil.isNoneBlank(labelInfo.getLabelName())) {
                label = iLabelInfoService.selectOneByLabelName(labelInfo.getLabelName(),labelInfo.getConfigId());
            } 
            if (StringUtil.isNoneBlank(labelInfo.getLabelName())&&!oldLab.getLabelName().equals(labelInfo.getLabelName())&& null!= label) {
                throw new ParamRequiredException("标签名称重复");
            } 
            oldLab = fromToBean(labelInfo, oldLab);
            if (StringUtil.isNotEmpty(approveStatusId)) {
                oldLab = iLabelInfoService.updateApproveInfo(approveStatusId, oldLab);
            }
            iLabelInfoService.modifyLabelInfo(oldLab);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("修改标签信息成功", SUCCESS);
    }
    
    @ApiOperation(value = "修改分类ID")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "labelId", value = "ID", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "categoryId", value = "标签分类ID", required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/labelInfo/updateCategoryId", method = RequestMethod.POST)
    public WebResult<String> editCategoryId(@ApiIgnore LabelInfo labelInfo) {
        WebResult<String> webResult = new WebResult<>(); 
        LabelInfo oldLab = new LabelInfo();
        try {
            oldLab = iLabelInfoService.selectLabelInfoById(labelInfo.getLabelId());
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        oldLab = fromToBean(labelInfo, oldLab);
        try {
            iLabelInfoService.modifyLabelInfo(oldLab);
        } catch (BaseException e1) {
            return webResult.fail(e1);
        }
        return webResult.success("修改标签信息成功", SUCCESS);
    }
    
/*    
    @ApiOperation(value = "删除标签信息")
    @ApiImplicitParam(name = "labelId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelInfo/delete", method = RequestMethod.POST)
    public WebResult<String> del(String labelId) {
        WebResult<String> webResult = new WebResult<>();
        LabelInfo labelInfo = iLabelInfoService.get(labelId);
        try {
            iLabelInfoService.deleteLabelInfo(labelId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除标签信息成功", SUCCESS);
    }
    
*/
    @ApiOperation(value = "通过标签ID得到维表表名")
    @ApiImplicitParam(name = "labelId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelInfo/getDimtableName", method = RequestMethod.POST) 
    public WebResult<String> findDimNameBylabelId(String labelId){
        WebResult<String> webResult = new WebResult<>();
        String dimTableName= null;
        try {
            dimTableName = iLabelInfoService.selectDimNameBylabelId(labelId);
        } catch (BaseException e) {
            webResult.fail(e);
        }
        return webResult.success("获取维表表名成功", dimTableName);
    }
    
    public LabelInfo fromToBean(LabelInfo lab, LabelInfo oldLab) {
        if (null != lab.getKeyType()) {
            oldLab.setKeyType(lab.getKeyType());
        }
        if (StringUtil.isNotBlank(lab.getConfigId())) {
            oldLab.setConfigId(lab.getConfigId());
        }
        if (StringUtil.isNotBlank(lab.getOrgId())) {
            oldLab.setOrgId(lab.getOrgId());
        }
        if (StringUtil.isNotBlank(lab.getLabelName())) {
            oldLab.setLabelName(lab.getLabelName());
        }
        if (null != lab.getUpdateCycle()) {
            oldLab.setUpdateCycle(lab.getUpdateCycle());
        }
        if (null != lab.getLabelTypeId()) {
            oldLab.setLabelTypeId(lab.getLabelTypeId());
        }
        if (StringUtil.isNotBlank(lab.getCategoryId())) {
            oldLab.setCategoryId(lab.getCategoryId());
        }
        if (null != lab.getCreateTypeId()) {
            oldLab.setCreateTypeId(lab.getCreateTypeId());
        }
        if (null != lab.getDataStatusId()) {
            oldLab.setDataStatusId(lab.getDataStatusId());
        }
        if (StringUtil.isNotBlank(lab.getDataDate())) {
            oldLab.setDataDate(lab.getDataDate());
        }
        if (StringUtil.isNotBlank(lab.getBusiCaliber())) {
            oldLab.setBusiCaliber(lab.getBusiCaliber());
        }
        if (null != lab.getEffecTime()) {
            oldLab.setEffecTime(lab.getEffecTime());
        }
        if (null != lab.getFailTime()) {
            oldLab.setFailTime(lab.getFailTime());
        }
        if (null != lab.getPublishTime()) {
            oldLab.setPublishTime(lab.getPublishTime());
        }
        if (StringUtil.isNotBlank(lab.getPublishDesc())) {
            oldLab.setPublishDesc(lab.getPublishDesc());
        }
        if (StringUtil.isNotBlank(lab.getBusiLegend())) {
            oldLab.setBusiLegend(lab.getBusiLegend());
        }
        if (StringUtil.isNotBlank(lab.getApplySuggest())) {
            oldLab.setApplySuggest(lab.getApplySuggest());
        }
        if (StringUtil.isNotBlank(lab.getLabelIdLevelDesc())) {
            oldLab.setLabelIdLevelDesc(lab.getLabelIdLevelDesc());
        }
        if (null != lab.getIsRegular()) {
            oldLab.setIsRegular(lab.getIsRegular());
        }
        if (null != lab.getGroupType()) {
            oldLab.setGroupType(lab.getGroupType());
        }
        if (null != lab.getSortNum()) {
            oldLab.setSortNum(lab.getSortNum());
        }
        if (StringUtil.isNotBlank(lab.getDependIndex())) {
            oldLab.setDependIndex(lab.getDependIndex());
        }
        if (StringUtil.isNotBlank(lab.getCountRules())) {
            oldLab.setCountRules(lab.getCountRules());
        }
        if (StringUtil.isNotBlank(lab.getDimId())) {
            oldLab.setDimId(lab.getDimId());
        }
        /*if (StringUtil.isNotBlank(lab.getDataType())) {
            oldLab.setDataType(lab.getDataType());
        }*/
        if (StringUtil.isNotBlank(lab.getUnit())) {
            oldLab.setUnit(lab.getUnit());
        }
        return oldLab;
    }
    
    /**
	 * 
	 * Description: 从session获取购物车对象
	 *
	 * @return
	 *
	 * @author tianxy3
	 * @date 2017年12月22日
	 */
	@SuppressWarnings("unchecked")
	private List<LabelRuleVo> getSessionLabelRuleList() {
		List<LabelRuleVo> rules = null;
		try {
			String ruleStrs = (String) getSessionAttribute(LabelRuleContants.SHOP_CART_RULE);
			if (StringUtil.isEmpty(ruleStrs)) {
				rules = new ArrayList<LabelRuleVo>();
			} else {
				rules = (List<LabelRuleVo>) JsonUtil.json2CollectionBean(ruleStrs, List.class, LabelRuleVo.class);
			}
		} catch (Exception e) {
			LogUtil.error("getSessionLabelRuleList异常", e);
		}
		return rules;
	}
	
	@ApiOperation(value = "查询标签有效列表")
    @RequestMapping(value = "/labelInfo/queryListEffective", method = RequestMethod.POST)
    public WebResult<List<LabelInfo>> findListByEffective(@ModelAttribute LabelInfoVo labelInfoVo) {
        WebResult<List<LabelInfo>> webResult = new WebResult<>();
        List<LabelInfo> labelInfoList = new ArrayList<>();
        try {
            labelInfoList = iLabelInfoService.selectLabelAllEffectiveInfoList(labelInfoVo);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        return webResult.success("获取有效标签信息成功.", labelInfoList);
    }
    

}
