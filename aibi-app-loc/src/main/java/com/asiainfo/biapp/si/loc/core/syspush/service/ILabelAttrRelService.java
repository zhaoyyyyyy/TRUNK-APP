/*
 * @(#)ILabelAttrRelService.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service;

import java.util.Date;
import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelAttrRelVo;

/**
 * Title : ILabelAttrRelService
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
public interface ILabelAttrRelService extends BaseService<LabelAttrRel, String>{

    /**
     * 根据条件分页查询 
     *
     * @param page
     * @param labelAttrRel
     * @return
     */
    public Page<LabelAttrRel> selectLabelAttrRelPageList(Page<LabelAttrRel> page, LabelAttrRelVo labelAttrRelVo) throws BaseException;
    
    /**
     * 
     * 根据条件查询列表
     *
     * @param labelAttrRelVo
     * @return
     */
    public List<LabelAttrRel> selectLabelAttrRelList(LabelAttrRelVo labelAttrRelVo) throws BaseException;
    
    /**
     * 通过主键得到一个实体 Description:
     *
     * @param priKey
     * @return
     * @throws BaseException
     */
    public LabelAttrRel selectLabelAttrRelById(String priKey) throws BaseException;
    
    /**
     * 新增或修改一个实体 Description:
     *
     * @param labelAttrRel
     * @throws BaseException
     */
    public void addLabelAttrRel(LabelAttrRel labelAttrRel) throws BaseException;

    /**
     * 修改一个实体 Description:
     *
     * @param labelAttrRel
     * @throws BaseException
     */
    public void modifyLabelAttrRel(LabelAttrRel labelAttrRel) throws BaseException;

    /**
     * 通过主键删除一个实体 Description:
     *
     * @param priKey
     * @throws BaseException
     */
    public void deleteLabelAttrRelById(String priKey) throws BaseException;
}
