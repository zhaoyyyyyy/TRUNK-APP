/*
 * @(#)ScheduleController.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.base.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;

/**
 * 
 * Title : SftpUtil
 * <p/>
 * Description : Sftp工具类
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 7.0 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2016-7-28    Hongfb        Created</pre>
 * <p/>
 *
 * @author  Hongfb
 * @date 2017-2-28
 * @version 1.0.0.2017-2-28
 */

public class SftpUtil {
    
    private static final String UN_UPLOADED = "_unUploaded";//未完成的后缀;
    
    private ChannelSftp sftp = null;
    
    private static Map<String, String> isCompletedMap = new ConcurrentHashMap<>();    //false:未完成；true：已完成
    
    
    /**
     * @param host 主机
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @return boolean
     *
     * @author  Hongfb
     * @date 2016-7-28
     * @version 1.0.0.2016-7-28
     * */
    public ChannelSftp  getconnect( String host, String username, String password,int port) {
        try {
            if (sftp != null) {
                LogUtil.info("sftp不为空！");
            }
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            LogUtil.error("sftp登录失败！" + e);
        }
        
        LogUtil.debug("sftp登录成功！");
        
        return sftp;
    }
    /**
     * 断开STFP
     *
     * @author  Hongfb
     * @date 2016-7-28
     * @version 1.0.0.2016-7-28
     * */
    public void disconnect() {
        if (this.sftp != null) {
            if (this.sftp.isConnected()) {
                this.sftp.disconnect();
            } else if (this.sftp.isClosed()) {
                LogUtil.debug("sftp is closed already");
            }
        }
    }
    
