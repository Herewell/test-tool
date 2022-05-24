/**
 * Author:   Herewe
 * Date:     2022/3/15 23:32
 * Description: 密钥交换算法
 */
package com.example.testtool.password.symmetric;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import javax.crypto.KeyAgreement;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

public class DH {
    @Test
    public void test() {
        Person bob = new Person("B");
        Person alice = new Person("A");
        // 1.各自生成KeyPair:
        bob.generateKeyPair();
        alice.generateKeyPair();
        // 2.双方交换各自的PublicKey:
        bob.generateSecretKey(alice.publicKey.getEncoded());
        alice.generateSecretKey(bob.publicKey.getEncoded());
        // 3.检查双方的本地密钥是否相同:
        bob.printKeys();
        alice.printKeys();
        System.out.println(Base64.getEncoder().encodeToString(alice.getSecretKey()).equals(Base64.getEncoder().encodeToString(bob.getSecretKey())));

        // 4.双方的SecretKey相同，后续通信将使用SecretKey作为密钥进行AES加解密...
    }
}

@Getter
class Person {
    public final String name;
    public PublicKey publicKey;
    private PrivateKey privateKey;
    private byte[] secretKey;

    public Person(String name) {
        this.name = name;
    }

    // 生成本地KeyPair:
    public void generateKeyPair() {
        try {
            KeyPairGenerator kpGen = KeyPairGenerator.getInstance("DH");
            kpGen.initialize(512);
            KeyPair kp = kpGen.generateKeyPair();
            this.privateKey = kp.getPrivate();
            this.publicKey = kp.getPublic();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateSecretKey(byte[] receivedPubKeyBytes) {
        try {
            // 从byte[]恢复PublicKey:
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(receivedPubKeyBytes);
            KeyFactory kf = KeyFactory.getInstance("DH");
            PublicKey receivedPublicKey = kf.generatePublic(keySpec);
            // 生成本地密钥:
            KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
            keyAgreement.init(this.privateKey); // 自己的PrivateKey
            keyAgreement.doPhase(receivedPublicKey, true); // 对方的PublicKey
            // 生成SecretKey密钥:
            this.secretKey = keyAgreement.generateSecret();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public void printKeys() {
        System.out.printf("Name: %s\n", this.name);
        System.out.printf("Private key: %x\n", new BigInteger(1, this.privateKey.getEncoded()));
        System.out.printf("Public key: %x\n", new BigInteger(1, this.publicKey.getEncoded()));
        System.out.printf("Secret key: %x\n", new BigInteger(1, this.secretKey));
    }
}
