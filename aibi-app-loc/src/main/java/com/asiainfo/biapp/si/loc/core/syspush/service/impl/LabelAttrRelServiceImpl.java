/*
 * @(#)LabelAttrRelServiceImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service.impl;

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
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.syspush.dao.ILabelAttrRelDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelAttrRelService;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelAttrRelVo;

/**
 * Title : LabelAttrRelServiceImpl
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
public class LabelAttrRelServiceImpl extends BaseServiceImpl<LabelAttrRel, String> implements ILabelAttrRelService{

    @Autowired
    private ILabelAttrRelDao iLabelAttrRelDao;
    
    @Autowired
    private ILabelAttrRelService iLabelAttrRelService;
    
    @Override
    protected BaseDao<LabelAttrRel, String> getBaseDao() {
        return iLabelAttrRelDao;
    }
    @Autowired
    private ILabelInfoService iLabelInfoService;
    
    public Page<LabelAttrRel> selectLabelAttrRelPageList(Page<LabelAttrRel> page, LabelAttrRelVo labelAttrRelVo) throws BaseException {
        return iLabelAttrRelDao.selectLabelAttrRelPageList(page, labelAttrRelVo);
    }

    public List<LabelAttrRel> selectLabelAttrRelList(LabelAttrRelVo labelAttrRelVo) throws BaseException {
        return iLabelAttrRelDao.selectLabelAttrRelList(labelAttrRelVo);
    }

    public LabelAttrRel selectLabelAttrRelById(String priKey) throws BaseException {
        if(StringUtil.isBlank(priKey)){
            throw new ParamRequiredException("主键为空");
        }
        return super.get(priKey);
    }

    public void addLabelAttrRel(LabelAttrRel labelAttrRel) throws BaseException {
    	super.saveOrUpdate(labelAttrRel);
    }
    
    public void addLabelAttrRelPreview(LabelAttrRel labelAttrRel,String userName) throws BaseException {
    	LabelAttrRelVo labelAttrRelVo = new LabelAttrRelVo();
    	labelAttrRelVo.setLabelId(labelAttrRel.getLabelId());
    	labelAttrRelVo.setStatus(0);
    	labelAttrRelVo.setAttrSettingType(3);
    	if(iLabelAttrRelService.selectLabelAttrRelList(labelAttrRelVo) != null){
    		List<LabelAttrRel> labelAttrRelList=iLabelAttrRelService.selectLabelAttrRelList(labelAttrRelVo);
    		for(int i=0;i<labelAttrRelList.size();i++){
    			LabelAttrRel labelAttrRel1 =labelAttrRelList.get(i);
    			labelAttrRel1.setStatus(1);
    			iLabelAttrRelService.modifyLabelAttrRel(labelAttrRel1);
    		}
    	}
    	String[] attrbuteIdList = labelAttrRel.getAttrbuteId().split(",");
        for(int i=0;i<attrbuteIdList.length;i++){
            if(!("").equals(attrbuteIdList[i])){
            	LabelAttrRel labelAttrRelPreview = new LabelAttrRel();
                LabelInfo labelInfo = iLabelInfoService.selectLabelInfoById(attrbuteIdList[i]);
                if(StringUtil.isNoneBlank(labelAttrRel.getSortAttrAndType())){
               	 String[] sortAttrAndTypeList = labelAttrRel.getSortAttrAndType().split(";");
               	 for(int j=0;j<sortAttrAndTypeList.length;j++){
                     	if(sortAttrAndTypeList[j].indexOf(labelInfo.getLabelName()) != -1){
                     		String attrSortType =sortAttrAndTypeList[j].split(",")[1];
                         	if(attrSortType.equals("升序")){
                         		labelAttrRelPreview.setSortType("asc");
                         	}else if(attrSortType.equals("降序")){
                         		labelAttrRelPreview.setSortType("desc");
                         	}
                         	labelAttrRelPreview.setSortNum(j+1);
                     	}
                    }
                }
                labelAttrRelPreview.setStatus(0);
                labelAttrRelPreview.setModifyTime(new Date());
                labelAttrRelPreview.setLabelId(labelAttrRel.getLabelId());	
                labelAttrRelPreview.setAttrColName(labelInfo.getLabelName());
                labelAttrRelPreview.setAttrSource(2);
                labelAttrRelPreview.setAttrSettingType(3);
                labelAttrRelPreview.setLabelOrCustomId(labelInfo.getLabelId());
                labelAttrRelPreview.setAttrColType(labelInfo.getLabelTypeId().toString());
                labelAttrRelPreview.setAttrCreateUserId(userName);
                labelAttrRelPreview.setPageSortNum(i+1);
                super.saveOrUpdate(labelAttrRelPreview);
            }
        }
    }

    public void modifyLabelAttrRel(LabelAttrRel labelAttrRel) throws BaseException {
        super.saveOrUpdate(labelAttrRel);
    }

    public void deleteLabelAttrRelById(String priKey) throws BaseException {
        if(StringUtil.isBlank(priKey)){
            throw new ParamRequiredException("主键为空");
        }
        super.delete(priKey);
    }
}
