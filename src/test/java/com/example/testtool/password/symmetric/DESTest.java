/**
 * Author:   Herewe
 * Date:     2022/3/14 23:10
 * Description: DES加密算法
 */
package com.example.testtool.password.symmetric;

import org.junit.jupiter.api.Test;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class DESTest {
    public static final String PASSWORD = "chengdu0110";

    public static final String ALGORITHM = "DES";
    // 算法名/工作模式/填充模式 (后两者不指定则有默认值)
    public static final String ALGORITHM_ALL = "DES/ECB/PKCS5Padding";

    @Test
    public void testE() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
        SecretKey secretKey = getSecretKey();
        System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        Cipher cipher = Cipher.getInstance(ALGORITHM_ALL);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] bytes = cipher.doFinal(PASSWORD.getBytes());
        String result = Base64.getEncoder().encodeToString(bytes);
        System.out.println(result);
    }

    @Test
    public void testD() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode("itCGv/KzN0Y=".getBytes()), ALGORITHM);
        String result = "flDyfVx2l+qtgUAo6gBmZA==";
        // 如果有key，则不生成，直接用key + ALGORITHM生成密钥即可
//        SecretKey secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM_ALL);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(result.getBytes()));
        String s = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(s);
    }

    /**
     * 获取密钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     */
    private SecretKey getSecretKey() throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        // 对称加密算法必须指定密钥长度
        keyGenerator.init(56);
        // 密钥
        SecretKey secretKey = keyGenerator.generateKey();
//        // 变为DES密钥材料
//        DESKeySpec desKeySpec = new DESKeySpec(secretKey.getEncoded());
//        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
//        // 密钥工厂生成DES密钥
//        SecretKey key = secretKeyFactory.generateSecret(desKeySpec);
        // 与利用工厂生成是等价的，建议指定算法生成的，比较通用
        SecretKey key = new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
        return key;
    }


}

