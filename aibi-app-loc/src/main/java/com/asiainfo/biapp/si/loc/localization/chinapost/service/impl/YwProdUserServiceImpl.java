package com.asiainfo.biapp.si.loc.localization.chinapost.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.auth.model.Organization;
import com.asiainfo.biapp.si.loc.auth.model.Resource;
import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.auth.service.IOrganizationService;
import com.asiainfo.biapp.si.loc.auth.service.IUserService;
import com.asiainfo.biapp.si.loc.auth.service.impl.DevUserServiceImpl;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.UserAuthException;
import com.asiainfo.biapp.si.loc.base.utils.HttpUtil;
import com.asiainfo.biapp.si.loc.base.utils.JsonUtil;

import net.sf.json.JSONObject;

/**
 * 
 * Title : 中国邮政集团，邮政业务，用户相关业务实现层
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
 * @version 1.0.0.2017年11月7日
 */
@Profile("cp-yw-prod")
@Service("userService")
@Transactional
public class YwProdUserServiceImpl extends YwDevUserServiceImpl implements IUserService{

}
