package com.ninelephas.alopex.controller.test;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@Controller("com.ninelephas.alopex.controller.SpringControllerTester")
@Log4j2
public class SpringControllerTester {

    @RequestMapping("/controller/test")
    @ResponseBody
    public String index() {


        log.debug("debug index 方法");
        log.info("info index 方法");
        log.error("error index 方法");
        log.warn("warn index 方法");
        log.trace("trace index 方法");
        log.fatal("fatal index 方法");

        return "Hello World!!";
    }
}
