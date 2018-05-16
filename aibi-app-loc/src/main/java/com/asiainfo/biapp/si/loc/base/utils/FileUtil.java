/*
 * @(#)FileUtil.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.base.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.ByteOrderMarkDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.UnicodeDetector;


/**
 * Title : FileUtil
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月11日    zhangnan7        Created</pre>
 * <p/>
 *
 * @author  zhangnan7
 * @version 1.0.0.2018年1月11日
 */
public class FileUtil {
    
    public static String uploadTargetUserFile(InputStream in, String fileName) throws Exception{
        String uploadName = "";
        if (in != null && fileName != null && fileName.length() > 0) {
            uploadName = getMpmStoreFilePath() + File.separator + generateNewFilename(fileName);
            BufferedInputStream bIn = null;
            BufferedOutputStream bOut = null;
            try {
                bIn = new BufferedInputStream(in);
                bOut = new BufferedOutputStream(new FileOutputStream(uploadName));
                int bytesRead;
                byte[] buffer = new byte[8192];
                while ((bytesRead =bIn.read(buffer)) != -1) {
                    bOut.write(buffer, 0, bytesRead);
                }
                bOut.flush();
            } catch (Exception e) {
                throw e;
            } finally {
                if(bOut != null)
                   bOut.close();
                if(bIn != null)
                   bIn.close();
            }
        }
        return uploadName;
    }
    
    public static String getMpmStoreFilePath() {
        String mpmPath = "";
        mpmPath = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey("LOC_CONFIG_SYS_TEMP_PATH");
        if (!mpmPath.endsWith(File.separator)) {
            mpmPath += File.separator;
        }
        File pathFile = new File(mpmPath);
        if (!pathFile.exists()) {
            if (!pathFile.mkdirs()) {
                return null;
            }
        }
        return mpmPath;
    }
    
    private static String generateNewFilename(String fileName) {
        //后缀的位置
        int nFileExtPos = fileName.lastIndexOf(".");
        //后缀名
        String fileExt = fileName.substring(nFileExtPos + 1);

        //不带后缀的文件名
        String pureFileName = nFileExtPos < 0 ? fileName : fileName.substring(0, nFileExtPos);
        //去除空格
        pureFileName = pureFileName.replaceAll(" ", "");
        //保存文件名，为避免重复，增加用户名和日期
        String saveFileName = (pureFileName.length() > 15 ? pureFileName.substring(0, 15) : pureFileName) +
        //              "_" + dFormatS.format(new Date());
                "_" + System.currentTimeMillis();
        if (saveFileName.length() > 40) {
            saveFileName = saveFileName.substring(saveFileName.length() - 40);
        }
        //加上扩展名
        saveFileName += "." + fileExt;
        return saveFileName;
    }
    
    public static Charset getInputStreamCharset(InputStream in) {
        long start = System.currentTimeMillis();
        //      String fileCharacterEnding = "UTF-8";
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        // This one is quick if we deal with unicode codepages: 
        detector.add(new ByteOrderMarkDetector());
        // The first instance delegated to tries to detect the meta charset attribut in html pages.
        //detector.add(new ParsingDetector(true)); // be verbose about parsing.
        // This one does the tricks of exclusion and frequency detection, if first implementation is 
        // unsuccessful:
        detector.add(JChardetFacade.getInstance()); // Another singleton.
        detector.add(ASCIIDetector.getInstance()); // Fallback, see javadoc.
        //UnicodeDetector用于Unicode家族编码的测定  
        detector.add(UnicodeDetector.getInstance());
        Charset charset = Charset.defaultCharset();
        try {
            charset = detector.detectCodepage(new BufferedInputStream(in), 1000);
        } catch (Exception e) {
            LogUtil.error("getFileCharset error", e);
        }
        LogUtil.info("getFileCharset cost:" + (System.currentTimeMillis() - start));
        return charset;
    }
    

    /**
     * 创建文件目录
     * @param fileName 文件名（全路径）或目录
     * @throws IOException 
     */
    public static File createLocDir(String fileName) throws Exception {
        File f = new File(fileName);
        File dir = null;
        if (f != null && !f.exists()) {
            if (f.isDirectory()) {//判断是否是文件
                dir = f;
            } else {
                dir = f.getParentFile();
            }
            if (dir != null && !dir.exists()) {
                boolean result = dir.mkdirs();
                if (!result) {
                    LogUtil.error("can not mkdir [" + dir + "] ,please check OS User'S privilege!");
                    throw new Exception("can not mkdir [" + dir + "] ,please check OS User'S privilege!");
                }
            }
        } else {
            dir = f.isFile() ? f.getParentFile() : f;
        }
        return dir;
    }

    /**
     * 创建文件或目录
     * @param fileName 文件名(绝对路径)或目录
     * @param content
     * @throws Exception 
     */
    public static void createFile(String fileName, String content) throws Exception {
        if (StringUtil.isEmpty(fileName)) {
            throw new Exception("file name is null!");
        }
        File f = new File(fileName);
        if (f.exists()) {
            throw new Exception("The file " + fileName + " has exists!");
        }
        createLocDir(fileName);
        if (f.getName().contains(".")) {
            if (StringUtil.isEmpty(content)) {
                f.createNewFile();//仅创建一个空文件
            } else {//向文件中写入内容
                FileWriter fileWriter = new FileWriter(f);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.append(content.replace("\\n", System.getProperty("line.separator")));
                bw.flush();
                bw.close();
                fileWriter.close();
            }
        }
    }

