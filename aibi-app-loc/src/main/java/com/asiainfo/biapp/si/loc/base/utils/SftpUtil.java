/*
 * @(#)ScheduleController.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.base.utils;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

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
    public void download(String directory, String downloadFile,String saveFile) {
        try {
            sftp.cd(directory);
            File file=new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
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
            String remotepath,String listTableName) throws IOException {
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
            sftpUtil.download(remotepath, filename, localpath);
         
            //下载完毕后 删除ftp上的文件
            sftpUtil.delete(remotepath, listTableName, cSftp);
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
            LogUtil.debug("The cost of uploading file: " + (System.currentTimeMillis() - t1) / 1000 + "s.");
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
            String remotepath) {
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
                if (locfile.isFile()) {
                    //如果远端是目录，则默认远端的文件名与本地相同
                    if (remotefile.endsWith("/")) {
                        remotefile = remotefile + locfile.getName();
                    } else if (!remotefile.contains(".") && !remotefile.endsWith("/")) {
                        remotefile = remotefile + "/" + locfile.getName();
                    }
                    remotefile = new String(remotefile.getBytes("UTF-8"),
                            "iso-8859-1");
                    sftpUtil.upload(cSftp, localpath, remotefile + UN_UPLOADED);
                } else {
                    remotefile = new String(remotefile.getBytes("UTF-8"),
                            "iso-8859-1");
                    flag = sftpUtil.uploadAll(cSftp, localpath, remotefile + UN_UPLOADED);
                }
                LogUtil.debug("sftp rename [" + remotefile + UN_UPLOADED + "] to [" + remotefile + "]");
                cSftp.rename(new File(remotefile + UN_UPLOADED).getName(), new File(remotefile).getName());//上传完改名
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
            LogUtil.debug("The cost of uploading file: " + (System.currentTimeMillis() - t1) / 1000 + "s.");
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
    private void upload(ChannelSftp client, String local, String remote) throws Exception {
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
        uploadFile(client, remoteFileName, local);
    }

    private void uploadFile(ChannelSftp client, String remoteFile, String localFile) throws IOException {
        LogUtil.debug("upload file: " + localFile);
        
        try {
//            client.put(localFile, remoteFile);//coc 3.X 的正常可用的代码
            
            //coc 4.X 的正常可用的代码
            client.put(new File(localFile).getAbsolutePath(), remoteFile, ChannelSftp.OVERWRITE);
        } catch (SftpException e) {
            LogUtil.error("sftp uploadFile ["+localFile+remoteFile+"] error!", e);
        }
        
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
    private boolean uploadAll(ChannelSftp client, String filename, String uploadpath) throws Exception {
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
                    uploadAll(client, f.getAbsoluteFile().toString(), uploadpath);
                } else {
                    String local = f.getCanonicalPath().replaceAll("\\\\", "/");
                    String remote = uploadpath.endsWith("/") ? uploadpath + local.substring(local.lastIndexOf("/") + 1)
                            : uploadpath + "/" + local.substring(local.lastIndexOf("/") + 1);
                    upload(client, local, remote);
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
    
    
}
