
/*
 * Copyright (c) 2017. 九象网络科技 - All Rights Reserved.
 * @author 徐泽宇
 *
 *
 */

package com.ninelephas.alopex.controller.fabric;


import com.ninelephas.alopex.controller.pojo.FabricUser;
import com.ninelephas.alopex.service.fabric.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Log4j2
@Controller("com.ninelephas.alopex.controller.fabric.UserController")
@RequestMapping(value = "/fabric/user")
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 跳转到用户注册界面
     *
     * @param key
     *
     * @return
     */
    @RequestMapping(value = "/create")
    public ModelAndView create(String key) {
        ModelAndView mv = new ModelAndView();
        log.debug("显示一个收集注册用户信息的界面。");
        return mv;
    }


    /**
     * 注册一个 Fabric 用户
     *
     * @param userName
     * @param userPasswd
     *
     * @return
     */
    @RequestMapping(value = "/rest/register", method = RequestMethod.POST)
    public @ResponseBody
    String register(@RequestParam(value = "userName", required = true) String userName, @RequestParam(value = "userPasswd", required = true) String userPasswd) throws Exception {
        FabricUser fabricUser = new FabricUser();
        fabricUser.setUserName(userName);
        //fabricUser.setPassword(userPasswd);
        log.debug(String.format("注册用户:[%s]", userName));
        log.debug(String.format("密码:[%s]", userPasswd));
        //调用用户服务类的注册方法
        //userService.register(fabricUser);
        return "{success}";
    }

}
