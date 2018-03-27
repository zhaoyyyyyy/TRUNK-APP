/*
 * @(#)LabelPushReqVo.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.vo;

import com.asiainfo.biapp.si.loc.core.label.vo.LabelInfoVo;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushReq;

import io.swagger.annotations.ApiParam;

/**
 * Title : LabelPushReqVo
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
public class LabelPushReqVo extends LabelPushReq{

    private static final long serialVersionUID = 1L;
    
    /*
     * 逗号分隔的选中的对端系统ID
     */
    public static final String REQID_PREFIX = "COC_";
    
    /*
     * 标签信息
     */
    @ApiParam(value="标签信息")
    private LabelInfoVo customInfoVo;
    
    private String selSysIds;

    
    
    public LabelInfoVo getCustomInfoVo() {
        return customInfoVo;
    }
    
    public void setCustomInfoVo(LabelInfoVo customInfoVo) {
        this.customInfoVo = customInfoVo;
    }
    
    public String getSelSysIds() {
        return selSysIds;
    }
    
    public void setSelSysIds(String selSysIds) {
        this.selSysIds = selSysIds;
    }

}
