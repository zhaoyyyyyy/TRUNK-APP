/*
 * @(#)IAllUserMsgService.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.AllUserMsg;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.AllUserMsgVo;

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

    /**
     * Description: 分页查询全量表
     *
     * @param page
     * @param allUserMsgVo
     * @return
     * @throws BaseException
     */
    public Page<AllUserMsg> selectAllUserMsgPageList(Page<AllUserMsg> page, AllUserMsgVo allUserMsgVo)
            throws BaseException;

    /**
     * Description: 不分页查询全量表
     *
     * @param allUserMsgVo
     * @return
     * @throws BaseException
     */
    public List<AllUserMsg> selectAllUserMsgList(AllUserMsgVo allUserMsgVo) throws BaseException;

    /**
     * Description: 根据ID查询全量表信息
     *
     * @param priKey
     * @return
     * @throws BaseException
     */
    public AllUserMsg selectAllUserMsgById(String priKey) throws BaseException;

    /**
     * Description: 保存全量表
     *
     * @param allUserMsg
     * @return
     * @throws BaseException
     */
    public void addAllUserMsg(AllUserMsg allUserMsg) throws BaseException;

    /**
     * Description: 修改全量表
     *
     * @param allUserMsg
     * @return
     * @throws BaseException
     */
    public void modifyAllUserMsg(AllUserMsg allUserMsg) throws BaseException;

    /**
     * Description: 根据ID删除全量表
     *
     * @param priKey
     * @return
     * @throws BaseException
     */
    public void deleteAllUserMsg(String priKey) throws BaseException;

}
