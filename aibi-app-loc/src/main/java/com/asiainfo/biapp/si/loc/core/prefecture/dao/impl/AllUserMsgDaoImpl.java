/*
 * @(#)AllUserMsgDaoImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.prefecture.dao.IAllUserMsgDao;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.AllUserMsg;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.AllUserMsgVo;

/**
 * Title : AllUserMsgDaoImpl
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
 * <pre>1    2018年1月24日    zhangnan7        Created</pre>
 * <p/>
 *
 * @author  zhangnan7
 * @version 1.0.0.2018年1月24日
 */
@Repository
public class AllUserMsgDaoImpl extends BaseDaoImpl<AllUserMsg, String> implements IAllUserMsgDao {
    
    public Page<AllUserMsg> selectAllUserMsgPageList(Page<AllUserMsg> page, AllUserMsgVo allUserMsgVo){
        Map<String, Object> reMap = fromBean(allUserMsgVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    public List<AllUserMsg> selectAllUserMsgList(AllUserMsgVo allUserMsgVo){
        Map<String, Object> reMap = fromBean(allUserMsgVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }
    
    public Map<String, Object> fromBean(AllUserMsgVo allUserMsgVo) {
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from AllUserMsg a where 1=1 ");
        if (StringUtils.isNotBlank(allUserMsgVo.getTableDesc())) {
            hql.append("and a.tableDesc LIKE :tableDesc ");
            params.put("tableDesc","%"+ allUserMsgVo.getTableDesc() +"%");
        }
        if (StringUtils.isNotBlank(allUserMsgVo.getDayTableName())) {
            hql.append("and a.dayTableName LIKE :dayTableName ");
            params.put("dayTableName","%"+ allUserMsgVo.getDayTableName() +"%");
        }
        if (StringUtils.isNotBlank(allUserMsgVo.getMonthTableName())) {
            hql.append("and a.monthTableName LIKE :monthTableName ");
            params.put("monthTableName","%"+ allUserMsgVo.getMonthTableName() +"%");
        }
        if (StringUtils.isNotBlank(allUserMsgVo.getDayMainColumn())) {
            hql.append("and a.dayMainColumn LIKE :dayMainColumn ");
            params.put("dayMainColumn","%"+ allUserMsgVo.getDayMainColumn() +"%");
        }
        if (StringUtils.isNotBlank(allUserMsgVo.getMonthMainColumn())) {
            hql.append("and a.monthMainColumn LIKE :monthMainColumn ");
            params.put("monthMainColumn","%"+ allUserMsgVo.getMonthMainColumn() +"%");
        }
        if (StringUtils.isNotBlank(allUserMsgVo.getIsPartition())) {
            hql.append("and a.isPartition LIKE :isPartition ");
            params.put("isPartition","%"+ allUserMsgVo.getIsPartition() +"%");
        }
        if (StringUtils.isNotBlank(allUserMsgVo.getDayPartitionColumn())) {
            hql.append("and a.dayPartitionColumn LIKE :dayPartitionColumn ");
            params.put("dayPartitionColumn","%"+ allUserMsgVo.getDayPartitionColumn() +"%");
        }
        if (StringUtils.isNotBlank(allUserMsgVo.getMonthPartitionColumn())) {
            hql.append("and a.monthPartitionColumn LIKE :monthPartitionColumn ");
            params.put("monthPartitionColumn","%"+ allUserMsgVo.getMonthPartitionColumn() +"%");
        }
        if (StringUtils.isNotBlank(allUserMsgVo.getOtherColumn())) {
            hql.append("and a.ltherColumn LIKE :ltherColumn ");
            params.put("ltherColumn","%"+ allUserMsgVo.getOtherColumn() +"%");
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }

}
