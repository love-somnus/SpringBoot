package com.somnus.springboot.pay.enums;

import org.springframework.util.StringUtils;

/**
 * UA枚举 请求终端
 * @description: 
 * @author: 丹青生
 * @version: 1.0
 * @createdate: 2015-12-11
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-12-11       丹青生                               1.0            初始化
 
 */
/**
 * @ClassName:     UAType.java
 * @Description:   TODO
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 上午10:48:08
 */
public enum UAType {

    PC(0,"PC"),
    WX_BROWSER(1,"微信");

    private Integer value;
    private String message;

    private UAType(Integer value,String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取UA相关描述
     * @param ua
     * @return
     */
    public static Integer getUAType(String ua) {
        Integer res = 0;
        if(!StringUtils.isEmpty(ua)){
            String temp = ua.toLowerCase();
            if (temp.indexOf("micromessenger") != -1){
                res = WX_BROWSER.value;
            }
        }
        return res;
    }

    /**
     * 判断是否是APP
     * @param value
     * @return
     */
    public static boolean isAPPType(Integer value) {
        boolean res = false;
        if((null != value) ){
            res = true ;
        }
        return res;
    }

    /**
     * 判断是否是IOS平台
     * @param ua
     * @return
     */
    public static boolean isIOSPlatform(String ua) {
        boolean res = false;
        if(!StringUtils.isEmpty(ua)){
            String temp = ua.toLowerCase();
            if (temp.toLowerCase().indexOf("iphone") != -1 || temp.toLowerCase().indexOf("ipad") != -1) {// 区别 ios和 andriod的参数
                res = true;
            }
        }
        return res;
    }

}
