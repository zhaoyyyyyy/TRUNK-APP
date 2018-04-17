
package com.asiainfo.biapp.si.loc.base.filter;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import com.alibaba.druid.support.http.StatViewServlet;


@Component
/**   
 * druid监控视图配置   
 * @ClassName: DruidStatViewServlet    
 * @author linge   
 * @date 2017年7月24日 上午10:54:27   
 */    
@WebServlet(urlPatterns = "/druid/*", initParams={    
    @WebInitParam(name="allow",value=""),// IP白名单 (没有配置或者为空，则允许所有访问)    
    @WebInitParam(name="deny",value=""),// IP黑名单 (存在共同时，deny优先于allow)    
    @WebInitParam(name="loginUsername",value="admin"),// 用户名    
    @WebInitParam(name="loginPassword",value="admin"),// 密码    
    @WebInitParam(name="resetEnable",value="true")// 禁用HTML页面上的“Reset All”功能    
})   
public class DruidStatViewServlet extends StatViewServlet {  
    private static final long serialVersionUID = 2359758657306626394L;  
  
} 
