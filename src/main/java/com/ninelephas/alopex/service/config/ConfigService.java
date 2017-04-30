package com.ninelephas.alopex.service.config;

import com.ninelephas.alopex.controller.config.ConfigItem;
import com.ninelephas.common.configer.ConfigHelper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by roamer on 2017/4/27.
 */
@Log4j2
@Service("com.ninelephas.alopex.service.config.ConfigService")
public class ConfigService {

    /**
     * 获取配置信息的所有 key 值
     *
     * @return
     */
    public ArrayList<ConfigItem> getKeys() {
        ArrayList<ConfigItem> arrayListItems = new ArrayList<ConfigItem>();
        Configuration configuration = ConfigHelper.getConfig();
        Iterator iter = configuration.getKeys();
        while (iter.hasNext()) {
            ConfigItem item = new ConfigItem();
            item.setKey((String) iter.next());
            item.setValue((String) configuration.getProperty(item.getKey()));
            arrayListItems.add(item);
        }
        return arrayListItems;
    }

    /**
     * 增加一个配置项目
     * @param item
     * @throws ConfigurationException
     */
    public void addItem(ConfigItem item ) throws ConfigurationException {
        ConfigHelper.setProperty(item.getKey(), item.getValue());
    }

    /**
     * 从配置文件中移除一个配置项
     * @param id
     * @throws ConfigurationException
     */
    public void removeItem(String id) throws ConfigurationException {
        ConfigHelper.removeProperty(id);
    }

}
