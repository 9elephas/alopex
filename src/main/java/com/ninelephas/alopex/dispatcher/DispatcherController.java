/*
 * Copyright (c) 2017. 九象网络科技 - All Rights Reserved.
 * @author 徐泽宇
 *
 *
 */

package com.ninelephas.alopex.dispatcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ninelephas.alopex.controller.ControllerException;
import com.ninelephas.common.helper.JsonUtilsHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 调度员 Controller 类
 *
 * @author roamer - 徐泽宇
 * @create 2017-05-2017/5/5  上午11:45
 */
@Log4j2
@Controller("com.ninelephas.alopex.dispatcher.DispatcherController")
@RequestMapping(value = "/rest/dispatcher")
public class DispatcherController {

    @Autowired
    DispatcherService dispatcherService ;

    @RequestMapping(value = "/invoke", method = RequestMethod.POST)
    public @ResponseBody
    String invoke(@RequestBody String invokeContent) throws ControllerException {
        log.debug("调度者处理 ---- 开始");
        log.debug(String.format("传入的参数是: %s", invokeContent));
        DispatchCommand dispatchCommand;
        try {
            dispatchCommand = JsonUtilsHelper.parseStringToObject(invokeContent, DispatchCommand.class);
            log.debug(String.format("要处理的方法是[%s]", dispatchCommand.getMethod()));
            log.debug(String.format("参数是[%s]", dispatchCommand.getParams()));
            //调用解析类，进行这个方法的执行
            dispatcherService.invoke(dispatchCommand);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        log.debug("调度者处理 ---- 完成");
        return "";
    }

}