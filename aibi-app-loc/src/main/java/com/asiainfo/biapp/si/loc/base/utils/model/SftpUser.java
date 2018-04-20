/*
 * @(#)SftpUser.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.base.utils.model;

import java.io.Serializable;

/**
 * 
 * Title : SftpUser
 * <p/>
 * Description : Sftp工具类的辅助类
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

public class SftpUser implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String address;
    private String port;
    private String userName;
    private String password;
    
    /**
     * @param address
     * @param port
     * @param user
     * @param password
     */
    public SftpUser() {}
    public SftpUser(String address, String port, String userName, String password) {
        super();
        this.address = address;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPort() {
        return port;
    }
    
    public void setPort(String port) {
        this.port = port;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    } 
    
}