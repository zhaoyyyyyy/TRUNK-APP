/*
 * @(#)LabelVerticalColumnRelServiceImpl.java
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
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelVerticalColumnRelDao;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelVerticalColumnRel;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelVerticalColumnRelId;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelVerticalColumnRelService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelVerticalColumnRelVo;

/**
 * Title : LabelVerticalColumnRelServiceImpl
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
public class LabelVerticalColumnRelServiceImpl extends BaseServiceImpl<LabelVerticalColumnRel, String> implements ILabelVerticalColumnRelService{

    @Autowired
    private ILabelVerticalColumnRelDao iLabelVerticalColumnRelDao;
    
    @Override
    protected BaseDao<LabelVerticalColumnRel, String> getBaseDao() {
        return iLabelVerticalColumnRelDao;
    }

    public Page<LabelVerticalColumnRel> selectLabelVerticalColumnRelPageList(Page<LabelVerticalColumnRel> page,
            LabelVerticalColumnRelVo labelVerticalColumnRelVo) throws BaseException {
        return iLabelVerticalColumnRelDao.selectLabelVerticalColumnRelPageList(page, labelVerticalColumnRelVo);
    }

    public List<LabelVerticalColumnRel> selectLabelVerticalColumnRelList(
            LabelVerticalColumnRelVo labelVerticalColumnRelVo) throws BaseException {
        return iLabelVerticalColumnRelDao.selectLabelVerticalColumnRelList(labelVerticalColumnRelVo);
    }

    public LabelVerticalColumnRel selectLabelVerticalColumnRelById(String labelId) throws BaseException {
        if(StringUtil.isBlank(labelId)){
            throw new ParamRequiredException("Id不能为空");
        }
        return super.get(labelId);
    }

    public void addLabelVerticalColumnRel(LabelVerticalColumnRel labelVerticalColumnRel) throws BaseException {
        super.saveOrUpdate(labelVerticalColumnRel);
    }

    public void modifyLabelVerticalColumnRel(LabelVerticalColumnRel labelVerticalColumnRel) throws BaseException {
        super.saveOrUpdate(labelVerticalColumnRel);
        
    }

    public void deleteLabelVerticalColumnRelById(String labelId) throws BaseException {
        if (selectLabelVerticalColumnRelById(labelId)==null){
            throw new ParamRequiredException("ID不存在");
        }
        super.delete(labelId);
        
    }
    
    /**
     * 
     * Description: 根据列id和标签id查找标签与纵表列关系数据
     *
     * @param columnId
     * @param labelId
     * @return
     */
    @Override
    public LabelVerticalColumnRel queryLabelVerticalCol(String columnId,String labelId){
        LabelVerticalColumnRelVo labelVerticalColumnRelVo = new LabelVerticalColumnRelVo();
        LabelVerticalColumnRelId labelVerticalColumnRelId = new LabelVerticalColumnRelId();
        labelVerticalColumnRelId.setColumnId(columnId);
        labelVerticalColumnRelId.setLabelId(labelId);
        labelVerticalColumnRelVo.setLabelVerticalColumnRelId(labelVerticalColumnRelId);
        List<LabelVerticalColumnRel> labelVerList = iLabelVerticalColumnRelDao.selectLabelVerticalColumnRelList(labelVerticalColumnRelVo);
        System.out.println("cdcsc");
        if (labelVerList !=null && labelVerList.size()>0) {
            return labelVerList.get(0);
        }
        return null;
    }
    

}
