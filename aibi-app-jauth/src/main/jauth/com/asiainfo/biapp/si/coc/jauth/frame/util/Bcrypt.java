package com.asiainfo.biapp.si.coc.jauth.frame.util;

import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Bcrypt {
	/**
	 * @describe MD5加密
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-10
	 */
	public static String BcryptCode(String toChapter) { 
    	String hashed = BCrypt.hashpw(toChapter, BCrypt.gensalt());
        return hashed; 
    }
}
