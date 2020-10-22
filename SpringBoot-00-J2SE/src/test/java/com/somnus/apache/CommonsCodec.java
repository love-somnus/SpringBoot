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
        String encode = Base64.encodeBase64String("Somnus".getBytes(/*"UTF-8"*/));
        System.out.println("Base64 编码后：" + encode);

        byte[] decode = Base64.decodeBase64("U29tbnVz");
        String decodestr = new String(decode);
        System.out.println("Base64 解码后：" + decodestr);
    }

    @Test
    @SneakyThrows
    public void Base64JDK8() {
        String encode = java.util.Base64.getEncoder().encodeToString("https://new.qq.com/rain/a/20200817A04IXC00".getBytes(/*"UTF-8"*/));
        System.out.println("Base64 编码后：" + encode);

        byte[] decode = java.util.Base64.getDecoder().decode("aHR0cHM6Ly9uZXcucXEuY29tL3JhaW4vYS8yMDIwMDgxN0EwNElYQzAw");
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
        System.out.println(DigestUtils.md5Hex("admin"));
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
