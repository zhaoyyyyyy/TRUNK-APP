/*
 * @(#)DimTableInfo2DataServiceImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.bd.compute.dataprocess.dimtable.task.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.SqlRunException;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.bd.compute.dataprocess.dimtable.task.service.IDimTableInfo2DataService;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableData;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableDataId;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;
import com.asiainfo.biapp.si.loc.core.dimtable.service.IDimTableDataService;
import com.asiainfo.biapp.si.loc.core.dimtable.service.IDimTableInfoService;
import com.asiainfo.biapp.si.loc.core.dimtable.vo.DimTableInfoVo;

/**
 * Title : DimTableInfo2DataServiceImpl
 * <p/>
 * Description : 本接口是dim_table的跑数接口，数据流是：DimTableInfo ——> DimTableData
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 8.0 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月3日    hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年1月3日
 */

@Component
public class DimTableInfo2DataServiceImpl implements IDimTableInfo2DataService {
    
    @Autowired
    private IDimTableInfoService iDimTableInfoService;
    @Autowired
    private IDimTableDataService iDimTableDataService;
    
    @Autowired
    private IBackSqlService backServiceImpl;
    
    //本类常量
    private static final String SQL_WHERE_L = "where";
    private static final String SQL_WHERE_U = "WHERE";
    private static final int PAGE_SIZE = 10000;
    
    /**
     * @Description: dim_table的跑数入口，数据流是：DimTableInfo ——> DimTableData
     * @param String tableName 维表表名
     */
    public void dimTableInfo2Data(String tableName) throws BaseException {
        LogUtil.info("dimTableInfo2Data--------->>>>>>>begin");
        long s = System.currentTimeMillis();
        
        //1.获取DimTableInfo数据
        DimTableInfoVo dimTableInfoVo = new DimTableInfoVo();
        if (StringUtil.isNoneBlank(tableName)) {
            dimTableInfoVo.setDimTableName(tableName);
        } else {
            LogUtil.info("本次维表跑数不指定表名，全量跑。");
        }
        List<DimTableInfo> dimTableInfos = null;
        try {
            dimTableInfos = iDimTableInfoService.selectDimTableInfoList(dimTableInfoVo);
        } catch (BaseException e) {
            LogUtil.error("查询dimTableInfo出错", e);
        }
        if (null != dimTableInfos && !dimTableInfos.isEmpty()) {
            String sql = null;
            int num = 0;
            int forNum = 1; //读取次数
            List<Map<String, String>> datas = null;
            DimTableData entity = null;
            DimTableDataId id = null;
            DimTableData dimTableData = null;
//            IBackSqlService backDbService = (IBackSqlService) SpringContextHolder.getBean("backServiceImpl");
            for (DimTableInfo dimTableInfo : dimTableInfos) {
                //获取sql，并查询，入库
                sql = this.getSql(dimTableInfo, new StringBuilder());
                LogUtil.info("查询维表("+dimTableInfo.getDimTableName()+"):"+sql);
                try {
                    num = backServiceImpl.queryCount(sql);
                } catch (SqlRunException e) {
                    //本维表注册的有问题，跳过
                    LogUtil.warn("查询维表("+dimTableInfo.getDimTableName()+")总条数出错！");
                    continue;
                }
                if (num > PAGE_SIZE) {
                    forNum = Integer.parseInt(String.valueOf(Math.ceil(num / PAGE_SIZE)));
                }
                //循环读取，防止后台跑不动
                for (int i = 0; i < forNum; i++) {
                    try {
                        datas = backServiceImpl.queryForPage(sql.toString(), i+1, PAGE_SIZE);
                    } catch (SqlRunException e) {
                        //本维表的数据有问题，跳过
                        LogUtil.warn("查询维表("+dimTableInfo.getDimTableName()+")数据出错！");
                        continue;
                    }
                    if (null != datas && !datas.isEmpty()) {
                        for (Map<String, String> map : datas) {
                            //入库
                            id = new DimTableDataId(dimTableInfo.getDimTableName(), map.get(dimTableInfo.getDimCodeCol()));
                            entity = new DimTableData(id, map.get(dimTableInfo.getDimValueCol()));
//                            iDimTableDataService.saveOrUpdate(entity);
                            dimTableData = iDimTableDataService.get(id);
                            if (null != dimTableData) {
                                if (!dimTableData.equals(entity)) {
                                    iDimTableDataService.update(entity);
                                }
                            } else {
                                iDimTableDataService.save(entity);
                            }
                            
                        } 
                    } else {
                        LogUtil.info("维表("+dimTableInfo.getDimTableName()+")没有数据。");
                    }
                }
            }
        }
        
        LogUtil.info("dimTableInfo2Data--------->>>>>>>end.cost:"+((System.currentTimeMillis()-s)/1000L)+"s.");
    }
    
    private String getSql(DimTableInfo dimTableInfo, StringBuilder sql){
        if (null != dimTableInfo) {
            sql.append("select ").append(dimTableInfo.getDimCodeCol()).append(", ")
               .append(dimTableInfo.getDimValueCol()).append(" ")
               .append("from ").append(dimTableInfo.getDimTableName()).append(" ");
               
            if (StringUtil.isNoneBlank(dimTableInfo.getDimWhere())) {
                if (dimTableInfo.getDimWhere().contains(SQL_WHERE_L) || dimTableInfo.getDimWhere().contains(SQL_WHERE_U)) {
                    sql.append(dimTableInfo.getDimWhere());
                } else {
                    sql.append("where ").append(dimTableInfo.getDimWhere());
                } 
            } 
        }
        
        return sql.toString(); 
    }

	
}