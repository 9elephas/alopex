package com.ninelephas.common.aspect.catchcontroller;

import com.ninelephas.common.helper.HttpResponseHelper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @ClassName: CatchControllerExceptionAspect
 * @Description: 捕获所有Controller中的方法的Exception
 *               通过HttpServletResponse输出到浏览器
 * @author 徐泽宇
 * @date 2016年11月1日 下午10:05:29
 *
 */
@SuppressWarnings("ALL")
@Log4j2
@Aspect
@Order(2)
@Component("com.ninelephas.common.aspect.catchcontroller.CatchControllerExceptionAspect")
public class CatchControllerExceptionAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    /**
     * catch appliction Controller
     *
     * @Auther 徐泽宇
     * @Date 2017年05月10日 下午10:08:50
     * @Title: catchControllerMethod
     * @Description: 切入所有需要切入的controller中方法的切面
     */
    @Pointcut("execution(* com.ninelephas.alopex.controller..*.*(..) ) || execution(* com.ninelephas.alopex.dispatcher..*.*(..) ) ")
    public void catchControllerMethod() {
        // Nothing to clean up
    }



    /**
     * writeToHttpResponse
     *
     * @throws IOException
     *
     * @throws ServletException
     *
     * @Auther 徐泽宇
     * @Date 2016年11月1日 下午10:11:24
     * @Title: writeToHttpResponse
     * @Description: 捕捉到切面产生的Exception后，写入HttpServletResponse
     */
    @AfterThrowing(throwing = "ex", pointcut = "catchControllerMethod()")
    private void writeToHttpResponse(Throwable ex) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("捕获到错误，开始装箱成json字符串.错误信息是:【{}】，错误类是：【{}】", ex.getMessage(),
                ex.getClass().getName()); // $NON-NLS-1$
            log.debug("writeToHttpResponse() - start"); //$NON-NLS-1$
            log.debug(new StringBuilder("访问的uri是：").append(request.getRequestURI()));
        }
        String invokeURI = request.getRequestURI();
        String uriPostfix = "";
        String[] urlStringArray = StringUtils.split(invokeURI, '.');
        if (urlStringArray.length == 2) {
            uriPostfix = urlStringArray[1];
        }
        response.setCharacterEncoding("UTF-8");
        try {
            if (!StringUtils.equalsIgnoreCase(uriPostfix, "json")) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, HttpResponseHelper.inbox(ex));
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("text/json;charset=UTF-8");
                response.getWriter().write(HttpResponseHelper.inbox(ex));
                log.debug("返回json化的错误信息:{}", HttpResponseHelper.inbox(ex));
            }
        } catch (IOException e) {
            log.error(e, e.fillInStackTrace());
        }
        if (log.isDebugEnabled()) {
            log.debug("writeToHttpResponse() - end"); //$NON-NLS-1$
        }
    }

}
