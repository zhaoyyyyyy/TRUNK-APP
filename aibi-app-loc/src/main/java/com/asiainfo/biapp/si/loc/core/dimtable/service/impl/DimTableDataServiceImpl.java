/*
 * @(#)DimTableDataServiceImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.dimtable.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.exception.SqlRunException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.core.dimtable.dao.IDimTableDataDao;
import com.asiainfo.biapp.si.loc.core.dimtable.dao.IDimTableInfoDao;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableData;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableDataId;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;
import com.asiainfo.biapp.si.loc.core.dimtable.service.IDimTableDataService;
import com.asiainfo.biapp.si.loc.core.dimtable.vo.DimTableInfoVo;

/**
 * Title : DimTableDataServiceImpl
 * <p/>
 * Description : 维表数据表(前台库存储)服务类
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 8.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月3日    hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年1月3日
 */

@Service
@Transactional
public class DimTableDataServiceImpl extends BaseServiceImpl<DimTableData, DimTableDataId> implements IDimTableDataService{

    @Autowired
    private IDimTableDataDao iDimTableDataDao;

    @Autowired
    private IDimTableInfoDao iDimTableInfoDao;
    
    @Autowired
    private IBackSqlService backServiceImpl;
    
    //本类常量
    private static final String SQL_WHERE_L = "where";
    private static final String SQL_WHERE_U = "WHERE";
    private static final double PAGE_SIZE = 10000;
    private static final long THOUSAND = 1000L;    //千进制
    
    @Override
    protected BaseDao<DimTableData, DimTableDataId> getBaseDao() {
        return iDimTableDataDao;
    }
        
    public Page<DimTableData> selectDimTableDataPageList(Page<DimTableData> page, DimTableData dimTableData) throws BaseException {
        if(StringUtil.isBlank(dimTableData.getId().getDimTableName())){
            throw new ParamRequiredException("DimTableName不能为空");
        }
        
        return iDimTableDataDao.selectDimTableDataPageList(page, dimTableData);
    }

    public List<DimTableData> selectDimTableDataList(DimTableData dimTableData) throws BaseException {
        return iDimTableDataDao.selectDimTableDataList(dimTableData);
    }

    
    /**
     * @Description: dim_table的跑数入口，数据流是：DimTableInfo ——> DimTableData
     * @param String tableName 维表表名
     */
    public void dimTableInfo2Data(String ...tableNames) throws BaseException {
        LogUtil.info(this.getClass().getSimpleName()+".dimTableInfo2Data() start");
        long s = System.currentTimeMillis();

        String schema = backServiceImpl.getCurBackDbSchema();
        String sql = null;
        int num = 0;
        int forNum = 1; //读取次数
        if (null != tableNames && tableNames.length > 0) {  //指定同步表
            for (String tableName : tableNames) {
                //1.获取DimTableInfo数据
                DimTableInfoVo dimTableInfoVo = new DimTableInfoVo();
                if (StringUtil.isNoneBlank(tableName)) {
                    dimTableInfoVo.setDimTableName(tableName);
                } else {
                    continue;
                }
                List<DimTableInfo> dimTableInfos = iDimTableInfoDao.selectDimTableInfoList(dimTableInfoVo);
                if (null != dimTableInfos && !dimTableInfos.isEmpty()) {
                    for (DimTableInfo dimTableInfo : dimTableInfos) {
                        //获取sql，并查询，入库
                        if (null != dimTableInfo) {
                            sql = this.getSql(schema, dimTableInfo, new StringBuilder());
                            LogUtil.info("查询维表(" + dimTableInfo.getDimTableName() + "):" + sql);
                            try {
                                num = backServiceImpl.queryCount(sql);
                            } catch (SqlRunException e) {
                                //本维表注册的有问题，跳过
                                LogUtil.warn("查询维表(" + dimTableInfo.getDimTableName() + ")总条数出错！");
                                continue;
                            }
                            if (num > PAGE_SIZE) {
                                forNum = (int) Math.ceil(num / PAGE_SIZE);
                            }
                            this.dimTableInfo2Data(dimTableInfo, sql, forNum);
                        }
                    }
                } else {
                    LogUtil.info("维表("+tableName+")没有注册或没有数据。");
                    continue;
                }
            } 
        } else {
            LogUtil.info("本次维表跑数不指定表名，全量跑。");
            
            //1.获取DimTableInfo数据
            List<DimTableInfo> dimTableInfos = iDimTableInfoDao.selectDimTableInfoList(new DimTableInfoVo());
            if (null != dimTableInfos && !dimTableInfos.isEmpty()) {
                for (DimTableInfo dimTableInfo : dimTableInfos) {
                    //获取sql，并查询，入库
                    sql = this.getSql(schema, dimTableInfo, new StringBuilder());
                    LogUtil.info("查询维表(" + dimTableInfo.getDimTableName() + "):" + sql);
                    try {
                        num = backServiceImpl.queryCount(sql);
                    } catch (SqlRunException e) {
                        //本维表注册的有问题，跳过
                        LogUtil.warn("查询维表(" + dimTableInfo.getDimTableName() + ")总条数出错！");
                        continue;
                    }
                    if (num > PAGE_SIZE) {
                        forNum = (int) Math.ceil(num / PAGE_SIZE);
                    }
                    this.dimTableInfo2Data(dimTableInfo, sql, forNum);
                }
            }
        }
        
        LogUtil.info(this.getClass().getSimpleName()+".dimTableInfo2Data() end.cost:"+((System.currentTimeMillis()-s)/THOUSAND)+"s.");
    }
    
    /**
     * @description:根据维表信息拼接sql
     * @param dimTableInfo
     * @param sql
     * @return 拼接好的sql
     */
    private String getSql(String schema, DimTableInfo dimTableInfo, StringBuilder sql){
        if (null != dimTableInfo) {
        	sql.append("select ").append(dimTableInfo.getDimCodeCol())
            .append(", ").append(dimTableInfo.getDimValueCol()).append(" from ")
            .append(StringUtil.isNotBlank(schema)?schema+".":"")
            .append(dimTableInfo.getDimTableName()).append(" ");
               
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
    private void dimTableInfo2Data(DimTableInfo dimTableInfo, String sql, int forNum) throws BaseException {
        //循环读取，防止后台跑不动
        List<Map<String, String>> datas = null;
        for (int i = 0; i < forNum; i++) {
            try {
                datas = backServiceImpl.queryForPage(sql, i+1, (int)PAGE_SIZE);
            } catch (SqlRunException e) {
                //本维表的数据有问题，跳过
                LogUtil.warn("查询维表("+dimTableInfo.getDimTableName()+")数据出错！");
                continue;
            }
            if (null != datas && !datas.isEmpty()) {
                DimTableDataId id = null;
                DimTableData entity = null;
                DimTableData dimTableData = null;
                for (Map<String, String> map : datas) {
                    //入库
                    id = new DimTableDataId(dimTableInfo.getDimTableName(), map.get(dimTableInfo.getDimCodeCol()));
                    entity = new DimTableData(id, map.get(dimTableInfo.getDimValueCol()));
                    dimTableData = iDimTableDataDao.get(id);
                    if (null != dimTableData) {
                        if (!dimTableData.equals(entity)) {
                            iDimTableDataDao.update(entity);
                        }
                    } else {
                        iDimTableDataDao.save(entity);
                    }
                } 
            } else {
                LogUtil.info("维表("+dimTableInfo.getDimTableName()+")没有数据。");
            }
        }
    
    }
    
}
