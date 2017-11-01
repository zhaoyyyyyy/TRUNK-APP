
package com.asiainfo.biapp.si.coc.jauth.log.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;

/**
 * 
 * Title : LogOperDetail
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年10月20日    panweiwei        Created</pre>
 * <p/>
 *
 * @author  panweiwei
 * @version 1.0.0.2017年10月20日
 */
@Entity
public class LogOperationDetail extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
