
package com.asiainfo.biapp.si.loc.core.home.message.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.home.message.entity.LocSysAnnouncement;
import com.asiainfo.biapp.si.loc.core.home.message.entity.LocUserReadInfo;
import com.asiainfo.biapp.si.loc.core.home.message.service.ILocPersonNoticeService;
import com.asiainfo.biapp.si.loc.core.home.message.service.ILocSysAnnouncementService;
import com.asiainfo.biapp.si.loc.core.home.message.service.ILocUserReadInfoService;
import com.asiainfo.biapp.si.loc.core.home.message.vo.LocSysAnnouncementVo;
import com.asiainfo.biapp.si.loc.core.home.message.vo.LocUserReadInfoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Title : MessageMgrController
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 7.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2018年4月20日    hejw3        Created
 * </pre>
 * <p/>
 *
 * @author hejw3
 * @version 1.0.0.2018年4月20日
 */
@Api(value = "11", description = "011.01->消息管理：何靖文")
@RestController
@RequestMapping("api/message")
public class MessageMgrController extends BaseController {
    private static final int IS_READ = 1;
    private static final int NOT_REAAD = 0; 
    @Autowired
    private ILocPersonNoticeService iLocPersonNoticeService;

    @Autowired
    private ILocSysAnnouncementService locSysAnnouncementService;

    @Autowired
    private ILocUserReadInfoService locUserReadInfoService;

    @RequestMapping(value = "/locSysAnnouncement/queryPage", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询系统通知")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "announcementDetail", value = "公告内容", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "announcementName", value = "公告名字", required = false, paramType = "query", dataType = "string") })
    public Page<LocSysAnnouncement> findLocSysAnnouncementPage(@ModelAttribute Page<LocSysAnnouncement> page,
            @ModelAttribute LocSysAnnouncementVo locSysAnnouncementVo) {
        Page<LocSysAnnouncement> locSysAnnouncementPage = new Page<LocSysAnnouncement>();
        try {
            User user = this.getLoginUser();
            if (StringUtil.isNotEmpty(locSysAnnouncementVo.getAnnouncementName())) {
                // 搜索
                locSysAnnouncementPage=locSysAnnouncementService.searchLocSysAnnouncement(page, locSysAnnouncementVo);
            } else {
                locSysAnnouncementPage = locSysAnnouncementService.selectLocSysAnnouncement(page, locSysAnnouncementVo);
            }
            List<LocSysAnnouncement> rows = locSysAnnouncementPage.getRows();
            LocUserReadInfoVo locUserReadInfoVo = new LocUserReadInfoVo();
            for (LocSysAnnouncement locSysAnnouncement : rows) {
                locUserReadInfoVo.setUserId(user.getUserName());
                locUserReadInfoVo.setAnnouncementId(locSysAnnouncement.getAnnouncementId());
                LocUserReadInfo readInfo = locUserReadInfoService.selectLocUserReadInfo(locUserReadInfoVo);
                if (null == readInfo){
                    locSysAnnouncement.setReadStatus(NOT_REAAD);
                } else {
                    locSysAnnouncement.setReadStatus(IS_READ);
                }
            }
        } catch (BaseException e) {
            locSysAnnouncementPage.fail(e);
        }
        return locSysAnnouncementPage;
    }

    
}
