package com.asiainfo.biapp.si.loc.base.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class HttpUtil {
	 static boolean proxySet = false;
	    static String proxyHost = "127.0.0.1";
	    static int proxyPort = 8087;
	    /** 
	     * 编码 
	     * @param source 
	     * @return 
	     */  
	    public static String urlEncode(String source,String encode) {  
	        String result = source;  
	        try {  
	            result = java.net.URLEncoder.encode(source,encode);  
	        } catch (UnsupportedEncodingException e) {  
	        	LogUtil.error("请求URL转码异常:"+source, e);
	            return "0";  
	        }  
	        return result;  
	    }
	    public static String urlEncodeGBK(String source) {  
	        String result = source;  
	        try {  
	            result = java.net.URLEncoder.encode(source,"GBK");  
	        } catch (UnsupportedEncodingException e) {  
	        	LogUtil.error("URL到GBK编码转换异常", e);
	            return "0";  
	        }  
	        return result;  
	    }
	    /** 
	     * 发起http请求获取返回结果 
	     * @param req_url 请求地址 
	     * @return 
	     */  
	    public static String httpRequest(String req_url) {
	        StringBuffer buffer = new StringBuffer(); 
	        InputStream inputStream = null;
	        InputStreamReader inputStreamReader = null;
	        HttpURLConnection httpUrlConn = null;
	        BufferedReader bufferedReader = null;
	        try {  
	            URL url = new URL(req_url);  
	            httpUrlConn = (HttpURLConnection) url.openConnection();  

	            httpUrlConn.setDoOutput(false);  
	            httpUrlConn.setDoInput(true);  
	            httpUrlConn.setUseCaches(false);  

	            httpUrlConn.setRequestMethod("GET");  
	            httpUrlConn.connect();  

	            // 将返回的输入流转换成字符串  
	            inputStream = httpUrlConn.getInputStream();  
	            inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
	            bufferedReader = new BufferedReader(inputStreamReader);  

	            String str = null;  
	            while ((str = bufferedReader.readLine()) != null) {  
	                buffer.append(str);  
	            }  

	        } catch (Exception e) {  
	            LogUtil.error("发起http请求获取返回结果失败" , e);
	        }finally {
				if(bufferedReader != null){
					try {
						bufferedReader.close();
					} catch (IOException e) {
						LogUtil.error("http请求,关闭输入流事变" , e);
					}  
				}
				if(inputStreamReader != null){
					try {
						inputStreamReader.close();
					} catch (IOException e) {
						LogUtil.error("http请求,关闭输入流失败" , e);
					}  
				}
				if(httpUrlConn != null){
					httpUrlConn.disconnect();  
				}
			}
	        return buffer.toString();  
	    }  

	    /** 
	     * 发送http请求取得返回的输入流 
	     * @param requestUrl 请求地址 
	     * @return InputStream 
	     */  
	    public static InputStream httpRequestIO(String requestUrl) {  
	        InputStream inputStream = null;  
	        try {  
	            URL url = new URL(requestUrl);  
	            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
	            httpUrlConn.setDoInput(true);  
	            httpUrlConn.setRequestMethod("GET");  
	            httpUrlConn.connect();  
	            // 获得返回的输入流  
	            inputStream = httpUrlConn.getInputStream();  
	        } catch (Exception e) {  
	        	LogUtil.error("发送http请求取得返回的输入流"+requestUrl,e);
	        }  
	        return inputStream;  
	    }

	    /**
	     * 向指定URL发送GET方法的请求
	     * 
	     * @param url
	     *            发送请求的URL
	     * @param param
	     *            请求参数，请求参数应该是K-V 的形式。
	     * @return URL 所代表远程资源的响应结果
	     */
	    public static String sendGet(String url, Map<String,Object> paramMap)  throws Exception{
	    	StringBuilder paramStrSb = new StringBuilder();
	    	if(paramMap != null && paramMap.size() > 0){
	    		Iterator<String> it = paramMap.keySet().iterator();
	    		int i = 0;
	    		while(it.hasNext()){
	    			if(i>0){
	    				paramStrSb.append("&");
	    			}
	    			String key = it.next();
	    			paramStrSb.append(key).append("=").append(paramMap.get(key).toString());
	    			i++;
	    		}
	    	}
	    	return sendGet(url, paramStrSb.toString());
	    }

	    /**
	     * 向指定URL发送GET方法的请求
	     * 
	     * @param url
	     *            发送请求的URL
	     * @param param
	     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。IO
	     * @return URL 所代表远程资源的响应结果
	     */
	    public static String sendGet(String url, String param) throws Exception{
	        String result = "";
	        BufferedReader in = null;
	        try {
	            String urlNameString = url + "?" + param ;
	            URL realUrl = new URL(urlNameString);
	            // 打开和URL之间的连接
	            URLConnection connection = realUrl.openConnection();
	            // 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            
	            if(param.contains("token")){
	            	connection.setRequestProperty("X-Authorization", param.split("=")[1]);
	            }
	            
	            // 建立实际的连接
	            connection.connect();
	            
	            // 获取所有响应头字段
	            Map<String, List<String>> map = connection.getHeaderFields();
	            // 遍历所有的响应头字段
//	            for (String key : map.keySet()) {
//	                LogUtil.info(key + "--->" + map.get(key));
//	            }
	            // 定义 BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	        	throw e;
	        }
	        // 使用finally块来关闭输入流
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	            	throw e2;
	            }
	        }
	        return result;
	    }

	    /**
	     * 向指定 URL 发送POST方法的请求
	     * 
	     * @param url
	     *            发送请求的 URL
	     * @param param
	     *            请求参数，请求参数应该是 k-v 的形式。
	     * @param isproxy
	     *               是否使用代理模式
	     * @return 所代表远程资源的响应结果
	     */
	    public static String sendPost(String url, Map<String,Object> paramMap)  throws Exception{
	    	StringBuilder paramStrSb = new StringBuilder();
	    	if(paramMap != null && paramMap.size() > 0){
	    		//paramStrSb.append("?");
	    		Iterator<String> it = paramMap.keySet().iterator();
	    		int i = 0;
	    		while(it.hasNext()){
	    			if(i>0){
	    				paramStrSb.append("&");
	    			}
	    			String key = it.next();
	    			Object value = paramMap.get(key);
	    			if(value != null){
	    				paramStrSb.append(key).append("=").append(value.toString());
	    			}
	    			i++;
	    		}
	    	}
	    	return sendPost(url, paramStrSb.toString(),false);
	    }
	    /**
	     * 向指定 URL 发送POST方法的请求
	     * 
	     * @param url
	     *            发送请求的 URL
	     * @param param
	     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	     * @param isproxy
	     *               是否使用代理模式
	     * @return 所代表远程资源的响应结果
	     */
	    public static String sendPost(String url, String param,boolean isproxy)  throws Exception{
	        OutputStreamWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            HttpURLConnection conn = null;
	            if(isproxy){//使用代理模式
	                @SuppressWarnings("static-access")
	                Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP, new InetSocketAddress(proxyHost, proxyPort));
	                conn = (HttpURLConnection) realUrl.openConnection(proxy);
	            }else{
	                conn = (HttpURLConnection) realUrl.openConnection();
	            }
	            // 打开和URL之间的连接

	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            conn.setRequestMethod("POST");    // POST方法

	            // 设置通用的请求属性
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            //conn.setRequestProperty("Content-Type", "application/json");

	            if(param.contains("token")){
                    String[] params = param.split("&");
                    for(int i = 0;i<params.length;i++){
                        if(params[i].contains("token")){
                            conn.setRequestProperty("X-Authorization", params[i].split("=")[1]);
                        }else{
                            continue;
                        }
                    }
                }
	            
	            conn.connect();

	            // 获取URLConnection对象对应的输出流
	            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
	            // 发送请求参数
	            out.write(param);
	            // flush输出流的缓冲
	            out.flush();
	            // 定义BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	        	throw e;
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	            	throw ex;
	            }
	        }
	        return result;
	    }    

}
