/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.frame.dao;
import java.net.InetAddress;

/**
 * @author zhougz
 * @date 2013-5-27
 */
public class UUIDHexGenerator {
	  private String sep = "";
	  private static final int IP;
	  private static short counter = 0;
	  private static final int JVM = (int)(System.currentTimeMillis() >>> 8);

	  private static UUIDHexGenerator uuidgen = new UUIDHexGenerator();

	  static {
	    int ipadd;
	    try { ipadd = toInt(InetAddress.getLocalHost().getAddress());
	    } catch (Exception e) {
	      ipadd = 0;
	    }
	    IP = ipadd;
	  }

	  public static UUIDHexGenerator getInstance() {
	    return uuidgen;
	  }

	  public static int toInt(byte[] bytes) {
	    int result = 0;
	    for (int i = 0; i < 4; ++i) {
	      result = (result << 8) - -128 + bytes[i];
	    }
	    return result;
	  }

	  protected String format(int intval) {
	    String formatted = Integer.toHexString(intval);
	    StringBuffer buf = new StringBuffer("00000000");
	    buf.replace(8 - formatted.length(), 8, formatted);
	    return buf.toString();
	  }

	  protected String format(short shortval) {
	    String formatted = Integer.toHexString(shortval);
	    StringBuffer buf = new StringBuffer("0000");
	    buf.replace(4 - formatted.length(), 4, formatted);
	    return buf.toString();
	  }

	  protected int getJVM() {
	    return JVM;
	  }

	  protected synchronized short getCount() {
	    if (counter < 0)
	      counter = 0;
	    short tmp13_10 = counter; counter = (short)(tmp13_10 + 1); return tmp13_10;
	  }

	  protected int getIP() {
	    return IP;
	  }

	  protected short getHiTime() {
	    return (short)(int)(System.currentTimeMillis() >>> 32);
	  }

	  protected int getLoTime() {
	    return (int)System.currentTimeMillis();
	  }

	  public String generate()
	  {
		  return  format(getIP()) + 
	      this.sep + format(getJVM()) + 
	      this.sep + format(getHiTime()) + 
	      this.sep + format(getLoTime()) + 
	      this.sep + format(getCount());
		  
//	    return 36 + format(getIP()) + 
//	      this.sep + format(getJVM()) + 
//	      this.sep + format(getHiTime()) + 
//	      this.sep + format(getLoTime()) + 
//	      this.sep + format(getCount());
	  }
	  
	}


