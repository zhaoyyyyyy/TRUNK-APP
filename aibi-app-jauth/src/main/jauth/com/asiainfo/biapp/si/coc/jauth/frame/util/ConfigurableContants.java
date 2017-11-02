/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.frame.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhougz
 * @date 2013-6-4
 */
public class ConfigurableContants {
	  protected static Logger logger = LoggerFactory.getLogger(ConfigurableContants.class);
	  protected static final Properties p = new Properties();

	  public static void init(String propertyFileName)
	  {
	    InputStream in = null;
	    try {
	      in = ConfigurableContants.class.getResourceAsStream(propertyFileName);
	      if (in != null){
	    	  p.load(in);
	      } 
	    } catch (IOException e) {
	    	logger.error("load " + propertyFileName + " into Contants error!");
	    } finally {
	      if (in != null)
	        try {
	          in.close();
	        } catch (IOException e) {
	        	logger.error("close " + propertyFileName + " error!");
	        }
	    }
	  }

	  protected static String getProperty(String key, String defaultValue)
	  {
	    return p.getProperty(key, defaultValue);
	  }
	  
	  protected static String getProperty(String key)
	  {
	    return p.getProperty(key);
	  }
}
