package com.asiainfo.biapp.si.coc.jauth.sysmgr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.coc.jauth.frame.controller.BaseController;
import com.asiainfo.biapp.si.coc.jauth.frame.json.JSONResult;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Coconfig;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.DicData;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.CoconfigService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.DicDataService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.CoconfigVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author zhangnan
 * @date 2017年9月27日 下午2:32:56
 */
@Api(value = "配置管理接口", description = "配置新建，编辑，查询，删除等")
@RequestMapping("api/config")
@RestController
public class ConfigController extends BaseController<Coconfig> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CoconfigService coconfigService;
	@Autowired
	private DicDataService dicDataService;

	@Override
	protected BaseService<Coconfig, String> getBaseService() {
		return coconfigService;
	}

	/**
	 * @describe 开始构造树
	 * @author zhangnan
	 * @date 2017年9月27日 下午2:34:34
	 */
	@ApiOperation(value = "构造树")
	@ApiImplicitParam(name = "coKey", value = "配置key", required = false, paramType = "query", dataType = "string")
	@RequestMapping(value = "/tree", method = RequestMethod.POST, produces = { MediaType.ALL_VALUE })
	public String renderOrgTree(String coKey) {
		if (StringUtils.isNotBlank(coKey)) {
			StringBuffer htmlC = new StringBuffer();
			Coconfig coconfig = coconfigService.getCoconfigByKey(coKey);
			return getSubTree(coconfig.getChildren(), htmlC);
		} else {
			Coconfig coconfig = coconfigService.getCoconfigByKey("Config_Center");
			StringBuffer html = new StringBuffer();
			return getTree(coconfig, html);
		}
	}

	// 子节点
	private String getTree(Coconfig coconfig, StringBuffer html) {
		html.append("<li id='").append(coconfig.getConfigKey()).append("' name='").append(coconfig.getConfigKey())
				.append("' selectable='true'><span class='text'>").append(coconfig.getConfigName()).append("</span>");
		html.append("</span><ul  class='ajax'>");
		html.append("<li>{url: " + this.getRequest().getContextPath() + "/api/config/tree?coKey="
				+ coconfig.getConfigKey() + "}</li></ul></li>");
		return html.toString();
	}

	// 最小节点
	private String getLeaf(Coconfig coconfig, StringBuffer htmlCon) {
		htmlCon.append("<li id='").append(coconfig.getConfigKey()).append("' name='").append(coconfig.getConfigKey())
				.append("' selectable='true'");
		htmlCon.append(">");
		htmlCon.append("<span class='text'>");
		htmlCon.append(coconfig.getConfigName());
		htmlCon.append("</span>");
		htmlCon.append("</li>");
		return htmlCon.toString();
	}

	private String getSubTree(Set<Coconfig> set, StringBuffer htmlCon) {
		for (Coconfig coconfig : set) {
			if (!coconfig.getChildren().isEmpty()) {
				getTree(coconfig, htmlCon);
			} else {
				getLeaf(coconfig, htmlCon);
			}
		}
		return htmlCon.toString();
	}

	// 得到配置信息
	@ApiOperation(value = "通过Key查询")
	@ApiImplicitParam(name = "coKey", value = "配置key", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> get(String coKey) {
		Map<String, Object> map = new HashMap<>();
		Coconfig coconfig = coconfigService.getCoconfigByKey(coKey);
		map.put("config", coconfig);
		return map;
	}
	
	// 得到配置子信息
	@ApiOperation(value = "通过parentKey查询")
    @ApiImplicitParam(name = "parentCode", value = "配置key", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/getChild", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Coconfig> getChildren(String parentCode){
	    List<Coconfig> conList = coconfigService.getCoconfigByParentKey(parentCode);
	    for(int i=0;i<conList.size();i++){
	        conList.get(i).setChildren(null);
	    }
	    return conList;
	}

	// 得到列表并分页
	@ApiOperation(value = "分页显示列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "cols", value = "列名称", required = true, paramType = "query", dataType = "string", defaultValue = "configName,configKey,configVal,configDesc,configValType,configKey"),
			@ApiImplicitParam(name = "configName", value = "配置名称", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "configKey", value = "配置编码", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "configDesc", value = "配置值", required = false, paramType = "query", dataType = "string") })
	@RequestMapping(value = "/configPage/query", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String list(@ModelAttribute JQGridPage<Coconfig> page, @ApiIgnore CoconfigVo coconfigVo, String cols) {
		String coKey = request.getParameter("parentKey");
		coconfigVo.setParentKey(coKey);
		JQGridPage<Coconfig> coconfigList = coconfigService.findCoconfigList(page, coconfigVo);
		return JSONResult.page2Json(coconfigList, cols);
	}

	// 新增或修改
	@ApiOperation(value = "保存")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "configKey", value = "配置key", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "configVal", value = "配置值", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "configValType", value = "配置值类型", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "configName", value = "配置名称", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "configDesc", value = "配置描述", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "isEdit", value = "是否编辑(0为新建，1为编辑)", required = true, paramType = "query", dataType = "string") })
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String save(@ApiIgnore Coconfig coconfig) {
		String isEdit = request.getParameter("isEdit");
		String[] conKeys = coconfig.getConfigKey().split(",");// 分割Key
		if (conKeys.length == 1) {// 判断是否为同时添加多条数据
			Coconfig oldCon = coconfigService.getCoconfigByKey(conKeys[0]);
			if (null != oldCon) {
				if ("1".equals(isEdit)) {// 编辑
					oldCon.setConfigName(coconfig.getConfigName());
					oldCon.setConfigDesc(coconfig.getConfigDesc());
					oldCon.setConfigVal(coconfig.getConfigVal());
					coconfigService.saveOrUpdate(oldCon);
				}
				if ("0".equals(isEdit)) {
					return "The key has already exited";
				}
			} else {// 新建
				coconfig.setConfigKey(coconfig.getParentKey()+"_"+conKeys[0]);
				coconfig.setStatus(1);
				coconfig.setSysId(coconfig.getParentKey());
				if ("taskConfig".equals(coconfig.getParentKey())) {// 调度任务配置添加到数据字典中
					List<DicData> dataList = dicDataService.findDataListByDicCode("DDRW");
					int count = dataList.size();
					DicData newdata = new DicData();
					newdata.setDicCode("DDRW");
					newdata.setCode(Integer.toString(count + 1));
					newdata.setDataName(coconfig.getConfigName());
					newdata.setNote(coconfig.getConfigDesc());
					newdata.setStatus("1");
					dicDataService.save(newdata);
				}
				coconfigService.saveOrUpdate(coconfig);
			}
			return "success";
		} else {
			String[] conVals = coconfig.getConfigVal().split(",");
			String[] conTypes = coconfig.getConfigValTypes().split(",");
			String[] conNames = coconfig.getConfigName().split(",");
			for (int i = 0; i < conKeys.length; i++) {// 判断表中是否存在相同编码
				for (int k = 0; k < conKeys.length; k++) {// 判断将要插入数据中是否存在相同编码
					if (conKeys[i].equals(conKeys[k]) && i != k) {
						return "Please don't input the same Key";
					} else {
						continue;
					}
				}
				if (null != coconfigService.getCoconfigByKey(conKeys[i])) {
					return "Line " + (i + 1) + " has already exited";
				}
			}
			for (int i = 0; i < conKeys.length; i++) {// 添加新配置
				Coconfig newCon = new Coconfig();
				newCon.setConfigName(conNames[i]);
				newCon.setConfigKey(coconfig.getParentKey()+"_"+conKeys[i]);
				newCon.setConfigVal(conVals[i]);
				newCon.setConfigValType(Integer.parseInt(conTypes[i]));
				newCon.setParentKey(coconfig.getParentKey());
				newCon.setStatus(1);
				newCon.setSysId("1");//TODO SYS_ID具体如何保存
				coconfigService.saveOrUpdate(newCon);
			}
			return "success";
		}
	}

	// 删除
	@ApiOperation(value = "删除配置")
	@ApiImplicitParam(name = "key", value = "配置key", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(String key) {
		Coconfig co = coconfigService.getCoconfigByKey(key);
		coconfigService.delete(key);
		if ("taskConfig".equals(co.getParentKey())) {// 如果是任务配置则同时删除数据字典中的词条
			List<DicData> taskDataList = dicDataService.findDataListByDicCode("DDRW");
			for (DicData dic : taskDataList) {
				if (co.getConfigName().equals(dic.getDataName())) {
					dicDataService.delete(dic.getId());
				}
			}
		}
	}

}
