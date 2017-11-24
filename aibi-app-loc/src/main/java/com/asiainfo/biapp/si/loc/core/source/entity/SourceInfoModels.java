/*
 * @(#)SourceInfoModels.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.entity;

import java.util.List;

/**
 * Title : SourceInfoModels
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月16日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月16日
 */
public class SourceInfoModels {

    private List<SourceInfo> sourceInfos;

    public List<SourceInfo> getSourceInfos() {
        return sourceInfos;
    }

    public void setSourceInfos(List<SourceInfo> sourceInfos) {
        this.sourceInfos = sourceInfos;
    }

}
