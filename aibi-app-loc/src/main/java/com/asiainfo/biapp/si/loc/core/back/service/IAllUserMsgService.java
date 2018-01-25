/*
 * @(#)IAllUserMsgService.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.back.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.back.entity.AllUserMsg;
import com.asiainfo.biapp.si.loc.core.back.vo.AllUserMsgVo;

/**
 * Title : IAllUserMsgService
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2018年1月24日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2018年1月24日
 */
public interface IAllUserMsgService extends BaseService<AllUserMsg, String> {

    public Page<AllUserMsg> selectAllUserMsgPageList(Page<AllUserMsg> page, AllUserMsgVo allUserMsgVo)
            throws BaseException;

    public List<AllUserMsg> selectAllUserMsgList(AllUserMsgVo allUserMsgVo) throws BaseException;

    public AllUserMsg selectAllUserMsgById(String priKey) throws BaseException;

    public void addAllUserMsg(AllUserMsg allUserMsg) throws BaseException;

    public void modifyAllUserMsg(AllUserMsg allUserMsg) throws BaseException;

    public void deleteAllUserMsg(String priKey) throws BaseException;

}
