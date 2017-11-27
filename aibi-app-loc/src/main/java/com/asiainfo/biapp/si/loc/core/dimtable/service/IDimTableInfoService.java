/*
 * @(#)IDimTableInfoService.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.dimtable.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;
import com.asiainfo.biapp.si.loc.core.dimtable.vo.DimTableInfoVo;
import com.asiainfo.biapp.si.loc.core.label.entity.CategoryInfo;

/**
 * Title : IDimTableInfoService
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
public interface IDimTableInfoService extends BaseService<DimTableInfo, String>{

    /**
     * 根据条件分页查询 
     *
     * @param page
     * @param dimTableInfoVo
     * @return
     */
    public Page<DimTableInfo> selectDimTableInfoPageList(Page<DimTableInfo> page, DimTableInfoVo dimTableInfoVo) throws BaseException;
    
    /**
     * 
     * 根据条件查询列表
     *
     * @param dimTableInfoVo
     * @return
     */
    public List<DimTableInfo> selectDimTableInfoList(DimTableInfoVo dimTableInfoVo) throws BaseException;
    
    /**
     * 通过ID得到一个实体 Description:
     *
     * @param dimId
     * @return
     * @throws BaseException
     */
    public DimTableInfo selectDimTableInfoById(String dimId) throws BaseException;

    /**
     * 新增或修改一个实体 Description:
     *
     * @param dimTableInfo
     * @throws BaseException
     */
    public void addDimTableInfo(DimTableInfo dimTableInfo) throws BaseException;

    /**
     * 修改一个实体 Description:
     *
     * @param dimTableInfo
     * @throws BaseException
     */
    public void modifyDimTableInfo(DimTableInfo dimTableInfo) throws BaseException;

    /**
     * 通过ID删除一个实体 Description:
     *
     * @param dimId
     * @throws BaseException
     */
    public void deleteDimTableInfoById(String dimId) throws BaseException;
}
