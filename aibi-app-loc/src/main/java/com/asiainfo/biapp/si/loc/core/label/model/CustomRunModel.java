
package com.asiainfo.biapp.si.loc.core.label.model;

import java.io.Serializable;

/**
 * 
 * Title : CustomRunModel
 * <p/>
 * Description : 跑客户群所需要的信息
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 6.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2018年1月12日    tianxy3        Created
 * </pre>
 * <p/>
 *
 * @author tianxy3
 * @version 1.0.0.2018年1月12日
 */
public class CustomRunModel extends ExploreQueryParam implements Serializable {

	/**  */
	private static final long serialVersionUID = 1L;

	private String customGroupId;// 客户群id

	public String getCustomGroupId() {
		return customGroupId;
	}

	public void setCustomGroupId(String customGroupId) {
		this.customGroupId = customGroupId;
	}


}
