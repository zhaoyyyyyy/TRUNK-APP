package com.asiainfo.biapp.si.coc.jauth.sysmgr.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

import com.asiainfo.biapp.si.coc.jauth.frame.controller.BaseController;
import com.asiainfo.biapp.si.coc.jauth.frame.json.JSONResult;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.frame.util.StringUtil;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.DicData;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.DicDataService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.DicDataVo;
@Api(value = "数据字典管理")
@RequestMapping("api/datadic")
@RestController
public class DicDataController extends BaseController<DicData>{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DicDataService dicDataService;
	
	@Override
	protected BaseService<DicData, String> getBaseService() {
		return dicDataService;
	}
	
	/**
	 * 去新增/编辑页面
	 * @return
	 */
	@ApiOperation(value="查询得到信息")
	@ApiImplicitParam(name = "id", value = "字典ID", required = true, paramType = "query" ,dataType = "string")
	@RequestMapping(value="/get", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> toDataDicDetail(String id){
		Map<String,Object> map = new HashMap<>();
		DicData dicData = new DicData();
		if(!StringUtil.isEmpty(id)){
			dicData = dicDataService.get(id);
		}
		map.put("dicData", dicData);
		return map;
		
	}
	
	@ApiOperation(value="保存数据字典内容")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "字典ID", required = false, paramType = "query" ,dataType = "string"),
		@ApiImplicitParam(name = "code", value = "编码", required = true, paramType = "query" ,dataType = "string"),
		@ApiImplicitParam(name = "dataName", value = "名称", required = true, paramType = "query" ,dataType = "string"),
		@ApiImplicitParam(name = "note", value = "备注", required = true, paramType = "query" ,dataType = "string"),
		@ApiImplicitParam(name = "parentId", value = "父字典ID", required = true, paramType = "query" ,dataType = "string")
	})
	@RequestMapping(value="/save", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public String save(DicData dicData){
		int i = 0;
		String id=dicData.getId();
		if(StringUtils.isNotBlank(id)){
			DicData dicdata=dicDataService.get(id);
			List<DicData> ifdata = dicDataService.findDataListByDicCode(dicData.getDicCode());
			for(DicData ida : ifdata){
				if(dicData.getCode().equals(ida.getCode())&&!dicData.getCode().equals(dicdata.getCode())){
					i=1;
					break;
				}
			}
			if(i==1){
				return "haveCode";
			}
			dicdata.setCode(dicData.getCode());
			dicdata.setDataName(dicData.getDataName());
			dicdata.setNote(dicData.getNote());
			dicdata.setStatus("1");
			dicDataService.update(dicdata);
			return "success";
		}
		String dicCode=request.getParameter("dicCode");
		DicData newdata = new DicData();
		List<DicData> ifdata = dicDataService.findDataListByDicCode(dicData.getDicCode());
		for(DicData ida : ifdata){
			if(dicData.getCode().equals(ida.getCode())){
				i=1;
				break;
			}
		}
		if(i==1){
			return "haveCode";
		}
		newdata.setDicCode(dicCode);
		newdata.setCode(dicData.getCode());
		newdata.setDataName(dicData.getDataName());
		newdata.setNote(dicData.getNote());
		newdata.setStatus("1");
		dicDataService.save(newdata);
		return "success";
	}
	
	/**
	 * 通过参数查询字典分页
	 * @return 
	 * @return
	 */
	@ApiOperation(value="显示数据字典的列表（分页形式）")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "cols", value = "列名称", required = true, paramType = "query" ,dataType = "string" ,defaultValue="code,dataName,note,id"),
		@ApiImplicitParam(name = "code", value = "编码", required = false, paramType = "query" ,dataType = "string"),
		@ApiImplicitParam(name = "dataName", value = "名称", required = false, paramType = "query" ,dataType = "string"),
		@ApiImplicitParam(name = "note", value = "备注", required = false, paramType = "query" ,dataType = "string"),
		@ApiImplicitParam(name = "dicCode", value = "字典编码", required = false, paramType = "query" ,dataType = "string"),
	})
	@RequestMapping(value="/dicdataPage/query", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public String findDicDataPageByParams(@ModelAttribute JQGridPage<DicData> page,String cols,@ApiIgnore DicDataVo dicDataVo){
		if(dicDataVo.getDicCode().contains(",")){
			dicDataVo.setDicCode((dicDataVo.getDicCode().split(","))[0]);
		}
		JQGridPage<DicData> dicDataList=dicDataService.findDicDataList(page, dicDataVo);
		return JSONResult.page2Json(dicDataList, cols);
	}
	
	
	/**
	 * 通过字典编码等参数，查询字典数据内容
	 * @return 
	 * @return
	 */
	@ApiOperation(value="通过字典编码等参数，查询字典数据内容")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "dicCode", value = "字典编码", required = false, paramType = "query" ,dataType = "string"),
		@ApiImplicitParam(name = "code", value = "字典数据编码", required = false, paramType = "query" ,dataType = "string"),
		@ApiImplicitParam(name = "dataName", value = "字典数据名称", required = false, paramType = "query" ,dataType = "string"),
		@ApiImplicitParam(name = "note", value = "备注", required = false, paramType = "query" ,dataType = "string"),
	})
	@RequestMapping(value="/dicdatas/query", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public List<DicData> findDicDataByParams(@ApiIgnore DicDataVo dicDataVo){
		
		JQGridPage<DicData> page = new JQGridPage<>();
		JQGridPage<DicData> dicDataList = dicDataService.findDicDataList(page, dicDataVo);
		return dicDataList.getData();
	}
	
	@ApiOperation(value="删除")
	@RequestMapping(value="/delete", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public void delete(String id){
		dicDataService.delete(id);
	}

}

