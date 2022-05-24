/**
 * Author:   Herewe
 * Date:     2022/3/13 23:15
 * Description: 散列算法
 */
package com.example.testtool.password;

import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class MDTest {
    public static final String PASSWORD = "chengdu0110";
    public static final String MD5 = "md5";
    public static final String SHA_256 = "sha-256";
    public static final String HMAC_SHA_256 = "HmacSHA256";
    @Test
    public void testMD5() throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(MD5);
        String salt = UUID.randomUUID().toString();
        // 加盐，防止彩虹表破解
        md5.update(salt.getBytes(StandardCharsets.UTF_8));
        md5.update(PASSWORD.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md5.digest();
        // 结果转换为16进制字符串(也可以使用工具类)spring、commons-codec等
        System.out.println(new BigInteger(1, digest).toString(16));
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes(StandardCharsets.UTF_8)));
        System.out.println(byteToHex(digest));
    }

    @Test
    public void testSha() throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(SHA_256);
        String salt = UUID.randomUUID().toString();
        md5.update(PASSWORD.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md5.digest();
        // 结果转换为16进制字符串(可以使用工具类)spring、commons-codec等
        System.out.println(new BigInteger(1, digest).toString(16));
        System.out.println(encodeHex(digest));
        System.out.println(byteToHex(digest));
    }

    @Test
    public void testMac() throws NoSuchAlgorithmException, InvalidKeyException {
        // 对接第三方时，我们一般使用密钥作为key
        SecretKeySpec secretKey = new SecretKeySpec("secretKey".getBytes(), HMAC_SHA_256);
        KeyGenerator key = KeyGenerator.getInstance(HMAC_SHA_256);
        Mac mac = Mac.getInstance(HMAC_SHA_256);
        mac.init(key.generateKey());
        byte[] bytes = mac.doFinal(PASSWORD.getBytes(StandardCharsets.UTF_8));
        // 结果转换为16进制字符串(可以使用工具类)spring、commons-codec等
        System.out.println(new BigInteger(1, bytes).toString(16));
        System.out.println(byteToHex(bytes));
    }

    /**
     * 结果转换为16进制字符串
     * @param bytes
     * @return
     */
    private String byteToHex(byte[] bytes){
        if (bytes==null){
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        String temp=null;
        for (int i = 0; i <bytes.length ; i++) {
            temp = Integer.toHexString(bytes[i]&0xff);
            if (temp.length()==1){
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    private static final char[] HEX_CHARS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * spring中转16进制字符串
     */
    private char[] encodeHex(byte[] bytes) {
        char[] chars = new char[64];
        for (int i = 0; i < chars.length; i = i + 2) {
            byte b = bytes[i / 2];
            chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
            chars[i + 1] = HEX_CHARS[b & 0xf];
        }
        return chars;
    }
}
