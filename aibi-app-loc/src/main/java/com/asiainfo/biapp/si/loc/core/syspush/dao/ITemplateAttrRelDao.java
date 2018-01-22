/*
 * @(#)ITemplateAttrRelDao.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.syspush.entity.TemplateAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.vo.TemplateAttrRelVo;

/**
 * Title : ITemplateAttrRelDao
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
public interface ITemplateAttrRelDao extends BaseDao<TemplateAttrRel, String>{

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param TemplateAttrRel
     * @return
     */
    public Page<TemplateAttrRel> selectTemplateAttrRelPageList(Page<TemplateAttrRel> page, TemplateAttrRelVo templateAttrRelVo);

    /**
     * 根据条件查询列表
     *
     * @param TemplateAttrRel
     * @return
     */
    public List<TemplateAttrRel> selectTemplateAttrRelList(TemplateAttrRelVo templateAttrRelVo);
}
