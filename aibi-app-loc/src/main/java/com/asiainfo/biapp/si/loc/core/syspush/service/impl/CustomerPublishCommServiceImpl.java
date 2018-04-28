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
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.SqlRunException;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.cache.CocCacheAble;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;
import com.asiainfo.biapp.si.loc.core.dimtable.service.IDimTableInfoService;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTable;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelExploreService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
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
public class CustomerPublishCommServiceImpl implements ICustomerPublishCommService {
	
    public static final int CUSTOMLIST_PASE_SIZE = 10;

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
    

    private CocCacheAble cacheProxy = CocCacheProxy.getCacheProxy();


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
        StringBuilder sql = new StringBuilder("SELECT m.").append(ServiceConstants.KHQ_CROSS_COLUMN).append(" ");
        
        LabelInfo realyCustomInfo = null;
        if (null == customInfo || null == customInfo.getDataDate()) {
            realyCustomInfo = iLabelInfoService.get(customInfo.getLabelId());
        }else {
            realyCustomInfo = customInfo;
        }
        if (null != attrRelList && !attrRelList.isEmpty()) {    //有属性列
            sql = this.getHasAttrListSql(attrRelList, realyCustomInfo, mainMdaSysTable, mainMdaSysTableColumn, isPush, sql);
        } else {    //无属性列
            sql = this.getNotHasAttrListSql(realyCustomInfo, mainMdaSysTable, mainMdaSysTableColumn, isPush, sql);
        }
        
