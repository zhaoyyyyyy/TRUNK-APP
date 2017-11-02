/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.frame.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zhougz
 * @date 2013-10-9
 */
public class HttpUtil {
	/**
	 * Description:发送http请求
	 * @param urlAddr Map<String,String> map
	 * @return
	 * @author XieGaosong
	 */
	public static String sentHttpReq(String urlAddr,Map<String,String> map) {
		HttpURLConnection urlCon = null;
		try {
			URL url = new URL(urlAddr);
			urlCon = (HttpURLConnection) url.openConnection();
			String keys="";
			String values="";
				Set<String> set = map.keySet();
				for(String key : set){
						keys=key;
						values=URLEncoder.encode(map.get(key),"GBK");
				}
			urlCon.setRequestMethod("POST");//设置为POST提交
			urlCon.setDoInput(true);//http正文内，因此需要设为true, 默认情况下是false;
			urlCon.setDoOutput(true); // 设置是否从httpUrlConnection读入，默认情况下是true;
			urlCon.setUseCaches(false); // Post 请求不能使用缓存
			urlCon.setConnectTimeout(10000);
			urlCon.setRequestProperty("Content-Type",
             "application/x-www-form-urlencoded");
			// urlCon.connect();
			/** 此处getOutputStream会隐含的进行connect (即：如同调用上面的connect()方法，
			所以在开发中不调用上述的connect()也可以)。*/ 
			OutputStream os = urlCon.getOutputStream();
		    // 现在通过输出流对象构建对象输出流对象，以实现输出可序列化的对象。
			//ObjectOutputStream oos = new ObjectOutputStream(os);
			// 向对象输出流写出数据，这些数据将存到内存缓冲区中
			 DataOutputStream out = new DataOutputStream(os);
			  String content = "keys=" +keys+"&values="+values;
		        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
	        out.writeBytes(content); 
	        out.flush();
	        out.close(); // flush and close
			urlCon.getOutputStream().flush();
			urlCon.getOutputStream().close();
			InputStream is = urlCon.getInputStream();// <===注意，实际发送请求的代码段就在这里
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			while (br.read() != -1) {
				sb.append(br.readLine());
			}
			is.close();
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (urlCon != null) {
				urlCon.disconnect();
			}
		}
		return null;
	}
	
	/**
	 * Description:发送http请求
	 * @param urlAddr
	 * @return
	 * @author zhougz
	 */
	public static String sentHttpReq(String urlAddr) {
		return HttpUtil.sentHttpReq(urlAddr,  null);
	}
	
	
	
}
