/*
 * @(#)CustomDownloadRecordServiceImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.DateUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.syspush.dao.ICustomDownloadRecordDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.CustomDownloadRecord;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.service.ICustomDownloadRecordService;
import com.asiainfo.biapp.si.loc.core.syspush.service.ICustomerPublishCommService;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelAttrRelVo;

/**
 * Title : CustomDownloadRecordServiceImpl
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
@Service
@Transactional
public class CustomDownloadRecordServiceImpl extends BaseServiceImpl<CustomDownloadRecord, String> implements ICustomDownloadRecordService{

    private static final int ONE = 1;
    
    
    @Autowired
    private ICustomerPublishCommService iCustomerPublishCommService;
    
    @Autowired
    private ICustomDownloadRecordDao iCustomDownloadRecordDao;
    
    @Override
    protected BaseDao<CustomDownloadRecord, String> getBaseDao() {
        return iCustomDownloadRecordDao;
    }
    
    public Page<CustomDownloadRecord> selectCustomDownloadRecordPageList(Page<CustomDownloadRecord> page, CustomDownloadRecord customDownloadRecord) throws BaseException {
        Page<CustomDownloadRecord> customDownloadRecords = iCustomDownloadRecordDao.selectCustomDownloadRecordPageList(page, customDownloadRecord);
        List<CustomDownloadRecord> data = customDownloadRecords.getData();
        //获取属性列
        if (!data.isEmpty()) {
            LabelAttrRelVo labelAttrRelVo = new LabelAttrRelVo();
            labelAttrRelVo.setLabelId(customDownloadRecord.getCustomId());
            labelAttrRelVo.setAttrSource(ServiceConstants.LabelAttrRel.ATTR_SOURCE_LABEL);
            labelAttrRelVo.setAttrSettingType(ServiceConstants.LabelAttrRel.ATTR_SETTING_TYPE_DOWNLOAD);
//            labelAttrRelVo.setStatus(ServiceConstants.LabelAttrRel.STATUS_SUCCESS);
            labelAttrRelVo.setOrderBy("pageSortNum ASC,sortNum ASC");
            StringBuilder sb = new StringBuilder();
            LabelInfo customInfo = null;
            for (CustomDownloadRecord obj : data) {
                List<LabelAttrRel> attrRelList = new ArrayList<>();
                if (StringUtil.isNotBlank(obj.getCustomId()) && StringUtil.isNotBlank(obj.getDataDate())) {
                    customInfo = new LabelInfo();
                    customInfo.setLabelId(obj.getCustomId());
                    customInfo.setDataDate(obj.getDataDate());
                    String dateStr = obj.getFileName().split("_")[2];
                    labelAttrRelVo.setModifyTimeStart(dateStr);
                    attrRelList = iCustomerPublishCommService.getLabelAttrRelsByCustom(customInfo, labelAttrRelVo);
                    if (!attrRelList.isEmpty()) {
                        sb.delete(0, sb.length());
                        for (LabelAttrRel labelAttrRel : attrRelList) {
                            sb.append(labelAttrRel.getAttrColName()).append(",");
                        }
                        if (StringUtil.isNotEmpty(sb.toString())) {
                            sb.delete(sb.length()-ONE, sb.length());
                            obj.setColNames(sb.toString());
                        }
                    }
                }
            }
        }
        return customDownloadRecords;
    }

    public List<CustomDownloadRecord> selectCustomDownloadRecordList(CustomDownloadRecord customDownloadRecord) throws BaseException {
        return iCustomDownloadRecordDao.selectCustomDownloadRecordList(customDownloadRecord);
    }

    public CustomDownloadRecord selectCustomDownloadRecordById(String priKey) throws BaseException {
        if(StringUtil.isBlank(priKey)){
            throw new ParamRequiredException("主键为空");
        }
        return super.get(priKey);
    }

    public void deleteCustomDownloadRecordById(String priKey) throws BaseException {
        if(StringUtil.isBlank(priKey)){
            throw new ParamRequiredException("主键为空");
        }
        super.delete(priKey);
    }
}
