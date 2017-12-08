package com.asiainfo.biapp.si.loc.auth.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.auth.model.DicData;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;

/**
 * 
 * Title : 数据字典持久层
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
public interface IDicDataDao extends BaseDao<DicData, String>{
	
	/**
	 * 
	 * Description: 通过字典编码，查询数据字典集合
	 *
	 * @param code
	 * @return
	 * @throws BaseException
	 */
	public List<DicData> selectDataBycode(String code) throws BaseException;
	
	/**
	 * 
	 * Description: 通过字典编码，查询数据字典集合（分页）
	 *
	 * @param page
	 * @param dicCode
	 * @return
	 */
	public Page<DicData> findDicDataList(Page<DicData> page,String dicCode);

}
