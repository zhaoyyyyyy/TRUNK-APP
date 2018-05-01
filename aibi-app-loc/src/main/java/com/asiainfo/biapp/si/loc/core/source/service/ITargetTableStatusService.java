/*
 * @(#)ITargetTableStatusService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.source.entity.TargetTableStatus;
import com.asiainfo.biapp.si.loc.core.source.vo.TargetTableStatusVo;

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
     * Description: 分页查询指标源表状态
     *
     * @param page
     * @param targetTableStatusVo
     * @return
     * @throws BaseException
     */
    public Page<TargetTableStatus> selectTargetTableStatusPageList(Page<TargetTableStatus> page,
            TargetTableStatusVo targetTableStatusVo) throws BaseException;

    /**
     * Description: 查询指标源表状态列表
     *
     * @param targetTableStatusVo
     * @return
     * @throws BaseException
     */
    public List<TargetTableStatus> selectTargetTableStatusList(TargetTableStatusVo targetTableStatusVo)
            throws BaseException;

    /**
     * Description: 通过ID拿到指标源表状态
     *
     * @param tabelId
     * @return
     * @throws BaseException
     */
    public TargetTableStatus selectTargertTableStatusById(String tableId) throws BaseException;

    /**
     * Description: 新增一个指标源表状态
     *
     * @param targetTableStatus
     * @throws BaseException
     */
    public void addTargertTableStatus(TargetTableStatus targetTableStatus) throws BaseException;

    /**
     * Description: 修改指标源表状态
     *
     * @param targetTableStatus
     * @throws BaseException
     */
    public void modifyTargertTableStatus(TargetTableStatus targetTableStatus) throws BaseException;

    /**
     * Description: 删除指标源表状态
     *
     * @param tabelId
     * @throws BaseException
     */
    public void deleteTargertTableStatus(String tableId) throws BaseException;

    /**
     * 
     * Description: 根据读取周期查询指标源表最新日期
     *
     * @param readCycle
     * @return
     * @throws BaseException
     */
    public String selectLastestDateByCycle(Integer  readCycle)throws BaseException;

}
