/*
 * Copyright (c) 2017. 九象网络科技 - All Rights Reserved.
 * @author 徐泽宇
 *
 *
 */

package com.ninelephas.alopex.dispatcher;

import lombok.Data;

/**
 * 调度命令和参数 pojo 类
 *
 * @author roamer - 徐泽宇
 * @create 2017-05-2017/5/5  上午11:58
 */
@Data
public class DispatchCommand implements java.io.Serializable{
    private String method ;
    private String params ;
}
