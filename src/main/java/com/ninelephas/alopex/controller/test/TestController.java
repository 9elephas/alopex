package com.ninelephas.alopex.controller.test;

import com.ninelephas.alopex.controller.ControllerException;
import com.ninelephas.common.helper.HttpResponseHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *
 * @ClassName: TestController
 * @Description: 用于测试的MVC Controller
 * @author 徐泽宇
 * @date 2016年12月2日 下午6:52:03
 *
 */
@Log4j2
@Controller("com.ninelephas.alopex.controller.test")
@RequestMapping(value = "/test")
public class TestController {

    /**
     * Test500ErrorController
     *
     * @return
     *
     * @throws ControllerException
     * @Auther 徐泽宇
     * @Date 2016年11月10日 下午7:18:14
     * @Title: Test500ErrorController
     * @Description: 抛出一个500的错误
     */
    @RequestMapping(value = "/500_error")
    @ResponseBody
    public String test500ErrorController() throws ControllerException {
        throw new ControllerException("抛出一个测试的ControllerException错误");
    }

    /**
     * test500ErrorController
     *
     * @return
     *
     * @throws ControllerException
     * @Auther 徐泽宇
     * @Date 2016年12月12日 下午2:59:53
     * @Title: test500ErrorController
     * @Description: 调用成功
     */
    @RequestMapping(value = "/success", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String success() throws ControllerException {
        return HttpResponseHelper.successInfoInbox("ok");
    }
}