    /**
     * 下载文件
     * @param directory 下载目录
     * @param downloadFile 下载的文件名
     * @param saveFile 存在本地的路径
     * @param sftp
     *
     * @author  Hongfb
     * @date 2016-7-28
     * @version 1.0.0.2016-7-28
     * */
    public void download(String directory, String downloadFile,String saveFile,boolean isHasMonitor, String threadName) {
        try {
            sftp.cd(directory);
            File file=new File(saveFile);
            SftpProgressMonitor monitor = null;
            if (isHasMonitor) {
                monitor = getSftpProgressMonitor(threadName);
            }
            sftp.get(downloadFile, new FileOutputStream(file), monitor);
        } catch (Exception e) {
            LogUtil.error("sftp download file ["+directory+saveFile+"] error!", e);
        }
    }
    /**
    * 删除文件
    * @param directory 要删除文件所在目录
    * @param deleteFile 要删除的文件
    * @param sftp
     *
     * @author  Hongfb
     * @date 2016-7-28
     * @version 1.0.0.2016-7-28
    */
    public void delete(String directory, String deleteFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            LogUtil.error("sftp delete ["+directory+deleteFile+"] error!", e);
        }
    }
    
    
    /**
     * 执行ftp下载操作
     * @param address 目标地址
     * @param port 端口
     * @param user 用户
     * @param password 口令
     * @param localpath 本地文件路径（可以是目录或文件名）
     * @param remotepath 远端路径（可以是目录或文件名）
     * @return
     * 
     * @date 2016-7-8
     * @version Hongfb
     */
    public static boolean sftpDownload(String address, String port, String user, String password, String localpath,
            String remotepath,String listTableName,boolean isDel,boolean isHasMonitor) throws IOException {
        boolean flag = false;
        long t1 = System.currentTimeMillis();
        SftpUtil sftpUtil = new SftpUtil();
        ChannelSftp cSftp = null;
        try {
            if (StringUtils.isEmpty(address) || StringUtils.isEmpty(user) || StringUtils.isEmpty(password)) {
                throw new Exception("address or user or password is null");
            }
            //连接ftp
            cSftp = sftpUtil.getconnect(address, user, password, StringUtils.isEmpty(port) ? 22 : Integer.valueOf(port));
            if (cSftp == null) {
                throw new Exception("sftp login fail:[user=" + user + "] or password or port[port="
                        + port + "] wrong,please check it!");
            } else {
                LogUtil.debug("sftp login success!");
            }
            

            String[] localPathArr = localpath.split(localpath.contains("/") ? "/" : "\\");
            String filename = localPathArr[localPathArr.length - 1];
            String threadName = "ThreadName" + String.valueOf((int)Math.floor(Math.random() * 10000));
            sftpUtil.download(remotepath, filename, localpath, isHasMonitor,threadName);
            if (isHasMonitor) {
	            	//等待文件传输完成
	            	int waitNO= 1;
	            	while (!Boolean.valueOf(SftpUtil.isCompletedMap.get(threadName))) {
	            		Thread.sleep((waitNO+25)*1000);
	            		++ waitNO;
	            		if (waitNO > 60) break; //超时失败
	            	}
	            	flag = Boolean.valueOf(SftpUtil.isCompletedMap.get(threadName));
            }
            if (isDel) {
                //下载完毕后 删除ftp上的文件
                sftpUtil.delete(remotepath, listTableName, cSftp);
            }
        } catch (Exception e) {
            flag = false;
            LogUtil.error("sftp error:", e);
        } finally {
            if (cSftp != null) {
                try {
                    cSftp.disconnect();
                    cSftp.exit();
                } catch (Exception e) {
                    LogUtil.error("sftp exit error:", e);
                }
            }
            long cost = System.currentTimeMillis() - t1;
            LogUtil.debug("The cost of Download file:" + (cost>1000 ? cost/1000 : cost) + (cost>1000 ? "s" : "ms") +".");
        }
        return flag;
    }
    

    /**
     * 执行sftp操作
     * @param address 目标地址
     * @param port 端口
     * @param user 用户
     * @param password 口令
     * @param localpath 本地文件路径（可以是目录或文件名）
     * @param remotepath 远端路径（可以是目录或文件名）
     * @return
     * 
     * @date 2016-7-8
     * @version Hongfb
     */
    public static boolean sftp(String address, String port, String user, String password, String localpath,
            String remotepath, boolean isHasMonitor) {
        boolean flag = true;
        long t1 = System.currentTimeMillis();
        SftpUtil sftpUtil = new SftpUtil();
        ChannelSftp cSftp = null;
        try {
            if (StringUtils.isEmpty(address) || StringUtils.isEmpty(user) || StringUtils.isEmpty(password)) {
                throw new Exception("address or user or password is null");
            }
            //连接ftp
            cSftp = sftpUtil.getconnect(address, user, password, StringUtils.isEmpty(port) ? 22 : Integer.valueOf(port));
            if (cSftp == null) {
                flag = false;
                throw new Exception("sftp login fail:[user=" + user + "] or password or port[port="
                        + port + "] wrong,please check it!");
            } else {
                LogUtil.debug("sftp login success!");
            }
            
            String remotefile = remotepath;
            File locfile = new File(localpath);
            if (locfile.exists()) {
	            	String threadName = "ThreadName" + String.valueOf((int)Math.floor(Math.random() * 10000));
                if (locfile.isFile()) {
                    //如果远端是目录，则默认远端的文件名与本地相同
                    if (remotefile.endsWith("/")) {
                        remotefile = remotefile + locfile.getName();
                    } else if (!remotefile.contains(".") && !remotefile.endsWith("/")) {
                        remotefile = remotefile + "/" + locfile.getName();
                    }
                    remotefile = new String(remotefile.getBytes("UTF-8"), "iso-8859-1");

                    sftpUtil.upload(cSftp, localpath, remotefile + UN_UPLOADED, isHasMonitor,threadName);
                    if (isHasMonitor) {
	                    	//等待文件传输完成
	                    	int waitNO= 1;
	                    	while (!Boolean.valueOf(SftpUtil.isCompletedMap.get(threadName))) {
	                    		Thread.sleep((waitNO+25)*1000);
	                    		++ waitNO;
	                    		if (waitNO > 60) break; //超时失败
	                    	}
	                    	flag = Boolean.valueOf(SftpUtil.isCompletedMap.get(threadName));
                    }
                } else {
                    remotefile = new String(remotefile.getBytes("UTF-8"), "iso-8859-1");
                    flag = sftpUtil.uploadAll(cSftp, localpath, remotefile + UN_UPLOADED, isHasMonitor,threadName);
                }
                LogUtil.debug("sftp rename [" + remotefile + UN_UPLOADED + "] to [" + remotefile + "]");
                cSftp.rename(new File(remotefile + UN_UPLOADED).getName(), new File(remotefile).getName());//上传完改名
                flag = true;
            } else {
                flag = false;
                throw new Exception("localpath[" + localpath + "] not exist!");
            }
        } catch (Exception e) {
            flag = false;
            LogUtil.error("sftp error:", e);
        } finally {
            if (cSftp != null) {
                try {
                    cSftp.disconnect();
                    cSftp.exit();
                } catch (Exception e) {
                    LogUtil.error("sftp exit error:", e);
                }
            }
            long cost = System.currentTimeMillis() - t1;
            LogUtil.debug("The cost of uploading file:" + (cost>1000 ? cost/1000 : cost) + (cost>1000 ? "s" : "ms") +".");
        }
        return flag;
    }
    /**
     * 上传单个文件
     * @param local
     * @param remote
     * @throws IOException
     * 
     * @date 2016-7-8
     * @version Hongfb
     */
    private boolean upload(ChannelSftp client, String local, String remote, boolean isHasMonitor,String threadName) throws Exception {
        LogUtil.debug("SFTP from:" + local + " to : " + remote);
        // 设置PassiveMode传输
//        client.enterLocalPassiveMode();
        // 设置以二进制流的方式传输
//        client.setFileType(FTP.BINARY_FILE_TYPE);
        
        // 对远程目录的处理
        String remoteFileName = remote;
        if (remote.contains("/")) {
            remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
            // 创建服务器远程目录结构，创建失败直接返回
            if (!createDirecroty(client, remote)) {
                LogUtil.warn("Create direcroty of remote(" + remote + ") fail!");
                throw new Exception("Create direcroty of remote(" + remote
                        + ") fail!please check FTP user's permission!");
            }
        }
        return uploadFile(client, remoteFileName, local, isHasMonitor,threadName);
    }

    private boolean uploadFile(ChannelSftp client, String remoteFile, String localFile, boolean isHasMonitor, String threadName) throws IOException {
        LogUtil.debug("upload file: " + localFile);
        boolean res = true;
        
        try {
//            client.put(localFile, remoteFile);//coc 3.X 的正常可用的代码
            
            //coc 4.X 的正常可用的代码
            if (!isHasMonitor) {
                client.put(new File(localFile).getAbsolutePath(), remoteFile, ChannelSftp.OVERWRITE);
            } else {
                client.put(new File(localFile).getAbsolutePath(), remoteFile, getSftpProgressMonitor(threadName));
            }
        } catch (SftpException e) {
            res = false;
            LogUtil.error("sftp uploadFile ["+localFile+"->"+remoteFile+"] error!", e);
        }
        return res;
        
    }
    
    /**
     * 上传文件,可以是目录
     * @param client
     * @param filename
     * @param uploadpath
     * @return
     * @throws Exception
     * 
     * @date 2016-7-8
     * @version Hongfb
     */
    private boolean uploadAll(ChannelSftp client, String filename, String uploadpath, boolean isHasMonitor,String threadName) throws Exception {
        boolean success = false;

        File file = new File(filename);
        // 要上传的是否存在
        if (!file.exists()) {
            return success;
        }
        // 要上传的是否是文件夹
        if (!file.isDirectory()) {
            return success;
        }
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.exists()) {
                if (f.isDirectory()) {
                    uploadAll(client, f.getAbsoluteFile().toString(), uploadpath, isHasMonitor,threadName);
                } else {
                    String local = f.getCanonicalPath().replaceAll("\\\\", "/");
                    String remote = uploadpath.endsWith("/") ? uploadpath + local.substring(local.lastIndexOf("/") + 1)
                            : uploadpath + "/" + local.substring(local.lastIndexOf("/") + 1);
                    upload(client, local, remote, isHasMonitor, threadName);
                    client.cd("/");
                }
            }
        }
        return true;
    }
    
    
    /**
     * 递归创建远端目录
     * @param client
     * @param remote
     * @return
     * @throws IOException
     * 
     * @date 2016-7-8
     * @version Hongfb
     */
    private boolean createDirecroty(ChannelSftp client, String remote) throws IOException {
        boolean success = true;
        String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
        
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/")) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            while (true) {
                String subDirectory = remote.substring(start, end);
                
                //如果存在，则切换到此目录
				try {
					client.cd(directory);
//					client.cd(subDirectory);
				} catch (SftpException se){
					//如果不存在，则创建此目录并切换到此目录
				    if(ChannelSftp.SSH_FX_NO_SUCH_FILE == se.id){
		                try {
		                	client.mkdir(subDirectory);
		                	client.cd(subDirectory);
		                } catch (SftpException e) {
		                    LogUtil.error("创建目录失败！");
		                    success = false;
		                    return success;
		                }
				    }
				}
                
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        
        return success;
    }
    
    /**
     * 获得一个sftp进程监控器实例
     * @return
     */
    public SftpProgressMonitor getSftpProgressMonitor(final String threadName) {
        
        return new SftpProgressMonitor(){
            private long count=0;     //当前接收的总字节数
            private long max=0;       //最终文件大小
            private long percent=-1;  //进度
            private long cost=0;      //耗时
            
            /**
             * 当开始传输时，调用init方法
             */
            @Override
            public void init(int op,String src,String dest,long max){
                LogUtil.debug("【Sftp Monitor】Transferring begin.");
                
                Thread.currentThread().setName(threadName);
                SftpUtil.isCompletedMap.put(threadName, "false");    //false:未完成；true：已完成
                
                this.cost = System.currentTimeMillis();
                this.max=max;
                this.count=0;
                this.percent=-1;
            }

           /**
            * 当每次传输了一个数据块后，调用count方法，count方法的参数为这一次传输的数据块大小
            */
            @Override
            public boolean count(long count){
                this.count += count;
                if(percent >= this.count*100/max){
                    return true;
                }
                percent = this.count*100/max;

                //打印当前进度
                LogUtil.debug("【Sftp Monitor】Completed "+this.count+" byte("+percent +"％) out of "+max+" byte.");
                
                return true;
            }
            
           /**
            *当传输结束时，调用end方法
            */
            @Override
            public void end(){
                SftpUtil.isCompletedMap.put(threadName, "true");    //false:未完成；true：已完成
                
                LogUtil.debug("传输已完成"); 
                LogUtil.debug("【Sftp Monitor】Transferring done.cost:"+(System.currentTimeMillis()-cost)+"ms.");
            }
        };
    }
    
    
}
