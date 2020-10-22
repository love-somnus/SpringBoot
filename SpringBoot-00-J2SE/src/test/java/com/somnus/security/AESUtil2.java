package com.somnus.security;

import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.Key;

/**
 * @author Kevin
 * @packageName com.netease.wt.microservice.commons.core.utils
 * @title: AESUtil
 * @description: TODO
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
     *
     * 在Key keyGenerator(byte[] key)方法中使用下述代码
     * <code>SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);</code> 替换
     * <code>
     * DESKeySpec desKey = new DESKeySpec(key);
     * SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
     * SecretKey secretKey = keyFactory.generateSecret(desKey);
     * </code>
     */
    public static final String ALGORITHM = "AES";

    private static final String key = "sqT0TOQLtH/FMLlAtus0mQ";

    /**
     *
     * 生成密钥key对象
     * @param key 密钥字符串
     * @return 密钥对象
     */
    @SneakyThrows
    private static SecretKey keyGenerator(byte[] key){
        /*当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码 */
        SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
        return secretKey;
    }

    /**
     * 加密数据
     * @param data 待加密数据
     * @return 加密后的数据
     */
    @SneakyThrows
    public static String encrypt(String data){
        Key deskey = keyGenerator(Base64.decodeBase64(key));
        // 实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 初始化Cipher对象，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] buff = cipher.doFinal(data.getBytes());
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
        return Hex.encodeHexString(buff);
    }

    /**
     * 解密数据
     * @param data 待解密数据
     * @return 解密后的数据
     */
    @SneakyThrows
    public static String decrypt(String data){
        Key deskey = keyGenerator(Base64.decodeBase64(key));
        // 实例化Cipher对象，它用于完成实际的解密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        //初始化Cipher对象，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        // 执行解密操作
        byte[] buff = cipher.doFinal(Hex.decodeHex(data.toCharArray()));
        return new String(buff);
    }

    public static void main(String[] args) throws Exception {


        String encryptData = encrypt("867");
        System.out.println("加密后: " + encrypt("867"));
        System.out.println("加密后: " + encrypt("867"));
        System.out.println("加密后: " + encrypt("867"));
        System.out.println("加密后: " + encrypt("867"));

        String decryptData = decrypt(encryptData);
        System.out.println("解密后: " + decryptData);
    }
}
