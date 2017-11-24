/*
 * @(#)MdaSysTableColServiceImpl.java
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
import com.asiainfo.biapp.si.loc.core.label.labelconfig.dao.IMdaSysTableColumnDao;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.service.IMdaSysTableColService;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.vo.MdaSysTableColumnVo;

/**
 * Title : MdaSysTableColServiceImpl
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月21日    lilin7        Created
 * </pre>
 * <p/>
 *
 * @author lilin7
 * @version 1.0.0.2017年11月21日
 */
@Service
@Transactional
public class MdaSysTableColServiceImpl extends BaseServiceImpl<MdaSysTableColumn, String>
        implements IMdaSysTableColService {

    @Autowired
    private IMdaSysTableColumnDao iMdaSysTableColumnDao;

    @Override
    protected BaseDao<MdaSysTableColumn, String> getBaseDao() {
        return iMdaSysTableColumnDao;
    }

    @Override
    public Page<MdaSysTableColumn> findMdaSysTableColPageList(Page<MdaSysTableColumn> page,
            MdaSysTableColumnVo mdaSysTableColumnVo) throws BaseException {
        return iMdaSysTableColumnDao.findMdaSysTableColPageList(page, mdaSysTableColumnVo);
    }

    @Override
    public List<MdaSysTableColumn> findMdaSysTableColList(MdaSysTableColumnVo mdaSysTableColumnVo)
            throws BaseException {
        return iMdaSysTableColumnDao.findMdaSysTableColList(mdaSysTableColumnVo);
    }

    @Override
    public MdaSysTableColumn getById(String columnId) throws BaseException {
        if (StringUtils.isBlank(columnId)) {
            throw new ParamRequiredException("Id不能为空");
        }
        return super.get(columnId);
    }

    @Override
    public void saveT(MdaSysTableColumn mdaSysTableColumn) throws BaseException {
        super.saveOrUpdate(mdaSysTableColumn);
    }

    public void updateT(MdaSysTableColumn mdaSysTableColumn) throws BaseException {
        super.saveOrUpdate(mdaSysTableColumn);
    };

    @Override
    public void deleteById(String columnId) throws BaseException {
        super.delete(columnId);
    }

}
