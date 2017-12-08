
package com.asiainfo.biapp.si.loc.base.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.type.JavaType;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class JsonUtil {

    public static ObjectMapper mapper = new ObjectMapper();

    /**
     * 无日期类型对象转换为Json字符串
     * 
     * @param object
     *        Object 需要转换的对象，可以是普通object，也可以是map或者list
     * @param dateFormat
     *        DateFormat 如果需要日期转换，需要传该参数
     * @return String 返回json串
     * @throws IOException
     */
    public static String toJsonString(Object object) throws IOException {
        // 解决hibernate延迟加载
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        String json = getJsonStr(object);
        return json;
    }

    /**
     * 有日期类型对象转换为Json字符串
     * 
     * @param object
     *        Object 需要转换的对象，可以是普通object，也可以是map或者list
     * @param dateFormat
     *        DateFormat 如果需要日期转换，需要传该参数
     * @return String 返回json串
     * @throws IOException
     */
    public static String toJsonString(Object object, DateFormat dateFormat) throws IOException {
        // 解决hibernate延迟加载
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        if (dateFormat != null) {
            mapper.setDateFormat(dateFormat);
        } else {
            mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        }
        String json = getJsonStr(object);
        return json;
    }

    /**
     * 根据给定的ObjectMapper对象把Object转换为Json字符串
     * 
     * @param mapper
     *        ObjectMapper 单独设置时需要的ObjectMapper对象
     * @param object
     *        Object 需要转换的对象，可以是普通object，也可以是map或者list
     * @return String 返回json串
     * @throws IOException
     */
    public static String getJsonStr(Object object) throws IOException {
        try {
            StringWriter sw = new StringWriter();
            JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
            mapper.writeValue(gen, object);
            gen.close();
            String json = sw.toString();
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{}";

    }

    /**
     * json字符串转换为对应实体类
     * 
     * @param json
     *        json字符串
     * @param cls
     *        实体模型
     * @return 实体对象
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static Object json2Bean(String json, Class<?> cls)
            throws JsonParseException, JsonMappingException, IOException {
        mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param json
     *        Json字符串
     * @param collectionClass
     *        集合类型
     * @param elementClasses
     *        实体对象类型
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static Object json2CollectionBean(String json, Class<?> collectionClass, Class<?>... elementClasses)
            throws JsonParseException, JsonMappingException, IOException {
        JavaType typeInfo = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        return mapper.readValue(json, typeInfo);
    }

    /**
     * 多层次类型嵌套解析
     * 
     * @param json
     * @param javaType
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static Object json2CollectionBean(String json, JavaType javaType)
            throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(json, javaType);
    }

    /**
     * 将Json对象转换成Map
     * 
     * @param jsonObject
     *        json对象
     * @return Map对象
     * @throws JSONException
     */
    public static Map toMap(String jsonString) throws JSONException {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Map result = new HashMap();
        Iterator iterator = jsonObject.keys();
        String key = null;
        Object value = null;

        while (iterator.hasNext()) {

            key = (String) iterator.next();
            value = jsonObject.get(key);
            result.put(key, value);
        }
        return result;
    }

    /**
     * 将Map转换成Javabean
     * 
     * @param javabean
     *        javaBean
     * @param data
     *        Map数据
     */
    public static Object toJavaBean(Object javabean, Map data) {

        Method[] methods = javabean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            try {
                if (method.getName().startsWith("set")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("set") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    method.invoke(javabean, new Object[] { data.get(field) });
                }
            } catch (Exception e) {
            }
        }
        return javabean;
    }
}
