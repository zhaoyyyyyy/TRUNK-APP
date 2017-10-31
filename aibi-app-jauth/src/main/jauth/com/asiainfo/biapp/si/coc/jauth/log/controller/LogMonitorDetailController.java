
package com.asiainfo.biapp.si.coc.jauth.log.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.coc.jauth.frame.controller.BaseController;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogMonitorDetail;
/**
 * 
 * Title : LogMonitorDetailController
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年10月24日    panweiwei        Created</pre>
 * <p/>
 *
 * @author  panweiwei
 * @version 1.0.0.2017年10月24日
 */
@RestController
public class LogMonitorDetailController  extends BaseController<LogMonitorDetail>{

    @Override
    protected BaseService<LogMonitorDetail, String> getBaseService() {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * 
     * Description: 查询方法
     *
     * @param page
     * @param cols
     */
    public void queryPageByParams(@ModelAttribute JQGridPage<LogMonitorDetail> page,String cols){
        
    }
}
