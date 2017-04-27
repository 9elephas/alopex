/**   
* @Title: JsonUtilsHelper.java 
* @Package com.ninelephas.meerkat.helper 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 徐泽宇 
* @date 2016年5月6日 上午11:59:18 
* @version V1.0   
*/
package com.ninelephas.common.helper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

/**
* @ClassName: JsonUtilsHelper 
* @Description: 使用Json工具包进行json和对象的相互转换的helper类
* @author 徐泽宇 
* @date 2016年5月6日 上午11:59:18 
*  
*/
@Log4j2
public class JsonUtilsHelper {


    private static ObjectMapper mapper ;

    private JsonUtilsHelper(){
        // do nothing
    }

    static {
        log.debug("JsonUtilsHelper() - start"); //$NON-NLS-1$
         mapper = new ObjectMapper();
        log.debug("JsonUtilsHelper() - end"); //$NON-NLS-1$

    }

    /** 
    * ObjectToJsonString 
    * TODO 把一个对象转换成json字符串
    * @param obj
    * @return json字符串
    * @throws Exception   
    * String    返回类型 
    * @throws 
    */
    public static String ObjectToJsonString(Object obj) throws JsonProcessingException {
        return  mapper.writeValueAsString(obj);
    }
    
    

}
