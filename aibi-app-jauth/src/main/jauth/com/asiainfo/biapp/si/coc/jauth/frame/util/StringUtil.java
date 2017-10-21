package com.asiainfo.biapp.si.coc.jauth.frame.util;

import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

public class StringUtil  extends StringUtils{
	public static boolean isEmpty(String str){
		if(str == null || "".equals(str)){
			return true;
		}
		return false;
	}
	
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	
	  public static String[] split(String seperators, String list, boolean include)
	  {
	    StringTokenizer tokens = new StringTokenizer(list, seperators, include);
	    String[] result = new String[tokens.countTokens()];
	    int i = 0;

	    while (tokens.hasMoreTokens())
	      result[(i++)] = tokens.nextToken();
	    return result;
	  }
	  
	  public static String[] split(String src, String flag)
	  {
	    return StringUtils.splitPreserveAllTokens(src, flag);
	  }
}
