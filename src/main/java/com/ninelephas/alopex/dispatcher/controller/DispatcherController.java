/*
 * Copyright (c) 2017. 九象网络科技 - All Rights Reserved.
 * @author 徐泽宇
 *
 *
 */

package com.ninelephas.alopex.dispatcher.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ninelephas.alopex.controller.ControllerException;
import com.ninelephas.alopex.dispatcher.DispatchCommand;
import com.ninelephas.alopex.dispatcher.service.DispatcherService;
import com.ninelephas.alopex.service.ServiceException;
import com.ninelephas.common.helper.HttpResponseHelper;
import com.ninelephas.common.helper.JsonUtilsHelper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
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
@Controller("com.ninelephas.alopex.dispatcher.controller.DispatcherController")
@RequestMapping(value = "/rest/dispatcher")
public class DispatcherController {

    @Autowired
    DispatcherService dispatcherService;

    @RequestMapping(value = "/invoke", method = RequestMethod.POST)
    public @ResponseBody
    String invoke(@RequestBody String invokeContent) throws ControllerException {
        log.debug(String.format("调度者处理 ---- 开始,传入的内容是:%s", invokeContent));
        log.debug(String.format("传入的参数是: %s", invokeContent));
        DispatchCommand dispatchCommand;
        try {
            dispatchCommand = JsonUtilsHelper.parseStringToObject(invokeContent, DispatchCommand.class);
            log.debug(String.format("要处理的方法是[%s]", dispatchCommand.getMethod()));
            if (StringUtils.isEmpty(dispatchCommand.getParams())){
                log.debug("无参数调用");
            }else{
                log.debug(String.format("参数是[%s]", dispatchCommand.getParams()));
            }
            //调用解析类，进行这个方法的执行
            Object objcect = dispatcherService.invoke(dispatchCommand);
            String returnString = JsonUtilsHelper.objectToJsonString(objcect);
            log.debug(String.format("调度者处理 ---- 完成,准备返回的内容是:%s", returnString));
            return HttpResponseHelper.successInfoInbox(returnString);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new ControllerException(e.getMessage());
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            throw new ControllerException(e.getMessage());
        }
    }

}
