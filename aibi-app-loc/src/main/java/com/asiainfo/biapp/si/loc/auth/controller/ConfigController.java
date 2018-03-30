package com.asiainfo.biapp.si.loc.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.auth.model.DicData;
import com.asiainfo.biapp.si.loc.base.LocCacheBase;
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
 * @version 1.0.0.2018年01月30日
 */
@Api(value = "003.01->-配置项获取")
@RequestMapping("api/config")
@RestController
public class ConfigController extends BaseController<DicData>{

	@Autowired  
	private Environment env;  
	
	/**
	 * 
	 * Description: 通过字典编码等参数，查询字典数据内容
	 *
	 * @param code
	 * @return 
	 */
	@ApiOperation(value="通过spring的配置项的key得到配置值")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "key", value = "配置项键", required = false, paramType = "query" ,dataType = "string")
	})
	@RequestMapping(value="/springConfig", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public WebResult<String> getSpringConfigByKey(String key){
		WebResult<String> webResult = new WebResult<String>();
		String value = "";
		try {
			value = env.getProperty(key);
		} catch (Exception e) {
			return webResult.fail("通过spring的配置项的key得到配置值失败");
		}
		return webResult.success("通过spring的配置项的key得到配置值成功",value );
	}
	
	/**
	 * 
	 * Description: 通过字典编码等参数，查询字典数据内容
	 *
	 * @param code
	 * @return 
	 */
	@ApiOperation(value="通过spring的配置项的key得到配置值")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "key", value = "配置项键", required = false, paramType = "query" ,dataType = "string")
	})
	@RequestMapping(value="/jauthConfig", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
	public WebResult<String> getJauthConfigByKey(String key){
		WebResult<String> webResult = new WebResult<String>();
		String value = "";
		try {
			value = LocCacheBase.getInstance().getSysConfigInfoByKey(key);
		} catch (Exception e) {
			return webResult.fail("通过jauth的配置项的key得到配置值失败");
		}
		return webResult.success("通过jauth的配置项的key得到配置值成功",value );
	}
}
