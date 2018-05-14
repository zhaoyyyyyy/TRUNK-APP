/*
 * @(#)ILabelExeInfoService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelExeInfo;

/**
 * 
 * Title : ILabelExeInfoService
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
 * <pre>1    2018年5月14日    shaosq        Created</pre>
 * <p/>
 *
 * @author  shaosq
 * @version 1.0.0.2018年5月14日
 */
public interface ILabelExeInfoService extends BaseService<LabelExeInfo, String>{

    /**
     * Description: 根据客户群ID和日期查询客户群生成失败信息
     *
     * @param labelId
     * @param dataDate
     * @return
     * @throws BaseException
     */
    public LabelExeInfo selectLabelExeInfoByLabelId(String labelId,String dataDate) throws BaseException;

}
