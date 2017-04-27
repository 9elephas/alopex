package com.ninelephas.alopex.controller.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ninelephas.alopex.controller.ControllerException;
import com.ninelephas.alopex.service.config.ConfigService;
import com.ninelephas.common.helper.JsonUtilsHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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


    @RequestMapping(value = "/getAllForDatatableContent")
    public @ResponseBody
    String indexForDatatable() throws ControllerException {
        // 获取所有的 config item
        ArrayList<ConfigItem> arrayListItem = configService.getKeys();
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
            m_rtn = JsonUtilsHelper.ObjectToJsonString(root);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(e);
        }
        log.debug(String.format("返回的 json 字符串是:\n %s", m_rtn));
        return m_rtn;
    }
}