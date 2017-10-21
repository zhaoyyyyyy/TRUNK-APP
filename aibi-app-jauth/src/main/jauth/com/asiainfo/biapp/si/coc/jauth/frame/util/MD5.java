package com.asiainfo.biapp.si.coc.jauth.frame.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {  
		/**
		 * @describe MD5加密
		 * @author XieGaosong
		 * @param
		 * @date 2013-7-10
		 */
		public static String MD5CODE(String toChapter) { 
	        String str = ""; 
	        try { 
	 
	            MessageDigest md = MessageDigest.getInstance("MD5"); 
	 
	            md.update(toChapter.getBytes()); 
	 
	            byte[] buf = md.digest(toChapter.getBytes()); 
	 
	            BigInteger bi = new BigInteger(buf); 
	 
	            str = bi.toString(36);// 36进制数的字符串形式 
	 
	        } catch (NoSuchAlgorithmException e) { 
	            e.printStackTrace(); 
	        } 
	        return str; 
	    }
	  
		
}
