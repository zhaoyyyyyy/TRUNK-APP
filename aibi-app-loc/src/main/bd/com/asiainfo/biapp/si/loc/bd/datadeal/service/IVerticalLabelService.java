package com.asiainfo.biapp.si.loc.bd.datadeal.service;

import com.asiainfo.biapp.si.loc.bd.datadeal.vo.BackParamVo;

/**
 * 
 * Title : VerticalLabelService
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.7 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年4月19日    Administrator        Created</pre>
 * <p/>
 *
 * @author  彭文杰
 * @version 1.0.0.2018年4月19日
 */
public interface IVerticalLabelService {
	
	/**
	 * 
	 * Description: 执行纵表数据处理
	 *
	 * @param backParamVo
	 */
    public void exeRun(BackParamVo backParamVo);
}
