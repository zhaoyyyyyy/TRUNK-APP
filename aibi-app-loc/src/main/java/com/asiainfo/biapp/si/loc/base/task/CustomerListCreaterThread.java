
package com.asiainfo.biapp.si.loc.base.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.core.label.model.CustomRunModel;
import com.asiainfo.biapp.si.loc.core.label.service.ICustomerManagerService;

/**
 * 
 * Title : CustomerListCreaterThread
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 6.0 +
 * <p/>
 * Modification History :生成一次性客户群的清单
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
@Service
@Scope("prototype")
public class CustomerListCreaterThread extends Thread {

	private String customGroupId;// 客户群id

	private CustomRunModel customRunModel;

	@Autowired
	private ICustomerManagerService customerManagerService;

	@Override
	public void run() {
		try {
			Thread.sleep(5000);// 之前的事务提交需要一定时间
		} catch (InterruptedException e) {
			LogUtil.error("InterruptedException", e);
		}
		try {
			customerManagerService.createCustomerList(customGroupId, customRunModel);
		} catch (BaseException e) {
			LogUtil.error("生成客户群的清单异常", e);
		}
	}
	
	public void setCustomRunModel(CustomRunModel customRunModel) {
		this.customRunModel = customRunModel;
		this.customGroupId = customRunModel.getCustomGroupId();
		if (this.customGroupId == null) {
			throw new RuntimeException("customRunModel.customGroupId can not be null!" + customRunModel);
		}
	}

}
