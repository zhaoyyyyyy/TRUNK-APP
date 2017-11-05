package com.asiainfo.biapp.si.loc.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.auth.model.DicData;
import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.auth.service.IDicDataService;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Title : 数据字典控制层
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年11月5日    Administrator        Created</pre>
 * <p/>
 *
 * @author  zhougz3
 * @version 1.0.0.2017年11月5日
 */
@Api(value = "字典获取")
@RequestMapping("api/dicData")
@RestController
public class DicDataController extends BaseController<DicData>{

	
	@Autowired
	private IDicDataService dicDataService;
	
	/**
	 * 
	 * Description: 通过字典编码等参数，查询字典数据内容
	 *
	 * @param code
	 * @return 
	 */
	@ApiOperation(value="通过字典编码等参数，查询字典数据内容")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "字典数据编码", required = false, paramType = "query" ,dataType = "string")
	})
	@RequestMapping(value="/queryList", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public WebResult<List<DicData>> findDataListByCode(String code){
		WebResult<List<DicData>> webResult = new WebResult<List<DicData>>();
		
		List<DicData> dicDataList = null;
		try {
			dicDataList = dicDataService.queryDataListByCode(code);
		} catch (BaseException e) {
			return webResult.fail(e);
		}
		return webResult.success("获取数据字典成功.",dicDataList );
	}
	
}
