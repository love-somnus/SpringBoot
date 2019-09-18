package com.somnus.springboot.pay.thirdpay.alipay.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

/**
 * @ClassName:     AlipayCore.java
 * @Description:   支付宝接口公用函数类
 * @author         Somnus
 * @version        V1.0  
 * @Date           2017年1月3日 下午2:19:26
 */
public class AlipayCore {

    /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) { // 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /** 
     * 把数组所有元素按照固定参数排序，以“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkStringNoSort(Map<String, String> params) {

        StringBuilder gotoSign_params = new StringBuilder(); // 手机网站支付MD5签名固定参数排序，顺序参照文档说明
        gotoSign_params.append("service=" + params.get("service"));
        gotoSign_params.append("&v=" + params.get("v"));
        gotoSign_params.append("&sec_id=" + params.get("sec_id"));
        gotoSign_params.append("&notify_data=" + params.get("notify_data"));

        return gotoSign_params.toString();
    }

    /** 
     * 生成文件摘要
     * @param strFilePath 文件路径
     * @param file_digest_type 摘要算法
     * @return 文件摘要结果
     */
    public static String getAbstract(String strFilePath, String file_digest_type) throws IOException {
    	InputStream is = new FileInputStream(new File(strFilePath));
    	try {
			if (file_digest_type.equals("MD5")) {
			    return DigestUtils.md5Hex(is);
			} else if (file_digest_type.equals("SHA")) {
			    return DigestUtils.sha256Hex(is);
			}
		} finally{
            IOUtils.closeQuietly(is);
        }
		return "";
    }
}
