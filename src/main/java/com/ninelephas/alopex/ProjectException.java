/**
 * @Title: UtilException.java
 * @Package com.ninelephas.terrier.util
 * @Description: UtileException 基类
 *               Copyright: Copyright (c) 2016
 *               Company:九象网络科技（上海）有限公司
 * 
 * @author 徐泽宇
 * @date 2016年10月14日 上午11:19:37
 * @version V1.0.0
 */

package com.ninelephas.alopex;

import org.springframework.validation.BindingResult;

/**
 * @ClassName: ProjectException
 * @Description: ProjectException 基类
 * @author 徐泽宇
 * @date 2016年10月14日 上午11:19:37
 *
 */

public class ProjectException extends Exception {


    /**
     * @Fields serialVersionUID : 序列号
     */
    private static final long serialVersionUID = -6397607290157480572L;

    private final  transient BindingResult bindingResult;

    /**
     * 
     * 创建一个新的实例 ProjectException.
     * 
     * @Auther 徐泽宇
     * @Date 2016年11月4日 下午1:27:12
     * @param message
     */
    public ProjectException(String message) {
        super(message);
        this.bindingResult = null;
    }


    /**
     * 
     * 创建一个新的实例 ProjectException.
     * 
     * @Auther 徐泽宇
     * @Date 2016年11月4日 下午1:27:29
     * @param bindingResult
     */
    public ProjectException(BindingResult bindingResult) {
        super();
        this.bindingResult = bindingResult;
    }

    
    public BindingResult getBindingResult(){
        return this.bindingResult;
    }
}
