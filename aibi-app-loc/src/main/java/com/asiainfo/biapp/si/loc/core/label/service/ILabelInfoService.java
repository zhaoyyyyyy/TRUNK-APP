/*
 * @(#)ILabelInfoService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelInfoVo;

/**
 * Title : ILabelInfoService
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
 * 1    2017年11月16日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月16日
 */
public interface ILabelInfoService extends BaseService<LabelInfo, String> {

    /**
     * Description:分页查询标签信息
     *
     * @param page
     * @param labelInfoVo
     * @return
     * @throws BaseException
     */
    public Page<LabelInfo> selectLabelInfoPageList(Page<LabelInfo> page, LabelInfoVo labelInfoVo) throws BaseException;

    /**
     * Description:查询标签信息列表
     *
     * @param labelInfoVo
     * @return
     * @throws BaseException
     */
    public List<LabelInfo> selectLabelInfoList(LabelInfoVo labelInfoVo) throws BaseException;

    /**
     * Description:通过ID拿到标签信息
     *
     * @param labelId
     * @return
     * @throws BaseException
     */
    public LabelInfo selectLabelInfoById(String labelId) throws BaseException;

    /**
     * Description:新增一个标签信息
     *
     * @param labelInfo
     * @throws BaseException
     */
    public void addLabelInfo(LabelInfo labelInfo) throws BaseException;

    /**
     * Description:修改标签信息
     *
     * @param labelInfo
     * @throws BaseException
     */
    public void modifyLabelInfo(LabelInfo labelInfo) throws BaseException;

    /**
     * Description:删除标签信息
     *
     * @param labelId
     * @throws BaseException
     */
    public void deleteLabelInfo(String labelId) throws BaseException;

}
