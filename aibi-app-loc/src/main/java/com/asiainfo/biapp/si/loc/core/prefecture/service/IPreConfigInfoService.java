/*
 * @(#)IPreConfigInfoService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.auth.model.User;
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
     * Description: 分页查询专区
     *
     * @param page
     * @param preConfigInfoVo
     * @return
     * @throws BaseException
     */
    public Page<PreConfigInfo> selectPreConfigInfoPageList(Page<PreConfigInfo> page, PreConfigInfoVo preConfigInfoVo,User user)
            throws BaseException;

    /**
     * Description: 查询专区列表
     *
     * @param preConfigInfoVo
     * @return
     * @throws BaseException
     */
    public List<PreConfigInfo> selectPreConfigInfoList(PreConfigInfoVo preConfigInfoVo,User user) throws BaseException;

    /**
     * Description: 根据名称查询专区
     *
     * @param sourceName
     * @return
     * @throws BaseException
     */
    public PreConfigInfo selectOneBySourceName(String sourceName) throws BaseException;

    /**
     * Description: 通过ID得到专区
     *
     * @param configId
     * @return
     * @throws BaseException
     */
    public PreConfigInfo selectPreConfigInfoById(String configId) throws BaseException;

    /**
     * Description: 新增一个专区
     *
     * @param preConfigInfo
     * @throws BaseException
     */
    public void addPreConfigInfo(PreConfigInfo preConfigInfo) throws BaseException;

    /**
     * Description: 修改专区
     *
     * @param preConfigInfo
     * @throws BaseException
     */
    public void modifyPreConfigInfo(PreConfigInfo preConfigInfo) throws BaseException;

    /**
     * Description: 删除专区
     *
     * @param configId
     * @throws BaseException
     */
    public void deletePreConfigInfo(String configId) throws BaseException;

}
