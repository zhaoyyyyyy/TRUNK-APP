package com.asiainfo.biapp.si.loc.core.home.dao;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.home.entity.UserAttentionLabel;
import com.asiainfo.biapp.si.loc.core.home.vo.UserAttentionLabelVo;

/**
 * Title : IUserAttentionLabelDao
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 7.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年5月18日    zhaoyy5        Created</pre>
 * <p/>
 *
 * @author zhaoyy5
 * @version 1.0.0.2018年5月18日
 */
public interface IUserAttentionLabelDao extends BaseDao<UserAttentionLabel, String> {

    /**
     * Description：获取收藏列表
     *
     * @param UserAttentionLabelVo
     * @return
     */
    Page<UserAttentionLabel> selectUserAttentionLabelList(Page<UserAttentionLabel> page,UserAttentionLabelVo userAttentionLabelVo);

}
