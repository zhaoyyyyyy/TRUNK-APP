/*
 * @(#)IDimTableInfoDao.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.dimtable.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;
import com.asiainfo.biapp.si.loc.core.dimtable.vo.DimTableInfoVo;

/**
 * Title : IDimTableInfoDao
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
 * <pre>1    2017年11月27日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2017年11月27日
 */
public interface IDimTableInfoDao extends BaseDao<DimTableInfo, String>{
    
    /**
     * 根据条件分页查询
     *
     * @param page
     * @param DimTableInfo
     * @return
     */
    public Page<DimTableInfo> selectDimTableInfoPageList(Page<DimTableInfo> page, DimTableInfoVo dimTableInfoVo);

    /**
     * 根据条件查询列表
     *
     * @param DimTableInfo
     * @return
     */
    public List<DimTableInfo> selectDimTableInfoList(DimTableInfoVo dimTableInfoVo);

}
