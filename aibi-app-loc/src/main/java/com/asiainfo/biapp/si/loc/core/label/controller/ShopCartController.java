
package com.asiainfo.biapp.si.loc.core.label.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.base.common.LabelRuleContants;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.JsonUtil;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.model.ExploreQueryParam;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelExploreService;
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
@Api(value = "购物车管理", description = "田旭阳")
@RequestMapping("api/shopCart")
@RestController
public class ShopCartController extends BaseController {

	@Autowired
	private ILabelExploreService exploreServiceImpl;

	private static final String SUCCESS = "success";

	/**
	 * 添加(规则)到购物车
	 * 
	 * 先决0.1获取参数calculationsId typeId（标签，客户群区分） 1、查询缓存中标签是否有效 //TODO
	 * 如果是修改用户群，则清空购物车 2、从缓存读取购物车数据，如果已经有标签先添加运算符例如：and or 3、根据标签拼接规则
	 * 4、查询规则条数放入缓存 5、括号的特殊处理
	 * 
	 */
	@ApiOperation(value = "添加(规则)到购物车")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "calculationsId", value = "标签ID", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "typeId", value = "类型", required = true, paramType = "query", dataType = "string") })
	@RequestMapping(value = "/saveShopSession", method = RequestMethod.POST)
	public WebResult<String> saveShopSession(HttpServletRequest req, String calculationsId, String typeId) {
		WebResult<String> webResult = new WebResult<>();
		boolean success = true;
		String msg = "";
		try {
			if (LabelRuleContants.LABEL_INFO_CALCULATIONS_TYPEID.equals(typeId)) {
				LabelInfo ciLabelInfo = CocCacheProxy.getCacheProxy().getLabelInfoById(calculationsId);
				if (StringUtil.isEmpty(ciLabelInfo.getDataDate())) {
					success = false;
					msg = "抱歉，该标签数据未准备好，不能添加到收纳篮！";
				}
				// 校验完之后的操作
				if (success) {
					msg = "加入购物车成功";
					List<LabelRuleVo> rules = getSessionLabelRuleList();
					Integer sort = 0;
					if (rules.size() > 0) {
						sort = rules.get(rules.size() - 1).getSortNum();
						sort = sort + 1;// 排序标号 ,默认规则为"and"
						rules.add(generateRule("and", LabelRuleContants.ELEMENT_TYPE_OPERATOR, sort));
					}
					sort = sort + 1;// 初始化标签规则
					LabelRuleVo rule = initLabelRule(ciLabelInfo, sort);
					rules.add(rule);

					setSessionAttribute(LabelRuleContants.SHOP_CART_RULE, JsonUtil.toJsonString(rules));
					setSessionAttribute(LabelRuleContants.SHOP_CART_RULE_NUM,
							String.valueOf(findLabelOrCustomNum(rules)));
				}
			}

		} catch (Exception e) {
			LogUtil.error("添加(规则)到购物车异常", e);
			return webResult.fail(e.getMessage());
		}
		if (success) {
			return webResult.success("加入购物车成功", SUCCESS);
		} else {
			return webResult.fail(msg);
		}
	}

	/**
	 * 更新session
	 */
	@ApiImplicitParam(name = "labelRuleStr", value = "规则串", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/updateShopSession", method = RequestMethod.POST)
	public WebResult<String> updateShopSession(HttpServletRequest req, String labelRuleStr) {
		WebResult<String> webResult = new WebResult<>();
		try {
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
			return webResult.fail(e.getMessage());
		}
		return webResult.success("更新购物车成功", SUCCESS);
	}

	/**
	 * Description: 刷新购物车页面 1、从缓存读取购物车数据 ....
	 * 
	 * @return shopCartRules：去除括号的规则列表List
	 *         <CiLabelRule> showCartRulesCount：购物车规则总数
	 * @author tianxy3
	 * @date 2017年12月14日
	 */
	@RequestMapping(value = "/findShopCart", method = RequestMethod.POST)
	public WebResult<List<LabelRuleVo>> findShopCart(HttpServletRequest request) {
		WebResult<List<LabelRuleVo>> webResult = new WebResult<>();
		List<LabelRuleVo> rules = getSessionLabelRuleList();
		return webResult.success("读取购物车数据成功", rules);
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
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dataDate", value = "日期", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "dayLabelDate", value = "数据日期(日)", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "monthLabelDate", value = "数据日期(月)", required = true, paramType = "query", dataType = "string") })
	@RequestMapping(value = "/explore", method = RequestMethod.POST)
	public WebResult<String> explore(HttpServletRequest req, String dataDate, String dayLabelDate,
			String monthLabelDate) {
		WebResult<String> webResult = new WebResult<>();
		List<LabelRuleVo> labelRules = getSessionLabelRuleList();
		ExploreQueryParam queryParam = new ExploreQueryParam(dataDate, monthLabelDate, dayLabelDate);
		String countSqlStr = null;
		try {
			countSqlStr = exploreServiceImpl.getCountSqlStr(labelRules, queryParam);
			//TODO 目前直接返回数据后期调用后台接口
		} catch (Exception e) {
			return webResult.fail(e.getMessage());
		}
		return webResult.success("探索成功", countSqlStr);

	}

	/**
	 * 删除购物车的对象
	 */
	@RequestMapping(value = "/delShopSession", method = RequestMethod.POST)
	public WebResult<String> delShopSession(HttpServletRequest req) {
		WebResult<String> webResult = new WebResult<>();
		try {
			setSessionAttribute(LabelRuleContants.SHOP_CART_RULE, "");
		} catch (Exception e) {
			LogUtil.error("删除购物车的对象异常", e);
			return webResult.fail(e.getMessage());
		}
		return webResult.success("删除购物车成功", SUCCESS);
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
		rule.setCalcuElement(String.valueOf(ciLabelInfo.getLabelId()));
		rule.setCustomOrLabelName(ciLabelInfo.getLabelName());
		rule.setMaxVal(new Double(1));
		rule.setMinVal(new Double(0));
		rule.setLabelFlag(1);// 取反标识，默认不取反
		rule.setSortNum(sort);
		rule.setLabelTypeId(ciLabelInfo.getLabelTypeId());
		rule.setElementType(LabelRuleContants.ELEMENT_TYPE_LABEL_ID);
		rule.setDataDate(ciLabelInfo.getDataDate());// 设置最新数据日期
		rule.setUpdateCycle(ciLabelInfo.getUpdateCycle());// 设置标签周期性
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
