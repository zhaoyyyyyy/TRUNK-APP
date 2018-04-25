/*
 * @(#)DESUtil.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
 
package com.asiainfo.biapp.si.loc.base.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
 
/**
 * Title : DESUtil
 * <p/>
 * Description : 
 * DES加密解密，可对文件及字符串加密、解密
 * 其中，字符串加密解密用到了Base64编码
 * 文件加密是针对文件流的加解密
 * 
 * 使用方式是new DESUtil("可见的密钥")
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date         Modified By    Why & What is modified</pre>
 * <pre>1    2018年2月26日     hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年2月26日
 */

public class DESUtil {

	private SecretKey key;

	public DESUtil() {

	}

	public DESUtil(String str) {
		setKey(str); // 生成密匙
	}

	public SecretKey getKey() {
		return key;
	}

	public void setKey(SecretKey key) {
		this.key = key;
	}

	/**
	 * 根据参数生成 KEY
	 */
	public void setKey(String strKey) {
		try {
			DESKeySpec desKeySpec = new DESKeySpec(strKey.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			this.key = keyFactory.generateSecret(desKeySpec);
			if (this.key == null) {
				KeyGenerator _generator = KeyGenerator.getInstance("DES");
				_generator.init(new SecureRandom(strKey.getBytes()));
				this.key = _generator.generateKey();
				_generator = null;
			}
		} catch (Exception e) {
			LogUtil.error("生成DES密钥失败", e);
		}
	}

	/**
	 * 加密 String 明文输入 ,String 密文输出
	 */
	public String encryptStr(String strMing) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		try {
			byteMing = strMing.getBytes("UTF-8");
			byteMi = this.encryptByte(byteMing);
			strMi = new String(Base64.encodeBase64(byteMi), "UTF-8");
		} catch (Exception e) {
			LogUtil.error("对字符串DES加密失败", e);
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}

	/**
	 * 解密 以 String 密文输入 ,String 明文输出
	 * 
	 * @param strMi
	 * @return
	 */
	public String decryptStr(String strMi) {
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = Base64.decodeBase64(strMi.getBytes("UTF-8"));
			byteMing = this.decryptByte(byteMi);
			strMing = new String(byteMing, "UTF-8");
		} catch (Exception e) {
			LogUtil.error("对字符串DES解密失败", e);
		} finally {
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	/**
	 * 加密以 byte[] 明文输入 ,byte[] 密文输出
	 * 
	 * @param byteS
	 * @return
	 */
	private byte[] encryptByte(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			LogUtil.error("对byte数组DES加密失败", e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密以 byte[] 密文输入 , 以 byte[] 明文输出
	 * 
	 * @param byteD
	 * @return
	 */
	private byte[] decryptByte(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			LogUtil.error("对byte数组DES解密失败", e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 文件 file 进行加密并保存目标文件 destFile 中
	 * 
	 * @param file
	 *            要加密的文件 如 c:/test/srcFile.txt
	 * @param destFile
	 *            加密后存放的文件名 如 c:/ 加密后文件 .txt
	 */
	public void encryptFile(String file, String destFile) {
		InputStream is = null;
		OutputStream out = null;
		CipherInputStream cis = null;
		try {
			Cipher cipher = Cipher.getInstance("DES");
			// cipher.init(Cipher.ENCRYPT_MODE, getKey());
			cipher.init(Cipher.ENCRYPT_MODE, this.key);
			is = new FileInputStream(file);
			out = new FileOutputStream(destFile);
			cis = new CipherInputStream(is, cipher);
			byte[] buffer = new byte[1024];
			int r;
			while ((r = cis.read(buffer)) > 0) {
				out.write(buffer, 0, r);
			}
		} catch (Exception e) {
			LogUtil.error("对文件DES加密失败", e);
		} finally {
			try {
				if (cis != null) {
					cis.close();
				}
				if (is != null) {
					is.close();			
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				LogUtil.error("关闭流异常", e);
			}
		}
	}

	/**
	 * 文件采用 DES 算法解密文件
	 * 
	 * @param file
	 *            已加密的文件 如 c:/ 加密后文件 .txt *
	 * @param destFile
	 *            解密后存放的文件名 如 c:/ test/ 解密后文件 .txt
	 */
	public void decryptFile(String file, String dest) {
		InputStream is = null;
		OutputStream out = null;
		CipherOutputStream cos = null;
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, this.key);
			is = new FileInputStream(file);
			out = new FileOutputStream(dest);
			cos = new CipherOutputStream(out, cipher);
			byte[] buffer = new byte[1024];
			int r;
			while ((r = is.read(buffer)) >= 0) {
				cos.write(buffer, 0, r);
			}
		} catch (Exception e) {
			LogUtil.error("对文件DES解密失败", e);
		} finally {
			try {
				if (cos != null) {
					cos.close();
				}
				if (is != null) {
					is.close();			
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				LogUtil.error("关闭流异常", e);
			}
		}
	}

	public static void main(String[] args) throws Exception {
//		String key = CiIdentifierGenerator.randomUUID();
		String key = "1RmnGiuzp1iomKP1ZPOJE8";
		long s = System.currentTimeMillis();
		DESUtil des = new DESUtil(key);
		// DES 加密文件
		des.encryptFile("C:/phone.zip", "D:/phone.zip");
		// DES 解密文件
		des.decryptFile("D:/phone.zip", "E:/phone.zip");
		long e = System.currentTimeMillis();
		String str1 = "COC向广告平台推送test";
		// DES 加密字符串
		String str2 = des.encryptStr(str1);
		// DES 解密字符串
		String deStr = des.decryptStr(str2);
	}
}
