/*
 * @(#)DimTargetTableStatusDaoImp.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.auth.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.auth.service.ICoConfigService;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.JauthServerException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.utils.HttpUtil;

/**
 * Title : CoConfigServiceImpl
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
 * 1    2017年11月15日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月15日
 */
@Service
@Transactional
public class CoConfigServiceImpl implements ICoConfigService {

    @Value("${jauth-url}")
    private String jauthUrl;

    /**
     * 通过编码取得一组子节点 Description:
     *
     * @param parentCode
     * @return
     */
    public Map<String, String> getPropertiesByParentCode(String parentCode, String token) throws BaseException {
        if (StringUtils.isBlank(parentCode)) {
            throw new ParamRequiredException("编码不能为空");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("parentCode", parentCode);
        map.put("token", token);
        String config = "";
        try {
        	config = HttpUtil.sendPost(jauthUrl + "/api/config/getChild", map);
		} catch (Exception e) {
			throw new JauthServerException("批量获取配置信息异常");
		}
       
        Map<String, String> returnMap = new HashMap<>();
        String str1 = config.replaceAll("\\[", "").replaceAll("\\]", "");
        String[] strs = str1.split("},");
        for (int i = 0; i < strs.length; i++) {
            if (StringUtils.isBlank(strs[i])) {
                continue;
            }
            JSONObject jsobj = JSONObject.fromObject(strs[i] += "}");
            returnMap.put(jsobj.getString("configKey"), jsobj.getString("configVal"));
        }
        return returnMap;
    }

    /**
     * 通过编码取得值 Description:
     *
     * @param code
     * @return
     */
    public String getProperties(String code, String token) throws BaseException {
        if (StringUtils.isBlank(code)) {
            throw new ParamRequiredException("编码不能为空");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("coKey", code);
        map.put("token", token);
        String configValue = "";
        try {
        	configValue = HttpUtil.sendPost(jauthUrl + "/api/config/get", map);
		} catch (Exception e) {
			throw new JauthServerException("批量配置信息异常");
		}
        
        String value = "";
        Object returna = JSONObject.fromObject(configValue).get("config");
        if (!(returna instanceof JSONNull)) {
            JSONObject jsObject = (JSONObject) returna;
            value = jsObject.getString("configVal");
        } else {
            value = null;
        }
        return value;
    }
    
    
    public Map<String, String> selectAll() throws BaseException {
        Map<String, Object> map = new HashMap<>();
        String config = "";
        try {
            config = HttpUtil.sendPost(jauthUrl + "/api/config/queryList", map);
        } catch (Exception e) {
            throw new JauthServerException("批量获取配置信息异常");
        }
       
        Map<String, String> returnMap = new HashMap<>();
        String str1 = config.replaceAll("\\[", "").replaceAll("\\]", "");
        String[] strs = str1.split("},");
        for (int i = 0; i < strs.length; i++) {
            if (StringUtils.isBlank(strs[i])) {
                continue;
            }
            JSONObject jsobj = JSONObject.fromObject(strs[i] += "}");
            returnMap.put(jsobj.getString("configKey"), jsobj.getString("configVal"));
        }
        return returnMap;
    }

}
