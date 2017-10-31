
package com.asiainfo.biapp.si.coc.jauth.log.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.coc.jauth.frame.controller.BaseController;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogInterfaceDetail;
@RestController
public class LogInterfaceDetailController extends BaseController<LogInterfaceDetail>{
    public void queryPageByParams(@ModelAttribute JQGridPage<LogInterfaceDetail> page,String cols){
        
    }

    @Override
    protected BaseService<LogInterfaceDetail, String> getBaseService() {
        // TODO Auto-generated method stub
        return null;
    }
}
