/*
 * @(#)ITemplateAttrRelService.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.syspush.entity.TemplateAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.vo.TemplateAttrRelVo;

/**
 * Title : ITemplateAttrRelService
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
public interface ITemplateAttrRelService extends BaseService<TemplateAttrRel, String>{

    /**
     * 根据条件分页查询 
     *
     * @param page
     * @param templateAttrRel
     * @return
     */
    public Page<TemplateAttrRel> selectTemplateAttrRelPageList(Page<TemplateAttrRel> page, TemplateAttrRelVo templateAttrRelVo) throws BaseException;
    
    /**
     * 
     * 根据条件查询列表
     *
     * @param templateAttrRelVo
     * @return
     */
    public List<TemplateAttrRel> selectTemplateAttrRelList(TemplateAttrRelVo templateAttrRelVo) throws BaseException;
    
    /**
     * 通过ID得到一个实体 Description:
     *
     * @param templateId,labelId,labelColumnId
     * @return
     * @throws BaseException
     */
    public TemplateAttrRel selectTemplateAttrRelById(String templateId, String labelId, String labelColumnId) throws BaseException;
    
    /**
     * 新增或修改一个实体 Description:
     *
     * @param templateAttrRel
     * @throws BaseException
     */
    public void addTemplateAttrRel(TemplateAttrRel templateAttrRel) throws BaseException;

    /**
     * 修改一个实体 Description:
     *
     * @param templateAttrRel
     * @throws BaseException
     */
    public void modifyTemplateAttrRel(TemplateAttrRel templateAttrRel) throws BaseException;

    /**
     * 通过ID删除一个实体 Description:
     *
     * @param templateId,labelId,labelColumnId
     * @throws BaseException
     */
    public void deleteTemplateAttrRelById(String templateId, String labelId, String labelColumnId) throws BaseException;
}
