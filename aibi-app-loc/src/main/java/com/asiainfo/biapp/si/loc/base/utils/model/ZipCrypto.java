/*
 * @(#)ZipCrypto.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
 
package com.asiainfo.biapp.si.loc.base.utils.model;

/**
 * Title : ZipCrypto
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

public class ZipCrypto {

	private static long keys[] = { 0x12345678, 0x23456789, 0x34567890 };

	private static int magicByte() {
		int t;
		t = (int) ((keys[2] & 0xFFFF) | 2);
		t = ((t * (t ^ 1)) >> 8);
		return (int) t;
	}

	private static void updateKeys(int byteValue) {
		int key0val;
		keys[0] = Crc32.update(keys[0], byteValue);
		key0val = (byte) keys[0];
		if ((byte) keys[0] < 0) {
			key0val += 256;
		}
		keys[1] = keys[1] + key0val;
		keys[1] = (keys[1] * 0x08088405);
		keys[1] = keys[1] + 1;
		keys[2] = Crc32.update(keys[2], (byte) (keys[1] >> 24));

	}

	public static void initCipher(String passphrase) {
		keys[0] = 0x12345678;
		keys[1] = 0x23456789;
		keys[2] = 0x34567890;
		for (int i = 0; i < passphrase.length(); i++) {
			updateKeys((byte) passphrase.charAt(i));
		}
	}

	public static byte[] decryptMessage(byte[] cipherText, int length) {
		byte[] plainText = new byte[length];
		for (int i = 0; i < length; i++) {
			int m = magicByte();
			byte C = (byte) (cipherText[i] ^ m);
			if (C < 0) {
				updateKeys((int) ((int) C + 256));
				plainText[i] = (byte) (int) ((int) C + 256);
			} else {
				updateKeys(C);
				plainText[i] = C;
			}
		}
		return plainText;
	}

	public static byte[] encryptMessage(byte[] plaintext, int length) {
		byte[] cipherText = new byte[length];
		for (int i = 0; i < length; i++) {
			byte C = plaintext[i];
			cipherText[i] = (byte) (plaintext[i] ^ magicByte());
			updateKeys(C);
		}
		return cipherText;
	}

}