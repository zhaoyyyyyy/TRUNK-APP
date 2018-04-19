
package com.asiainfo.biapp.si.loc.core.label.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.base.common.LabelRuleContants;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.DateUtil;
import com.asiainfo.biapp.si.loc.base.utils.JsonUtil;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelVerticalColumnRel;
import com.asiainfo.biapp.si.loc.core.label.model.ExploreQueryParam;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelExploreService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelRuleVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Title : ShopCartController
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 6.0 +
 * <p/>
 * Modification History :购物车（探索）相关操作
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年12月13日    tianxy3        Created
 * </pre>
 * <p/>
 *
 * @author tianxy3
 * @version 1.0.0.2017年12月13日
 */
@Api(value = "007.04->-购物车管理", description = "田旭阳")
@RequestMapping("api/shopCart")
@RestController
public class ShopCartController extends BaseController {

	@Autowired
	private ILabelInfoService labelInfoService;
	
	@Autowired
	private ILabelExploreService exploreServiceImpl;

	@Autowired
	private IBackSqlService backServiceImpl;
	
	private static final String SUCCESS = "success";

	@ApiOperation(value = "判断所选日期是否早于标签生效日期")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "newLabelMonthFormat", value = "所选月数据日期", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "newLabelDayFormat", value = "所选日数据日期(20180108)", required = true, paramType = "query", dataType = "string") })
	@RequestMapping(value = "/checkRuleEffectDate", method = RequestMethod.POST)
	public WebResult<String> checkRuleEffectDate( String newLabelMonthFormat,String newLabelDayFormat) {
		WebResult<String> webResult = new WebResult<>();
		boolean flag = false;
		List<LabelRuleVo> rules = getSessionLabelRuleList();
		for (LabelRuleVo rule : rules) {
			if (LabelRuleContants.ELEMENT_TYPE_LABEL_ID == rule.getElementType()) {
				String effectDate = rule.getEffectDate();
				if (ServiceConstants.LabelInfo.UPDATE_CYCLE_M == rule.getUpdateCycle()) {
					if (Integer.valueOf(newLabelMonthFormat) < Integer.valueOf(effectDate)) {
						flag = true;
						break;
					}
				} else {
					if (Integer.valueOf(newLabelDayFormat) < Integer.valueOf(effectDate)) {
						flag = true;
						break;
					}
				}
			} // end getElementType
		}
		if (flag) {
			return webResult.fail("所选日期早于标签生效日期，请重新选择！");
		} else {
			return webResult.success("判断所选日期是否早于标签生效日期", SUCCESS);
		}
	}
	
	
	/**
	 * Description:  查找购物车规则中最早数据日期
	 */
	@ApiOperation(value = "查找购物车规则中最早数据日期")
	@RequestMapping(value = "/findEaliestDataDate", method = RequestMethod.POST)
	public WebResult<Map<String, Object>> findEaliestDataDate() {
		Map<String, Object> result = new HashMap<String, Object>();
		WebResult<Map<String, Object>> webResult = new WebResult<>();
		try {
			String monthDate =getNewLabelMonth();
			String dayDate =getNewLabelDay();
			boolean existMonthLabel = false;//是否存在月周期标签
		    boolean existDayLabel = false;//是否存在日周期标签
			boolean isAllNewDate = true;//规则中标签是否都是最新数据日期
			List<LabelRuleVo> rules = getSessionLabelRuleList();
			for (LabelRuleVo rule : rules) {
				if (LabelRuleContants.ELEMENT_TYPE_LABEL_ID == rule.getElementType()) {
					String dataDate = rule.getDataDate();
					if (ServiceConstants.LabelInfo.UPDATE_CYCLE_M == rule.getUpdateCycle()) {
						existMonthLabel = true;
						if (Integer.valueOf(dataDate) < Integer.valueOf(monthDate)) {
							monthDate = dataDate;// 最新日数据日期
							isAllNewDate = false;
						}
					} else {
						existDayLabel = true;
						if (Integer.valueOf(dataDate) < Integer.valueOf(dayDate)) {
							dayDate = dataDate;
							isAllNewDate = false;
						}
					}
				} else if (LabelRuleContants.ELEMENT_TYPE_CUSTOM_RULES == rule.getElementType()) {
					List<LabelRuleVo> children = rule.getChildLabelRuleList();
					for (int j = 0; j < children.size(); j++) {
						LabelRuleVo child = children.get(j);
						if (LabelRuleContants.ELEMENT_TYPE_LABEL_ID == child.getElementType()) {
							String dataDate = child.getDataDate();
							if (ServiceConstants.LabelInfo.UPDATE_CYCLE_M == child.getUpdateCycle()) {
								existMonthLabel = true;
								if (Integer.valueOf(dataDate) < Integer.valueOf(monthDate)) {
									monthDate = dataDate;
									isAllNewDate = false;
								}
							} else {
								existDayLabel = true;
								if (Integer.valueOf(dataDate) < Integer.valueOf(dayDate)) {
									dayDate = dataDate;
									isAllNewDate = false;
								}
							}
						}
					}
				} // else if
			}
			result.put("monthDate", DateUtil.string2StringFormat(monthDate, DateUtil.FORMAT_YYYYMM, DateUtil.FORMAT_YYYY_MM));//规则中最早月数据日期
			result.put("dayDate", DateUtil.string2StringFormat(dayDate, DateUtil.FORMAT_YYYYMMDD, DateUtil.FORMAT_YYYY_MM_DD));//规则中最早日数据日期
			result.put("isAllNewDate", isAllNewDate);
			result.put("newMonthDate", DateUtil.string2StringFormat(getNewLabelMonth(), DateUtil.FORMAT_YYYYMM, DateUtil.FORMAT_YYYY_MM));//最新月数据日期
			result.put("newDayDate", DateUtil.string2StringFormat(getNewLabelDay(), DateUtil.FORMAT_YYYYMMDD, DateUtil.FORMAT_YYYY_MM_DD));//最新日数据日期
			result.put("existMonthLabel", existMonthLabel);
			result.put("existDayLabel", existDayLabel);
		} catch (Exception e) {
			LogUtil.error("查找购物车规则中最早数据日期异常", e);
			return webResult.fail("查找购物车规则中最早数据日期异常");
		}
		return webResult.success(" 查找购物车规则中最早数据日期成功", result);
	}
	
	
	/**
	 * 查询标签是否能够加入到购物车
	 */
	@ApiOperation(value = "查询标签是否能够加入到购物车")
	@ApiImplicitParam(name = "labelId", value = "标签id", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/findLabelValidate", method = RequestMethod.POST)
	public WebResult<String> findLabelValidate( String labelId) {
		WebResult<String> webResult = new WebResult<>();
		boolean success = true;
		String msg = "抱歉，该标签数据未准备好，不能添加到收纳篮！";
		try {
			LabelInfo ciLabelInfo = CocCacheProxy.getCacheProxy().getLabelInfoById(labelId);
			if (StringUtil.isEmpty(ciLabelInfo.getDataDate())) {
				success = false;
			}
		} catch (Exception e) {
			LogUtil.error("校验标签异常", e);
			return webResult.fail(msg);
		}
		if (success) {
			return webResult.success("查询标签是否能够加入到购物车成功", SUCCESS);
		} else {
			return webResult.fail(msg);
		}
	}
	
	/**
	 * 纵表标签设置参数弹出页面 
	 * @return
	 * @version ZJ
	 */
	@ApiOperation(value = "纵表标签设置参数弹出页面 ")
	@ApiImplicitParam(name = "labelId", value = "标签id", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/findVerticalLabel", method = RequestMethod.POST)
	public WebResult<Map<String, Object>> findVerticalLabel(String labelId) {
		Map<String, Object> result = new HashMap<String, Object>();
		WebResult<Map<String, Object>> webResult = new WebResult<>();
		try {
			LabelInfo labelInfo = CocCacheProxy.getCacheProxy().getLabelInfoById(labelId);
			List<LabelVerticalColumnRel> verticalColumnRels = labelInfo.getVerticalColumnRels();
			result.put("ciLabelVerticalColumnRelList", verticalColumnRels);
			result.put("vertLabelName", labelInfo.getLabelName());
		} catch(Exception e) {
			LogUtil.error("查询纵表标签异常", e);
			return webResult.fail("查询纵表标签异常");
		}
		return webResult.success("查询纵表标签成功", result);
	}
	
	
	/**
	 * 查询客户群是否能够加入到购物车
	 */
	@ApiOperation(value = "查询客户群是否能够加入到购物车")
	@ApiImplicitParam(name = "labelId", value = "客户群id", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/findCusotmValidate", method = RequestMethod.POST)
	public WebResult<String> findCusotmValidate( String labelId) {
		WebResult<String> webResult = new WebResult<>();
		boolean success = true;
		String msg = "抱歉，该客户群无规则、无清单可用，不能添加到收纳篮！";
		//msg = "客户群已经被删除，无法加入到购物车！";
		try {
			LabelInfo customGroup = labelInfoService.selectLabelInfoById(labelId);
			
			if (ServiceConstants.LabelInfo.DATA_STATUS_ID_G_SUCCESS != customGroup.getDataStatusId()) {
				success=false;
			}
		}catch (BaseException baseException) {
			return webResult.fail(baseException);
		} catch (Exception e) {
			LogUtil.error("查询客户群异常", e);
			return webResult.fail(msg);
		}
		if (success) {
			return webResult.success("查询标签是否能够加入到购物车成功", SUCCESS);
		} else {
			return webResult.fail(msg);
		}
	}

	/**
	 * 添加(规则)到购物车
	 * 
	 * 先决0.1获取参数calculationsId typeId（标签，客户群区分） 1、查询缓存中标签是否有效 
	 * 如果是修改用户群，则清空购物车 2、从缓存读取购物车数据，如果已经有标签先添加运算符例如：and or 3、根据标签拼接规则
	 * 4、查询规则条数放入缓存 5、括号的特殊处理
	 * 
	 */
	@ApiOperation(value = "添加(规则)到购物车")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "calculationsId", value = "标签ID", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "typeId", value = "类型", required = true, paramType = "query", dataType = "string") })
	@RequestMapping(value = "/saveShopSession", method = RequestMethod.POST)
	public WebResult<String> saveShopSession( String calculationsId, String typeId) {
		WebResult<String> webResult = new WebResult<>();
		boolean success = true;
		String msg = "抱歉，该标签数据未准备好，不能添加到收纳篮！";
		try {
			List<LabelRuleVo> rules = getSessionLabelRuleList();
			Integer sort = 0;
			if (rules.size() > 0) {
				sort = rules.get(rules.size() - 1).getSortNum();
				sort = sort + 1;// 排序标号 ,默认规则为"and"
				rules.add(generateRule("and", LabelRuleContants.ELEMENT_TYPE_OPERATOR, sort));
			}
			sort = sort + 1;
			if (LabelRuleContants.LABEL_INFO_CALCULATIONS_TYPEID.equals(typeId)) {
				LabelInfo ciLabelInfo = CocCacheProxy.getCacheProxy().getLabelInfoById(calculationsId);
				if (StringUtil.isEmpty(ciLabelInfo.getDataDate())) {
					success = false;
				}
				// 校验完之后的操作
				if (success) {
					msg = "加入购物车成功";
					LabelRuleVo rule = initLabelRule(ciLabelInfo, sort);
					rules.add(rule);// 初始化标签规则
				}
			}else if (LabelRuleContants.CUSTOM_GROUP_INFO_CALCULATIONS_TYPEID.equals(typeId)) {
				LabelInfo ciCustomGroupInfo = labelInfoService.selectLabelInfoById(calculationsId);
				LabelRuleVo rule = new LabelRuleVo();
				rule.setCalcuElement(ciCustomGroupInfo.getLabelId());
				rule.setCustomId(ciCustomGroupInfo.getLabelId());
				rule.setLabelTypeId(ciCustomGroupInfo.getLabelTypeId());
				rule.setElementType(LabelRuleContants.ELEMENT_TYPE_LIST_ID);
				rule.setCustomOrLabelName(ciCustomGroupInfo.getLabelName());
				rule.setSortNum(sort);
				rule.setLabelFlag(1);// 取反标识，默认不取反
				rule.setAttrVal(ciCustomGroupInfo.getDataDate());
				rules.add(rule);
			}
			setSessionAttribute(LabelRuleContants.SHOP_CART_RULE, JsonUtil.toJsonString(rules));
			setSessionAttribute(LabelRuleContants.SHOP_CART_RULE_NUM,
					String.valueOf(findLabelOrCustomNum(rules)));

		} catch (BaseException baseException) {
			return webResult.fail(baseException);
		}catch (Exception e) {
			LogUtil.error("添加(规则)到购物车异常", e);
			return webResult.fail("添加(规则)到购物车异常");
		}
		if (success) {
			return webResult.success("加入购物车成功", SUCCESS);
		} else {
			return webResult.fail(msg);
		}
	}

	/**
	 * 更新session(计算中心每次修改之后对应重新设置session)
	 */
	@ApiOperation(value = "计算中心每次修改之后对应重新设置session")
	@ApiImplicitParam(name = "labelRuleStr", value = "规则串", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/updateShopSession", method = RequestMethod.POST)
	public WebResult<String> updateShopSession( String labelRuleStr) {
		WebResult<String> webResult = new WebResult<>();
		try {
			List<LabelRuleVo> rules = (List<LabelRuleVo>) JsonUtil.json2CollectionBean(labelRuleStr, List.class, LabelRuleVo.class);
			int numValue = 0;
			numValue = findLabelOrCustomNum(rules);
			if (numValue == 0) {
				setSessionAttribute(LabelRuleContants.SHOP_CART_RULE, "");
			}else{
				setSessionAttribute(LabelRuleContants.SHOP_CART_RULE, JsonUtil.toJsonString(rules));
			}
			setSessionAttribute(LabelRuleContants.SHOP_CART_RULE_NUM, String.valueOf(numValue));
		} catch (BaseException baseException) {
			return webResult.fail(baseException);
		}catch (Exception e) {
			LogUtil.error("计算中心修改异常", e);
			return webResult.fail("计算中心修改异常");
		}
		return webResult.success("计算中心修改成功", SUCCESS);
	}

	/**
	 * Description: 刷新购物车页面 1、从缓存读取购物车数据 ....
	 * 
	 * @return shopCartRules：去除括号的规则列表List
	 *         <CiLabelRule> showCartRulesCount：购物车规则总数
	 * @author tianxy3
	 * @date 2017年12月14日
	 */
	@ApiOperation(value = "读取购物车数据")
	@RequestMapping(value = "/findShopCart", method = RequestMethod.POST)
	public WebResult<Map<String, Object>> findShopCart() {
		Map<String, Object> result = new HashMap<String, Object>();
		WebResult<Map<String, Object>> webResult = new WebResult<>();
		try {
			List<LabelRuleVo> rules = getSessionLabelRuleList();
			int showCartRulesCount = findLabelOrCustomNum(rules);
			result.put("showCartRulesCount", showCartRulesCount);
			result.put("shopCartRules", rules);
		} catch (Exception e) {
			LogUtil.error("刷新购物车页面异常", e);
			return webResult.fail("刷新购物车页面异常");
		}
		return webResult.success("读取购物车数据成功", result);
	}

	@ApiOperation(value = "校验sql")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dataDate", value = "日期", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "dayLabelDate", value = "数据日期(日)", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "monthLabelDate", value = "数据日期(月)", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "dataPrivaliege", value = "数据范围", required = true, paramType = "query", dataType = "string")})
	@RequestMapping(value = "/validateSql", method = RequestMethod.POST)
	public WebResult<String> validateSql(String dataDate, String dayLabelDate,
			String monthLabelDate,String dataPrivaliege) {
		WebResult<String> webResult = new WebResult<>();
		List<LabelRuleVo> labelRules = getSessionLabelRuleList();
		ExploreQueryParam queryParam = new ExploreQueryParam(dataDate, monthLabelDate, dayLabelDate);
		queryParam.setOrgId(dataPrivaliege);
		queryParam.setValidate(true);
		StringBuffer sql = new StringBuffer();
		try {
			if(haveCustomOrVerticalLabel(labelRules)) {
				String querySql = exploreServiceImpl.getFromSqlForMultiLabel(labelRules, queryParam);
				sql.append("select count(1) from (").append(querySql).append(") abc");
			}else{
				//不包含纵表的探索
				String querySql = exploreServiceImpl.getCountSqlStr(labelRules, queryParam);
				sql.append("select count(1) ").append(querySql);
			}
			System.out.println("querySql SQL : " + sql.toString());
			LogUtil.info("querySql SQL : " + sql.toString());
			backServiceImpl.queryCount(sql.toString());	
		} catch (BaseException baseException) {
			LogUtil.error("校验sql异常", baseException);
			return webResult.fail("校验sql异常");
		}catch (Exception e) {
			LogUtil.error("校验sql异常", e);
			return webResult.fail("校验sql异常");
		}
		return webResult.success("校验sql成功", SUCCESS);
	}
	/**
	 * 
	 * Description: 探索
	 *
	 * dataDate:201711 monthLabelDate:201711 dayLabelDate:20171217
	 *
	 * @author tianxy3
	 * @date 2017年12月18日
	 */
	@ApiOperation(value = "探索")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dataDate", value = "日期", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "dayLabelDate", value = "数据日期(日)", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "monthLabelDate", value = "数据日期(月)", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "dataPrivaliege", value = "数据范围", required = true, paramType = "query", dataType = "string")})
	@RequestMapping(value = "/explore", method = RequestMethod.POST)
	public WebResult<String> explore(String dataDate, String dayLabelDate, String monthLabelDate,
			String dataPrivaliege) {
		WebResult<String> webResult = new WebResult<>();
		List<LabelRuleVo> labelRules = getSessionLabelRuleList();
		ExploreQueryParam queryParam = new ExploreQueryParam(dataDate, monthLabelDate, dayLabelDate);
		queryParam.setOrgId(dataPrivaliege);
		Integer num;
		StringBuffer sql = new StringBuffer();
		try {
			if (haveCustomOrVerticalLabel(labelRules)) {
				String querySql = exploreServiceImpl.getFromSqlForMultiLabel(labelRules, queryParam);
				sql.append("select count(1) from (").append(querySql).append(") abc");
			} else {
				// 不包含纵表的探索
				String querySql = exploreServiceImpl.getCountSqlStr(labelRules, queryParam);
				sql.append("select count(1) ").append(querySql);
			}
			LogUtil.info("countSql SQL : " + sql.toString());
			// 调用后台接口
			num = backServiceImpl.queryCount(sql.toString());
		} catch (Exception e) {
			LogUtil.error("探索异常", e);
			return webResult.fail("探索异常");
		}
		return webResult.success("探索成功", String.valueOf(num));
	}

	/**
	 * 删除购物车的对象
	 */
	@RequestMapping(value = "/delShopSession", method = RequestMethod.POST)
	public WebResult<String> delShopSession() {
		WebResult<String> webResult = new WebResult<>();
		try {
			setSessionAttribute(LabelRuleContants.SHOP_CART_RULE, "");
		} catch (Exception e) {
			LogUtil.error("删除购物车的对象异常", e);
			return webResult.fail(e.getMessage());
		}
		return webResult.success("删除购物车成功", SUCCESS);
	}

	//"计算中心每次修改之后对应重新设置session"
	public WebResult<String> saveSession() {
		WebResult<String> webResult = new WebResult<>();
		try {
			String labelRuleStr = null;
			LabelRuleVo labelRuleVo = (LabelRuleVo) JsonUtil.json2Bean(labelRuleStr, LabelRuleVo.class);
			List<LabelRuleVo> rules = getSessionLabelRuleList();
			List<LabelRuleVo> updateRules = new ArrayList<LabelRuleVo>();
			for (LabelRuleVo rule : rules) {
				int sortNum = rule.getSortNum();
				if (sortNum == labelRuleVo.getSortNum()) {
					updateRules.add(labelRuleVo);
					continue;
				}
				updateRules.add(rule);
			}
			setSessionAttribute(LabelRuleContants.SHOP_CART_RULE, JsonUtil.toJsonString(updateRules));
		} catch (Exception e) {
			LogUtil.error("设置属性更新session异常", e);
			return webResult.fail("设置属性更新session异常");
		}
		return webResult.success("更新购物车成功", SUCCESS);

	}

	
	/**
	 * 验证是否包含用户群或者纵表标签
	 * @param rules List<CiLabelRule> 处理过的规则
	 * @return
	 */
	private boolean haveCustomOrVerticalLabel(List<LabelRuleVo> rules) {
		boolean result = false;
		if(rules != null) {
			for (LabelRuleVo rule : rules) {
				if (rule.getElementType() == LabelRuleContants.ELEMENT_TYPE_CUSTOM_RULES 
						|| rule.getElementType() == LabelRuleContants.ELEMENT_TYPE_LIST_ID) {
					result = true;
					break;
				} else if (rule.getElementType() == LabelRuleContants.ELEMENT_TYPE_LABEL_ID) {
				    if (rule.getLabelTypeId() == ServiceConstants.LabelInfo.LABEL_TYPE_ID_VERT) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 计算标签和清单的总个数 并为
	 * 
	 * @param rules
	 * @return
	 */
	private Integer findLabelOrCustomNum(List<LabelRuleVo> rules) {
		int numValue = 0;
		if (rules != null) {
			for (LabelRuleVo item : rules) {
				if (item != null) {
					if (item.getElementType() == LabelRuleContants.ELEMENT_TYPE_LABEL_ID
							|| item.getElementType() == LabelRuleContants.ELEMENT_TYPE_LIST_ID
							|| item.getElementType() == LabelRuleContants.ELEMENT_TYPE_CUSTOM_RULES) {
						numValue = numValue + 1;
						item.setLabelOrCustomSort(numValue);
					}
				}
			}
		}
		return numValue;
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

	/**
	 * 
	 * Description: 初始化标签规则
	 *
	 * @param ciLabelInfo
	 * @param sort
	 * @return
	 *
	 * @author tianxy3
	 * @date 2017年12月26日
	 */
	private LabelRuleVo initLabelRule(LabelInfo ciLabelInfo, Integer sort) {
		LabelRuleVo rule = new LabelRuleVo();
		rule.setCalcuElement(ciLabelInfo.getLabelId());
		rule.setCustomOrLabelName(ciLabelInfo.getLabelName());
		rule.setMaxVal(new Double(1));
		rule.setMinVal(new Double(0));
		rule.setLabelFlag(1);// 取反标识，默认不取反
		rule.setSortNum(sort);
		rule.setLabelTypeId(ciLabelInfo.getLabelTypeId());
		rule.setElementType(LabelRuleContants.ELEMENT_TYPE_LABEL_ID);
		rule.setDataDate(ciLabelInfo.getDataDate());// 设置最新数据日期
		rule.setUpdateCycle(ciLabelInfo.getUpdateCycle());// 设置标签周期性
		if(ciLabelInfo.getMdaSysTableColumn()!=null){
			rule.setUnit(ciLabelInfo.getMdaSysTableColumn().getUnit());
		}
		Date effectTime = ciLabelInfo.getEffecTime();
		int updateCycle = ciLabelInfo.getUpdateCycle();
		if(updateCycle == ServiceConstants.LabelInfo.UPDATE_CYCLE_D){
			//获得标签可以使用的最早日期
			String effectDate =DateUtil.date2String(effectTime, DateUtil.FORMAT_YYYYMMDD);
			rule.setEffectDate(DateUtil.getOffsetDateByDate(effectDate, -1, 1));
		} else if(updateCycle == ServiceConstants.LabelInfo.UPDATE_CYCLE_M){
			//获得标签可以使用的最早月份
			String effectDate =DateUtil.date2String(effectTime, DateUtil.FORMAT_YYYYMM);
			rule.setEffectDate(DateUtil.getOffsetDateByDate(effectDate, -1, 0));
		}
		return rule;
	}

	/**
	 * 构造CiLabelRule对象，用户拼接购物车初始化规则
	 * 
	 * @param element
	 * @param type
	 * @param sortNum
	 * @return
	 * @version ZJ
	 */
	private LabelRuleVo generateRule(String element, int type, Integer sortNum) {
		LabelRuleVo result = new LabelRuleVo();
		result.setCalcuElement(element);
		result.setElementType(type);
		result.setSortNum(sortNum);
		return result;
	}
}
