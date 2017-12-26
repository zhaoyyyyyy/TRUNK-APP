package com.asiainfo.biapp.si.loc.bd.common.util;
//package com.asiainfo.biapp.si.loc.util;
//
//import java.io.BufferedOutputStream;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.security.PrivilegedExceptionAction;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FSDataInputStream;
//import org.apache.hadoop.fs.FSDataOutputStream;
//import org.apache.hadoop.fs.FileStatus;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.IOUtils;
//import org.apache.hadoop.security.UserGroupInformation;
//import org.apache.log4j.Logger;
//
///**
// * hdfs java操作工具类HdfsUtil
// * 
// * @author wanghf5
// * 
// */
//public class HdfsUtil {
//	private static final Logger log = Logger.getLogger(HdfsUtil.class);
//	static FileSystem fs = null;
//	private static int fileLevel;
//	static String hdfsUrl;
//
//	public static void initEnvi(String uri, String user) throws IOException,
//			InterruptedException, URISyntaxException {
//		UserGroupInformation ugi = UserGroupInformation.createRemoteUser(user);
//		hdfsUrl = uri;
//		 try {
//			ugi.doAs(new PrivilegedExceptionAction<Void>() {
//			     public Void run() throws Exception {
//			    	 Configuration conf = new Configuration();
//			    	 conf.set("fs.defaultFS", hdfsUrl);
//			    	 fs = FileSystem.get(new URI(hdfsUrl), conf);
//			    	 return null;
//			     }
//			 });
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public static void closeEnvi() throws IOException {
//
//		fs.close();
//
//	}
//
//	public static String createPrintStr(String name, int level, boolean flag) {
//
//		String printStr = "";
//		for (int i = 0; i < level; i++) {
//			printStr = printStr + "  ";
//		}
//		if (false == flag)
//			printStr = printStr + "- " + name + "   ---file";
//		else
//			printStr = printStr + "- " + name + "   ---dir";
//		return printStr;
//	}
//
//	public static void putFile(String src, String dst)
//			throws IllegalArgumentException, IOException {
//
//		File file = new File(src);
//		if (!file.exists()) {
//			System.out.println(src + " does not exists");
//			return;
//		}
//		fs.copyFromLocalFile(new Path(src), new Path(dst));
//
//	}
//
//	/**
//	 * 将src目下的数据文件遍历读取，追加写入到目标数据文件里
//	 * @param src HDFS源数据文件路径
//	 * @param dst 本地数据文件需要存放的路径 目的路径是Linux下的路径，如果在 windows 下测试，需要改写为Windows下的路径，比如D://hadoop/djt/
//	 * @throws IllegalArgumentException
//	 * @throws IOException
//	 */
//	public static void getFile(String src, String dst)
//			throws IllegalArgumentException, IOException {
//		log.debug(" ----------------------------  hdfsUtil   getFile begining------------");
//		
//		log.debug(" ----------------------------  hdfsUtil   src =" + src);
//		log.debug(" ----------------------------  hdfsUtil   src =" + dst);
//		Path path = new Path(src);
//		if (checkPath(path))
//			return;
//		FileStatus[] listStatus = fs.listStatus(path);
//		for (FileStatus status : listStatus) {
//
//			String name = status.getPath().getName();
//			String localPath = src + name;
//			Path srcPath = new Path(localPath);
//
//			FSDataInputStream in = null;
//			BufferedWriter out = null;
//			try {
//				//先读出input流
//				in = fs.open(srcPath);
//				//由于追加方式写入不支持字节流读取，只能先转成字符流
//				BufferedReader bf = new BufferedReader(new InputStreamReader(in));
//				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dst, true),"gbk"));
//				// 打开一个随机访问文件流，按读写方式     
//				String valueString = null;
//				while ((valueString=bf.readLine())!=null) {
//					out.write(valueString);
//					out.newLine();//由于工具类不做自动换行，只能手动增加换行操作
//				}
//				bf.close();
//				in.close();
//				out.close();
//			} catch (IOException e) {
//				IOUtils.closeStream(in);
//				throw e;
//			}
//
//			// 下载hdfs上的文件
//			// fs.copyToLocalFile(false,srcPath, dstPath,true);
//
//		}
//		// 释放资源
//		fs.close();
//		log.debug(" ----------------------------  hdfsUtil   getFile OVER ------------");
//	}
//
//	public static void fileLocation(String fPath) throws FileNotFoundException,
//			IllegalArgumentException, IOException {
//		Path path = new Path(fPath);
//		if (checkPath(path))
//			return;
//		FileStatus[] listStatus = fs.listStatus(path);
//
//		for (FileStatus status : listStatus) {
//
//			String name = status.getPath().getName();
//			if (status.isDirectory()) {
//
//				System.out.println(createPrintStr(name, fileLevel, true));
//				fileLevel++;
//				fileLocation(status.getPath().toString());
//				fileLevel--;
//			} else {
//				System.out.println(createPrintStr(name, fileLevel, false));
//			}
//		}
//	}
//
//	public static FSDataOutputStream createFile(String fPath, Boolean overwrite)
//			throws IllegalArgumentException, IOException {
//
//		FSDataOutputStream create = fs.create(new Path(fPath), overwrite);
//
//		return create;
//
//	}
//
//	public static boolean mikdir(String fPath) throws IllegalArgumentException,
//			IOException {
//
//		return fs.mkdirs(new Path(fPath));
//
//	}
//
//	public static boolean remove(String path, Boolean recursive)
//			throws IllegalArgumentException, IOException {
//		Path fPath = new Path(path);
//		if (checkPath(fPath))
//			return false;
//
//		return fs.delete(fPath, recursive);
//
//	}
//
//	public static boolean checkfPath(Path Path) throws IOException {
//
//		if (!fs.exists(Path) || fs.getFileStatus(Path).isDirectory()) {
//			System.out.println("File " + Path + " does not exists");
//			return true;
//		}
//		return false;
//
//	}
//
//	public static boolean checkPath(Path Path) throws IOException {
//
//		if (!fs.exists(Path)) {
//			System.out.println("File " + Path + " does not exists");
//			return true;
//		}
//		return false;
//
//	}
//
//	public static void readFile(String fPath) throws IOException {
//		Path path = new Path(fPath);
//		if (!fs.exists(path)) {
//			System.out.println("File " + fPath + " does not exists");
//			return;
//		}
//
//		FSDataInputStream in = fs.open(path);
//
//		String filename = fPath.substring(fPath.lastIndexOf('/') + 1,
//				fPath.length());
//
//		OutputStream out = new BufferedOutputStream(new FileOutputStream(
//				new File(filename)));
//
//		byte[] b = new byte[1024];
//		int numBytes = 0;
//		while ((numBytes = in.read(b)) > 0) {
//			out.write(b, 0, numBytes);
//		}
//
//		in.close();
//		out.close();
//		// fs.close();
//	}
//
//	public static void catFile(String remoteFile) throws IOException {
//
//		Path path = new Path(remoteFile);
//		if (checkfPath(path))
//			return;
//
//		FSDataInputStream fsdis = null;
//		System.out.println("cat: " + remoteFile);
//		try {
//			fsdis = fs.open(path);
//			IOUtils.copyBytes(fsdis, System.out, 4096, false);
//		} finally {
//			IOUtils.closeStream(fsdis);
//			// fs.close();
//		}
//	}
//
//	public static void main(String[] args) throws IOException,
//			InterruptedException, URISyntaxException {
//
//		HdfsUtil.initEnvi("hdfs://10.1.251.98:8033/", "root");
//		HdfsUtil.fileLocation("/user/hive/warehouse/ci_cuser_20150116104533694_484750/");
//		// HdfsUtil.catFile("/chengdu/md_user_ip_20160111/data/md_user_ip_20160111.csv");
//		HdfsUtil.getFile("/user/hive/warehouse/ci_cuser_20150116104533694_484750/",
//				"D://hadoop/djt/gaga.csv");
////		HdfsUtil.readFile("/user/hive/warehouse/ci_cuser_20150116104533694_484750/part-00000");
//		// HdfsUtil.remove("/abcd", true);
//		// HdfsUtil.putFile("C:\\data1.txt", "/input/data/");
//	}
//
//}
