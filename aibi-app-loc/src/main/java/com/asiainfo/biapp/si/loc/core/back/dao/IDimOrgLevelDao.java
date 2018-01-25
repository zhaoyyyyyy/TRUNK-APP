/*
 * @(#)IDimOrgLevelDao.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.back.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.back.entity.DimOrgLevel;
import com.asiainfo.biapp.si.loc.core.back.vo.DimOrgLevelVo;

/**
 * Title : IDimOrgLevelDao
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
 * <pre>1    2018年1月25日    zhangnan7        Created</pre>
 * <p/>
 *
 * @author  zhangnan7
 * @version 1.0.0.2018年1月25日
 */
public interface IDimOrgLevelDao extends BaseDao<DimOrgLevel, String> {
    
    public Page<DimOrgLevel> selectDimOrgLevelPageList(Page<DimOrgLevel> page, DimOrgLevelVo dimOrgLevelVo);

    public List<DimOrgLevel> selectDimOrgLevelList(DimOrgLevelVo dimOrgLevelVo);

}
