/*
 * @(#)ICustomDownloadRecordService.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.syspush.entity.CustomDownloadRecord;

/**
 * Title : ICustomDownloadRecordService
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月19日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月19日
 */
public interface ICustomDownloadRecordService extends BaseService<CustomDownloadRecord, String>{

    /**
     * 根据条件分页查询 
     *
     * @param page
     * @param CustomDownloadRecord
     * @return
     */
    public Page<CustomDownloadRecord> selectCustomDownloadRecordPageList(Page<CustomDownloadRecord> page, CustomDownloadRecord customDownloadRecord) throws BaseException;
    
    /**
     * 
     * 根据条件查询列表
     *
     * @param CustomDownloadRecordVo
     * @return
     */
    public List<CustomDownloadRecord> selectCustomDownloadRecordList(CustomDownloadRecord customDownloadRecord) throws BaseException;
    
    /**
     * 通过主键得到一个实体 Description:
     *
     * @param priKey
     * @return
     * @throws BaseException
     */
    public CustomDownloadRecord selectCustomDownloadRecordById(String priKey) throws BaseException;
    
    /**
     * 通过主键删除一个实体 Description:
     *
     * @param priKey
     * @throws BaseException
     */
    public void deleteCustomDownloadRecordById(String priKey) throws BaseException;
}
