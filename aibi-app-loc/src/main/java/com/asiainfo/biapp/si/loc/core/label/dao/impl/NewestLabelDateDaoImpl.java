/*
 * @(#)NewestLabelDateDaoImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.label.dao.INewestLabelDateDao;
import com.asiainfo.biapp.si.loc.core.label.entity.NewestLabelDate;
import com.asiainfo.biapp.si.loc.core.label.vo.NewestLabelDateVo;

/**
 * Title : NewestLabelDateDaoImpl
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年11月21日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2017年11月21日
 */
@Repository
public class NewestLabelDateDaoImpl extends BaseDaoImpl<NewestLabelDate, String> implements INewestLabelDateDao{

    public Page<NewestLabelDate> findNewestLabelDatePageList(Page<NewestLabelDate> page,
            NewestLabelDateVo newestLabelDateVo) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from NewestLabelDate n where 1=1 ");
        if(StringUtil.isNoneBlank(newestLabelDateVo.getDayNewestDate()) && StringUtil.isNoneBlank(newestLabelDateVo.getMonthNewestDate())){
            hql.append("and n.dayNewestDate = :dayNewestDate and n.monthNewestDate = :monthNewestDate ");
            params.put("dayNewestDate", newestLabelDateVo.getDayNewestDate());
            params.put("monthNewestDate", newestLabelDateVo.getMonthNewestDate());
        }
        if(null != newestLabelDateVo.getDayNewestStatus()){
            hql.append("and n.dayNewestStatus = :dayNewestStatus ");
            params.put("dayNewestStatus", newestLabelDateVo.getDayNewestStatus());
        }
        if(null != newestLabelDateVo.getMonthNewestStatus()){
            hql.append("and n.monthNewestStatus = :monthNewestStatus ");
            params.put("monthNewestStatus", newestLabelDateVo.getMonthNewestStatus());
        }
        return super.findPageByHql(page, hql.toString(), params);
    }

    public List<NewestLabelDate> findNewestLabelDateList(NewestLabelDateVo newestLabelDateVo) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from NewestLabelDate n where 1=1 ");
        if(StringUtil.isNoneBlank(newestLabelDateVo.getDayNewestDate()) && StringUtil.isNoneBlank(newestLabelDateVo.getMonthNewestDate())){
            hql.append("and n.dayNewestDate = :dayNewestDate and n.monthNewestDate = :monthNewestDate ");
            params.put("dayNewestDate", newestLabelDateVo.getDayNewestDate());
            params.put("monthNewestDate", newestLabelDateVo.getMonthNewestDate());
        }
        if(null != newestLabelDateVo.getDayNewestStatus()){
            hql.append("and n.dayNewestStatus = :dayNewestStatus ");
            params.put("dayNewestStatus", newestLabelDateVo.getDayNewestStatus());
        }
        if(null != newestLabelDateVo.getMonthNewestStatus()){
            hql.append("and n.monthNewestStatus = :monthNewestStatus ");
            params.put("monthNewestStatus", newestLabelDateVo.getMonthNewestStatus());
        }
        return super.findListByHql(hql.toString(), params);
    }

}
