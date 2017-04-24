package com.ninelephas.alopex.configer;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
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

    private static Configuration config;

    private ConfigHelper() {
        //do nothing
    }

    static {
        if (log.isDebugEnabled()) {
            log.debug("ConfigHelper() - start"); //$NON-NLS-1$
        }
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(new Parameters().properties()
                                .setFileName("config/config.properties").setEncoding("UTF-8"));
        try {
            config = builder.getConfiguration();
        } catch (ConfigurationException cex) {
            log.error(cex, cex.fillInStackTrace()); //$NON-NLS-1$
        }

        if (log.isDebugEnabled()) {
            log.debug("ConfigHelper() - end"); //$NON-NLS-1$
        }
    }

    public static Configuration getConfig() {
        return config;
    }

}