    /**
     * 将输入的源文件目录打成zip包:支持目录和单个文件压缩，不支持多级目录
     * @param srcDir 源文件或目录
     * @param destFile 目标zip文件名称，全路径
     * @param password 压缩文件密码
     * @param encode 文件名编码
     * @throws Exception
     */
    public static void zipFileByPassword(String srcFile, String destFile, String password, String encode)
            throws Exception {
        File file = null;
        File destZipFile = null;
        File[] files = null;
        if (StringUtil.isEmpty(srcFile) || StringUtil.isEmpty(destFile)) {
            throw new Exception("source file's directory or dest zip file is null!");
        }
        if (StringUtil.isEmpty(password)) {
            throw new Exception("password is null!");
        }
        file = new File(srcFile);
        destZipFile = new File(destFile);
        if (!file.exists()) {
            throw new Exception("source file[" + srcFile + "] not exists!");
        }
        //判断是否是目录
        if (!file.isDirectory()) {
            files = new File[] { file };
        } else {
            files = getFileList(srcFile);
        }
        if (destZipFile.isDirectory()) {
            throw new Exception("dest file[" + destFile + "] must be file,but directory!");
        }
        //创建目录
        try {
            createLocDir(destFile);
            byte[] zipByte = ZipUtil.getEncryptZipByte(files, password, encode);
            writeByteFile(zipByte, new File(destFile));
        } catch (Exception e) {
            if (destZipFile != null && destZipFile.exists()) {
                destZipFile.delete();
            }
            LogUtil.error("zipFileByPassword error:", e);
        }
    }

    /**
     * 将输入的源文件目录打成zip包：支持单个文件和目录压缩，不支持多级目录
     * @param srcDir 源文件或目录
     * @param destFile 目标zip文件名称，全路径
     * @throws Exception
     */
    public static void zipFileUnPassword(String srcFile, String destFile, String encode) throws Exception {
        File file = null;
        File destZipFile = null;
        if (StringUtil.isEmpty(srcFile) || StringUtil.isEmpty(destFile)) {
            LogUtil.error("source file or dest zip file is null!");
            throw new RuntimeException("source file or dest zip file is null!");
        }
        file = new File(srcFile);
        destZipFile = new File(destFile);
        if (!file.exists()) {
            LogUtil.error("source file[" + srcFile + "] not exists!");
            throw new RuntimeException("source file[" + srcFile + "] not exists!");
        }
        if (destZipFile.isDirectory()) {
            LogUtil.error("dest file[" + destFile + "] must be file,but directory!");
            throw new RuntimeException("dest file[" + destFile + "] must be file,but directory!");
        }
        long t1 = System.currentTimeMillis();
        //创建目录
        createLocDir(destFile);
        OutputStream os = null;
        ZipOutputStream zipOut = null;
        ZipEntry entry = null;
        int bufferSize = 2048;
        InputStream in = null;
        FileInputStream fin = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(destZipFile), bufferSize);
            zipOut = new ZipOutputStream(os);
            zipOut.setEncoding(encode);
            File[] files = null;
            //获取待压缩的文件
            if (file.isFile()) {//文件
                files = new File[] { file };
            } else {//目录
                files = getFileList(srcFile);
            }
            //遍历文件集合压缩
            for (File f : files) {
                entry = new ZipEntry(f.getName());
                zipOut.putNextEntry(entry);
                //byte data[] = readFileByte(f);一次读取性能较慢
                fin = new FileInputStream(f);
                in = new BufferedInputStream(fin, bufferSize);
                byte data[] = new byte[bufferSize];
                int count;
                while ((count = in.read(data, 0, bufferSize)) != -1) {
                    zipOut.write(data, 0, count);
                }
                zipOut.closeEntry();
                in.close();
            }
            zipOut.flush();
        } catch (Exception e) {
            if (destZipFile != null && destZipFile.exists()) {
                destZipFile.delete();
            }
            LogUtil.error("zipFileUnPassword error:", e);
        } finally {
            LogUtil.debug("The cost of compressing files :  " + (System.currentTimeMillis() - t1) + "ms");
            fin.close();
            zipOut.close();
            os.close();
        }
    }

    /**
     * 将文件压缩成zip包[如果password为空则不进行加密压缩，否则进行加密压缩]
     * @param srcFile
     * @param destFile
     * @param password
     * @param encode
     * @return
     * @throws Exception
     */
    public static boolean zipFile(String srcFile, String destFile, String password, String encode) {
        boolean flag = true;
        try {
            if (StringUtil.isEmpty(password)) {
                zipFileUnPassword(srcFile, destFile, encode);
            } else {
                zipFileByPassword(srcFile, destFile, password, encode);
            }
        } catch (Exception e) {
            flag = false;
            LogUtil.error("zipFile[srcFile=" + srcFile + ";destFile=" + destFile + ";password=" + password + "] error: ", e);
        }
        return flag;
    }


    /**
     * 获取目录的文件清单[仅文件]
     * @param fileDir
     * @return
     */
    public static File[] getFileList(String fileDir) throws Exception {
        File dir = new File(fileDir);
        List<File> files = new ArrayList<File>();
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                if (f.isFile() && !f.isHidden()) {
                    files.add(f);
                }
            }
        } else {
            LogUtil.warn(fileDir + " is not dir!");
        }
        return files.toArray(new File[] {});
    }


    /**
     * 将字节数组写入文件
     * @param bytes
     * @param file
     * @return
     */
    public static boolean writeByteFile(byte[] bytes, File file) throws Exception {
        FileOutputStream fos = null;
        boolean flag = true;
        try {
            fos = new FileOutputStream(file);
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            flag = false;
            LogUtil.error("writeByteFile error:", e);
        } catch (IOException e) {
            flag = false;
            LogUtil.error("writeByteFile error:", e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    LogUtil.error(e);
                }
            }
        }
        return flag;
    }
    
    
}
