/*
 * @(#)IDimTableDataService.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.dimtable.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableData;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableDataId;

/**
 * Title : IDimTableDataService
 * <p/>
 * Description :  维表数据表(前台库存储)服务类
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 8.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月3日    hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年1月3日
 */
public interface IDimTableDataService extends BaseService<DimTableData, DimTableDataId>{

    /**
     * 根据条件分页查询 
     *
     * @param page
     * @param DimTableDataVo
     * @return
     */
    public Page<DimTableData> selectDimTableDataPageList(Page<DimTableData> page, DimTableData dimTableData) throws BaseException;
    
    /**
     * 
     * 根据条件查询列表
     *
     * @param DimTableDataVo
     * @return
     */
    public List<DimTableData> selectDimTableDataList(DimTableData dimTableData) throws BaseException;
    
    /**
     * 通过ID得到一个实体 Description:
     *
     * @param dimId
     * @return
     * @throws BaseException
     */
    public DimTableData selectDimTableDataById(DimTableDataId dimTableDataId) throws BaseException;


}