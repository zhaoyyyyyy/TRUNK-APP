
package com.asiainfo.biapp.si.loc.core.label.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelRule;
import com.asiainfo.biapp.si.loc.core.label.model.ExploreQueryParam;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelRuleService;
import com.asiainfo.biapp.si.loc.core.label.service.impl.LabelExploreServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
public class ShopCartController {

	@Autowired
	private ILabelRuleService labelRuleService;

	@Autowired
	private LabelExploreServiceImpl exploreServiceImpl;

	private static final Logger LOGGER = LoggerFactory.getLogger(ShopCartController.class);

	/**
	 * 添加(规则)到购物车
	 * 
	 * 先决0.1获取参数calculationsId typeId（标签，客户群区分） 1、查询缓存中标签是否有效 //TODO
	 * 如果是修改用户群，则清空购物车 2、从缓存读取购物车数据，如果已经有标签先添加运算符例如：and or 3、根据标签拼接规则
	 * 4、查询规则条数放入缓存 5、括号的特殊处理
	 * 
	 */
	@RequestMapping(value = "/saveShopSession", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveShopSession(HttpServletRequest req) {
		String calculationsId = req.getParameter("calculationsId");
		String typeId = req.getParameter("typeId");

		return null;
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
	@ResponseBody
	public Map<String, Object> findShopCart(HttpServletRequest request) {
		// HttpSession session = request.getSession();
		// List<CiLabelRule> rules = (List<CiLabelRule>)
		// session.getAttribute(ServiceConstants.SHOP_CART_RULE);
		// TODO 从缓存查询
		List<LabelRule> rules = new ArrayList<>();

		return null;
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
	@ApiOperation(value = "测试")
	@ApiImplicitParam(name = "labelId", value = "ID", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/explore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> explore(HttpServletRequest req, String labelId) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<LabelRule> labelRules = new ArrayList<>();//TODO 
		LabelRule labelRule = labelRuleService.get("4028ac815fe2cbf1015fe2ee2a160003");
		labelRules.add(labelRule);
		
		ExploreQueryParam queryParam=new ExploreQueryParam();
		queryParam.setDayDate("20171219");
		String countSqlStr = exploreServiceImpl.getCountSqlStr(labelRules, queryParam);
		result.put("countSqlStr", countSqlStr);
		return result;
	}

	/**
	 * 更新session
	 */
	@RequestMapping(value = "/updateShopSession", method = RequestMethod.POST)
	@ResponseBody
	public String updateShopSession(HttpServletRequest req) {
		// CiLabelRuleVo ciLabelRuleVo

		return null;
	}

	/**
	 * 删除购物车的对象
	 */
	@RequestMapping(value = "/delShopSession", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delShopSession(HttpServletRequest req) {

		return null;
	}

}
