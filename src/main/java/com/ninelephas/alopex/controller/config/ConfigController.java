package com.ninelephas.alopex.controller.config;

import com.ninelephas.alopex.service.config.ConfigService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/**
 * 配置信息维护 controller
 * Created by roamer on 2017/4/27.
 */
@Log4j2
@Controller("com.ninelephas.alopex.controller.config.ConfigController")
@RequestMapping(value = "/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        log.debug("获取配置信息的列表");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/config/index");
        ArrayList<ConfigItem> arrayListItem = configService.getKeys();

        mv.addObject("configItems", arrayListItem);
        log.debug("返回配置项列表的视图");
        return mv;
    }
}
