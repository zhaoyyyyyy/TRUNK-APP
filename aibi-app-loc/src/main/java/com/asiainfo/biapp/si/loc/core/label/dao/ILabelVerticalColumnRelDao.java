/*
 * @(#)ILabelVerticalColumnRelDao.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelVerticalColumnRel;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelVerticalColumnRelVo;

/**
 * Title : ILabelVerticalColumnRelDao
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
 * <pre>1    2017年11月21日     wangrd        Created</pre>
 * <p/>
 *
 * @author   wangrd
 * @version 1.0.0.2017年11月21日
 */
public interface ILabelVerticalColumnRelDao extends BaseDao<LabelVerticalColumnRel, String>{
    
    /**
     * 根据条件分页查询
     * 
     * @param page
     * @param LabelVerticalColumnRel
     * @return 
     */
    public Page<LabelVerticalColumnRel> selectLabelVerticalColumnRelPageList(Page<LabelVerticalColumnRel> page, LabelVerticalColumnRelVo labelVerticalColumnRelVo);
    
    /**
     * 根据条件查询列表
     * 
     * @param LabelVerticalColumnRel
     * @return
     */
    public List<LabelVerticalColumnRel> selectLabelVerticalColumnRelList(LabelVerticalColumnRelVo labelVerticalColumnRelVo);

}
