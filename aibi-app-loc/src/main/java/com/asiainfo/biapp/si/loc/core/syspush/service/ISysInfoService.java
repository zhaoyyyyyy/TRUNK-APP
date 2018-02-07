/*
 * @(#)ISysInfoService.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.syspush.entity.SysInfo;
import com.asiainfo.biapp.si.loc.core.syspush.vo.SysInfoVo;

/**
 * Title : ISysInfoService
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
 * <pre>1    2018年1月17日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月17日
 */
public interface ISysInfoService extends BaseService<SysInfo, String>{
    
    /**
     * 根据条件分页查询 
     *
     * @param page
     * @param sysInfo
     * @return
     */
    public Page<SysInfo> selectSysInfoPageList(Page<SysInfo> page, SysInfoVo sysInfoVo) throws BaseException;
    
    /**
     * 
     * 根据条件查询列表
     *
     * @param sysInfoVo
     * @return
     */
    public List<SysInfo> selectSysInfoList(SysInfoVo sysInfoVo) throws BaseException;
    
    /**
     * 通过ID得到一个实体 Description:
     *
     * @param sysId
     * @return
     * @throws BaseException
     */
    public SysInfo selectSysInfoById(String sysId) throws BaseException;
    
    /**
     * Description: 根据名称查询平台
     *
     * @param sysName
     * @return
     * @throws BaseException
     */
    public SysInfo selectSysInfoBySysName(String sysName) throws BaseException;
    
    /**
     * 新增或修改一个实体 Description:
     *
     * @param sysInfo
     * @throws BaseException
     */
    public void addSysInfo(SysInfo sysInfo) throws BaseException;

    /**
     * 修改一个实体 Description:
     *
     * @param sysInfo
     * @throws BaseException
     */
    public void modifySysInfo(SysInfo sysInfo) throws BaseException;

    /**
     * 通过ID删除一个实体 Description:
     *
     * @param sysId
     * @throws BaseException
     */
    public void deleteSysInfoById(String sysId) throws BaseException;

}
