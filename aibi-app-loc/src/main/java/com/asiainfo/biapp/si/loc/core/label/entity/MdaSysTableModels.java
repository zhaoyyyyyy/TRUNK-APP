/*
 * @(#)MdaSyaTableModels.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.entity;

import java.util.List;

/**
 * Title : MdaSyaTableModels
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月20日    lilin7        Created
 * </pre>
 * <p/>
 *
 * @author lilin7
 * @version 1.0.0.2017年11月20日
 */
public class MdaSysTableModels {

    public List<MdaSysTable> mdaSysTables;

    public List<MdaSysTable> getMdaSysTables() {
        return mdaSysTables;
    }

    public void setMdaSysTables(List<MdaSysTable> mdaSysTables) {
        this.mdaSysTables = mdaSysTables;
    }

}
