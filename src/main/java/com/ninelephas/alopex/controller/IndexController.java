package com.ninelephas.alopex.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by roamer on 2017/4/26.
 * 主界面 controller 类
 */
@Log4j2
@Controller("com.ninelephas.alopex.controller.IndexController")
@RequestMapping(value = "/")
public class IndexController {

    /**
     * 显示登录后的首界面
     * @return
     */
    @RequestMapping(value = "/main")
    @ResponseBody
    public ModelAndView mainPage(){
        log.debug("访问主界面");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/user/main");
        return mv ;
    }


}
