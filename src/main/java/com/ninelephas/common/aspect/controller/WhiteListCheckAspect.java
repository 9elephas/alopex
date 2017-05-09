package com.ninelephas.common.aspect.controller;

import com.ninelephas.common.ProjectException;
import com.ninelephas.common.configer.ConfigHelper;
import com.ninelephas.util.Ipv4WhiteList;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * 
  * @ClassName: WhitePaperCheckAspect
  * @Description: 访问白名单处理
  * @author  徐泽宇
  * @date 2016年12月21日 下午12:13:26
  *
 */
@Log4j2
@Aspect
@Order(3)
@Component("com.ninelephas.common.aspect.controller.WhiteListCheckAspect")
public class WhiteListCheckAspect {
    @Autowired
    private HttpServletRequest request;
    
    /**
     * 
     * runControllerMethod
     * 
     * @Auther 徐泽宇
     * @Date 2016年12月10日 上午3:15:59
     * @Title: logTheController
     * @Description: 定义一个切面，指向所有的controller类中的所有方法
     */
    @Pointcut("execution(* com.ninelephas.alopex.controller..*.*(..)) ")
    public void beforeRunControllerMethod() {
        // Nothing to clean up
    }
    /**
     * 
     * runControllerMethod
     * 
     * @Auther 徐泽宇
     * @Date 2016年12月10日 上午3:17:12
     * @Title: runControllerMethod
     * @Description: 进行白名单授权检查
     */
    @Before("beforeRunControllerMethod()")
    private void whiteListCheck() throws ProjectException{
        if (log.isDebugEnabled()) {
            log.debug("whitePaperCheck() - start"); //$NON-NLS-1$
        }
        Ipv4WhiteList list = new Ipv4WhiteList(ConfigHelper.getConfig().getString("System.Whitelist"));
        String requestIP  = request.getRemoteAddr();
        boolean isInWhitelist = list.isIn(requestIP);
        if (!isInWhitelist){
            String errorMessage = new StringBuilder(requestIP).append("不在访问白名单中，访问被拒绝").toString();
            throw  new ProjectException(errorMessage);
        }else{
            log.debug(new StringBuilder("白名单判断 :").append(requestIP).append("在白名单中。"));
        }
            
    }
}
