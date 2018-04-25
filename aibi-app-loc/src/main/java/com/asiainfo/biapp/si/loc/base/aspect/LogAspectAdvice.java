
package com.asiainfo.biapp.si.loc.base.aspect;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.asiainfo.biapp.si.loc.auth.utils.AuthUtils;
import com.asiainfo.biapp.si.loc.base.utils.DateUtil;
import com.asiainfo.biapp.si.loc.base.utils.HttpUtil;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;

/**
 * Title : LogAspectAdvice
 * <p/>
 * Description : 接口操作日志记录
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司O
 * <p/>
 * JDK Version Used : JDK 8.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年12月1日    panww        Created
 * </pre>
 * <p/>
 *
 * @author panww
 * @version 1.0.0.2017年12月1日
 */
// @Aspect : 标记为切面类
// @Pointcut : 指定匹配切点集合
// @Before : 指定前置通知，value中指定切入点匹配
// @AfterReturning ：后置通知，具有可以指定返回值
// @AfterThrowing ：异常通知
// 注意：前置/后置/异常通知的函数都没有返回值，只有环绕通知有返回值
@Component // 首先初始化切面类
@Aspect // 声明为切面类，底层使用动态代理实现AOP
public class LogAspectAdvice {
    
	
    private static String jauthUrl;
    private static String nodeName;

    @Value("${jauth-url}")
    public void setJauthUrl(String jauthUrl) {
        this.jauthUrl = jauthUrl;
    }

    @Value("${spring.application.name}")
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * 获取方法名称
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public static String getbooleanMethod(JoinPoint joinPoint) throws Exception {
        Class<?> clazz = joinPoint.getTarget().getClass();
        String name = joinPoint.getSignature().getName();
        Object[] parameterTypes = joinPoint.getArgs();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(name) && method.getParameterTypes().length == parameterTypes.length) {
            	ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            if (apiOperation != null) {
                return apiOperation.value();
            }
                break;
            }
        }
        return "";
    }
    
    

    // 指定切入点匹配表达式，注意它是以方法的形式进行声明的。
    // 第一个* 代表返回值类型,需要加空格
    // 如果要设置多个切点可以使用 || 拼接
    public void saveLog(String inputParams,String interfaceName, String method, String targetName, String result) {
        // http保存日志
        Map<String, Object> params = new HashMap<>();
        
    	try {
    		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    		String token = AuthUtils.getTokenByRequest(request);
        	params.put("userId",AuthUtils.getUserByToken(token).getUserName());
        	params.put("ipAddr",request.getRequestURL()+"/"+ request.getRequestURI());
    	} catch (Exception e) {
    		params.put("userId", "sys_loc");
    	    params.put("ipAddr", "localhost");
    	}
    	
        params.put("opTime",DateUtil.date2String(new Date()));
        params.put("sysId", nodeName);
        params.put("interfaceName",interfaceName );
        params.put("interfaceUrl", targetName + "/" + method);
        params.put("inputParams", inputParams);
        params.put("outputParams", result);
        try {
        	 HttpUtil.sendPost(jauthUrl + "/api/log/interface/save", params);
		} catch (Exception e) {
			LogUtil.error("同步给JAUTH异常数据失败", e);
		}
       
    }

    // 环绕通知（##环绕通知的方法中一定要有ProceedingJoinPoint 参数,与
    // Filter中的 doFilter方法类似）

    @Around("execution(* com.asiainfo.biapp.si.loc.core.*.controller.*.*(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        // 调用方法的参数
        Object[] args = pjp.getArgs();
        // 调用的方法名
        String method = pjp.getSignature().getName();
        // 获取目标对象(形如：com.action.admin.LoginAction@1a2467a)
        Object target = pjp.getTarget();
        // 获取目标对象的类名(形如：com.action.admin.LoginAction)
        String targetName = pjp.getTarget().getClass().getName();
        // 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
        long startTime = System.currentTimeMillis();  
        Object result = pjp.proceed();// result的值就是被拦截方法的返回值
        long endTime = System.currentTimeMillis();  
        if(!"setReqAndRes".equals(method)){
            
        	
        	

        	
        	
        	String inputParams = "";
        	if(args != null  && args.length> 0){
        		
        		ObjectMapper mapper = new ObjectMapper();
        		if(args[0] instanceof org.apache.catalina.connector.RequestFacade){
        			inputParams = "请求头";
        		}else{
        		    try {
        		        inputParams = mapper.writeValueAsString(args);
                    } catch (Exception e) {
                        inputParams = "不能解析的参数";
                    }
        		}
        		if(inputParams.length() > 2000){
        			inputParams = inputParams.substring(0, 2000);
        		}
        	}
        	
        	String resultStr = "";
        	if(result != null ){
        		//resultStr = JSONObject.fromObject(result).toString();
        		
        		ObjectMapper mapper = new ObjectMapper();
        		resultStr = mapper.writeValueAsString(result);
        		
        		if(resultStr.length() > 2000){
        			resultStr = resultStr.substring(0, 2000);
        		}
        	}
        	String interfaceName = getbooleanMethod(pjp);
        	this.saveLog(inputParams, endTime-startTime+"ms：：："+interfaceName,method, targetName, resultStr );
        }
        return result;
    }

    // 异常通知
    @AfterThrowing(value = "execution(* com.asiainfo.biapp.si.loc.*.controller.*.*(..))", throwing = "e")
    public void doThrow(JoinPoint jp, Throwable e) {
    	LogUtil.info("删除出错啦");
    }

}
