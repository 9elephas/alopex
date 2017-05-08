/*
 * Copyright (c) 2017. 九象网络科技 - All Rights Reserved.
 * @author 徐泽宇
 *
 *
 */

package com.ninelephas.common.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * HttpServletResponse 辅助类
 *
 * @author roamer - 徐泽宇
 * @create 2017-05-2017/5/8  上午11:50
 */
@Log4j2
public class HttpServletResponseHelper {

    /**
     * 返回成功后的json 形式的字符串
     *
     * @param returnObjString
     *
     * @return
     */
    @ResponseBody
    public static String Success(String returnObjString) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setStatus("Success");
        returnJson.setVersion("1.0.0");
        if (StringUtils.isEmpty(returnObjString) || returnObjString.equalsIgnoreCase("null")){
            returnJson.setResponse("");
        }else{
            returnJson.setResponse(returnObjString);
        }

        try {
            return JsonUtilsHelper.objectToJsonString(returnJson);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return e.getMessage();
        }
    }
}

/**
 * 自定义的返回的 json 结构
 */
@Data
class ReturnJson {
    private String status;
    private String version;
    private String response;

}
