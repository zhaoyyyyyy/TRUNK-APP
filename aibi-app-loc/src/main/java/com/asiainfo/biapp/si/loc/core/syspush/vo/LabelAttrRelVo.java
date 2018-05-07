/*
 * @(#)LabelAttrRelVo.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.vo;

import java.util.Date;

import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrRel;

/**
 * Title : LabelAttrRelVo
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
 * <pre>1    2018年1月18日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月18日
 */
public class LabelAttrRelVo extends LabelAttrRel{

    private static final long serialVersionUID = 1L;
    
    private String orderBy;
    private String modifyTimeStart;
    private String modifyTimeEnd;
    
    public String getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    public String getModifyTimeStart() {
        return modifyTimeStart;
    }
    public void setModifyTimeStart(String modifyTimeStart) {
        this.modifyTimeStart = modifyTimeStart;
    }
    public String getModifyTimeEnd() {
        return modifyTimeEnd;
    }
    public void setModifyTimeEnd(String modifyTimeEnd) {
        this.modifyTimeEnd = modifyTimeEnd;
    }


    
}
