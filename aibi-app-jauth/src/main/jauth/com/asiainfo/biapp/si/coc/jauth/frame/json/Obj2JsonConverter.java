package com.asiainfo.biapp.si.coc.jauth.frame.json;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.asiainfo.biapp.si.coc.jauth.frame.Constants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

public abstract class Obj2JsonConverter
{
  public static final JSONObject EMPTY_JSON = new JSONObject();

  protected JsonConfig jc = new JsonConfig();

  public Obj2JsonConverter() {
    defaultSetupJsonConfig(this.jc);
  }

  protected void defaultSetupJsonConfig(JsonConfig jc) {
    jc.setCycleDetectionStrategy(CycleDetectionStrategy.NOPROP);
    jc.registerJsonValueProcessor(Date.class, new JsonDateProcessor(Constants.DEFAULT_DATETIME_FORMAT));
  }

  public JSONObject toJSONObject(Object obj)
  {
    return JSONObject.fromObject(obj, this.jc);
  }

  public JSONArray toJSONArray(Collection<?> collection)
  {
    JSONArray ja = new JSONArray();
    for (Iterator localIterator = collection.iterator(); localIterator.hasNext(); ) { Object obj = localIterator.next();
      ja.add(toJSONObject(obj)); }

    return ja;
  }

  public String toJSONString(Object pojo)
  {
    if (pojo instanceof Collection) {
      return toJSONArray((Collection)pojo).toString();
    }
    return toJSONObject(pojo).toString();
  }
}
