/*
 * @(#)ILabelVerticalColumnRelService.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelStatus;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelVerticalColumnRel;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelVerticalColumnRelVo;

/**
 * Title : ILabelVerticalColumnRelService
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年11月21日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2017年11月21日
 */
public interface ILabelVerticalColumnRelService extends BaseService<LabelVerticalColumnRel, String>{

    /**
     * 根据条件分页查询
     * 
     * @param page
     * @param LabelVerticalColumnRel
     * @return 
     */
    public Page<LabelVerticalColumnRel> findLabelVerticalColumnRelPageList(Page<LabelVerticalColumnRel> page, LabelVerticalColumnRelVo labelVerticalColumnRelVo) throws BaseException;

    /**
     * 根据条件查询列表
     * 
     * @param LabelVerticalColumnRel
     * @return
     */
    public List<LabelVerticalColumnRel> findLabelVerticalColumnRelList(LabelVerticalColumnRelVo labelVerticalColumnRelVo) throws BaseException;

    /**
     * 通过ID得到一个实体类Description
     * 
     * @param labelId
     * @return 
     * @throws BaseException
     */
    public LabelVerticalColumnRel getById(String labelId) throws BaseException;
    
    /**
     * 新增或修改一个实体 Description:
     *
     * @param labelVerticalColumnRel
     * @throws BaseException
     */
    public void saveT(LabelVerticalColumnRel labelVerticalColumnRel) throws BaseException;
    
    /**
     * 修改一个实体 Description:
     *
     * @param labelVerticalColumnRel
     * @throws BaseException
     */
    public void updateT(LabelVerticalColumnRel labelVerticalColumnRel) throws BaseException;
    
    /**
     * 通过ID删除一个实体 Description:
     *
     * @param labelId
     * @throws BaseException
     */
    public void deleteById(String labelId) throws BaseException;
}
