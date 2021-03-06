/*
 * @(#)IPreConfigInfoDao.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.PreConfigInfo;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.PreConfigInfoVo;

/**
 * Title : IPreConfigInfoDao
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月7日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月7日
 */
public interface IPreConfigInfoDao extends BaseDao<PreConfigInfo, String> {

    /**
     * 根据条件分页查询专区
     * 
     * @param page
     * @param dataSourceInfo
     * @return
     */
    public Page<PreConfigInfo> selectPreConfigInfoPageList(Page<PreConfigInfo> page, PreConfigInfoVo preConfigInfoVo,User user);

    /**
     * 根据条件查询专区列表
     * 
     * @param page
     * @param dataSourceInfo
     * @return
     */
    public List<PreConfigInfo> selectPreConfigInfoList(PreConfigInfoVo preConfigInfoVo,User user);

    /**
     * 根据专区名称查询专区
     * 
     * @param sourceName
     * @return
     */
    public PreConfigInfo selectOneBySourceName(String sourceName);
    
    public List<PreConfigInfo> selectEffectivaPreConfigInfo();

}
