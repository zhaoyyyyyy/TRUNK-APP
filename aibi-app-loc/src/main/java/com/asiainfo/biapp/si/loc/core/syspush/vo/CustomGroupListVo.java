/*
 * @(#)CustomGroupList.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.vo;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

/**
 * Title : CustomGroupList
 * <p/>
 * Description : 客户群清单
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月29日    hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年1月29日
 */

public class CustomGroupListVo extends BaseEntity {

    private static final long serialVersionUID = 1L;
    
    
    private String productNo;


    
    public CustomGroupListVo() {
        super();
    }
    /**
     * @param productNo
     */
    public CustomGroupListVo(String productNo) {
        super();
        this.productNo = productNo;
    }

    
    public String getProductNo() {
        return productNo;
    }
    
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }


    @Override
    public String toString() {
        return "CustomGroupListVo [productNo=" + productNo + "]";
    }

    
}
