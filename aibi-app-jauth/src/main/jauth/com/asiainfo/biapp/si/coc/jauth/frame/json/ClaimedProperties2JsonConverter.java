package com.asiainfo.biapp.si.coc.jauth.frame.json;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClaimedProperties2JsonConverter extends Obj2JsonConverter {

	private static Logger logger = LoggerFactory.getLogger(ClaimedProperties2JsonConverter.class);
	
	private Set<String> propertySet = new HashSet<String>();
	
	public ClaimedProperties2JsonConverter(String[] properties) {
		super();
		addPropertySet(properties);
	}
	
	public ClaimedProperties2JsonConverter(String properties) {
		super();
		String[] arr = properties.split(",");
		addPropertySet(arr);
	}
	
	public ClaimedProperties2JsonConverter(Collection<String> properties) {
		super();
		propertySet.addAll(properties);
	}
	
	private void addPropertySet(String[] properties) {
		for (String property : properties) {
			propertySet.add(property);
		}
	}

	@Override
	public JSONObject toJSONObject(Object obj) {
		return this.toJSONObject(obj, propertySet);
	}
	
	// 根据指定的属性集合，来转换成json对象
	private JSONObject toJSONObject(final Object obj, final Set<String> propSet) {
		if (propSet.isEmpty() || obj == null) {
			return new JSONObject();
		}
		
		JSONObject jsonRslt = null;
		
		final Set<String> localSet = new HashSet<String>();
		localSet.addAll(propSet);
		
		// 过滤localset中的属性，过滤后，只剩下简单的属性，没有符合属性了。
		Map<String,Set<String>> map = this.filterProperties(localSet);

		
		
		//add by zhougz
		jsonRslt = new JSONObject();
		for(String s :localSet){
			String methodName= "get"  +s.replaceFirst(s.substring(0, 1),s.substring(0, 1).toUpperCase());
			Object o = null;
			try {
				o = obj.getClass().getMethod(methodName).invoke(obj);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			jsonRslt.element(s, o == null?"":o.toString());
		}
		
		
		
		//modify by zhougz
//		if (!localSet.isEmpty()) {
//			// 设置当前的过滤条件
//			JsonConfig jsonConfig = new JsonConfig();
//			super.defaultSetupJsonConfig(jsonConfig);
//			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors( obj );
//			List<String> excludes = new ArrayList<String>(); 
//			for( int i = 0; i < pds.length; i++ ){
//	           
//	            String key = pds[i].getName();
//	            
//	            if (!localSet.contains(key)) {
//	            	excludes.add(key);
//	            }
//	         }
//			
//			jsonConfig.setExcludes(ArrayUtil.toString(excludes.toArray()));
//			jsonRslt = JSONObject.fromObject(obj, jsonConfig);
//		} else {
//			jsonRslt = new JSONObject();
//		}
		
		
		
		
		// 如果还有复杂属性了，要递归处理一下
		if (!map.isEmpty()) {
			Set<String> keySet = map.keySet();
			
			// 针对每一个复杂的属性
			for (String complexProp : keySet) {
				Object complexPropValue = null;
				try {
					complexPropValue = PropertyUtils.getProperty(obj, complexProp);
				} catch (Exception e) {
					logger.error("从对象{}中，获取属性{}时出错", obj, complexProp);
				}
				Set<String> complexSet = map.get(complexProp);
				
				if (complexPropValue != null) {
					// 如果该属性为集合类型，则需要转换其内部的对象
					if (complexPropValue instanceof Collection) {
						Collection<?> collection = (Collection<?>)complexPropValue;
						if (!collection.isEmpty()) {
							for (Object subObj : collection) {
								jsonRslt.accumulate(complexProp, toJSONObject(subObj, complexSet));
							}
						}
					} else {
						jsonRslt.element(complexProp, toJSONObject(complexPropValue, complexSet));
					}
				}
			}
		}
		
		return jsonRslt;
	}

	/**
	 * 将符合属性都处理到map中，set中剩下的就是单纯属性。
	 * 比如：
	 * 原来的属性集合为[a, b, c.x,c.y, d, e.m, e.n, f]；
	 * 那么处理结束后，原来集合中还剩[a,b,d,f],Map中有<c:[x,y], e:[m,n]>。
	 * @param set 当前处理的属性集合
	 * @return
	 */
	private Map<String, Set<String>> filterProperties(Set<String> set) {
		// 复合属性，也就是要从当前set中删除的。
		Set<String> removed = new HashSet<String>();
		// 符合属性的一个映射。比如[a.b, a.c] 那么处理后就成为[a->[b,c]]
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		
		for (String prop : set) {
			int index = prop.indexOf(".");
			
			if (index > -1) {
				removed.add(prop);
				String key = prop.substring(0, index);
				Set<String> subSet = map.get(key);
				if (subSet == null) {
					subSet = new HashSet<String>();
					map.put(key, subSet);
				}
				subSet.add(prop.substring(index+1));
			}
		}
		
		set.removeAll(removed);
		return map;
	}
}
