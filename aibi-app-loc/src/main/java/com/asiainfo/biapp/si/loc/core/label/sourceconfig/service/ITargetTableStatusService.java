/*
 * @(#)ITargetTableStatusService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.sourceconfig.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.entity.TargetTableStatus;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.vo.TargetTableStatusVo;

/**
 * Title : ITargetTableStatusService
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月15日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月15日
 */
public interface ITargetTableStatusService extends BaseService<TargetTableStatus, String> {

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param targetTableStatusVo
     * @return
     */
    public Page<TargetTableStatus> findTargetTableStatusPageList(Page<TargetTableStatus> page,
            TargetTableStatusVo targetTableStatusVo) throws BaseException;

    /**
     * 根据条件查询列表
     *
     * @param targetTableStatusVo
     * @return
     */
    public List<TargetTableStatus> findTargetTableStatusList(TargetTableStatusVo targetTableStatusVo)
            throws BaseException;

    /**
     * 根据ID得到一个实体
     *
     * @param labelId
     * @return
     * @throws BaseException
     */
    public TargetTableStatus getById(String labelId) throws BaseException;

    /**
     * 新增一个实体
     *
     * @param targetTableStatus
     * @throws BaseException
     */
    public void saveT(TargetTableStatus targetTableStatus) throws BaseException;

    /**
     * 修改一个实体
     *
     * @param targetTableStatus
     * @throws BaseException
     */
    public void updateT(TargetTableStatus targetTableStatus);

    /**
     * 根据ID删除一个实体
     *
     * @param labelId
     * @throws BaseException
     */
    public void deleteById(String labelId) throws BaseException;

}
