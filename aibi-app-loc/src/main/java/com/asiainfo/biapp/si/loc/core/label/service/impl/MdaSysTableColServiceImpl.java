/*
 * @(#)MdaSysTableColServiceImpl.java
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
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;
import com.asiainfo.biapp.si.loc.core.dimtable.service.IDimTableInfoService;
import com.asiainfo.biapp.si.loc.core.label.dao.IMdaSysTableColumnDao;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelVerticalColumnRel;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelVerticalColumnRelId;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTable;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelVerticalColumnRelService;
import com.asiainfo.biapp.si.loc.core.label.service.IMdaSysTableColService;
import com.asiainfo.biapp.si.loc.core.label.service.IMdaSysTableService;
import com.asiainfo.biapp.si.loc.core.label.vo.MdaSysTableColumnVo;

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
    
    @Autowired
    private IMdaSysTableService iMdaSysTableService;
    
    @Autowired
    private ILabelInfoService iLabelInfoService;
    
    @Autowired 
    private IDimTableInfoService iDimTableInfoService;
    
    @Autowired
    private ILabelVerticalColumnRelService iLabelVerticalColumnRelService;

    @Override
    protected BaseDao<MdaSysTableColumn, String> getBaseDao() {
        return iMdaSysTableColumnDao;
    }

    @Override
    public Page<MdaSysTableColumn> selectMdaSysTableColPageList(Page<MdaSysTableColumn> page,
            MdaSysTableColumnVo mdaSysTableColumnVo) throws BaseException {
        return iMdaSysTableColumnDao.selectMdaSysTableColPageList(page, mdaSysTableColumnVo);
    }

    @Override
    public List<MdaSysTableColumn> selectMdaSysTableColList(MdaSysTableColumnVo mdaSysTableColumnVo)
            throws BaseException {
        return iMdaSysTableColumnDao.selectMdaSysTableColList(mdaSysTableColumnVo);
    }

    @Override
    public MdaSysTableColumn selectMdaSysTableColumnById(String columnId) throws BaseException {
        if (StringUtils.isBlank(columnId)) {
            throw new ParamRequiredException("Id不能为空");
        }
        return super.get(columnId);
    }

    @Override
    public void addMdaSysTableColumn(MdaSysTableColumn mdaSysTableColumn) throws BaseException {
        //保存标签与纵表列关系
        if (mdaSysTableColumn.getLabelTypeId()!=null) {
            if (StringUtil.isNotBlank(mdaSysTableColumn.getDimTransId())) {
                mdaSysTableColumn.setDimTransId(mdaSysTableColumn.getDimTransId());
                DimTableInfo dimTable = iDimTableInfoService.selectDimTableInfoById(mdaSysTableColumn.getDimTransId());
                int columnDataTypeId = Integer.parseInt(dimTable.getCodeColType());
                mdaSysTableColumn.setColumnDataTypeId(columnDataTypeId);
            }
            LabelInfo labelInfo = iLabelInfoService.get(mdaSysTableColumn.getLabelId());
            MdaSysTable mdaSysTable = iMdaSysTableService.queryMdaSysTable(labelInfo.getConfigId(), labelInfo.getUpdateCycle(), 3);
            mdaSysTableColumn.setTableId(mdaSysTable.getTableId());
            super.saveOrUpdate(mdaSysTableColumn);
            
            LabelVerticalColumnRel labelVerticalColumnRel = new LabelVerticalColumnRel();
            LabelVerticalColumnRelId labelVerticalColumnRelId = new LabelVerticalColumnRelId();
            labelVerticalColumnRelId.setLabelId(mdaSysTableColumn.getLabelId());
            labelVerticalColumnRelId.setColumnId(mdaSysTableColumn.getColumnId());  
            labelVerticalColumnRel.setLabelVerticalColumnRelId(labelVerticalColumnRelId);
            labelVerticalColumnRel.setIsMustColumn(mdaSysTableColumn.getIsMustColumn());
            labelVerticalColumnRel.setLabelTypeId(mdaSysTableColumn.getLabelTypeId());
            labelVerticalColumnRel.setSortNum(mdaSysTableColumn.getColumnNum());
            iLabelVerticalColumnRelService.addLabelVerticalColumnRel(labelVerticalColumnRel); 
        } else{
            super.saveOrUpdate(mdaSysTableColumn);
        }      
    }

    public void modifyMdaSysTableColumn(MdaSysTableColumn mdaSysTableColumn) throws BaseException {
        super.saveOrUpdate(mdaSysTableColumn);
        
        //修改标签与纵表列对应关系表
        LabelVerticalColumnRel labelVerticalColumnRel = iLabelVerticalColumnRelService.queryLabelVerticalCol(mdaSysTableColumn.getColumnId(), mdaSysTableColumn.getLabelId());
        labelVerticalColumnRel.setLabelTypeId(mdaSysTableColumn.getLabelTypeId());
        labelVerticalColumnRel.setIsMustColumn(mdaSysTableColumn.getIsMustColumn());
        iLabelVerticalColumnRelService.modifyLabelVerticalColumnRel(labelVerticalColumnRel);
    };

    @Override
    public void deleteMdaSysTableColumnById(String columnId) throws BaseException {
        if (selectMdaSysTableColumnById(columnId)==null){
            throw new ParamRequiredException("ID不存在");
        }
        super.delete(columnId);
    }

    @Override
    public MdaSysTableColumn selectMdaSysTableColBylabelId(String labelId) {
        return iMdaSysTableColumnDao.selectMdaSysTableColBylabelId(labelId);
    }
    
    @Override
    public List<MdaSysTableColumn> addMdaSysTableColList(List<MdaSysTableColumn> mdaSysTableColList){
        for (MdaSysTableColumn mdaSysTableColumn : mdaSysTableColList) {
            LabelInfo labelInfo = iLabelInfoService.get(mdaSysTableColumn.getLabelId());
            if (labelInfo.getLabelTypeId()==8) {
                LabelVerticalColumnRel labelVerticalColumnRel = iLabelVerticalColumnRelService.queryLabelVerticalCol(mdaSysTableColumn.getColumnId(), mdaSysTableColumn.getLabelId());
                mdaSysTableColumn.setLabelTypeId(labelVerticalColumnRel.getLabelTypeId());
                mdaSysTableColumn.setIsMustColumn(labelVerticalColumnRel.getIsMustColumn());
            }
        }
        return mdaSysTableColList;
    }
}
