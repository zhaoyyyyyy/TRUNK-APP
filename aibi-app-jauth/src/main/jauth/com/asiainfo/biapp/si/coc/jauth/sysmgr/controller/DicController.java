package com.asiainfo.biapp.si.coc.jauth.sysmgr.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.asiainfo.biapp.si.coc.jauth.frame.util.StringUtil;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Dic;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.DicData;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.DicDataService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.DicService;

@Api(value = "数据字典管理")
@RequestMapping("api/datadic/dic")
@RestController
public class DicController extends BaseController<Dic>{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DicService dicService ;
	@Autowired
	private DicDataService dicDataService ;
	
	@Override
	protected BaseService<Dic, String> getBaseService() {
		return dicService;
	}
	/**
	 * @describe 通过参数查询字典分页
	 * @author zhougz
	 * @param
	 * @date 2013-6-4
	 */
	@ApiOperation(value="显示数据字典的列表（分页形式）")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "cols", value = "列名称", required = true, paramType = "query" ,dataType = "string" ,defaultValue="dicName,dicCode,dicType,note,id"),
		@ApiImplicitParam(name = "dicName", value = "名称", required = false, paramType = "query" ,dataType = "string"),
		@ApiImplicitParam(name = "dicType", value = "类型", required = false, paramType = "query" ,dataType = "string"),
		@ApiImplicitParam(name = "dicCode", value = "字典编码", required = false, paramType = "query" ,dataType = "string"),
	})
	@RequestMapping(value="/dicPage/query", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public void findDicPageByParams(@ModelAttribute JQGridPage<Dic> page,String cols){
		
		//得到前端传入的参数
		HttpServletRequest request = this.getRequest();
		String dicName = request.getParameter("dicName");
		String dicCode = request.getParameter("dicCode");
		String dicType = request.getParameter("dicType");
		
		//拼装hql 及参数
		Map<String,Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		hql.append("from Dic where 1=1 ");
		if(!StringUtil.isEmpty(dicName)){
			hql.append(" and dicName like :dicName");
			params.put("dicName", "%"+dicName+"%");
		}
		if(!StringUtil.isEmpty(dicCode)){
			hql.append(" and dicCode =:dicCode");
			params.put("dicCode", dicCode);
		}
		if(!StringUtil.isEmpty(dicType)){
			hql.append(" and dicType = :dicType");
			params.put("dicType", dicType);
		}
		if(StringUtils.isNotBlank(page.getSortCol())){
			hql.append(" order by "+page.getSortCol()+" "+page.getSortOrder());
		}
		//调用业务逻辑层
		JQGridPage<Dic> dicList = (JQGridPage<Dic>) dicService.findPageByHql(page, hql.toString(), params);
		this.renderText(JSONResult.page2Json(dicList, cols));
		
	}

	
	/**
	 * @describe 编辑字典索引
	 * @author zhougz
	 * @param
	 * @date 2013-9-12
	 */
	@ApiOperation(value="根据ID查询字典")
	@ApiImplicitParam(name = "id", value = "字典ID", required = true, paramType = "query" ,dataType = "string")
	@RequestMapping(value="/get", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> toDicDetail(String id){
		Dic dic = new Dic();
		if(!StringUtil.isEmpty(id)){
			dic = dicService.get(id);
		}
		Map<String,Object> map = new HashMap<>();
		map.put("dic", dic);
		return map;
		
	}
	
	@ApiOperation(value="保存数据字典内容")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "字典ID", required = false, paramType = "query" ,dataType = "string"),
		@ApiImplicitParam(name = "code", value = "编码", required = false, paramType = "query" ,dataType = "string"),
		@ApiImplicitParam(name = "dicName", value = "名称", required = false, paramType = "query" ,dataType = "string"),
		@ApiImplicitParam(name = "note", value = "备注", required = false, paramType = "query" ,dataType = "string"),
		@ApiImplicitParam(name = "dicType", value = "类型", required = false, paramType = "query" ,dataType = "string")
	})
	@RequestMapping(value="/save", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public String save(Dic dic){
		String id=dic.getId();
		if(StringUtils.isNotBlank(id)){
			Dic dicd=dicService.get(id);
			String dicCo=dicd.getDicCode();
			List<Dic> diclist = dicService.findDicListByDic(dic.getDicCode());
			if(diclist.isEmpty()){
				dicd.setDicCode(dic.getDicCode());
			}else if(!dicd.getDicCode().equals(dic.getDicCode())){
				return "haveCode";
			}
			dicd.setDicName(dic.getDicName());
			dicd.setNote(dic.getNote());
			dicd.setDicType(dic.getDicType());
			dicService.update(dicd);
			//更改字典详情的字典代码
			List<DicData> dicD=dicDataService.findDataListByDicCode(dicCo);
			if(!dicD.isEmpty()){
				for(DicData dD:dicD){
					dD.setDicCode(dicd.getDicCode());
					dicDataService.update(dD);
				}
			}
			return "success";
		}
		Dic newdic = new Dic();
		List<Dic> ifdata = dicService.findDicListByDic(dic.getDicCode());
		if(ifdata.isEmpty()){
			newdic.setDicCode(dic.getDicCode());
		}else{
			return "haveCode";
		}
		newdic.setDicCode(dic.getDicCode());
		newdic.setDicName(dic.getDicName());
		newdic.setNote(dic.getNote());
		newdic.setDicType(dic.getDicType());
		dicService.save(newdic);
		return "success";
	}
	
}
