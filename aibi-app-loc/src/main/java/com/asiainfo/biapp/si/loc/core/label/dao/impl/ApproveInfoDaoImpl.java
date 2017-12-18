/*
 * @(#)ApproveInfoDaoImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.dao.impl;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.core.label.dao.IApproveInfoDao;
import com.asiainfo.biapp.si.loc.core.label.entity.ApproveInfo;

/**
 * Title : ApproveInfoDaoImpl
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
@Repository
public class ApproveInfoDaoImpl extends BaseDaoImpl<ApproveInfo, String> implements IApproveInfoDao{

    @Override
    public ApproveInfo selectApproveInfo(String resourceId) {
        return super.findOneByHql("from ApproveInfo a where a.resourceId = ?0 ",resourceId);
    } 
}
