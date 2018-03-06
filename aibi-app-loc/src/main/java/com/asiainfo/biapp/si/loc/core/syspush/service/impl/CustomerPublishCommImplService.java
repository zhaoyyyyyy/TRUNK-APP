/*
 * @(#)CustomerPublishCommImplService.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.cache.CocCacheAble;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTable;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelExploreService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.service.ICustomerPublishCommService;

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
    
    
	public String getCustomListSql(LabelInfo customInfo, List<LabelAttrRel> attrRelList) {
        //获取主表表名
        MdaSysTableColumn mdaSysTableColumn = customInfo.getMdaSysTableColumn();
		MdaSysTable mainMdaSysTable = null!=mdaSysTableColumn ? mdaSysTableColumn.getMdaSysTable() : null;
        //拼装sql
        StringBuilder sql = new StringBuilder("SELECT m.").append(LabelInfoContants.KHQ_CROSS_COLUMN).append(" ");

        String fromSql = "";
        if (null != attrRelList && !attrRelList.isEmpty()) {    //有属性列
            //拼接列
            LabelAttrRel labelAttrRel = null;
            for (int i = 0; i < attrRelList.size(); i++) {
                labelAttrRel = attrRelList.get(i);
                sql.append(",").append("attr_").append(i).append(".").append(labelAttrRel.getAttrCol());
            }
            sql.append(" from (");
            
            //拼接主表
            if (null != mdaSysTableColumn) {
                sql.append("SELECT t.").append(LabelInfoContants.KHQ_CROSS_COLUMN).append(" from ");
                if (StringUtil.isNotEmpty(mainMdaSysTable.getTableSchema())) {
                    //拼接Schema
                    sql.append(mainMdaSysTable.getTableSchema()).append(".");
                }
                sql.append(mainMdaSysTable.getTableName()).append(customInfo.getDataDate()).append(" t ")
                   .append("where t.").append(LabelInfoContants.KHQ_CROSS_ID_PARTION).append("='")
                   .append(customInfo.getLabelId()).append("'");
            } else {
                try {
                    fromSql = iLabelExploreService.getListTableSql(customInfo.getLabelId(), customInfo.getDataDate());
                } catch (BaseException e) {
                    LogUtil.error("获取客户群清单sq出错：", e);;
                }
                sql.append(fromSql);
            }
            sql.append(") m ");

            //拼接left join
            CocCacheAble cacheProxy = CocCacheProxy.getCacheProxy();
            for (int i = 0; i < attrRelList.size(); i++) {
                labelAttrRel = attrRelList.get(i);
                sql.append(this.getJoinSqlByLabelId(labelAttrRel.getLabelOrCustomId(), i, cacheProxy, null, null));
            }
            //拼接orderby
            sql.append("order by ");
            String sortType = "ASC";
            for (int i = 0; i < attrRelList.size(); i++) {
                labelAttrRel = attrRelList.get(i);
                if (StringUtil.isNoneBlank(labelAttrRel.getSortType())) {
                    sortType = labelAttrRel.getSortType();
                }
                sql.append("attr_").append(i).append(".").append(labelAttrRel.getAttrCol())
                   .append(" ").append(sortType).append(",");
            }
            sql.delete(sql.length()-1, sql.length());
        } else {    //无属性列
            //拼接主表
	        	sql.append(" from ");
            if (null != mdaSysTableColumn) {
                //拼接主表名
                if (StringUtil.isNotEmpty(mainMdaSysTable.getTableSchema())) {
                    //拼接Schema
                    sql.append(mainMdaSysTable.getTableSchema()).append(".");
                }
                sql.append(mainMdaSysTable.getTableName()).append(customInfo.getDataDate()).append(" m ")
                   .append("where m.").append(LabelInfoContants.KHQ_CROSS_ID_PARTION).append("='")
                   .append(customInfo.getLabelId()).append("'");
            } else {
                try {
                    fromSql = iLabelExploreService.getListTableSql(customInfo.getLabelId(), customInfo.getDataDate());
                } catch (BaseException e) {
                    LogUtil.error("获取客户群清单sq出错：", e);;
                }
                sql.append(fromSql.replace(" where ", " m where m."));
            }
        }
        
        return sql.toString();
    }
	
    /**
     * 根据标签id获取left join sql,形如：<br/>
     * 	left join dw_A_111_20180301 t on maintable.product_no=t.product_no 
     * @param labelId	String	属性标签id
     * @param i		int		别名后缀
     * @param cacheProxy CocCacheAble	循环调用同一参数
     * @param label	LabelInfo	循环调用同一参数
     * @param mdaSysTableColumn	MdaSysTableColumn	循环调用同一参数
     * @return
     */
    private String getJoinSqlByLabelId(String labelId,int i,CocCacheAble cacheProxy,LabelInfo label,
    		MdaSysTableColumn mdaSysTableColumn) {
        StringBuffer sql = new StringBuffer("LEFT JOIN ");
        //获取表名
//        label = cacheProxy.getLabelInfoById(labelId);
//        if (null == label) {
            label = iLabelInfoService.get(labelId);
//        }
        mdaSysTableColumn = label.getMdaSysTableColumn();
        if (null != mdaSysTableColumn) {
            MdaSysTable mdaSysTable = mdaSysTableColumn.getMdaSysTable();
            //拼接表名 Schema
            if (StringUtil.isNoneBlank(mdaSysTable.getTableSchema())) {
                sql.append(mdaSysTable.getTableSchema()).append(".");
            }
            sql.append(mdaSysTable.getTableName()).append(label.getDataDate()).append(" attr_").append(i)
               .append(" on ").append("m.").append(LabelInfoContants.KHQ_CROSS_COLUMN).append("=").append("attr_")
               .append(i).append(".").append(LabelInfoContants.KHQ_CROSS_COLUMN).append(" ");
        } else {
            sql = new StringBuffer("");
            LogUtil.error("标签【"+labelId+"】的 mdaSysTableColumn is null！");
        }
        
        return sql.toString();
    }
	
    
}
