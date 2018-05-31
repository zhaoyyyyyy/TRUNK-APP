package com.asiainfo.biapp.si.loc.core.home.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.home.dao.ILocPersonNoticeDao;
import com.asiainfo.biapp.si.loc.core.home.dao.IUserAttentionLabelDao;
import com.asiainfo.biapp.si.loc.core.home.entity.LocPersonNotice;
import com.asiainfo.biapp.si.loc.core.home.entity.UserAttentionLabel;
import com.asiainfo.biapp.si.loc.core.home.vo.LocPersonNoticeVo;

/**
 * Title : UserAttentionLabelDaoImpl
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
@Repository

public class UserAttentionLabelDaoImpl extends BaseDaoImpl<UserAttentionLabel, String> implements IUserAttentionLabelDao {
    private static final int FIRST_INDEX = 0;
    private static final String PARAMS = "params";
    
    @Override
    public Page<LocPersonNotice> selectLocPersonNoticeList(Page<LocPersonNotice> page,LocPersonNoticeVo locPersonNoticeVo) {
        Map<String, Object> reMap = fromBean(locPersonNoticeVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get(PARAMS);
        return super.findPageByHql(page,reMap.get("hql").toString(), params);
    }

}
