/*
 * @(#)FtpClient.java
 *
 * CopyRight (c) 2015 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;


/**
 * Title : FtpClient
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 7.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018-2-26    hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018-2-26
 */
public class FtpClient {
    
    private FTPClient ftp;
    
    
    /**    
     *     
     * @param path 上传到ftp服务器哪个路径下       
     * @param addr 地址    
     * @param port 端口号    
     * @param username 用户名    
     * @param password 密码    
     * @return    
     * @throws Exception    
     */      
    public boolean connect(String path,String addr,int port,String username,String password) throws Exception {        
        boolean result = false;
        ftp = new FTPClient();
        int reply;
        ftp.connect(addr,port);
        ftp.login(username,password);
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            return result;
        }
        ftp.changeWorkingDirectory(path);
        result = true;
        return result;
    }

    /**  
     * @author   
     * @class  ItemFtp  
     * @title  upload  
     * @Description :   
     * @time 2013 2013-11-27  
     * @return void  
     * @exception :(Error note)  
     * @param file 上传的文件或文件夹  
     * @param path 上传的文件的路径   
     * @throws Exception  
     */  
    public void upload(File file , String path) throws Exception{
        LogUtil.debug("file.isDirectory() : " + file.isDirectory());
        
        if(file.isDirectory()){
            ftp.makeDirectory(file.getName());
            ftp.changeWorkingDirectory(file.getName());
            String[] files = file.list();
            for (int i = 0; i < files.length; i++) {
                File file1 = new File(file.getPath()+"\\"+files[i] );
                if(file1.isDirectory()){
                    upload(file1 , path );
                    ftp.changeToParentDirectory();
                }else{
                    File file2 = new File(file.getPath()+"\\"+files[i]);
                    FileInputStream input = new FileInputStream(file2);
                    ftp.storeFile(file2.getName(), input);
                    input.close();
                }
            }
        }else{
            File file2 = new File(file.getPath());

            LogUtil.debug(" file.getPath() : " + file.getPath()  + " | file2.getName() : " + file2.getName());

            InputStream input = new FileInputStream(file2);
 
            ftp.changeWorkingDirectory(path);
            ftp.storeFile(file2.getName(), input);

            input.close();  //关闭输入流  
//            ftp.logout();  //退出连接  
        }
    }

    /**
     * Description: 退出FTP连接，每次调用最后都需要调用该方法释放资源
     *
     * @throws Exception
     */
    public void closeFtp() throws Exception{
        LogUtil.debug("log out ftp");
        ftp.logout();  //退出连接  
    }

    /**  
     * @author   
     * @class  ItemFtp  
     * @title  download  
     * @Description : FPT 下载文件方法  
     * @time 2013 2013-11-27  
     * @return void  
     * @exception :(Error note)  
     * @param reomvepath 下载的文件的路径   
     * @param fileName  下载的文件名   
     * @param localPath 下载的文件本地路径  
     * @throws Exception  
     */  
    public void download(String reomvepath, String fileName, String localPath) throws Exception {

        if(!(reomvepath.endsWith("/") || reomvepath.endsWith("\\"))){
            reomvepath +="/";
        }
        ftp.changeWorkingDirectory(reomvepath);

        // 列出该目录下所有文件
        FTPFile[] fs = ftp.listFiles();
        
        isIxistsForder(localPath);
        
        // 遍历所有文件，找到指定的文件
        for (FTPFile ff : fs) {
            if (ff.getName().equals(fileName)) {
                // 根据绝对路径初始化文件
                File localFile = new File(localPath + "/" + ff.getName());
                // 输出流
                OutputStream is = new FileOutputStream(localFile);
                // 下载文件
                ftp.retrieveFile(ff.getName(), is);
                is.close();
                LogUtil.debug("["+ff.getName()+"]下载成功");
            }
        }

//        ftp.logout(); // 退出连接

    }
    /**  
     * 下载指定的文件
     * @author   
     * @class  ItemFtp  
     * @title  download  
     * @Description : FPT 下载文件方法  
     * @time 2015 2015-19-27  
     * @return void    
     * @exception :(Error note)  
     * @param reomvepath 下载的文件的路径   
     * @param fileName  下载的文件名   
     * @param localPath 下载的文件本地路径  
     * @throws Exception  
     */  
    public void downloadFile(String reomvepath, String fileName, OutputStream outputStream) throws Exception {
 
        if(!(reomvepath.endsWith("/") || reomvepath.endsWith("\\"))){
            reomvepath +="/";
        }
        ftp.changeWorkingDirectory(reomvepath);

        // 列出该目录下所有文件
        FTPFile[] fs = ftp.listFiles();

        // 遍历所有文件，找到指定的文件
        for (FTPFile ff : fs) {
            if (ff.getName().equals(fileName)) {
                // 下载文件
                ftp.retrieveFile(ff.getName(), outputStream);
                LogUtil.debug("["+ff.getName()+"]下载成功");
            }
        }
//        ftp.logout(); // 退出连接

    }
    
    /**
     * 检查FTP文件是否存在
     * @param remotePath FTP目录
     * @param fileName  文件名
     * @return
     * @throws Exception
     */
    public boolean existFile(String remotePath, String fileName) throws Exception{
    	if(!(remotePath.endsWith("/") || remotePath.endsWith("\\"))){
    		remotePath +="/";
        }
        ftp.changeWorkingDirectory(remotePath);
        // 列出该目录下所有文件
        FTPFile[] fs = ftp.listFiles();
        // 遍历所有文件，找到指定的文件
        for (FTPFile ff : fs) {
            if (ff.getName().equals(fileName)) {
            	return true;
            }
        }
        return false;
    }
    
    /**
     * Description: 排除批量下载文件（只下载excludeFile中不存在的文件）
     *
     * @param reomvepath 下载的文件的路径 （FTP路径）
     * @param excludeFile 不需要下载的文件列表
     * @param localPath 下载到的本地路径
     * @param fileSuffix 文件后缀
     * @throws Exception
     */
    public void excludeBatchDownload(String reomvepath, Map<String,String> excludeFile, String localPath,String fileSuffix)  throws Exception {
        
        if(!(reomvepath.endsWith("/") || reomvepath.endsWith("\\"))){
            reomvepath +="/";
        }
        
        ftp.changeWorkingDirectory(reomvepath);
        // 列出该目录下所有文件
        FTPFile[] fs = ftp.listFiles();
        isIxistsForder(localPath);
        // 遍历所有文件，找到指定的文件
        for (FTPFile ff : fs) {
            if(excludeFile.containsKey(ff.getName())){
                continue;
            }
            
            // 判断文件后缀是不是要下载的文件后缀
            if(ff.getName().endsWith(fileSuffix)){
                
                // 根据绝对路径初始化文件
                File localFile = new File(localPath + "/" + ff.getName());
                // 输出流
                OutputStream is = new FileOutputStream(localFile);
                // 下载文件
                ftp.retrieveFile(ff.getName(), is);
                is.close();
//                log.debug("文件["+ff.getName()+"]下载成功");
                System.out.println("文件["+ff.getName()+"]下载成功");
            }
        }
    }
    
    /**
     * Description: 批量下载文件（下载fileNames中的文件列表到本地）
     *
     * @param dir 下载的文件的路径 （FTP路径）
     * @param fileNames 下载的文件列表
     * @param localPath 下载到的本地路径
     * @throws Exception
     */
    public void batchDownload(String dir, Map<String,String> fileNames, String localPath)  throws Exception {
        
        if(!(dir.endsWith("/") || dir.endsWith("\\"))){
            dir +="/";
        }
        
        ftp.changeWorkingDirectory(dir);
        isIxistsForder(localPath);
        // 列出该目录下所有文件
        FTPFile[] fs = ftp.listFiles();
        
        // 遍历所有文件，找到指定的文件
        for (FTPFile ff : fs) {
            if (fileNames.containsKey(ff.getName())) {
                
                // 根据绝对路径初始化文件
                File localFile = new File(localPath + "/" + ff.getName());
                // 输出流
                OutputStream is = new FileOutputStream(localFile);
                // 下载文件
                ftp.retrieveFile(ff.getName(), is);
                is.close();
                LogUtil.debug("[" + ff.getName() + "]下载成功");
            }
        }
    }
    
    /**
     * Description: 判断文件夹是否存在 ,不存在就创建文件夹
     *
     * @param path
     */
    private void isIxistsForder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    /**
     * 删除单个问价你的方法
     * @param fileName 文件的全路径，如：/home/hadoop/ys/aa.txt
     * @return
     * @throws Exception
     */
    public boolean deleteFile(String fileName) throws Exception{
    	return ftp.deleteFile(fileName);
    }
    
    /**
     * 创建文件夹，只可以一层一层的创建
     * @param pathname
     * @return
     * @throws Exception
     */
    public boolean mkDir(String pathname) throws Exception{
    	return ftp.makeDirectory(pathname);
    }
    /**
     * 批量下载指定的文件夹下的所有文件
     * @param dir
     * @param localPath
     * @throws Exception
     */
    public void batchDownloadAll(String dir, String localPath) throws Exception{
    	if(!(dir.endsWith("/") || dir.endsWith("\\"))){
            dir +="/";
        }
        ftp.changeWorkingDirectory(dir);
        isIxistsForder(localPath);
        // 列出该目录下所有文件
        FTPFile[] fs = ftp.listFiles();
        
        // 遍历所有文件，找到指定的文件
		for (FTPFile ff : fs) {
			if(ff.isDirectory()){
				this.batchDownloadAll(dir+"/"+ff.getName(), localPath+"/"+ff.getName());
			}else{
				//递归下载进入了其他目录,要先切回原来的目录才能下载文件
				ftp.changeWorkingDirectory(dir);
				// 根据绝对路径初始化文件
				File localFile = new File(localPath + "/" + ff.getName());
				// 输出流
				OutputStream is = new FileOutputStream(localFile);
				// 下载文件
				ftp.retrieveFile(ff.getName(), is);
				is.close();
				LogUtil.debug("[" + ff.getName() + "]下载成功");
			}
			
		}
    }
    public static void main(String[] args) throws Exception {
		FtpClient ftpClient = new FtpClient();
		boolean connect = ftpClient.connect("/home/weblogic/qhy/testDIR", "10.1.253.232", 21,
				"weblogic", "weblogic");
		File file = new File("D:\\testftp\\QQ图12222222片20150313100456.png");
		System.out.println("连接 ：" + connect);
//		t.upload(file, "/testDIR");P50603-075244.jpg
//		ftpClient.download("/home/weblogic/qhy/testDIR", "12121212.png", "D:\\testftp\\download\\");
//		ftpClient.deleteFile("/home/weblogic/qhy/attached/logoImage_20151028153851496.jpg");
//		ftpClient.mkDir("/home/weblogic/qhy/testDIRsdsd/");
		ftpClient.batchDownloadAll("/home/weblogic/qhy/attached/", "D:\\testftp\\download\\new");
		ftpClient.closeFtp();
		
//        PocFtpClient t = new PocFtpClient();
//
//        boolean connect = t.connect("/home/hadoop", "10.1.251.122", 21, "hadoop", "hadoop");
//        System.out.println("连接 ：" + connect);
//
//        // 上传
//        // File file = new File("d:\\test.txt");
//        // t.upload(file , "E:\\ftptest\\mulu");
//
//        // 下载
////        t.download("/home/hadoop", "temperature.txt", "D:\\work");
//        Map<String, String> m = new HashMap<String, String>();
//        m.put("temperature.txt", "temperature.txt");
//        
//        t.excludeBatchDownload("/home/hadoop", m,"D:\\work", "txt");
//        t.closeFtp();
//        System.out.println("over");
//        
//        File file = new File("D:\\work\\test\\mkdir");
//        file.mkdirs();

    }
}
