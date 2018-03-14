/*
 * @(#)CustomerPublishCommImplService.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.SqlRunException;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.cache.CocCacheAble;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;
import com.asiainfo.biapp.si.loc.core.dimtable.service.IDimTableInfoService;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTable;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelExploreService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.syspush.common.constant.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.service.ICustomerPublishCommService;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelAttrRelService;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelAttrRelVo;

/**
 * Title : CustomerPublishCommImplService
 * <p/>
 * Description : <pre>客户群推送公用接口类.</pre>
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.7 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date         Modified By    Why & What is modified</pre>
 * <pre>1    2018年3月5日     hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年3月5日
 */

@Service
public class CustomerPublishCommImplService implements ICustomerPublishCommService {
	

    @Autowired
    private ILabelInfoService iLabelInfoService;
    @Autowired
    private ILabelExploreService iLabelExploreService;
    
    @Autowired
    private ILabelAttrRelService iLabelAttrRelService;
    
    @Autowired
    private IDimTableInfoService iDimTableInfoService;
    
    @Autowired
    private IBackSqlService iBackService;


    @Override
    public List<LabelAttrRel> getLabelAttrRelsByCustom(LabelInfo customInfo, int attrSettingType) {
        //获取属性列
        List<LabelAttrRel> attrRelList = null;
        
        LabelAttrRelVo labelAttrRelVo = new LabelAttrRelVo();
        labelAttrRelVo.setLabelId(customInfo.getLabelId());
        labelAttrRelVo.setAttrSource(ServiceConstants.LabelAttrRel.ATTR_SOURCE_LABEL);
        labelAttrRelVo.setAttrSettingType(attrSettingType);
        labelAttrRelVo.setStatus(ServiceConstants.LabelAttrRel.STATUS_SUCCESS);
        labelAttrRelVo.setOrderBy("pageSortNum ASC,sortNum ASC");
        try {
            attrRelList = iLabelAttrRelService.selectLabelAttrRelList(labelAttrRelVo);
        } catch (Exception e) {
            LogUtil.error("查询客户群关联的属性错误！", e);
        }
        
        return attrRelList;
    }
    
