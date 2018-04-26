/*
 * @(#)IMdaSysTableColService.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.vo.MdaSysTableColumnVo;

/**
 * Title : IMdaSysTableColService
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
public interface IMdaSysTableColService extends BaseService<MdaSysTableColumn, String> {

    /**
     * Description: 根据分页条件查询
     *
     * @param page
     * @param mdaSysTableColumnVo
     * @return
     * @throws BaseException
     */
    public Page<MdaSysTableColumn> selectMdaSysTableColPageList(Page<MdaSysTableColumn> page,
            MdaSysTableColumnVo mdaSysTableColumnVo) throws BaseException;

    /**
     * Description:根据条件查询列表
     *
     * @param mdaSysTableColumnVo
     * @return
     * @throws BaseException
     */
    public List<MdaSysTableColumn> selectMdaSysTableColList(MdaSysTableColumnVo mdaSysTableColumnVo) throws BaseException;

    /**
     * Description:根据列id得到一个实体
     *
     * @param columnId
     * @return
     */
    public MdaSysTableColumn selectMdaSysTableColumnById(String columnId) throws BaseException;

    /**
     * Description:新增一个实体
     *
     * @param mdaSysTableColumn
     * @throws BaseException
     */
    public void addMdaSysTableColumn(MdaSysTableColumn mdaSysTableColumn) throws BaseException;

    /**
     * Description:修改
     *
     * @param mdaSysTableColumn
     */
    public void modifyMdaSysTableColumn(MdaSysTableColumn mdaSysTableColumn) throws BaseException;

    /**
     * Description: 通过Id删除一个实体
     *
     * @param columnId
     * @throws BaseException
     */
    public void deleteMdaSysTableColumnById(String columnId) throws BaseException;
    
    /**
     * 
     * Description:根据标签id获取实体 
     *
     * @param labelId
     * @return
     */
    public MdaSysTableColumn selectMdaSysTableColBylabelId(String labelId);
    
    /**
     * 
     * Description:根据标签id获取实体 
     *
     * @param labelId
     * @return
     */
    public List<MdaSysTableColumn> selectMdaSysTableColListBylabelId(String labelId);
    
    /**
     * 
     * Description: 向mdaSysTableColList中添加属性值 
     *
     * @param mdaSysTableColList
     * @return
     */
    public List<MdaSysTableColumn> addMdaSysTableColList(List<MdaSysTableColumn> mdaSysTableColList);
}
