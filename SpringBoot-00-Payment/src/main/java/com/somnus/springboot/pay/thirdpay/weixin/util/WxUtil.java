package com.somnus.springboot.pay.thirdpay.weixin.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WxUtil {
	
	/**
     * 获取unix时间，从1970-01-01 00:00:00开始的秒数
     * @param date
     * @return long
     */
    public static long getUnixTime(Date date) {
        if (null == date) {
            return 0;
        }

        return date.getTime() / 1000;
    }
    
    /**
     * 获取编码字符集
     * @param request
     * @param response
     * @return String
     */
    public static String getCharacterEncoding(HttpServletRequest request,HttpServletResponse response) {

        if (null == request || null == response) {
            return "utf-8";
        }

        String enc = request.getCharacterEncoding();
        
        if (null == enc || "".equals(enc)) {
            enc = response.getCharacterEncoding();
        }

        if (null == enc || "".equals(enc)) {
            enc = "utf-8";
        }

        return enc;
    }
    
}