    @Override
	public String getCustomListSql(LabelInfo customInfo, List<LabelAttrRel> attrRelList, boolean isPush) {
        //获取主表表名
        MdaSysTableColumn mainMdaSysTableColumn = customInfo.getMdaSysTableColumn();
		MdaSysTable mainMdaSysTable = null!=mainMdaSysTableColumn ? mainMdaSysTableColumn.getMdaSysTable() : null;
        //拼装sql
        StringBuilder sql = new StringBuilder("SELECT m.").append(LabelInfoContants.KHQ_CROSS_COLUMN).append(" ");

        if (null == customInfo || null == customInfo.getDataDate()) {
            customInfo = iLabelInfoService.get(customInfo.getLabelId());
        }
        if (null != attrRelList && !attrRelList.isEmpty()) {    //有属性列
            CocCacheAble cacheProxy = CocCacheProxy.getCacheProxy();
            //拼接列
            MdaSysTableColumn mdaSysTableCol = null;
            LabelAttrRel labelAttrRel = null;
            LabelInfo label = null;
            List<String> leftJoinEnumStrs = new ArrayList<>();
            String curBackDbSchema = null;
            try {
				curBackDbSchema = iBackService.getCurBackDbSchema();
			} catch (SqlRunException e1) {
				LogUtil.warn("获取后台库Schema错误："+ e1.getMessage());
			}
            for (int i = 0; i < attrRelList.size(); i++) {
                labelAttrRel = attrRelList.get(i);
                //获取列信息
                label = cacheProxy.getLabelInfoById(labelAttrRel.getLabelOrCustomId()); //datedate is null,so it's error
                if (null == label || (null!=label && null==label.getDataDate())) {
                    label = iLabelInfoService.get(labelAttrRel.getLabelOrCustomId());
                }
                mdaSysTableCol = label.getMdaSysTableColumn();
                if (null != mdaSysTableCol) {
                    //翻译维表值
					switch (label.getLabelTypeId()) {
					case ServiceConstants.LabelInfo.LABEL_TYPE_ID_SIGN:	//1=标志型，case when
//						case attr_col1_t.L0000031 when 1 then '是' else '否' end as attr_col1,
						//表别名
	                    sql.append(",case ").append(labelAttrRel.getAttrCol()).append("_t.").append(mdaSysTableCol.getColumnName())
	                       .append(" when 1 then '是' else '否' end as ").append(labelAttrRel.getAttrCol());	//列别名
						break;
					case ServiceConstants.LabelInfo.LABEL_TYPE_ID_ENUM:	//5=枚举型,关联维表
						DimTableInfo dimTableInfo = this.getDimTableInfoByLabelId(customInfo.getLabelId());
						if (null != dimTableInfo) {
		                    sql.append(",").append(labelAttrRel.getAttrCol()).append("_m.").append(dimTableInfo.getDimValueCol())
		                       .append(" as ").append(labelAttrRel.getAttrCol());	//列别名
//		                    left join coctest.dim_loc_sex attr_col2_m on attr_col2_t.L0000034=attr_col2_m.sex_name
		                    leftJoinEnumStrs.add(new StringBuffer().append("LEFT JOIN ")
		                    		.append(StringUtil.isEmpty(curBackDbSchema)?"":curBackDbSchema+".").append(dimTableInfo.getDimTableName()).append(" ")
		                    		.append(labelAttrRel.getAttrCol()).append("_m on ").append(labelAttrRel.getAttrCol()).append("_t.")
		                    		.append(mdaSysTableCol.getColumnName()).append("=").append(labelAttrRel.getAttrCol()).append("_m.")
		                    		.append(dimTableInfo.getDimCodeCol()).append(" ").toString());
						}
                       break;
					default:
                			//表别名
	                    sql.append(",").append(labelAttrRel.getAttrCol()).append("_t.").append(mdaSysTableCol.getColumnName())
	                       .append(" as ").append(labelAttrRel.getAttrCol());	//列别名
						break;
					}
                } else {
                    LogUtil.error("MdaSysTableColumn of labelinfo【"+label.getLabelId()+"】 is null!");
                }
            }
            sql.append(" from (");
            
            //拼接主表
            sql.append("SELECT t.").append(LabelInfoContants.KHQ_CROSS_COLUMN).append(" from ");
            if (null != mainMdaSysTableColumn) {
                if (StringUtil.isNotEmpty(mainMdaSysTable.getTableSchema())) {
                    //拼接Schema
                    sql.append(mainMdaSysTable.getTableSchema()).append(".");
                }
                sql.append(mainMdaSysTable.getTableName()).append(customInfo.getDataDate()).append(" t ")
                   .append("where t.").append(LabelInfoContants.KHQ_CROSS_ID_PARTION).append("='")
                   .append(customInfo.getLabelId()).append("'");
            } else {
                String fromSql = "";
                try {
                    fromSql = iLabelExploreService.getListTableSql(customInfo.getLabelId(), customInfo.getDataDate());
                } catch (BaseException e) {
                    LogUtil.error("获取客户群清单sq出错：", e);;
                }
                sql.append(fromSql.replace(" where ", " t where t."));
            }
            sql.append(") m ");

            //拼接left join
            Map<String,String> tabelName = new HashMap<>();
            for (int i = 0; i < attrRelList.size(); i++) {
                labelAttrRel = attrRelList.get(i);
                sql.append(getJoinSqlByLabelId(labelAttrRel.getLabelOrCustomId(),labelAttrRel.getAttrCol(),cacheProxy,null,null,tabelName));
            }
            //拼接枚举型 leftJoin
            for (String leftJoinEnumStr : leftJoinEnumStrs) {
            		sql.append(leftJoinEnumStr);
			}
            //拼接orderby
            String sortType = "ASC";
            StringBuffer orderbyStr = new StringBuffer();
            for (int i = 0; i < attrRelList.size(); i++) {
                labelAttrRel = attrRelList.get(i);
                if (StringUtil.isNoneBlank(labelAttrRel.getSortType())) {
                    sortType = labelAttrRel.getSortType();
                    //获取列信息
                    label = cacheProxy.getLabelInfoById(labelAttrRel.getLabelOrCustomId()); //datedate is null,so it's error
                    if (null == label || (null!=label && null==label.getDataDate())) {
                        label = iLabelInfoService.get(labelAttrRel.getLabelOrCustomId());
                    }
                    mdaSysTableCol = label.getMdaSysTableColumn();
                    if (null != mdaSysTableCol) {
                    		orderbyStr.append(labelAttrRel.getAttrCol()).append("_t.").append(mdaSysTableCol.getColumnName())
                           .append(" ").append(sortType).append(",");
                    } else {
                        LogUtil.error("MdaSysTableColumn of labelinfo【"+label.getLabelId()+"】 is null!");
                    }
                }
            }
            if (StringUtil.isNotEmpty(orderbyStr)) {
            		orderbyStr.delete(orderbyStr.length()-1, orderbyStr.length());
            } else {
            		orderbyStr.append(LabelInfoContants.KHQ_CROSS_COLUMN);
            }
            sql.append(" order by ").append(orderbyStr);
            
            //纠正别名，所有属性都在同一张宽表
			String sqlTmp = sql.toString();
			if (sqlTmp.contains("LEFT JOIN")) {
				int e = sqlTmp.indexOf("from");
				String attrsStr = sqlTmp.substring(0, e);
				String otherStr = sqlTmp.substring(e, sqlTmp.length());
				int attrNo = attrsStr.split(",").length;
				for (int i = 2; i <= attrNo; i++) {
		    			attrsStr = attrsStr.replace("attr_col"+i+"_t", "attr_col1_t");
		    			otherStr = otherStr.replace("attr_col"+i+"_t", "attr_col1_t");
		    		}
		    		sql = new StringBuilder(attrsStr).append(otherStr);
		    }
        } else {    //无属性列
            //拼接主表
	        	sql.append(" from ");
            if (null != mainMdaSysTableColumn) {
                //拼接主表名
                if (StringUtil.isNotEmpty(mainMdaSysTable.getTableSchema())) {
                    //拼接Schema
                    sql.append(mainMdaSysTable.getTableSchema()).append(".");
                }
                sql.append(mainMdaSysTable.getTableName()).append(customInfo.getDataDate()).append(" m ")
                   .append("where m.").append(LabelInfoContants.KHQ_CROSS_ID_PARTION).append("='")
                   .append(customInfo.getLabelId()).append("'");
            } else {
                String fromSql = "";
                try {
                    fromSql = iLabelExploreService.getListTableSql(customInfo.getLabelId(), customInfo.getDataDate());
                } catch (BaseException e) {
                    LogUtil.error("获取客户群清单sq出错：", e);;
                }
                sql.append(fromSql.replace(" where ", " m where m."));
            }
        }
        
        if (!isPush) {
            int customListNo = 10;//尹振华说只查10条,@2018-03-13 14:39:36
            String customListNoStr = CocCacheProxy.getCacheProxy().getSYSConfigInfoByKey("CUSTOM_LIST_NUMBER");
            if (StringUtil.isNotEmpty(customListNoStr)) {
            		customListNo = Integer.parseInt(customListNoStr);
            }
            sql.append(" limit ").append(customListNo);
        }
        
        return sql.toString();
    }
	
