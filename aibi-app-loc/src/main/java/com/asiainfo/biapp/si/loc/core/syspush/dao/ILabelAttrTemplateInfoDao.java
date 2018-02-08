/*
 * @(#)ILabelAttrTemplateInfoDao.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrTemplateInfo;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelAttrRelVo;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelAttrTemplateInfoVo;

/**
 * Title : ILabelAttrTemplateInfoDao
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
public interface ILabelAttrTemplateInfoDao extends BaseDao<LabelAttrTemplateInfo, String>{

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param LabelAttrTemplateInfo
     * @return
     */
    public Page<LabelAttrTemplateInfo> selectLabelAttrTemplateInfoPageList(Page<LabelAttrTemplateInfo> page, LabelAttrTemplateInfoVo labelAttrTemplateInfoVo);

    /**
     * 根据条件查询列表
     *
     * @param LabelAttrTemplateInfo
     * @return
     */
    public List<LabelAttrTemplateInfo> selectLabelAttrTemplateInfoList(LabelAttrTemplateInfoVo labelAttrTemplateInfoVo);
}
