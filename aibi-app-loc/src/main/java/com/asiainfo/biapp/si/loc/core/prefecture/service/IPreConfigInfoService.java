/*
 * @(#)IPreConfigInfoService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.PreConfigInfo;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.PreConfigInfoVo;

/**
 * Title : IPreConfigInfoService
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月7日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月7日
 */
public interface IPreConfigInfoService extends BaseService<PreConfigInfo, String> {

    /**
     * 根据条件分页查询专区
     * 
     * @param page
     * @param preConfigInfo
     * @return
     */
    public Page<PreConfigInfo> findPreConfigInfoPageList(Page<PreConfigInfo> page, PreConfigInfoVo preConfigInfoVo)
            throws BaseException;

    /**
     * 根据条件查询专区列表
     * 
     * @param page
     * @param dataSourceInfo
     * @return
     */
    public List<PreConfigInfo> findPreConfigInfoList(PreConfigInfoVo preConfigInfoVo) throws BaseException;

    /**
     * 根据数据源名称查询一个专区
     * 
     * @param sourceName
     * @return
     */
    public PreConfigInfo findOneBySourceName(String sourceName);

    /**
     * 通过主键得到实体
     * 
     * @param id
     * @return
     */
    public PreConfigInfo getById(String configId) throws BaseException;

    /**
     * 新增专区
     *
     * @param preConfigInfo
     * @return
     * @throws BaseException
     */
    public void saveT(PreConfigInfo preConfigInfo) throws BaseException;

    /**
     * 修改专区
     *
     * @param preConfigInfo
     * @return
     * @throws BaseException
     */
    public void updateT(PreConfigInfo preConfigInfo);

    /**
     * 删除专区
     *
     * @param preConfigInfo
     * @throws BaseException
     */
    public void deleteById(String configId) throws BaseException;

}
