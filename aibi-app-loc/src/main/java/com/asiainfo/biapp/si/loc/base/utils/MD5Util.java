/*
 * @(#)MD5Util.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
 
package com.asiainfo.biapp.si.loc.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * Title : MD5Util
 * <p/>
 * Description : 
 * 用于生成文件的MD5校验码
 * 也可生成字符串MD5校验码
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

public class MD5Util {
	private static final Logger log = Logger.getLogger(MD5Util.class);
	
	/**
	 * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
	 */
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	protected static MessageDigest messagedigest = null;
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			String msg = MD5Util.class.getName() + "初始化失败，MessageDigest不支持MD5Util。";
			LogUtil.error(msg, e);
		}
	}

	/**
	 * 生成字符串的md5校验值
	 * 
	 * @param s
	 * @return
	 */
	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	/**
	 * 判断字符串的md5校验码是否与一个已知的md5码相匹配
	 * 
	 * @param password
	 *            要校验的字符串
	 * @param md5PwdStr
	 *            已知的md5校验码
	 * @return
	 */
	public static boolean checkPassword(String password, String md5PwdStr) {
		String s = getMD5String(password);
		return s.equals(md5PwdStr);
	}

	/**
	 * 生成文件的md5校验值
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(File file) {
		InputStream fis = null;
		try {
			fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int numRead = 0;
			while ((numRead = fis.read(buffer)) > 0) {
				messagedigest.update(buffer, 0, numRead);
			}
		} catch (Exception e) {
			log.error("获取文件MD5校验码失败", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					log.error("关闭文件流异常", e);
				}
			}
		}
		return bufferToHex(messagedigest.digest());
	}

	/**
	 * JDK1.4中不支持以MappedByteBuffer类型为参数update方法，并且网上有讨论要慎用MappedByteBuffer，
	 * 原因是当使用 FileChannel.map 方法时，MappedByteBuffer 已经在系统内占用了一个句柄， 而使用
	 * FileChannel.close 方法是无法释放这个句柄的，且FileChannel有没有提供类似 unmap 的方法，
	 * 因此会出现无法删除文件的情况。
	 * 
	 * 不推荐使用
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String_old(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
				file.length());
		messagedigest.update(byteBuffer);
		return bufferToHex(messagedigest.digest());
	}

	public static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		// 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		// 取字节中低 4 位的数字转换
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static void main(String[] args) throws IOException {
		long begin = System.currentTimeMillis();

		File file = new File("C:/phone.zip");
		String md5 = getFileMD5String(file);

		// String md5 = getMD5String("a");

		long end = System.currentTimeMillis();
		System.out.println("md5:" + md5 + " time:" + ((end - begin) / 1000)
				+ "s");
	}
}
