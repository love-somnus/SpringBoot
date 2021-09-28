package com.somnus.security;

import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Kevin
 * @packageName com.netease.wt.microservice.commons.core.utils
 * @title: AESUtil
 * @description: http://tool.chacuo.net/cryptaes
 * @date 2020/2/26 17:26
 */
public abstract class AESUtil2 {

    /**
     * ALGORITHM 算法 <br>
     * 可替换为以下任意一种算法，同时key值的size相应改变。
     *
     * <pre>
     * DES                  key size must be equal to 56
     * DESede(TripleDES)    key size must be equal to 112 or 168
     * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
     * Blowfish             key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
     * RC2                  key size must be between 40 and 1024 bits
     * RC4(ARCFOUR)         key size must be between 40 and 1024 bits
     * </pre>
     */
    public static final String ALGORITHM = "AES";

    //密钥为16位
    private static final String key = "MIGfMA0GCSqGSIb3";

    //偏移量，AES 128位数据块对应偏移量为16位
    public static final String IV = "5oEUdAyqrBEYGCoU";

    //"算法/模式/补码方式"（Java不支持AES/CBC/PKCS7Padding）
    private static final String PADDING = "AES/CBC/PKCS5Padding";

    //编码方式
    public static final String CODE_TYPE = "UTF-8";

    /**
     * 加密数据
     * @param data 待加密数据
     * @return 加密后的数据
     */
    @SneakyThrows
    public static String encrypt(String data){
        /*
         * 新建一个密码编译器的实例，由三部分构成，用"/"分隔，分别代表如下
         * 1. 加密的类型(如AES，DES，RC2等)
         * 2. 模式(AES中包含ECB，CBC，CFB，CTR，CTS等)
         * 3. 补码方式(包含nopadding/PKCS5Padding等等)
         * 依据这三个参数可以创建很多种加密方式
         */
        Cipher cipher = Cipher.getInstance(PADDING);
        //偏移量
        IvParameterSpec zeroIv = new IvParameterSpec(IV.getBytes(CODE_TYPE));
        //使用加密秘钥
        SecretKey skeySpec = new SecretKeySpec(key.getBytes(CODE_TYPE), ALGORITHM);
        //初始化为加密模式的密码器
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, zeroIv);
        //执行加密操作
        byte[] buff = cipher.doFinal(data.getBytes(CODE_TYPE));
        //加密后的结果通常都会用Hex编码进行传输[https://cryptojs.gitbook.io/docs/#encoders]
        return Hex.encodeHexString(buff);
    }

    /**
     * 解密数据
     * @param data 待解密数据
     * @return 解密后的数据
     */
    @SneakyThrows
    public static String decrypt(String data){
        // 实例化Cipher对象，它用于完成实际的解密操作
        Cipher cipher = Cipher.getInstance(PADDING);
        //偏移量
        IvParameterSpec zeroIv = new IvParameterSpec(IV.getBytes(CODE_TYPE));
        //使用加密秘钥
        SecretKey skeySpec = new SecretKeySpec(key.getBytes(CODE_TYPE), ALGORITHM);
        //初始化Cipher对象，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, zeroIv);
        // 执行解密操作
        byte[] buff = cipher.doFinal(Hex.decodeHex(data.toCharArray()));
        //byte转换为字符串
        return new String(buff, CODE_TYPE);
    }

    public static void main(String[] args){

        System.out.println("加密后: " + encrypt("token=ff445ff786c8424397c61faff068e0ed"));

        String decryptData = decrypt("df0095e2d058adfa94736b5aae0d575c4422a1147a45b38323855455e881ead47c840c76a72cb519a3cbfb63c118b555");

        System.out.println("解密后: " + decryptData);
    }
}
