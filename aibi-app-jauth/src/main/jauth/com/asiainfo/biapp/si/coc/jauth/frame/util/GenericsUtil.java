package com.asiainfo.biapp.si.coc.jauth.frame.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericsUtil {
	  private static final Logger log = LoggerFactory.getLogger(GenericsUtil.class);

	  public static Class getSuperClassGenricType(Class clazz)
	  {
	    return getSuperClassGenricType(clazz, 0);
	  }

	  public static Class getSuperClassGenricType(Class clazz, int index)
	  {
	    Type genType = clazz.getGenericSuperclass();

	    if (!(genType instanceof ParameterizedType)) {
	      log.warn(clazz.getSimpleName() + 
	        "'s superclass not ParameterizedType");
	      return Object.class;
	    }

	    Type[] params = ((ParameterizedType)genType).getActualTypeArguments();

	    if ((index >= params.length) || (index < 0)) {
	      log.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + 
	        "'s Parameterized Type: " + params.length);
	      return Object.class;
	    }
	    if (!(params[index] instanceof Class)) {
	      log.warn(clazz.getSimpleName() + 
	        " not set the actual class on superclass generic parameter");
	      return Object.class;
	    }
	    return (Class)params[index];
	  }

	  public static Class getGenericClass(Class clazz)
	  {
	    return getGenericClass(clazz, 0);
	  }

	  public static Class getGenericClass(Class clazz, int index)
	  {
	    Type genType = clazz.getGenericSuperclass();

	    if (genType instanceof ParameterizedType) {
	      Type[] params = ((ParameterizedType)genType).getActualTypeArguments();

	      if ((params != null) && (params.length >= index - 1)) {
	        return (Class)params[index];
	      }
	    }
	    return null;
	  }
	}