/*
 * @(#)ILabelAttrTemplateInfoService.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrTemplateInfo;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelAttrTemplateInfoVo;

/**
 * Title : ILabelAttrTemplateInfoService
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
 * <pre>1    2018年1月19日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月19日
 */
public interface ILabelAttrTemplateInfoService extends BaseService<LabelAttrTemplateInfo, String>{

    /**
     * 根据条件分页查询 
     *
     * @param page
     * @param labelAttrTemplateInfo
     * @return
     */
    public Page<LabelAttrTemplateInfo> selectLabelAttrTemplateInfoPageList(Page<LabelAttrTemplateInfo> page, LabelAttrTemplateInfoVo labelAttrTemplateInfoVo) throws BaseException;
    
    /**
     * 
     * 根据条件查询列表
     *
     * @param labelAttrTemplateInfoVo
     * @return
     */
    public List<LabelAttrTemplateInfo> selectLabelAttrTemplateInfoList(LabelAttrTemplateInfoVo labelAttrTemplateInfoVo) throws BaseException;
    
    /**
     * 通过ID得到一个实体 Description:
     *
     * @param templateId
     * @return
     * @throws BaseException
     */
    public LabelAttrTemplateInfo selectLabelAttrTemplateInfoById(String templateId) throws BaseException;
    
    /**
     * 新增或修改一个实体 Description:
     *
     * @param labelAttrTemplateInfo
     * @throws BaseException
     */
    public void addLabelAttrTemplateInfo(LabelAttrTemplateInfo labelAttrTemplateInfo) throws BaseException;

    /**
     * 修改一个实体 Description:
     *
     * @param labelAttrTemplateInfo
     * @throws BaseException
     */
    public void modifyLabelAttrTemplateInfo(LabelAttrTemplateInfo labelAttrTemplateInfo) throws BaseException;

    /**
     * 通过ID删除一个实体 Description:
     *
     * @param templateId
     * @throws BaseException
     */
    public void deleteLabelAttrTemplateInfoById(String templateId) throws BaseException;
}
