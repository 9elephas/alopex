package com.ninelephas.common.configer;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;


/**
 * @author 徐泽宇
 * @ClassName: ConfigHelper
 * @Description: 读取配置文件，获取系统全局配置参数
 * @date 2016年10月24日 下午10:48:32
 */
@Log4j2
public class ConfigHelper {

    private static FileBasedConfigurationBuilder<XMLConfiguration> builder;
    private static Configuration config;


    private ConfigHelper() {
        //do nothing
    }

    static {
        log.debug("ConfigHelper() - start"); //$NON-NLS-1$
        Parameters params = new Parameters();
        builder = new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
                .configure(params.xml()
                        .setFileName("config/config.xml"));
        try {
            config = builder.getConfiguration();
        } catch (ConfigurationException cex) {
            log.error(cex, cex.fillInStackTrace()); //$NON-NLS-1$
        }

        log.debug("ConfigHelper() - end"); //$NON-NLS-1$

    }

    public static Configuration getConfig() {
        return config;
    }


    /**
     * 设置属性项目
     *
     * @param key
     * @param value
     * @throws ConfigurationException
     */
    public static void setProperty(String key, Object value) throws ConfigurationException {
        config.setProperty(key, value);
        builder.save();
    }


    /**
     * 移除一个配置项
     * @param key
     * @throws ConfigurationException
     */
    public static void removeProperty(String key) throws ConfigurationException{
        config.clearProperty(key);
        builder.save();
    }

}
