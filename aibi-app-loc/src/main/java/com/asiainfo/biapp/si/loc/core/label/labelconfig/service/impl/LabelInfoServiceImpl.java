/*
 * @(#)LabelInfoServiceImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.labelconfig.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.dao.ILabelInfoDao;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.vo.LabelInfoVo;

/**
 * Title : LabelInfoServiceImpl
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
@Service
@Transactional
public class LabelInfoServiceImpl extends BaseServiceImpl<LabelInfo, String> implements ILabelInfoService {

    @Autowired
    private ILabelInfoDao iLabelInfoDao;

    @Override
    protected BaseDao<LabelInfo, String> getBaseDao() {
        return iLabelInfoDao;
    }

    /**
     * Description: 按条件查询分页列表
     *
     * @param page
     * @param labelInfo
     * @return
     */
    public Page<LabelInfo> findLabelInfoPageList(Page<LabelInfo> page, LabelInfoVo labelInfoVo) {
        return iLabelInfoDao.findLabelInfoPageList(page, labelInfoVo);
    }

    /**
     * Description: 按条件查询列表
     *
     * @param labelInfo
     * @return
     */
    public List<LabelInfo> findLabelInfoList(LabelInfoVo labelInfoVo) {
        return iLabelInfoDao.findLabelInfoList(labelInfoVo);
    }

    /**
     * Description: 通过ID得到一个实体
     *
     * @param labelId
     * @return
     * @throws BaseException
     */
    public LabelInfo getById(String labelId) throws BaseException {
        if (StringUtils.isBlank(labelId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(labelId);
    }

    /**
     * Description: 新增一个实体
     *
     * @param labelInfo
     * @throws BaseException
     */
    public void saveT(LabelInfo labelInfo) {
        super.saveOrUpdate(labelInfo);
    }

    /**
     * Description: 修改一个实体
     *
     * @param labelInfo
     */
    public void updateT(LabelInfo labelInfo) {
        super.saveOrUpdate(labelInfo);
    }

    /**
     * Description: 通过ID删除一个实体
     *
     * @param labelId
     */
    public void deleteById(String labelId) {
        super.delete(labelId);
    }

}
