/*
 * @(#)ILabelInfoDao.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelInfoVo;

/**
 * Title : ILabelInfoDao
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
 * 1    2017年11月16日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月16日
 */
public interface ILabelInfoDao extends BaseDao<LabelInfo, String> {

    /**
     * Description: 按条件查询分页列表
     *
     * @param page
     * @param labelInfo
     * @return
     */
    public Page<LabelInfo> selectLabelInfoPageList(Page<LabelInfo> page, LabelInfoVo labelInfoVo);

    /**
     * Description: 按条件查询列表
     *
     * @param labelInfo
     * @return
     */
    public List<LabelInfo> selectLabelInfoList(LabelInfoVo labelInfoVo);

    /**
     * Description: 取得所有已生效标签
     *
     * @return
     */
    public List<LabelInfo> selectEffectiveCiLabelInfo();

}
