/*
 * @(#)ILabelExtInfoService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelExtInfo;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelExtInfoVo;

/**
 * Title : ILabelExtInfoService
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月21日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月21日
 */
public interface ILabelExtInfoService extends BaseService<LabelExtInfo, String> {

    /**
     * Description: 分页查询标签扩展信息
     *
     * @param page
     * @param labelExtInfoVo
     * @return
     * @throws BaseException
     */
    public Page<LabelExtInfo> selectLabelExtInfoPageList(Page<LabelExtInfo> page, LabelExtInfoVo labelExtInfoVo)
            throws BaseException;

    /**
     * Description: 查询标签扩展信息列表
     *
     * @param labelExtInfoVo
     * @return
     * @throws BaseException
     */
    public List<LabelExtInfo> selectLabelExtInfoList(LabelExtInfoVo labelExtInfoVo) throws BaseException;

    /**
     * Description: 通过ID拿到标签扩展信息
     *
     * @param labelId
     * @return
     * @throws BaseException
     */
    public LabelExtInfo selectLabelExtInfoById(String labelId) throws BaseException;

    /**
     * Description: 新增一个标签扩展信息
     *
     * @param labelExtInfo
     * @throws BaseException
     */
    public void addLabelExtInfo(LabelExtInfo labelExtInfo) throws BaseException;

    /**
     * Description: 修改标签扩展信息
     *
     * @param labelExtInfo
     * @throws BaseException
     */
    public void modifyLabelExtInfo(LabelExtInfo labelExtInfo) throws BaseException;

    /**
     * Description: 删除标签扩展信息
     *
     * @param labelId
     * @throws BaseException
     */
    public void deleteLabelExtInfo(String labelId) throws BaseException;

}
