package com.asiainfo.biapp.si.loc.core.label.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;

/**
 * 根据标签类型id生成标签对应子类实例的工厂类
 * 
 * @author luyan3
 * @version ZJ
 */
public class LabelElementFactory {

	private Logger log = Logger.getLogger(LabelElementFactory.class);

	/**
	 * 标签类型与实现类名(全路径)对应关系map,不包含组合标签
	 */
	public final static Map<Integer, String> labelTypeMap = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(LabelInfoContants.LABEL_TYPE_SIGN, "com.asiainfo.biapp.si.loc.core.label.model.sub.SignLabel");
			put(LabelInfoContants.LABEL_TYPE_KPI, "com.asiainfo.biapp.si.loc.core.label.model.sub.KpiLabel");
			put(LabelInfoContants.LABEL_TYPE_ENUM, "com.asiainfo.biapp.si.loc.core.label.model.sub.EnumLabel");
			put(LabelInfoContants.LABEL_TYPE_DATE, "com.asiainfo.biapp.si.loc.core.label.model.sub.DateLabel");
			put(LabelInfoContants.LABEL_TYPE_TEXT, "com.asiainfo.biapp.si.loc.core.label.model.sub.TextLabel");
		}
	};

	private LabelElement labelElement;

	/**
	 * 获得标签类型
	 * 
	 * @return
	 * @version ZJ
	 */
	public LabelElement getLabelElement() {
		return labelElement;
	}

	/**
	 * 根据标签类型初始化不同标签类型
	 * 
	 * @param labelTypeId
	 * @version ZJ
	 */
	public void setLabelElement(Integer labelTypeId) {
		try {
			String clsName = labelTypeMap.get(labelTypeId);
			Class<?> cls = Class.forName(clsName);
			this.labelElement = (LabelElement) cls.newInstance();
		} catch (Exception e) {
			String msg = "计算标签不存在";
			log.error(msg, e);
			throw new RuntimeException(msg, e);
		}
	}

}
