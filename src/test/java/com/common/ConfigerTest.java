package com.common;

import com.ninelephas.common.configer.ConfigHelper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by roamer on 2017/4/27.
 */
@Log4j2
public class ConfigerTest {

    private String KEY_NAME = "Test.Item";

    /**
     * 测试获取配置文件中的属性值
     */
    @Test
    public void getProperty() {
        log.info(String.format("test 的属性值是: %s", ConfigHelper.getConfig().getString(KEY_NAME)));
    }

    /**
     * 测试设置配置中的属性值，并且保存到配置文件中
     */
    @Test
    public void setProperty() throws ConfigurationException {
        log.info(String.format("修改前的 %s 的属性值是: %s", KEY_NAME, ConfigHelper.getConfig().getString(KEY_NAME)));
        ConfigHelper.setProperty(KEY_NAME, String.format("属性值被修改成:啊啊啊啊!修改时间：%s", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
        log.info("属性值设置调用完成");
        log.info(String.format("重新获取 %s 的属性值是: %s", KEY_NAME, ConfigHelper.getConfig().getString(KEY_NAME)));
    }


}
