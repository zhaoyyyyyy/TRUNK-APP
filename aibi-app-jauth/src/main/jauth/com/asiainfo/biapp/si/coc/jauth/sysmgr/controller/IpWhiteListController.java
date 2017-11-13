
package com.asiainfo.biapp.si.coc.jauth.sysmgr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.coc.jauth.frame.controller.BaseController;
import com.asiainfo.biapp.si.coc.jauth.frame.json.JSONResult;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.IpWhiteList;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.IpWhiteListService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.utils.IpVerifyUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Title : IpWhiteListController
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年10月23日    chenchao3        Created
 * </pre>
 * <p/>
 *
 * @author chenchao3
 * @version 5.0.0.2017年10月23日
 */
@Api(value = "IP白名单管理")
@RequestMapping("api/ipWhiteList")
@RestController
public class IpWhiteListController extends BaseController<IpWhiteList> {
	@Autowired
	private IpWhiteListService ipwhitelistService;

	@Override
	protected BaseService<IpWhiteList, String> getBaseService() {
		return ipwhitelistService;
	}

	/**
	 * Description: IP白名单查询接口
	 * 
	 * @param ipAdress
	 *            IP地址
	 * @param requestAddress
	 *            请求地址
	 * @return
	 */
	@ApiOperation(value = "显示白名单列表（不分页）")
	@ApiImplicitParam(name="cols",value="列名称",required=true,paramType="query",dataType="string",defaultValue="ipAddress,requestAddress,listId")
	@RequestMapping(value = "/whitelist/query", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String queryIpWhiteList(String cols) {
		List<IpWhiteList> ipWhiteLists = ipwhitelistService.findIpWhiteList();
		return JSONResult.list2JsonAsPage(ipWhiteLists, cols);
	}

	/**
	 * Description: 进入IP白名单编辑接口
	 * 
	 * @param whiteListId
	 *            白名单id
	 * @return
	 */
	@ApiOperation(value="进入白名单编辑接口（回显数据）")
	@ApiImplicitParam(name="whiteListId",value="白名单id",required=true,paramType="query",dataType="integer")
	@RequestMapping(value = "/whitelistInfo/query", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> initIpWhiteList(Integer whiteListId) {
		Map<String, Object> map = new HashMap<String, Object>();
		IpWhiteList ipWhiteList = ipwhitelistService.get(whiteListId);
		map.put("ipwhitelist", ipWhiteList);
		return map;
	}
	
	/**
	 * Description: IP白名单保存接口
	 * 
	 * @param ipAdress
	 *            IP地址
	 * @param requestAddress
	 *            地址
	 * @return
	 */
	@ApiOperation(value = "保存")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "ipAddress", value="ip地址", required=true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "requestAddress", value="请求地址", required=true, paramType = "query", dataType = "string")
    })
	@RequestMapping(value = "/whitelist/save", method = RequestMethod.POST)
	public String addIpWhiteList(IpWhiteList ipWhiteList) {
		IpWhiteList newIpWhiteList = new IpWhiteList();
		newIpWhiteList.setIpAddress(ipWhiteList.getIpAddress());
		newIpWhiteList.setRequestAddress(ipWhiteList.getRequestAddress());
		ipwhitelistService.save(newIpWhiteList);
		return "success";
	}

	/**
	 * Description: IP白名单修改接口
	 * 
	 * @param listId
	 *            白名单ID
	 * @param ipAdress
	 *            IP地址
	 * @param requestAddress
	 *            地址
	 * @return
	 */
	@ApiOperation(value="白名单修改接口")
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "ipAddress", value="ip地址", required=true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "requestAddress", value="请求地址", required=true, paramType = "query", dataType = "string")
	})
	@RequestMapping(value = "/whitelist/update", method = RequestMethod.POST)	
	public String updateIpWhiteList(IpWhiteList ipWhiteList) {
	    IpWhiteList oldIpWhiteList=ipwhitelistService.get(ipWhiteList.getListId());
	    oldIpWhiteList.setIpAddress(ipWhiteList.getIpAddress());
	    oldIpWhiteList.setRequestAddress(ipWhiteList.getRequestAddress());
	    ipwhitelistService.update(oldIpWhiteList);
		return "success";
	}

	/**
	 * Description: IP白名单删除接口
	 * 
	 * @param whiteListId
	 *            白名单ID
	 * @return
	 */
	@ApiOperation(value = "删除")
	@ApiImplicitParam(name="whiteListId",value="白名单id",required=true,paramType="query",dataType="integer")
	@RequestMapping(value = "/whitelist/delete", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public void deleteIpWhiteList(Integer whiteListId) {
		ipwhitelistService.delete(whiteListId);
	}

	/**
	 * Description: IP白名单校验接口
	 * 
	 * @param ipAdress
	 *            IP地址
	 * @param requestAddress
	 *            地址
	 * @return
	 */
	@ApiOperation(value = "校验白名单")
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "ipAddress", value="ip地址", required=true, paramType = "query", dataType = "string"),
	    @ApiImplicitParam(name = "requestAddress", value="请求地址", required=true, paramType = "query", dataType = "string")
	})
	@RequestMapping(value = "/whitelist/verify", method = RequestMethod.POST)
	public String verifyIpWhiteList(String ipAddress, String requestAddress) {
		List<IpWhiteList> ipWhiteLists = ipwhitelistService.findIpWhiteList();
		for (IpWhiteList ipWhiteList : ipWhiteLists) {
		    String ipAddressconfig = ipWhiteList.getIpAddress();
		    boolean ipflag = IpVerifyUtil.checkLoginIP(ipAddress, ipAddressconfig);
		    if (ipflag) {
                String requestAddrconfig = ipWhiteList.getRequestAddress();
                boolean reqflag = IpVerifyUtil.checkreqAddres(requestAddress, requestAddrconfig);
                if (reqflag) {
                    return "success";
                }
            }
		    return "refuse";
		}
		return "refuse";
	}
}