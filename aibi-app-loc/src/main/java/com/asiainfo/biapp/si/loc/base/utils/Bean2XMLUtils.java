/*
 * @(#)Bean2XMLUtils.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
 
package com.asiainfo.biapp.si.loc.base.utils;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import org.apache.commons.betwixt.BindingConfiguration;
import org.apache.commons.betwixt.IntrospectionConfiguration;
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.betwixt.strategy.DecapitalizeNameMapper;
import org.apache.commons.betwixt.strategy.DefaultObjectStringConverter;
import org.apache.commons.betwixt.strategy.HyphenatedNameMapper;
import org.apache.commons.betwixt.expression.Context;
import org.xml.sax.SAXException;


/**
 * Title : Bean2XMLUtils
 * <p/>
 * Description : 标签推送设置信息表服务类
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date         Modified By    Why & What is modified</pre>
 * <pre>1    2018年2月26日     hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年2月26日
 */

public class Bean2XMLUtils {
	private static final String xmlHead = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";

	/**
	 * 将javaBean对象转换成xml文件，对于没有设置的属性将不会生成xml标签
	 * 
	 * @param obj 待转换的javabean对象
	 * @return String 转换后的xml 字符串
	 * @throws IntrospectionException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static String bean2XmlString(Object obj) throws IOException,
			SAXException, IntrospectionException, IllegalArgumentException{
		if (obj == null) {
			throw new IllegalArgumentException("给定的参数不能为null！");
		}
		StringWriter sw = new StringWriter();
		sw.write(xmlHead);// 写xml文件头
		BeanWriter writer = new BeanWriter(sw);
		IntrospectionConfiguration config = writer.getXMLIntrospector()
				.getConfiguration();
		BindingConfiguration bc = writer.getBindingConfiguration();
		bc.setObjectStringConverter(new DateConverter());
		bc.setMapIDs(false);
		config.setAttributesForPrimitives(false);
		config.setAttributeNameMapper(new HyphenatedNameMapper());
		config.setElementNameMapper(new DecapitalizeNameMapper());
		writer.enablePrettyPrint();
		writer.write(obj.getClass().getSimpleName(), obj);
		writer.close();
		return sw.toString();
	}

	/**
	 * 日期转换，主要是解决日期为null或者空字符串解析报错问题
	 * 
	 */
	private static class DateConverter extends DefaultObjectStringConverter {
		private static final long serialVersionUID = -197858851188189916L;

		@SuppressWarnings("rawtypes")
		public String objectToString(Object object, Class type, String flavour, Context context) {
			if (object != null) {
				if (object instanceof java.util.Date) {
					return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((java.util.Date) object);
				}
				return object.toString();
			} else {
				return "";
			}
		}

	}

}
