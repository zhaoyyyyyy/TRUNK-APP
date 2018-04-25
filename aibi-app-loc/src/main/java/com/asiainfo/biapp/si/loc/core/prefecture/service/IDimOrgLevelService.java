/*
 * @(#)IDimOrgLevelService.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.DimOrgLevel;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.DimOrgLevelId;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.DimOrgLevelVo;

/**
 * Title : IDimOrgLevelService
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
public interface IDimOrgLevelService extends BaseService<DimOrgLevel, String> {

    /**
     * Description: 分页查询数据权限组织层级
     *
     * @param page
     * @param dimOrgLevelVo
     * @return
     * @throws BaseException
     */
    public Page<DimOrgLevel> selectDimOrgLevelPageList(Page<DimOrgLevel> page, DimOrgLevelVo dimOrgLevelVo)
            throws BaseException;

    /**
     * Description: 不分页查询数据权限组织层级
     *
     * @param dimOrgLevelVo
     * @return
     * @throws BaseException
     */
    public List<DimOrgLevel> selectDimOrgLevelList(DimOrgLevelVo dimOrgLevelVo) throws BaseException;

    /**
     * Description: 根据ID查询数据权限组织层级
     *
     * @param dimOrgLevelId
     * @return
     * @throws BaseException
     */
    public DimOrgLevel selectDimOrgLevelById(DimOrgLevelId dimOrgLevelId) throws BaseException;

    /**
     * Description: 增加数据权限组织层级
     *
     * @param dimOrgLevel
     * @return
     * @throws BaseException
     */
    public void addDimOrgLevel(DimOrgLevel dimOrgLevel) throws BaseException;

    /**
     * Description: 修改数据权限组织层级
     *
     * @param dimOrgLevel
     * @return
     * @throws BaseException
     */
    public void modifyDimOrgLevel(DimOrgLevel DimOrgLevel) throws BaseException;

    /**
     * Description: 根据ID删除数据权限组织层级
     *
     * @param dimOrgLevelId
     * @return
     * @throws BaseException
     */
    public void deleteDimOrgLevel(DimOrgLevelId dimOrgLevelId) throws BaseException;

}
