/*
 * @(#)CoConfigUtils.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.auth.utils;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.asiainfo.biapp.si.loc.auth.service.ICoConfigService;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;

/**
 * Title : CoConfigUtils
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
public class CoConfigUtils {

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
        ICoConfigService coConfigService = (ICoConfigService) SpringContextHolder.getBean("coConfigService");
        return coConfigService.getPropertiesByParentCode(parentCode, token);
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
        ICoConfigService coConfigService = (ICoConfigService) SpringContextHolder.getBean("coConfigService");
        return coConfigService.getProperties(code, token);
    }

}
