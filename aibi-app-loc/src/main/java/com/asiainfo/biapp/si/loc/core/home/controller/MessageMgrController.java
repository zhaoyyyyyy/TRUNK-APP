
package com.asiainfo.biapp.si.loc.core.home.controller;

import java.util.Date;
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
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.home.entity.LocPersonNotice;
import com.asiainfo.biapp.si.loc.core.home.entity.LocSysAnnouncement;
import com.asiainfo.biapp.si.loc.core.home.entity.LocUserReadInfo;
import com.asiainfo.biapp.si.loc.core.home.service.ILocPersonNoticeService;
import com.asiainfo.biapp.si.loc.core.home.service.ILocSysAnnouncementService;
import com.asiainfo.biapp.si.loc.core.home.service.ILocUserReadInfoService;
import com.asiainfo.biapp.si.loc.core.home.vo.LocPersonNoticeVo;
import com.asiainfo.biapp.si.loc.core.home.vo.LocSysAnnouncementVo;
import com.asiainfo.biapp.si.loc.core.home.vo.LocUserReadInfoVo;

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

    private static final int NOT_REAAD = 2;

    private static final int IS_SUCCESS = 1;

    private static final int DELETED = 0;

    private static final int EFFECTIVE = 1;

    private static final int FIRST_SIZE = 0;

    private static final String SUCCESS = "success";

    @Autowired
    private ILocPersonNoticeService locPersonNoticeService;

    @Autowired
    private ILocSysAnnouncementService locSysAnnouncementService;

    @Autowired
    private ILocUserReadInfoService locUserReadInfoService;

    @RequestMapping(value = "/locSysAnnouncement/queryPage", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询系统公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "announcementName", value = "公告名字", required = false, paramType = "query", dataType = "string") })
    public Page<LocSysAnnouncement> findLocSysAnnouncementPage(@ModelAttribute Page<LocSysAnnouncement> page,
            @ModelAttribute LocSysAnnouncementVo locSysAnnouncementVo) {
        Page<LocSysAnnouncement> locSysAnnouncementPage = new Page<LocSysAnnouncement>();
        try {
            User user = this.getLoginUser();
            locSysAnnouncementPage = locSysAnnouncementService.selectLocSysAnnouncement(page, locSysAnnouncementVo);
            List<LocSysAnnouncement> rows = locSysAnnouncementPage.getRows();
            LocUserReadInfoVo locUserReadInfoVo = new LocUserReadInfoVo();
            int totalSize = locUserReadInfoService.selectCount(null);
            for (LocSysAnnouncement locSysAnnouncement : rows) {
                locUserReadInfoVo.setUserId(user.getUserName());
                locUserReadInfoVo.setAnnouncementId(locSysAnnouncement.getAnnouncementId());
                LocUserReadInfo readInfo = locUserReadInfoService.selectLocUserReadInfo(locUserReadInfoVo);
                if (null == readInfo) {
                    locSysAnnouncement.setReadStatus(NOT_REAAD);
                } else {
                    locSysAnnouncement.setReadStatus(IS_READ);
                }
                locSysAnnouncement.setUnread(locSysAnnouncementPage.getTotalCount() - totalSize);
            }
        } catch (BaseException e) {
            locSysAnnouncementPage.fail(e);
        }
        return locSysAnnouncementPage;
    }

    @RequestMapping(value = "/locSysAnnouncement/read", method = RequestMethod.POST)
    @ApiOperation(value = "个人阅读系统公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "announcementId", value = "公告id", required = false, paramType = "query", dataType = "string") })
    public WebResult<String> readLocSysAnnouncement(@ModelAttribute LocSysAnnouncementVo locSysAnnouncementVo) {
        WebResult<String> webResult = new WebResult<>();
        try {
            User user = this.getLoginUser();
            LocUserReadInfo locUserReadInfo = new LocUserReadInfo();
            locUserReadInfo.setAnnouncementId(locSysAnnouncementVo.getAnnouncementId());
            locUserReadInfo.setReadTime(new Date());
            locUserReadInfo.setUserId(user.getUserName());
            locUserReadInfo.setStatus(IS_READ);
            locUserReadInfoService.insertLocUserReadInfo(locUserReadInfo);
        } catch (BaseException e) {
            webResult.fail(e);
        }
        return webResult.success("阅读成功", null);
    }

    @RequestMapping(value = "/locSysAnnouncement/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除系统公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "announcementId", value = "公告id", required = false, paramType = "query", dataType = "string") })
    // ids格式："'1','2','3','4'"
    public WebResult<String> deleteLocSysAnnouncement(@ModelAttribute LocSysAnnouncementVo locSysAnnouncementVo,
            String ids) {
        WebResult<String> webResult = new WebResult<>();
        // 逻辑删除
        String ammouncementIds = ids;
        if (StringUtil.isNotEmpty(ids)) {
            locSysAnnouncementService.deleteByUpdateLocSysAnnouncement(ids);
        } else {
            LocSysAnnouncement locSysAnnouncement = locSysAnnouncementService
                .getLocSysAnnouncementById(locSysAnnouncementVo.getAnnouncementId());
            locSysAnnouncement.setStatus(DELETED);
            locSysAnnouncementService.updateLocSysAnnouncement(locSysAnnouncement);
            ammouncementIds = "'" + locSysAnnouncement.getAnnouncementId() + "'";
        }
        // 删除个人阅读信息
        locUserReadInfoService.deleteLocUserReadInfo(ammouncementIds);
        return webResult.success("删除成功", SUCCESS);
    }

    @RequestMapping(value = "/locSysAnnouncement/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增系统公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "announcementId", value = "公告id", required = false, paramType = "query", dataType = "string") })
    public WebResult<String> addLocSysAnnouncement(@ModelAttribute LocSysAnnouncement locSysAnnouncement) {
        WebResult<String> webResult = new WebResult<>();
        try {
            User user = this.getLoginUser();
            locSysAnnouncement.setReleaseUserId(user.getUserName());
            locSysAnnouncement.setStatus(EFFECTIVE);
            locSysAnnouncement.setReleaseDate(new Date());
            locSysAnnouncement.setIsSuccess(IS_SUCCESS);
            locSysAnnouncementService.insertLocSysAnnouncement(locSysAnnouncement);
        } catch (BaseException e) {
            webResult.fail(e);
        }
        return webResult.success("添加成功", SUCCESS);
    }

    @RequestMapping(value = "/personNotice/queryPage", method = RequestMethod.POST)
    @ApiOperation(value = "个人通知查询")
    public Page<LocPersonNotice> findLocPersonNoticePage(@ModelAttribute Page<LocPersonNotice> page,
            @ModelAttribute LocPersonNoticeVo locPersonNoticeVo) {
        Page<LocPersonNotice> locPersonNoticePage = new Page<LocPersonNotice>();
        int unreadSize = locPersonNoticeService.selectUnreadCount();
        try {
            User user = this.getLoginUser();
            locPersonNoticeVo.setReceiveUserId(user.getUserName());
            locPersonNoticePage = locPersonNoticeService.selectLocPersonNoticeList(page, locPersonNoticeVo);
        } catch (BaseException e) {
            locPersonNoticePage.fail(e);
        }
        if (!locPersonNoticePage.getRows().isEmpty()) {
            locPersonNoticePage.getRows().get(FIRST_SIZE).setUnreadSize(unreadSize);
        }
        return locPersonNoticePage;

    }

    @RequestMapping(value = "/personNotice/read", method = RequestMethod.POST)
    @ApiOperation(value = "个人通知阅读")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "noticeId", value = "通知id", required = false, paramType = "query", dataType = "string") })
    public WebResult<String> readLocPersonNotice(@ModelAttribute LocPersonNoticeVo locPersonNoticeVo) {
        WebResult<String> webResult = new WebResult<>();
        try {
            User user = this.getLoginUser();
            locPersonNoticeVo.setReceiveUserId(user.getUserName());
            LocPersonNotice locPersonNotice = locPersonNoticeService.selectLocPersonNoticeById(locPersonNoticeVo);
            if (locPersonNotice.getReadStatus() == IS_READ) {
                throw new BaseException("该消息已阅读");
            }
            locPersonNotice.setReadStatus(IS_READ);
            locPersonNoticeService.updateLocPersonNotice(locPersonNotice);
        } catch (BaseException e) {
            webResult.fail(e);
        }
        return webResult.success("阅读成功", null);
    }

}