        return sql.toString();
    }

    /** 得到有属性的清单sql
     * @param attrRelList
     * @param realyCustomInfo
     * @param mainMdaSysTable
     * @param mainMdaSysTableColumn
     * @param isPush
     * @param sql
     * @return
     */
    private StringBuilder getHasAttrListSql(List<LabelAttrRel> attrRelList, LabelInfo realyCustomInfo,
            MdaSysTable mainMdaSysTable, MdaSysTableColumn mainMdaSysTableColumn, boolean isPush, StringBuilder sql) {
        //拼接列
        List<String> leftJoinEnumStrs = new ArrayList<>();
        String curBackDbSchema = null;
        try {
        	curBackDbSchema = iBackService.getCurBackDbSchema();
        } catch (SqlRunException e1) {
        	LogUtil.warn("获取后台库Schema错误："+ e1.getMessage());
        }
        //拼接sql的列
        this.getHasAttrListCols(attrRelList, leftJoinEnumStrs, curBackDbSchema, sql);
        
        //拼接sql的主表
        sql.append(" from (");
        sql.append("SELECT t.").append(ServiceConstants.KHQ_CROSS_COLUMN).append(" from ");
        if (null != mainMdaSysTableColumn) {
            if (StringUtil.isNotEmpty(mainMdaSysTable.getTableSchema())) {
                //拼接Schema
                sql.append(mainMdaSysTable.getTableSchema()).append(".");
            }
            sql.append(mainMdaSysTable.getTableName()).append(realyCustomInfo.getDataDate()).append(" t ")
               .append("where t.").append(ServiceConstants.KHQ_CROSS_ID_PARTION).append("='")
               .append(realyCustomInfo.getLabelId()).append("'");
        } else {
            String fromSql = "";
            try {
                fromSql = iLabelExploreService.getListTableSql(realyCustomInfo.getLabelId(), realyCustomInfo.getDataDate());
            } catch (BaseException e) {
                LogUtil.error("获取客户群清单sq出错：", e);;
            }
            sql.append(fromSql.replace(" where ", " t where t."));
        }
        if (!isPush) {	//清单预览
            int customListNo = CUSTOMLIST_PASE_SIZE;//尹振华说只查10条,@2018-03-13 14:39:36
            String customListNoStr = cacheProxy.getSYSConfigInfoByKey("LOC_CONFIG_APP_CUSTOM_LIST_NUMBER");
            if (StringUtil.isNotEmpty(customListNoStr)) {
            		customListNo = Integer.parseInt(customListNoStr);
            }
            sql.append(" limit ").append(customListNo);
        }
        sql.append(") m ");

        //拼接left join
        Map<String, String> tabelNameReplace = this.getHasAttrListLeftjoinSql(attrRelList, leftJoinEnumStrs, sql);
        
        //拼接orderby sql
        this.getHasAttrListOrderBySql(attrRelList, sql);
        
        //修正别名
        String sqlTmp = sql.toString();
        if (sqlTmp.contains("LEFT JOIN")) {
        	int e = sqlTmp.indexOf("from");
        	String attrsStr = sqlTmp.substring(0, e);
        	String otherStr = sqlTmp.substring(e, sqlTmp.length());
            for (Entry<String,String> entry : tabelNameReplace.entrySet()) {
        		attrsStr = attrsStr.replace(entry.getKey(), entry.getValue());
        		otherStr = otherStr.replace(entry.getKey(), entry.getValue());
        	}
        		sql = new StringBuilder(attrsStr).append(otherStr);
        }
        return sql;
    }

    /** 得到有属性的清单的链接sql
     * @param attrRelList
     * @param leftJoinEnumStrs
     * @param sql
     * @return
     */
    private Map<String, String> getHasAttrListLeftjoinSql(List<LabelAttrRel> attrRelList, List<String> leftJoinEnumStrs,
            StringBuilder sql) {
        LabelAttrRel labelAttrRel;
        Map<String,String> tabelNameSql = new HashMap<>();	
        Map<String,String> tabelNameReplace = new HashMap<>();//key:要替换的别名，value：真正可用的别名
        for (int i = 0; i < attrRelList.size(); i++) {
            labelAttrRel = attrRelList.get(i);
            sql.append(getJoinSqlByLabelId(labelAttrRel.getLabelOrCustomId(),labelAttrRel.getAttrCol(),sql,tabelNameSql, tabelNameReplace));
        }
        //拼接枚举型 leftJoin
        for (String leftJoinEnumStr : leftJoinEnumStrs) {
        		sql.append(leftJoinEnumStr);
        }
        return tabelNameReplace;
    }

    /** 得到有属性的清单的排序sql
     * @param attrRelList
     * @param sql
     */
    private void getHasAttrListOrderBySql(List<LabelAttrRel> attrRelList, StringBuilder sql) {
        MdaSysTableColumn mdaSysTableCol;
        LabelAttrRel labelAttrRel;
        LabelInfo label;
        //拼接orderby
        String sortType = "ASC";
        StringBuilder orderbyStr = new StringBuilder();
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
            orderbyStr.append("m.").append(ServiceConstants.KHQ_CROSS_COLUMN);
        }
        sql.append(" order by ").append(orderbyStr);
    }

    /** 得到有属性的清单的列sql
     * @param attrRelList
     * @param leftJoinEnumStrs
     * @param curBackDbSchema
     * @param sql
     */
    private void getHasAttrListCols(List<LabelAttrRel> attrRelList, List<String> leftJoinEnumStrs,
            String curBackDbSchema, StringBuilder sql) {
        MdaSysTableColumn mdaSysTableCol;
        LabelAttrRel labelAttrRel;
        LabelInfo label;
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
        			DimTableInfo dimTableInfo = this.getDimTableInfoByLabelId(label.getLabelId());
        			if (null != dimTableInfo) {
                        sql.append(",").append(labelAttrRel.getAttrCol()).append("_m.").append(dimTableInfo.getDimValueCol())
                           .append(" as ").append(labelAttrRel.getAttrCol());	//列别名
//		                    left join coctest.dim_loc_sex attr_col2_m on attr_col2_t.L0000034=attr_col2_m.sex_name
                        leftJoinEnumStrs.add(new StringBuilder().append("LEFT JOIN ")
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
    }

    /** 得到没有属性的清单sql
     * @param realyCustomInfo
     * @param mainMdaSysTable
     * @param mainMdaSysTableColumn
     * @param isPush
     * @param sql
     */
    private StringBuilder getNotHasAttrListSql(LabelInfo realyCustomInfo, MdaSysTable mainMdaSysTable,
            MdaSysTableColumn mainMdaSysTableColumn, boolean isPush, StringBuilder sql) {
        //拼接主表
        	sql.append(" from ");
        if (null != mainMdaSysTableColumn) {
            //拼接主表名
            if (StringUtil.isNotEmpty(mainMdaSysTable.getTableSchema())) {
                //拼接Schema
                sql.append(mainMdaSysTable.getTableSchema()).append(".");
            }
            sql.append(mainMdaSysTable.getTableName()).append(realyCustomInfo.getDataDate()).append(" m ")
               .append("where m.").append(ServiceConstants.KHQ_CROSS_ID_PARTION).append("='")
               .append(realyCustomInfo.getLabelId()).append("'");
        } else {
            String fromSql = "";
            try {
                fromSql = iLabelExploreService.getListTableSql(realyCustomInfo.getLabelId(), realyCustomInfo.getDataDate());
            } catch (BaseException e) {
                LogUtil.error("获取客户群清单sq出错：", e);;
            }
            sql.append(fromSql.replace(" where ", " m where m."));
        }
        if (!isPush) {	//清单预览
            int customListNo = CUSTOMLIST_PASE_SIZE;//尹振华说只查10条,@2018-03-13 14:39:36
            String customListNoStr = cacheProxy.getSYSConfigInfoByKey("LOC_CONFIG_APP_CUSTOM_LIST_NUMBER");
            if (StringUtil.isNotEmpty(customListNoStr)) {
            		customListNo = Integer.parseInt(customListNoStr);
            }
            sql.append(" limit ").append(customListNo);
        }
        return sql;
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
    private String getJoinSqlByLabelId(String labelId,String attrCol,
    		final StringBuilder mSql,Map<String,String> tabelName, Map<String,String> tabelNameReplace) {
        StringBuilder sql = new StringBuilder();
        //获取表名
        LabelInfo realyLabel = cacheProxy.getLabelInfoById(labelId); //datedate is null,so it's error
        if (null == realyLabel || (null!=realyLabel && null==realyLabel.getDataDate())) {
            realyLabel = iLabelInfoService.get(labelId);
        }
        MdaSysTableColumn mdaSysTableColumn = realyLabel.getMdaSysTableColumn();
        if (null != mdaSysTableColumn) {
            MdaSysTable mdaSysTable = mdaSysTableColumn.getMdaSysTable();
            if (!tabelName.containsKey(mdaSysTable.getTableName())) {
                sql.append("LEFT JOIN ");
                //拼接表名 Schema
                if (StringUtil.isNoneBlank(mdaSysTable.getTableSchema())) {
                    sql.append(mdaSysTable.getTableSchema()).append(".");
                }
                sql.append(mdaSysTable.getTableName()).append(realyLabel.getDataDate()).append(" ").append(attrCol)
                   .append("_t on ").append("m.").append(ServiceConstants.KHQ_CROSS_COLUMN).append("=")
                   .append(attrCol).append("_t.").append(ServiceConstants.KHQ_CROSS_COLUMN).append(" ");
                tabelName.put(mdaSysTable.getTableName(), attrCol+"_t");
            } else {
            		String[] leftJoinArr = mSql.toString().split("LEFT JOIN ");
            		//查找上次有效的别名
            		String oldAlias=null;
            		for (int i = leftJoinArr.length-1; i > 0; i--) {
            			if (!leftJoinArr[i].contains("_m") && leftJoinArr[i].contains("_t")) {
            				oldAlias = leftJoinArr[i].split("=")[1].split("\\.")[0];
            			}
            		}
            		tabelNameReplace.put(attrCol+"_t", oldAlias);
            }
        } else {
            sql = new StringBuilder("");
            LogUtil.error("标签【"+labelId+"】的 mdaSysTableColumn is null！");
        }
        
        return sql.toString();
    }
    
    @Override
    public DimTableInfo getDimTableInfoByLabelId(String LabelId) {
		StringBuilder sql = new StringBuilder("select ");
		/**
		 * 获取维表信息sql:
		 * SELECT DIM_TABLENAME,DIM_CODE_COL,DIM_VALUE_COL  from coctest.loc_dimtable_info
		 * left JOIN loc_mda_sys_table_column b on b.DIM_TRANS_ID=DIM_ID 
		 * where  b.LABEL_ID='L0000034';
		 */
		sql.append("DIM_TABLENAME,DIM_CODE_COL,DIM_VALUE_COL from LOC_MDA_SYS_TABLE_COLUMN d ");
		sql.append("INNER JOIN LOC_DIMTABLE_INFO b on b.DIM_ID=d.DIM_TRANS_ID ");
		sql.append("where d.LABEL_ID='"+LabelId+"'");
		
		LogUtil.info("获取维表信息sql:"+sql.toString());
		
        @SuppressWarnings("unchecked")
        List<Object[]> dimTableInfoList = (List<Object[]>) iDimTableInfoService.findListBySql(sql.toString(), new HashMap<>());
        if (!dimTableInfoList.isEmpty()) {
        		Object[] dimTableInfo = dimTableInfoList.get(0);
        		return new DimTableInfo(String.valueOf(dimTableInfo[0]),String.valueOf(dimTableInfo[1]),String.valueOf(dimTableInfo[2]));
		}
        
		return null;
    }
    
}
