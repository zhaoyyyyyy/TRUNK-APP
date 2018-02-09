package com.asiainfo.biapp.si.loc.base.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;

import net.sf.json.JSONObject;

public class ServletUtil {

	/**
	 * 
	 * Description: 返回异常信息
	 *
	 * @param response
	 *
	 * @author  tianxy3
	 * @date 2017年9月19日
	 */
	public static void responseMsg(HttpServletResponse response,BaseException baseException) {
		//没有权限      、将实体对象转换为JSON Object转换
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			WebResult<Object> webResult = new WebResult<Object>();
			out = response.getWriter();
			out.append(JSONObject.fromObject(webResult.fail(baseException)).toString() );
		} catch (IOException e) {
			LogUtil.error("响应接口信息日常",e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
