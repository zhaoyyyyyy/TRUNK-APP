/*
 * @(#)CoConfigController.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.auth.service.ICoConfigService;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;

/**
 * Title : CoConfigController
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
 * 1    2017年11月9日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月9日
 */
@Api(value = "配置管理")
@RequestMapping("api/config")
@RestController
public class CoConfigController {

    @Autowired
    private ICoConfigService iCoConfigService;

    /**
     * 通过parentCode拿到配置 Description:
     *
     * @param parentCode
     * @param token
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentCode", value = "编码", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "token", value = "token", required = true, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/getChild", method = RequestMethod.POST)
    public WebResult<Map<String, String>> getPropertiesByParentCode(String parentCode, String token) {
        WebResult<Map<String, String>> webResult = new WebResult<>();
        Map<String, String> map = new HashMap<>();
        try {
            map = iCoConfigService.getPropertiesByParentCode(parentCode, token);
        } catch (BaseException e) {
            webResult.fail(e);
        }
        return webResult.success("获取配置成功", map);
    }

    /**
     * 通过配置编码拿value Description:
     *
     * @param code
     * @param token
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "编码", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "token", value = "token", required = true, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public WebResult<String> getProperties(String code, String token) {
        WebResult<String> webResult = new WebResult<>();
        String value = "";
        try {
            value = iCoConfigService.getProperties(code, token);
            if (StringUtils.isBlank(value)) {
                return webResult.fail("不存在的编码");
            }
        } catch (BaseException e) {
            webResult.fail(e);
        }
        return webResult.success("获取VALUE成功", value);
    }

}
