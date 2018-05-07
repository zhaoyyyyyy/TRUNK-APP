/*
 * @(#)ICustomerPublishCommService.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelAttrRelVo;

/**
 * Title : ICustomerPublishCommService
 * <p/>
 * Description : <pre>客户群推送公用接口类.</pre>
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.7 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date         Modified By    Why & What is modified</pre>
 * <pre>1    2018年3月5日     hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年3月5日
 */

public interface ICustomerPublishCommService {

    /**
     * Description: 根据标签信息，获取客户群标签与属性对应关系。

     * @param customInfo LabelInfo 客户群对象
     * @param attrSettingType int 属性类型
     * 
     * @return attrRelList List<LabelAttrRel>  客户群关联的属性列List
     */
    public List<LabelAttrRel> getLabelAttrRelsByCustom(LabelInfo customInfo, LabelAttrRelVo labelAttrRelVo);
    
    
    /**
     * Description: 根据标签信息，以及客户群标签与属性对应关系表，拼出创建清单文件sql。形如：
     *   select maintable.product_no, t1.A1,t2,A2,p.B1 from (
     *      select product_no from cross_111_20180301 f where f.custome_id=1111 ) maintable 
     *   left join  dw_A_111_20180301 t on maintable.product_no=t.product_no 
     *   left join dw_A_111_201803 p on p.product_no=t.product_no
     * @param customInfo LabelInfo 客户群对象
     * @param attrRelList List<LabelAttrRel>  客户群关联的属性列List
     * 
     * @return sql String 拼接好的创建清单文件sql
     */
	public String getCustomListSql(LabelInfo customInfo, List<LabelAttrRel> attrRelList, boolean isPush);

	
	/**
	 * Description:根据标签id查询维表值
	 * @param LabelId	标签id
	 * @return
	 */
    public DimTableInfo getDimTableInfoByLabelId(String LabelId);
}
