/*
 * @(#)IMdaSysTableColumnDao.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.vo.MdaSysTableColumnVo;

/**
 * Title : IMdaSysTableColumnDao
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月21日    lilin7        Created
 * </pre>
 * <p/>
 *
 * @author lilin7
 * @version 1.0.0.2017年11月21日
 */
public interface IMdaSysTableColumnDao extends BaseDao<MdaSysTableColumn, String> {

    /**
     * Description: 根据分页条件查询
     *
     * @param page
     * @param mdaSysTableColumnVo
     * @return
     */
    public Page<MdaSysTableColumn> selectMdaSysTableColPageList(Page<MdaSysTableColumn> page,
            MdaSysTableColumnVo mdaSysTableColumnVo);

    /**
     * Description:根据条件查询列表
     *
     * @param mdaSysTableColumnVo
     * @return
     */
    public List<MdaSysTableColumn> selectMdaSysTableColList(MdaSysTableColumnVo mdaSysTableColumnVo);
    
    public MdaSysTableColumn selectMdaSysTableColBylabelId(String labelId);
}

