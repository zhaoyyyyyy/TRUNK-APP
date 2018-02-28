/*
 * @(#)EncryptZipEntry.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
 
package com.asiainfo.biapp.si.loc.base.utils.model;

import java.util.Date;
import java.util.zip.ZipEntry;

/**
 * Title : EncryptZipEntry
 * <p/>
 * Description : 标签推送设置信息表服务类
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

public class EncryptZipEntry extends ZipEntry {

	public EncryptZipEntry(String name) {
		super(name);
		this.name = name;
	}

	String name; // entry name
	long time = -1; // modification time (in DOS time)
	long crc = -1; // crc-32 of entry data
	long size = -1; // uncompressed size of entry data
	long csize = -1; // compressed size of entry data
	int method = -1; // compression method
	byte[] extra; // optional extra field data for entry
	String comment; // optional comment string for entry
	// The following flags are used only by Zip{Input,Output}Stream
	int flag; // bit flags
	int version; // version needed to extract
	long offset; // offset of loc header

	public void setTime(long time) {
		this.time = javaToDosTime(time);
	}

	/*
	 * Converts Java time to DOS time.
	 */
	@SuppressWarnings("deprecation")
	private static long javaToDosTime(long time) {
		Date d = new Date(time);
		int year = d.getYear() + 1900;
		if (year < 1980) {
			return (1 << 21) | (1 << 16);
		}
		return (year - 1980) << 25 | (d.getMonth() + 1) << 21 | d.getDate() << 16 | d.getHours() << 11
				| d.getMinutes() << 5 | d.getSeconds() >> 1;
	}
}