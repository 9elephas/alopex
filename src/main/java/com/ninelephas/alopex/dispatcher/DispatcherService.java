/*
 * Copyright (c) 2017. 九象网络科技 - All Rights Reserved.
 * @author 徐泽宇
 *
 *
 */

package com.ninelephas.alopex.dispatcher;

import com.ninelephas.alopex.service.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 调度员的 service 类
 *
 * @author roamer - 徐泽宇
 * @create 2017-05-2017/5/5  下午1:32
 */
@Log4j2
@Service("com.ninelephas.alopex.dispatcher.DispatcherService")
public class DispatcherService {


    /**
     * 动态调用一个在 spring 中注册的 bean 的动态方法
     *
     * @param dispatchCommand
     *
     * @throws ServiceException
     */
    public void invoke(DispatchCommand dispatchCommand) throws ServiceException {
        log.debug("开始分析调度员的请求方法");
        String command = dispatchCommand.getMethod();
        String params = dispatchCommand.getParams();
        String className = CommandDict.getInvokeClass(command);
        String method = CommandDict.getInvokeClassMethod(command);
        log.debug(String.format("开始把 %s 对应到 %s 类的 %s 方法", command, className, method));
        log.debug(String.format("开始运行 %s 类的 %s 方法", className, method));
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        Class c = wac.getBean(className).getClass();
        try {
            Method test = c.getMethod(method);
            try {
                Object returnValue = test.invoke(wac.getBean(className), (Object[]) null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
