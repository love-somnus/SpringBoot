package com.somnus.springboot.pay.thirdpay.alipay.sign;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @ClassName:     RSA.java
 * @Description:   支付宝RSA签名处理
 * @author         Somnus
 * @version        V1.0  
 * @Date           2017年1月3日 下午2:46:04
 */
public class RSA {

    public static final String ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";  

    /**
    * RSA签名
    * @param content 待签名数据
    * @param privateKey 商户私钥
    * @param input_charset 编码格式
    * @return 签名值
    */
    public static String sign(String content, String privateKey, String input_charset){
        // 解密由base64编码的私钥
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        // 构造PKCS8EncodedKeySpec对象  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        try {
            // ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            // 取私钥匙对象
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(priKey);
            signature.update(content.getBytes(input_charset));
            return Base64.encodeBase64String(signature.sign());
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SignatureException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    /**
    * RSA验签名检查
    * @param content 待签名数据
    * @param sign 签名值
    * @param ali_public_key 支付宝公钥
    * @param input_charset 编码格式
    * @return 布尔值
    */
    public static boolean verify(String content, String sign, String ali_public_key, String input_charset){
        // 解密由base64编码的公钥
        byte[] keyBytes = Base64.decodeBase64(ali_public_key);
        // 构造X509EncodedKeySpec对象  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
        try {
            // ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            // 取公钥匙对象
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(input_charset));
            // 验证签名是否正常
            return signature.verify(Base64.decodeBase64(sign));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
    * 解密
    * @param content 密文
    * @param private_key 商户私钥
    * @param input_charset 编码格式
    * @return 解密后的字符串
    */
    public static String decrypt(String content, String private_key, String input_charset) throws Exception {
        // 对密钥解密
        byte[] keyBytes = Base64.decodeBase64(private_key);
        // 取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);  
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);  
        //初始化Cipher对象，设置为解密模式
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // 执行解密操作
        byte[] buff = cipher.doFinal(Hex.decodeHex(content.toCharArray()));
        return new String(buff,input_charset);
    }

}
