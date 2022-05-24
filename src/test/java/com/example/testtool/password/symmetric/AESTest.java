/**
 * Author:   Herewe
 * Date:     2022/3/14 23:10
 * Description: DES加密算法
 */
package com.example.testtool.password.symmetric;

import org.junit.jupiter.api.Test;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class AESTest {
    public static final String PASSWORD = "chengdu0110";

    public static final String  ALGORITHM = "AES";
    // 算法名/工作模式/填充模式 (后两者不指定则有默认值)
    // CBC模式需要指定一个16位的初始向量 SecureRandom(16) + IvParameterSpec
    public static final String ALGORITHM_ALL = "AES/CBC/PKCS5Padding";

    @Test
    public void testE() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        SecretKey secretKey = getSecretKey();
        System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        SecureRandom secureRandom = new SecureRandom();
        // CBC需要生成一个16位的初始向量
        byte[] seed = secureRandom.generateSeed(16);
        System.out.println(Base64.getEncoder().encodeToString(seed));
        IvParameterSpec ivParameterSpec = new IvParameterSpec(seed);
        Cipher cipher = Cipher.getInstance(ALGORITHM_ALL);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] bytes = cipher.doFinal(PASSWORD.getBytes());
        String result = Base64.getEncoder().encodeToString(bytes);
        System.out.println(result);
    }

    @Test
    public void testD() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode("ChiMcweAVp9OGJ/3yEirhg==".getBytes()), ALGORITHM);
        String result = "Be4RZUs0frZPx7TMzo3HDg==";
        // 如果有key，则不生成，直接用key + ALGORITHM生成密钥即可
//        SecretKey secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        byte[] seed = Base64.getDecoder().decode("vlT0dWK8TsES2e9DvuCfug==");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(seed);
        Cipher cipher = Cipher.getInstance(ALGORITHM_ALL);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
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
        keyGenerator.init(128);
        // 密钥
        SecretKey secretKey = keyGenerator.generateKey();
        // 与利用工厂生成是等价的，建议指定算法生成的，比较通用
        SecretKey key = new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
        return key;
    }


}

