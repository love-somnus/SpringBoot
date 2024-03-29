package com.somnus.apache;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import lombok.SneakyThrows;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.net.URLCodec;
import org.junit.Test;

/**
 * @author Somnus
 * @version V1.0
 * @Description: TODO
 * @date 2015年6月12日 下午5:38:47
 */
public class CommonsCodec {

    @Test
    public void Base64() {
        byte[] key = java.util.Base64.getDecoder().decode("DEsSyV+rxMOqtsgdm80xf99QD0AWf/1zokLd9ne5kJQS5xAb5v3gGD4/qMKgmy7gnGJ2LgrEZhWpX4Jl5EpfeeeiTrjUTfMBLG28ire8VgYqN/s5/JthUoPnd/BrB9sT2KxRK8j4i+LMKiN56x00GyBQeVnkjw4X73cok3o6OF4=");

        System.out.println(Arrays.toString(key));
        System.out.println(key.length);
    }

    @Test
    @SneakyThrows
    public void Base64JDK8() {
        String abc = "grantCode=ac02c00e332c11r&appAbbr=CloudFlower&device={\"clientIp\":\"172.0.1.1\", \"channel\":\"0\", \"deviceId\":\"0\", \"deviceType\":\"0\", \"appVer\":\"0\", \"os\":\"0\", \"osVer\":\"0\", \"brand\":\"0\", \"model\":\"0\"}";
        String encode = java.util.Base64.getEncoder().encodeToString(abc.getBytes(/*"UTF-8"*/));
        System.out.println("Base64 编码后：" + encode);
        System.out.println(java.util.Base64.getEncoder().encodeToString("appAbbr=cms&code=186e95667e7b49aebf3ee1f0d31047f5".getBytes(/*"UTF-8"*/)));

        byte[] decode = java.util.Base64.getDecoder().decode("Z3JhbnRDb2RlPTEyMzQ1NiZhcHBBYmJyPUNsb3VkRmxvd2VyJmRldmljZT17ImNsaWVudElwIjoiMTcyLjAuMS4xIiwgImNoYW5uZWwiOiIwIn0=");
        String decodestr = new String(decode);
        System.out.println("Base64 解码后：" + decodestr);

        String md5 = DigestUtils.md5Hex("style_9.png" + "8d969eef6ecad3c");
        System.out.println(md5);
    }

    @Test
    public void Hex() throws DecoderException {
        byte[] buff = "Somnus".getBytes(/*"utf-8"*/);
        System.out.println(Arrays.toString(buff));

        String byte2hex = Hex.encodeHexString(buff);
        System.out.println(byte2hex);

        byte[] hex2byte = Hex.decodeHex(byte2hex.toCharArray());
        System.out.println(Arrays.toString(hex2byte));
        System.out.println(new String(hex2byte));
    }

    @Test
    public void DigestUtils() {
        System.out.println(DigestUtils.md5Hex("{\"productId\":\"com.boltrend.sdk.demo.sku001\",\"price\":\"10000\",\"ip\":\"192.168.97.101\",\"currency\":\"HKD\",\"type\":\"14\",\"userid\":\"1222010\",\"productName\":\"sku001\",\"rstUrl\":\"http://api.test.nesh/wt-cpc/payment/order/notify\"}" + "4031f6027a864c85978df5cdd2a6f39e"));
        System.out.println(DigestUtils.sha1Hex("admin"));

        System.out.println( org.springframework.util.DigestUtils.md5DigestAsHex("admin".getBytes()));
    }

    @Test
    public void shortUrl(){
        String hex = DigestUtils.md5Hex("admin");
        String[] chars = new String[]{
                "a","b","c","d","e","f","g","h", "i","j","k","l","m","n","o","p", "q","r","s","t","u","v","w","x", "y","z",
                "0","1","2","3","4","5", "6","7","8","9",
                "A","B","C","D", "E","F","G","H","I","J","K","L", "M","N","O","P","Q","R","S","T", "U","V","W","X","Y","Z"
        };
        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {
            //把加密字符按照8位一组16进制与0x3FFFFFFF进行位与运算
            int hexint = 0x3FFFFFFF & Integer.parseUnsignedInt(hex.substring(i * 8, (i+1) * 8), 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                //把得到的值与0x0000003D进行位与运算，取得字符数组chars索引
                int index = 0x0000003D & hexint;
                //把取得的字符相加
                outChars += chars[index];
                //每次循环按位右移5位
                hexint = hexint >> 5;
            }
            //把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        Arrays.stream(resUrl).forEach(item -> System.out.println(item));
    }

    @Test
    public void URLCodec() throws UnsupportedEncodingException, DecoderException {
        System.out.println(java.net.URLEncoder.encode("hello&魔都", "UTF-8"));
        System.out.println(java.net.URLDecoder.decode("hello%26%E9%AD%94%E9%83%BD", "UTF-8"));

        System.out.println(new URLCodec().encode("hello&魔都", "UTF-8"));
        System.out.println(new URLCodec().decode("hello%26%E9%AD%94%E9%83%BD", "UTF-8"));
    }
}
