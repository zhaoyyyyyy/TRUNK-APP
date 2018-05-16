package com.asiainfo.biapp.si.loc.auth.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.JauthServerException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.exception.UserAuthException;
import com.asiainfo.biapp.si.loc.base.utils.HttpUtil;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

public class LocConfigUtil {

    private String jauthUrl;
    
    private String tokenStr ;
    
    private static volatile LocConfigUtil instance = null;
	
    
    

	//自动登录标识
    protected String autoLoginSign = "xm8EV6Hy5RMFK4EEACIDAwQus" ;
	
	public String getTokenStr() throws BaseException {
		if(tokenStr == null){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("username", "sys_loc");
			map.put("password", autoLoginSign);
			try{
				String	tokenStr = HttpUtil.sendPost(jauthUrl+"/api/auth/login", map);
				JSONObject jsObject = JSONObject.fromObject(tokenStr);
				return jsObject.getString("token");
			}catch(Exception e){
				throw new UserAuthException("错误的用户名/密码");
			}
		}
		return tokenStr;
	}


	public static LocConfigUtil getInstance(String jauthUrl) {
		if(instance == null) {
			synchronized(LocConfigUtil.class){
	            if(instance == null) {
	               instance = new LocConfigUtil(jauthUrl);
	            }
	         }
	      }
		return instance;
	}
    
    public LocConfigUtil(String ijauthUrl){
    	this.jauthUrl=ijauthUrl;
    }

    /**
     * 通过编码取得一组子节点 Description:
     *
     * @param parentCode
     * @return
     */
    public Map<String, String> getPropertiesByParentCode(String parentCode) throws BaseException {
        if (StringUtils.isBlank(parentCode)) {
            throw new ParamRequiredException("编码不能为空");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("parentCode", parentCode);
        map.put("token", getTokenStr());
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
            strs[i] += "}";
            JSONObject jsobj = JSONObject.fromObject(strs[i]);
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
    public String getProperties(String code) throws BaseException {
        if (StringUtils.isBlank(code)) {
            throw new ParamRequiredException("编码不能为空");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("coKey", code);
        map.put("token", getTokenStr());
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
        map.put("token", getTokenStr());
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
