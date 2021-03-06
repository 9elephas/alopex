package com.ninelephas.alopex.controller.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ninelephas.alopex.controller.ControllerException;
import com.ninelephas.alopex.service.config.ConfigService;
import com.ninelephas.common.helper.JsonUtilsHelper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    /**
     * 显示配置的所有属性的界面内容
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        log.debug("获取配置信息的列表");
        ModelAndView mv = new ModelAndView();
        log.debug("返回配置项列表的视图");
        return mv;
    }

    /**
     * 以 REST 的方式提供所有的配置值的 json 字符串
     *
     * @return
     * @throws ControllerException
     */
    @RequestMapping(value = "/rest/index.json")
    public @ResponseBody
    String indexForDatatable() throws ControllerException {
        // 获取所有的 config item
        ArrayList<ConfigItem> arrayListItem = configService.getKeys();
        String m_rtn;
        m_rtn = this.gerenateJson(arrayListItem);
        return m_rtn;
    }

    /**
     * 增加一个属性项
     *
     * @param key
     * @param value
     * @return
     * @throws ControllerException
     */
    @RequestMapping(value = "/rest/create", method = RequestMethod.POST)
    public @ResponseBody
    String create(@RequestParam(value = "data[0][key]", required = true) String key, @RequestParam(value = "data[0][value]", required = true) String value) throws ControllerException {
        log.debug("新建一个配置项");
        log.debug(String.format("增加的key 是 %s, value 是 %s", key, value));
        //增加一个配置项
        ConfigItem configItem = new ConfigItem();
        try {
            configItem.setKey(key);
            configItem.setValue(value);
            configService.addItem(configItem);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            log.error(e);
            throw new ControllerException(e.getMessage());
        }

        String m_rtn = null;
        //使用 fastjson 进行 json 化
        ArrayList<ConfigItem> arrayListItem = new ArrayList<>();
        arrayListItem.add(configItem);
        m_rtn = this.gerenateJson(arrayListItem);
        return m_rtn;
    }

    /**
     * 删除一个配置项
     *
     * @param key
     * @throws ConfigurationException
     */
    @RequestMapping(value = "/rest/remove", method = RequestMethod.DELETE)
    public @ResponseBody
    String remove(@RequestParam(value = "key", required = true) String key) throws ControllerException {
        log.debug(String.format("删除项目是: %s", key));
        try {
            configService.removeItem(key);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            throw new ControllerException(e.getMessage());
        }
        HashMap<String, String> root = new HashMap<>();
        root.put("data", "");
        String m_rtn;
        try {
            m_rtn = JsonUtilsHelper.objectToJsonString(root);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(e);
            throw new ControllerException(e.getMessage());
        }
        return m_rtn;
    }

    /**
     * 更新一个配置项
     * 上传的 参数是 data[<原有的 key>][key]=aa333&data[<原有的 key>][value]=bbb
     *
     * @return
     * @throws ControllerException
     */
    @RequestMapping(value = "/rest/edit", method = RequestMethod.PUT)
    public @ResponseBody
    String upate(@RequestParam(value = "key") String key, HttpServletRequest request) throws ControllerException {
        String new_key = (String) request.getParameter(String.format("data[%s][key]", key));
        String new_value = (String) request.getParameter(String.format("data[%s][value]", key));
        log.debug(String.format("配置项将被修改成 key【%s】， value 【%s】 ", new_key, new_value));
        ConfigItem configItem = new ConfigItem();
        configItem.setKey(new_key);
        configItem.setValue(new_value);
        try {
            // 为了适应修改 key 的情况，可以先删除原有的 key 的配置项
            configService.removeItem(key);
            // 再增加新修改的配置项
            configService.addItem(configItem);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            log.error(e);
            throw new ControllerException(e.getMessage());
        }
        //使用 fastjson 进行 json 化
        String m_rtn;
        ArrayList<ConfigItem> arrayListItem = new ArrayList<>();
        arrayListItem.add(configItem);
        m_rtn = this.gerenateJson(arrayListItem);
        return m_rtn;
    }


    /**
     * 把配置项目属性转换成 json 字符串
     */
    private String gerenateJson(ArrayList<ConfigItem> arrayListItem) throws ControllerException {
        HashMap<String, List<Object>> root = new HashMap<String, List<Object>>();
        List<Object> data = new ArrayList<Object>();

        for (ConfigItem item : arrayListItem) {
            HashMap<String, Object> itemHash = new HashMap<String, Object>();
            itemHash.put("id", item.getKey());
            itemHash.put("key", item.getKey());
            itemHash.put("value", item.getValue());
            data.add(itemHash);
        }
        root.put("data", data);

        String m_rtn = null;
        //使用 fastjson 进行 json 化
        try {
            m_rtn = JsonUtilsHelper.objectToJsonString(root);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(e);
            throw new ControllerException(e.getMessage());
        }
        log.debug(String.format("返回的 json 字符串是:\n %s", m_rtn));
        return m_rtn;
    }
}
