package com.somnus.apache;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

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
        String encode = Base64.encodeBase64String("Somnus罂粟花".getBytes(/*"UTF-8"*/));
        System.out.println("Base64 编码后：" + encode);

        byte[] decode = Base64.decodeBase64("U29tbnVz");
        String decodestr = new String(decode);
        System.out.println("Base64 解码后：" + decodestr);
    }

    @Test
    public void Base64JDK8() {
        String encode = java.util.Base64.getEncoder().encodeToString("0ff62954802f11e98e6d246e965dfd84:d12f8816584f4417a91e5fb2014190cb0ff63b38802f11e98e6d246e965dfd84".getBytes(/*"UTF-8"*/));
        System.out.println("Base64 编码后：" + encode);

        byte[] decode = java.util.Base64.getDecoder().decode("U29tbnVz");
        String decodestr = new String(decode);
        System.out.println("Base64 解码后：" + decodestr);
    }

    @Test
    public void Hex() throws DecoderException {
        byte[] buff = "Somnus罂粟花".getBytes(/*"utf-8"*/);
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
        /*System.out.println(DigestUtils.shaHex("admin"));*/
    }

    @Test
    public void URLCodec() throws UnsupportedEncodingException, DecoderException {
        System.out.println(java.net.URLEncoder.encode("hello&魔都", "UTF-8"));
        System.out.println(java.net.URLDecoder.decode("hello%26%E9%AD%94%E9%83%BD", "UTF-8"));

        System.out.println(new URLCodec().encode("hello&魔都", "UTF-8"));
        System.out.println(new URLCodec().decode("hello%26%E9%AD%94%E9%83%BD", "UTF-8"));
    }
}
