/*
 * @(#)FileUtil.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.base.utils;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.ByteOrderMarkDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.asiainfo.biapp.si.loc.base.common.CommonConstants;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;


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
    
    private static Map<String, Map<String, String>> props = new HashMap<>();
    
    private static Map<String,String> fileNames = new LinkedHashMap<>();
    
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
        try {
            mpmPath = getProperties("LOC_CONFIG_SYS_TEMP_PATH");
        } catch (BaseException e) {
            LogUtil.error(e);
        }
        if (!mpmPath.endsWith(File.separator)) {
            mpmPath += File.separator;
        }
        mpmPath += CommonConstants.CI_STORE_SUB_PATH;
        File pathFile = new File(mpmPath);
        if (!pathFile.exists()) {
            if (!pathFile.mkdirs()) {
                return null;
            }
        }
        return mpmPath;
    }
    
    public static String getProperties(String key) throws BaseException {
        String value = null;
        for (String fileKey : fileNames.keySet()) {
            Map<String, String> prop = props.get(fileKey);
            if (prop != null && (value = prop.get(key)) != null) {
                break;
            }
        }
        return StringUtil.isEmpty(value)? "" :value.trim();
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

}
