package com.asiainfo.biapp.si.coc.jauth.frame.json;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.biapp.si.coc.jauth.frame.Constants;
import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;
import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * 文件名：JSONResult.java
 * 日期：2011-3-8
 * 功能说明：各种控件用Json格式数据的转换
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */
public class JSONResult {

  /** 日志 */
  private static final Logger logger = LoggerFactory.getLogger(JSONResult.class);

  /** 码表Json数组中的Key即代码 */
  private static final String SELECT_CODE_KEY = "\"code\":";
  /** 码表Json数组中的value即代码类别 */
  private static final String SELECT_CODE_VALUE = "\"codeDesc\":";

  public static final String PAGE = "page";
  public static final String TOTAL_PAGE = "total";
  public static final String TOTAL_ROW = "records";
  public static final String ROWS = "rows";
  public static final String EMPTY_RESULT = "{\"page\":\"0\",\"total\":0,\"records\":\"0\",\"rows\":[]}";

  private static final char QUOTE_IN_JSON = '"';
  private static final String COMMA = ",";
  private static final char COLON = ':';

  private static JsonConfig jc = new JsonConfig();

  static {
    jc.setCycleDetectionStrategy(CycleDetectionStrategy.NOPROP);
  }



  /**
   * 将一个jsonArray 使用page来包装出一个page来
   * 
   * @param page
   * @param rows
   * @return
   */
  public static String wrapJson(Page<?> page, JSONArray rows) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.element(PAGE, page.getCurrentPageNo());
    jsonObject.element(TOTAL_PAGE, page.getTotalPageCount());
    jsonObject.element(TOTAL_ROW, page.getTotalCount());

    jsonObject.element(ROWS, rows);

    String rslt = jsonObject.toString();
    logger.debug(rslt);

    return rslt;
  }

  /**
   * 功能描述：查询
   * 
   * @version 1.0
   * @see com.jeaw.commons.web
   * @param page
   * @param properties
   * @return String
   */
  public static String page2Json(Page<?> page, String properties,Map<String,Map<String,String>>... propertiesMapTrans) {

    if (page.getTotalCount() == 0) {
      return EMPTY_RESULT;
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.element(PAGE, page.getCurrentPageNo());
    jsonObject.element(TOTAL_PAGE, page.getTotalPageCount());
    jsonObject.element(TOTAL_ROW, page.getTotalCount());

    JSONArray rows = list2Json(page.getData(), properties,propertiesMapTrans);
    jsonObject.element(ROWS, rows);
    String rslt = jsonObject.toString();
    logger.debug(rslt);
    return rslt;
  }

  public static JSONObject page2RawJson(Page<?> page, String properties) {
    if (page.getTotalCount() == 0) {
      return new JSONObject();
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.element(PAGE, page.getCurrentPageNo());
    jsonObject.element(TOTAL_PAGE, page.getTotalPageCount());
    jsonObject.element(TOTAL_ROW, page.getTotalCount());

    JSONArray rows = list2Json(page.getData(), properties);
    jsonObject.element(ROWS, rows);

    return jsonObject;
  }

  /**
   * 将一个map转成字符串，规则为{"key1":"value1","key2":"value2"}
   * 
   * @param map
   * @return
   */
  public static String map2Json(Map map) {
    if (map == null || map.isEmpty()) {
      return "{}";
    }
    StringBuffer rslt = new StringBuffer("{");
    Set<Map.Entry> entrys = map.entrySet();
    int i = 0;
    for (Map.Entry entry : entrys) {
      if (i > 0) {
        rslt.append(COMMA);
      } else {
        i++;
      }
      rslt.append(QUOTE_IN_JSON).append(entry.getKey()).append(QUOTE_IN_JSON).append(COLON).append(QUOTE_IN_JSON)
          .append(entry.getValue()).append(QUOTE_IN_JSON);
    }
    rslt.append("}");

    return rslt.toString();
  }

  public static JSONObject array2Json(Object[] arr, String[] nameMappings,Map<String,Map<String,String>>... map) {
    if (arr == null || arr.length == 0) {
      return new JSONObject();
    }

    JSONObject rslt = new JSONObject();
    for (int i = 0; i < nameMappings.length; i++) {
      if (nameMappings[i].indexOf(".") != -1) {
        String[] subProps = nameMappings[i].split(".");
        JSONObject subRslt = rslt;
        for (int j = 0; j < subProps.length - 1; j++) {
          if (subRslt.has(subProps[j])) {
            subRslt = subRslt.getJSONObject(subProps[j]);
          } else {
            JSONObject temp = new JSONObject();
            subRslt.element(subProps[j], temp);
            subRslt = temp;
          }
        }

        subRslt.accumulate(subProps[subProps.length - 1], arr[i], jc);

      } else {
        // 不是复合属性，直接设置就可以。
          if(map != null && map.length>0 && map[0].containsKey(nameMappings[i]) &&  map[0].get(nameMappings[i]).get(arr[i]) != null ){
              rslt.element(nameMappings[i], map[0].get(nameMappings[i]).get(arr[i]));
          }else{
              rslt.element(nameMappings[i], arr[i], jc);
          }
      }
    }

    return rslt;
  }

  /**
   * 将一个列表转成一个json数据，以jsonArray表示。
   * 列表中的对象根据要求转义的属性转成json。
   * 
   * @param collection 待转换的列表。
   * @param properties 其中要转的属性。
   * @return
   */
  public static JSONArray list2Json(Collection<?> collection, String properties,Map<String,Map<String,String>>... propertiesMapTrans) {

    if (collection == null || collection.isEmpty()) {
      return new JSONArray();
    }

    String[] arrProperties = properties.split(COMMA);
    Obj2JsonConverter jsonConverter = new ClaimedProperties2JsonConverter(properties);
    JSONArray rows = new JSONArray();
    for (Object obj : collection) {
    	if(obj == null){continue;}
    	if (obj.getClass().isArray()) {
    		rows.add(array2Json((Object[]) obj, arrProperties,propertiesMapTrans));
    	} else {
    		JSONObject row = jsonConverter.toJSONObject(obj);
    		if (obj instanceof BaseEntity) {
	          BaseEntity entity = (BaseEntity) obj;
	          row.element(Constants.DEFAULT_ID_NAME, entity.getId());
	        }
    		rows.add(row);
    	}
    }

    return rows;
  }

  /**
   * 将一个对象转成一个json数据，以jsonArray表示。
   * 列表中的对象根据要求转义的属性转成json。
   * @param obj 待转换的对象。
   * @param properties 其中要转的属性。
   * @return
   */
  public static JSONObject Object2Json(Object obj, String properties) {
   if (obj == null) {
      return new JSONObject();
    }
    Obj2JsonConverter jsonConverter = new ClaimedProperties2JsonConverter(properties);
	JSONObject row = jsonConverter.toJSONObject(obj);
	if (obj instanceof BaseEntity) {
      BaseEntity entity = (BaseEntity) obj;
      row.element(Constants.DEFAULT_ID_NAME, entity.getId());
    }
    return row;
  }
  
  /**
   * 将一个list直接转成page json数据。
   * 应用场景是：
   * 一次性加载所有的数据不分页；
   * 或者一次记载所有的数据，在前端再分页。
   * 
   * @param collection
   * @param properties
   * @return
   */
  public static String list2JsonAsPage(Collection<?> collection, String properties) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.element(PAGE, 1);
    jsonObject.element(TOTAL_PAGE, 1);
    jsonObject.element(TOTAL_ROW, collection.size());

    JSONArray rows = list2Json(collection, properties);
    jsonObject.element(ROWS, rows);

    return jsonObject.toString();
  }

}
