package com.asiainfo.biapp.si.coc.jauth.frame.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;


public class JsonDateProcessor
  implements JsonValueProcessor
{
  private DateFormat format;

  public JsonDateProcessor(String format)
  {
    this.format = new SimpleDateFormat(format);
  }

  public Object processArrayValue(Object value, JsonConfig jsonConfig) {
    return "";
  }

  public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
    if (value == null) {
      return null;
    }
    return this.format.format((Date)value);
  }
}
