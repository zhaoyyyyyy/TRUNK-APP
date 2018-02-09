package com.asiainfo.biapp.si.loc.auth.utils;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.JauthServerException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.utils.HttpUtil;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

public class LocConfigUtil {

    private String jauthUrl;
    
    private String tokenStr = "getALLKV";
    
    private static volatile LocConfigUtil instance = null;
	
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
        map.put("token", this.tokenStr);
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
    public String getProperties(String code) throws BaseException {
        if (StringUtils.isBlank(code)) {
            throw new ParamRequiredException("编码不能为空");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("coKey", code);
        map.put("token", this.tokenStr);
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
        map.put("token", this.tokenStr);
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



	public static void main(String[] args) throws Exception {
		System.out.println(LocConfigUtil.getInstance("http://127.0.0.1:8440/jauth").getProperties("SYSConfig_REDIS_IP"));
		System.out.println(LocConfigUtil.getInstance("http://127.0.0.1:8440/jauth").selectAll());
	}

}
