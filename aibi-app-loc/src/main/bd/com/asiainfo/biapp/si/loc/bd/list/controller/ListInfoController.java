/*
 * @(#)ListInfoController.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.bd.list.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.bd.list.entity.ListInfo;
import com.asiainfo.biapp.si.loc.bd.list.entity.ListInfoId;
import com.asiainfo.biapp.si.loc.bd.list.service.IListInfoService;

/**
 * Title : ListInfoController
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2018年04月25日    shaosq        Created
 * </pre>
 * <p/>
 *
 * @author shaosq
 * @version 1.0.0.2018年04月25日
 */
@Api(value = "007.05->-标签清单信息管理", description = "邵思迁")
@RequestMapping("api/label")
@RestController
public class ListInfoController extends BaseController<ListInfo> {
    @Autowired
    private IListInfoService iListInfoService;
    
    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/listInfo/queryPage", method = RequestMethod.POST)
    public Page<ListInfo> list(@ModelAttribute Page<ListInfo> page,@ModelAttribute ListInfo listInfoVo,@ModelAttribute ListInfoId listInfoId) throws BaseException{
        Page<ListInfo> listInfoPage = new Page <>();
        try {
            listInfoVo.setListInfoId(listInfoId);
            listInfoPage=iListInfoService.selectListInfoPageList(listInfoPage, listInfoVo);
        } catch (BaseException e) {
            listInfoPage.fail(e);
        }
        return listInfoPage;
    }
}
