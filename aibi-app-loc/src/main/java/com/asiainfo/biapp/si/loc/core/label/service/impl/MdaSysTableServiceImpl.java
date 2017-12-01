/*
 * @(#)MdaSysTableServiceImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service.impl;

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
import com.asiainfo.biapp.si.loc.core.label.dao.IMdaSysTableDao;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTable;
import com.asiainfo.biapp.si.loc.core.label.service.IMdaSysTableService;
import com.asiainfo.biapp.si.loc.core.label.vo.MdaSysTableVo;

/**
 * Title : MdaSysTableServiceImpl
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
 * 1    2017年11月20日    lilin7        Created
 * </pre>
 * <p/>
 *
 * @author lilin7
 * @version 1.0.0.2017年11月20日
 */
@Service
@Transactional
public class MdaSysTableServiceImpl extends BaseServiceImpl<MdaSysTable, String> implements IMdaSysTableService {

    @Autowired
    private IMdaSysTableDao iMdaSysTableDao;

    @Override
    protected BaseDao<MdaSysTable, String> getBaseDao() {
        return iMdaSysTableDao;
    }

    @Override
    public Page<MdaSysTable> selectMdaSysTablePageList(Page<MdaSysTable> page, MdaSysTableVo mdaSysTableVo)
            throws BaseException {
        return iMdaSysTableDao.selectMdaSysTablePageList(page, mdaSysTableVo);
    }

    @Override
    public List<MdaSysTable> selectMdaSysTableList(MdaSysTableVo mdaSysTableVo) throws BaseException {
        return iMdaSysTableDao.selectMdaSysTableList(mdaSysTableVo);
    }

    public MdaSysTable selectMdaSysTableById(String tableId) throws BaseException {
        if (StringUtils.isBlank(tableId)) {
            throw new ParamRequiredException("Id不能为空");
        }
        return super.get(tableId);
    }

    public void addMdaSysTable(MdaSysTable mdaSysTable) throws BaseException {
        super.saveOrUpdate(mdaSysTable);
    }

    public void modifyMdaSysTable(MdaSysTable mdaSysTable) throws BaseException {
        super.saveOrUpdate(mdaSysTable);
    }

    public void deleteMdaSysTableById(String tableId) throws BaseException {
        if (selectMdaSysTableById(tableId)==null){
            throw new ParamRequiredException("ID不存在");
        }
        super.delete(tableId);
    }
}
