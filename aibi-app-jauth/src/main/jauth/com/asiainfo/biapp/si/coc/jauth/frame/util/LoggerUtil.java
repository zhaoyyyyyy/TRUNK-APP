/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.frame.util;

import org.apache.log4j.MDC;
import org.slf4j.Logger;

/**
 * @author zhougz
 * @date 2013-6-4
 */
public class LoggerUtil {
	public final static String TYPE_DELETE = "delete";
	public final static String TYPE_UPDATE = "update";
	public final static String TYPE_LOGIN = "login";
	public final static String TYPE_LOGOUT = "logout";
	
	/**
	 * @describe 输出日志
	 * @author zhougz
	 * @param
	 * @date 2013-6-4
	 */
	public static String sysLog(Logger logger,String type,Object... entity){
		MDC.put("loginId", "zhougz");
		MDC.put("userName", "周广哲");
		MDC.put("type", type);
		MDC.put("obj", entity);
		String msg = "某某人怎么了";
		logger.info(msg);
		return msg;
	}
}
