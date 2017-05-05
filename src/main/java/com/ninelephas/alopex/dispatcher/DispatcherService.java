/*
 * Copyright (c) 2017. 九象网络科技 - All Rights Reserved.
 * @author 徐泽宇
 *
 *
 */

package com.ninelephas.alopex.dispatcher;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * 调度员的 service 类
 *
 * @author roamer - 徐泽宇
 * @create 2017-05-2017/5/5  下午1:32
 */
@Log4j2
@Service("com.ninelephas.alopex.dispatcher.DispatcherService")
public class DispatcherService {

    public void invoke(DispatchCommand dispatchCommand) {
        log.debug("开始分析调度员的请求方法");
        String command = dispatchCommand.getMethod();
        String params = dispatchCommand.getParams();
        String className = CommandDict.getInvokeClass(command);
        String method = CommandDict.getInvokeClassMethod(command);
        log.debug(String.format("开始把 %s 对应到 %s 类的 %s 方法", command, className, method));
    }
}
