/*
 * @(#)ZipUtil.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
 
package com.asiainfo.biapp.si.loc.base.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.asiainfo.biapp.si.loc.base.utils.model.EncryptZipEntry;
import com.asiainfo.biapp.si.loc.base.utils.model.EncryptZipInput;
import com.asiainfo.biapp.si.loc.base.utils.model.EncryptZipOutput;

/**
 * Title : ZipUtil
 * <p/>
 * Description : zip工具类
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

public final class ZipUtil {
	/**
	 * 目录压缩
	 * @param srcfile 源文件所在目录
	 * @param password 密码
	 * @param encode 编码
	 * @return
	 */
	public static byte[] getEncryptZipByte(File[] srcfile, String password, String encode) {
		ByteArrayOutputStream tempOStream = new ByteArrayOutputStream(1024);
		byte[] tempBytes = null;
		EncryptZipOutput out = null;
		FileInputStream in = null;
		byte[] buf = new byte[1024];
		try {
			out = new EncryptZipOutput(tempOStream, password);
			out.setEncoding(encode);
			for (int i = 0; i < srcfile.length; i++) {
				in = new FileInputStream(srcfile[i]);
				out.putNextEntry(new EncryptZipEntry(srcfile[i].getName()));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
			}
			tempOStream.flush();
			tempBytes = tempOStream.toByteArray();
		} catch (IOException e) {
		    LogUtil.error("IOException:", e);
		}finally {
		    if (null != in) {
		        try {
	                in.close();
		        } catch (IOException e) {
		            LogUtil.error(e);
		        }
		    }
		    if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    LogUtil.error(e);
                }
            }
            if (null != tempOStream) {
                try {
                    tempOStream.close();
                } catch (IOException e) {
                    LogUtil.error(e);
                }
            }
            
        }

		return tempBytes;
	}

	public static void unzipFiles(byte[] zipBytes, String password, String dir) throws IOException {
		InputStream bais = new ByteArrayInputStream(zipBytes);
		EncryptZipInput zin = new EncryptZipInput(bais, password);
		EncryptZipEntry ze;
		while ((ze = zin.getNextEntry()) != null) {
			ByteArrayOutputStream toScan = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len;
			while ((len = zin.read(buf)) > 0) {
				toScan.write(buf, 0, len);
			}
			byte[] fileOut = toScan.toByteArray();
			toScan.close();
			writeByteFile(fileOut, new File(dir + File.separator + ze.getName()));

		}
		zin.close();
		bais.close();
	}

	/**
	 * 将字节数组写入文件
	 * @param bytes
	 * @param file
	 * @return
	 */
	private static boolean writeByteFile(byte[] bytes, File file) {
		FileOutputStream fos = null;
		boolean flag = true;
		try {
			fos = new FileOutputStream(file);
			fos.write(bytes);
		} catch (FileNotFoundException e) {
			flag = false;
			LogUtil.error("FileNotFoundException:", e);
		} catch (IOException e) {
			flag = false;
			LogUtil.error("IOException:", e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				    LogUtil.error("IOException:", e);
				}
			}
		}
		return flag;
	}

	/*public static void main(String[] args) {
		String zipDir = "D:/temp/file/";
		String zipFile = "D:/test.zip";
		String password = "123";
		File file = new File(zipDir);
		byte[] zipByte = getEncryptZipByte(file.listFiles(), password);
		writeByteFile(zipByte, new File(zipFile));
	}*/
}