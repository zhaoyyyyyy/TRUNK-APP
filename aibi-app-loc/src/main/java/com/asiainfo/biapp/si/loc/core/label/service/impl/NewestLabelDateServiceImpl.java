/*
 * @(#)NewestLabelDateServiceImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.label.dao.INewestLabelDateDao;
import com.asiainfo.biapp.si.loc.core.label.entity.NewestLabelDate;
import com.asiainfo.biapp.si.loc.core.label.service.INewestLabelDateService;
import com.asiainfo.biapp.si.loc.core.label.vo.NewestLabelDateVo;

/**
 * Title : NewestLabelDateServiceImpl
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
@Service
@Transactional
public class NewestLabelDateServiceImpl extends BaseServiceImpl<NewestLabelDate, String> implements INewestLabelDateService{

    @Autowired
    private INewestLabelDateDao iNewestLabelDateDao;
    
    protected BaseDao<NewestLabelDate, String> getBaseDao() {
        return iNewestLabelDateDao;
    }
    
    public Page<NewestLabelDate> selectNewestLabelDatePageList(Page<NewestLabelDate> page,
            NewestLabelDateVo newestLabelDateVo) throws BaseException {
        return iNewestLabelDateDao.selectNewestLabelDatePageList(page, newestLabelDateVo);
    }

    public List<NewestLabelDate> selectNewestLabelDateList(NewestLabelDateVo newestLabelDateVo) throws BaseException {
        return iNewestLabelDateDao.selectNewestLabelDateList(newestLabelDateVo);
    }

    public NewestLabelDate selectNewestLabelDateByDayNewestDate(String dayNewestDate) throws BaseException {
        if(StringUtil.isBlank(dayNewestDate)){
            throw new ParamRequiredException("最新日数据日期不能为空");
        }
        return super.get(dayNewestDate);
    }

    public void addNewestLabelDate(NewestLabelDate newestLabelDate) throws BaseException {
        super.saveOrUpdate(newestLabelDate);
        
    }

    public void modifyNewestLabelDate(NewestLabelDate newestLabelDate) throws BaseException {
        super.saveOrUpdate(newestLabelDate);
        
    }

    public void deleteNewestLabelDateByDayNewestDate(String dayNewestDate) throws BaseException {
        if (selectNewestLabelDateByDayNewestDate(dayNewestDate)==null){
            throw new ParamRequiredException("ID不存在");
        }
        super.delete(dayNewestDate);
        
    }

    

}
