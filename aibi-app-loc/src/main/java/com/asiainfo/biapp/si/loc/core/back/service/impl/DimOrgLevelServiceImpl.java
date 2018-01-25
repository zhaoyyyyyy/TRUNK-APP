/*
 * @(#)DimOrgLevelServiceImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.back.service.impl;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.core.back.entity.DimOrgLevel;
import com.asiainfo.biapp.si.loc.core.back.entity.DimOrgLevelId;
import com.asiainfo.biapp.si.loc.core.back.service.IDimOrgLevelService;

/**
 * Title : DimOrgLevelServiceImpl
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2018
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
 * 1    2018年1月24日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2018年1月24日
 */
public class DimOrgLevelServiceImpl extends BaseServiceImpl<DimOrgLevel, String>implements IDimOrgLevelService {

    @Override
    protected BaseDao<DimOrgLevel, String> getBaseDao() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addDimOrgLevel(DimOrgLevel dimOrgLevel) throws BaseException {
        super.saveOrUpdate(dimOrgLevel);
    }

    public void modifyDimOrgLevel(DimOrgLevel dimOrgLevel) throws BaseException {
        super.saveOrUpdate(dimOrgLevel);
    }

    public void deleteDimOrgLevel(DimOrgLevelId dimOrgLevelId) throws BaseException {
        super.delete(dimOrgLevelId);
    }

}
