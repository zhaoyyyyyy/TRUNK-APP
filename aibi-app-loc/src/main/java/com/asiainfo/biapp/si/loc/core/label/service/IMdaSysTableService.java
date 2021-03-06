/*
 * @(#)IMdaSysTableService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTable;
import com.asiainfo.biapp.si.loc.core.label.vo.MdaSysTableVo;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.PreConfigInfo;

/**
 * Title : IMdaSysTableService
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
 * 1    2017年11月20日    lilin7        Created
 * </pre>
 * <p/>
 *
 * @author lilin7
 * @version 1.0.0.2017年11月20日
 */
public interface IMdaSysTableService extends BaseService<MdaSysTable, String> {

    /**
     * @Describe 根据分页条件查询
     * @author lilin7
     * @param page
     * @param mdaSysTableVo
     * @return
     */
    public Page<MdaSysTable> selectMdaSysTablePageList(Page<MdaSysTable> page, MdaSysTableVo mdaSysTableVo)
            throws BaseException;

    /**
     * @Describe 根据条件查询列表
     * @author lilin7
     * @param page
     * @param mdaSysTableVo
     * @return
     */
    public List<MdaSysTable> selectMdaSysTableList(MdaSysTableVo mdaSysTableVo) throws BaseException;

    /**
     * @Describe 通过ID得到一个实体
     * @author lilin7
     * @param tableId
     * @return
     */
    public MdaSysTable selectMdaSysTableById(String tableId) throws BaseException;

    /**
     * @Describe 新增一个实体
     * @author lilin7
     * @param mdaSysTableVo
     * @return
     */
    public void addMdaSysTable(MdaSysTable mdaSysTable) throws BaseException;

    /**
     * @Describe 修改一个实体
     * @author lilin7
     * @param mdaSysTableVo
     * @return
     */
    public void modifyMdaSysTable(MdaSysTable mdaSysTable) throws BaseException;

    /**
     * @Describe 通过Id删除一个实体
     * @author lilin7
     * @param tableId
     * @return
     */
    public void deleteMdaSysTableById(String tableId) throws BaseException;

    /**
     * Description:添加用户/客户群  日/月周期表
     *
     * @param preConfigInfo
     */
    public void addDWTable(PreConfigInfo preConfigInfo);

    /**
     * Description:通过专区ID与表类型获得表的集合
     *
     * @param configId
     * @param tableType
     * @return
     */
    public List<MdaSysTable> selectMdaSysTableListByConfigAndType(String configId, Integer tableType);
    
    /**
     * 
     * Description: 
     *
     * @param configId	专区id
     * @param updateCycle	更新周期
     * @param tableType	宽表类型	（1、标签  2、客户群）
     * @return
     *
     * @author  tianxy3
     * @date 2018年1月11日
     */
    public MdaSysTable queryMdaSysTable(String configId,Integer updateCycle,Integer tableType);
    
}
