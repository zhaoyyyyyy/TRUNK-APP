/*
 * @(#)INewestLabelDateService.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.label.entity.NewestLabelDate;
import com.asiainfo.biapp.si.loc.core.label.vo.NewestLabelDateVo;

/**
 * Title : INewestLabelDateService
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
public interface INewestLabelDateService extends BaseService<NewestLabelDate, String>{

    /**
     * 根据条件分页查询
     * 
     * @param page
     * @param NewestLabelDateVo
     * @return 
     */
    public Page<NewestLabelDate> selectNewestLabelDatePageList(Page<NewestLabelDate> page, NewestLabelDateVo newestLabelDateVo) throws BaseException;

    /**
     * 根据条件查询列表
     * 
     * @param NewestLabelDateVo
     * @return
     */
    public List<NewestLabelDate> selectNewestLabelDateList(NewestLabelDateVo newestLabelDateVo) throws BaseException;
    
    /**
     * 通过最新日数据日期得到一个实体类Description
     * 
     * @param dayNewestDate
     * @return 
     * @throws BaseException
     */
    public NewestLabelDate selectNewestLabelDateByDayNewestDate(String dayNewestDate) throws BaseException;
    
    /**
     * 新增或修改一个实体 Description:
     *
     * @param newestLabelDate
     * @throws BaseException
     */
    public void addNewestLabelDate(NewestLabelDate newestLabelDate) throws BaseException;
    
    /**
     * 修改一个实体 Description:
     *
     * @param newestLabelDate
     * @throws BaseException
     */
    public void modifyNewestLabelDate(NewestLabelDate newestLabelDate) throws BaseException;
    
    /**
     * 通过最新日数据日期删除一个实体 Description:
     *
     * @param dayNewestDate
     * @throws BaseException
     */
    public void deleteNewestLabelDateByDayNewestDate(String dayNewestDate) throws BaseException;
}