    /**
     * 根据标签id获取left join sql,形如：<br/>
     * 	left join dw_A_111_20180301 t on maintable.product_no=t.product_no 
     * @param labelId	String	属性标签id
     * @param attrCol		String		别名
     * @param cacheProxy CocCacheAble	循环调用同一参数
     * @param label	LabelInfo	循环调用同一参数
     * @param mdaSysTableColumn	MdaSysTableColumn	循环调用同一参数
     * @param List<String>	MdaSysTableColumn	循环调用同一参数
     * @return
     */
    private String getJoinSqlByLabelId(String labelId,String attrCol,CocCacheAble cacheProxy,LabelInfo label,
    		MdaSysTableColumn mdaSysTableColumn, Map<String,String> tabelName) {
        StringBuffer sql = new StringBuffer();
        //获取表名
        label = cacheProxy.getLabelInfoById(labelId); //datedate is null,so it's error
        if (null == label || (null!=label && null==label.getDataDate())) {
            label = iLabelInfoService.get(labelId);
        }
        mdaSysTableColumn = label.getMdaSysTableColumn();
        if (null != mdaSysTableColumn) {
            MdaSysTable mdaSysTable = mdaSysTableColumn.getMdaSysTable();
            if (!tabelName.containsKey(mdaSysTable.getTableName())) {
                sql.append("LEFT JOIN ");
                //拼接表名 Schema
                if (StringUtil.isNoneBlank(mdaSysTable.getTableSchema())) {
                    sql.append(mdaSysTable.getTableSchema()).append(".");
                }
                sql.append(mdaSysTable.getTableName()).append(label.getDataDate()).append(" ").append(attrCol)
                   .append("_t on ").append("m.").append(LabelInfoContants.KHQ_CROSS_COLUMN).append("=")
                   .append(attrCol).append("_t.").append(LabelInfoContants.KHQ_CROSS_COLUMN).append(" ");
                tabelName.put(mdaSysTable.getTableName(), attrCol);
            }
        } else {
            sql = new StringBuffer("");
            LogUtil.error("标签【"+labelId+"】的 mdaSysTableColumn is null！");
        }
        
        return sql.toString();
    }
    
    public DimTableInfo getDimTableInfoByLabelId(String LabelId) {
		StringBuffer sql = new StringBuffer("select ");
		/**
		 * 获取维表信息sql:
		 * SELECT DIM_TABLENAME,DIM_CODE_COL,DIM_VALUE_COL  from coctest.loc_dimtable_info
		 * left JOIN loc_mda_sys_table_column b on b.DIM_TRANS_ID=DIM_ID 
		 * where  b.LABEL_ID='L0000034';
		 */
		sql.append("DIM_TABLENAME,DIM_CODE_COL,DIM_VALUE_COL from LOC_DIMTABLE_INFO d ");
		sql.append("LEFT JOIN LOC_MDA_SYS_TABLE_COLUMN b on b.DIM_TRANS_ID=d.DIM_ID ");
		sql.append("and b.LABEL_ID='"+LabelId+"'");
		
		LogUtil.debug(sql.toString());
		
        List<Object[]> dimTableInfoList = (List<Object[]>) iDimTableInfoService.findListBySql(sql.toString(), new HashMap<>());
        if (!dimTableInfoList.isEmpty()) {
        		Object[] dimTableInfo = dimTableInfoList.get(0);
        		return new DimTableInfo(String.valueOf(dimTableInfo[0]),String.valueOf(dimTableInfo[1]),String.valueOf(dimTableInfo[2]));
		}
        
		return null;
    }
		
    
}
