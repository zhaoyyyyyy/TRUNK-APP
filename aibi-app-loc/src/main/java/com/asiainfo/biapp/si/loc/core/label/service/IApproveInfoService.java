/*
 * @(#)ApproveInfoService.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.label.entity.ApproveInfo;

/**
 * Title : ApproveInfoService
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年12月15日    lilin7        Created</pre>
 * <p/>
 *
 * @author  lilin7
 * @version 1.0.0.2017年12月15日
 */
public interface IApproveInfoService extends BaseService<ApproveInfo, String>{
    
    /**
     * 
     * Description: 通过资源Id得到审批信息
     *
     * @param resourceId
     * @return
     * @throws BaseException 
     */
    public ApproveInfo selectApproveInfo(String resourceId) throws BaseException;

    public void addApproveInfo(ApproveInfo approveInfo);
}
