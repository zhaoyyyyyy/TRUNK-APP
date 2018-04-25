package com.asiainfo.biapp.si.loc.localization.prdbd.service.impl;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.auth.service.IUserService;
import com.asiainfo.biapp.si.loc.auth.service.impl.DevUserServiceImpl;

/**
 * 
 * Title : 用户相关业务实现层
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
 * <pre>1    2017年11月7日    Administrator        Created</pre>
 * <p/>
 *
 * @author  zhougz3
 * @version 1.0.0.2018年04月25日
 */
@Profile("as-show")
@Service("userService")
@Transactional
public class ShowUserServiceImpl extends DevUserServiceImpl implements IUserService{

	
	
}
