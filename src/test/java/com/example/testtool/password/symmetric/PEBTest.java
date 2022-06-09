/**
 * Author:   Herewe
 * Date:     2022/3/15 0:08
 * Description: 口令加密算法，用户输入的口令作为密钥，但规律性较强，所以需要手动加入盐值进行加密
 */
package com.example.testtool.password.symmetric;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PEBTest {

    public static final String PASSWORD = "chengdu0110";

    public static final String  ALGORITHM = "PBEWithHmacSHA1AndAES_128";
    // 包含Hmac的都需要初始化向量
    public static final String ALGORITHM_ALL = "PBEWithHmacSHA1AndAES_128";

    public static final String KEY = "hello";

    public static final int COUNT = 100;


    @Test
    public void testE() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
//        Security.addProvider(new BouncyCastleProvider());
        SecureRandom secureRandom = new SecureRandom();
        // 初始化向量
        byte[] seed = secureRandom.generateSeed(16);
        System.out.println("seed,salt:" + Base64.getEncoder().encodeToString(seed));
        IvParameterSpec ivParameterSpec = new IvParameterSpec(seed);
        // key
        PBEKeySpec pbeKeySpec = new PBEKeySpec(KEY.toCharArray());
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
        System.out.println("key:" + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        // 实例化PBE参数材料
        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(seed, COUNT, ivParameterSpec);
        // Cipher
        Cipher cipher = Cipher.getInstance(ALGORITHM_ALL);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, pbeParameterSpec);
        byte[] bytes = cipher.doFinal(PASSWORD.getBytes());
        String result = Base64.getEncoder().encodeToString(bytes);
        System.out.println("result:"+result);
    }

    @Test
    public void testD() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        // 只要往材料中添加了key，那么生成的Secret就是一样的
        PBEKeySpec pbeKeySpec = new PBEKeySpec(KEY.toCharArray());
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
        System.out.println("key:" + Base64.getEncoder().encodeToString(secretKey.getEncoded()));

        String result = "QNZMBdmnWWETb5nW2hAYTA==";
        byte[] seed = Base64.getDecoder().decode("b+usIwC80OAkm6086uPddQ==");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(seed);

        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(seed, COUNT, ivParameterSpec);

        Cipher cipher = Cipher.getInstance(ALGORITHM_ALL);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, pbeParameterSpec);
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
