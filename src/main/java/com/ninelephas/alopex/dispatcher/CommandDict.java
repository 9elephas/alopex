/*
 * Copyright (c) 2017. 九象网络科技 - All Rights Reserved.
 * @author 徐泽宇
 *
 *
 */

package com.ninelephas.alopex.dispatcher;

import com.ninelephas.common.configer.ConfigHelper;
import lombok.extern.log4j.Log4j2;

/**
 * 命令和类中方法的对应字典类
 *
 * @author roamer - 徐泽宇
 * @create 2017-05-2017/5/5  下午1:40
 */
@Log4j2
public class CommandDict {

    private static String PREIX = "MethodDict";

    private CommandDict() {
        //do nothing
    }

    static {

    }

    /**
     * 获取请求方法对应的执行类
     *
     * @param method
     *
     * @return
     */
    public static String getInvokeClass(String method) {
        return (String) ConfigHelper.getConfig().getProperty(String.format("%s.%s.clazz",PREIX,method));
    }

    /**
     * 获取请求方法对应的执行类中的方法
     *
     * @param method
     *
     * @return
     */
    public static String getInvokeClassMethod(String method) {
        return (String) ConfigHelper.getConfig().getProperty(String.format("%s.%s.method",PREIX,method));
    }
}

