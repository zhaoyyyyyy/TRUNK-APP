/*
 * @(#)FtpUtil.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
 
package com.asiainfo.biapp.si.loc.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;

/**
 * Title : FtpUtil
 * <p/>
 * Description : FTP操作工具类
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

public class FtpUtil {
	private static final String UN_UPLOADED = "_unUploaded";// 未完成的后缀
    private static final String ENCODE_UTF_8 = "UTF-8"; //UTF-8;
    private static final String ENCODE_GBK = "GBK";     //GBK;
    private static final String ENCODE_ISO_8859_1 = "iso-8859-1";     //iso-8859-1;

	private FTPClient ftpClient;
	public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;
	public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE;
    private static final int FTP_DEFAULT_PORT = 21;    //ftp默认端口;

	public FtpUtil() {
		super();
		this.ftpClient = new FTPClient();
	}

	public boolean connectServer(String server, int port, String user, String password, String path)
			throws IOException {
		ftpClient.setControlEncoding(ENCODE_GBK);
		ftpClient.connect(server, port);
		boolean flag = false;
		LogUtil.debug("Connected to " + server + "  server response:" + ftpClient.getReplyCode());

		if (ftpClient.login(user, password)) {
			LogUtil.debug("log in success");
			flag = true;
		} else {
			LogUtil.debug("log in failed user:" + user);
		}
		ftpClient.setFileType(BINARY_FILE_TYPE);
		// Path is the sub-path of the FTP path
		if (path.length() != 0) {
			if (ftpClient.changeWorkingDirectory(path)) {
				LogUtil.debug("ftp:working directory change to:" + path);
			} else {
				LogUtil.debug("ftp:change working directory to:" + path + " failed");
			}

		}
		return flag;
	}

	public void closeServer() {
		if (ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (Exception e) {
				LogUtil.error("", e);
			}
			LogUtil.debug("ftp:disconnnect");
		}
	}

	public long checkFile(String fileName) throws IOException {
		long fileLength = -1;
		FTPFile[] ftpFiles = ftpClient.listFiles();
		for (FTPFile ftpFile : ftpFiles) {
			if (ftpFile.isFile() && fileName.equals(ftpFile.getName())) {
				fileLength = ftpFile.getSize();
				break;
			}
		}
		LogUtil.debug("ftp:check file [" + fileName + "] length length=" + fileLength);
		return fileLength;
	}

	/**
	 * 检查FTP目录是否存在
	 * 
	 * @param remotePath
	 *            FTP目录
	 * @return boolean
	 * @throws Exception
	 */
	public boolean existDir(String remoteDir) {
		boolean result = true;

		try {
			result = ftpClient.changeWorkingDirectory(remoteDir);
		} catch (Exception e) {
			LogUtil.info("目录不存在！");
			result = false;// ftp的目录不存在
		} finally {
			if (ftpClient != null) {
				try {
					ftpClient.disconnect();
					ftpClient.logout();
				} catch (Exception e) {
                    LogUtil.warn("ftp 断开退出警告。");
				}
			}
		}

		return result;
	}

	/**
	 * 检查FTP文件是否存在
	 * 
	 * @param remotePath
	 *            FTP目录
	 * @param fileName
	 *            文件名
	 * @return
	 * @throws Exception
	 */
	public boolean existFile(String remotePath, String fileName) throws Exception {
		if (!(remotePath.endsWith(File.separator) || remotePath.endsWith("\\"))) {
			remotePath += File.separator;
		}
		LogUtil.debug("检查目录：" + remotePath + " 检查文件：" + fileName);
		ftpClient.changeWorkingDirectory(remotePath);
		// 列出该目录下所有文件
		FTPFile[] fs = ftpClient.listFiles();
		// 遍历所有文件，找到指定的文件
		for (FTPFile ff : fs) {
			if (ff.getName().equals(fileName)) {
				return true;
			}
		}
		return false;
	}

	// 从ftp下载文件
	public boolean download(String remoteFileName, String localFileName) throws IOException {
		boolean flag = false;
		File outfile = new File(localFileName);
		OutputStream oStream = null;
		try {
			oStream = new FileOutputStream(outfile);
			flag = ftpClient.retrieveFile(remoteFileName, oStream);
		} catch (IOException e) {
			flag = false;
			return flag;
		} finally {
			if (oStream != null) {
				oStream.close();
			}
		}
		return flag;
	}

	public boolean deleteFile(String pathName) throws IOException {
		return ftpClient.deleteFile(pathName);
	}

	/**
	 * 执行ftp操作
	 * 
	 * @param address
	 *            目标地址
	 * @param port
	 *            端口
	 * @param user
	 *            用户
	 * @param password
	 *            口令
	 * @param localpath
	 *            本地文件路径（可以是目录或文件名）
	 * @param remotepath
	 *            远端路径（可以是目录或文件名）
	 * @return
	 */
	public static boolean ftp(String address, String port, String user, String password, String localpath,
			String remotepath) {
		boolean flag = true;
		long t1 = System.currentTimeMillis();
		FTPClient client = null;
		try {
			if (StringUtils.isEmpty(address) || StringUtils.isEmpty(user) || StringUtils.isEmpty(password)) {
				throw new BaseException("address or user or password is null");
			}
			client = new FTPClient();
			client.connect(address, StringUtils.isEmpty(port) ? FTP_DEFAULT_PORT : Integer.valueOf(port));
			client.login(user, password);
			if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
				throw new BaseException("ftp login fail:[user=" + user + "] or password or port[port=" + port
						+ "] wrong,please check it!");
			} else {
				LogUtil.debug("ftp login success!");
			}
			String remotefile = remotepath;
			File locfile = new File(localpath);
			if (locfile.exists()) {
				if (locfile.isFile()) {
					// 如果远端是目录，则默认远端的文件名与本地相同
					if (remotefile.endsWith(File.separator)) {
						remotefile = remotefile + locfile.getName();
					} else if (!remotefile.contains(".") && !remotefile.endsWith(File.separator)) {
						remotefile = remotefile + File.separator + locfile.getName();
					}
					remotefile = new String(remotefile.getBytes(ENCODE_UTF_8), ENCODE_ISO_8859_1);
					flag = upload(client, localpath, remotefile + UN_UPLOADED);
				} else {
					remotefile = new String(remotefile.getBytes(ENCODE_UTF_8), ENCODE_ISO_8859_1);
					flag = uploadAll(client, localpath, remotefile + UN_UPLOADED);
				}
				LogUtil.debug("ftp rename [" + remotefile + UN_UPLOADED + "] to [" + remotefile + "]");
				flag = client.rename(new File(remotefile + UN_UPLOADED).getName(), new File(remotefile).getName());// 上传完改名
			} else {
				throw new BaseException("localpath[" + localpath + "] not exist!");
			}
		} catch (Exception e) {
			flag = false;
			LogUtil.error("ftp error:", e);
		} finally {
			if (client != null) {
				try {
					client.disconnect();
					client.logout();
				} catch (Exception e) {
		            LogUtil.warn("ftp 断开退出警告。");
				}
			}
			LogUtil.debug("The cost of uploading file: " + (System.currentTimeMillis() - t1)+ "s.");
		}
		return flag;
	}

	/**
	 * 执行ftp下载操作
	 * 
	 * @param address
	 *            目标地址
	 * @param port
	 *            端口
	 * @param user
	 *            用户
	 * @param password
	 *            口令
	 * @param localpath
	 *            本地文件路径（可以是目录或文件名）
	 * @param remotepath
	 *            远端路径（可以是目录或文件名）
	 * @return
	 */
	public static boolean ftpDownload(String address, String port, String user, String password, String localpath,
			String remotepath, String listTableName) {
		boolean flag = false;
		long t1 = System.currentTimeMillis();
		FTPClient client = null;
		try {
			if (StringUtils.isEmpty(address) || StringUtils.isEmpty(user) || StringUtils.isEmpty(password)) {
				throw new BaseException("address or user or password is null");
			}
			client = new FTPClient();
			// 连接ftp
			client.connect(address, StringUtils.isEmpty(port) ? FTP_DEFAULT_PORT : Integer.valueOf(port));
			client.login(user, password);
			if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
				throw new BaseException("ftp login fail:[user=" + user + "] or password or port[port=" + port
						+ "] wrong,please check it!");
			} else {
				LogUtil.debug("ftp login success!");
			}
			OutputStream oStream = null;
			try {
				String remotefile = remotepath;
				File outfile = new File(localpath);
				oStream = new FileOutputStream(outfile);
				flag = client.retrieveFile(remotefile, oStream);
				// 下载完毕后 删除ftp上的文件
				deleteFtpFile(client, remotefile);
			} catch (IOException e) {
				flag = false;
				return flag;
			} finally {
	            if (oStream != null) {
	                try {
	                    oStream.close();
	                } catch (Exception e) {
	                    LogUtil.warn("ftp 断开退出警告。");
	                }
	            }
	        }
		} catch (Exception e) {
			flag = false;
			LogUtil.error("ftp error:", e);
		} finally {
			if (client != null) {
				try {
					client.disconnect();
					client.logout();
				} catch (Exception e) {
                    LogUtil.warn("ftp 断开退出警告。");
				}
			}
			LogUtil.debug("The cost of uploading file: " + (System.currentTimeMillis() - t1) + "s.");
		}
		return flag;
	}

	public static boolean upload(String address, String port, String user, String password, String localPath,
			String remotePath) {
		boolean flag = false;
		long t1 = System.currentTimeMillis();
		FTPClient client = null;
		try {
			if (StringUtils.isEmpty(address) || StringUtils.isEmpty(user) || StringUtils.isEmpty(password)) {
				throw new BaseException("address or user or password is null");
			}
			client = new FTPClient();
			// 连接ftp
			client.connect(address, StringUtils.isEmpty(port) ? FTP_DEFAULT_PORT : Integer.valueOf(port));
			client.login(user, password);
			if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
				throw new BaseException("ftp login fail:[user=" + user + "] or password or port[port=" + port
						+ "] wrong,please check it!");
			} else {
				LogUtil.debug("ftp login success!");
			}

			flag = upload(client, localPath, remotePath);
		} catch (Exception e) {
			flag = false;
			LogUtil.error("ftp error:", e);
		} finally {
			if (client != null) {
				try {
					client.disconnect();
					client.logout();
				} catch (Exception e) {
                    LogUtil.warn("ftp 断开退出警告。");
				}
			}
			LogUtil.debug("The cost of uploading file: " + (System.currentTimeMillis() - t1)+ "s.");
		}
		return flag;
	}

	/**
	 * 上传单个文件
	 * 
	 * @param local
	 * @param remote
	 * @throws IOException
	 */
	private static boolean upload(FTPClient client, String local, String remote) throws Exception {
		LogUtil.debug("FTP from:" + local + " to : " + remote);
		// 设置PassiveMode传输
		client.enterLocalPassiveMode();
		// 设置以二进制流的方式传输
		client.setFileType(FTP.BINARY_FILE_TYPE);
		// 对远程目录的处理
		// 创建服务器远程目录结构，创建失败直接返回
		if (remote.contains(File.separator) && !createDirecroty(client, remote)) {
			LogUtil.warn("Create direcroty of remote(" + remote + ") fail!");
			throw new BaseException("Create direcroty of remote(" + remote 
			    + ") fail!please check FTP user's permission!");
		}
		File f = new File(local);
		return uploadFile(client, remote, f);
	}

	private static boolean uploadFile(FTPClient client, String remoteFile, File localFile) throws IOException {
	    boolean res = true;
		LogUtil.debug("upload file: " + localFile.getName());
		InputStream in = new FileInputStream(localFile);
		try {
		    res = client.storeFile(remoteFile, in);
		} catch (Exception e) {
			// 改为主动模式再次上传文件
			client.enterLocalActiveMode();
			res = client.storeFile(remoteFile, in);
		} finally {
			in.close();
		}
		return res;
	}

	/**
	 * 递归创建远端目录
	 * 
	 * @param client
	 * @param remote
	 * @return
	 * @throws IOException
	 */
	private static boolean createDirecroty(FTPClient client, String remote) throws IOException {
		boolean success = true;
		String directory = remote.substring(0, remote.lastIndexOf(File.separator) + 1);
		// 如果远程目录不存在，则递归创建远程服务器目录
		if (!File.separator.equalsIgnoreCase(directory) && !client.changeWorkingDirectory(directory)) {
			int start = 0;
			int end = 0;
			if (directory.startsWith(File.separator)) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf(File.separator, start);
			while (true) {
				String subDirectory = remote.substring(start, end);
				if (!client.changeWorkingDirectory((start == 1 ? File.separator : "") + subDirectory)) {
					if (client.makeDirectory(subDirectory)) {
						client.changeWorkingDirectory(subDirectory);
					} else {
						success = false;
						return success;
					}
				}
				start = end + 1;
				end = directory.indexOf(File.separator, start);
				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return success;
	}

	/**
	 * 上传文件,可以是目录
	 * 
	 * @param client
	 * @param filename
	 * @param uploadpath
	 * @return
	 * @throws Exception
	 */
	private static boolean uploadAll(FTPClient client, String filename, String uploadpath) throws Exception {
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
					String local = f.getCanonicalPath().replaceAll("\\\\", File.separator);
					String remote = uploadpath.endsWith(File.separator) ? uploadpath + local.substring(local.lastIndexOf(File.separator) + 1)
							: uploadpath + File.separator + local.substring(local.lastIndexOf(File.separator) + 1);
					upload(client, local, remote);
					client.changeWorkingDirectory(File.separator);
				}
			}
		}
		return true;
	}

	/**
	 * 删除ftp文件
	 * 
	 * @param ftp
	 * @param remotePath
	 * @param fileName
	 * @throws Exception
	 */
	public static void deleteFtpFile(FTPClient ftp, String remotePath) throws Exception {
		// 转移到FTP服务器目录
		ftp.changeWorkingDirectory(remotePath);
		boolean result = ftp.deleteFile(remotePath);
		if (result) {
			LogUtil.info("删除成功！");
		}
		ftp.logout();
		if (ftp.isConnected()) {
			ftp.disconnect();
		}
	}

	/**
	 * Description: 下载fileNames之外的dir下的文件列表
	 *
	 * @param dir
	 *            下载文件的路径
	 * @param fileNames
	 *            不下载的文件
	 * @param localPath
	 *            本地目录
	 * @throws Exception
	 */
	public boolean batchDownloadList(String dir, Map<String, String> fileNames, String localPath) throws Exception {
		boolean flag = false;
		if (!(dir.endsWith(File.separator) || dir.endsWith("\\"))) {
			dir += File.separator;
		}
		ftpClient.changeWorkingDirectory(dir);
		isIxistsForder(localPath);
		// 列出该目录下所有文件
		FTPFile[] fs = ftpClient.listFiles();
		OutputStream is = null;
		try {
			// 遍历所有文件，找到指定的文件
			for (FTPFile ff : fs) {
				if (!fileNames.containsKey(ff.getName())) {
					LogUtil.info(System.getProperty("file.separator"));
					// 根据绝对路径初始化文件
					File localFile = new File(localPath + File.separator + ff.getName());
					// 输出流
					is = new FileOutputStream(localFile);
					// 下载文件
					ftpClient.retrieveFile(ff.getName(), is);
					LogUtil.debug("[" + ff.getName() + "]下载成功");
				}
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
			return flag;
		}finally {
		    if (null != is) {
                is.close();
            }
        }
		return flag;
	}

	/**
	 * 批量下载文件
	 *
	 * @param dir
	 *            下载文件的目录路径
	 * @param fileNames
	 *            下载的文件
	 * @param localPath
	 *            本地目录
	 * @throws Exception
	 */
	public boolean batchDownload(String dir, List<String> fileNames, String localPath) throws Exception {
		boolean flag = false;
		if (!(dir.endsWith(File.separator) || dir.endsWith("\\"))) {
			dir += File.separator;
		}
		ftpClient.changeWorkingDirectory(dir);
		isIxistsForder(localPath);
		// 列出该目录下所有文件
		FTPFile[] fs = ftpClient.listFiles();
		OutputStream os = null;
		try {
			// 遍历所有文件，找到指定的文件
			for (FTPFile ff : fs) {
				if (fileNames.contains(ff.getName())) {
					// 根据绝对路径初始化文件
					File localFile = new File(localPath + File.separator + ff.getName());
					os = new FileOutputStream(localFile);
					// 下载文件
					ftpClient.retrieveFile(ff.getName(), os);
					LogUtil.debug("[" + ff.getName() + "]下载成功");
				}
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
			return flag;
		}finally {
		    if (null != os) {
                os.close();
            }
        }
		return flag;
	}

	private void isIxistsForder(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 列出ftp目录下所有文件名
	 * 
	 * @param dir
	 *            目录路径
	 * @return
	 */
	public List<String> listFiles(String dir) {
		List<String> fileNames = new LinkedList<String>();
		try {
			if (!(dir.endsWith(File.separator) || dir.endsWith("\\"))) {
				dir += File.separator;
			}
			ftpClient.changeWorkingDirectory(dir);
			// 列出该目录下所有文件
			FTPFile[] fs = ftpClient.listFiles();
			for (FTPFile ff : fs) {
				if (ff.isFile()) {
					fileNames.add(ff.getName());
				}
			}
		} catch (Exception e) {
			LogUtil.error("列出FTP下所有文件名异常:", e);
		}
		return fileNames;
	}

	public static boolean createDirectory(String address, String port, String user, String password,
			String remotePath) {
		boolean flag = false;
		FTPClient client = null;
		try {
			if (StringUtils.isEmpty(address) || StringUtils.isEmpty(user) || StringUtils.isEmpty(password)) {
				throw new BaseException("address or user or password is null");
			}
			client = new FTPClient();
			// 连接ftp
			client.connect(address, StringUtils.isEmpty(port) ? FTP_DEFAULT_PORT : Integer.valueOf(port));
			client.login(user, password);
			if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
				throw new BaseException("ftp login fail:[user=" + user + "] or password or port[port=" + port
						+ "] wrong,please check it!");
			} else {
				LogUtil.debug("ftp login success!");
			}
			flag = createDirecroty(client, remotePath);
		} catch (Exception e) {
			flag = false;
			LogUtil.error("ftp error:", e);
		} finally {
			if (client != null) {
				try {
					client.disconnect();
					client.logout();
				} catch (Exception e) {
                    LogUtil.warn("ftp 断开退出警告。");
				}
			}
		}
		return flag;
	}

	// 将ftppath下的所有文件都下载到locapath
	public static void dowloadFtpDir(String localPath, String ftpPath,
	        String ftpHost,String ftpPort,String ftpUsername,String ftpPwd) {
		FtpClient ftpClient = new FtpClient();
		boolean connect = false;
		try {
			connect = ftpClient.connect(ftpPath, ftpHost, Integer.parseInt(ftpPort), ftpUsername, ftpPwd);
		} catch (NumberFormatException e) {
			LogUtil.error(e);
		} catch (Exception e) {
			LogUtil.error(e);
		}
		if (connect) {
			LogUtil.debug("连接到ftp服务器成功，正在下载文件.ftpHost:" + ftpHost);
			try {
				ftpClient.batchDownloadAll(ftpPath, localPath);
				ftpClient.closeFtp();
			} catch (Exception e) {
				LogUtil.error(e);
			}
		} else {
			LogUtil.error("连接到ftp服务器失败!!ftpHost:" + ftpHost);
		}
	}

}
