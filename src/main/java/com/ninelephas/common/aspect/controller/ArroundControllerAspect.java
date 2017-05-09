/**
 * @Title: SystemLogAspect.java
 * @Package com.ninelephas.meerkat.aspect
 * @Description: 系统日志的AOP类
 *               Copyright: Copyright (c) 2016
 *               Company:九象网络科技（上海）有限公司
 * 
 * @author "徐泽宇"
 * @date 2016年8月1日 下午5:25:15
 * @version V1.0.0
 */

package com.ninelephas.common.aspect.controller;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName: ArroundControllerAspect
 * @Description: 处理所有controller类中方法的环绕通知，什么事情都不做，就是执行方法，为了和CatchControllerExceptionAspect配合
 *               切面定义和 CatchControllerExceptionAspect 一致，并且优先级要高于CatchControllerExceptionAspect
 * @author 徐泽宇
 * @date 2016年12月10日 上午3:11:39
 *
 */
@Log4j2
@Aspect
@Order(1)
@Component("com.ninelephas.common.aspect.controller.ArroundControllerAspect")
public class ArroundControllerAspect {


    /**
     * 
     * runControllerMethod
     * 
     * @Auther 徐泽宇
     * @Date 2016年12月10日 上午3:15:59
     * @Title: logTheController
     * @Description: 定义一个切面，指向所有的controller类中的所有方法
     */
    @Pointcut("execution(* com.ninelephas.alopex.controller..*.*(..))")
    public void runControllerMethod() {
        // Nothing to clean up
    }



    /**
     * 
     * runControllerMethod
     * 
     * @Auther 徐泽宇
     * @Date 2016年12月10日 上午3:17:12
     * @Title: runControllerMethod
     * @Description: 什么都不处理，仅仅是增加一个环绕通知.
     * 这里必须返回一个object，把处理过程交回给controller。否则controller中的@ResponseBody不会起作用了
     * @param joinPoint
     */
    @Around("runControllerMethod()")
    private Object runControllerMethod(ProceedingJoinPoint joinPoint) {
        if (log.isDebugEnabled()) {
            log.debug("runControllerMethod(ProceedingJoinPoint joinPoint={}) - start", joinPoint); //$NON-NLS-1$
        }

        Object rtnObject = null;
        try {
            rtnObject = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(),e);
        }

        if (log.isDebugEnabled()) {
            log.debug("runControllerMethod(ProceedingJoinPoint joinPoint={}) - end", joinPoint); //$NON-NLS-1$
        }
        return rtnObject;
    }


}
