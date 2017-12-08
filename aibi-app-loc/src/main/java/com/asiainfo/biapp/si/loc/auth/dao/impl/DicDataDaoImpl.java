package com.asiainfo.biapp.si.loc.auth.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.auth.dao.IDicDataDao;
import com.asiainfo.biapp.si.loc.auth.model.DicData;
import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;

/**
 * 
 * Title : 数据字典持久层实现类
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年11月5日    Administrator        Created</pre>
 * <p/>
 *
 * @author  zhougz3
 * @version 1.0.0.2017年11月5日
 */
@Repository
public class DicDataDaoImpl extends BaseDaoImpl<DicData, String> implements IDicDataDao{
	
	/**
	 * 
	 * {@inheritDoc}
	 * @see com.asiainfo.biapp.si.loc.auth.dao.IDicDataDao#selectDataBycode(java.lang.String)
	 */
	@Override
	public List<DicData> selectDataBycode(String code) throws BaseException {
		return this.findListByHql("from DicData b where b.dicCode = ?0 order by orderNum,code",code);
	}

	
	/**
	 * 
	 * {@inheritDoc}
	 * @see com.asiainfo.biapp.si.loc.auth.dao.IDicDataDao#findDicDataList(com.asiainfo.biapp.si.loc.base.page.Page, java.lang.String)
	 */
	@Override
	public Page<DicData> findDicDataList(Page<DicData> page, String dicCode) {
		Map<String,Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder("from DicData where 1=1 ");
		//拼装hql 及参数
		if(StringUtils.isNotBlank(dicCode)){
			hql.append(" and dicCode = :dicCode");
			params.put("dicCode", dicCode);
		}
		return (Page<DicData>) super.findPageByHql(page, hql.toString(), params);
	}

}
