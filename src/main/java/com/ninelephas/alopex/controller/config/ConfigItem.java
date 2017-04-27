package com.ninelephas.alopex.controller.config;

import lombok.Data;

/**
 * config 的配置项对象，等同于 property
 * Created by roamer on 2017/4/27.
 */
@Data
public class ConfigItem {

    private String key;
    private String value ;
}
