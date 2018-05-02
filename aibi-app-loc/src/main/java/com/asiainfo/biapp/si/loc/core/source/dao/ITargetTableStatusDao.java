/*
 * @(#)ITargetTableStatusDao.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.source.entity.TargetTableStatus;
import com.asiainfo.biapp.si.loc.core.source.vo.TargetTableStatusVo;

/**
 * Title : ITargetTableStatusDao
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
public interface ITargetTableStatusDao extends BaseDao<TargetTableStatus, String> {

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param targetTableStatusVo
     * @return
     */
    public Page<TargetTableStatus> selectTargetTableStatusPageList(Page<TargetTableStatus> page,
            TargetTableStatusVo targetTableStatusVo);

    /**
     * 根据条件查询列表
     *
     * @param targetTableStatusVo
     * @return
     */
    public List<TargetTableStatus> selectTargetTableStatusList(TargetTableStatusVo targetTableStatusVo);
    
    /**
     * 根据读取周期查询指标源表最新日期
     *
     * @return
     */
    public String selectLastestDateByCycle();
    

}
