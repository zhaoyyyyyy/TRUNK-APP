
package com.asiainfo.biapp.si.loc.bd.list.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.model.ExploreQueryParam;
import com.asiainfo.biapp.si.loc.core.label.service.ICustomerManagerService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;

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

	private String customId;// 客户群id

	private ExploreQueryParam model;

	@Autowired
	private ICustomerManagerService customerManagerService;

	@Autowired
	private ILabelInfoService labelInfoService;
	
	@Override
	public void run() {
		try {
			Thread.sleep(5000);// 之前的事务提交需要一定时间
		} catch (InterruptedException e) {
			LogUtil.error("InterruptedException", e);
		}
		try {
			LabelInfo customGroup = labelInfoService.get(customId);
		    customGroup.setDataStatusId(ServiceConstants.LabelInfo.DATA_STATUS_ID_G_CREATING);
		    labelInfoService.syncUpdateCustomGroupInfo(customGroup, null);
			customerManagerService.createCustomerList(customId, model);
		} catch (BaseException e) {
			LogUtil.error("生成客户群的清单异常", e);
		}
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

	public void setModel(ExploreQueryParam model) {
		this.model = model;
	}

}
